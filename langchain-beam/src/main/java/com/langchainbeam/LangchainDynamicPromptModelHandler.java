package com.langchainbeam;

import com.langchainbeam.model.LangchainModelOptions;

import java.util.Map;

/**
 * A specialized model handler that loads prompts dynamically from a file path.
 * <p>
 * This handler allows for prompts to be updated without requiring application
 * redeployment by monitoring a specified file path for changes. The prompt file
 * is periodically checked for updates based on the configured watch duration.
 * </p>
 * <p>
 * This approach is particularly useful for development and testing scenarios
 * where prompt engineering requires frequent iterations.
 * </p>
 *
 * @see BaseModelHandler
 * @see LangchainModelHandler
 */
public class LangchainDynamicPromptModelHandler extends BaseModelHandler {
    public static final int DEFAULT_WATCH_DURATION_MINUTES = 15;

    private final String promptPath;
    private int watchDurationMinutes = DEFAULT_WATCH_DURATION_MINUTES;

    /**
     * Constructs a new dynamic prompt handler with the specified model options
     * and prompt file path.
     *
     * @param options    the model configuration options
     * @param promptPath the file path from which to load the prompt
     */
    public LangchainDynamicPromptModelHandler(LangchainModelOptions options, String promptPath) {
        super(options);
        this.promptPath = promptPath;
    }

    /**
     * Constructs a new dynamic prompt handler with the specified model options,
     * prompt file path, and output format.
     *
     * @param options      the model configuration options
     * @param promptPath   the file path from which to load the prompt
     * @param outputFormat the desired output format, represented as a map of keys and values
     */
    public LangchainDynamicPromptModelHandler(LangchainModelOptions options, String promptPath, Map<String, String> outputFormat) {
        super(options, outputFormat);
        this.promptPath = promptPath;
    }

    /**
     * Sets the duration in minutes for how frequently the prompt file should be checked for changes.
     *
     * @param watchDurationMinutes the watch duration in minutes
     * @return this handler instance for method chaining
     */
    public LangchainDynamicPromptModelHandler withWatchDurationMinutes(int watchDurationMinutes) {
        this.watchDurationMinutes = watchDurationMinutes;
        return this;
    }

    /**
     * Returns the file path from which the prompt is loaded.
     *
     * @return the prompt file path
     */
    public String getPromptPath() {
        return promptPath;
    }

    /**
     * Returns the duration in minutes for how frequently the prompt file is checked for changes.
     *
     * @return the watch duration in minutes
     */
    public int getWatchDurationMinutes() {
        return watchDurationMinutes;
    }
}