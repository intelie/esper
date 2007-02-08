using System;

using net.esper.eql.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.support.eql
{
	
	public class SupportStreamTypeSvc1Stream : StreamTypeService
	{
		virtual public String[] StreamNames
		{
			get
			{
				return new String[]{"s0"};
			}
			
		}
		virtual public EventType[] EventTypes
		{
			get
			{
				EventType[] eventTypes = new EventType[]{SupportEventTypeFactory.createBeanType(typeof(SupportBean))};
				return eventTypes;
			}
			
		}
		public static EventBean[] SampleEvents
		{
			get
			{
				return new EventBean[]{SupportEventBeanFactory.createObject(new SupportBean())};
			}
			
		}
		private StreamTypeService impl;
		
		public SupportStreamTypeSvc1Stream()
		{
			impl = new StreamTypeServiceImpl(EventTypes, StreamNames);
		}
		
		public virtual PropertyResolutionDescriptor resolveByPropertyName(String propertyName)
		{
			return impl.resolveByPropertyName(propertyName);
		}
		
		public virtual PropertyResolutionDescriptor resolveByStreamAndPropName(String streamName, String propertyName)
		{
			return impl.resolveByStreamAndPropName(streamName, propertyName);
		}
		
		public virtual PropertyResolutionDescriptor resolveByStreamAndPropName(String streamAndPropertyName)
		{
			return impl.resolveByStreamAndPropName(streamAndPropertyName);
		}
	}
}
