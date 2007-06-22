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
	/// <para>
	/// The additional fields are represented as a Map. Any queries to event properties are first
	/// held against the additional fields, and secondly are handed through to the underlying event.
	/// </para>
	/// <para>
	/// If this event type is to add information to another wrapper event type (wrapper to wrapper), then it is the
	/// responsibility of the creating logic to use the existing event type and add to it.
    /// </para>
	/// <para>
	/// Uses a the map event type {@link net.esper.events.MapEventType} to represent the mapped properties. This is because the additional properties
	/// can also be beans or complex types and the Map event type handles these nicely.
    /// </para>
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

	        List<String> lPropertyNames = new List<String>();
			foreach (String eventProperty in underlyingEventType.PropertyNames)
			{
                lPropertyNames.Add(eventProperty);
			}
			foreach (String mapProperty in underlyingMapType.PropertyNames)
			{
                lPropertyNames.Add(mapProperty);
			}
            this.propertyNames = lPropertyNames.ToArray();
		}

        /// <summary>
        /// Returns enumerable over all super types to event type, going up the hierarchy and including
        /// all interfaces (and their extended interfaces) and superclasses as EventType instances.
        /// </summary>
        /// <value></value>
		public IEnumerable<EventType> DeepSuperTypes
		{
			get { return null; }
		}

        /// <summary>
        /// Get the getter for a specified event property. Returns null if the property name is not valid.
        /// </summary>
        /// <param name="property">is the property name</param>
        /// <returns>
        /// a getter that can be used to obtain property values for event instances of the same event type
        /// </returns>
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

        /// <summary>
        /// Get all valid property names for the event type.
        /// </summary>
        /// <value>The property names.</value>
        /// <returns> A string array containing the property names of this typed event data object.
        /// </returns>
		public ICollection<string> PropertyNames
		{
			get { return propertyNames; }
		}

        /// <summary>
        /// Get the type of an event property as returned by the "getter" method for that property. Returns
        /// unboxed (such as 'typeof(int)') as well as boxed (typeof(int?)) type.
        /// Returns null if the property name is not valid.
        /// </summary>
        /// <param name="property">is the property name</param>
        /// <returns>
        /// type of the property, the unboxed or the boxed type.
        /// </returns>
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

        /// <summary>
        /// Returns an array of event types that are super to this event type, from which this event type
        /// inherited event properties.  For object instances underlying the event this method returns the
        /// event types for all superclasses extended by the object and all interfaces implemented by the
        /// object.
        /// </summary>
        /// <value></value>
        /// <returns>an array of event types</returns>
		public IEnumerable<EventType> SuperTypes
		{
			get { return null; }
		}

        /// <summary>
        /// Get the class that represents the type of the event type.
        /// Returns a bean event class if the schema represents a bean event type.
        /// Returns Map if the schema represents a collection of values in a Map.
        /// </summary>
        /// <value>The type of the underlying.</value>
        /// <returns> type of the event object
        /// </returns>
        public Type UnderlyingType
        {
            get
            {
                // If the additional properties are empty, such as when wrapping a native event by
                // means of wildcard-only select then the underlying type is simply the wrapped type.
                if (isNoMapProperties)
                {
                    return underlyingEventType.UnderlyingType;
                }
                else
                {
                    return typeof(Pair<Object, IDictionary<string,object>>);
                }
            }
        }

        /// <summary>
        /// Check that the given property name is valid for this event type, ie. that is exists in the event type.
        /// </summary>
        /// <param name="property">is the property to check</param>
        /// <returns>true if exists, false if not</returns>
		public bool IsProperty(String property)
		{
			return
                underlyingEventType.IsProperty(property) ||
				underlyingMapType.IsProperty(property);
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return
                "WrapperEventType " +
			    "underlyingEventType=" + underlyingEventType + " " +
			    "underlyingMapType=" + underlyingMapType;
		}

        /// <summary>
        /// Determines whether the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <param name="obj">The <see cref="T:System.Object"></see> to compare with the current <see cref="T:System.Object"></see>.</param>
        /// <returns>
        /// true if the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>; otherwise, false.
        /// </returns>
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

        /// <summary>
        /// Serves as a hash function for a particular type. <see cref="M:System.Object.GetHashCode"></see> is suitable for use in hashing algorithms and data structures like a hash table.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
	    public override int GetHashCode()
	    {
	        return hashCode;
	    }

        /// <summary>
        /// Checks for repeated property names.
        /// </summary>
        /// <param name="eventType">Type of the event.</param>
        /// <param name="properties">The properties.</param>
	    private static void CheckForRepeatedPropertyNames(EventType eventType, IDictionary<String, Type> properties)
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
