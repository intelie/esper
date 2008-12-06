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

import java.util.Map;

/**
 * EventType than can be supplied with a preconfigured list of properties getters (aka. explicit properties).
 * @author pablo
 */
public abstract class BaseConfigurableEventType implements EventTypeSPI {

    private final EventTypeMetadata metadata;
    private Class underlyngType;
	private Map<String,TypedEventPropertyGetter> explicitProperties;
    private EventPropertyDescriptor[] propertyDescriptors;
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

    public String getName()
    {
        return metadata.getPrimaryName();
    }

    /**
     * Sets explicit properties using a map of event property name and getter instance for each property.
     * @param explicitProperties is the preconfigured properties not implicit in the event type
     */
    protected void setExplicitProperties(Map<String, TypedEventPropertyGetter> explicitProperties)
    {
        this.explicitProperties = explicitProperties;

        propertyDescriptors = new EventPropertyDescriptor[explicitProperties.size()];
        propertyNames = new String[explicitProperties.size()];

        int count = 0;
        for (Map.Entry<String, TypedEventPropertyGetter> entry : explicitProperties.entrySet())
        {
            propertyNames[count] = entry.getKey();
            propertyDescriptors[count] = new EventPropertyDescriptor(entry.getKey(), entry.getValue().getResultClass(), false,false,false,false,false);
            count++;
        }
    }

    public Class getPropertyType(String property) {
		TypedEventPropertyGetter getter = explicitProperties.get(property);
		if (getter != null)
			return getter.getResultClass();
		return doResolvePropertyType(property);
	}


	public Class getUnderlyingType() {
		return underlyngType;
	}

	public EventPropertyGetter getGetter(String property) {
		EventPropertyGetter getter = explicitProperties.get(property);
		if (getter != null)
			return getter;
		return doResolvePropertyGetter(property);
	}

	public String[] getPropertyNames() {
		return propertyNames;
	}

	public boolean isProperty(String property) {
		return (getGetter(property) != null);
	}

	/**
	 * Subclasses must implement this and supply a getter to a given property.
     * @param property is the property name
	 * @return getter for property
	 */
	protected abstract EventPropertyGetter doResolvePropertyGetter(String property);

	/**
	 * Subclasses must implement this and return a type for a property.
     * @param property is the property name
	 * @return property type
	 */
	protected abstract Class doResolvePropertyType(String property);

    public EventPropertyDescriptor[] getPropertyDescriptors()
    {
        return propertyDescriptors;
    }

    public EventTypeMetadata getMetadata()
    {
        return metadata;
    }
}
