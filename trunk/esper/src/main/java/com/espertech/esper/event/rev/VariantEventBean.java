package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class VariantEventBean implements EventBean
{
    private final VariantEventType variantEventType;
    private final EventBean underlying;

    public VariantEventBean(VariantEventType variantEventType, EventBean underlying)
    {
        this.variantEventType = variantEventType;
        this.underlying = underlying;
    }

    public EventType getEventType()
    {
        return variantEventType;
    }

    public Object get(String property) throws PropertyAccessException
    {
        EventPropertyGetter getter = variantEventType.getGetter(property);
        if (getter == null)
        {
            return null;
        }
        return getter.get(this);
    }

    public Object getUnderlying()
    {
        return underlying.getUnderlying();
    }

    public VariantEventType getVariantEventType()
    {
        return variantEventType;
    }
}
