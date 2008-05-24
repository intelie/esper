package com.espertech.esper.event.vaevent;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;

/**
 * An event bean that represents multiple potentially disparate underlying events and presents a unified face
 * across each such types or even any type.
 */
public class VariantEventBean implements EventBean, VariantEvent
{
    private final VariantEventType variantEventType;
    private final EventBean underlyingEventBean;

    /**
     * Ctor.
     * @param variantEventType the event type
     * @param underlying the event
     */
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

    /**
     * Returns the underlying event.
     * @return underlying event
     */
    public EventBean getUnderlyingEventBean()
    {
        return underlyingEventBean;
    }

}
