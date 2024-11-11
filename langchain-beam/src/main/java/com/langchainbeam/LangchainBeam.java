package com.langchainbeam;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import com.langchainbeam.model.LangchainModelOptions;

import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * A {@link PTransform} that integrates LangChain's ChatModel interface with
 * Apache Beam.
 * 
 * This transform converts a {@link PCollection} of input elements
 * into a {@link PCollection} of strings representing model outputs generated
 * using LangChain's model provider interface.
 * <p>
 * The transformation applies a {@link LangchainModelHandler} that takes in
 * {@link LangchainModelOptions} along with a prompt, which contains
 * instructions for processing each element by performing classification,
 * generation or
 * executing a specific data processing task. This transformation uses
 * {@link ChatLanguageModel}
 * inference to
 * process the instructions, yielding a {@link PCollection} of processed strings
 * as the model’s output.
 * </p>
 * 
 * @param <T> the type of elements in the input {@link PCollection}
 * 
 *            Example usage:
 * 
 *            <pre>
 *            // Define the instruction prompt to process the element
 *            String prompt = "Categorize the product review as Positive or Negative.";
 * 
 *            // Create model options
 *            OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
 *                    .modelName("gpt-4o-mini")
 *                    .apiKey(OPENAI_API_KEY)
 *                    .build();
 * 
 *            // Initialize LangchainModelHandler
 *            LangchainModelHandler handler = new LangchainModelHandler(modelOptions, prompt);
 * 
 *            // Create the pipeline
 *            Pipeline p = Pipeline.create();
 * 
 *            // Apply LangchainBeam transform in the pipeline
 *            p.apply(TextIO.read().from("path/to/product_reviews.csv"))
 *                    .apply(LangchainBeam.run(handler)) // Run model handler
 *                    .apply(ParDo.of(new DoFn<String, Void>() {
 *                        public void processElement(@Element String output) {
 *                            System.out.println("Model Output: " + output);
 *                        }
 *                    }));
 *            p.run(); // Execute the pipeline
 *            </pre>
 */
public class LangchainBeam<T> extends PTransform<PCollection<T>, PCollection<String>> {
    private final LangchainModelHandler handler;

    private LangchainBeam(LangchainModelHandler handler) {
        this.handler = handler;
    }

    /**
     * Applies the transformation to the input {@link PCollection}
     * of elements. Each element is processed by a {@link LangchainBeamDoFn}, which
     * uses the element's content, an instruction prompt, and the model's
     * configuration to generate a model output.
     * 
     * @param input the input {@link PCollection} containing elements to be
     *              processed by the LangChain model
     * @return a {@link PCollection} of {@link String} representing the model
     *         outputs for each input element
     */
    @Override
    public PCollection<String> expand(PCollection<T> input) {
        return input.apply("LangchainBeam Model Transform", ParDo.of(new LangchainBeamDoFn<>(handler)));
    }

    /**
     * Creates and initializes a new {@link LangchainBeam} instance with the
     * provided
     * {@link LangchainModelHandler}.
     * <p>
     * This static method validates the provided handler and returns a new instance
     * of {@link LangchainBeam} configured with it.
     * </p>
     * 
     * @param handler the {@link LangchainModelHandler} to process input elements in
     *                the transformation
     * @param <T>     the type of input {@link PCollection} element
     * @return a new instance of {@link LangchainBeam}
     * @throws IllegalArgumentException if the handler is {@code null}
     */
    public static <T> LangchainBeam<T> run(LangchainModelHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("Handler cannot be null");
        }
        return new LangchainBeam<>(handler);
    }

}
