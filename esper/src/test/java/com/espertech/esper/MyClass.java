package com.espertech.esper;

import java.io.Serializable;

// TODO - remove me
public class MyClass implements MyInterface, Serializable {

    private String value;

    public MyClass() {
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
