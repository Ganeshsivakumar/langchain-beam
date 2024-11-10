package com.langchainbeam;

import java.lang.reflect.InvocationTargetException;

import org.apache.beam.sdk.transforms.DoFn;

import com.langchainbeam.model.LangchainModelBuilder;
import com.langchainbeam.model.LangchainModelOptions;
import com.langchainbeam.model.ModelPrompt;

import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * A {@link DoFn} implementation for Apache Beam that processes input elements
 * using a LangChain model. Each element is transformed based on a prompt
 * and model configuration provided by the {@link LangchainModelHandler}.
 *
 * <p>
 * This class sets up a {@link ChatLanguageModel} via a
 * {@link LangchainModelBuilder}
 * and uses it to generate outputs based on the input element's content
 * combined with a prompt. The generated output is emitted as a string.
 * </p>
 *
 * @param <T> the type of input elements in the {@link PCollection}
 */
class LangchainBeamDoFn<T> extends DoFn<T, String> {

    private final LangchainModelHandler handler;
    // private final SerializableFunction<String, T> modelOutputCallback;
    private ChatLanguageModel model;
    private LangchainModelBuilder modelBuilder;
    private String modelOutput;

    /**
     * Constructs a new {@link LangchainBeamDoFn} with the specified
     * {@link LangchainModelHandler}.
     *
     * @param handler the {@link LangchainModelHandler} used to configure
     *                and build the LangChain model
     */
    public LangchainBeamDoFn(LangchainModelHandler handler) {
        this.handler = handler;
    }

    /**
     * Initializes the LangChain model before processing elements.
     * <p>
     * This method retrieves model options from the handler, instantiates a
     * {@link LangchainModelBuilder} based on those options, and configures
     * it to build the model.
     * </p>
     *
     * @throws Exception if an error occurs during model setup, such as
     *                   instantiation or configuration errors
     */
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

    /**
     * Processes each input element, generating a model output based on the
     * element's content and the instruction prompt.
     * <p>
     * This method uses {@link ModelPrompt#PROMPT} to format a final prompt,
     * incorporating
     * both the input element's string representation and the handler's instruction
     * prompt.
     * The formatted prompt is then passed to the model to generate an output, which
     * is
     * emitted as a string.
     * </p>
     *
     */
    @ProcessElement
    public void processElement(ProcessContext context) {
        T input = context.element();

        final String finalPrompt = String.format(ModelPrompt.PROMPT, input.toString(), handler.getPrompt());
        try {
            modelOutput = model.generate(finalPrompt);

        } catch (Exception e) {
            throw e;
        }

        context.output(modelOutput);
    }

}
