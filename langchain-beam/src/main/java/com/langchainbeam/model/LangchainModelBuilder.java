package com.langchainbeam.model;

import dev.langchain4j.model.chat.ChatLanguageModel;

public interface LangchainModelBuilder {

    void setOptions(LangchainModelOptions modelOptions);

    ChatLanguageModel build();
}
