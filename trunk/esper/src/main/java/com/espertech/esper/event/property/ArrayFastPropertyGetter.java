/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.property;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.PropertyAccessException;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Array;

/**
 * Getter for an array property identified by a given index, using the CGLIB fast method.
 */
public class ArrayFastPropertyGetter implements EventPropertyGetter
{
    private final FastMethod fastMethod;
    private final int index;

    /**
     * Constructor.
     * @param fastMethod is the method to use to retrieve a value from the object
     * @param index is tge index within the array to get the property from
     */
    public ArrayFastPropertyGetter(FastMethod fastMethod, int index)
    {
        this.index = index;
        this.fastMethod = fastMethod;

        if (index < 0)
        {
            throw new IllegalArgumentException("Invalid negative index value");
        }
    }

    public final Object get(EventBean obj) throws PropertyAccessException
    {
        Object underlying = obj.getUnderlying();

        try
        {
            Object value = fastMethod.invoke(underlying, null);
            if (Array.getLength(value) <= index)
            {
                return null;
            }
            return Array.get(value, index);
        }
        catch (ClassCastException e)
        {
            throw new PropertyAccessException("Mismatched getter instance to event bean type");
        }
        catch (InvocationTargetException e)
        {
            throw new PropertyAccessException(e);
        }
    }

    public String toString()
    {
        return "ArrayFastPropertyGetter " +
                " fastMethod=" + fastMethod.toString() +
                " index=" + index;
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }

    public EventBean getFragment(EventBean eventBean)
    {
        return null; // TODO
    }

    public Integer getIndexSize(EventBean eventBean)
    {
        return null; // TODO
    }    
}
