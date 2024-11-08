package com.langchainbeam.model.openai;

import com.langchainbeam.model.LangchainModelBuilder;
import com.langchainbeam.model.LangchainModelOptions;

import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 * Internal builder for constructing an {@link OpenAiChatModel} using
 * {@link OpenAiModelOptions}.
 * <p>
 * This class is responsible for setting model options and building an
 * OpenAI-based model
 * </p>
 */
public class OpenAiModelBuilder implements LangchainModelBuilder {
    private OpenAiModelOptions options;

    /**
     * Returns the {@link OpenAiModelOptions} used by this builder.
     * 
     * @return the model options
     */
    public OpenAiModelOptions getOptions() {
        return options;
    }

    /**
     * Sets the {@link OpenAiModelOptions} for the builder
     * 
     * @param modelOptions the model options to set
     * @throws IllegalArgumentException if the options are not of type
     *                                  {@link OpenAiModelOptions}
     */
    @Override
    public void setOptions(LangchainModelOptions modelOptions) {
        if (modelOptions instanceof OpenAiModelOptions openAiModelOptions) {
            this.options = openAiModelOptions;
        } else {
            throw new IllegalArgumentException("Invalid options type. Expected OpenAiModelOptions.");
        }
    }

    /**
     * Builds and returns an {@link OpenAiChatModel} based on the configured
     * options.
     * 
     * @return a configured OpenAiChatModel
     */
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
