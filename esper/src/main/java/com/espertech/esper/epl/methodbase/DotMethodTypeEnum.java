package com.espertech.esper.epl.methodbase;

public enum DotMethodTypeEnum {
    ENUM("enumeration"),
    DATETIME("date-time");

    private final String typeName;

    private DotMethodTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
