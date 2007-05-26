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
	/**
	 * Provides event adapter services through it's base class.
	 */
	public class EventAdapterServiceImpl : EventAdapterServiceBase
	{
	    public EventType AddBeanType(String eventTypeAlias, Type type) 
	    {
	        return base.AddBeanTypeByAliasAndClazz(eventTypeAlias, type);
	    }

	    public EventType AddBeanType(String eventTypeAlias, String typeName) 
	    {
	        return base.AddBeanTypeByAliasAndClassName(eventTypeAlias, typeName);
	    }

	    public EventBean AdapterForBean(Object _event)
	    {
	        return base.adapterForBean(_event, null);
	    }
	}
}
