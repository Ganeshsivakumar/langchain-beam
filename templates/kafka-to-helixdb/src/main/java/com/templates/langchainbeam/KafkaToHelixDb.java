package com.templates.langchainbeam;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.transforms.windowing.AfterProcessingTime;
import org.apache.beam.sdk.transforms.windowing.AfterWatermark;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.joda.time.Duration;

import com.langchainbeam.EmbeddingModelHandler;
import com.langchainbeam.LangchainBeamEmbedding;
import com.langchainbeam.model.EmbeddingModelOptions;
import com.langchainbeam.model.openai.OpenAiEmbeddingModelOptions;

public class KafkaToHelixDb {

    public static void main(String[] args) {

        KafkaToHelixDbPipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation()
                .as(KafkaToHelixDbPipelineOptions.class);

        Pipeline p = Pipeline.create(options);

        EmbeddingModelOptions modelOptions = OpenAiEmbeddingModelOptions.builder()
                .modelName(options.getEmbeddingModel())
                .apikey(options.getOpenaiApiKey())
                .build();

        EmbeddingModelHandler handler = new EmbeddingModelHandler(modelOptions);

        p.apply("Read from Kafka", KafkaIO.<String, String>read()
                .withBootstrapServers(options.getBrokers())
                .withTopic(options.getTopic())
                .withKeyDeserializer(StringDeserializer.class)
                .withValueDeserializer(StringDeserializer.class)
                .withConsumerConfigUpdates(Config.buildKafkaConfig(options)).withoutMetadata())
                .apply("Fixed Window", Window
                        .<KV<String, String>>into(FixedWindows.of(Duration.standardSeconds(10)))
                        .triggering(
                                AfterWatermark.pastEndOfWindow()
                                        .withLateFirings(AfterProcessingTime
                                                .pastFirstElementInPane()
                                                .plusDelayOf(Duration
                                                        .standardSeconds(
                                                                10)))
                                        .withEarlyFirings(AfterProcessingTime
                                                .pastFirstElementInPane()
                                                .plusDelayOf(Duration
                                                        .standardSeconds(
                                                                10))))
                        .withAllowedLateness(Duration.standardMinutes(5))
                        .accumulatingFiredPanes())
                .apply("Extract Values", Values.<String>create())
                .apply("Embed text", LangchainBeamEmbedding.embed(handler))
                .apply("Write to HelixDB",
                        ParDo.of(new HelixDbWriter(options.getHelixEndpoint(), options.getHelixUrl())));

        p.run();
    }
}
