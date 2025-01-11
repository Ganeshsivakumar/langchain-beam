package com.langchainbeam;

import java.util.Arrays;
import java.util.List;

import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.Pipeline.PipelineExecutionException;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.values.PCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.langchainbeam.fakes.FakeEmbeddingModel;
import com.langchainbeam.fakes.FakeEmbeddingModelBuilder;
import com.langchainbeam.fakes.FakeEmbeddingModelOptions;
import com.langchainbeam.model.BeamEmbedding;
import com.langchainbeam.model.EmbeddingModelOptions;
import com.langchainbeam.model.EmbeddingOutput;
import com.langchainbeam.model.openai.OpenAiEmbeddingModelOptions;

import dev.ai4j.openai4j.OpenAiHttpException;
import dev.langchain4j.model.embedding.EmbeddingModel;

public class LangchainBeamEmbeddingTest {

    String input = "input embedding string";
    FakeEmbeddingModelOptions modelOptions = FakeEmbeddingModelOptions.builder().apiKey("key")
            .modelName("fake-embedding-model").build();

    OpenAiEmbeddingModelOptions oaiOptions = OpenAiEmbeddingModelOptions.builder().apikey("key")
            .modelName("text-embedding-3-small").build();

    EmbeddingModelHandler handler = new EmbeddingModelHandler(modelOptions);

    float[] vector = { 0.071781114f, 0.24112974f, -0.26148483f, };
    BeamEmbedding embedding = new BeamEmbedding(vector);

    PipelineOptions options = PipelineOptionsFactory.create();
    {
        options.setRunner(DirectRunner.class);
    }

    @Test
    public void test_embed() {
        EmbeddingModelOptions mockOptions = new EmbeddingModelOptions(null);
        EmbeddingModelHandler mockHandler = new EmbeddingModelHandler(mockOptions);
        LangchainBeamEmbedding transform = LangchainBeamEmbedding.embed(mockHandler);

        assertNotNull(transform);
        assertEquals(mockOptions, mockHandler.getOptions());
    }

    @Test
    public void testTransform() {

        final Pipeline p = TestPipeline.create(options);

        PCollection<EmbeddingOutput> output = p.apply(Create.of(input))
                .apply(LangchainBeamEmbedding.embed(handler));

        PAssert.that(output).containsInAnyOrder(Arrays.asList(new EmbeddingOutput(embedding, input)));

    }

    @Test
    public void test_null_input() {
        String nullInput = null;
        final Pipeline p = TestPipeline.create(options);

        p.apply(Create.of(nullInput))
                .apply(LangchainBeamEmbedding.embed(handler));

        Exception exception = assertThrows(Pipeline.PipelineExecutionException.class, () -> {
            p.run().waitUntilFinish();
        });

        Throwable cause = exception.getCause();
        assertNotNull(cause);
        assertEquals(IllegalArgumentException.class, cause.getClass());
        assertEquals("text cannot be null or blank", cause.getMessage());
    }

    @Test
    public void test_embeddingOutput() {

        final Pipeline p = TestPipeline.create(options);
        PCollection<EmbeddingOutput> output = p.apply(Create.of(input))
                .apply(LangchainBeamEmbedding.embed(handler));

        PAssert.that(output).satisfies((SerializableFunction<Iterable<EmbeddingOutput>, Void>) out -> {
            for (EmbeddingOutput o : out) {
                assertEquals(3, o.getEmbedding().dimention());
            }
            return null;
        });

        p.run();
    }

    @Test
    public void test_multiple_iputs() {

        final Pipeline p = TestPipeline.create(options);

        List<String> inputs = Arrays.asList("input 1", "input 2", "input 3");
        PCollection<EmbeddingOutput> output = p.apply(Create.of(inputs))
                .apply(LangchainBeamEmbedding.embed(handler));

        PAssert.that(output).containsInAnyOrder(
                new EmbeddingOutput(embedding, "input 1"),
                new EmbeddingOutput(embedding, "input 2"),
                new EmbeddingOutput(embedding, "input 3"));

        p.run();
    }

    @Test
    public void test_with_validOptions() {

        FakeEmbeddingModelBuilder builder = new FakeEmbeddingModelBuilder();

        builder.setOptions(modelOptions);

        EmbeddingModel model = builder.build();
        assertNotNull(model);
        assertTrue(model instanceof FakeEmbeddingModel);
    }

    @Test
    public void test_with_IncompatibleOptions() {

        FakeEmbeddingModelBuilder builder = new FakeEmbeddingModelBuilder();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    builder.setOptions(oaiOptions);
                });
        assertEquals("Invalid model options, Expected FakeEmbeddingModelOptions", exception.getMessage());
    }

    @Test
    public void test_transform_with_NullHandler() {

        final Pipeline p = TestPipeline.create(options);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            p.apply(Create.of(input)).apply(LangchainBeamEmbedding.embed(null));
        });

        assertEquals("Handler cannot be null", exception.getMessage());

    }

    @Test
    public void test_modeloutput_error() {

        final Pipeline p = TestPipeline.create(options);
        EmbeddingModelHandler openaiHandler = new EmbeddingModelHandler(modelOptions);
        try {
            p.apply(Create.of(input)).apply(LangchainBeamEmbedding.embed(openaiHandler));
            p.run().waitUntilFinish();
        } catch (PipelineExecutionException e) {
            Throwable rootCause = e.getCause();
            assertTrue(rootCause instanceof RuntimeException);
            assertTrue(rootCause.getCause() instanceof OpenAiHttpException);
        }
    }

    @Test
    public void test_modelOptions() {
        assertEquals(modelOptions.getApiKey(), "key");
        assertEquals(modelOptions.getModelName(), "fake-embedding-model");
    }

}
