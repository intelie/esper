package com.espertech.esper.support.bean;

public class SupportBeanCtorOne {

    private final String string;
    private final Integer intBoxed;
    private final int intPrimitive;
    private final boolean boolPrimitive;

    public SupportBeanCtorOne(String string, Integer intBoxed, int intPrimitive, boolean boolPrimitive) {
        this.string = string;
        this.intBoxed = intBoxed;
        this.intPrimitive = intPrimitive;
        this.boolPrimitive = boolPrimitive;
    }

    public SupportBeanCtorOne(String string, Integer intBoxed, int intPrimitive) {
        this.string = string;
        this.intBoxed = intBoxed;
        this.intPrimitive = intPrimitive;
        this.boolPrimitive = false;
    }

    public SupportBeanCtorOne(String string, Integer intBoxed) {
        this.string = string;
        this.intBoxed = intBoxed;
        this.intPrimitive = 99;
        this.boolPrimitive = false;
    }

    public SupportBeanCtorOne(String string) {
        throw new RuntimeException("This is a test exception");
    }

    public String getString() {
        return string;
    }

    public Integer getIntBoxed() {
        return intBoxed;
    }

    public int getIntPrimitive() {
        return intPrimitive;
    }

    public boolean isBoolPrimitive() {
        return boolPrimitive;
    }
}
