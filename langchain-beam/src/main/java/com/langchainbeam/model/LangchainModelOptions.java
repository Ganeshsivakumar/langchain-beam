package com.langchainbeam.model;

import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * Options for creating the {@link ChatLanguageModel}
 */
public class LangchainModelOptions implements BaseModelOptions {

    public Class<? extends LangchainModelBuilder> getModelBuilderClass() {
        return null;
    }
}
