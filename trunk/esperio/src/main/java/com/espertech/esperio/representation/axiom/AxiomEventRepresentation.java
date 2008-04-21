package com.espertech.esperio.representation.axiom;

import com.espertech.esper.plugin.*;
import com.espertech.esper.client.ConfigurationEventTypeXMLDOM;
import com.espertech.esper.event.EventType;

import java.util.Map;
import java.util.HashMap;

/**
 * Plug-in event representation for Apache AXIOM.
 * <p>
 * Allows direct querying of org.apache.axiom.OMDocument and org.apache.axiom.om.OMNode objects via
 * properties that are translated into XPath.
 */
public class AxiomEventRepresentation implements PlugInEventRepresentation
{
    private Map<String, AxiomXMLEventType> types = new HashMap<String, AxiomXMLEventType>();

    public void init(PlugInEventRepresentationContext eventRepresentationContext)
    {
    }

    public boolean acceptsType(PlugInEventTypeHandlerContext acceptTypeContext)
    {
        return acceptTypeContext.getTypeInitializer() instanceof ConfigurationEventTypeXMLDOM;
    }

    public PlugInEventTypeHandler getTypeHandler(PlugInEventTypeHandlerContext eventTypeContext)
    {
        ConfigurationEventTypeXMLDOM config = (ConfigurationEventTypeXMLDOM) eventTypeContext.getTypeInitializer();
        AxiomXMLEventType eventType = new AxiomXMLEventType(config);
        types.put(config.getRootElementName(), eventType);  // keep a handle on the types created to allow dynamic event reflection via bean factory
        return new AxiomEventTypeHandler(eventType);
    }

    public boolean acceptsEventBeanResolution(PlugInEventBeanReflectorContext context)
    {
        return true;
    }

    public PlugInEventBeanFactory getEventBeanFactory(PlugInEventBeanReflectorContext uri)
    {
        return new AxionEventBeanFactory(types);
    }
}
