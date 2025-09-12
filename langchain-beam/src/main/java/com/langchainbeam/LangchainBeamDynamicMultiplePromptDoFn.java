package com.langchainbeam;

import com.langchainbeam.model.LangchainBeamOutput;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.vendor.guava.v32_1_2_jre.com.google.common.collect.Iterables;

import java.util.Map;

public class LangchainBeamDynamicMultiplePromptDoFn extends BaseLangchainDoFn<LangchainDynamicPromptModelHandler, KV<String, String>> {
    /**
     * Constructs a new BaseLangchainDoFn with the specified model handler.
     *
     * @param handler The model handler that provides configuration and behavior for this DoFn
     */
    protected LangchainBeamDynamicMultiplePromptDoFn(LangchainDynamicPromptModelHandler handler) {
        super(handler);
    }

    @ProcessElement
    public void processElement(
            @SideInput("prompt") Map<String, Iterable<String>> promptMap,
            @Element KV<String, String> element,
            OutputReceiver<LangchainBeamOutput> output
    ) {
        final var promptList = promptMap.get(element.getKey());
        if (promptList == null) {
            final LangchainBeamOutput lbModelOutput = LangchainBeamOutput.builder()
                    .inputElement(element.getValue())
                    .output("Sorry, the prompt is null. <CODE : 404>")
                    .build();

            output.output(lbModelOutput);
        } else {
            final String lastVersionPrompt = Iterables.getLast(promptList);
            final String finalPrompt = this.getFinalPrompt(element.getValue(), lastVersionPrompt);
            final String modelOutput = this.doGenerate(finalPrompt);
            LangchainBeamOutput lbModelOutput = LangchainBeamOutput.builder().inputElement(element.getValue())
                    .output(modelOutput).build();
            output.output(lbModelOutput);
        }

    }
}
