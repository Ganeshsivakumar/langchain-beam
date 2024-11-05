package com.langchainbeam.model.openai;

import java.util.List;

import com.langchainbeam.model.LangchainModelOptions;

import lombok.Builder;

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
