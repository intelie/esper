package com.espertech.esper.event;

import com.espertech.esper.client.EventSender;
import com.espertech.esper.client.EPException;
import com.espertech.esper.core.EPRuntimeEventSender;

import java.util.Map;

public class EventSenderMap implements EventSender
{
    private final EPRuntimeEventSender runtimeEventSender;
    private final MapEventType mapEventType;

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
