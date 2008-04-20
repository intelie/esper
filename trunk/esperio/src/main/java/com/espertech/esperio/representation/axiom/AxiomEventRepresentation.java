package com.espertech.esperio.representation.axiom;

import com.espertech.esper.plugin.*;
import com.espertech.esper.client.ConfigurationEventTypeXMLDOM;
import com.espertech.esper.event.EventType;

import java.util.Map;
import java.util.HashMap;

public class AxiomEventRepresentation implements PlugInEventRepresentation
{
    private Map<EventType, ConfigurationEventTypeXMLDOM> types = new HashMap<EventType, ConfigurationEventTypeXMLDOM>();

    public void init(PlugInEventRepresentationContext eventRepresentationContext)
    {
    }

    public boolean acceptsType(PlugInEventTypeHandlerContext acceptTypeContext)
    {
        if (!(acceptTypeContext.getTypeInitializer() instanceof ConfigurationEventTypeXMLDOM))
        {
            return false;
        }
        return true;  // accept all types as long as there is a configuration
    }

    public PlugInEventTypeHandler getTypeHandler(PlugInEventTypeHandlerContext eventTypeContext)
    {
        ConfigurationEventTypeXMLDOM config = (ConfigurationEventTypeXMLDOM) eventTypeContext.getTypeInitializer();
        AxiomXMLEventType eventType = new AxiomXMLEventType(config);
        types.put(eventType, config);
        return new AxiomEventTypeHandler(eventType);
    }

    public boolean acceptsEventBeanResolution(PlugInEventBeanReflectorContext context)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PlugInEventBeanFactory getEventBeanFactory(PlugInEventBeanReflectorContext uri)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
