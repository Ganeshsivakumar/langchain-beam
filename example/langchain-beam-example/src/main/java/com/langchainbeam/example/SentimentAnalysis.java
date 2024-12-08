package com.langchainbeam.example;

import java.util.HashMap;
import java.util.Map;

import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

import com.langchainbeam.LangchainBeam;
import com.langchainbeam.LangchainModelHandler;
import com.langchainbeam.model.LangchainBeamOutput;
import com.langchainbeam.model.openai.OpenAiModelOptions;

public class SentimentAnalysis {

    public static void main(String[] args) {

        identifySentiment();
        withStructuredOutput();

    }

    static void identifySentiment() {
        // instruction prompt on how to process the input element
        String prompt = "Categorize the product review as Positive or Negative.";

        String apiKey = System.getenv("OPENAI_API_KEY");

        // Create model options with the model and its parameters
        OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
                .modelName("gpt-4o-mini")
                .apiKey(apiKey)
                .build();

        // create LangchainModelHandler to pass it to LangchainBeam transform
        LangchainModelHandler handler = new LangchainModelHandler(modelOptions, prompt);

        PipelineOptions options = PipelineOptionsFactory.create();
        options.setRunner(DirectRunner.class);

        // create beam pipeline
        Pipeline p = Pipeline.create(options);

        p.apply(TextIO.read().from("/home/ganesh/Downloads/product_reviews.csv"))// set your local path
                .apply(LangchainBeam.run(handler)) // apply the LangchainBeam transform.
                .apply(ParDo.of(new DoFn<LangchainBeamOutput, Void>() {

                    @ProcessElement
                    public void processElement(@Element LangchainBeamOutput out) {
                        System.out.println("Model Output: " + out.getOutput());
                    }
                }));

        p.run();

    }

    static void withStructuredOutput() {

        String prompt = "Categorize the product review as Positive or Negative. respond only in JSON format";

        String apiKey = System.getenv("OPENAI_API_KEY");

        OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
                .modelName("gpt-4o-mini")
                .apiKey(apiKey)
                .build();

        // specify the models's output format as a key and value as description
        Map<String, String> outputFormat = new HashMap<>();
        outputFormat.put("review", "product review");
        outputFormat.put("sentiment", "sentiment of the product, postive or negative");

        LangchainModelHandler handler = new LangchainModelHandler(modelOptions, prompt, outputFormat);

        PipelineOptions options = PipelineOptionsFactory.create();
        options.setRunner(DirectRunner.class);

        Pipeline p = Pipeline.create(options);

        p.apply(TextIO.read().from("/home/ganesh/Downloads/product_reviews.csv")) // set your local path
                .apply(LangchainBeam.run(handler))
                .apply(ParDo.of(new DoFn<LangchainBeamOutput, Void>() {

                    @ProcessElement
                    public void processElement(@Element LangchainBeamOutput out) {
                        System.out.println("Model Output: " + out.getOutput());
                        // Model Output:
                        // {"sentiment":"Positive","review":"Very satisfied with my purchase. Customer
                        // support was helpful too."}
                    }
                }));

        p.run();
    }
}
