package com.templates.langchainbeam;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.kafka.common.security.scram.ScramLoginModule;

import com.sun.security.auth.module.Krb5LoginModule;

public class Config {

    public static Map<String, Object> buildKafkaConfig(KafkaToPineconePipelineOptions options) {
        Map<String, Object> kafkaConfig = new HashMap<>();

        // Consumer configuration
        kafkaConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "langchain-beam-consumer");
        kafkaConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        // Connection settings
        kafkaConfig.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "45000");
        kafkaConfig.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "60000");
        kafkaConfig.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "300000");

        // Add authentication if credentials are provided
        if (options.getKafkaUsername() != null && options.getKafkaPassword() != null) {
            kafkaConfig.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, options.getKafkaSecurityProtocol());
            kafkaConfig.put(SaslConfigs.SASL_MECHANISM, options.getKafkaSaslMechanism());

            // Dynamically build JAAS config based on SASL mechanism
            String jaasConfig = buildJaasConfig(
                    options.getKafkaSaslMechanism(),
                    options.getKafkaUsername(),
                    options.getKafkaPassword());
            kafkaConfig.put(SaslConfigs.SASL_JAAS_CONFIG, jaasConfig);
        }

        return kafkaConfig;
    }

    private static String buildJaasConfig(String saslMechanism, String username, String password) {
        String loginModuleClass = getSaslLoginModuleClass(saslMechanism);
        return String.format("%s required username=\"%s\" password=\"%s\";",
                loginModuleClass, username, password);
    }

    private static String getSaslLoginModuleClass(String saslMechanism) {
        return switch (saslMechanism.toUpperCase()) {
            case "PLAIN" -> PlainLoginModule.class.getName();
            case "SCRAM-SHA-256", "SCRAM-SHA-512" -> ScramLoginModule.class.getName();
            case "GSSAPI" -> Krb5LoginModule.class.getName();
            default -> PlainLoginModule.class.getName();
        };
    }
}