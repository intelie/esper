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
        virtual public Type UnderlyingType
        {
            get { return typeof(IDataDictionary); }
        }

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
        	
			public object GetValue(EventBean eventBean)
			{
	            // The underlying is expected to be a map
	            IDataDictionary map = eventBean.Underlying as IDataDictionary;
	            if ( map == null )
	            {
	                throw new PropertyAccessException(
	                    "Mismatched property getter to event bean type, " +
	                    "the underlying data object is not of type IDataDictionary" );
	            }
	
	            // If the map does not contain the key, this is allowed and represented as null
	            Object value = map.Fetch( name, null ) ;
	            return value;
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
            String propertyMap = propertyName.Substring(0, (index) - (0));
            String propertyNested = propertyName.Substring(index + 1, (propertyName.Length) - (index + 1));

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
            String propertyMap = propertyName.Substring(0, (index) - (0));
            String propertyNested = propertyName.Substring(index + 1, (propertyName.Length) - (index + 1));

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
        	
			public object GetValue(EventBean eventBean)
			{
	            // The underlying is expected to be a map
	            IDataDictionary map = eventBean.Underlying as IDataDictionary;
	            if ( map == null )
	            {
	                throw new PropertyAccessException(
	                    "Mismatched property getter to event bean type, " +
	                    "the underlying data object is not of type DataDictionary" );
	            }

	            // If the map does not contain the key, this is allowed and represented as null
                Object value = map.Fetch(propertyMap);
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

        public virtual bool isProperty(String propertyName)
        {
            Type propertyType = GetPropertyType(propertyName);
            return propertyType != null;
        }

        public virtual IEnumerable<EventType> SuperTypes
        {
            get { return null; }
        }

        public virtual IEnumerable<EventType> DeepSuperTypes
        {
            get { return EventTypeArray.Empty ; }
        }

        public override String ToString()
        {
            return "MapEventType " + "propertyNames=" + CollectionHelper.Render(propertyNames);
        }

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

        public override int GetHashCode()
        {
            return hashCode;
        }
    }
}
