/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.property;

import com.espertech.esper.event.*;

import java.util.Map;
import java.io.StringWriter;

/**
 * Interface for a property of an event of type BeanEventType (JavaBean event). Properties are designed to
 * handle the different types of properties for such events: indexed, mapped, simple, nested, or a combination of
 * those.
 */
public interface Property
{
    /**
     * Returns the property type.
     * @param eventType is the event type representing the JavaBean
     * @return property type class
     */
    public Class getPropertyType(BeanEventType eventType);

    /**
     * Returns value getter for the property of an event of the given event type.
     * @param eventType is the type of event to make a getter for
     * @return fast property value getter for property
     */
    public EventPropertyGetter getGetter(BeanEventType eventType);

    /**
     * Returns the property type for use with Map event representations.
     * @param optionalMapPropTypes a map-within-map type definition, if supplied, or null if not supplied
     * @param eventAdapterService for resolving further map event types that are property types
     * @return property type @param optionalMapPropTypes
     */
    public Class getPropertyTypeMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService);

    /**
     * Returns the getter-method for use with Map event representations.
     * @param optionalMapPropTypes a map-within-map type definition, if supplied, or null if not supplied
     * @param eventAdapterService for resolving further map event types that are property types
     * @return getter for maps @param optionalMapPropTypes
     */
    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService);

    /**
     * Write the EPL-representation of the property.
     * @param writer to write to
     */
    public void toPropertyEPL(StringWriter writer);
}
