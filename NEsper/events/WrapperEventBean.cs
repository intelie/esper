///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.collection;

using Properties = net.esper.compat.EDataDictionary;

namespace net.esper.events
{
	/// <summary>
	/// Event bean that wraps another event bean adding additional properties.
	/// <p>
	/// This can be useful for classes for which the statement adds derived values retaining the original class.
	/// <p>
	/// The event type of such events is always {@link WrapperEventType}. Additional properties are stored in a
	/// Map.
	/// </summary>
	public class WrapperEventBean : EventBean {

		private readonly EventBean _event;
		private readonly IDictionary<String, Object> map;
		private readonly EventType eventType;
	    private Integer hashCode;

	    /// <summary>Ctor.</summary>
	    /// <param name="_event">is the wrapped event</param>
	    /// <param name="properties">
	    /// is zero or more property values that embellish the wrapped event
	    /// </param>
	    /// <param name="eventType">is the {@link WrapperEventType}.</param>
	    public WrapperEventBean(EventBean _event, IDictionary<String, Object> properties, EventType eventType)
		{
			this._event = _event;
			this.map = properties;
			this.eventType = eventType;
		}

		public Object Get(String property)
		{
	        EventPropertyGetter getter = eventType.GetGetter(property);
	        if (getter == null)
	        {
	            throw new IllegalArgumentException("Property named '" + property + "' is not a valid property name for this type");
	        }
	        return eventType.GetGetter(property).Get(this);
		}

		public EventType GetEventType()
		{
			return eventType;
		}

		public Object GetUnderlying()
		{
	        // If wrapper is simply for the underlyingg with no additional properties, then return the underlying type
	        if (map.IsEmpty())
	        {
	            return _event.Underlying;
	        }
	        else
	        {
	            return new Pair<Object, Map>(_event.Underlying, map);
	        }
	    }

	    /// <summary>
	    /// Returns the underlying map storing the additional properties, if any.
	    /// </summary>
	    /// <returns>event property IDictionary</returns>
	    public Map UnderlyingMap
	    {
	        get { return map; }
	    }

	    /// <summary>Returns the wrapped event.</summary>
	    /// <returns>wrapped event</returns>
	    public EventBean UnderlyingEvent
	    {
	        get { return _event; }
	    }

	    public override String ToString()
		{
	        return
                "WrapperEventBean " +
	            "[event=" + _event + "] " +
	            "[properties=" + map + "]";
		}

	    public override bool Equals(Object otherObject)
	    {
	        if (otherObject == this)
	        {
	            return true;
	        }

	        if (otherObject == null)
	        {
	            return false;
	        }

	        if (Class != otherObject.Class)
	        {
	            return false;
	        }

	        WrapperEventBean other = (WrapperEventBean) otherObject;

	        if (other.eventType != eventType)
	        {
	            return false;
	        }

	        if (map.Size() != other.map.Size())
	        {
	            return false;
	        }

	        // Compare entry by entry
	        foreach (Map.Entry<String, Object> entry in map.EntrySet())
	        {
	            String name = entry.Key;
	            Object value = entry.Value;
	            Object otherValue = other.Get(name);

	            if ((otherValue == null) && (value == null))
	            {
	                continue;
	            }

	            if ((otherValue == null) && (value != null))
	            {
	                return false;
	            }

	            if (!otherValue.Equals(value))
	            {
	                return false;
	            }
	        }

	        return other._event.Equals(this._event);
	    }

	    public override int GetHashCode()
	    {
	        if (hashCode == null)
	        {
	            int hashCodeVal = 0;
	            foreach (Map.Entry<String, Object> entry in map.EntrySet())
	            {
	                String name = entry.Key;
	                Object value = entry.Value;

	                if (value != null)
	                {
	                    hashCodeVal = hashCodeVal ^ name.GetHashCode() ^ value.GetHashCode();
	                }
	            }
	            hashCode = hashCodeVal ^ _event.GetHashCode();
	        }
	        return hashCode;
	    }
	}
} // End of namespace
