package com.langchainbeam;

import java.io.Serializable;

import com.langchainbeam.model.LangchainModelOptions;

public class LangchainModelHandler implements Serializable {

    private final LangchainModelOptions options;
    private final String instructionPrompt;

    public LangchainModelHandler(LangchainModelOptions options, String instructionPrompt) {
        this.options = options;
        this.instructionPrompt = instructionPrompt;

    }

    public LangchainModelOptions getOptions() {
        return options;
    }

    public String getPrompt() {
        return instructionPrompt;
    }
}
