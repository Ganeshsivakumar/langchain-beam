# Langchain-Beam

Langchain-Beam integrates Large Language Models as PTransforms in Apache Beam pipelines using LangChain.
This library lets you use language model capabilities directly in your Beam workflows for data processing and transformations.

## Motivation

Apache Beam is a programming model for creating batch and streaming-based data processing pipelines. It's an abstraction that provides a way to create data processing as Transforms, each transform is like a stage in the pipeline. Langchain-beam library provides transforms to integrate LLMs into beam pipelines.

The goal is to combine the apache beam's abstraction with the capabilities of Large Language Models, such as generation, completion, classification, and reasoning to process the data by leveraging LangChain, which provides a unified interface for connecting with various LLM providers, retrievals, and tools.

![Pipeline Diagram](docs/langchainbeam.png)

## Getting Started

Include **Langchain-Beam** as dependency in `pom.xml`.
Additionally, ensure you have the required Apache Beam dependencies in your project.

```xml
<dependency>
    <groupId>io.github.ganeshsivakumar</groupId>
    <artifactId>langchain-beam</artifactId>
    <version>0.3.0</version>
</dependency>
```

import required modules

```java
import com.langchainbeam.LangchainBeam;
import com.langchainbeam.LangchainModelHandler;

// import the model options class based on the model provider
// that you want to use. Additional providers will be integrated in future releases.
import com.langchainbeam.model.openai.OpenAiModelOptions;
```

## Steps to Use Langchain-Beam

1. **Create Model Options**  
   Define the `modelOptions` based on the model provider you’re using, configuring parameters such as temperature, max tokens, and other relevant settings.
2. **Define the Instruction Prompt**  
   Create an `instructionPrompt` that will guide the model on how to process each `PCollection` input element.

3. **Apply the LangchainBeam PTransform**  
   Pass the `modelOptions` and `instructionPrompt` to the `LangchainModelHandler`. Use this handler as input to the `LangchainBeam` PTransform, which can then be applied in the pipeline with `LangchainBeam.run(modelHandler)`.

Example:

```java

// Define the instruction prompt for processing the element
String prompt = "Categorize the product review as Positive or Negative and output your response in this JSON format: {review : {input_element}, feedback: {positive or negative}}";

// Create model options with the model and its parameters
OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
        .modelName("gpt-4o-mini")
        .apiKey(OPENAI_API_KEY)
        .build();

// Initialize the LangchainModelHandler with model options and prompt
LangchainModelHandler handler = new LangchainModelHandler(modelOptions, prompt);

//create the pipeline
Pipeline p = Pipeline.create();

// Apply transformations in the pipeline
p.apply(TextIO.read().from("/home/ganesh/Downloads/product_reviews.csv")) // load data
        .apply(LangchainBeam.run(handler))  // Run the model handler using LangchainBeam
        .apply(ParDo.of(new DoFn<String, Void>() {
            @ProcessElement
            public void processElement(@Element LangchainBeamOutput out) {
                System.out.println("Model Output: " + out.getOutput());  // Print model output
            }
        }));

p.run();  // Execute the pipeline


```
