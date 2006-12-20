package net.esper.type;

/**
 * Placeholder for a String value in an event expression.
 */
public final class StringValue extends PrimitiveValueBase
{
    private String stringValue;

    /**
     * Constructor.
     * @param stringValue sets initial value
     */
    public StringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }

    /**
     * Constructor.
     */
    public StringValue()
    {
    }

    public PrimitiveValueType getType()
    {
        return PrimitiveValueType.STRING;
    }

    /**
     * Parse the string array returning a string array.
     * @param values - string array
     * @return typed array
     */
    public static String[] parseString(String[] values)
    {
        String[] result = new String[values.length];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = parseString(values[i]);
        }
        return result;
    }

    public final void parse(String value)
    {
        stringValue = parseString(value);
    }

    public final Object getValueObject()
    {
        return stringValue;
    }

    public final void setString(String x)
    {
        this.stringValue = x;
    }

    public final String toString()
    {
        if (stringValue == null)
        {
            return "null";
        }
        return stringValue;
    }

    /**
     * Parse the string literal consisting of text between double-quotes or single-quotes.
     * @param value is the text wthin double or single quotes
     * @return parsed value
     */
    public static String parseString(String value)
    {
        if ( (value.startsWith("\"")) & (value.endsWith("\"")) ||
             (value.startsWith("'")) & (value.endsWith("'")) )
        {
            if (value.length() > 1)
            {
                String stringValue = value.substring(1, value.length() - 1);
                return stringValue;
            }
        }

        throw new IllegalArgumentException("String value of '" + value + "' cannot be parsed");
    }
}
