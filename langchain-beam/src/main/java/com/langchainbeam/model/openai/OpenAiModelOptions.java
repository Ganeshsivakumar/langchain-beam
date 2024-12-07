package com.langchainbeam.model.openai;

import java.util.List;

import com.langchainbeam.model.LangchainModelOptions;

import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.Builder;

/**
 * Configuration options for building an OpenAI model within LangChain-Beam
 * <p>
 * This class extends {@link LangchainModelOptions} and provides specific
 * configuration options
 * required for interacting with OpenAI models, such as model name, API key,
 * temperature, max tokens
 * to create and configure an {@link OpenAiChatModel}.
 * </p>
 */
@SuppressWarnings("FieldMayBeFinal")
public class OpenAiModelOptions extends LangchainModelOptions {
    private String modelName;
    private String apiKey;
    private Double temperature;
    private Double topP;
    private List<String> stop;
    private Integer maxCompletionTokens;

    /**
     * Constructs an instance of {@code OpenAiModelOptions} with the specified
     * configuration settings.
     *
     * @param apiKey              The API key for authenticating requests to the
     *                            OpenAI API.
     * @param modelName           The name of the model to use.
     * @param temperature         The temperature value for controlling randomness
     *                            in output.
     * @param topP                The top-p value for nucleus sampling.
     * @param stop                A list of stop sequences to terminate the
     *                            generation.
     * @param maxCompletionTokens The maximum number of tokens to generate in the
     *                            completion.
     */
    @Builder
    public OpenAiModelOptions(String apiKey, String modelName, Double temperature, Double topP, List<String> stop,
            Integer maxCompletionTokens) {
        this.modelName = modelName;
        this.apiKey = apiKey;
        this.temperature = temperature;
        this.topP = topP;
        this.stop = stop;
        this.maxCompletionTokens = maxCompletionTokens;
    }

    /**
     * Returns the builder class used to construct Model.
     * 
     * @return the {@link OpenAiModelBuilder} class
     */
    @Override
    public Class<OpenAiModelBuilder> getModelBuilderClass() {
        return OpenAiModelBuilder.class;
    }

    public String getModelName() {
        return modelName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getTopP() {
        return topP;
    }

    public List<String> getStop() {
        return stop;
    }

    public Integer getMaxCompletionTokens() {
        return maxCompletionTokens;
    }

}
