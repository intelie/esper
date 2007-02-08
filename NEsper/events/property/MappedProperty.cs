using System;
using System.Reflection;

using net.esper.events;

namespace net.esper.events.property
{
    /// <summary>
    /// Represents a mapped property or array property, ie. a 'value' property
    /// with read method getValue(int index) or a 'array' property via read
    /// method Array returning an array.
    /// </summary>

    public class MappedProperty : PropertyBase
    {
        private String key;

        /// <summary> Returns the key value for mapped access.</summary>
        /// <returns> key value
        /// </returns>

		virtual public String Key
        {
            get { return key; }
        }

        /// <summary> Ctor.</summary>
        /// <param name="propertyName">is the property name of the mapped property
        /// </param>
        /// <param name="key">is the key value to access the mapped property
        /// </param>
        
		public MappedProperty( String propertyName, String key )
            : base(propertyName)
        {
            this.key = key;
        }

        public override EventPropertyGetter GetGetter(BeanEventType eventType)
        {
            EventPropertyDescriptor propertyDesc = eventType.GetMappedProperty(propertyName);
            if (propertyDesc == null)
            {
                // property not found, is not a property
                return null;
            }

            return new KeyedPropertyGetter(propertyDesc.Descriptor as IndexedPropertyDescriptor, key);
        }

        public override Type GetPropertyType(BeanEventType eventType)
        {
            EventPropertyDescriptor propertyDesc = eventType.GetMappedProperty(propertyName);
            if (propertyDesc == null)
            {
                // property not found, is not a property
                return null;
            }
            return propertyDesc.ReturnType;
        }
    }
}
