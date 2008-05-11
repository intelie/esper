package com.espertech.esper.event.property;

/**
 * All properties have a property name and this is the abstract base class that serves up the property name.
 */
public abstract class PropertyBase implements Property
{
    /**
     * Property name.
     */
    protected String propertyNameAtomic;

    /**
     * Ctor.
     * @param propertyName is the name of the property
     */
    public PropertyBase(String propertyName)
    {
        this.propertyNameAtomic = propertyName;
    }

    /**
     * Returns the atomic property name, which is a part of all of the full (complex) property name.
     * @return atomic name of property
     */
    public String getPropertyNameAtomic()
    {
        return propertyNameAtomic;
    }
}
