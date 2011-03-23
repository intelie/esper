package com.espertech.esper.epl.methodbase;

public enum DotMethodFPInputEnum {
    SCALAR_NUMERIC,
    SCALAR_ANY,
    EVENTCOLL,
    ANY;

    public boolean isScalar() {
        return this == SCALAR_ANY || this == SCALAR_NUMERIC;
    }
}
