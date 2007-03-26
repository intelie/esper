using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;

namespace net.esper.events
{
	/// <summary>
	/// A cache and factory class for obtaining 
    /// <seealso cref="EventType"/> instances and <seealso cref="EventBean"/> instances
	/// for object events. The class caches <seealso cref="EventType"/> instances
    /// already known for performance reasons.
	/// </summary>

	public class BeanEventAdapter
	{
		private readonly EDictionary<Type, BeanEventType> typesPerJavaBean;
		private readonly EDictionary<String, ConfigurationEventTypeLegacy> classToLegacyConfigs;

		/// <summary> Ctor.</summary>
		/// <param name="classToLegacyConfigs">us a map of event type alias to legacy event type config
		/// </param>
		
		public BeanEventAdapter( IDictionary<String, ConfigurationEventTypeLegacy> classToLegacyConfigs )
		{
			typesPerJavaBean = new EHashDictionary<Type, BeanEventType>();

			this.classToLegacyConfigs = new EHashDictionary<String, ConfigurationEventTypeLegacy>();
			if ( classToLegacyConfigs != null )
			{
				this.classToLegacyConfigs.PutAll( classToLegacyConfigs );
			}
		}

		/// <summary>
		/// Returns an adapter for the given object.
		/// </summary>
		/// <param name="_event">The ev.</param>
		/// <returns>EventBean wrapping object</returns>

		public virtual EventBean AdapterForBean(Object _event)
		{
			Type eventClass = _event.GetType();
			EventType eventType = CreateOrGetBeanType(eventClass);
			return new BeanEventBean(_event, eventType);
		}

		/// <summary>
		/// Creates a new EventType object for a specified type if this is the first time
		/// the type has been seen. Else uses a cached EventType instance, i.e. client types
        /// do not need to cache.
		/// </summary>
		/// <param name="type">the type of the object.</param>
		/// <returns>EventType implementation for bean class</returns>
		
		public BeanEventType CreateOrGetBeanType(Type type)
		{
			if (type == null)
			{
				throw new ArgumentException("Null value passed as class");
			}
			
			// Check if its already there
			BeanEventType eventType = typesPerJavaBean.Fetch(type, null);
			if (eventType != null)
			{
				return eventType;
			}
			
			// Check if we have a legacy type definition for this class
			ConfigurationEventTypeLegacy legacyDef = classToLegacyConfigs.Fetch( type.FullName, null ) ;
			eventType = new BeanEventType(type, this, legacyDef);
			typesPerJavaBean[type] = eventType;
			
			return eventType;
		}
	}
}
