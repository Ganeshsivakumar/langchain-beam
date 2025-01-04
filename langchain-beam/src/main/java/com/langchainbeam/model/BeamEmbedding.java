package com.langchainbeam.model;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.coders.SerializableCoder;

//add equals and hashCode overide

@DefaultCoder(SerializableCoder.class)
public class BeamEmbedding implements Serializable {

    private final float[] vector;

    public BeamEmbedding(float[] vector) {
        this.vector = vector;
    }

    public float[] vector() {
        return vector;
    }

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
