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
		
        public PropertyBase(String propertyName)
		{
			this.propertyName = propertyName;
		}
		
        public abstract EventPropertyGetter GetGetter(BeanEventType param1);
		public abstract Type GetPropertyType(BeanEventType param1);
	}
}