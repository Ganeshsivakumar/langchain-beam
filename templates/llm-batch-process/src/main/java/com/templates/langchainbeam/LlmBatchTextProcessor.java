package com.templates.langchainbeam;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

import com.langchainbeam.LangchainBeam;
import com.langchainbeam.LangchainModelHandler;
import com.langchainbeam.model.LangchainBeamOutput;
import com.langchainbeam.model.openai.OpenAiModelOptions;

public class LlmBatchTextProcessor {

  public static void main(String[] args) {

    BatchProcessorPipelineOptions options = PipelineOptionsFactory.fromArgs(args)
        .withValidation()
        .as(BatchProcessorPipelineOptions.class);

    Pipeline p = Pipeline.create(options);

    OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
        .modelName(options.getModelName())
        .apiKey(options.getApiKey())
        .build();

    LangchainModelHandler handler = new LangchainModelHandler(modelOptions, options.getPrompt());

    PCollection<String> inputData = p.apply("Read data", TextIO.read().from(options.getInputDataFile()));

    PCollection<LangchainBeamOutput> modelOutput = inputData.apply("LLM transfrom", LangchainBeam.run(handler));

    modelOutput
        .apply(
            "FormatOutput",
            MapElements.into(TypeDescriptors.strings())
                .via(
                    out -> {
                      String formatted = String.format(
                          "Model Output: %s, Input Element: %s",
                          out.getOutput(), out.getInputElement());
                      org.slf4j.LoggerFactory.getLogger(LlmBatchTextProcessor.class)
                          .info("Output: {}", formatted);
                      return formatted;
                    }))
        .apply(
            "WriteToGCS",
            TextIO.write().to(options.getLlmOutputFile()).withSuffix(".txt").withoutSharding());

    p.run();

  }

}