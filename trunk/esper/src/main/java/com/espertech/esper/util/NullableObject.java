package com.espertech.esper.util;

public class NullableObject<T>
{
    private T object;

    public NullableObject(T object)
    {
        this.object = object;
    }

    public T getObject()
    {
        return object;
    }

    public void setObject(T object)
    {
        this.object = object;
    }
}
