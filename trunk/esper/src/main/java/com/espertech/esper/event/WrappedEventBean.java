package com.espertech.esper.event;

import java.util.Map;

public interface WrappedEventBean
{
    /**
     * Returns the wrapped event.
     * @return wrapped event
     */
    public EventBean getUnderlyingEvent();

    /**
     * Returns decorating properties.
     * @return property name and values
     */
    public Map<String, Object> getDecoratingProperties();
}
