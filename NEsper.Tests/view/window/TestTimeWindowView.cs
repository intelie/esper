using System;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.schedule;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.window
{
	[TestFixture]
	public class TestTimeWindowView
	{
		private const long TEST_WINDOW_MSEC = 60000;

		private TimeWindowView myView;
		private SupportBeanClassView childView;
		private SupportSchedulingServiceImpl schedulingServiceStub;

		[SetUp]
		public virtual void setUp()
		{
			// Set up length window view and a test child view
			myView = new TimeWindowView( TEST_WINDOW_MSEC / 1000d );
			childView = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
			myView.AddView( childView );

			// Set the scheduling service to use
			schedulingServiceStub = new SupportSchedulingServiceImpl();
			myView.ViewServiceContext = SupportViewContextFactory.makeContext( schedulingServiceStub );
		}

		[Test]
		public virtual void testViewPushAndExpire()
		{
			long startTime = 1000000;
			schedulingServiceStub.Time = startTime;
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );

			EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
				new String[]{ "a1", "b1", "b2", "c1", "d1", "e1", "f1", "f2" });

			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), (EventBean[]) null );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, null );

			// Send new events to the view - should have scheduled a callback for X msec after
			myView.Update( new EventBean[] { events.Fetch( "a1" ) }, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[TEST_WINDOW_MSEC] != null );
			schedulingServiceStub.getAdded().Clear();

			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] { events.Fetch( "a1" ) } );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] { events.Fetch( "a1" ) } );

			// Send more events, check
			schedulingServiceStub.Time = startTime + 10000;
			myView.Update( new EventBean[] { events.Fetch( "b1" ), events.Fetch( "b2" ) }, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );

			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] {
				events.Fetch( "a1" ),
				events.Fetch( "b1" ), 
				events.Fetch( "b2" ) } );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] {
				events.Fetch( "b1" ),
				events.Fetch( "b2" ) } );

			// Send more events, check
			schedulingServiceStub.Time = startTime + TEST_WINDOW_MSEC - 1;
			myView.Update( new EventBean[] { events.Fetch( "c1" ) }, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );

			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] {
				events.Fetch( "a1" ),
				events.Fetch( "b1" ),
				events.Fetch( "b2" ), 
				events.Fetch( "c1" ) } );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] { events.Fetch( "c1" ) } );

			// Pretend we are getting the callback from scheduling, check old data and check new scheduling
			schedulingServiceStub.Time = startTime + TEST_WINDOW_MSEC;
			myView.Expire();
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] {
				events.Fetch( "b1" ),
				events.Fetch( "b2" ),
				events.Fetch( "c1" ) } );
			SupportViewDataChecker.checkOldData( childView, new EventBean[] { events.Fetch( "a1" ) } );
			SupportViewDataChecker.checkNewData( childView, null );

			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[10000L] != null );
			schedulingServiceStub.getAdded().Clear();

			// Send another 2 events
			schedulingServiceStub.Time = startTime + TEST_WINDOW_MSEC;
			myView.Update( new EventBean[] { events.Fetch( "d1" ) }, null );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] { events.Fetch( "d1" ) } );

			schedulingServiceStub.Time = startTime + TEST_WINDOW_MSEC + 1;
			myView.Update( new EventBean[] { events.Fetch( "e1" ) }, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] { events.Fetch( "e1" ) } );
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] {
				events.Fetch( "b1" ),
				events.Fetch( "b2" ), 
				events.Fetch( "c1" ), 
				events.Fetch( "d1" ), 
				events.Fetch( "e1" ) } );

			// Pretend callback received
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );
			schedulingServiceStub.Time = startTime + TEST_WINDOW_MSEC + 10000;
            myView.Expire();
			SupportViewDataChecker.checkOldData( childView, new EventBean[] {
				events.Fetch( "b1" ), 
				events.Fetch( "b2" ) } );
			SupportViewDataChecker.checkNewData( childView, null );
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] {
				events.Fetch( "c1" ),
				events.Fetch( "d1" ),
				events.Fetch( "e1" ) } );

			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[49999L] != null );
			schedulingServiceStub.getAdded().Clear();

			// Pretend callback received
			schedulingServiceStub.Time = startTime + TEST_WINDOW_MSEC + 59999;
            myView.Expire();
			SupportViewDataChecker.checkOldData( childView, new EventBean[] { events.Fetch( "c1" ) } );
			SupportViewDataChecker.checkNewData( childView, null );
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] {
				events.Fetch( "d1" ),
				events.Fetch( "e1" ) } );

			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[1L] != null );
			schedulingServiceStub.getAdded().Clear();

			// Send another event
			schedulingServiceStub.Time = startTime + TEST_WINDOW_MSEC + 200;
			myView.Update( new EventBean[] { events.Fetch( "f1" ), events.Fetch( "f2" ) }, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] {
				events.Fetch( "f1" ),
				events.Fetch( "f2" ) } );
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] {
				events.Fetch( "d1" ),
				events.Fetch( "e1" ),
				events.Fetch( "f1" ), 
				events.Fetch( "f2" ) } );

			// Pretend callback received, we didn't schedule for 1 msec after, but for 100 msec after
			// testing what happens when clock resolution or some other delay happens
			schedulingServiceStub.Time = startTime + TEST_WINDOW_MSEC + 60099;
            myView.Expire();
			SupportViewDataChecker.checkOldData( childView, new EventBean[] {
				events.Fetch( "d1" ),
				events.Fetch( "e1" ) } );
			SupportViewDataChecker.checkNewData( childView, null );
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] {
				events.Fetch( "f1" ),
				events.Fetch( "f2" ) } );

			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[101L] != null );
			schedulingServiceStub.getAdded().Clear();

			// Pretend callback received
			schedulingServiceStub.Time = startTime + TEST_WINDOW_MSEC + 60201;
            myView.Expire();
			SupportViewDataChecker.checkOldData( childView, new EventBean[] {
				events.Fetch( "f1" ),
				events.Fetch( "f2" ) } );
			SupportViewDataChecker.checkNewData( childView, null );
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), (EventBean[]) null);
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );
		}

		[Test]
		public virtual void testViewAttachesTo()
		{
			// Should attach to anything
			TimeWindowView view = new TimeWindowView( 5000 );
			SupportBeanClassView parent = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
			Assert.IsTrue( view.AttachesTo( parent ) == null );
			parent.AddView( view );
			Assert.IsTrue( view.EventType == parent.EventType );
		}

		[Test]
		public virtual void testCopyView()
		{
			SupportBeanClassView parent = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
            myView.Parent = parent;
			TimeWindowView copied = (TimeWindowView) ViewSupport.ShallowCopyView( myView );
			Assert.AreEqual( myView.MillisecondsBeforeExpiry, copied.MillisecondsBeforeExpiry );
		}

		public virtual EventBean[] MakeEvents( String[] ids )
		{
			return EventFactoryHelper.MakeEvents( ids );
		}
	}
}
