package com.langchainbeam;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import com.langchainbeam.model.EmbeddingOutput;

public class LangchainBeamEmbedding extends PTransform<PCollection<String>, PCollection<EmbeddingOutput>> {

    private final EmbeddingModelHandler handler;

    private LangchainBeamEmbedding(EmbeddingModelHandler modelHandler) {
        this.handler = modelHandler;
    }

    @Override
    public PCollection<EmbeddingOutput> expand(PCollection<String> input) {
        return input.apply("Embedding transform", ParDo.of(new EmbeddingDoFn(handler)));
    }

    public static LangchainBeamEmbedding embed(EmbeddingModelHandler modelHandler) {
        return new LangchainBeamEmbedding(modelHandler);
    }

}
