package com.espertech.esperio.representation.axiom;

import com.espertech.esper.client.EventSender;
import com.espertech.esper.core.EPRuntimeEventSender;
import com.espertech.esper.event.EventType;
import com.espertech.esper.plugin.PlugInEventTypeHandler;

public class AxiomEventTypeHandler implements PlugInEventTypeHandler
{
    private final AxiomXMLEventType eventType;

    public AxiomEventTypeHandler(AxiomXMLEventType eventType)
    {
        this.eventType = eventType;
    }

    public EventType getType()
    {
        return eventType;
    }

    public EventSender getSender(EPRuntimeEventSender runtimeEventSender)
    {
        return new AxionEventSender(eventType, runtimeEventSender);
    }
}
