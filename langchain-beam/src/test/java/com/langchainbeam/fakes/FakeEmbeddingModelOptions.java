package com.langchainbeam.fakes;

import com.langchainbeam.model.EmbeddingModelOptions;

import lombok.Builder;

@SuppressWarnings("FieldMayBeFinal")
public class FakeEmbeddingModelOptions extends EmbeddingModelOptions {

    private String apiKey;
    private String modelName;
    private Integer dimensions;

    @Builder
    public FakeEmbeddingModelOptions(String apiKey, String modelName, Integer dimensions) {
        super(FakeEmbeddingModelBuilder.class);
        this.apiKey = apiKey;
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
