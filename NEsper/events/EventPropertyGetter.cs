using System;

namespace net.esper.events
{
	/// <summary> Get property values from an event instance for a given event property.
	/// Instances that implement this interface are usually bound to a particular <seealso cref="EventType" /> and cannot
	/// be used to access <seealso cref="EventBean" /> instances of a different type.
	/// </summary>

    public interface EventPropertyGetter
    {
        /// <summary> Return the value for the property in the event object specified when the instance was obtained.
        /// Useful for fast access to event properties. Throws a PropertyAccessException if the getter instance
        /// doesn't match the EventType it was obtained from, and to indicate other property access problems.
        /// </summary>
        /// <param name="eventBean">is the event to get the value of a property from
        /// </param>
        /// <returns> value of property in event
        /// </returns>
        /// <throws>  PropertyAccessException to indicate that property access failed </throws>

        Object GetValue(EventBean eventBean) ;
    }

    /// <summary>
    /// A delegate wrapper for the event property getter
    /// </summary>
    /// <param name="eventBean"></param>
    /// <returns></returns>

    public delegate Object EventPropertyGetterDelegate( EventBean eventBean ) ;

    /// <summary>
    /// An interface that wraps the the event property getter with a delegate
    /// </summary>

    public sealed class EventPropertyGetterImpl : EventPropertyGetter
    {
        private EventPropertyGetterDelegate getterDelegate;

        /// <summary>
        /// Initializes a new instance of the <see cref="EventPropertyGetterImpl"/> class.
        /// </summary>
        /// <param name="getterDelegate">The getter delegate.</param>
        public EventPropertyGetterImpl(EventPropertyGetterDelegate getterDelegate)
        {
            this.getterDelegate = getterDelegate;
        }

        /// <summary>
        /// Return the value for the property in the event object specified when the instance was obtained.
        /// Useful for fast access to event properties. Throws a PropertyAccessException if the getter instance
        /// doesn't match the EventType it was obtained from, and to indicate other property access problems.
        /// </summary>
        /// <param name="eventBean">is the event to get the value of a property from</param>
        /// <returns>value of property in event</returns>
        /// <throws>  PropertyAccessException to indicate that property access failed </throws>
        public Object GetValue(EventBean eventBean)
        {
            return this.getterDelegate(eventBean);
        }
    }
}