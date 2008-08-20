/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

/**
 * EventType than can be supplied with a preconfigured list of properties getters (aka. explicit properties).
 * @author pablo
 */
public abstract class BaseConfigurableEventType implements EventType {

    private Class underlyngType;
	private Map<String,TypedEventPropertyGetter> explicitProperties;

    /**
     * Ctor.
     * @param underlyngType is the underlying type returned by the event type
     */
    protected BaseConfigurableEventType(Class underlyngType)
    {
        this.underlyngType = underlyngType;
    }

    /**
     * Sets explicit properties using a map of event property name and getter instance for each property.
     * @param explicitProperties is the preconfigured properties not implicit in the event type
     */
    protected void setExplicitProperties(Map<String, TypedEventPropertyGetter> explicitProperties)
    {
        this.explicitProperties = explicitProperties;
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
		Collection<String> propNames = new LinkedList<String>(explicitProperties.keySet());
		Collections.addAll(propNames,doListPropertyNames());
		return propNames.toArray(new String[propNames.size()]);
	}

	public boolean isProperty(String property) {
		return (getGetter(property) != null);
	}

	/**
	 * Subclasses must implement this to supply a list of valid property names.
	 * @return list of properties
	 */
	protected abstract String[] doListPropertyNames();

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
}


