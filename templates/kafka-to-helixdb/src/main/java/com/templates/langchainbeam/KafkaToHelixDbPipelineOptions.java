package com.templates.langchainbeam;

import org.apache.beam.sdk.options.PipelineOptions;

public interface KafkaToHelixDbPipelineOptions extends PipelineOptions {

    /** Kafka brokers, comma-separated. */
    String getBrokers();

    void setBrokers(String brokers);

    /** Kafka topic to consume from. */
    String getTopic();

    void setTopic(String topic);

    /**
     * "Kafka username (API Key for Confluent Cloud)"
     * 
     * This is used for authentication with Kafka brokers.
     * If not set, authentication will not be performed.
     * 
     * Note: Ensure that the Kafka brokers are configured to accept the provided
     * username and password.
     */
    String getKafkaUsername();

    void setKafkaUsername(String username);

    /**
     * "Kafka password (API Secret for Confluent Cloud)"
     */
    String getKafkaPassword();

    void setKafkaPassword(String password);

    /**
     * Optional: Kafka security protocol.
     * 
     * This is used to specify the security protocol for Kafka connections.
     * Common values include PLAINTEXT, SSL, SASL_PLAINTEXT, and SASL_SSL.
     * 
     * Note: Ensure that the Kafka brokers are configured to support the specified
     * security protocol.
     */
    String getKafkaSecurityProtocol();

    void setKafkaSecurityProtocol(String securityProtocol);

    /**
     * Optional: Kafka SASL mechanism (e.g., PLAIN, SCRAM-SHA-256, SCRAM-SHA-512,
     * GSSAPI).
     * This is used for authentication with Kafka brokers.
     * 
     * Note: Ensure that the Kafka brokers support the specified SASL mechanism.
     */
    String getKafkaSaslMechanism();

    void setKafkaSaslMechanism(String saslMechanism);

    /** Embedding model name. */
    String getEmbeddingModel();

    void setEmbeddingModel(String model);

    /** OpenAI API key. */
    String getOpenaiApiKey();

    void setOpenaiApiKey(String key);

    /** Helix DB base url */
    String getHelixUrl();

    void setHelixUrl(String url);

    /** Helix db ingestion endpoint */
    String getHelixEndpoint();

    void setHelixEndpoint(String endpoint);

}
