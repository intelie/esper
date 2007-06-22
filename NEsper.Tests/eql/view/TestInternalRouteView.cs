using System;

using net.esper.events;
using net.esper.support.core;
using net.esper.support.events;
using net.esper.support.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.view
{
	
	[TestFixture]
	public class TestInternalRouteView 
	{
		private InternalRouteView viewIStream;
		private InternalRouteView viewRStream;
		private SupportInternalEventRouter supportRouter;
		private SupportMapView childView = new SupportMapView();
		
		[SetUp]
		public virtual void  setUp()
		{
			supportRouter = new SupportInternalEventRouter();
			
			viewIStream = new InternalRouteView(true, supportRouter);
			viewRStream = new InternalRouteView(false, supportRouter);
			
			childView = new SupportMapView();
			viewIStream.AddView(childView);
		}
		
		[Test]
		public virtual void  testUpdate()
		{
			EventBean[] events = SupportEventBeanFactory.MakeEvents(new String[]{"a", "b"});

            viewIStream.Update(events, null);
			Assert.AreEqual(2, supportRouter.GetRouted().Count);
			supportRouter.Reset();
			Assert.AreEqual(events, childView.LastNewData);

            viewIStream.Update(null, events);
			Assert.AreEqual(0, supportRouter.GetRouted().Count);
			supportRouter.Reset();

            viewRStream.Update(null, events);
			Assert.AreEqual(2, supportRouter.GetRouted().Count);
			supportRouter.Reset();

            viewRStream.Update(events, null);
			Assert.AreEqual(0, supportRouter.GetRouted().Count);
			supportRouter.Reset();
		}
	}
}
