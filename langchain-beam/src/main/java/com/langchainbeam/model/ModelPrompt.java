package com.langchainbeam.model;

import org.apache.beam.sdk.values.PCollection;

/**
 * A template for constructing prompts to be sent to the model.
 * 
 * This class provides a static prompt template, {@link ModelPrompt#PROMPT},
 * which formats each input element alongside an instruction prompt. The
 * resulting
 * string is used as input for the model to generate the desired output.
 */
public class ModelPrompt {

    /**
     * Template string for formatting the input element and instruction prompt.
     * 
     * The template takes two parameters:
     * - The input {@link PCollection} element, representing the data to
     * process.
     * - An instruction prompt that defines how the element should be
     * processed.
     * These values are substituted into the template to create a final prompt,
     * which is then passed to the model.
     */
    public static String PROMPT = "You are a PTransform within an Apache Beam pipeline. Your objective is to take the input element: {%s} and execute the following instruction prompt: {%s}. Provide the output that fulfills this instruction.";
}