package com.langchainbeam.model.openai;

import com.langchainbeam.model.EmbeddingModelBuilder;
import com.langchainbeam.model.EmbeddingModelOptions;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;

public class OpenAiEmbeddingModelBuilder implements EmbeddingModelBuilder {

    private OpenAiEmbeddingModelOptions options;

    @Override
    public void setOptions(EmbeddingModelOptions modelOptions) {
        if (modelOptions instanceof OpenAiEmbeddingModelOptions openAiOptions) {
            this.options = openAiOptions;
        }
    }

    @Override
    public EmbeddingModel build() {
        return OpenAiEmbeddingModel.builder().apiKey(options.getApiKey()).modelName(options.getModelName())
                .dimensions(options.getDimensions()).build();
    }

}
