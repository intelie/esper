using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.events
{
	/// <summary> EventType than can be supplied with a preconfigured list of properties getters (aka. explicit properties).</summary>
	/// <author>  pablo
	/// </author>

    public abstract class BaseConfigurableEventType : EventType
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
			get { return underlyngType; }
		}

        /// <summary>
        /// Get all valid property names for the event type.
        /// </summary>
        /// <value>The property names.</value>
        /// <returns> A string array containing the property names of this typed event data object.
        /// </returns>
        virtual public ICollection<String> PropertyNames
		{
            get
            {
                List<String> propNames = new List<String>() ;
				propNames.AddRange( explicitProperties.Keys );
				propNames.AddRange( DoListPropertyNames() );
				return propNames;
            }
		}

        /// <summary>
        /// Returns an array of event types that are super to this event type, from which this event type
        /// inherited event properties.  For bean instances underlying the event this method returns the 
        /// event types for all superclasses extended by the bean and all interfaces implemented by the bean.
        /// </summary>
        /// <value></value>
        /// <returns>an array of event types</returns>
        public abstract IEnumerable<EventType> SuperTypes
        {
            get;
        }

        /// <summary>
        /// Returns enumerable over all super types to event type, going up the hierarchy and including
        /// all interfaces (and their extended interfaces) and superclasses as EventType instances.
        /// </summary>
        /// <value></value>
        public abstract IEnumerable<EventType> DeepSuperTypes
        {
            get;
        }

		private Type underlyngType;
		private EDictionary<String, TypedEventPropertyGetter> explicitProperties;
		
		/// <summary> Ctor.</summary>
		/// <param name="underlyngType">is the underlying type returned by the event type
		/// </param>
		
        internal BaseConfigurableEventType(Type underlyngType)
		{
			this.underlyngType = underlyngType;
		}
		
		/// <summary> Sets explicit properties using a map of event property name and getter instance for each property.</summary>
		/// <param name="explicitProperties">is the preconfigured properties not implicit in the event type
		/// </param>

        protected void SetExplicitProperties(EDictionary<String, TypedEventPropertyGetter> explicitProperties)
        {
            this.explicitProperties = explicitProperties;
        }

        /// <summary>
        /// Get the type of an event property as returned by the "getter" method for that property. Returns
        /// unboxed (such as 'int.class') as well as boxed (java.lang.Integer) type.
        /// Returns null if the property name is not valid.
        /// </summary>
        /// <param name="property">is the property name</param>
        /// <returns>
        /// type of the property, the unboxed or the boxed type.
        /// </returns>
		public virtual Type GetPropertyType(String property)
		{
            TypedEventPropertyGetter getter = null;
            if ( explicitProperties.TryGetValue( property, out getter ) )
				return getter.ResultClass;
			return DoResolvePropertyType(property);
		}

        /// <summary>
        /// Get the getter for a specified event property. Returns null if the property name is not valid.
        /// </summary>
        /// <param name="property">is the property name</param>
        /// <returns>
        /// a getter that can be used to obtain property values for event instances of the same event type
        /// </returns>
        public virtual EventPropertyGetter GetGetter(String property)
        {
        	EventPropertyGetter getter = explicitProperties.Fetch( property, null ) ;
        	if ( getter != null )
            {
                return getter;
            }
            return DoResolvePropertyGetter(property);
        }

        /// <summary>
        /// Check that the given property name is valid for this event type, ie. that is exists in the event type.
        /// </summary>
        /// <param name="property">is the property to check</param>
        /// <returns>true if exists, false if not</returns>
		public virtual bool IsProperty(String property)
		{
			return (GetGetter(property) != null);
		}
		
		/// <summary> Subclasses must implement this to supply a list of valid property names.</summary>
		/// <returns> list of properties
		/// </returns>
		internal abstract String[] DoListPropertyNames();
		
		/// <summary> Subclasses must implement this and supply a getter to a given property.</summary>
		/// <param name="property">is the property name
		/// </param>
		/// <returns> getter for property
		/// </returns>
		internal abstract EventPropertyGetter DoResolvePropertyGetter(String property);
		
		/// <summary> Subclasses must implement this and return a type for a property.</summary>
		/// <param name="property">is the property name
		/// </param>
		/// <returns> property type
		/// </returns>
		internal abstract Type DoResolvePropertyType(String property);
	}
}
