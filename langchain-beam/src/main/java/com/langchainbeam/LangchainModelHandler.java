package com.langchainbeam;

import java.io.Serializable;

import com.langchainbeam.model.LangchainModelOptions;

/**
 * A handler class for managing LangChain model options and instruction prompts.
 * This class is used to configure the model options (e.g., model name,
 * temperature)
 * and the
 * instruction prompt that is passed to the model for inference.
 * <p>
 * The handler encapsulates the {@link LangchainModelOptions} and the
 * instruction prompt, which
 * are necessary to interact with LangChain's model provider interface. The
 * handler is designed
 * to be used in conjunction with {@link LangchainBeam} to run inference tasks
 * on a {@link PCollection} of data.
 * </p>
 */
public class LangchainModelHandler implements Serializable {

    private final LangchainModelOptions options;
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
        this.options = options;
        this.instructionPrompt = instructionPrompt;

    }

    /**
     * Returns the {@link LangchainModelOptions} for this handler, which includes
     * model configurations such as the model name and API key.
     * 
     * @return the model options used for inference
     */
    public LangchainModelOptions getOptions() {
        return options;
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
