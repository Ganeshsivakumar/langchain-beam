package com.templates.langchainbeam;

import org.apache.beam.sdk.options.PipelineOptions;

public interface KafkaToPineconePipelineOptions extends PipelineOptions {

    /** Kafka brokers, comma-separated. */
    String getBrokers();

    void setBrokers(String brokers);

    /** Kafka topic to consume from. */
    String getTopic();

    void setTopic(String topic);

    /** Embedding model name. */
    String getEmbeddingModel();

    void setEmbeddingModel(String model);

    /** OpenAI API key. */
    String getOpenaiApiKey();

    void setOpenaiApiKey(String key);

    /** Pinecone API key. */
    String getPineconeApiKey();

    void setPineconeApiKey(String key);

    /** Pinecone index name. */
    String getPineconeIndex();

    void setPineconeIndex(String index);

    /** Pinecone namespace. */
    String getPineconeNamespace();

    void setPineconeNamespace(String namespace);

    String getPineconeApiUrl();

    void setPineconeApiUrl(String url);
}

// 3.8.0