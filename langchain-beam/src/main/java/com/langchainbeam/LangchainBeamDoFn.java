package com.langchainbeam;

import com.langchainbeam.model.LangchainBeamOutput;
import com.langchainbeam.model.LangchainModelBuilder;
import com.langchainbeam.model.ModelPrompt;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.apache.beam.sdk.transforms.DoFn;

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
 */
class LangchainBeamDoFn extends BaseLangchainDoFn<LangchainModelHandler, String> {

    /**
     * Constructs a new LangchainBeamDoFn with the specified model handler.
     *
     * @param handler The model handler that provides configuration and behavior for this DoFn
     */
    protected LangchainBeamDoFn(LangchainModelHandler handler) {
        super(handler);
    }

    /**
     * Processes each input element, generating a model output based on the
     * element's content and the instruction prompt.
     * <p>
     * This method uses {@link ModelPrompt#PROMPT} to format a final prompt,
     * incorporating both the input element's string representation and the handler's instruction
     * prompt. The formatted prompt is then passed to the model to generate an output, which is
     * emitted as a {@link LangchainBeamOutput} object containing both the input and output.
     * </p>
     *
     * @param context The processing context that provides access to the input element
     *                and allows outputting the result
     */
    @ProcessElement
    public void processElement(ProcessContext context) {
        String input = context.element();
        final String finalPrompt = this.getFinalPrompt(input, handler.getPrompt());
        String modelOutput = this.doGenerate(finalPrompt);
        LangchainBeamOutput lbModelOutput = LangchainBeamOutput.builder().inputElement(input)
                .output(modelOutput).build();
        context.output(lbModelOutput);
    }
}