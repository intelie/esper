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
	public class TestTimeBatchView
	{
		private const long TEST_INTERVAL_MSEC = 10000;

		private TimeBatchView myView;
		private SupportBeanClassView childView;
		private SupportSchedulingServiceImpl schedulingServiceStub;

		[SetUp]
		public virtual void setUp()
		{
			// Set up length window view and a test child view
			myView = new TimeBatchView( TEST_INTERVAL_MSEC / 1000d );
			childView = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
			myView.AddView( childView );

			// Set the scheduling service to use
			schedulingServiceStub = new SupportSchedulingServiceImpl();
			myView.ViewServiceContext = SupportViewContextFactory.makeContext( schedulingServiceStub );
		}

		[Test]
		public virtual void testIncorrectUse()
		{
			try
			{
				myView = new TimeBatchView( 99 );
			}
			catch ( ArgumentException ex )
			{
				// Expected exception
			}
		}

		[Test]
		public virtual void testViewPushNoRefPoint()
		{
			long startTime = 1000000;
			schedulingServiceStub.Time = startTime;

			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), (EventBean[]) null);
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, null );

			EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
				new String[] { "a1", "b1", "b2", "c1", "d1" } );

			// Send new events to the view - should have scheduled a callback for X msec after
			myView.Update( new EventBean[] { events.Fetch( "a1" ) }, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null );
			schedulingServiceStub.getAdded().Clear();
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] { events.Fetch( "a1" ) } );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, null ); // Data got batched, no data release till later

			schedulingServiceStub.Time = startTime + 5000;
			myView.Update( new EventBean[] { events.Fetch( "b1" ), events.Fetch( "b2" ) }, null );
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] { events.Fetch( "a1" ), events.Fetch( "b1" ), events.Fetch( "b2" ) } );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );

			// Pretend we have a callback, check data, check scheduled new callback
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC;
			myView.SendBatch();
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), (EventBean[]) null);
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] { events.Fetch( "a1" ), events.Fetch( "b1" ), events.Fetch( "b2" ) } );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null );
			schedulingServiceStub.getAdded().Clear();

			// Pretend callback received again, should schedule a callback since the last interval showed data
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC * 2;
			myView.SendBatch();
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), (EventBean[]) null);
			SupportViewDataChecker.checkOldData( childView, new EventBean[] { events.Fetch( "a1" ), events.Fetch( "b1" ), events.Fetch( "b2" ) } ); // Old data is published
			SupportViewDataChecker.checkNewData( childView, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null );
			schedulingServiceStub.getAdded().Clear();

			// Pretend callback received again, not schedule a callback since the this and last interval showed no data
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC * 3;
			myView.SendBatch();
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), (EventBean[]) null);
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );

			// Send new event to the view - pretend we are 500 msec into the interval
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC * 3 + 500;
			myView.Update( new EventBean[] { events.Fetch( "c1" ) }, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded().Fetch( TEST_INTERVAL_MSEC - 500 ) != null );
			schedulingServiceStub.getAdded().Clear();
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] { events.Fetch( "c1" ) } );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, null ); // Data got batched, no data release till later

			// Pretend callback received again
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC * 4;
			myView.SendBatch();
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), (EventBean[]) null);
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] { events.Fetch( "c1" ) } );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null );
			schedulingServiceStub.getAdded().Clear();

			// Send new event to the view
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC * 4 + 500;
			myView.Update( new EventBean[] { events.Fetch( "d1" ) }, null );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 0 );
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] { events.Fetch( "d1" ) } );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, null );

			// Pretend callback again
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC * 5;
			myView.SendBatch();
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), (EventBean[]) null);
			SupportViewDataChecker.checkOldData( childView, new EventBean[] { events.Fetch( "c1" ) } );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] { events.Fetch( "d1" ) } );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null );
			schedulingServiceStub.getAdded().Clear();

			// Pretend callback again
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC * 6;
			myView.SendBatch();
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), (EventBean[]) null);
			SupportViewDataChecker.checkOldData( childView, new EventBean[] { events.Fetch( "d1" ) } );
			SupportViewDataChecker.checkNewData( childView, null );

			// Pretend callback again
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC * 7;
			myView.SendBatch();
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), (EventBean[]) null);
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, null );
		}

		[Test]
		public virtual void testViewPushWithRefPoint()
		{
			long startTime = 50000;
			schedulingServiceStub.Time = startTime;

			myView = new TimeBatchView( TEST_INTERVAL_MSEC / 1000d, 1505L ) ;
			childView = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
			myView.AddView( childView );
			myView.ViewServiceContext = SupportViewContextFactory.makeContext( schedulingServiceStub );

			EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
				new String[]{ "A1", "A2", "A3" });

			// Send new events to the view - should have scheduled a callback for X msec after
			myView.Update( new EventBean[] {
				events.Fetch( "A1" ),
				events.Fetch( "A2" ),
				events.Fetch( "A3" ) },
				null ) ;
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[1505L] != null );
			schedulingServiceStub.getAdded().Clear();
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), new EventBean[] { events.Fetch( "A1" ), events.Fetch( "A2" ), events.Fetch( "A3" ) } );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, null ); // Data got batched, no data release till later

			// Pretend we have a callback, check data, check scheduled new callback
			schedulingServiceStub.Time = startTime + 1505;
			myView.SendBatch();
			ArrayAssertionUtil.assertEqualsExactOrder( myView.GetEnumerator(), (EventBean[]) null );
			SupportViewDataChecker.checkOldData( childView, null );
			SupportViewDataChecker.checkNewData( childView, new EventBean[] { events.Fetch( "A1" ), events.Fetch( "A2" ), events.Fetch( "A3" ) } );
			Assert.IsTrue( schedulingServiceStub.getAdded().Count == 1 );
			Assert.IsTrue( schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null );
		}

		[Test]
		public virtual void testViewAttachesTo()
		{
			// Should attach to anything
			TimeBatchView view = new TimeBatchView( 200000 );
			SupportBeanClassView parent = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
			Assert.IsTrue( view.AttachesTo( parent ) == null );
			parent.AddView( view );
			Assert.IsTrue( view.EventType == parent.EventType );
		}

		[Test]
		public virtual void testComputeWaitMSec()
		{
			// With current=2300, ref=1000, and interval=500, expect 2500 as next interval and 200 as solution
			long result = TimeBatchView.ComputeWaitMSec( 2300, 1000, 500 );
			Assert.AreEqual( 200, result );

            result = TimeBatchView.ComputeWaitMSec(2300, 4200, 500);
			Assert.AreEqual( 400, result );

            result = TimeBatchView.ComputeWaitMSec(2200, 4200, 500);
			Assert.AreEqual( 500, result );

            result = TimeBatchView.ComputeWaitMSec(2200, 2200, 500);
			Assert.AreEqual( 500, result );

            result = TimeBatchView.ComputeWaitMSec(2201, 2200, 500);
			Assert.AreEqual( 499, result );

            result = TimeBatchView.ComputeWaitMSec(2600, 2200, 500);
			Assert.AreEqual( 100, result );

            result = TimeBatchView.ComputeWaitMSec(2699, 2200, 500);
			Assert.AreEqual( 1, result );

            result = TimeBatchView.ComputeWaitMSec(2699, 2700, 500);
			Assert.AreEqual( 1, result );

            result = TimeBatchView.ComputeWaitMSec(2699, 2700, 10000);
			Assert.AreEqual( 1, result );

            result = TimeBatchView.ComputeWaitMSec(2700, 2700, 10000);
			Assert.AreEqual( 10000, result );

            result = TimeBatchView.ComputeWaitMSec(2700, 6800, 10000);
			Assert.AreEqual( 4100, result );

            result = TimeBatchView.ComputeWaitMSec(23050, 16800, 10000);
			Assert.AreEqual( 3750, result );
		}

		[Test]
		public virtual void testCopyView()
		{
			myView = new TimeBatchView( TEST_INTERVAL_MSEC / 1000d );

			ViewServiceContext context = SupportViewContextFactory.makeContext();
			SupportBeanClassView parent = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
            myView.Parent = parent;
			myView.ViewServiceContext = context;

			TimeBatchView copied = (TimeBatchView) ViewSupport.shallowCopyView( myView );
			Assert.AreEqual( myView.MsecIntervalSize, copied.MsecIntervalSize );
			Assert.AreEqual( myView.InitialReferencePoint, copied.InitialReferencePoint );
			Assert.AreEqual( myView.ViewServiceContext, copied.ViewServiceContext );
		}

		[Test]
		public virtual void testConstructors()
		{
			myView = new TimeBatchView( 8.12 );
			Assert.AreEqual( 8120, myView.MsecIntervalSize );
			Assert.IsNull( myView.InitialReferencePoint );

			myView = new TimeBatchView( 0.23, 10000L ) ;
			Assert.AreEqual( 230, myView.MsecIntervalSize );
			Assert.AreEqual( 10000L, (long) myView.InitialReferencePoint );

			myView = new TimeBatchView( (int) 10, 20000L ) ;
			Assert.AreEqual( 10000, myView.MsecIntervalSize );
			Assert.AreEqual( 20000L, (long) myView.InitialReferencePoint );
		}
	}
}
