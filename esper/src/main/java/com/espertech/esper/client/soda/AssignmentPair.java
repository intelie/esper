package com.espertech.esper.client.soda;

import java.io.Serializable;

public class AssignmentPair implements Serializable {
    private String name;
    private Expression value;

    public AssignmentPair() {
    }

    public AssignmentPair(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
