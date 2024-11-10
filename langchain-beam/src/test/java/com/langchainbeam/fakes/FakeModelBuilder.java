package com.langchainbeam.fakes;

import com.langchainbeam.model.LangchainModelBuilder;
import com.langchainbeam.model.LangchainModelOptions;

import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * A fake model builder for bulding {@link FakeChatModel}
 */
public class FakeModelBuilder implements LangchainModelBuilder {
    private FakeModelOptions options;

    @Override
    public void setOptions(LangchainModelOptions modelOptions) {
        if (modelOptions instanceof FakeModelOptions fakeModelOptions) {
            this.options = fakeModelOptions;
        } else {
            throw new IllegalArgumentException("Invalid options type. Expected FakeModelOptions.");
        }
    }

    @Override
    public ChatLanguageModel build() {
        return FakeChatModel.builder()
                .modelName(options.getModelName())
                .apiKey(options.getApiKey())
                .temperature(options.getTemperature())
                .topP(options.getTopP())
                .stop(options.getStop())
                .maxTokens(options.getMaxTokens())
                .maxCompletionTokens(options.getMaxCompletionTokens()).build();
    }

}
