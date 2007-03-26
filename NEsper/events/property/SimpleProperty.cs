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

        /// <summary>
        /// Returns value getter for the property of an event of the given event type.
        /// </summary>
        /// <param name="eventType">is the type of event to make a getter for</param>
        /// <returns>fast property value getter for property</returns>
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

        /// <summary>
        /// Returns the property type.
        /// </summary>
        /// <param name="eventType">is the event type representing the JavaBean</param>
        /// <returns>property type class</returns>
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