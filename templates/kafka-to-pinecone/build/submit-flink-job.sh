#!/bin/bash
set -e

# Default Flink version 
FLINK_VERSION="${FLINK_VERSION:-1.18}"

# Default flink master
FLINK_MASTER="${FLINK_MASTER:-localhost:8081}"


# Download location for the JAR
JAR_PATH="/tmp/kafka-to-pinecone.jar"

echo "🔽 Downloading kafka-to-pinecone JAR for Flink $FLINK_VERSION..."


JAR_PATH=$(curl -fsSL https://templates.langbeam.cloud/kafka-to-pinecone.sh | bash -s -- "$FLINK_VERSION")

echo "✅ Downloaded JAR to $JAR_PATH"

echo "🚀 Submitting job using Flink CLI..."

/opt/flink/bin/flink run \
  --jobmanager "$FLINK_MASTER" \
  -c com.templates.langchainbeam.KafkaToPinecone \
  "$JAR_PATH" "$@"

echo "✅ Job successfully submitted to Flink