using System;
using net.esper.events;

namespace net.esper.events.property
{
    /// <summary>
    /// Represents a simple property of a given name.
    /// </summary>
    public class SimpleProperty : PropertyBase
    {
        /// <summary> Ctor.</summary>
        /// <param name="propertyName">is the property name
        /// </param>
        public SimpleProperty(String propertyName)
            : base(propertyName)
        {
        }

        public override EventPropertyGetter GetGetter(BeanEventType eventType)
        {
            EventPropertyDescriptor propertyDesc = eventType.GetSimpleProperty(propertyName);
            if (propertyDesc == null)
            {
                return null;
            }
            if (!propertyDesc.PropertyType.Equals(EventPropertyType.SIMPLE))
            {
                return null;
            }
            return eventType.GetGetter(propertyName);
        }

        public override Type GetPropertyType(BeanEventType eventType)
        {
            EventPropertyDescriptor propertyDesc = eventType.GetSimpleProperty(propertyName);
            if (propertyDesc == null)
            {
                return null;
            }
            return eventType.GetPropertyType(propertyName);
        }
    }
}