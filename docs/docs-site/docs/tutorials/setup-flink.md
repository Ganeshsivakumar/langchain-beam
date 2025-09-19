# How to run Apache Flink Locally

[Apache Flink](https://flink.apache.org/) is a powerful stream-processing engine. It can also run [Apache Beam](https://beam.apache.org/) pipelines and templates using the Flink Runner. This short guide walks you through setting up Flink on your laptop in just a few steps.


#### Step 1: Download Flink 1.19

Use `wget` to download the latest Flink 1.19 release (Scala 2.12 build):  

```sh
wget https://dlcdn.apache.org/flink/flink-1.19.3/flink-1.19.3-bin-scala_2.12.tgz
```

#### Step 2: Extract the Archive

Unpack the downloaded archive:

```sh
tar -xzf flink-1.19.3-bin-scala_2.12.tgz
```

#### Step 3: Start the Flink Cluster

Navigate into the extracted directory and start a local cluster:

```sh
cd flink-1.19.3

# Start the cluster
./bin/start-cluster.sh
```

#### Step 4: Verify the Cluster

Once started, open the **Flink Web UI** in your browser:

👉 [http://localhost:8081/](http://localhost:8081/)

If the UI loads, your local Flink cluster is up and running! 🎉  
Next, try running some [LangBeam templates](https://ganeshsivakumar.github.io/langchain-beam/docs/category/templates/) on your Flink cluster 😃


#### Step 5: Stop the Cluster

When you’re done, stop the cluster to free up resources:

```sh
./bin/stop-cluster.sh
```