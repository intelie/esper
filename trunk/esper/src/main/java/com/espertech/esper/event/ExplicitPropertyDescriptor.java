package com.espertech.esper.event;

import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.EventPropertyGetter;

public class ExplicitPropertyDescriptor
{
    private final EventPropertyGetter getter;
    private final EventPropertyDescriptor descriptor;
    private final String optionalFragmentTypeAlias;
    private final boolean isFragmentArray;

    public ExplicitPropertyDescriptor(EventPropertyDescriptor descriptor, EventPropertyGetter getter, boolean fragmentArray, String optionalFragmentTypeAlias)
    {
        this.descriptor = descriptor;
        this.getter = getter;
        isFragmentArray = fragmentArray;
        this.optionalFragmentTypeAlias = optionalFragmentTypeAlias;
    }

    public EventPropertyDescriptor getDescriptor()
    {
        return descriptor;
    }

    public EventPropertyGetter getGetter()
    {
        return getter;
    }

    public String getOptionalFragmentTypeAlias()
    {
        return optionalFragmentTypeAlias;
    }

    public boolean isFragmentArray()
    {
        return isFragmentArray;
    }

    public String toString()
    {
        return descriptor.getPropertyName();
    }
}
