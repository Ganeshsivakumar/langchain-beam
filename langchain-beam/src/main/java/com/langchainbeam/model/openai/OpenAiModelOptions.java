package com.langchainbeam.model.openai;

import java.util.List;

import com.langchainbeam.model.LangchainModelOptions;

import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.Builder;

/**
 * Configuration options for building an OpenAI model within the LangChain
 * framework.
 * <p>
 * This class extends {@link LangchainModelOptions} and provides specific
 * configuration options
 * required for interacting with OpenAI models, such as model name, API key,
 * temperature, max tokens
 * to create and configure an {@link OpenAiChatModel}.
 * </p>
 */
public class OpenAiModelOptions extends LangchainModelOptions {
    private String modelName;
    private String apiKey;
    private Double temperature;
    private Double topP;
    private List<String> stop;
    private Integer maxTokens;
    private Integer maxCompletionTokens;
    private Boolean logRequests;
    private Boolean logResponses;

    @Builder
    public OpenAiModelOptions(String apiKey, String modelName, Double temperature, Double topP, List<String> stop,
            Integer maxTokens, Integer maxCompletionTokens,
            Boolean logRequests, Boolean logResponses) {
        this.modelName = modelName;
        this.apiKey = apiKey;
        this.temperature = temperature;
        this.topP = topP;
        this.stop = stop;
        this.maxTokens = maxTokens;
        this.maxCompletionTokens = maxCompletionTokens;
        this.logRequests = logRequests;
        this.logResponses = logResponses;
    }

    /**
     * Returns the builder class used to construct Model.
     * 
     * @return the {@link OpenAiModelBuilder} class
     */
    @Override
    public Class<OpenAiModelBuilder> getModelBuilderClass() {
        return OpenAiModelBuilder.class;
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

    public List<String> getStop() {
        return stop;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public Integer getMaxCompletionTokens() {
        return maxCompletionTokens;
    }

    public Boolean getLogRequests() {
        return logRequests;
    }

    public Boolean getLogResponses() {
        return logResponses;
    }

}
