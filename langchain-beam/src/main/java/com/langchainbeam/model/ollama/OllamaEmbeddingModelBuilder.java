package com.langchainbeam.model.ollama;

import com.langchainbeam.model.EmbeddingModelBuilder;
import com.langchainbeam.model.EmbeddingModelOptions;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;

/**
 * Internal builder for constructing an {@link OllamaEmbeddingModel} using
 * {@link OllamaEmbeddingModelOptions}.
 * <p>
 * This class is responsible for setting model options and building an
 * Ollama embedding model
 * </p>
 */
public class OllamaEmbeddingModelBuilder implements EmbeddingModelBuilder {
    private OllamaEmbeddingModelOptions options;

    /**
     * Sets the {@link OllamaEmbeddingModelOptions} for the builder
     *
     * @param modelOptions the model options to set
     * @throws IllegalArgumentException if the options are not of type
     *                                  {@link OllamaEmbeddingModelOptions}
     */
    @Override
    public void setOptions(EmbeddingModelOptions modelOptions) {
        if (modelOptions instanceof OllamaEmbeddingModelOptions ollamaOptions) {
            this.options = ollamaOptions;
        } else {
            throw new IllegalArgumentException("Invalid options type. Expected OllamaEmbeddingModelOptions.");
        }
    }

    /**
     * Builds and returns an {@link OllamaEmbeddingModel} based on the configured
     * options.
     *
     * @return a configured OllamaEmbeddingModel
     */
    @Override
    public EmbeddingModel build() {
        return OllamaEmbeddingModel.builder()
                .modelName(options.getModelName())
                .baseUrl(options.getBaseUrl())
                .build();
    }

}
