/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.type;

/**
 * Placeholder for a boolean value in an event expression.
 */
public final class BoolValue extends PrimitiveValueBase
{
    private Boolean boolValue;

    public PrimitiveValueType getType()
    {
        return PrimitiveValueType.BOOL;
    }

    /**
     * Constructor.
     * @param boolValue sets the value.
     */
    public BoolValue(Boolean boolValue)
    {
        this.boolValue = boolValue;
    }

    /**
     * Constructor.
     */
    public BoolValue()
    {
    }

    /**
     * Parse the boolean string.
     * @param value is a bool value
     * @return parsed boolean
     */
    public static boolean parseString(String value)
    {
        if (!(value.equals("true")) && (!(value.equals("false"))))
        {
            throw new IllegalArgumentException("Boolean value '" + value + "' cannot be converted to boolean");
        }
        return Boolean.parseBoolean(value);
    }

    /**
     * Parse the string array returning a boolean array.
     * @param values - string array
     * @return typed array
     */
    public static boolean[] parseString(String[] values)
    {
        boolean[] result = new boolean[values.length];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = parseString(values[i]);
        }
        return result;
    }

    public final void parse(String value)
    {
        boolValue = parseString(value);
    }

    public final Object getValueObject()
    {
        return boolValue;
    }

    public final void setBoolean(boolean x)
    {
        this.boolValue = x;
    }

    public final String toString()
    {
        if (boolValue == null)
        {
            return "null";
        }
        return boolValue.toString();
    }
}
