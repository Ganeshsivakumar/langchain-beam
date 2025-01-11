package com.langchainbeam.model;

import dev.langchain4j.model.embedding.EmbeddingModel;

/**
 * Options for creating the {@link EmbeddingModel}
 */
public class EmbeddingModelOptions implements BaseModelOptions {

    private final Class<? extends EmbeddingModelBuilder> builder;

    /**
     * Creates the instance of `EmbeddingModelOptions`
     * 
     * @param builder to build {@link EmbeddingModel}
     */
    public EmbeddingModelOptions(Class<? extends EmbeddingModelBuilder> builder) {
        this.builder = builder;
    }

    /**
     * Returns the builder class that can build the {@link EmbeddingModel}
     * 
     * @return builder class
     */
    public Class<? extends EmbeddingModelBuilder> getModelBuilderClass() {
        return builder;
    }
}
