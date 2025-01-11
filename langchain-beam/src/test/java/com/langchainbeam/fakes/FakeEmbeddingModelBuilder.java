package com.langchainbeam.fakes;

import com.langchainbeam.model.EmbeddingModelBuilder;
import com.langchainbeam.model.EmbeddingModelOptions;

import dev.langchain4j.model.embedding.EmbeddingModel;

public class FakeEmbeddingModelBuilder implements EmbeddingModelBuilder {

    @SuppressWarnings("unused")
    private FakeEmbeddingModelOptions options;

    @Override
    public void setOptions(EmbeddingModelOptions modelOptions) {
        if (modelOptions instanceof FakeEmbeddingModelOptions openAiOptions) {
            this.options = openAiOptions;
        } else {
            throw new IllegalArgumentException("Invalid model options, Expected FakeEmbeddingModelOptions");
        }
    }

    @Override
    public EmbeddingModel build() {
        return FakeEmbeddingModel.builder()
                .apiKey("APIKEY")
                .modelName("Fake-Model")
                .build();
    }

}
