/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.collection.Pair;

import java.util.Map;
import java.util.HashMap;

/**
 * EventType than can be supplied with a preconfigured list of properties getters (aka. explicit properties).
 * @author pablo
 */
public abstract class BaseConfigurableEventType implements EventTypeSPI {

    private final EventTypeMetadata metadata;
    private Class underlyngType;
	private Map<String,EventPropertyGetter> propertyGetters;
    private EventPropertyDescriptor[] propertyDescriptors;
    private Map<String, EventPropertyDescriptor> propertyDescriptorMap;
    private String[] propertyNames;

    /**
     * Ctor.
     * @param underlyngType is the underlying type returned by the event type
     * @param metadata event type metadata
     */
    protected BaseConfigurableEventType(EventTypeMetadata metadata, Class underlyngType)
    {
        this.metadata = metadata;
        this.underlyngType = underlyngType;
    }

    /**
     * Subclasses must implement this and supply a getter to a given property.
     * @param property is the property expression
     * @return getter for property
     */
    protected abstract EventPropertyGetter doResolvePropertyGetter(String property);

    /**
     * Subclasses must implement this and return a type for a property.
     * @param property is the property expression
     * @return property type
     */
    protected abstract Class doResolvePropertyType(String property);

    /**
     * Subclasses must implement this and return a fragment type for a property.
     * @param property is the property expression
     * @return fragment property type
     */
    protected abstract FragmentEventType doResolveFragmentType(String property);

    public String getName()
    {
        return metadata.getPrimaryName();
    }

    /**
     * Sets explicit properties using a map of event property name and getter instance for each property.
     */
    protected void initialize(Map<String, Pair<EventPropertyGetter, EventPropertyDescriptor>> explicitProperties)
    {
        propertyGetters = new HashMap<String,EventPropertyGetter>();
        propertyDescriptors = new EventPropertyDescriptor[explicitProperties.size()];
        propertyNames = new String[explicitProperties.size()];
        propertyDescriptorMap = new HashMap<String, EventPropertyDescriptor>();

        int count = 0;
        for (Map.Entry<String, Pair<EventPropertyGetter, EventPropertyDescriptor>> entry : explicitProperties.entrySet())
        {
            propertyNames[count] = entry.getKey();
            EventPropertyGetter getter = entry.getValue().getFirst();
            propertyGetters.put(entry.getKey(), getter);
            EventPropertyDescriptor desc = entry.getValue().getSecond();
            propertyDescriptors[count] = desc;
            propertyDescriptorMap.put(desc.getPropertyName(), desc);
            count++;
        }
    }

    public Class getPropertyType(String property) {
		EventPropertyDescriptor desc = propertyDescriptorMap.get(property);
		if (desc != null) {
			return desc.getPropertyType();
        }
        return doResolvePropertyType(property);
	}


	public Class getUnderlyingType() {
		return underlyngType;
	}

	public EventPropertyGetter getGetter(String property) {
		EventPropertyGetter getter = propertyGetters.get(property);
		if (getter != null)
			return getter;
		return doResolvePropertyGetter(property);
	}

    public FragmentEventType getFragmentType(String property)
    {
        return doResolveFragmentType(property);
    }

    public String[] getPropertyNames() {
		return propertyNames;
	}

	public boolean isProperty(String property) {
		return (getGetter(property) != null);
	}

    public EventPropertyDescriptor[] getPropertyDescriptors()
    {
        return propertyDescriptors;
    }

    public EventTypeMetadata getMetadata()
    {
        return metadata;
    }

    public EventPropertyDescriptor getPropertyDescriptor(String propertyName)
    {
        return propertyDescriptorMap.get(propertyName);
    }    
}
