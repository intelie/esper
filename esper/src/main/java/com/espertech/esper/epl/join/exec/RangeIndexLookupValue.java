package com.espertech.esper.epl.join.exec;

public abstract class RangeIndexLookupValue {
    private Object value;

    public RangeIndexLookupValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
