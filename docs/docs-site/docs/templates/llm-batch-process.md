# LLM batch processor

Llm Batch Processor is a pre-built Apache Beam pipeline that lets you process a batch of text inputs using an LLM (OpenAI models) and save the results to a GCS path. You provide an instruction prompt that tells the model how to process the input data—basically, what to do with it. The pipeline uses the model to transform the data and writes the final output to a GCS file.


## What It Does 🛠️

This template:

- Reads input text data from a file (e.g., in Google Cloud Storage).
- Applies an LLM transformation via prompt using the [LangchainBeam](https://github.com/Ganeshsivakumar/langchain-beam) library.
- Writes the formatted output to a specified location in `.txt` format.



## Use Cases 📦

- Information extraction
- Text summarization  
- Classification    
- Structured Output for Downstream Use


## Template Parameters ⚙️

| Parameter           | Description                                                                                  |
|---------------------|----------------------------------------------------------------------------------------------|
| `inputDataFile`     | Path to the input text file (e.g., `gs://your-bucket/input.txt`)                             |
| `llmOutputFile`     | Output file prefix (e.g., `gs://your-bucket/output/llm-results`)                             |
| `modelName`         | Name of the LLM model to use (e.g., `gpt-3.5-turbo`, `gpt-4o-mini`)                          |
| `apiKey`            | Your OpenAI API key (e.g., `sk-proj-BX_6MwMEV5_wont_share_my_key_tho`)                       |
| `prompt`            | Instruction prompt that guides the model on how to process the input data                    |



## How to Run 🚀

You can deploy this template using the Beam runner of your choice.  


### 1. Google Cloud Dataflow

This pipeline is built and packaged as a [Dataflow Flex Template](https://cloud.google.com/dataflow/docs/guides/templates/using-flex-templates), and the template file (`gs://langbeam-cloud/templates/llm-batch-process.json`) is publicly accessible. This means you can run it directly in your own GCP project by using the `gcloud` CLI with appropriate parameters.

The pipeline source code is fully [open source on GitHub](https://github.com/Ganeshsivakumar/langchain-beam). You're free to fork the repository, customize the pipeline, rebuild it as a Flex Template, and deploy it using your own GCP infrastructure.

#### Run Template:

```bash
gcloud dataflow flex-template run "llm-batch-process" \
  --template-file-gcs-location="gs://langbeam-cloud/templates/llm-batch-process.json" \
  --region="us-east1" \
  --project="ptransformers" \
  --network="default" \
  --subnetwork="https://www.googleapis.com/compute/v1/projects/project-id/regions/us-east1/subnetworks/default" \
  --staging-location="gs://your-stage-bucket/stage/" \
  --temp-location="gs://your-stage-bucket/temp/" \
  --parameters="inputDataFile=gs://pt-public-bucket/inputfile/product_reviews.csv,llmOutputFile=gs://your-bucket/outputfile/output,modelName=gpt-4o-mini,apiKey=openaikey,prompt=Categorize the product review as Positive or Negative"

```

If you'd like to host the template in your own GCP project:

- **Fork the Langchain-Beam repo** and clone it locally.  
  You’ll find the `llm-batch-process` template under the `templates/` directory.

- **Build the template** using Maven:

  ```bash
  mvn clean package -Prunner-dataflow
  ```

- **Use the `gcloud` CLI** to run Dataflow Flex Template Build command.  
  This will:
  - Push your pipeline JAR as a container image to Artifact Registry
  - Generate a `template.json` file in your GCS bucket, which acts as a pointer to your pipeline image and contains configuration metadata

#### Build Command: 

```bash
gcloud dataflow flex-template build gs://langbeam-cloud/templates/llm-batch-process.json \
  --image-gcr-path=us-docker.pkg.dev/your-projectid/your-repo/llm-batch-process:latest \
  --jar=/your-folder-path/langchain-beam/lbtemplate/langchain-beam/templates/llm-batch-process/target/llm-batch-process-dataflow.jar \
  --env=FLEX_TEMPLATE_JAVA_MAIN_CLASS=com.templates.langchainbeam.LlmBatchTextProcessor \
  --flex-template-base-image=JAVA17 \
  --metadata-file=/your-folder-path/langchain-beam/templates/llm-batch-process/src/main/metadata/metadata.json \
  --sdk-language=JAVA
```

Now the template is buit and hosted your GCS path. you can pass GCS template file to run command to execute the template on dataflow 

### 2. Apache Flink

If you have an Apache Flink standalone cluster, you can submit the template as a job using the prebuilt Docker image.

```bash
docker run --rm \
  -e FLINK_MASTER=host.docker.internal:8081 \
  -e FLINK_VERSION=1.18 \
  us-docker.pkg.dev/ptransformers/langbeam-cloud/templates/flink/llm-batch-process:latest \
  --runner=FlinkRunner \
  --inputDataFile=gs://pt-public-bucket/inputfile/product_reviews.csv \
  --llmOutputFile=gs://your-bucket/outputfile/output \
  --modelName=gpt-4o \
  --apiKey=your_openai_key \
  --prompt="Categorize the product review as Positive or Negative"
```

#### How It Works

The pipeline is built and packaged as a JAR. Since Apache Beam’s Flink Runner must be compatible with your Flink version, there are multiple JARs available—each tailored to a specific Beam and Flink version combination.
Refer to the Flink version [compatibility matrix](https://beam.apache.org/documentation/runners/flink/#flink-version-compatibility) to choose the correct version. 


- The container downloads the appropriate .jar file from GCS based on ***FLINK_VERSION*** (your flink cluster version) with right beam and runner depedencies

- It uses the Flink CLI (flink run) to submit the job to the Flink cluster (as specified by `FLINK_MASTER`)

- All dependencies—including Java 17 and the Flink CLI—are preinstalled in the image, so you don’t need to set up anything else.


### 3. Locally

You can also run the template locally using the Apache Beam DirectRunner. This is useful for testing, debugging, or running small jobs without setting up a full cluster.

Prerequisites:
- JDK 17
- Maven

#### Run with Maven ####

Clone the repository:
```bash
git clone https://github.com/Ganeshsivakumar/langchain-beam.git
cd langchain-beam/templates/llm-batch-process
```

Build the template:
```bash
mvn clean package -Prunner-direct
```

Run with direct runner:
```bash
java -cp target/llm-batch-process-direct.jar \
  com.templates.langchainbeam.LlmBatchTextProcessor \
  --runner=DirectRunner \
  --inputDataFile=gs://pt-public-bucket/inputfile/product_reviews.csv \
  --llmOutputFile=/tmp/output \
  --modelName=gpt-4o \
  --apiKey=your_openai_key \
  --prompt="Categorize the product review as Positive or Negative"

```
:::tip

 You can use a local file (e.g., --inputDataFile=/path/to/input.txt) instead of GCS during local runs.

:::

### 4. Apache Spark

Spark support will be added soon via [Beam's Spark runner](https://beam.apache.org/documentation/runners/spark/).



## Template Support 🧰

This template is fully open source and part of the [Langchain-Beam](https://github.com/Ganeshsivakumar/langchain-beam) project.

We built it to make it easier for developers and data teams to bring the power of LLMs into their data pipelines — whether you're using Flink, Dataflow, or running jobs locally.

Feel free to:

- 🔧 Fork and customize the template to suit your specific use case and Deploy it using your own infrastructure or extend it to support new LLMs or output formats.

- 🌱 Submit a PR if you'd like to contribute improvements, new features, or even entirely new templates.

- 🐞 Create an issue if you run into any problems running the template on Flink, Dataflow, or locally — we're happy to help troubleshoot!



✨ Want zero setup? A managed cloud platform is in the works. Join the [waitlist](https://app.youform.com/forms/9hbdmkly) or book a [quick call](https://cal.com/ganesh-sivakumar/30min) to chat about custom templates executed on LangBeam cloud platform. 
