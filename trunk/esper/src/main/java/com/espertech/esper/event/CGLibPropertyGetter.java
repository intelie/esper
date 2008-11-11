/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import net.sf.cglib.reflect.FastMethod;
import java.lang.reflect.InvocationTargetException;

/**
 * Property getter using CGLib's FastMethod instance.
 */
public class CGLibPropertyGetter implements EventPropertyGetter
{
    private final FastMethod fastMethod;

    /**
     * Constructor.
     * @param fastMethod is the method to use to retrieve a value from the object.
     */
    public CGLibPropertyGetter(FastMethod fastMethod)
    {
        this.fastMethod = fastMethod;
    }

    public final Object get(EventBean obj) throws PropertyAccessException
    {
        Object underlying = obj.getUnderlying();

        try
        {
            return fastMethod.invoke(underlying, null);
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
        return "CGLibPropertyGetter " +
                "fastMethod=" + fastMethod.toString();
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }
}
