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
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.langchainbeam.fakes.FakeChatModel;
import com.langchainbeam.fakes.FakeModelBuilder;
import com.langchainbeam.fakes.FakeModelOptions;
import com.langchainbeam.model.LangchainBeamOutput;
import com.langchainbeam.model.openai.OpenAiModelOptions;

import dev.ai4j.openai4j.OpenAiHttpException;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class LangchainBeamTest {

    String prompt = "instruction prompt";
    String output = "fake model response";

    FakeModelOptions modelOptions = FakeModelOptions.builder().modelName("fakemodel").build();
    OpenAiModelOptions openAiModelOptions = OpenAiModelOptions.builder().modelName("gpt-4").apiKey("fake-key").build();
    LangchainModelHandler handler = new LangchainModelHandler(modelOptions, prompt);

    PipelineOptions options = PipelineOptionsFactory.create();
    {
        options.setRunner(DirectRunner.class);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_langchainbeam_ptransform() {
        final Pipeline p = TestPipeline.create(options);

        String inputElement = "input pcollection element";

        PCollection<LangchainBeamOutput> modelOutput = p.apply(Create.of(inputElement))
                .apply(LangchainBeam.run(handler));

        PCollection<String> extractedOutputs = modelOutput.apply(MapElements
                .into(TypeDescriptor.of(String.class))
                .via((LangchainBeamOutput outputElement) -> outputElement.getOutput()));

        PAssert.that(extractedOutputs).containsInAnyOrder(output);

        p.run().waitUntilFinish();

    }

    @Test
    public void test_langchainbeam_ptransform_withMultipleInputs() {
        final Pipeline p = TestPipeline.create(options);
        List<String> inputs = Arrays.asList("input1", "input2", "input3");
        List<String> expectedOutputs = Arrays.asList("fake model response", "fake model response",
                "fake model response");

        PCollection<LangchainBeamOutput> modelOutput = p.apply(Create.of(inputs))
                .apply(LangchainBeam.run(handler));

        PCollection<String> extractedOutputs = modelOutput.apply(MapElements
                .into(TypeDescriptor.of(String.class))
                .via((LangchainBeamOutput outputElement) -> outputElement.getOutput()));

        PAssert.that(extractedOutputs).containsInAnyOrder(expectedOutputs);

        p.run().waitUntilFinish();
    }

    @Test
    public void test_langchainbeam_ptransform_withNullHandler() {
        final Pipeline p = TestPipeline.create(options);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Handler cannot be null");

        String inputElement = "input";

        p.apply(Create.of(inputElement)).apply(LangchainBeam.run(null));

        p.run().waitUntilFinish();
    }

    @Test
    public void test_modeloutput_error() {
        final Pipeline p = TestPipeline.create(options);
        LangchainModelHandler openaiHandler = new LangchainModelHandler(openAiModelOptions, prompt);
        String inputElement = "input";

        try {
            p.apply(Create.of(inputElement)).apply(LangchainBeam.run(openaiHandler));
            p.run().waitUntilFinish();
        } catch (PipelineExecutionException e) {
            Throwable rootCause = e.getCause();
            assertTrue(rootCause instanceof RuntimeException);
            assertTrue(rootCause.getCause() instanceof OpenAiHttpException);
        }

    }

    @Test
    public void test_FakeModelBuilder_With_IncompatibleOptions() {
        FakeModelBuilder builder = new FakeModelBuilder();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid options type. Expected FakeModelOptions.");

        builder.setOptions(openAiModelOptions);
    }

    @Test
    public void testFakeModelBuilderWithValidOptions() {
        FakeModelBuilder builder = new FakeModelBuilder();

        builder.setOptions(modelOptions);

        ChatLanguageModel model = builder.build();

        assertNotNull(model);
        assertTrue(model instanceof FakeChatModel);
    }

}
