package com.espertech.esper.regression.event;

import com.espertech.esper.plugin.PlugInEventTypeHandler;
import com.espertech.esper.plugin.PlugInEventTypeHandlerContext;
import com.espertech.esper.event.EventType;
import com.espertech.esper.client.EventSender;

import java.util.Set;

public class MyPlugInPropertiesEventTypeHandler implements PlugInEventTypeHandler
{
    private final EventType eventType;

    public MyPlugInPropertiesEventTypeHandler(Set<String> typeProps)
    {
        eventType = new MyPlugInPropertiesEventType(typeProps);
    }
    
    public EventType getType()
    {
        return eventType;
    }

    public EventSender getSender()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
