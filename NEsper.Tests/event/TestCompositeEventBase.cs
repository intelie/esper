using System;

using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events
{
	public abstract class TestCompositeEventBase
	{
		protected internal EventType eventType;
		protected internal EventBean _eventBeanComplete;
		protected internal EventBean _eventBeanInComplete;
		protected internal SupportBean _event;

		public virtual void setUp()
		{
			EDictionary<String, EventType> taggedEventTypes = new HashDictionary<String, EventType>();
			taggedEventTypes.Put( "a", SupportEventAdapterService.Service.AddBeanType( "A", typeof( SupportBean ) ) );
			taggedEventTypes.Put( "b", SupportEventAdapterService.Service.AddBeanType( "B", typeof( SupportBeanComplexProps ) ) );
			eventType = new CompositeEventType( taggedEventTypes );

			_event = new SupportBean();
			_event.SetIntPrimitive(1);

			EDictionary<String, EventBean> wrappedEvents = new HashDictionary<String, EventBean>();
			wrappedEvents.Put( "a", SupportEventAdapterService.Service.AdapterForBean( _event ) );
			wrappedEvents.Put( "b", SupportEventAdapterService.Service.AdapterForBean( SupportBeanComplexProps.MakeDefaultBean() ) );
			_eventBeanComplete = new CompositeEventBean( wrappedEvents, eventType );

			wrappedEvents = new HashDictionary<String, EventBean>();
			wrappedEvents.Put( "a", SupportEventAdapterService.Service.AdapterForBean( _event ) );
			_eventBeanInComplete = new CompositeEventBean( wrappedEvents, eventType );
		}
	}
}
