using System;
using System.Collections.Generic;
using System.Collections.Specialized;

using net.esper.compat;

namespace net.esper.events
{
    /// <summary>
    /// Implementation of the {@link EventType} interface for handling plain Maps containing name value pairs.
    /// </summary>

    public class MapEventType : EventType
    {
        /// <summary>
        /// Get the class that represents the type of the event type.
        /// Returns a bean event class if the schema represents a bean event type.
        /// Returns Map if the schema represents a collection of values in a Map.
        /// </summary>
        /// <value>The type of the underlying.</value>
        /// <returns> type of the event object
        /// </returns>
        virtual public Type UnderlyingType
        {
            get { return typeof(System.Collections.IDictionary); }
        }

        /// <summary>
        /// Get all valid property names for the event type.
        /// </summary>
        /// <value>The property names.</value>
        /// <returns> A string array containing the property names of this typed event data object.
        /// </returns>
        virtual public ICollection<String> PropertyNames
        {
            get { return propertyNames; }
        }

        private readonly String[] propertyNames; // Cache an array of property names so not to construct one frequently
        private readonly EDictionary<String, Type> types; // Mapping of property name and type
        private EDictionary<String, EventPropertyGetter> propertyGetters; // Mapping of property name and getters
        private int hashCode;
        private EventAdapterService eventAdapterService;

        /// <summary> Constructor takes a map of property names and types.</summary>
        /// <param name="propertyTypes">is pairs of property name and type
        /// </param>
        /// <param name="eventAdapterService">is 
        /// </param>

        public MapEventType(IDictionary<String, Type> propertyTypes, EventAdapterService eventAdapterService)
        {
            this.eventAdapterService = eventAdapterService;
            // copy the property names and types
            this.types = new EHashDictionary<String, Type>() ;
            this.types.PutAll( propertyTypes ) ;

            hashCode = 0;
            propertyNames = new String[types.Count];
            propertyGetters = new EHashDictionary<String, EventPropertyGetter>();

            // Initialize getters and names array
            int index = 0;
            foreach (KeyValuePair<String, Type> entry in types)
            {
                String name = entry.Key;
                hashCode = hashCode ^ name.GetHashCode();
                propertyNames[index++] = name;
                propertyGetters[name] = new SimpleEventPropertyGetter( name ) ;
            }
        }

        /// <summary>
        /// An EventProperty designed to extract the named property from
        /// a DataDictionary.  This method was originally implemented
        /// as an anonymous innerclass in Java.
        /// </summary>
        
        internal class SimpleEventPropertyGetter : EventPropertyGetter
        {
        	private string name ;
        	
        	internal SimpleEventPropertyGetter( string name ) 
        	{
        		this.name = name ;
        	}

            /// <summary>
            /// Return the value for the property in the event object specified when the instance was obtained.
            /// Useful for fast access to event properties. Throws a PropertyAccessException if the getter instance
            /// doesn't match the EventType it was obtained from, and to indicate other property access problems.
            /// </summary>
            /// <param name="eventBean">is the event to get the value of a property from</param>
            /// <returns>value of property in event</returns>
            /// <throws>  PropertyAccessException to indicate that property access failed </throws>
			public object GetValue(EventBean eventBean)
			{
                Object underlying = eventBean.Underlying;
                if (underlying is IDictionary<String, Object>)
                {
                    Object value = null;
                    ((IDictionary<String, Object>)underlying).TryGetValue(name, out value);
                    return value;
                }
                else if (underlying is System.Collections.IDictionary)
                {
                    Object value = ((System.Collections.IDictionary)underlying)[name];
                    return value;
                }
                else
                {
	                throw new PropertyAccessException(
	                    "Mismatched property getter to event bean type, " +
	                    "the underlying data object is not of type IDictionary" );
	            }
			}
        }
        
        /// <summary>
        /// Gets the type of property associated with the property name.
        /// </summary>
        /// <param name="propertyName"></param>
        /// <returns></returns>
        
        public Type GetPropertyType(String propertyName)
        {
            Type result = types.Fetch(propertyName, null);
            if (result != null)
            {
                return result;
            }

            // see if this is a nested property
            int index = propertyName.IndexOf('.');
            if (index == -1)
            {
                return null;
            }

            // Take apart the nested property into a map key and a nested value class property name
            String propertyMap = propertyName.Substring(0, index);
            String propertyNested = propertyName.Substring(index + 1, propertyName.Length - index - 1);

            if ( ! types.TryGetValue( propertyMap, out result ) )
            {
                return null;
            }

            // ask the nested class to resolve the property
            EventType nestedType = eventAdapterService.AddBeanType(result.FullName, result);
            return nestedType.GetPropertyType(propertyNested);
        }
        
        /// <summary>
        /// Gets the getter for the property name.
        /// </summary>
        /// <param name="propertyName"></param>
        /// <returns></returns>

