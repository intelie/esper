///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.collection;

namespace net.esper.events
{
	/// <summary>
	/// An event type that adds zero or more fields to an existing event type.
	/// <p>
	/// The additional fields are represented as a Map. Any queries to event properties are first
	/// held against the additional fields, and secondly are handed through to the underlying event.
	/// <p>
	/// If this event type is to add information to another wrapper event type (wrapper to wrapper), then it is the
	/// responsibility of the creating logic to use the existing event type and add to it.
	/// <p>
	/// Uses a the map event type {@link net.esper.events.MapEventType} to represent the mapped properties. This is because the additional properties
	/// can also be beans or complex types and the Map event type handles these nicely.
	/// </summary>
	public class WrapperEventType : EventType
	{
		private readonly EventType underlyingEventType;
		private readonly MapEventType underlyingMapType;
		private readonly String[] propertyNames;
	    private readonly int hashCode;
	    private readonly bool isNoMapProperties;

	    /// <summary>Ctor.</summary>
	    /// <param name="typeName">is the event type alias name</param>
	    /// <param name="eventType">is the event type of the wrapped events</param>
	    /// <param name="properties">is the additional properties this wrapper adds</param>
	    /// <param name="eventAdapterService">is the ser</param>
	    public WrapperEventType(String typeName, EventType eventType, IDictionary<String, Type> properties, EventAdapterService eventAdapterService)
		{
			CheckForRepeatedPropertyNames(eventType, properties);

			this.underlyingEventType = eventType;
			this.underlyingMapType = new MapEventType(typeName, properties, eventAdapterService);
	        this.hashCode = underlyingMapType.GetHashCode() ^ underlyingEventType.GetHashCode();
	        this.isNoMapProperties = properties.Count == 0;

	        List<String> propertyNames = new List<String>();
			foreach (String eventProperty in underlyingEventType.PropertyNames)
			{
				propertyNames.Add(eventProperty);
			}
			foreach (String mapProperty in underlyingMapType.PropertyNames)
			{
				propertyNames.Add(mapProperty);
			}
			this.propertyNames = propertyNames.ToArray();
		}

		public IEnumerable<EventType> DeepSuperTypes
		{
			get { return null; }
		}

		public EventPropertyGetter GetGetter(String property)
		{
			if(underlyingEventType.IsProperty(property))
			{
	            return new EventPropertyGetterImpl(new EventPropertyGetterDelegate(
                    delegate(EventBean _event)
	                {
	                    if(!(_event is WrapperEventBean))
	                    {
	                        throw new PropertyAccessException("Mismatched property getter to EventBean type");
	                    }
	                    WrapperEventBean wrapperEvent = (WrapperEventBean) _event;
	                    EventBean wrappedEvent = wrapperEvent.UnderlyingEvent;
	                    return underlyingEventType.GetGetter(property).GetValue(wrappedEvent);
                    }));
			}
			else if (underlyingMapType.IsProperty(property))
			{
	            return new EventPropertyGetterImpl(
                    delegate(EventBean _event)
	                {
	                    if(!(_event is WrapperEventBean))
	                    {
	                        throw new PropertyAccessException("Mismathched property getter to EventBean type");
	                    }
	                    WrapperEventBean wrapperEvent = (WrapperEventBean) _event;
	                    IDictionary<string,object> map = wrapperEvent.UnderlyingMap;
	                    return underlyingMapType.GetValue(property, map);
	                });
			}
			else
			{
				return null;
			}
		}

		public ICollection<string> PropertyNames
		{
			get { return propertyNames; }
		}

		public Type GetPropertyType(String property)
		{
			if(underlyingEventType.IsProperty(property))
			{
				return underlyingEventType.GetPropertyType(property);
			}
			else if (underlyingMapType.IsProperty(property))
			{
				return underlyingMapType.GetPropertyType(property);
			}
			else
			{
				return null;
			}
		}

		public IEnumerable<EventType> SuperTypes
		{
			get { return null; }
		}

        public Type UnderlyingType
        {
            get
            {
                // If the additional properties are empty, such as when wrapping a native event by means of wildcard-only select
                // then the underlying type is simply the wrapped type.
                if (isNoMapProperties)
                {
                    return underlyingEventType.UnderlyingType;
                }
                else
                {
                    return typeof(Pair);
                }
            }
        }

		public bool IsProperty(String property)
		{
			return
                underlyingEventType.IsProperty(property) ||
				underlyingMapType.IsProperty(property);
		}

		public override String ToString()
		{
			return
                "WrapperEventType " +
			    "underlyingEventType=" + underlyingEventType + " " +
			    "underlyingMapType=" + underlyingMapType;
		}

	    public override bool Equals(Object obj)
	    {
	        if (this == obj)
	        {
	            return true;
	        }

	        if (!(obj is WrapperEventType))
	        {
	            return false;
	        }

	        WrapperEventType other = (WrapperEventType) obj;

	        if ((other.underlyingEventType.Equals(this.underlyingEventType)) &&
	            (other.underlyingMapType.Equals(this.underlyingMapType)))
	        {
	            return true;
	        }
	        return false;
	    }

	    public override int GetHashCode()
	    {
	        return hashCode;
	    }

	    private void CheckForRepeatedPropertyNames(EventType eventType, IDictionary<String, Type> properties)
		{
			foreach (String property in eventType.PropertyNames)
			{
				if(properties.ContainsKey(property))
				{
					throw new EPException("Property " + property + " occurs in both the underlying event and in the additional properties");
				}
			}
		}
	}
} // End of namespace
