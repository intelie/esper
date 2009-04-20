package com.espertech.esper.epl.spec;

import java.util.Map;

public class AnnotationDesc
{
    private String name;

    // Value={constant, array of value, AnnotationDesc}  (exclusive with Map)
    private Object value;

    // Map of Identifier and value={constant, array of value (Object[]), AnnotationDesc} (exclusive with value)
    private Map<String, Object> properties;


    public AnnotationDesc(String name, Object value, Map<String, Object> properties)
    {
        this.name = name;
        this.properties = properties;
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public Map<String, Object> getProperties()
    {
        return properties;
    }

    public Object getValue()
    {
        return value;
    }
}
