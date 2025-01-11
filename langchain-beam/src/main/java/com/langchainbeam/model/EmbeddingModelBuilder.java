package com.langchainbeam.model;

import dev.langchain4j.model.embedding.EmbeddingModel;

/**
 * Interface for creating {@link EmbeddingModel} using
 * compatible {@link EmbeddingModelOptions} of the model provider
 */
public interface EmbeddingModelBuilder {

    /**
     * sets the options for creating {@link EmbeddingModel}
     * 
     * @param modelOptions
     */
    void setOptions(EmbeddingModelOptions options);

    /**
     * Builds the {@link EmbeddingModel} using the provided
     * {@link EmbeddingModelOptions}
     * 
     * @return a {@link EmbeddingModel} instance.
     */
    EmbeddingModel build();

}
