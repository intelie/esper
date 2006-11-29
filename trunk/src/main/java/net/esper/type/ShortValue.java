package net.esper.type;

/**
 * Placeholder for a short-typed value in an event expression.
 */
public final class ShortValue extends PrimitiveValueBase
{
    private Short shortValue;

    public PrimitiveValueType getType()
    {
        return PrimitiveValueType.SHORT;
    }

    public final void parse(String value)
    {
        shortValue = Short.parseShort(value);
    }

    public final Object getValueObject()
    {
        return shortValue;
    }

    public final void setShort(short x)
    {
        this.shortValue = x;
    }

    public final String toString()
    {
        if (shortValue == null)
        {
            return "null";
        }
        return shortValue.toString();
    }
}
