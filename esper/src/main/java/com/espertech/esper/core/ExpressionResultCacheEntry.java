package com.espertech.esper.core;

public class ExpressionResultCacheEntry<R, T> {
    private final R reference;
    private final T result;

    public ExpressionResultCacheEntry(R reference, T result) {
        this.reference = reference;
        this.result = result;
    }

    public R getReference() {
        return reference;
    }

    public T getResult() {
        return result;
    }
}
