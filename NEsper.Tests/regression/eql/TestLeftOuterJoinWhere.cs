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
	public class TestLeftOuterJoinWhere
	{
		private EPServiceProvider epService;
		private SupportUpdateListener updateListener;

		private SupportBean_S0[] eventsS0 = new SupportBean_S0[15];
		private SupportBean_S1[] eventsS1 = new SupportBean_S1[15];

		[SetUp]
		public virtual void setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			updateListener = new SupportUpdateListener();

			int count = 100;
			for ( int i = 0 ; i < eventsS0.Length ; i++ )
			{
				eventsS0[i] = new SupportBean_S0( count++, Convert.ToString( i ) );
			}
			count = 200;
			for ( int i = 0 ; i < eventsS1.Length ; i++ )
			{
				eventsS1[i] = new SupportBean_S1( count++, Convert.ToString( i ) );
			}
		}

		[Test]
		public void testWhereNotNullIs()
		{
			setupStatement( "where s1.p11 is not null" );
			tryWhereNotNull();
		}

		[Test]
		public void testWhereNotNullNE()
		{
			setupStatement( "where s1.p11 != null" );
			tryWhereNotNull();
		}

		[Test]
		public void testWhereNotNullSQLNE()
		{
			setupStatement( "where s1.p11 <> null" );
			tryWhereNotNull();
		}

		[Test]
		public void testWhereNullIs()
		{
			setupStatement( "where s1.p11 is null" );
			tryWhereNull();
		}

		[Test]
		public void testWhereNullEq()
		{
			setupStatement( "where s1.p11 = null" );
			tryWhereNull();
		}

		[Test]
		public void testWhereJoinOrNull()
		{
			setupStatement( "where s0.p01 = s1.p11 or s1.p11 is null" );

			// Send S0[0] p01=a
			eventsS0[0].P01 = "[a]";
			SendEvent( eventsS0[0] );
			compareEvent( updateListener.AssertOneGetNewAndReset(), eventsS0[0], null );

			// Send events to test the join for multiple rows incl. null value
			SupportBean_S1 s1_1 = new SupportBean_S1( 1000, "5", "X" );
			SupportBean_S1 s1_2 = new SupportBean_S1( 1001, "5", "Y" );
			SupportBean_S1 s1_3 = new SupportBean_S1( 1002, "5", "X" );
			SupportBean_S1 s1_4 = new SupportBean_S1( 1003, "5", null );
			SupportBean_S0 s0 = new SupportBean_S0( 1, "5", "X" );
			SendEvent( new Object[] { s1_1, s1_2, s1_3, s1_4, s0 } );

			Assert.AreEqual( 3, updateListener.LastNewData.Length );
			Object[] received = new Object[3];
			for ( int i = 0 ; i < 3 ; i++ )
			{
				Assert.AreSame( s0, updateListener.LastNewData[i]["s0"] );
				received[i] = updateListener.LastNewData[i]["s1"];
			}
			ArrayAssertionUtil.AreEqualAnyOrder( new Object[] { s1_1, s1_3, s1_4 }, received );
		}

		[Test]
		public void testWhereJoin()
		{
			setupStatement( "where s0.p01 = s1.p11" );

			// Send S0[0] p01=a
			eventsS0[0].P01 = "[a]";
			SendEvent( eventsS0[0] );
			Assert.IsFalse( updateListener.IsInvoked );

			// Send S1[1] p11=b
			eventsS1[1].P11 = "[b]";
			SendEvent( eventsS1[1] );
			Assert.IsFalse( updateListener.IsInvoked );

			// Send S0[1] p01=c, no match expected
			eventsS0[1].P01 = "[c]";
			SendEvent( eventsS0[1] );
			Assert.IsFalse( updateListener.IsInvoked );

			// Send S1[2] p11=d
			eventsS1[2].P11 = "[d]";
			SendEvent( eventsS1[2] );
			// Send S0[2] p01=d
			eventsS0[2].P01 = "[d]";
			SendEvent( eventsS0[2] );
			compareEvent( updateListener.AssertOneGetNewAndReset(), eventsS0[2], eventsS1[2] );

			// Send S1[3] and S0[3] with differing props, no match expected
			eventsS1[3].P11 = "[e]";
			SendEvent( eventsS1[3] );
			eventsS0[3].P01 = "[e1]";
			SendEvent( eventsS0[3] );
			Assert.IsFalse( updateListener.IsInvoked );
		}

		public virtual EPStatement setupStatement( String whereClause )
		{
			String joinStatement =
				"select * from " +
                typeof(SupportBean_S0).FullName + ".win:length(5) as s0 " + "left outer join " +
                typeof(SupportBean_S1).FullName + ".win:length(5) as s1" + " on s0.p00 = s1.p10 " +
				whereClause;

			EPStatement outerJoinView = epService.EPAdministrator.CreateEQL( joinStatement );
            outerJoinView.AddListener(updateListener);
			return outerJoinView;
		}

		[Test]
		public void testEventType()
		{
			EPStatement outerJoinView = setupStatement( "" );
			EventType type = outerJoinView.EventType;
			Assert.AreEqual( typeof( SupportBean_S0 ), type.GetPropertyType( "s0" ) );
			Assert.AreEqual( typeof( SupportBean_S1 ), type.GetPropertyType( "s1" ) );
		}

		private void tryWhereNotNull()
		{
			SupportBean_S1 s1_1 = new SupportBean_S1( 1000, "5", "X" );
			SupportBean_S1 s1_2 = new SupportBean_S1( 1001, "5", null );
			SupportBean_S1 s1_3 = new SupportBean_S1( 1002, "6", null );
			SendEvent( new Object[] { s1_1, s1_2, s1_3 } );
			Assert.IsFalse( updateListener.IsInvoked );

			SupportBean_S0 s0 = new SupportBean_S0( 1, "5", "X" );
			SendEvent( s0 );
			compareEvent( updateListener.AssertOneGetNewAndReset(), s0, s1_1 );
		}

		private void tryWhereNull()
		{
			SupportBean_S1 s1_1 = new SupportBean_S1( 1000, "5", "X" );
			SupportBean_S1 s1_2 = new SupportBean_S1( 1001, "5", null );
			SupportBean_S1 s1_3 = new SupportBean_S1( 1002, "6", null );
			SendEvent( new Object[] { s1_1, s1_2, s1_3 } );
			Assert.IsFalse( updateListener.IsInvoked );

			SupportBean_S0 s0 = new SupportBean_S0( 1, "5", "X" );
			SendEvent( s0 );
			compareEvent( updateListener.AssertOneGetNewAndReset(), s0, s1_2 );
		}

		private void compareEvent( EventBean receivedEvent, SupportBean_S0 expectedS0, SupportBean_S1 expectedS1 )
		{
			Assert.AreSame( expectedS0, receivedEvent["s0"] );
			Assert.AreSame( expectedS1, receivedEvent["s1"] );
		}

		private void SendEvent( Object[] events )
		{
			for ( int i = 0 ; i < events.Length ; i++ )
			{
				SendEvent( events[i] );
			}
		}

		private void SendEvent( Object _event )
		{
			epService.EPRuntime.SendEvent( _event );
		}
	}
}
