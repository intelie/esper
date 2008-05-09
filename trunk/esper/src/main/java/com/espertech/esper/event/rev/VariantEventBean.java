package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;

public class VariantEventBean implements EventBean
{
    private final VariantEventType variantEventType;
    private final EventBean underlyingEventBean;

    public VariantEventBean(VariantEventType variantEventType, EventBean underlying)
    {
        this.variantEventType = variantEventType;
        this.underlyingEventBean = underlying;
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
        return underlyingEventBean.getUnderlying();
    }

    public EventBean getUnderlyingEventBean()
    {
        return underlyingEventBean;
    }

    public VariantEventType getVariantEventType()
    {
        return variantEventType;
    }
}
