package com.langchainbeam.model;

import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * Options for creating the {@link ChatLanguageModel}
 */
public class LangchainModelOptions implements BaseModelOptions {

    private final Class<? extends LangchainModelBuilder> builder;

    public LangchainModelOptions(Class<? extends LangchainModelBuilder> builder) {
        this.builder = builder;
    }

    public Class<? extends LangchainModelBuilder> getModelBuilderClass() {
        return builder;
    }
}
