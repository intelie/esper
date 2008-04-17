package com.espertech.esper.plugin;

import com.espertech.esper.event.EventType;

public interface PlugInEventRepresentation
{
    public void init(PlugInEventRepresentationContext eventRepresentationContext);
    public boolean acceptsType(String pluginEventTypeURI);
    public EventType getType(PlugInEventTypeContext eventTypeContext);

}
