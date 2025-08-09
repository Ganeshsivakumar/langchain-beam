#!/bin/bash
set -e

# Default Flink version 
FLINK_VERSION="${FLINK_VERSION:-1.18}"

# Base public GCS URL for your template jars
BASE_GCS_URL="https://storage.googleapis.com/langbeam-cloud/build/flink-templates/kafka-to-pinecone"

# Construct JAR URL based on version
JAR_URL="${JAR_URL:-$BASE_GCS_URL/kafka-to-pinecone-flink-${FLINK_VERSION}.jar}"

# Download location for the JAR
JAR_PATH="/tmp/kafka-to-pinecone.jar"


echo "🔽 Downloading JAR for Flink $FLINK_VERSION from:"
echo "$JAR_URL"
curl -sSL -o "$JAR_PATH" "$JAR_URL"
echo "✅ Downloaded to $JAR_PATH"


# Submit job using Flink CLI 

FLINK_MASTER="${FLINK_MASTER:-localhost:8081}"

echo "🚀 Submitting job using Flink CLI..."

/opt/flink/bin/flink run \
  --jobmanager "$FLINK_MASTER" \
  -c com.templates.langchainbeam.LlmBatchTextProcessor \
  "$JAR_PATH" "$@"

echo "✅ Job successfully submitted to Flink