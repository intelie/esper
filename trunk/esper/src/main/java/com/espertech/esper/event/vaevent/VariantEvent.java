package com.espertech.esper.event.vaevent;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.event.EventPropertyGetter;

public interface VariantEvent
{
    /**
     * Returns the underlying event.
     * @return underlying event
     */
    public EventBean getUnderlyingEventBean();
}
