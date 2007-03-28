using System;
using System.Xml;

using net.esper.events;

namespace net.esper.events.xml
{
    /// <summary> EventBean wrapper for XML documents. 
    /// Currently only instances of org.w3c.dom.Node can be used
    /// </summary>
    /// <author>  pablo
    /// </author>

    public class XMLEventBean : EventBean
    {
        /// <summary>
        /// Return the <seealso cref="EventType" /> instance that describes the set of properties available for this event.
        /// </summary>
        /// <value></value>
        /// <returns> event type
        /// </returns>
        virtual public EventType EventType
        {
            get { return eventType; }
        }

        /// <summary>
        /// Get the underlying data object to this event wrapper.
        /// </summary>
        /// <value></value>
        /// <returns> underlying data object, usually either a Map or a bean instance.
        /// </returns>
        virtual public Object Underlying
        {
            get { return _event; }
        }

        private EventType eventType;
        private XmlNode _event;

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="_event">is the node with event property information</param>
        /// <param name="type">is the event type for this event wrapper</param>

        public XMLEventBean(XmlNode _event, EventType type)
        {
            this._event = _event;
            this.eventType = type;
        }

        /// <summary>
        /// Returns the value of an event property.
        /// </summary>
        /// <value></value>
        /// <returns> the value of a simple property with the specified name.
        /// </returns>
        /// <throws>  PropertyAccessException - if there is no property of the specified name, or the property cannot be accessed </throws>
        public virtual Object this[String property]
        {
            get
            {
                EventPropertyGetter getter = eventType.GetGetter(property);
                if (getter == null)
                {
                    throw new PropertyAccessException("Property named '" + property + "' is not a valid property name for this type");
                }

                return getter.GetValue(this);
            }
        }
    }
}