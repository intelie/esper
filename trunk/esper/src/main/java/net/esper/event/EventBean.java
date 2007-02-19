/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.event;

/**
 * Interface for event representation. All events have an {@link EventType}. Events also
 * usually have one or more event properties. This interface allows the querying of event type,
 * event property values and the underlying event object.
 */
public interface EventBean
{
    /**
     * Return the {@link EventType} instance that describes the set of properties available for this event.
     * @return event type
     */
    public EventType getEventType();

    /**
     * Returns the value of an event property.
     * @param property - name of the property whose value is to be retrieved
     * @return the value of a simple property with the specified name.
     * @throws PropertyAccessException - if there is no property of the specified name, or the property cannot be accessed
     */
    public Object get(String property) throws PropertyAccessException;

    /**
     * Get the underlying data object to this event wrapper.
     * @return underlying data object, usually either a Map or a Java bean instance.
     */
    public Object getUnderlying();
}
