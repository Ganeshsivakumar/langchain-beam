package com.langchainbeam.model;

public class EmbeddingModelOptions implements BaseModelOptions {

    private final Class<? extends EmbeddingModelBuilder> builder;

    public EmbeddingModelOptions(Class<? extends EmbeddingModelBuilder> builder) {
        this.builder = builder;
    }

    public Class<? extends EmbeddingModelBuilder> getModelBuilderClass() {
        return builder;
    }
}
