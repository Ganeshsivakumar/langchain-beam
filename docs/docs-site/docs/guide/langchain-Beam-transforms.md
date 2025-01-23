This section provides a conceptual guide on the transforms offered by Langchain-Beam library.
For explanation about what is transform (PTransform) and other Apache Beam-related concepts, please refer to this [page](docs/guide/apache-beam.md)

## LLM Transform

The LLM transform integrates Large Language Models as PTransform

The `LangchainBeam` is an Apache Beam `PTransform` that uses LangChain’s ChatModel interface,
to integrate LLMs from various providers like OpenAI. The transform accepts a `String` as input, where each element is processed using a specified language model via a `LangchainModelHandler` and yields a `LangchainBeamOutput`, containing the model’s responses for each input element.

#### Input and Output

- **Input**: A `PCollection<String>` representing the input strings to be processed by the language model.
- **Output**: A `PCollection<LangchainBeamOutput>` containing the model’s responses and the corresponding input elements.

### Usage in Pipeline

`LangchainBeam` transform can be instantiated in pipeline using the `run()` method and it takes a
`LangchainModelHandler` as input.

```java
LangchainBeam.run(handler);
```

the `LangchainModelHandler` stores the instruction prompt and model options, which together define how the language model will process each input element. Here’s how these inputs can be provided:

1. **Instruction Prompt:** The instruction prompt is a string that specifies the task or operation the language model should perform on the input data. It provides the necessary context and instructions for the model to generate appropriate outputs. For example, a prompt might instruct the model to “Categorize the product review as Positive or Negative.”

2. **Model Options:** A configuration object that specifies the provide, language model to use and provides other options of the model.

**Example:**

```java
// Define the instruction prompt for LLM
String instructionPrompt = "Categorize the product review as Positive or Negative.";

// Create model options
OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
                .modelName("gpt-4o-mini")
                .apiKey(apiKey)
                .build();

// Initialize LangchainModelHandler with the prompt and model options
LangchainModelHandler handler = new LangchainModelHandler(instructionPrompt, modelOptions);

//create transform
LangchainBeam.run(handler);

```

### Execution:

During pipeline execution the transform will use the model to process the input element based on the
provided instruction prompt and the transform with output a `LangchainBeamOutput` object, which encapsulates the **The model's response** and the **The input element** that was processed.

```java
LangchainBeamOutput out;
out.getOutput() // returns model's output
out.getInputElement() // returns input element
```

`LangchainBeamOutput` is a Serializable class and it is serialized using default coder provided by Beam. So, the object can be directly passed on to next transform without any aditional coder step.

## Embedding Transform

This transform integrates Embedding models as `PTransform` to generate vector embeddings for text in beam pipeline.
