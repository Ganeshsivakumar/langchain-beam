package com.langchainbeam.model.ollama;

import com.langchainbeam.model.LangchainModelOptions;
import lombok.Builder;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;

/**
 * Configuration options for building an Ollama model within LangChain-Beam
 * <p>
 * This class extends {@link LangchainModelOptions} and provides specific
 * configuration options required for interacting with Ollama models, such as
 * model name, base URL, temperature, top-p, top-k, and timeout settings
 * to create and configure an {@link dev.langchain4j.model.ollama.OllamaChatModel}.
 * </p>
 */
public class OllamaModelOptions extends LangchainModelOptions {

    private static final String DEFAULT_OLLAMA_URL = "http://localhost:11434";
    private String modelName;
    private Double temperature;
    private Double topP;
    private int topK;
    private Duration timeout;
    private @Nullable String baseUrl;

    /**
     * Constructs an instance of {@code OllamaModelOptions} with the specified
     * configuration settings.
     *
     * @param modelName   The name of the model to use
     * @param temperature The temperature value for controlling randomness in output
     * @param topP        The top-p value for nucleus sampling
     * @param topK        The top-k value for filtering
     * @param timeout     The duration to wait before timing out the request
     * @param baseUrl     The base URL for the Ollama API endpoint. If null, defaults to {@value DEFAULT_OLLAMA_URL}
     */
    @Builder
    public OllamaModelOptions(
            String modelName,
            Double temperature,
            Double topP,
            int topK,
            Duration timeout,
            @Nullable String baseUrl
    ) {
        super(OllamaModelBuilder.class);
        this.modelName = modelName;
        this.temperature = temperature;
        this.topP = topP;
        this.topK = topK;
        this.timeout = timeout;
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
     * Returns the temperature setting for the model.
     *
     * @return the temperature value
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * Returns the top-p (nucleus sampling) value.
     *
     * @return the top-p value
     */
    public Double getTopP() {
        return topP;
    }

    /**
     * Returns the top-k filtering value.
     *
     * @return the top-k value
     */
    public int getTopK() {
        return topK;
    }

    /**
     * Returns the timeout duration for API requests.
     *
     * @return the timeout duration
     */
    public Duration getTimeout() {
        return timeout;
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