        public virtual EventPropertyGetter GetGetter(String propertyName)
        {
            EventPropertyGetter getter = propertyGetters.Fetch(propertyName, null);
            if (getter != null)
            {
                return getter;
            }

            // see if this is a nested property
            int index = propertyName.IndexOf('.');
            if (index == -1)
            {
                return null;
            }

            // Take apart the nested property into a map key and a nested value class property name
            String propertyMap = propertyName.Substring(0, index);
            String propertyNested = propertyName.Substring(index + 1, propertyName.Length - index - 1);

            Type result = types.Fetch(propertyMap, null);
            if (result == null)
            {
                return null;
            }

            // ask the nested class to resolve the property
            EventType nestedType = eventAdapterService.AddBeanType(result.FullName, result);
            EventPropertyGetter nestedGetter = nestedType.GetGetter(propertyNested);
            if (nestedGetter == null)
            {
                return null;
            }

            // construct getter for nested property
            getter = new NestedEventPropertyGetter( eventAdapterService, nestedGetter, propertyMap ) ;

            return getter;
        }
        
        /// <summary>
        /// An EventProperty designed to extract the named property from
        /// a DataDictionary.  This method was originally implemented
        /// as an anonymous innerclass in Java.
        /// </summary>
        
        internal class NestedEventPropertyGetter : EventPropertyGetter
        {
        	private EventAdapterService eventAdapterService;
        	private EventPropertyGetter nestedGetter ;
        	private string propertyMap ;
        	
        	internal NestedEventPropertyGetter(
        		EventAdapterService eventAdapterService,
        		EventPropertyGetter nestedGetter,
        		string propertyMap )
        	{
        		this.eventAdapterService = eventAdapterService;
        		this.nestedGetter = nestedGetter;
        		this.propertyMap = propertyMap ;
        	}

            /// <summary>
            /// Return the value for the property in the event object specified when the instance was obtained.
            /// Useful for fast access to event properties. Throws a PropertyAccessException if the getter instance
            /// doesn't match the EventType it was obtained from, and to indicate other property access problems.
            /// </summary>
            /// <param name="eventBean">is the event to get the value of a property from</param>
            /// <returns>value of property in event</returns>
            /// <throws>  PropertyAccessException to indicate that property access failed </throws>
			public object GetValue(EventBean eventBean)
			{
                Object value = null;
                Object underlying = eventBean.Underlying;
                if (underlying is IDictionary<String, Object>)
                {
                    ((IDictionary<String, Object>)underlying).TryGetValue(propertyMap, out value);
                }
                else if (underlying is System.Collections.IDictionary)
                {
                    value = ((System.Collections.IDictionary)underlying)[propertyMap];
                }
                else
                {
                    throw new PropertyAccessException(
                        "Mismatched property getter to event bean type, " +
                        "the underlying data object is not of type IDictionary");
                }

	            // If the map does not contain the key, this is allowed and represented as null
                if (value == null)
                {
                    return null;
                }
                
                // Wrap object
                EventBean _event = eventAdapterService.AdapterForBean(value);
                return nestedGetter.GetValue(_event);
            }
        }

        /// <summary>
        /// Returns true if the specified property name maps to a property.
        /// </summary>
        /// <param name="propertyName"></param>
        /// <returns></returns>

        public virtual bool IsProperty(String propertyName)
        {
            Type propertyType = GetPropertyType(propertyName);
            return propertyType != null;
        }

        /// <summary>
        /// Returns an array of event types that are super to this event type, from which this event type
        /// inherited event properties.  For object instances underlying the event this method returns the
        /// event types for all superclasses extended by the object and all interfaces implemented by the
        /// object.
        /// </summary>
        /// <value></value>
        /// <returns>an array of event types</returns>
        public virtual IEnumerable<EventType> SuperTypes
        {
            get { return null; }
        }

        /// <summary>
        /// Returns enumerable over all super types to event type, going up the hierarchy and including
        /// all interfaces (and their extended interfaces) and superclasses as EventType instances.
        /// </summary>
        /// <value></value>
        public virtual IEnumerable<EventType> DeepSuperTypes
        {
            get { return EventTypeArray.Empty ; }
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return "MapEventType " + "propertyNames=" + CollectionHelper.Render(propertyNames);
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

            if (!(obj is MapEventType))
            {
                return false;
            }

            MapEventType other = (MapEventType)obj;

            // Should have the same number of properties
            if (other.types.Count != this.types.Count)
            {
                return false;
            }

            // Compare property by property
            foreach (KeyValuePair<String, Type> entry in types)
            {
                Type otherClass = other.types.Fetch(entry.Key, null);
                if (otherClass == null)
                {
                    return false;
                }
                if (!otherClass.Equals(entry.Value))
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
            return hashCode;
        }
    }
}
