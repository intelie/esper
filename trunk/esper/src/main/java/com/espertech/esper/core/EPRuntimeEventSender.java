package com.espertech.esper.core;

import com.espertech.esper.event.EventBean;

/**
 * For use by {@link com.espertech.esper.client.EventSender} for direct feed of wrapped events for processing.
 */
public interface EPRuntimeEventSender
{
    /**
     * Equivalent to the sendEvent method of EPRuntime, for use to process an known event.
     * @param eventBean is the event object wrapped by an event bean providing the event metadata
     */
    public void processWrappedEvent(EventBean eventBean);   
}
