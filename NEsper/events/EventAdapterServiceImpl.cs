using System;
using System.Collections.Generic;
using System.Xml;

using net.esper.compat;
using net.esper.client;
using net.esper.events;
using net.esper.events.xml;
using net.esper.util;

namespace net.esper.events
{
	/// <summary>
	/// Provides event adapter services through it's base class.
	/// </summary>
	public class EventAdapterServiceImpl : EventAdapterServiceBase
	{
        /// <summary>
        /// Add an event type with the given alias and Java class.
        /// If the alias already exists with the same Class, returns the existing EventType instance.
        /// If the alias already exists with different Class name, throws an exception.
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.events.BeanEventType"/>.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type</param>
        /// <param name="type">is the type</param>
        /// <returns>event type is the type added</returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match class names </throws>
	    public override EventType AddBeanType(String eventTypeAlias, Type type) 
	    {
	        return base.AddBeanTypeByAliasAndType(eventTypeAlias, type);
	    }

        /// <summary>
        /// Adds the type of the bean.
        /// </summary>
        /// <param name="eventTypeAlias">The event type alias.</param>
        /// <param name="typeName">Name of the type.</param>
        /// <returns></returns>
	    public override EventType AddBeanType(String eventTypeAlias, String typeName) 
	    {
	        return base.AddBeanTypeByAliasAndClassName(eventTypeAlias, typeName);
	    }

        /// <summary>
        /// Adapters for bean.
        /// </summary>
        /// <param name="_event">The _event.</param>
        /// <returns></returns>
	    public override EventBean AdapterForBean(Object _event)
	    {
	        return base.AdapterForBean(_event, null);
	    }
	}
}
