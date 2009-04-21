package com.espertech.esper.epl.spec;

import com.espertech.esper.collection.Pair;

import java.util.List;

public class AnnotationDesc
{
    private String name;

    // Map of Identifier and value={constant, array of value (Object[]), AnnotationDesc} (exclusive with value)
    private List<Pair<String, Object>> properties;

    public AnnotationDesc(String name, List<Pair<String, Object>> properties)
    {
        this.name = name;
        this.properties = properties;
    }

    public String getName()
    {
        return name;
    }

    public List<Pair<String, Object>> getProperties()
    {
        return properties;
    }
}
