package net.esper.eql.core;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.PropertyAccessException;

public class NaturalEventBean implements EventBean
{
    private final EventType eventBeanType;
    private final Object[] natural;
    private final EventBean optionalSynthetic;

    public NaturalEventBean(EventType eventBeanType, Object[] natural, EventBean optionalSynthetic) {
        this.eventBeanType = eventBeanType;
        this.natural = natural;
        this.optionalSynthetic = optionalSynthetic;
    }

    public EventType getEventType() {
        return eventBeanType;
    }

    public Object get(String property) throws PropertyAccessException {
        if (optionalSynthetic != null)
        {
            return optionalSynthetic.get(property);
        }
        throw new PropertyAccessException("Property access not allowed for natural events without the synthetic event present");
    }

    public Object getUnderlying() {
        return Object[].class;
    }

    public Object[] getNatural()
    {
        return natural;
    }

    public EventBean getOptionalSynthetic() {
        return optionalSynthetic;
    }
}
