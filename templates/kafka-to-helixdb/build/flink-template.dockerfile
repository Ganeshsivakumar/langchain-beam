# Base image with Java 17
FROM openjdk:17-slim

# Install tools
RUN apt-get update && apt-get install -y curl unzip && rm -rf /var/lib/apt/lists/*

# Download and extract Flink 1.19.2
ENV FLINK_VERSION=1.19.2
ENV FLINK_HOME=/opt/flink
ENV PATH=$FLINK_HOME/bin:$PATH

# Download and extract Flink 1.19.2 into /opt/flink
RUN curl -sSL https://dlcdn.apache.org/flink/flink-1.19.2/flink-1.19.2-bin-scala_2.12.tgz \
  | tar -xz -C /opt/ && mv /opt/flink-1.19.2 $FLINK_HOME

# job submission script
COPY submit-flink-job.sh /usr/local/bin/submit-flink-job.sh
RUN chmod +x /usr/local/bin/submit-flink-job.sh

# Entry point
ENTRYPOINT ["/usr/local/bin/submit-flink-job.sh"]