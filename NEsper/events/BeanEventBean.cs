using System;

namespace net.esper.events
{
    /// <summary>
    /// Wrapper for regular objects the represent events.
    /// Allows access to event properties, which is done through the getter supplied by the event type.
    /// {@link EventType} instances containing type information are obtained from {@link BeanEventAdapter}.
    /// Two BeanEventBean instances are equal if they have the same event type and refer to the same
    /// instance of event object.  Clients that need to compute equality between objects wrapped by
    /// this class need to obtain the underlying object.
    /// </summary>

    public class BeanEventBean : EventBean
    {
        virtual public Object Underlying
        {
            get { return _event; }
        }

        virtual public EventType EventType
        {
            get { return eventType; }
        }

        private EventType eventType;
        private Object _event;

        /// <summary> Constructor.</summary>
        /// <param name="event">is the event object
        /// </param>
        /// <param name="eventType">is the schema information for the event object.
        /// </param>

        protected internal BeanEventBean(Object _event, EventType eventType)
        {
            this.eventType = eventType;
            this._event = _event;
        }

        public override bool Equals(Object other)
        {
            if (!(other is BeanEventBean))
            {
                return false;
            }
            BeanEventBean o = (BeanEventBean)other;
            if (other == this)
            {
                return true;
            }
            if (o.eventType != eventType)
            {
                return false;
            }
            if (o._event != _event)
            {
                return false;
            }
            return true;
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

        public override String ToString()
        {
            return "BeanEventBean" + " eventType=" + eventType + " bean=" + _event;
        }

        //UPGRADE_NOTE: The following method implementation was automatically added to preserve functionality. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1306'"
        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}