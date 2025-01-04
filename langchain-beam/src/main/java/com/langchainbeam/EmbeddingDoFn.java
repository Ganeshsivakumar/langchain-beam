package com.langchainbeam;

import java.lang.reflect.InvocationTargetException;

import org.apache.beam.sdk.transforms.DoFn;

import com.langchainbeam.model.BeamEmbedding;
import com.langchainbeam.model.EmbeddingModelBuilder;
import com.langchainbeam.model.EmbeddingOutput;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;

class EmbeddingDoFn extends DoFn<String, EmbeddingOutput> {

    private final EmbeddingModelHandler handler;
    private EmbeddingModelBuilder modelBuilder;
    private EmbeddingModel model;
    private Response<Embedding> response;

    public EmbeddingDoFn(EmbeddingModelHandler handler) {
        this.handler = handler;
    }

    @Setup
    public void setupModel() throws Exception {

        Class<? extends EmbeddingModelBuilder> modelBuilderClass = handler.getOptions().getModelBuilderClass();

        try {
            modelBuilder = modelBuilderClass.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException
                | SecurityException | InvocationTargetException e) {
            throw new Exception("Failed to set up Embedding model due to instantiation error: ", e);
        }

        modelBuilder.setOptions(handler.getOptions());
        model = modelBuilder.build();

    }

    @ProcessElement
    public void processElement(ProcessContext context) {

        String input = context.element();

        try {
            response = model.embed(input);
        } catch (Exception e) {
            throw e;
        }

        EmbeddingOutput embeddingOutput = EmbeddingOutput.builder()
                .embedding(new BeamEmbedding(response.content().vector())).inputElement(input)
                .build();
        context.output(embeddingOutput);
    }

}
