package com.langchainbeam.model;

/**
 * A template for constructing prompts to be sent to the model.
 * <p>
 * This class provides a static prompt template, {@link ModelPrompt#PROMPT},
 * which
 * formats each input element alongside an instruction prompt. The resulting
 * string
 * is used as input for the model to generate the desired output.
 * </p>
 */
public class ModelPrompt {

    /**
     * Template string for formatting the input element and instruction prompt.
     * <p>
     * The template takes two parameters:
     * <ul>
     * <li>The input {@link PCollection} element, representing the data to
     * process.</li>
     * <li>An instruction prompt that defines how the element should be
     * processed.</li>
     * </ul>
     * These values are substituted into the template to create a final prompt,
     * which is then passed to the model
     * </p>
     */
    public static String PROMPT = "You are a PTransform within an Apache Beam pipeline. Your objective is to take the input element: {%s} and execute the following instruction prompt: {%s}. Provide the output that fulfills this instruction.";
}
