package com.espertech.esper.epl.enummethod.dot;

public enum EnumMethodInputEnum {
    SCALAR_NUMERIC,
    SCALAR_ANY,
    EVENTCOLL,
    ANY;

    public boolean isScalar() {
        return this == SCALAR_ANY || this == SCALAR_NUMERIC;
    }
}
