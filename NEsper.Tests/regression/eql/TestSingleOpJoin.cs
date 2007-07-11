using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestSingleOpJoin
	{
		private EPServiceProvider epService;
		private EPStatement joinView;
		private SupportUpdateListener updateListener;

		private SupportBean_A[] eventsA = new SupportBean_A[10];
		private SupportBean_A[] eventsASetTwo = new SupportBean_A[10];
		private SupportBean_B[] eventsB = new SupportBean_B[10];
		private SupportBean_B[] eventsBSetTwo = new SupportBean_B[10];

		[SetUp]
		public virtual void setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			updateListener = new SupportUpdateListener();

            String eventA = typeof(SupportBean_A).FullName;
            String eventB = typeof(SupportBean_B).FullName;

			String joinStatement =
				"select * from " + 
				eventA + "().win:length(3) as streamA," +
				eventB + "().win:length(3) as streamB" + 
				" where streamA.id = streamB.id";

			joinView = epService.EPAdministrator.CreateEQL( joinStatement );
			joinView.AddListener(updateListener);

			for ( int i = 0 ; i < eventsA.Length ; i++ )
			{
				eventsA[i] = new SupportBean_A( Convert.ToString( i ) );
				eventsASetTwo[i] = new SupportBean_A( Convert.ToString( i ) );
				eventsB[i] = new SupportBean_B( Convert.ToString( i ) );
				eventsBSetTwo[i] = new SupportBean_B( Convert.ToString( i ) );
			}
		}

		[Test]
		public void testJoinUniquePerId()
		{
			SendEvent( eventsA[0] );
			SendEvent( eventsB[1] );
			Assert.IsNull( updateListener.LastNewData );

			// Test join new B with id 0
			SendEvent( eventsB[0] );
			Assert.AreSame( eventsA[0], updateListener.LastNewData[0][ "streamA" ] );
			Assert.AreSame( eventsB[0], updateListener.LastNewData[0][ "streamB" ] );
			Assert.IsNull( updateListener.LastOldData );
			updateListener.Reset();

			// Test join new A with id 1
			SendEvent( eventsA[1] );
			Assert.AreSame( eventsA[1], updateListener.LastNewData[0][ "streamA" ] );
			Assert.AreSame( eventsB[1], updateListener.LastNewData[0][ "streamB" ] );
			Assert.IsNull( updateListener.LastOldData );
			updateListener.Reset();

			SendEvent( eventsA[2] );
			Assert.IsNull( updateListener.LastOldData );

			// Test join old A id 0 leaves length window of 3 events
			SendEvent( eventsA[3] );
			Assert.AreSame( eventsA[0], updateListener.LastOldData[0][ "streamA" ] );
			Assert.AreSame( eventsB[0], updateListener.LastOldData[0][ "streamB" ] );
			Assert.IsNull( updateListener.LastNewData );
			updateListener.Reset();

			// Test join old B id 1 leaves window
			SendEvent( eventsB[4] );
			Assert.IsNull( updateListener.LastOldData );
			SendEvent( eventsB[5] );
			Assert.AreSame( eventsA[1], updateListener.LastOldData[0][ "streamA" ] );
			Assert.AreSame( eventsB[1], updateListener.LastOldData[0][ "streamB" ] );
			Assert.IsNull( updateListener.LastNewData );
		}

		[Test]
		public void testJoinNonUniquePerId()
		{
			SendEvent( eventsA[0] );
			SendEvent( eventsA[1] );
			SendEvent( eventsASetTwo[0] );
			Assert.IsTrue( updateListener.LastOldData == null && updateListener.LastNewData == null );

			SendEvent( eventsB[0] ); // Event B id 0 joins to A id 0 twice
			EventBean[] data = updateListener.LastNewData;
			Assert.IsTrue(
                eventsASetTwo[0] == data[0][ "streamA" ] ||
                eventsASetTwo[0] == data[1][ "streamA" ] ); // Order arbitrary
			Assert.AreSame( eventsB[0], data[0][ "streamB" ] );
			Assert.IsTrue( eventsA[0] == data[0][ "streamA" ] || eventsA[0] == data[1][ "streamA" ] );
			Assert.AreSame( eventsB[0], data[1][ "streamB" ] );
			Assert.IsNull( updateListener.LastOldData );
			updateListener.Reset();

			SendEvent( eventsB[2] );
			SendEvent( eventsBSetTwo[0] ); // Ignore events generated
			updateListener.Reset();

			SendEvent( eventsA[3] ); // Pushes A id 0 out of window, which joins to B id 0 twice
			data = updateListener.LastOldData;
			Assert.AreSame( eventsA[0], updateListener.LastOldData[0][ "streamA" ] );
			Assert.IsTrue( 
                eventsB[0] == data[0][ "streamB" ] ||
                eventsB[0] == data[1][ "streamB" ] ); // B order arbitrary
			Assert.AreSame( eventsA[0], updateListener.LastOldData[1][ "streamA" ] );
			Assert.IsTrue(
                eventsBSetTwo[0] == data[0][ "streamB" ] || 
                eventsBSetTwo[0] == data[1][ "streamB" ] );
			Assert.IsNull( updateListener.LastNewData );
			updateListener.Reset();

			SendEvent( eventsBSetTwo[2] ); // Pushes B id 0 out of window, which joins to A set two id 0
			Assert.AreSame( eventsASetTwo[0], updateListener.LastOldData[0][ "streamA" ] );
			Assert.AreSame( eventsB[0], updateListener.LastOldData[0][ "streamB" ] );
			Assert.AreEqual( 1, updateListener.LastOldData.Length );
		}

		[Test]
		public void testEventType()
		{
			Assert.AreEqual( typeof( SupportBean_A ), joinView.EventType.GetPropertyType( "streamA" ) );
			Assert.AreEqual( typeof( SupportBean_B ), joinView.EventType.GetPropertyType( "streamB" ) );
			Assert.AreEqual( 2, joinView.EventType.PropertyNames.Count );
		}

		private void SendEvent( Object _event )
		{
			epService.EPRuntime.SendEvent( _event );
		}
	}
}
