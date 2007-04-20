using System;
using System.Collections.Generic;
using System.Collections.Specialized;

using net.esper.compat;

namespace net.esper.events
{
    /// <summary>
    /// Wrapper for events represented by a Map of key-value pairs that are the event properties.
    /// MapEventBean instances are equal if they have the same <seealso cref="EventType" /> and all property names
    /// and values are reference-equal. 
    /// </summary>

    public class MapEventBean : EventBean
    {
        private EventType eventType;
        private EDataDictionary properties;
        private Int32 hashCode = 0;
        private bool hasHashCode = false;

        /// <summary>
        /// Return the <seealso cref="EventType" /> instance that describes the set of properties available for this event.
        /// </summary>
        /// <value></value>
        /// <returns> event type
        /// </returns>
        virtual public EventType EventType
        {
            get { return eventType; }
        }

        /// <summary>
        /// Get the underlying data object to this event wrapper.
        /// </summary>
        /// <value></value>
        /// <returns> underlying data object.
        /// </returns>
        virtual public Object Underlying
        {
            get { return properties; }
        }

        /// <summary> Constructor for initialization with existing values.
        /// Makes a shallow copy of the supplied values to not be surprised by changing property values.
        /// </summary>
        /// <param name="properties">are the event property values
        /// </param>
        /// <param name="eventType">is the type of the event, i.e. describes the map entries
        /// </param>

        public MapEventBean(IDataDictionary properties, EventType eventType)
        {
        	this.properties = new EDataDictionary() ;
            this.properties.PutAll(properties);
            this.eventType = eventType;
        }

        /// <summary> Constructor for initialization with existing values.
        /// Makes a shallow copy of the supplied values to not be surprised by changing property values.
        /// </summary>
        /// <param name="eventType">is the type of the event, i.e. describes the map entries
        /// </param>
        /// <param name="events">are the event property constisting of events
        /// </param>

        public MapEventBean(EventType eventType, IDictionary<String, EventBean> events)
        {
            this.properties = new EDataDictionary();

            foreach (KeyValuePair<String, EventBean> entry in events)
            {
                properties[entry.Key] = entry.Value.Underlying;
            }

            this.eventType = eventType;
        }

        /// <summary> Constructor for the mutable functions, e.g. only the type of values is known but not the actual values.</summary>
        /// <param name="eventType">is the type of the event, i.e. describes the map entries
        /// </param>

        public MapEventBean(EventType eventType)
        {
            this.properties = new EDataDictionary();
            this.eventType = eventType;
        }

        /// <summary>
        /// Returns the value of an event property.
        /// </summary>
        /// <value></value>
        /// <returns> the value of a simple property with the specified name.
        /// </returns>
        /// <throws>  PropertyAccessException - if there is no property of the specified name, or the property cannot be accessed </throws>
        public virtual Object this[String property]
        {
            get
            {
                EventPropertyGetter getter = eventType.GetGetter(property);
                if (getter == null)
                {
                    throw new ArgumentException("Property named '" + property + "' is not a valid property name for this type");
                }
             
                return getter.GetValue(this);
            }
        }

        /// <summary>
        /// Returns true if the objects are equal.
        /// </summary>
        /// <param name="otherObject">The other object.</param>
        /// <returns></returns>
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

            if (GetType() != otherObject.GetType())
            {
                return false;
            }

            MapEventBean other = (MapEventBean)otherObject;

            if (other.EventType != eventType)
            {
                return false;
            }

            if (properties.Count != other.properties.Count)
            {
                return false;
            }

            // Compare entry by entry
            foreach (KeyValuePair<String, Object> entry in properties)
            {
                String name = entry.Key;
                Object value = entry.Value;
                Object otherValue = other[name];

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

            return true;
        }

        /// <summary>
        /// Serves as a hash function for a particular type.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override int GetHashCode()
        {
            if (!hasHashCode)
            {
                int hashCodeVal = 0;
                foreach (KeyValuePair<String, Object> entry in properties)
                {
                    String name = entry.Key;
                    Object value = entry.Value;

                    if (value != null)
                    {
                        hashCodeVal = hashCodeVal ^ name.GetHashCode() ^ value.GetHashCode();
                    }
                }
                hashCode = hashCodeVal;
                hasHashCode = true;
            }
 
            return hashCode;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return "MapEventBean " + "eventType=" + eventType;
        }
    }
}