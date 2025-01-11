package com.langchainbeam.model.openai;

import com.langchainbeam.model.EmbeddingModelOptions;

import lombok.Builder;

@SuppressWarnings("FieldMayBeFinal")
public class OpenAiEmbeddingModelOptions extends EmbeddingModelOptions {

    private String apiKey;
    private String modelName;
    private Integer dimensions;

    @Builder
    public OpenAiEmbeddingModelOptions(String modelName, String apikey, Integer dimensions) {
        super(OpenAiEmbeddingModelBuilder.class);
        this.apiKey = apikey;
        this.modelName = modelName;
        this.dimensions = dimensions;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getModelName() {
        return modelName;
    }

    public Integer getDimensions() {
        return dimensions;
    }
}
