/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import java.util.Iterator;
import java.util.Map;

/**
 * This interface provides metadata on events.
 * <p>
 * The interface exposes events as organizations of named values.
 * The contract is that any event in the system must have a name-based way of accessing sub-data within its
 * event type. A simple example is a Java bean: the names can be property names, and those properties can have still
 * more properties beneath them. Another example is a Map structure. Here string names can refer to data objects.
 * <p>
 * The event type behaves somewhat similar to the DynaClass and DynaBean interfaces in the Jakarta commons beanutils
 * package. The Jakarta beanutils were not used for the reason that they don't provide a Getter interface
 * for fast retrieval of event property values for a given property name and given Java object or Map. Also,
 * events are immutable which contradicts the DynaBean interface.
 * <p>
 * Information on the super-types (superclass and interfaces implemented by JavaBean events) is also available. Supertypes
 * generally exclude Java language interfaces and types.
 */
public interface EventType
{
    /**
     * Get the type of an event property as returned by the "getter" method for that property. Returns
     * unboxed (such as 'int.class') as well as boxed (java.lang.Integer) type.
     * Returns null if the property name is not valid.
     * @param property is the property name
     * @return type of the property, the unboxed or the boxed type.
     */
    public Class getPropertyType(String property);

    /**
     * Get the class that represents the Java type of the event type.
     * Returns a Java bean event class if the schema represents a Java bean event type.
     * Returns java.util.Map is the schema represents a collection of values in a Map.
     * @return type of the event object
     */
    public Class getUnderlyingType();

    /**
     * Get the getter for a specified event property. Returns null if the property name is not valid.
     * @param property is the property name
     * @return a getter that can be used to obtain property values for event instances of the same event type
     */
    public EventPropertyGetter getGetter(String property);

    /**
     * Get all valid property names for the event type.
     * @return A string array containing the property names of this typed event data object.
     */
    public String[] getPropertyNames();

    /**
     * Check that the given property name is valid for this event type, ie. that is exists in the event type.
     * @param property is the property to check
     * @return true if exists, false if not
     */
    public boolean isProperty(String property);

    /**
     * Returns an array of event types that are super to this event type, from which this event type inherited event properties.
     * <p>For Java bean instances underlying the event this method returns the event types for all
     * superclasses extended by the Java bean and all interfaces implemented by the Java bean.
     * @return an array of event types
     */
    public EventType[] getSuperTypes();

    /**
     * Returns iterator over all super types to event type, going up the hierarchy and including all
     * Java interfaces (and their extended interfaces) and superclasses as EventType instances.
     * @return iterator of event types represeting all superclasses and implemented interfaces, all the way up to
     * java.lang.Object but excluding java.lang.Object itself
     */
    public Iterator<EventType> getDeepSuperTypes();
}
