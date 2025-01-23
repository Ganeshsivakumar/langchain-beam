# Models

The integration of Large Language Models is managed by Langchain-Beam library using langchain. Pipeline authors just need to specify the provider and the model that they want to use with the
transform in beam pipeline.

Each model provider comes with its own dedicated options class within the library. For example, if you wish to use OpenAI's models, you can configure them using the corresponding options class.

```java
//import the OpenAi options class
import com.langchainbeam.model.openai.OpenAiModelOptions;
import com.langchainbeam.LangchainModelHandler;

// create model options
OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
                .modelName("gpt-4o-mini")
                .apiKey(apiKey)
                .temperature(0.7)
                .build();

// pass the options to handler
LangchainModelHandler handler = new LangchainModelHandler(instructionPrompt, modelOptions);

//create transform
LangchainBeam.run(handler);
```

Example for Anthropic model :

```java
import com.langchainbeam.model.anthropic.AnthropicModelOptions;

AnthropicModelOptions modelOptions1 = AnthropicModelOptions.builder()
                .modelName("claude-3-5-sonnet-latest")
                .apiKey(ANTHROPIC_API_KEY)
                .build();
```

Additional providers will be integrated in future releases. If you need support for a specific provider or are interested in contributing to the integration of new model providers, please feel free to create a GitHub issue.
