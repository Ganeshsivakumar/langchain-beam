package com.langchainbeam.model;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.coders.SerializableCoder;

/**
 * 
 * A Serializable vector embedding of a text
 * to represent Embeddings in apache beam pipeline.
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * float[] vector = new float[] { 1.0f, 2.0f, 3.0f };
 * BeamEmbedding embedding = new BeamEmbedding(vector);
 * System.out.println("Dimension: " + embedding.dimention());
 * System.out.println("Vector: " + Arrays.toString(embedding.vector()));
 * </pre>
 */
@DefaultCoder(SerializableCoder.class)
public class BeamEmbedding implements Serializable {

    private final float[] vector;

    /**
     * Constructs a new {@link BeamEmbedding} instance with the specified embedding
     * vector.
     * 
     * @param vector A {@code float[]} array representing the embedding vector.
     * @throws IllegalArgumentException If the provided vector is null.
     */
    public BeamEmbedding(float[] vector) {
        this.vector = vector;
    }

    /**
     * Returns the embedding vector.
     * 
     * @return A {@code float[]} array representing the embedding vector.
     */
    public float[] vector() {
        return vector;
    }

    /**
     * Returns the dimensionality (length) of the embedding vector.
     * This method is useful for understanding the size of the vector, which may
     * vary
     * depending on the specific use case or machine learning model.
     * 
     * @return The dimention of the embedding vector.
     */
    public int dimention() {
        return vector.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BeamEmbedding that = (BeamEmbedding) o;
        return Arrays.equals(vector, that.vector);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vector);
    }

}
