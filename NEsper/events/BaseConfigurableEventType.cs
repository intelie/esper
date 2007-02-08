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
		virtual public Type UnderlyingType
		{
			get { return underlyngType; }
		}

        virtual public ICollection<String> PropertyNames
		{
            get
            {
                List<String> propNames = new List<String>() ;
				propNames.AddRange( explicitProperties.Keys );
				propNames.AddRange( doListPropertyNames() );
				return propNames;
            }
		}

        public abstract IEnumerable<EventType> SuperTypes
        {
            get;
        }

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

        protected void setExplicitProperties(EDictionary<String, TypedEventPropertyGetter> explicitProperties)
        {
            this.explicitProperties = explicitProperties;
        }
		
		public virtual Type GetPropertyType(String property)
		{
            TypedEventPropertyGetter getter = null;
            if ( explicitProperties.TryGetValue( property, out getter ) )
				return getter.ResultClass;
			return doResolvePropertyType(property);
		}

        public virtual EventPropertyGetter GetGetter(String property)
        {
        	EventPropertyGetter getter = explicitProperties.Fetch( property, null ) ;
        	if ( getter != null )
            {
                return getter;
            }
            return doResolvePropertyGetter(property);
        }
		
		public virtual bool isProperty(String property)
		{
			return (GetGetter(property) != null);
		}
		
		/// <summary> Subclasses must implement this to supply a list of valid property names.</summary>
		/// <returns> list of properties
		/// </returns>
		internal abstract String[] doListPropertyNames();
		
		/// <summary> Subclasses must implement this and supply a getter to a given property.</summary>
		/// <param name="property">is the property name
		/// </param>
		/// <returns> getter for property
		/// </returns>
		internal abstract EventPropertyGetter doResolvePropertyGetter(String property);
		
		/// <summary> Subclasses must implement this and return a type for a property.</summary>
		/// <param name="property">is the property name
		/// </param>
		/// <returns> property type
		/// </returns>
		internal abstract Type doResolvePropertyType(String property);
	}
}
