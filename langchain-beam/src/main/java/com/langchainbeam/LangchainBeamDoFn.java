package com.langchainbeam;

import java.lang.reflect.InvocationTargetException;

import org.apache.beam.sdk.transforms.DoFn;

import com.langchainbeam.model.LangchainModelBuilder;
import com.langchainbeam.model.LangchainModelOptions;
import com.langchainbeam.model.ModelPrompt;

import dev.langchain4j.model.chat.ChatLanguageModel;

public class LangchainBeamDoFn<T> extends DoFn<T, String> {

    private final LangchainModelHandler handler;
    // private final SerializableFunction<String, T> modelOutputCallback;
    private ChatLanguageModel model;
    private LangchainModelBuilder modelBuilder;
    private String modelOutput;

    public LangchainBeamDoFn(LangchainModelHandler handler) {
        this.handler = handler;
    }

    @Setup
    public void setup() throws Exception {
        LangchainModelOptions options = handler.getOptions();
        Class<? extends LangchainModelBuilder> modelBuilderClass = options.getModelBuilderClass();

        try {
            modelBuilder = modelBuilderClass.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException
                | SecurityException | InvocationTargetException e) {
            throw new Exception("Failed to set up Langchain model due to instantiation error: ", e);
        }

        modelBuilder.setOptions(options);
        model = modelBuilder.build();

    }

    @ProcessElement
    public void processElement(ProcessContext context) {
        T input = context.element();

        final String finalPrompt = String.format(ModelPrompt.PROMPT, input.toString(), handler.getPrompt());
        try {
            modelOutput = model.generate(finalPrompt);

        } catch (Exception e) {
            throw new RuntimeException("Error generating model output for prompt: " + finalPrompt, e);
        }

        context.output(modelOutput);
    }

}
