package com.langchainbeam.fakes;

import java.util.ArrayList;
import java.util.List;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import lombok.Builder;

/**
 * Fake Embedding model
 */
@SuppressWarnings("FieldMayBeFinal")
public class FakeEmbeddingModel implements EmbeddingModel {

    private String apiKey;
    private String modelName;
    private Integer dimensions;

    @Builder
    public FakeEmbeddingModel(String apiKey, String modelName, Integer dimensions) {
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

    @Override
    public Response<List<Embedding>> embedAll(List<TextSegment> textSegments) {
        float[] vector = { 0.071781114f, 0.24112974f, -0.26148483f, };

        Embedding embedding = new Embedding(vector);
        List<Embedding> dummyEmbeddings = new ArrayList<>();
        dummyEmbeddings.add(embedding);

        return Response.from(dummyEmbeddings);
    }

}
