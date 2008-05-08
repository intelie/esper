package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;

public class VariantPropertyDesc
{
    private final Class propertyType;
    private final EventPropertyGetter getter;
    private final boolean isProperty;

    public VariantPropertyDesc(Class propertyType, EventPropertyGetter getter, boolean property)
    {
        this.propertyType = propertyType;
        this.getter = getter;
        isProperty = property;
    }

    public boolean isProperty()
    {
        return isProperty;
    }

    public Class getPropertyType()
    {
        return propertyType;
    }

    public EventPropertyGetter getGetter()
    {
        return getter;
    }
}
