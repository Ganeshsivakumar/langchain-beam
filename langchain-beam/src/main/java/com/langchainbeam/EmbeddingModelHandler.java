package com.langchainbeam;

import java.io.Serializable;

import com.langchainbeam.model.EmbeddingModelOptions;

/**
 * A handler for managing embedding model operations in an Apache Beam pipeline.
 * 
 * <p>
 * The {@link EmbeddingModelHandler} is responsible for configuring and
 * utilizing
 * an embedding model based on the provided {@link EmbeddingModelOptions}. It
 * acts as
 * a bridge between the embedding model and the Beam transforms, ensuring that
 * embeddings are generated with the specified model settings.
 * </p>
 * 
 * <h3>Key Responsibilities:</h3>
 * <ul>
 * <li>Holds and manages {@link EmbeddingModelOptions}, which define the model
 * name, dimensions, and other relevant settings.</li>
 * <li>Provides access to the model options via the {@link #getOptions()}
 * method.</li>
 * </ul>
 * 
 * <h3>Usage:</h3>
 * 
 * <pre>{@code
 * EmbeddingModelOptions options = OpenAiEmbeddingModelOptions.builder()
 *         .apikey("your-api-key")
 *         .modelName("text-embedding-ada-002")
 *         .build();
 * 
 * EmbeddingModelHandler handler = new EmbeddingModelHandler(options);
 * 
 * EmbeddingModelOptions retrievedOptions = handler.getOptions();
 * System.out.println("Using model: " + retrievedOptions.getModelName());
 * }</pre>
 */
public class EmbeddingModelHandler implements Serializable {

    private final EmbeddingModelOptions options;

    /**
     * Constructs an {@link EmbeddingModelHandler} with the specified options.
     * 
     * @param modelOptions the options defining the embedding model configuration.
     *                     Must not be {@code null}.
     */
    public EmbeddingModelHandler(EmbeddingModelOptions modelOptions) {
        this.options = modelOptions;
    }

    /**
     * Retrieves the {@link EmbeddingModelOptions} associated with this handler.
     * 
     * @return the options defining the embedding model configuration.
     */
    public EmbeddingModelOptions getOptions() {
        return options;
    }

}
