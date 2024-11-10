# Langchain-Beam

Langchain-Beam integrates Large Language Models as PTransforms in Apache Beam pipelines using LangChain.
This library lets you use language model capabilities directly in your Beam workflows for data processing and transformations.

## Motivation

Apache Beam is a programming model for creating batch and streaming-based data processing pipelines. It's an abstraction that provides a way to create data processing logic in a declarative way, little similar to creating UI components with Flutter or React Native.

The goal is to combine the apache beam's abstraction with the capabilities of Large Language Models, such as generation, completion, classification, and reasoning to process the data by leveraging LangChain, which provides a unified interface for connecting with various LLM providers, retrievals, and tools.

![Pipeline Diagram](docs/langchainbeam.png)

## Usage

1. Start by creating `modelOptions` for the model and provider, specifying parameters like temperature, maxTokens, and other settings.
2. Define the `instructionPrompt` that the model will use to process each input the `PCollection` element.
3. Pass `modelOptions` and `instructionPrompt` to the `LangchainModelHandler`, which serves as input to LangchainBeam PTransform. You can then apply the transformation using `LangchainBeam.run(modelHandler)`.

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
            public void processElement(@Element String output) {
                System.out.println("Model Output: " + output);  // Print model output
            }
        }));

p.run();  // Execute the pipeline


```
