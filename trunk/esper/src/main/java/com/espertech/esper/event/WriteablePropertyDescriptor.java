package com.espertech.esper.event;

import java.lang.reflect.Method;

public class WriteablePropertyDescriptor
{
    private String propertyName;
    private Class type;
    private Method writeMethod;

    public WriteablePropertyDescriptor(String propertyName, Class type, Method writeMethod)
    {
        this.propertyName = propertyName;
        this.type = type;
        this.writeMethod = writeMethod;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public Class getType()
    {
        return type;
    }

    public Method getWriteMethod()
    {
        return writeMethod;
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        WriteablePropertyDescriptor that = (WriteablePropertyDescriptor) o;

        if (!propertyName.equals(that.propertyName))
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
