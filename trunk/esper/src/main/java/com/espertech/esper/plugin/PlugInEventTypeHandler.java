package com.espertech.esper.plugin;

import com.espertech.esper.event.EventType;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.core.EPRuntimeSPI;
import com.espertech.esper.core.EPRuntimeEventSender;

/**
 * Provided once by an {@link PlugInEventRepresentation} for any event type it creates.
 */
public interface PlugInEventTypeHandler
{
    /**
     * Returns the event type.
     * @return event type.
     */
    public EventType getType();

    /**
     * Returns a facility responsible for converting or wrapping event objects.
     * @param runtimeEventSender for sending events into the engine
     * @return sender
     */
    public EventSender getSender(EPRuntimeEventSender runtimeEventSender);
}
