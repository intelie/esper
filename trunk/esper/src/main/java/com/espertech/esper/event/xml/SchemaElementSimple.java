package com.espertech.esper.event.xml;

public class SchemaElementSimple implements SchemaElement
{
    private final String name;
    private final String namespace;
    private final short xsSimpleType;     // Types from XSSimpleType
    private final String typeName;
    private final boolean isArray;

    /**
     * Ctor.
     * @param name name
     * @param namespace namespace
     * @param type is the simple element type
     * @param isArray if unbound
     */
    public SchemaElementSimple(String name, String namespace, short type, String typeName, boolean isArray)
    {
        this.name = name;
        this.namespace = namespace;
        this.xsSimpleType = type;
        this.isArray = isArray;
        this.typeName = typeName;
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
    public short getXsSimpleType()
    {
        return xsSimpleType;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public boolean isArray()
    {
        return isArray;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public String toString()
    {
        return "Simple " + namespace + " " + name;
    }
}
