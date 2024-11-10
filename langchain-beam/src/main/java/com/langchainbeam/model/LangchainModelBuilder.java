package com.langchainbeam.model;

import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * Interface for creating {@link ChatLanguageModel} using
 * compatible {@link LangchainModelOptions} of the model provider
 */
public interface LangchainModelBuilder {

    /**
     * sets the options for creating {@link ChatLanguageModel}
     * 
     * @param modelOptions
     */
    void setOptions(LangchainModelOptions modelOptions);

    /**
     * Builds the {@link ChatLanguageModel} using the provided
     * {@link LangchainModelOptions}
     * 
     * @return a {@link ChatLanguageModel} instance.
     */
    ChatLanguageModel build();
}
