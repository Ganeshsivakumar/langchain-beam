package com.templates.langchainbeam;

import org.apache.beam.sdk.transforms.DoFn;

import com.langchainbeam.model.EmbeddingOutput;

public class HelixDbWriter extends DoFn<EmbeddingOutput, Void> {

    private final String endpoint;
    private final String baseUrl;

    private transient HelixDbClient client;

    public HelixDbWriter(String endpoint, String baseUrl) {

        this.endpoint = endpoint;
        this.baseUrl = baseUrl;
    }

    @Setup
    public void setup() {

        this.client = HelixDbClient.initialize(endpoint, baseUrl);
    }

    @ProcessElement
    public void processElement(ProcessContext c) {

        EmbeddingOutput element = c.element();

        try {
            client.writeData(element.getEmbedding().vector(), element.getInputElement());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
