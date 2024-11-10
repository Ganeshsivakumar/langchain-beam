package com.langchainbeam.fakes;

import java.util.List;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import lombok.Builder;

/**
 * A fake chat model for testing that implements {@link ChatLanguageModel}
 * like other model providers.
 */
public class FakeChatModel implements ChatLanguageModel {

    private String modelName;
    private String apiKey;
    private Double temperature;
    private Double topP;
    private List<String> stop;
    private Integer maxTokens;
    private Integer maxCompletionTokens;

    @Builder
    public FakeChatModel(String modelName, String apiKey, Double temperature, Double topP,
            List<String> stop, Integer maxTokens, Integer maxCompletionTokens) {
        this.modelName = modelName;
        this.apiKey = apiKey;
        this.temperature = temperature;
        this.topP = topP;
        this.stop = stop;
        this.maxTokens = maxTokens;
        this.maxCompletionTokens = maxCompletionTokens;
    }

    @Override
    public Response<AiMessage> generate(List<ChatMessage> messages) {
        AiMessage aiMessage = new AiMessage("fake model response");
        return Response.from(aiMessage);
    }

}