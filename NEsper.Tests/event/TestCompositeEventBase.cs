using System;

using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events
{
	[TestFixture]
	public abstract class TestCompositeEventBase
	{
		protected internal EventType eventType;
		protected internal EventBean eventBeanComplete;
		protected internal EventBean eventBeanInComplete;
		protected internal SupportBean _event;

		[SetUp]
		public virtual void setUp()
		{
			EDictionary<String, EventType> taggedEventTypes = new EHashDictionary<String, EventType>();
			taggedEventTypes.Put( "a", SupportEventAdapterService.Service.AddBeanType( "A", typeof( SupportBean ) ) );
			taggedEventTypes.Put( "b", SupportEventAdapterService.Service.AddBeanType( "B", typeof( SupportBeanComplexProps ) ) );
			eventType = new CompositeEventType( taggedEventTypes );

			_event = new SupportBean();
			_event.intPrimitive = 1;

			EDictionary<String, EventBean> wrappedEvents = new EHashDictionary<String, EventBean>();
			wrappedEvents.Put( "a", SupportEventAdapterService.Service.AdapterForBean( _event ) );
			wrappedEvents.Put( "b", SupportEventAdapterService.Service.AdapterForBean( SupportBeanComplexProps.makeDefaultBean() ) );
			eventBeanComplete = new CompositeEventBean( wrappedEvents, eventType );

			wrappedEvents = new EHashDictionary<String, EventBean>();
			wrappedEvents.Put( "a", SupportEventAdapterService.Service.AdapterForBean( _event ) );
			eventBeanInComplete = new CompositeEventBean( wrappedEvents, eventType );
		}
	}
}
