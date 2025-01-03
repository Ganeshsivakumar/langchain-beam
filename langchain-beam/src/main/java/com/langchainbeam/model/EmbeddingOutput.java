package com.langchainbeam.model;

import java.io.Serializable;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.coders.SerializableCoder;

import dev.langchain4j.data.embedding.Embedding;
import lombok.Builder;

@DefaultCoder(SerializableCoder.class)
public class EmbeddingOutput implements Serializable {

    private final Embedding embedding;
    private final String inputElement;

    @Builder
    public EmbeddingOutput(Embedding embedding, String inputElement) {
        this.embedding = embedding;
        this.inputElement = inputElement;
    }

    public Embedding getEmbedding() {
        return embedding;
    }

    public String getInputElement() {
        return inputElement;
    }
}
