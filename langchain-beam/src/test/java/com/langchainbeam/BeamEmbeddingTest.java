package com.langchainbeam;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.langchainbeam.model.BeamEmbedding;

public class BeamEmbeddingTest {
    float[] vectors = {
            0.071781114f,
            0.24112974f,
            -0.26148483f,
            -0.25952762f,
            -0.66075814f,
            -0.42354283f,
            0.38694277f,
            0.050545257f,
            0.15687133f,
            0.08998328f
    };

    @Test
    public void testEmbeddingVector() {

        BeamEmbedding embedding = new BeamEmbedding(vectors);
        assertArrayEquals(vectors, embedding.vector(), 0);

    }

    @Test
    public void testDimensions() {
        BeamEmbedding embedding = new BeamEmbedding(vectors);
        assertEquals(vectors.length, embedding.dimention());
    }

    @Test
    public void testEqualityAndHashCode() {
        BeamEmbedding embedding1 = new BeamEmbedding(vectors);
        BeamEmbedding embedding2 = new BeamEmbedding(vectors);

        assertEquals(embedding1, embedding2);
        assertEquals(embedding1.hashCode(), embedding2.hashCode());

        float[] differentVectors = { 1.0f, 2.0f, 3.0f };
        BeamEmbedding embedding3 = new BeamEmbedding(differentVectors);
        assertNotEquals(embedding1, embedding3);
        assertNotEquals(embedding1.hashCode(), embedding3.hashCode());
    }
}
