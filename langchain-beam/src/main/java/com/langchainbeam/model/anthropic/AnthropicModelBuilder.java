package com.langchainbeam.model.anthropic;

import com.langchainbeam.model.LangchainModelBuilder;
import com.langchainbeam.model.LangchainModelOptions;

import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * Internal builder for constructing an {@link AnthropicChatModel} using
 * {@link AnthropicModelOptions}.
 * <p>
 * This class is responsible for setting model options and building an
 * Anthropic model
 * </p>
 */
public class AnthropicModelBuilder implements LangchainModelBuilder {
    private AnthropicModelOptions options;

    /**
     * Returns the {@link AnthropicModelOptions} used by this builder.
     * 
     * @return the model options
     */
    public AnthropicModelOptions getOptions() {
        return options;
    }

    /**
     * Sets the {@link AnthropicModelOptions} for the builder
     * 
     * @param modelOptions the model options to set
     * @throws IllegalArgumentException if the options are not of type
     *                                  {@link AnthropicModelOptions}
     */
    @Override
    public void setOptions(LangchainModelOptions modelOptions) {
        if (modelOptions instanceof AnthropicModelOptions openAiModelOptions) {
            this.options = openAiModelOptions;
        } else {
            throw new IllegalArgumentException("Invalid options type. Expected OpenAiModelOptions.");
        }
    }

    /**
     * Builds and returns an {@link AnthropicChatModel} based on the configured
     * options.
     * 
     * @return a configured AnthropicChatModel
     */
    @Override
    public ChatLanguageModel build() {
        return AnthropicChatModel.builder()
                .modelName(options.getModelName())
                .apiKey(options.getApiKey())
                .maxTokens(options.getMaxTokens())
                .temperature(options.getTemperature())
                .topP(options.getTopP())
                .topK(options.getTopK())
                .timeout(options.getTimeout())
                .build();
    }

}
