package com.espertech.esper.event.xml;

public class SchemaItemAttribute implements SchemaItem
{
    private final String namespace;
    private final String name;
    private final short xsSimpleType;     // Types from XSSimpleType
    private final String typeName;

    /**
     * Ctor.
     * @param namespace namespace
     * @param name name
     * @param type attribute type
     */
    public SchemaItemAttribute(String namespace, String name, short type, String typeName)
    {
        this.name = name;
        this.namespace = namespace;
        this.xsSimpleType = type;
        this.typeName = typeName;
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
    public short getXsSimpleType()
    {
        return xsSimpleType;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public String toString()
    {
        return "Attribute " + namespace + " " + name;
    }
}
