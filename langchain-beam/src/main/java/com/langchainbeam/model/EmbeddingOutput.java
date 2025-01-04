package com.langchainbeam.model;

import java.io.Serializable;
import java.util.Objects;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.coders.SerializableCoder;

import lombok.Builder;

@DefaultCoder(SerializableCoder.class)
public class EmbeddingOutput implements Serializable {

    private final BeamEmbedding embedding;
    private final String inputElement;

    @Builder
    public EmbeddingOutput(BeamEmbedding embedding, String inputElement) {
        this.embedding = embedding;
        this.inputElement = inputElement;
    }

    public BeamEmbedding getEmbedding() {
        return embedding;
    }

    public String getInputElement() {
        return inputElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EmbeddingOutput that = (EmbeddingOutput) o;
        return Objects.equals(embedding, that.embedding) &&
                Objects.equals(inputElement, that.inputElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(embedding, inputElement);
    }
}
