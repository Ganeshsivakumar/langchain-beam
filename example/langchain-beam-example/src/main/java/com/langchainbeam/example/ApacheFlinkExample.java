package com.langchainbeam.example;

import org.apache.beam.runners.flink.FlinkPipelineOptions;
import org.apache.beam.runners.flink.FlinkRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

import com.langchainbeam.LangchainBeam;
import com.langchainbeam.LangchainModelHandler;
import com.langchainbeam.model.LangchainBeamOutput;
import com.langchainbeam.model.openai.OpenAiModelOptions;

// Run sentiment analysis pipeline on Apache Flink using flink runner
public class ApacheFlinkExample {

    public static void main(String[] args) {
        String prompt = "Categorize the product review as Positive or Negative.";

        String apiKey = System.getenv("OPENAI_API_KEY");

        // Create model options with the model and its parameters
        OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
                .modelName("gpt-4o-mini")
                .apiKey(apiKey)
                .build();

        // Create Apache flink pipeline options
        FlinkPipelineOptions options = PipelineOptionsFactory.as(FlinkPipelineOptions.class);
        options.setJobName("product-review-job");
        options.setRunner(FlinkRunner.class);

        // Set Flink-specific options
        options.setParallelism(2);
        options.setMaxParallelism(16);

        // create beam pipeline
        Pipeline p = Pipeline.create(options);

        // create model handler
        LangchainModelHandler handler = new LangchainModelHandler(modelOptions, prompt);

        p.apply(TextIO.read().from("/home/ganesh/Downloads/product_reviews.csv"))// load data
                .apply(LangchainBeam.run(handler)) // apply the LangchainBeam transform.
                .apply(ParDo.of(new DoFn<LangchainBeamOutput, Void>() {

                    @ProcessElement
                    public void processElement(@Element LangchainBeamOutput out) {
                        System.out
                                .println("Model Output: " + out.getOutput() + "Input Element " + out.getInputElement());
                    }
                }));

        p.run();
    }
}
