package net.esper.event.property;

import net.sf.cglib.reflect.FastMethod;

public class DynamicPropertyDescriptor
{
    private Class clazz;
    private FastMethod method;
    private boolean hasParameters;

    public DynamicPropertyDescriptor(Class clazz, FastMethod method, boolean hasParameters)
    {
        this.clazz = clazz;
        this.method = method;
        this.hasParameters = hasParameters;
    }

    public Class getClazz()
    {
        return clazz;
    }

    public FastMethod getMethod()
    {
        return method;
    }

    public boolean isHasParameters()
    {
        return hasParameters;
    }
}
