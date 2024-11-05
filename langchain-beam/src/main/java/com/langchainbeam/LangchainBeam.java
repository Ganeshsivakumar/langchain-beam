package com.langchainbeam;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class LangchainBeam<T> extends PTransform<PCollection<T>, PCollection<String>> {
    private final LangchainModelHandler handler;

    private LangchainBeam(LangchainModelHandler handler) {
        this.handler = handler;
    }

    @Override
    public PCollection<String> expand(PCollection<T> input) {
        return input.apply("LangchainBeam Model Transform", ParDo.of(new LangchainBeamDoFn<>(handler)));
    }

    public static <T> LangchainBeam<T> run(LangchainModelHandler handler) {
        return new LangchainBeam<>(handler);
    }

}
