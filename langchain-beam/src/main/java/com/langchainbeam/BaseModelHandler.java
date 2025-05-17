package com.langchainbeam;

import com.langchainbeam.model.LangchainModelOptions;
import com.langchainbeam.utils.JsonUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Base abstract class for all model handlers in the Langchain-Beam framework.
 * This class provides common functionality for managing model options and output formats.
 * <p>
 * The BaseModelHandler serves as the foundation for specialized model handlers that
 * implement specific model interaction patterns. It handles serialization requirements
 * and manages the configuration options needed for model inference.
 * </p>
 *
 * @see LangchainModelHandler
 * @see LangchainDynamicPromptModelHandler
 */
public abstract class BaseModelHandler implements Serializable {

    private final LangchainModelOptions options;
    private String outputFormat;

    /**
     * Constructs a BaseModelHandler with the specified model options.
     *
     * @param options the model configuration options
     */
    public BaseModelHandler(LangchainModelOptions options) {
        this.options = options;
    }

    /**
     * Constructs a BaseModelHandler with the specified model options and output format.
     *
     * @param options      the model configuration options
     * @param outputFormat a map specifying the desired output format structure
     */
    public BaseModelHandler(LangchainModelOptions options,
                            Map<String, String> outputFormat) {
        this(options);
        this.setOutputFormat(outputFormat);
    }

    /**
     * Sets the output format by converting the provided map to a JSON string.
     *
     * @param outputFormat a map specifying the desired output format structure
     */
    private void setOutputFormat(Map<String, String> outputFormat) {
        this.outputFormat = JsonUtils.mapToJson(outputFormat);
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
     * Returns the output format as a JSON string.
     *
     * @return the output format specification as a JSON string, or null if not set
     */
    public String getOutputFormat() {
        return outputFormat;
    }
}