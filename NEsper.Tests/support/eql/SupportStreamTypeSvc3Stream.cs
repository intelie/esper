using System;

using net.esper.eql.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.support.eql
{

	public class SupportStreamTypeSvc3Stream : StreamTypeService
	{
		virtual public String[] StreamNames
		{
			get
			{
				return new String[]{"s0", "s1", "s2"};
			}

		}
		virtual public EventType[] EventTypes
		{
			get
			{
                EventType[] eventTypes = new EventType[]{
                    SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)),
                    SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)),
                    SupportEventTypeFactory.CreateBeanType(typeof(SupportBeanComplexProps))
                };
				return eventTypes;
			}

		}
		public static EventBean[] SampleEvents
		{
			get
			{
				return new EventBean[]{SupportEventBeanFactory.CreateObject(new SupportBean()), SupportEventBeanFactory.CreateObject(new SupportBean()), SupportEventBeanFactory.CreateObject(SupportBeanComplexProps.MakeDefaultBean())};
			}

		}
		private StreamTypeService impl;

		public SupportStreamTypeSvc3Stream()
		{
			impl = new StreamTypeServiceImpl(EventTypes, StreamNames);
		}

        public virtual PropertyResolutionDescriptor ResolveByPropertyName(String propertyName)
		{
            return impl.ResolveByPropertyName(propertyName);
		}

		public virtual PropertyResolutionDescriptor ResolveByStreamAndPropName(String streamName, String propertyName)
		{
			return impl.ResolveByStreamAndPropName(streamName, propertyName);
		}

		public virtual PropertyResolutionDescriptor ResolveByStreamAndPropName(String streamAndPropertyName)
		{
			return impl.ResolveByStreamAndPropName(streamAndPropertyName);
		}
	}
}
