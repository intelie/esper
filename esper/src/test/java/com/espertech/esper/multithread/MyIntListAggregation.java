package com.espertech.esper.multithread;

import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.epl.agg.AggregationValidationContext;

import java.util.ArrayList;
import java.util.List;

public class MyIntListAggregation extends AggregationSupport {

    private List<Integer> values = new ArrayList<Integer>();

    @Override
    public void validate(AggregationValidationContext validationContext) {
    }

    @Override
    public void enter(Object value) {
        values.add((Integer)value);
    }

    @Override
    public void leave(Object value) {
    }

    @Override
    public Object getValue() {
        return new ArrayList<Integer>(values);
    }

    @Override
    public Class getValueType() {
        return List.class;
    }

    @Override
    public void clear() {
        values.clear();
    }
}
