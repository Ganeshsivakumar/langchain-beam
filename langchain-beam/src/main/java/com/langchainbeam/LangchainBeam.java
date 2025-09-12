package com.langchainbeam;

import com.langchainbeam.model.LangchainBeamOutput;
import com.langchainbeam.model.LangchainModelOptions;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.windowing.AfterPane;
import org.apache.beam.sdk.transforms.windowing.AfterProcessingTime;
import org.apache.beam.sdk.transforms.windowing.Repeatedly;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;
import org.joda.time.Duration;

import java.io.IOException;
import java.util.Map;


/**
 * A {@link PTransform} that integrates LangChain's ChatModel interface with
 * Apache Beam.
 * <p>
 * This transform converts a {@link PCollection} of input elements
 * into a {@link PCollection} of {@link LangchainBeamOutput} objects representing
 * model outputs generated using LangChain's model provider interface.
 * <p>
 * The transformation applies a {@link LangchainModelHandler} that takes in
 * {@link LangchainModelOptions} along with a prompt, which contains
 * instructions for processing each element by performing classification,
 * generation or executing a specific data processing task. This transformation uses
 * {@link ChatLanguageModel} inference to process the instructions, yielding
 * a {@link PCollection} of processed outputs as the model's result.
 * </p>
 * <p>
 * Example usage:
 *
 * <pre>
 * // Define the instruction prompt to process the element
 * String prompt = "Categorize the product review as Positive or Negative.";
 *
 * // Create model options
 * OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
 *         .modelName("gpt-4o-mini")
 *         .apiKey(OPENAI_API_KEY)
 *         .build();
 *
 * // Initialize LangchainModelHandler
 * LangchainModelHandler handler = new LangchainModelHandler(modelOptions, prompt);
 *
 * // Create the pipeline
 * Pipeline p = Pipeline.create();
 *
 * // Apply LangchainBeam transform in the pipeline
 * p.apply(TextIO.read().from("/path/to/product_reviews.csv"))
 *         .apply(LangchainBeam.run(handler)) // Run model handler
 *         .apply(ParDo.of(new DoFn<LangchainBeamOutput, Void>() {
 *             &#064;ProcessElement
 *             public void processElement(@Element LangchainBeamOutput output) {
 *                 System.out.println("Model Output: " + output.getOutput());
 *             }
 *         }));
 * p.run(); // Execute the pipeline
 * </pre>
 */
public class LangchainBeam {

