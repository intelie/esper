package net.esper.type;

/**
 * Placeholder for a byte value in an event expression.
 */
public final class ByteValue extends PrimitiveValueBase
{
    private Byte byteValue;

    public PrimitiveValueType getType()
    {
        return PrimitiveValueType.BYTE;
    }

    public final void parse(String value)
    {
        byteValue = Byte.parseByte(value);
    }

    public final Object getValueObject()
    {
        return byteValue;
    }

    public final void setByte(byte x)
    {
        this.byteValue = x;
    }

    public final String toString()
    {
        if (byteValue == null)
        {
            return "null";
        }
        return byteValue.toString();
    }
}
