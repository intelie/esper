using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.client;
using net.esper.compat;
using net.esper.util;

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
		private readonly EDictionary<Type, BeanEventType> typesPerBean;
		private EDictionary<String, ConfigurationEventTypeLegacy> typeToLegacyConfigs;
		private readonly ReaderWriterLock typesPerBeanLock ;

		/// <summary> Ctor.</summary>
		
		public BeanEventAdapter()
		{
			typesPerBean = new EHashDictionary<Type, BeanEventType>();
			typesPerBeanLock = new ReaderWriterLock();
			typeToLegacyConfigs = new EHashDictionary<String, ConfigurationEventTypeLegacy>();
		}

		/// <summary>
		/// Sets the additional mappings for legacy types.
		/// </summary>
		
		public EDictionary<String, ConfigurationEventTypeLegacy> TypeToLegacyConfigs
		{
            get
            {
                throw new NotSupportedException();
            }
			set
			{
				typeToLegacyConfigs.PutAll( value ) ;
			}
		}
		
		/// <summary>
		/// Returns an adapter for the given object.
		/// </summary>
		/// <param name="_event">The event.</param>
		/// <param name="eventId">The optional event id.</param>
		/// <returns>EventBean wrapping object</returns>

		public virtual EventBean AdapterForBean(Object _event, Object eventId)
		{
			Type eventClass = _event.GetType();
			EventType eventType = CreateOrGetBeanType(eventClass);
			return new BeanEventBean(_event, eventType, eventId);
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
			typesPerBeanLock.AcquireReaderLock( LockConstants.ReaderTimeout ) ;
			
			try
			{
				BeanEventType eventType = typesPerBean.Fetch(type, null);
				if (eventType != null)
				{
					return eventType;
				}
			}
			finally
			{
				typesPerBeanLock.ReleaseReaderLock() ;
			}
			
			// Check if we have a legacy type definition for this class
			using( WriterLock writerLock = new WriterLock( typesPerBeanLock ) )
			{
	            BeanEventType eventType = typesPerBean.Fetch(type);
	            if (eventType != null)
	            {
	                return eventType;
	            }

	            // Check if we have a legacy type definition for this class
	            ConfigurationEventTypeLegacy legacyDef = typeToLegacyConfigs.Fetch(type.FullName);

	            String eventTypeId = "TYPE_" + type.FullName;
	            eventType = new BeanEventType(type, this, legacyDef, eventTypeId);
			    typesPerBean[type] = eventType;
	        }
		}
	}
}
