package com.espertech.esper.event;

import com.espertech.esper.client.EventSender;
import com.espertech.esper.client.EPException;
import com.espertech.esper.core.EPRuntimeEventSender;

import java.util.Map;

/**
 * Event sender for map-backed events.
 * <p>
 * Allows sending only event objects of type map, does not check map contents. Any other event object generates an error.
 */
public class EventSenderMap implements EventSender
{
    private final EPRuntimeEventSender runtimeEventSender;
    private final MapEventType mapEventType;

    /**
     * Ctor.
     * @param runtimeEventSender for processing events
     * @param mapEventType the event type
     */
    public EventSenderMap(EPRuntimeEventSender runtimeEventSender, MapEventType mapEventType)
    {
        this.runtimeEventSender = runtimeEventSender;
        this.mapEventType = mapEventType;
    }

    public void sendEvent(Object event)
    {
        if (!(event instanceof Map))
        {
            throw new EPException("Unexpected event object of type " + event.getClass().getName() + ", expected " + Map.class.getName());
        }
        Map<String, Object> map = (Map<String, Object>) event;
        MapEventBean mapEvent = new MapEventBean(map, mapEventType);
        runtimeEventSender.processWrappedEvent(mapEvent);
    }
}
