package com.langchainbeam;

import com.langchainbeam.model.LangchainBeamOutput;
import org.apache.beam.sdk.transforms.DoFn;

/**
 * A specialized {@link DoFn} implementation that processes input elements using a LangChain model
 * with dynamically provided prompts.
 *
 * <p>
 * Unlike {@link LangchainBeamDoFn}, this class accepts prompts as side inputs, allowing for
 * dynamic prompt configuration during pipeline execution. This enables more flexible processing
 * where prompts can vary based on runtime conditions or external inputs.
 * </p>
 *
 * <p>
 * The class uses a {@link LangchainDynamicPromptModelHandler} to configure the underlying
 * language model while accepting the actual prompt text as a side input.
 * </p>
 */
public class LangchainBeamDynamicPromptDoFn extends BaseLangchainDoFn<LangchainDynamicPromptModelHandler, String> {

    /**
     * Constructs a new LangchainBeamDynamicPromptDoFn with the specified model handler.
     *
     * @param handler The model handler that provides configuration for this DoFn
     */
    protected LangchainBeamDynamicPromptDoFn(LangchainDynamicPromptModelHandler handler) {
        super(handler);
    }

    /**
     * Processes each input element using a dynamically provided prompt.
     *
     * <p>
     * This method takes a prompt as a side input, combines it with the input element,
     * and passes the formatted prompt to the language model. The resulting output is
     * emitted as a {@link LangchainBeamOutput} object containing both the input and output.
     * </p>
     *
     * <p>
     * If the provided prompt is null or empty, an {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param prompt  The instruction prompt provided as a side input
     * @param element The input element to process
     * @param output  The output receiver for emitting the processing result
     * @throws IllegalArgumentException if the prompt is null or empty
     */
    @ProcessElement
    public void processElement(
            @SideInput("prompt") String prompt,
            @Element String element,
            OutputReceiver<LangchainBeamOutput> output) {
        if (prompt == null || prompt.isEmpty()) {
            throw new IllegalArgumentException("prompt cannot be null or empty");
        }

        final String finalPrompt = this.getFinalPrompt(element, prompt);
        final String modelOutput = this.doGenerate(finalPrompt);
        LangchainBeamOutput lbModelOutput = LangchainBeamOutput.builder().inputElement(element)
                .output(modelOutput).build();
        output.output(lbModelOutput);
    }
}