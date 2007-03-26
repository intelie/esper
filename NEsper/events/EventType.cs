// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;
using System.Collections.Generic;

namespace net.esper.events
{	
	/// <summary>
    /// This interface provides metadata on events.
	/// <para>
	/// The interface exposes events as organizations of named values.
	/// The contract is that any event in the system must have a name-based
    /// way of accessing sub-data within its event type. A simple example is
    /// an object: the names can be property names, and those properties can
    /// have still more properties beneath them. Another example is a Dictionary
    /// structure. Here string names can refer to data objects.
    /// </para>
	/// </summary>
	
    public interface EventType
	{
        /// <summary>
        /// Get the class that represents the type of the event type.
        /// Returns a bean event class if the schema represents a bean event type.
        /// Returns Map if the schema represents a collection of values in a Map.
        /// </summary>
        /// <value>The type of the underlying.</value>
        /// <returns> type of the event object
        /// </returns>
		
        Type UnderlyingType { get ; }
        
        /// <summary>
        /// Get all valid property names for the event type.
        /// </summary>
        /// <value>The property names.</value>
        /// <returns> A string array containing the property names of this typed event data object.
        /// </returns>
        
        ICollection<String> PropertyNames { get ; }

        /// <summary>
        /// Get the type of an event property as returned by the "getter" method for that property. Returns
        /// unboxed (such as 'int.class') as well as boxed (java.lang.Integer) type.
        /// Returns null if the property name is not valid.
        /// </summary>
        /// <param name="property">is the property name</param>
        /// <returns>
        /// type of the property, the unboxed or the boxed type.
        /// </returns>
		
        Type GetPropertyType(String property);

        /// <summary>
        /// Get the getter for a specified event property. Returns null if the property name is not valid.
        /// </summary>
        /// <param name="property">is the property name</param>
        /// <returns>
        /// a getter that can be used to obtain property values for event instances of the same event type
        /// </returns>
		
        EventPropertyGetter GetGetter(String property);

        /// <summary>
        /// Check that the given property name is valid for this event type, ie. that is exists in the event type.
        /// </summary>
        /// <param name="property">is the property to check</param>
        /// <returns>true if exists, false if not</returns>
		
        bool IsProperty(String property);

        /// <summary>
        /// Returns an array of event types that are super to this event type, from which this event type 
        /// inherited event properties.  For object instances underlying the event this method returns the
        /// event types for all superclasses extended by the object and all interfaces implemented by the
        /// object.
        /// </summary>
        /// <returns>an array of event types</returns>
        
        IEnumerable<EventType> SuperTypes { get; }

        /// <summary>
        /// Returns enumerable over all super types to event type, going up the hierarchy and including
        /// all interfaces (and their extended interfaces) and superclasses as EventType instances.
        /// </summary>

        IEnumerable<EventType> DeepSuperTypes { get; }
	}

    class EventTypeArray
    {
        public static readonly EventType[] Empty = new EventType[] { };
    }
}