/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.bean;

import java.lang.reflect.Method;

public class InternalWritablePropDescriptor
{
    private String propertyName;
    private Method writeMethod;

    public InternalWritablePropDescriptor(String propertyName, Method writeMethod)
    {
        this.propertyName = propertyName;
        this.writeMethod = writeMethod;
    }

    /**
     * Return the property name, for mapped and indexed properties this is just the property name
     * without parantheses or brackets.
     * @return property name
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    public Method getWriteMethod()
    {
        return writeMethod;
    }

    public String toString()
    {
        return  "propertyName=" + propertyName +
                " writeMethod=" + writeMethod;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof InternalWritablePropDescriptor))
        {
            return false;
        }
        InternalWritablePropDescriptor otherDesc = (InternalWritablePropDescriptor) other;
        if (!otherDesc.propertyName.equals(propertyName))
        {
            return false;
        }
        if  ( ((otherDesc.writeMethod == null) && (writeMethod != null)) ||
              ((otherDesc.writeMethod != null) && (writeMethod == null)) )
        {
            return false;
        }
        if ((otherDesc.writeMethod != null) && (writeMethod != null) &&
            (!otherDesc.writeMethod.equals(writeMethod)))
        {
            return false;
        }
        return true;
    }

    public int hashCode()
    {
        return propertyName.hashCode();
    }
}
