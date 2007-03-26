// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;

namespace net.esper.events
{
	/// <summary>
    /// Interface for event representation. All events have an {@link EventType}. Events also
	/// usually have one or more event properties. This interface allows the querying of event type,
	/// event property values and the underlying event object.
	/// </summary>
	
    public interface EventBean
	{
		/// <summary> Return the {@link EventType} instance that describes the set of properties available for this event.</summary>
		/// <returns> event type
		/// </returns>
	
        EventType EventType
		{
			get;
		}

		/// <summary> Get the underlying data object to this event wrapper.</summary>
		/// <returns> underlying data object, usually either a Map or a bean instance.
		/// </returns>
		
        Object Underlying
		{
			get;
		}
		
		/// <summary> Returns the value of an event property.</summary>
		/// <param name="property">name of the property whose value is to be retrieved
		/// </param>
		/// <returns> the value of a simple property with the specified name.
		/// </returns>
		/// <throws>  PropertyAccessException - if there is no property of the specified name, or the property cannot be accessed </throws>
        
        Object this[String property]
        {
            get;
        }
	}
}