package com.templates.langchainbeam;

import org.apache.beam.sdk.options.PipelineOptions;

public interface BatchProcessorPipelineOptions extends PipelineOptions {

    /** API key to authenticate with the LLM provider */
    String getApiKey();

    void setApiKey(String key);

    /** Prompt instruction to apply on each input element. */
    String getPrompt();

    void setPrompt(String prompt);

    /** Name of the model to use for processing. Example: gpt-3.5-turbo, gpt-4. */
    String getModelName();

    void setModelName(String name);

    /**
     * GCS path to the input data file. Expected format:
     * gs://<bucket>/path/to/input.txt
     */
    String getInputDataFile();

    void setInputDataFile(String file);

    /**
     * GCS path to the output file where results will be written. Example:
     * gs://<bucket>/path/to/output
     */
    String getLlmOutputFile();

    void setLlmOutputFile(String value);
}
