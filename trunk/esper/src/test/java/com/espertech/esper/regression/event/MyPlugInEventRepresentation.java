package com.espertech.esper.regression.event;

import com.espertech.esper.plugin.*;

import java.net.URI;
import java.util.*;

public class MyPlugInEventRepresentation implements PlugInEventRepresentation
{
    // Properties shared by all event types, for testing
    private Set<String> baseProps;

    // Since this implementation also tests dynamic event reflection, keep a list of event types
    private List<MyPlugInPropertiesEventType> types;

    public void init(PlugInEventRepresentationContext context)
    {
        // Load a fixed set of properties from a String initializer, in comma-separated list.
        // Each type we generate will have this base set of properties.
        String initialValues = (String) context.getRepresentationInitializer();
        String[] propertyList = (initialValues != null) ? initialValues.split(",") : new String[0];
        baseProps = new HashSet<String>(Arrays.asList(propertyList));

        types = new ArrayList<MyPlugInPropertiesEventType>();
    }

    public boolean acceptsType(PlugInEventTypeHandlerContext context)
    {
        return true;
    }

    public PlugInEventTypeHandler getTypeHandler(PlugInEventTypeHandlerContext eventTypeContext)
    {
        String typeProperyies = (String) eventTypeContext.getTypeInitializer();
        String[] propertyList = (typeProperyies != null) ? typeProperyies.split(",") : new String[0];

        // the set of properties know are the set of this alias as well as the set for the base
        Set<String> typeProps = new HashSet<String>(Arrays.asList(propertyList));
        typeProps.addAll(baseProps);

        // save type for testing dynamic event object reflection
        MyPlugInPropertiesEventType eventType = new MyPlugInPropertiesEventType(typeProps);
        types.add(eventType);
        
        return new MyPlugInPropertiesEventTypeHandler(eventType);
    }

    public boolean acceptsEventBeanResolution(PlugInEventBeanReflectorContext eventBeanContext)
    {
        return true;
    }

    public PlugInEventBeanFactory getEventBeanFactory(PlugInEventBeanReflectorContext eventBeanContext)
    {
        return new MyPlugInPropertiesBeanFactory(types);
    }
}