package com.langchainbeam.model.ollama;

import com.langchainbeam.model.EmbeddingModelOptions;
import lombok.Builder;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Configuration options for Ollama embedding model.
 * Extends {@link EmbeddingModelOptions} to provide specific settings
 * for Ollama embedding model configuration.
 */
public class OllamaEmbeddingModelOptions extends EmbeddingModelOptions {

    private static final String DEFAULT_OLLAMA_URL = "http://localhost:11434";

    private final String modelName;
    private final @Nullable String baseUrl;

    /**
     * Constructs a new instance of OllamaEmbeddingModelOptions.
     *
     * @param modelName the name of the Ollama model to use
     * @param baseUrl   the base URL for the Ollama API endpoint. If null, defaults to {@value DEFAULT_OLLAMA_URL}
     */
    @Builder
    public OllamaEmbeddingModelOptions(
            String modelName,
            @Nullable String baseUrl
    ) {
        super(OllamaEmbeddingModelBuilder.class);
        this.modelName = modelName;
        this.baseUrl = baseUrl;
    }


    /**
     * Returns the name of the Ollama model.
     *
     * @return the model name
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Returns the base URL for the Ollama API endpoint.
     * If no base URL was specified during construction,
     * returns the default URL ({@value DEFAULT_OLLAMA_URL}).
     *
     * @return the base URL for the Ollama API
     */
    public String getBaseUrl() {
        if (this.baseUrl == null) {
            return DEFAULT_OLLAMA_URL;
        }
        return this.baseUrl;
    }
}
