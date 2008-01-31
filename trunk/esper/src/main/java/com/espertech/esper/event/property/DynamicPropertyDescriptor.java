package com.espertech.esper.event.property;

import net.sf.cglib.reflect.FastMethod;

/**
 * Provides method information for dynamic (unchecked) properties of each class for use in obtaining property values.
 */
public class DynamicPropertyDescriptor
{
    private Class clazz;
    private FastMethod method;
    private boolean hasParameters;

    /**
     * Ctor.
     * @param clazz the class to match when looking for a method
     * @param method the fast method to call
     * @param hasParameters true if the method takes parameters
     */
    public DynamicPropertyDescriptor(Class clazz, FastMethod method, boolean hasParameters)
    {
        this.clazz = clazz;
        this.method = method;
        this.hasParameters = hasParameters;
    }

    /**
     * Returns the class for the method.
     * @return class to match on
     */
    public Class getClazz()
    {
        return clazz;
    }

    /**
     * Returns the method to invoke.
     * @return method to invoke
     */
    public FastMethod getMethod()
    {
        return method;
    }

    /**
     * Returns true if the method takes parameters.
     * @return indicator if parameters are required
     */
    public boolean isHasParameters()
    {
        return hasParameters;
    }
}
