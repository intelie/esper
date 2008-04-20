package com.espertech.esper.plugin;

import com.espertech.esper.event.EventType;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.core.EPRuntimeSPI;
import com.espertech.esper.core.EPRuntimeEventSender;

public interface PlugInEventTypeHandler
{
    public EventType getType();
    public EventSender getSender(EPRuntimeEventSender runtimeEventSender);
}
