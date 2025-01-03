package com.langchainbeam.model;

import dev.langchain4j.model.embedding.EmbeddingModel;

public interface EmbeddingModelBuilder {

    void setOptions(EmbeddingModelOptions options);

    EmbeddingModel build();

}
