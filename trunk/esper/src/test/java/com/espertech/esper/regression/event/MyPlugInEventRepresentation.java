package com.espertech.esper.regression.event;

import com.espertech.esper.event.EventType;
import com.espertech.esper.plugin.PlugInEventRepresentation;
import com.espertech.esper.plugin.PlugInEventRepresentationContext;
import com.espertech.esper.plugin.PlugInEventTypeHandlerContext;
import com.espertech.esper.plugin.PlugInEventTypeHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

public class MyPlugInEventRepresentation implements PlugInEventRepresentation
{
    private static Log log = LogFactory.getLog(MyPlugInEventRepresentation.class);

    // Properties shared by all event types, for testing
    private URI baseURI;
    private Set<String> baseProps;

    public void init(PlugInEventRepresentationContext context)
    {
        // Load a fixed set of properties from a String initializer, in comma-separated list.
        // Each type we generate will have this base set of properties.
        String initialValues = (String) context.getRepresentationInitializer();
        String[] propertyList = initialValues.split(",");
        baseProps = new HashSet<String>(Arrays.asList(propertyList));
        baseURI = context.getEventRepresentationURI();
    }

    public boolean acceptsType(URI pluginEventTypeURI, Serializable initializer)
    {
        return true;
    }

    public PlugInEventTypeHandler getHandler(PlugInEventTypeHandlerContext eventTypeContext)
    {
        String typeProperyies = (String) eventTypeContext.getTypeInitializer();
        String[] propertyList = typeProperyies.split(",");

        // the set of properties know are the set of this alias as well as the set for the base
        Set<String> typeProps = new HashSet<String>(Arrays.asList(propertyList));
        typeProps.addAll(baseProps);

        return new MyPlugInPropertiesEventTypeHandler(typeProps);        
    }
}
