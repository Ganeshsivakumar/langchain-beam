package com.langchainbeam;

import java.io.Serializable;

import com.langchainbeam.model.EmbeddingModelOptions;

public class EmbeddingModelHandler implements Serializable {

    private final EmbeddingModelOptions options;

    public EmbeddingModelHandler(EmbeddingModelOptions modelOptions) {
        this.options = modelOptions;
    }

    public EmbeddingModelOptions getOptions() {
        return options;
    }

}
