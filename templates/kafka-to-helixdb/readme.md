There are 2 simple steps for running the kafka to helixdb template

## Step 1: Install flink and start flink cluster

download flink 1.19

```sh
wget https://dlcdn.apache.org/flink/flink-1.19.3/flink-1.19.3-bin-scala_2.12.tgz
```

extract files
```sh
tar -xzf flink-1.19.3-bin-scala_2.12.tgz
```

start the flink cluster

```sh
cd flink-1.19.3-bin-scala_2.12

# start cluster using script
./bin/start-cluster.sh
```
once you start the flink cluster, verify that the Flink Web UI is running at [http://localhost:8081/](http://localhost:8081)

## Setp 2: Run the template on cluster

now that we have flink cluster running locally we can submit the template as a job to the cluster for execution.
Run this docker command and the prebuilt image will automatically do the steps and start the pipeline on flink. 


```sh
docker run --rm \
  -e FLINK_MASTER=host.docker.internal:8081 \
  -e FLINK_VERSION=1.19 \
  us-docker.pkg.dev/ptransformers/langbeam-cloud-stage/templates/flink/kafka-to-helixdb:latest \
  --runner=FlinkRunner \
  --brokers=broker_1:9092 \
  --topic=topic \
  --kafkaUsername=username \
  --kafkaPassword=password \
  --kafkaSecurityProtocol=SASL_SSL \
  --kafkaSaslMechanism=PLAIN \
  --embeddingModel=text-embedding-ada-002 \
  --openaiApiKey=key \
  --helixEndpoint=endpoint \
  --helixUrl=https://localhost:6969
```

note: replace the pipeline options

Once pipeline is running, start producing messages to the kafka topic and the pipeline will read it, generate embeddings and write it to helixdb endpoint. At present the pipeline the supports writing the content and its embeddings into the endpoint So, make sure that your helix query(endpoint) that you'll be passing in pipeline options doesn't take any additional properties or metadata. Also the name the parameters as `vector` and `content` with its data types.

Example ingestion query: 

```
QUERY InsertVector (vector: [F64], content: String) =>
    
    document <- AddV<Document>(vector, { content: content })
    RETURN document
```