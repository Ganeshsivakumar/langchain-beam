package com.langchainbeam;

import com.langchainbeam.model.LangchainModelOptions;
import org.apache.beam.sdk.values.PCollection;

import java.util.Map;

/**
 * A handler class for managing LangChain model options and instruction prompts.
 * This class is used to configure the model options (e.g., model name,
 * temperature) and the instruction prompt that is passed to the model for inference.
 * <p>
 * The handler encapsulates the {@link LangchainModelOptions} and the
 * instruction prompt, which are necessary to interact with LangChain's model provider interface.
 * The handler is designed to be used in conjunction with {@link LangchainBeam} to run
 * inference tasks on a {@link PCollection} of data.
 * </p>
 *
 * @see BaseModelHandler
 * @see LangchainBeam
 */
public class LangchainModelHandler extends BaseModelHandler {

    private final String instructionPrompt;

    /**
     * Constructs a new {@link LangchainModelHandler} with the specified model
     * options and instruction prompt.
     *
     * @param options           the {@link LangchainModelOptions} containing model
     *                          configurations such as model name and API key
     * @param instructionPrompt the instruction prompt that will guide the model's
     *                          behavior (e.g., for classification tasks)
     */
    public LangchainModelHandler(LangchainModelOptions options, String instructionPrompt) {
        super(options);
        this.instructionPrompt = instructionPrompt;
    }

    /**
     * Constructs a new {@link LangchainModelHandler} with the specified model
     * options, instruction prompt, and output format.
     *
     * @param options           the {@link LangchainModelOptions} containing model
     *                          configurations
     * @param instructionPrompt the instruction prompt to guide the model on
     *                          processing the element.
     *                          Note: Instruct to respond in JSON to get output
     *                          as a JSON string. Use out `outputFormat` Map to
     *                          specify the format
     * @param outputFormat      the desired output format, represented as a
     *                          map of keys and values as description
     */
    public LangchainModelHandler(LangchainModelOptions options, String instructionPrompt,
                                 Map<String, String> outputFormat) {
        super(options, outputFormat);
        this.instructionPrompt = instructionPrompt;
    }

    /**
     * Returns the instruction prompt that guides the model in performing tasks such
     * as classification or generating outputs.
     *
     * @return the instruction prompt string
     */
    public String getPrompt() {
        return instructionPrompt;
    }
}