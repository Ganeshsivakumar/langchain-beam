package com.langchainbeam.example;

import java.util.Arrays;

import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.Pipeline;

import com.langchainbeam.EmbeddingModelHandler;
import com.langchainbeam.LangchainBeamEmbedding;
import com.langchainbeam.model.EmbeddingOutput;
import com.langchainbeam.model.openai.OpenAiEmbeddingModelOptions;

public class EmbeddingPipeline {
    public static void main(String[] args) {

        String apiKey = System.getenv("OPENAI_API_KEY");

        // create Embedding model options
        OpenAiEmbeddingModelOptions moptions = OpenAiEmbeddingModelOptions.builder().apikey(apiKey)
                .modelName("text-embedding-3-small")
                .build();

        EmbeddingModelHandler handler = new EmbeddingModelHandler(moptions);

        PipelineOptions options = PipelineOptionsFactory.create();
        options.setRunner(DirectRunner.class);

        // create beam pipeline
        Pipeline p = Pipeline.create(options);

        // load data
        p.apply(TextIO.read().from("/home/ganesh/Downloads/product_reviews.csv"))

                // apply LangchainBeamEmbedding transform to generate embeddings for data
                .apply(LangchainBeamEmbedding.embed(handler))
                .apply(ParDo.of(new DoFn<EmbeddingOutput, Void>() {

                    @ProcessElement
                    public void processElement(@Element EmbeddingOutput out) {
                        System.out.println("Embedding output " + Arrays.toString(out.getEmbedding().vector()));
                    }
                }));

        // run pipeline
        p.run();
    }
}
