using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.events.property
{
	/// <summary>
    /// Getter for one or more levels deep nested properties.
    /// </summary>

    public class NestedPropertyGetter : EventPropertyGetter
    {
        private readonly EventPropertyGetter[] getterChain;
        private readonly BeanEventAdapter beanEventAdapter;

        /// <summary> Ctor.</summary>
        /// <param name="getterChain">is the chain of getters to retrieve each nested property
        /// </param>
        /// <param name="beanEventAdapter">is the chache and factory for event bean types and event wrappers
        /// </param>
        public NestedPropertyGetter(IList<EventPropertyGetter> getterChain, BeanEventAdapter beanEventAdapter)
        {
            this.getterChain = CollectionHelper.ToArray( getterChain ) ;
            this.beanEventAdapter = beanEventAdapter;
        }

        /// <summary>
        /// Return the value for the property in the event object specified when the instance was obtained.
        /// Useful for fast access to event properties. Throws a PropertyAccessException if the getter instance
        /// doesn't match the EventType it was obtained from, and to indicate other property access problems.
        /// </summary>
        /// <param name="eventBean">is the event to get the value of a property from</param>
        /// <returns>value of property in event</returns>
        /// <throws>  PropertyAccessException to indicate that property access failed </throws>
        public virtual Object GetValue(EventBean eventBean)
        {
            Object _value = null;

            for (int i = 0; i < getterChain.Length; i++)
            {
            	_value = getterChain[i].GetValue(eventBean);

                if (_value == null)
                {
                    return null;
                }

                if (i < (getterChain.Length - 1))
                {
                    eventBean = beanEventAdapter.AdapterForBean(_value);
                }
            }

            return _value;
        }
    }
}
