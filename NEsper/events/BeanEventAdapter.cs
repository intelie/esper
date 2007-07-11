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
		private readonly EDictionary<String, ConfigurationEventTypeLegacy> typeToLegacyConfigs;
		private readonly ReaderWriterLock typesPerBeanLock ;

        /// <summary>Default property resolution style.</summary>
        protected PropertyResolutionStyle defaultPropertyResolutionStyle;

		/// <summary> Ctor.</summary>
		
		public BeanEventAdapter()
		{
			typesPerBean = new HashDictionary<Type, BeanEventType>();
			typesPerBeanLock = new ReaderWriterLock();
			typeToLegacyConfigs = new HashDictionary<String, ConfigurationEventTypeLegacy>();
            defaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_SENSITIVE;
		}

        /// <summary>
        /// Initializes a new instance of the <see cref="BeanEventAdapter"/> class.
        /// </summary>
        /// <param name="defaultPropertyResolutionStyle">The default property resolution style.</param>
        public BeanEventAdapter( PropertyResolutionStyle defaultPropertyResolutionStyle )
        {
            this.typesPerBean = new HashDictionary<Type, BeanEventType>();
            this.typesPerBeanLock = new ReaderWriterLock();
            this.typeToLegacyConfigs = new HashDictionary<String, ConfigurationEventTypeLegacy>();
            this.defaultPropertyResolutionStyle = defaultPropertyResolutionStyle;
        }

        /// <summary>
        /// Gets or sets the property resolution style.
        /// </summary>
        /// <value>The property resolution style.</value>

        public virtual PropertyResolutionStyle DefaultPropertyResolutionStyle
        {
            get { return defaultPropertyResolutionStyle; }
            set { defaultPropertyResolutionStyle = value; }
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
		        throw new ArgumentException("Null value passed as type");
		    }

		    // Check if its already there
		    typesPerBeanLock.AcquireReaderLock(LockConstants.ReaderTimeout);
		    BeanEventType eventType = typesPerBean.Fetch(type);
		    typesPerBeanLock.ReleaseReaderLock();
		    if (eventType != null)
		    {
		        return eventType;
		    }

		    // not created yet, thread-safe create
		    using(new WriterLock(typesPerBeanLock))
		    {
		        eventType = typesPerBean.Fetch(type);
		        if (eventType != null)
		        {
		            return eventType;
		        }

		        // Check if we have a legacy type definition for this class
		        ConfigurationEventTypeLegacy legacyDef = typeToLegacyConfigs.Fetch(type.FullName);

		        string eventTypeId = "TYPE_" + type.FullName;
		        eventType = new BeanEventType(type, this, legacyDef, eventTypeId);
		        typesPerBean[type] = eventType;
		    }

		    return eventType;
		}
	}
}
