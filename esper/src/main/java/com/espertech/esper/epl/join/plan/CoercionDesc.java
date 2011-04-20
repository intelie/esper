package com.espertech.esper.epl.join.plan;

public class CoercionDesc {

    private boolean coerce;
    private Class[] coercionTypes;

    public CoercionDesc(boolean coerce, Class[] coercionTypes) {
        this.coerce = coerce;
        this.coercionTypes = coercionTypes;
    }

    public boolean isCoerce() {
        return coerce;
    }

    public Class[] getCoercionTypes() {
        return coercionTypes;
    }
}
