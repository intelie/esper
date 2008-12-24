package com.espertech.esper.event.xml;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class SchemaModel
{
    private List<SchemaElementComplex> components;
    private List<String> namespaces;

    public SchemaModel(List<SchemaElementComplex> components, List<String> namespaces)
    {
        this.components = components;
        this.namespaces = namespaces;
    }

    public SchemaModel(SchemaElementComplex component, List<String> namespaces)
    {
        components = new ArrayList<SchemaElementComplex>(1);
        components.add(component);
        this.namespaces = namespaces;
    }

    public List<SchemaElementComplex> getComponents()
    {
        return components;
    }

    public List<String> getNamespaces()
    {
        return namespaces;
    }
}
