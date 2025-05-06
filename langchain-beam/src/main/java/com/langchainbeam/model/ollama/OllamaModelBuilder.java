package com.langchainbeam.model.ollama;

import com.langchainbeam.model.LangchainModelBuilder;
import com.langchainbeam.model.LangchainModelOptions;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

/**
 * Internal builder for constructing an {@link OllamaChatModel} using
 * {@link OllamaModelOptions}.
 * <p>
 * This class is responsible for setting model options and building an
 * Ollama-based model
 * </p>
 */
public class OllamaModelBuilder implements LangchainModelBuilder {
    private OllamaModelOptions options;

    /**
     * Returns the {@link OllamaModelOptions} used by this builder.
     *
     * @return the model options
     */
    public OllamaModelOptions getOptions() {
        return this.options;
    }

    /**
     * Sets the {@link OllamaModelOptions} for the builder
     *
     * @param modelOptions the model options to set
     * @throws IllegalArgumentException if the options are not of type
     *                                  {@link OllamaModelOptions}
     */
    @Override
    public void setOptions(LangchainModelOptions modelOptions) {
        if (modelOptions instanceof OllamaModelOptions ollamaModelOptions) {
            this.options = ollamaModelOptions;
        } else {
            throw new IllegalArgumentException("Invalid option type. Expected OllamaModelOptions.");
        }
    }

    /**
     * Builds and returns an {@link OllamaChatModel} based on the configured
     * options.
     *
     * @return a configured OllamaChatModel
     */
    @Override
    public ChatLanguageModel build() {
        return OllamaChatModel.builder()
                .modelName(options.getModelName())
                .temperature(options.getTemperature())
                .topP(options.getTopP())
                .topK(options.getTopK())
                .timeout(options.getTimeout())
                .baseUrl(options.getBaseUrl())
                .build();
    }

}
