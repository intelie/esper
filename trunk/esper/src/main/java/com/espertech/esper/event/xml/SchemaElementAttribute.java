package com.espertech.esper.event.xml;

public class SchemaElementAttribute implements SchemaItem
{
    private final String namespace;
    private final String name;
    private final short type;     // Types from XSSimpleType

    /**
     * Ctor.
     * @param namespace namespace
     * @param name name
     * @param type attribute type
     */
    public SchemaElementAttribute(String namespace, String name, short type)
    {
        this.name = name;
        this.namespace = namespace;
        this.type = type;
    }

    /**
     * Returns the namespace.
     * @return namespace
     */
    public String getNamespace()
    {
        return namespace;
    }

    /**
     * Returns the name.
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the type.
     * @return type
     */
    public short getType()
    {
        return type;
    }

    public String toString()
    {
        return "Attribute " + namespace + " " + name;
    }
}
