package com.espertech.esper.regression.event;

import com.espertech.esper.plugin.PlugInEventTypeHandler;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.core.EPRuntimeEventSender;

public class MyPlugInPropertiesEventTypeHandler implements PlugInEventTypeHandler
{
    private final MyPlugInPropertiesEventType eventType;

    public MyPlugInPropertiesEventTypeHandler(MyPlugInPropertiesEventType eventType)
    {
        this.eventType = eventType;
    }

    public EventSender getSender(EPRuntimeEventSender runtimeEventSender)
    {
        return new MyPlugInPropertiesEventSender(eventType, runtimeEventSender);
    }

    public EventType getType()
    {
        return eventType;
    }
}
