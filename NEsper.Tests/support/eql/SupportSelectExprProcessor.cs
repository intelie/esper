using System;

using net.esper.eql.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.support.eql
{
	
	public class SupportSelectExprProcessor : SelectExprProcessor
	{
		virtual public EventType ResultEventType
		{
			get
			{
				return SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
			}
			
		}
		
		public virtual EventBean Process(EventBean[] eventsPerStream)
		{
			return eventsPerStream[0];
		}
	}
}
