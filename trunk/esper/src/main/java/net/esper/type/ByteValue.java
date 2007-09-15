/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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
