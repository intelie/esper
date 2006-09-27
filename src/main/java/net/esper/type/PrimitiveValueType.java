package net.esper.type;

/**
 * Enumeration of types of primitive values.
 */
public enum PrimitiveValueType
{
    /**
     * Byte.
     */
    BYTE("byte"),

    /**
     * Short.
     */
    SHORT("short"),

    /**
     * Integer.
     */
    INTEGER("int"),

    /**
     * Long.
     */
    LONG("long"),

    /**
     * Float.
     */
    FLOAT("float"),

    /**
     * Double.
     */
    DOUBLE("double"),

    /**
     * Boolean.
     */
    BOOL("bool"),

    /**
     * String.
     */
    STRING("string");

    private String typeName;

    private PrimitiveValueType(String typeName)
    {
        this.typeName = typeName;
    }

    /**
     * Returns the name of the type.
     * @return type name
     */
    public String getTypeName()
    {
        return typeName;
    }

    public String toString()
    {
        return typeName;
    }
}
