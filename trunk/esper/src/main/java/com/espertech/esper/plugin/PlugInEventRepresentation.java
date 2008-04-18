package com.espertech.esper.plugin;

import com.espertech.esper.event.EventType;

import java.io.Serializable;
import java.net.URI;

public interface PlugInEventRepresentation
{
    public void init(PlugInEventRepresentationContext eventRepresentationContext);
    public boolean acceptsType(URI pluginEventTypeURI, Serializable initializer);
    public PlugInEventTypeHandler getHandler(PlugInEventTypeHandlerContext eventTypeContext);
}
