using System;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{

	[TestFixture]
	public class Test3StreamSingleOpJoin
	{
		private EPServiceProvider epService;
		private EPStatement joinView;
		private SupportUpdateListener updateListener;

		private SupportBean_A[] eventsA = new SupportBean_A[10];
		private SupportBean_B[] eventsB = new SupportBean_B[10];
		private SupportBean_C[] eventsC = new SupportBean_C[10];

		[SetUp]
		public virtual void setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			updateListener = new SupportUpdateListener();

			String eventA = typeof( SupportBean_A ).FullName;
			String eventB = typeof( SupportBean_B ).FullName;
			String eventC = typeof( SupportBean_C ).FullName;

			String joinStatement =
				"select * from " + 
				eventA + ".win:length(3) as streamA," + 
				eventB + ".win:length(3) as streamB," + 
				eventC + ".win:length(3) as streamC" + 
				" where streamA.id = streamB.id " + 
				"   and streamB.id = streamC.id" + 
				"   and streamA.id = streamC.id";

			joinView = epService.EPAdministrator.createEQL( joinStatement );
			joinView.AddListener( updateListener );

			for ( int i = 0 ; i < eventsA.Length ; i++ )
			{
				eventsA[i] = new SupportBean_A( Convert.ToString( i ) );
				eventsB[i] = new SupportBean_B( Convert.ToString( i ) );
				eventsC[i] = new SupportBean_C( Convert.ToString( i ) );
			}
		}

		[Test]
		public virtual void testJoinUniquePerId()
		{
			// Test sending a C event
			SendEvent( eventsA[0] );
			SendEvent( eventsB[0] );
			Assert.IsNull( updateListener.LastNewData );
			SendEvent( eventsC[0] );
			assertEventsReceived( eventsA[0], eventsB[0], eventsC[0] );

			// Test sending a B event
			SendEvent( new Object[] { eventsA[1], eventsB[2], eventsC[3] } );
			SendEvent( eventsC[1] );
			Assert.IsNull( updateListener.LastNewData );
			SendEvent( eventsB[1] );
			assertEventsReceived( eventsA[1], eventsB[1], eventsC[1] );

			// Test sending a C event
			SendEvent( new Object[] { eventsA[4], eventsA[5], eventsB[4], eventsB[3] } );
			Assert.IsNull( updateListener.LastNewData );
			SendEvent( eventsC[4] );
			assertEventsReceived( eventsA[4], eventsB[4], eventsC[4] );
			Assert.IsNull( updateListener.LastNewData );
		}

		private void assertEventsReceived( SupportBean_A event_A, SupportBean_B event_B, SupportBean_C event_C )
		{
			Assert.AreEqual( 1, updateListener.LastNewData.Length );
			Assert.AreSame( event_A, updateListener.LastNewData[0][ "streamA" ] );
			Assert.AreSame( event_B, updateListener.LastNewData[0][ "streamB" ] );
			Assert.AreSame( event_C, updateListener.LastNewData[0]["streamC" ] );
			updateListener.reset();
		}

		private void SendEvent( Object _event )
		{
			epService.EPRuntime.SendEvent( _event );
		}

		private void SendEvent( Object[] events )
		{
			for ( int i = 0 ; i < events.Length ; i++ )
			{
				epService.EPRuntime.SendEvent( events[i] );
			}
		}
	}
}
