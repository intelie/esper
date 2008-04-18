package com.espertech.esper.plugin;

import com.espertech.esper.event.EventType;
import com.espertech.esper.client.EventSender;

public interface PlugInEventTypeHandler
{
    public EventType getType();
    public EventSender getSender();
}
