/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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

    public final static short parseString(String value)
    {
        return Short.parseShort(value);
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
