# Use LLM transfrom

This notebook shares how to create a example sentiment analysis pipeline using apache beam
and use LLM transform to catragorise the sentiment of product reviews
and run the pipeline locally using direct runner.

The pipeline

- loads the csv file that contains reviews and feedback of a product
- uses LLM transform to catragorise reviews
- print the model output

**1. Create a maven project**

```java
mvn org.apache.maven.plugins:maven-archetype-plugin:3.1.2:generate \
    -DarchetypeArtifactId="maven-archetype-quickstart" \
    -DarchetypeGroupId="org.apache.maven.archetypes" \
    -DarchetypeVersion="1.4" \
    -DgroupId="com.example" \
    -DartifactId="sentimentanalysis"

```

**2. Add required dependencies in pom.xml**

```xml
<dependency>
    <groupId>org.apache.beam</groupId>
    <artifactId>beam-runners-direct-java</artifactId>
    <version>2.60.0</version>
</dependency>
<dependency>
    <groupId>org.apache.beam</groupId>
    <artifactId>beam-sdks-java-core</artifactId>
    <version>2.60.0</version>
</dependency>
<dependency>
    <groupId>io.github.ganeshsivakumar</groupId>
    <artifactId>langchain-beam</artifactId>
    <version>0.2.0</version>
</dependency>

```

**3. Create the beam pipeline with LLM transform**

```java

import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

import com.langchainbeam.LangchainBeam;
import com.langchainbeam.LangchainModelHandler;
import com.langchainbeam.model.LangchainBeamOutput;
import com.langchainbeam.model.openai.OpenAiModelOptions;

public class SentimentAnalysis {

    public static void main(String[] args) {

         // instruction prompt
        String prompt = "Categorize the product review as Positive or Negative.";

        String apiKey = System.getenv("OPENAI_API_KEY");

        // Create model options
        OpenAiModelOptions modelOptions = OpenAiModelOptions.builder()
                .modelName("gpt-4o-mini")
                .apiKey(apiKey)
                .build();

        // create handler
        LangchainModelHandler sentimentHandler = new LangchainModelHandler(modelOptions, prompt);

        // create beam pipeline options
        PipelineOptions options = PipelineOptionsFactory.create();
        // set DirectRunner
        options.setRunner(DirectRunner.class);

        // create beam pipeline
        Pipeline p = Pipeline.create(options);

        p.apply(TextIO.read().from("/home/ganesh/Downloads/product_reviews.csv"))// set file path
                .apply(LangchainBeam.run(sentimentHandler)) // apply the LLM transform.
                .apply(ParDo.of(new DoFn<LangchainBeamOutput, Void>() {

                    @ProcessElement
                    public void processElement(@Element LangchainBeamOutput out) {
                        System.out.println("Model Output: " + out.getOutput());
                    }
                }));

        p.run();

    }
}
```

Check out the example sentiment analysis pipeline with product reviews data on [github](https://github.com/Ganeshsivakumar/langchain-beam/blob/main/example/langchain-beam-example/src/main/java/com/langchainbeam/example/SentimentAnalysis.java)
