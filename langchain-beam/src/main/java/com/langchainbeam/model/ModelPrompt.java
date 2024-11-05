package com.langchainbeam.model;

public class ModelPrompt {
    public static String PROMPT = "You are a PTransform within an Apache Beam pipeline. Your objective is to take the input element: {%s} and execute the following instruction prompt: {%s}. Provide the output that fulfills this instruction.";
}
