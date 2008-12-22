package com.espertech.esper.event.xml;

import java.util.List;

public class SchemaModel
{
    private List<SchemaElementComplex> components;
    private List<String> namespaces;

    public SchemaModel(List<SchemaElementComplex> components, List<String> namespaces)
    {
        this.components = components;
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