    /**
     * Creates and initializes a new {@link PTransform} with the provided
     * {@link LangchainModelHandler}.
     * <p>
     * This static method validates the provided handler and returns a new PTransform
     * configured with it.
     * </p>
     *
     * @param handler the {@link LangchainModelHandler} to process input elements in
     *                the transformation
     * @return a new {@link PTransform} that processes inputs with the provided handler
     * @throws IllegalArgumentException if the handler is {@code null}
     */
    public static PTransform<PCollection<String>, PCollection<LangchainBeamOutput>> run(
            LangchainModelHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("Handler cannot be null");
        }
        return new Inner(handler);
    }

    /**
     * Creates and initializes a new {@link PTransform} with the provided
     * {@link LangchainDynamicPromptModelHandler} for dynamic prompt handling.
     * <p>
     * This method allows the prompt to be dynamically loaded from a file and updated
     * during pipeline execution. The prompt file is monitored for changes according to
     * the handler's configuration.
     * </p>
     *
     * @param handler the {@link LangchainDynamicPromptModelHandler} to process input elements
     *                with a dynamically loaded prompt
     * @return a new {@link PTransform} that processes inputs with the provided handler
     * @throws IllegalArgumentException if the handler is {@code null}
     */
    public static PTransform<PCollection<String>, PCollection<LangchainBeamOutput>> runWithDynamicPrompt(
            LangchainDynamicPromptModelHandler handler
    ) {
        if (handler == null) {
            throw new IllegalArgumentException("Handler cannot be null");
        }
        return new InnerWithDynamicPrompt(handler);
    }

    /**
     * Creates and initializes a new {@link PTransform} with the provided
     * {@link LangchainDynamicPromptModelHandler} for handling multiple dynamic prompts.
     * <p>
     * This method processes key-value pairs where the key identifies which prompt template
     * to use from a collection of dynamically loaded prompts. This allows for different
     * processing instructions based on the input data type or category.
     * </p>
     *
     * @param handler the {@link LangchainDynamicPromptModelHandler} to process input elements
     *                with multiple dynamically loaded prompts
     * @return a new {@link PTransform} that processes key-value inputs with the provided handler
     * @throws IllegalArgumentException if the handler is {@code null}
     */
    public static PTransform<PCollection<KV<String, String>>, PCollection<LangchainBeamOutput>> runWithDynamicMultiplePrompt(
            LangchainDynamicPromptModelHandler handler
    ) {
        if (handler == null) {
            throw new IllegalArgumentException("Handler cannot be null");
        }
        return new InnerWithMultipleDynamicPrompt(handler);
    }

    /**
     * Abstract base class for all inner implementations of the LangchainBeam transform.
     * <p>
     * Provides common functionality for handling streaming options and model handlers.
     * </p>
     *
     * @param <InputT>   the type of elements in the input {@link PCollection}
     * @param <HandlerT> the type of model handler to use for processing
     */
    private abstract static class BaseInner<InputT, HandlerT extends BaseModelHandler> extends PTransform<PCollection<InputT>, PCollection<LangchainBeamOutput>> {
        protected final HandlerT handler;

        private BaseInner(HandlerT handler) {
            this.handler = handler;
        }

        /**
         * Determines if the pipeline is running in streaming mode.
         *
         * @param options the pipeline options
         * @return true if the pipeline is in streaming mode, false otherwise
         */
        protected boolean isStreaming(PipelineOptions options) {
            return options.as(StreamingOptions.class).isStreaming();
        }
    }

    /**
     * Implementation of {@link BaseInner} that handles multiple dynamic prompts.
     * <p>
     * This class processes input elements with key-value pairs, where the key determines
     * which prompt template to use from a dynamically loaded collection of prompts.
     * </p>
     */
    private static class InnerWithMultipleDynamicPrompt extends BaseInner<KV<String, String>, LangchainDynamicPromptModelHandler> {

        private InnerWithMultipleDynamicPrompt(LangchainDynamicPromptModelHandler handler) {
            super(handler);
        }

        /**
         * Applies the transformation to the input {@link PCollection} of key-value pairs.
         * <p>
         * Dynamically loads multiple prompt templates from files and uses them to process
         * input elements based on their associated keys.
         * </p>
         *
         * @param input the input {@link PCollection} containing key-value pairs to be processed
         * @return a {@link PCollection} of {@link LangchainBeamOutput} representing the model outputs
         */
        @Override
        public PCollection<LangchainBeamOutput> expand(PCollection<KV<String, String>> input) {
            final FileIO.Match match;
            if (isStreaming(input.getPipeline().getOptions())) {
                match = FileIO.match().filepattern(handler.getPromptPath())
                        .continuously(Duration.standardMinutes(handler.getWatchDurationMinutes()), Watch.Growth.never(), true);
            } else {
                match = FileIO.match().filepattern(handler.getPromptPath());
            }
            final PCollectionView<Map<String, Iterable<String>>> promptView = input.getPipeline()
                    .apply(match)
                    .apply(FileIO.readMatches())
                    .apply(ParDo.of(new DoFn<FileIO.ReadableFile, KV<String, String>>() {
                        @ProcessElement
                        public void process(@Element FileIO.ReadableFile file, OutputReceiver<KV<String, String>> output) throws IOException {
                            final String filename = file.getMetadata().resourceId().getFilename();
                            final String prompt = file.readFullyAsUTF8String();
                            output.output(KV.of(filename, prompt));
                        }
                    }))
                    .apply(Window.<KV<String, String>>configure()
                            .triggering(
                                    Repeatedly.forever(
                                            AfterProcessingTime.pastFirstElementInPane()
                                                    .alignedTo(Duration.standardSeconds(
                                                            /* ensure this provides enough time to collect all prompts from the specified path*/
                                                            10L
                                                    ))
                                    )
                            )
                            .accumulatingFiredPanes()
                            .withAllowedLateness(Duration.ZERO)
                    )
                    .apply(View.asMultimap());

            return input.apply("Langchain Beam Model Transform with Multiple Dynamic Prompt", ParDo.of(new LangchainBeamDynamicMultiplePromptDoFn(handler))
                    .withSideInput("prompt", promptView));
        }
    }

    /**
     * Implementation of {@link BaseInner} that handles a single dynamic prompt.
     * <p>
     * This class processes input elements using a prompt template that is dynamically
     * loaded from a file and can be updated during pipeline execution.
     * </p>
     */
    private static class InnerWithDynamicPrompt extends BaseInner<String, LangchainDynamicPromptModelHandler> {

        private InnerWithDynamicPrompt(LangchainDynamicPromptModelHandler handler) {
            super(handler);
        }

        /**
         * Applies the transformation to the input {@link PCollection} of elements.
         * <p>
         * Dynamically loads a prompt template from a file and uses it to process
         * each input element.
         * </p>
         *
         * @param input the input {@link PCollection} containing elements to be processed
         * @return a {@link PCollection} of {@link LangchainBeamOutput} representing the model outputs
         */
        @Override
        public PCollection<LangchainBeamOutput> expand(PCollection<String> input) {
            final TextIO.Read read;
            if (isStreaming(input.getPipeline().getOptions())) {
                read = TextIO.read()
                        .from(handler.getPromptPath())
                        .watchForNewFiles(
                                Duration.standardMinutes(handler.getWatchDurationMinutes()),
                                Watch.Growth.never(),
                                true
                        );
            } else {
                read = TextIO.read().from(handler.getPromptPath());
            }

            final PCollectionView<String> promptView = input.getPipeline()
                    .apply(read)
                    .apply(Window.<String>configure()
                            .triggering(
                                    Repeatedly.forever(
                                            AfterPane.elementCountAtLeast(1)
                                    )
                            )
                            .discardingFiredPanes()
                            .withAllowedLateness(Duration.ZERO)
                    )
                    .apply(View.<String>asSingleton()
                            .withDefaultValue("" /*prevent for empty PCollection*/)
                    );

            return input.apply("Langchain Beam Model Transform with Dynamic Prompt",
                    ParDo.of(new LangchainBeamDynamicPromptDoFn(handler))
                            .withSideInput("prompt", promptView));
        }
    }


    /**
     * Implementation of {@link BaseInner} that handles a static prompt.
     * <p>
     * This class processes input elements using a fixed prompt template provided
     * at construction time.
     * </p>
     */
    private static class Inner extends BaseInner<String, LangchainModelHandler> {

        private Inner(LangchainModelHandler handler) {
            super(handler);
        }

        /**
         * Applies the transformation to the input {@link PCollection}
         * of elements. Each element is processed by a {@link LangchainBeamDoFn}, which
         * uses the element's content, an instruction prompt, and the model's
         * configuration to generate a model output.
         *
         * @param input the input {@link PCollection} containing elements to be
         *              processed by the LangChain model
         * @return a {@link PCollection} of {@link LangchainBeamOutput} representing the model
         * outputs for each input element
         */
        @Override
        public PCollection<LangchainBeamOutput> expand(PCollection<String> input) {
            return input.apply("LangchainBeam Model Transform", ParDo.of(new LangchainBeamDoFn(this.handler)));
        }
    }

}
