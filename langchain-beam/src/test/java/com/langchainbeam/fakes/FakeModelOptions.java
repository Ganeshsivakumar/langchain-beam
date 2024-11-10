package com.langchainbeam.fakes;

import java.util.List;

import com.langchainbeam.model.LangchainModelOptions;

import lombok.Builder;

/**
 * Fake model options for @link FakeChatModel}
 */
public class FakeModelOptions extends LangchainModelOptions {
    private String modelName;
    private String apiKey;
    private Double temperature;
    private Double topP;
    private List<String> stop;
    private Integer maxTokens;
    private Integer maxCompletionTokens;

    @Builder
    public FakeModelOptions(String modelName, String apiKey, Double temperature, Double topP,
            List<String> stop, Integer maxTokens, Integer maxCompletionTokens) {
        this.modelName = modelName;
        this.apiKey = apiKey;
        this.temperature = temperature;
        this.topP = topP;
        this.stop = stop;
        this.maxTokens = maxTokens;
        this.maxCompletionTokens = maxCompletionTokens;
    }

    @Override
    public Class<FakeModelBuilder> getModelBuilderClass() {
        return FakeModelBuilder.class;
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
}
