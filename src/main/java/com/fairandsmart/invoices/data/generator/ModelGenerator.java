package com.fairandsmart.invoices.data.generator;

public interface ModelGenerator<T> {

    T generate(GenerationContext ctx);

}
