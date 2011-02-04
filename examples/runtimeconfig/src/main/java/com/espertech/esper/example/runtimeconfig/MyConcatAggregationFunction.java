package com.espertech.esper.example.runtimeconfig;

import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.epl.agg.AggregationValidationContext;

public class MyConcatAggregationFunction extends AggregationSupport {
    private final static char DELIMITER = ' ';
    private StringBuilder builder;
    private String delimiter;

    public MyConcatAggregationFunction() {
        super();
        builder = new StringBuilder();
        delimiter = "";
    }

    public void validate(AggregationValidationContext validationContext) {
        if ((validationContext.getParameterTypes().length != 1) ||
            (validationContext.getParameterTypes()[0] != String.class)) {
            throw new IllegalArgumentException("Concat aggregation requires a single parameter of type String");
        }
    }

    public void enter(Object value) {
        if (value != null) {
            builder.append(delimiter);
            builder.append(value.toString());
            delimiter = String.valueOf(DELIMITER);
        }
    }

    public void leave(Object value) {
        if (value != null) {
            builder.delete(0, value.toString().length() + 1);
        }
    }

    public Class getValueType() {
        return String.class;
    }

    public Object getValue() {
        return builder.toString();
    }

    public void clear() {
        builder = new StringBuilder();
        delimiter = "";
    }
}
