using System;

using net.esper.events;

namespace net.esper.events.property
{
	/// <summary>
    /// All properties have a property name and this is the abstract base class
    /// that serves up the property name.
    /// </summary>
	
    public abstract class PropertyBase : Property
	{
		/// <summary> Returns the property name.</summary>
		/// <returns> name of property
		/// </returns>
	
        virtual public String PropertyName
		{
			get { return propertyName; }
		}
		
        /// <summary>
        /// Property name.
        /// </summary>
		
        internal String propertyName;
		
		/// <summary> Ctor.</summary>
		/// <param name="propertyName">is the name of the property
		/// </param>
		
        protected PropertyBase(String propertyName)
		{
			this.propertyName = propertyName;
		}

        /// <summary>
        /// Gets the getter.
        /// </summary>
        /// <param name="param1">The param1.</param>
        /// <returns></returns>
        public abstract EventPropertyGetter GetGetter(BeanEventType param1);

        /// <summary>
        /// Gets the type of the property.
        /// </summary>
        /// <param name="param1">The param1.</param>
        /// <returns></returns>
		public abstract Type GetPropertyType(BeanEventType param1);
	}
}
