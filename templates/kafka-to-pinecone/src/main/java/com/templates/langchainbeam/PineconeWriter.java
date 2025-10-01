package com.templates.langchainbeam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.beam.sdk.transforms.DoFn;

import com.langchainbeam.model.EmbeddingOutput;

import io.pinecone.clients.Index;
import io.pinecone.configs.PineconeConfig;
import io.pinecone.configs.PineconeConnection;

public class PineconeWriter extends DoFn<EmbeddingOutput, Void> {
    private transient PineconeConnection connection;
    private transient Index index;

    private final String pineconeKey;
    private final String hostUrl;
    private final String indexName;
    private final String namespace;

    public PineconeWriter(String pineconeKey, String hostUrl, String indexName, String namespace) {

        this.pineconeKey = pineconeKey;
        this.hostUrl = hostUrl;
        this.indexName = indexName;
        this.namespace = namespace;
    }

    @Setup
    public void setup() {
        PineconeConfig config = new PineconeConfig(pineconeKey, "langbeam");
        config.setHost(hostUrl);
        this.connection = new PineconeConnection(config);
        this.index = new Index(connection, indexName);
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        float[] floatArray = c.element().getEmbedding().vector();
        List<Float> floatList = IntStream.range(0, floatArray.length)
                .mapToObj(i -> floatArray[i])
                .collect(Collectors.toList());

        index.upsert(
                String.valueOf(Math.random()),
                floatList,
                null, null, null,
                namespace);
    }

    @Teardown
    public void teardown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
