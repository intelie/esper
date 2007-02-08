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
        virtual public EventType EventType
        {
            get { return eventType; }
        }

        virtual public Object Underlying
        {
            get { return _event; }
        }

        private EventType eventType;
        private XmlNode _event;

        /// <summary> Ctor.</summary>
        /// <param name="event">is the node with event property information
        /// </param>
        /// <param name="type">is the event type for this event wrapper
        /// </param>

        public XMLEventBean(XmlNode _event, EventType type)
        {
            this._event = _event;
            this.eventType = type;
        }

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