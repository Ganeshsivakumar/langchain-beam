package com.templates.langchainbeam;

import java.util.Map;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.io.kafka.KafkaRecord;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.joda.time.Duration;

import com.langchainbeam.EmbeddingModelHandler;
import com.langchainbeam.LangchainBeamEmbedding;
import com.langchainbeam.model.EmbeddingModelOptions;
import com.langchainbeam.model.openai.OpenAiEmbeddingModelOptions;

public class KafkaToPinecone {

    public static void main(String[] args) {

        KafkaToPineconePipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation()
                .as(KafkaToPineconePipelineOptions.class);

        Pipeline p = Pipeline.create(options);

        EmbeddingModelOptions modelOptions = OpenAiEmbeddingModelOptions.builder()
                .modelName(options.getEmbeddingModel())
                .apikey(options.getOpenaiApiKey())
                .build();

        EmbeddingModelHandler handler = new EmbeddingModelHandler(modelOptions);

        p.apply(KafkaIO.<String, String>read()
                .withBootstrapServers(options.getBrokers())
                .withTopic(options.getTopic())
                .withKeyDeserializer(StringDeserializer.class)
                .withValueDeserializer(StringDeserializer.class)
                .withConsumerConfigUpdates(Map.of(
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest",
                        ConsumerConfig.GROUP_ID_CONFIG,
                        "langchain-beam-consumer-" + System.currentTimeMillis())))
                .apply(Window.<KafkaRecord<String, String>>into(
                        FixedWindows.of(Duration.standardSeconds(10))))
                .apply(MapElements.into(TypeDescriptors.strings())
                        .via((KafkaRecord<String, String> record) -> record.getKV().getValue()))
                .apply(LangchainBeamEmbedding.embed(handler))
                .apply(ParDo.of(new PineconeWriter(
                        options.getPineconeApiKey(),
                        options.getPineconeApiUrl(),
                        options.getPineconeIndex(),
                        options.getPineconeNamespace())));
        p.run();
    }
}
