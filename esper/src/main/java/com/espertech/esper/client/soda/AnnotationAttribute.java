package com.espertech.esper.client.soda;

import java.io.Serializable;

/**
 * Represents a single annotation attribute, the value of which may itself be a single value, array or further annotations.
 */
public class AnnotationAttribute implements Serializable {

    private String name;
    private Object value;

    public AnnotationAttribute() {
    }

    public AnnotationAttribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}