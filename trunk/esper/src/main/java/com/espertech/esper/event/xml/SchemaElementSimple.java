package com.espertech.esper.event.xml;

public class SchemaElementSimple implements SchemaElement
{
    private final String name;
    private final String namespace;
    private final short type;     // Types from XSSimpleType
    private final boolean isArray;

    /**
     * Ctor.
     * @param name name
     * @param namespace namespace
     * @param type is the simple element type
     * @param isArray if unbound
     */
    public SchemaElementSimple(String name, String namespace, short type, boolean isArray)
    {
        this.name = name;
        this.namespace = namespace;
        this.type = type;
        this.isArray = isArray;
    }

    /**
     * Returns element name.
     * @return element name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns type.
     * @return type
     */
    public short getType()
    {
        return type;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public boolean isArray()
    {
        return isArray;
    }

    public String toString()
    {
        return "Simple " + namespace + " " + name;
    }
}
