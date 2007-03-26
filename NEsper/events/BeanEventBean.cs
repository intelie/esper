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

        /// <summary>
        /// Return the {@link EventType} instance that describes the set of properties available for this event.
        /// </summary>
        /// <value></value>
        /// <returns> event type
        /// </returns>
        virtual public EventType EventType
        {
            get { return eventType; }
        }

        private EventType eventType;
        private Object _event;

        /// <summary>
        /// Constructor.
        /// </summary>
        /// <param name="_event">is the event object</param>
        /// <param name="eventType">is the schema information for the event object.</param>

        protected internal BeanEventBean(Object _event, EventType eventType)
        {
            this.eventType = eventType;
            this._event = _event;
        }

        /// <summary>
        /// Returns true if this object equals the other object.
        /// </summary>
        /// <param name="other">The other.</param>
        /// <returns></returns>
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

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return "BeanEventBean" + " eventType=" + eventType + " bean=" + _event;
        }

        /// <summary>
        /// Serves as a hash function for a particular type.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}