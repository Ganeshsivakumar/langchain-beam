package com.langchainbeam.model.anthropic;

import java.time.Duration;

import com.langchainbeam.model.LangchainModelBuilder;
import com.langchainbeam.model.LangchainModelOptions;

import dev.langchain4j.model.anthropic.AnthropicChatModel;
import lombok.Builder;

/**
 * Configuration options for building an Anthropic model within
 * LangChain-Beam
 * <p>
 * This class extends {@link LangchainModelOptions} and provides specific
 * configuration options
 * required for interacting with Anthropic models, such as model name, API key,
 * temperature, max tokens
 * to create and configure an {@link AnthropicChatModel}.
 * </p>
 */
@SuppressWarnings("FieldMayBeFinal")
public class AnthropicModelOptions extends LangchainModelOptions {

    private String modelName;
    private String apiKey;
    private Double temperature;
    private Double topP;
    private int topK;
    private int maxTokens;
    private Duration timeout;

    /**
     * Constructs an instance of {@code AnthropicModelOptions} with the specified
     * configuration settings.
     *
     * @param modelName   The name of the model to be used.
     * @param apiKey      The API key for authenticating requests to the Anthropic
     *                    API.
     * @param temperature The sampling temperature for controlling output
     *                    randomness.
     * @param topP        TopP
     * @param topK        TopK
     * @param maxTokens   The maximum number of tokens for the model output.
     * @param timeout     The timeout for API requests. Default: 60 seconds
     */
    @Builder
    public AnthropicModelOptions(String modelName, String apiKey, Double temperature, Double topP, int topK,
            int maxTokens, Duration timeout) {
        this.modelName = modelName;
        this.apiKey = apiKey;
        this.temperature = temperature;
        this.topP = topP;
        this.topK = topK;
        this.maxTokens = maxTokens;
        this.timeout = timeout;

    }

    /**
     * Returns the builder class used to construct Model.
     * 
     * @return the {@link AnthropicModelBuilder} class
     */
    @Override
    public Class<? extends LangchainModelBuilder> getModelBuilderClass() {
        return AnthropicModelBuilder.class;
    }

    public String getModelName() {
        return modelName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getTopP() {
        return topP;
    }

    public int getTopK() {
        return topK;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public Duration getTimeout() {
        return timeout;
    }
}
