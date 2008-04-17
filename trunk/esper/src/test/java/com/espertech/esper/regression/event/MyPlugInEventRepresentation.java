package com.espertech.esper.regression.event;

import com.espertech.esper.event.EventType;
import com.espertech.esper.plugin.PlugInEventRepresentation;
import com.espertech.esper.plugin.PlugInEventRepresentationContext;
import com.espertech.esper.plugin.PlugInEventTypeContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public boolean acceptsType(String pluginEventTypeURI)
    {
        URI typeURI;
        try
        {
            typeURI = new URI(pluginEventTypeURI);
        }
        catch (URISyntaxException e)
        {
            log.error("Error parsing URI " + pluginEventTypeURI, e);
            return false;
        }

        // Simply check schema and authority
        if (typeURI.getScheme().equals(baseURI.getScheme()))
        {
            return false;
        }
        if (typeURI.getAuthority().equals(baseURI.getAuthority()))
        {
            return false;
        }
        return true;
    }

    public EventType getType(PlugInEventTypeContext eventTypeContext)
    {
        String typeProperyies = (String) eventTypeContext.getTypeInitializer();
        String[] propertyList = typeProperyies.split(",");
        Set<String> typeProps = new HashSet<String>(Arrays.asList(propertyList));
        return new MyPlugInPropertiesEventType(typeProps);
    }
}
