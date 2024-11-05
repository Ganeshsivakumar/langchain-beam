package com.langchainbeam.model.openai;

import com.langchainbeam.model.LangchainModelBuilder;
import com.langchainbeam.model.LangchainModelOptions;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class OpenAiModelBuilder implements LangchainModelBuilder {
    private OpenAiModelOptions options;

    public OpenAiModelOptions getOptions() {
        return options;
    }

    @Override
    public void setOptions(LangchainModelOptions modelOptions) {
        if (modelOptions instanceof OpenAiModelOptions openAiModelOptions) {
            this.options = openAiModelOptions;
        } else {
            throw new IllegalArgumentException("Invalid options type. Expected OpenAiModelOptions.");
        }
    }

    @Override
    public OpenAiChatModel build() {
        return OpenAiChatModel.builder()
                .modelName(options.getModelName())
                .apiKey(options.getApiKey())
                .temperature(options.getTemperature())
                .topP(options.getTopP())
                .stop(options.getStop())
                .maxTokens(options.getMaxTokens())
                .maxCompletionTokens(options.getMaxCompletionTokens())
                .logRequests(options.getLogRequests())
                .logResponses(options.getLogResponses())
                .build();
    }

}
