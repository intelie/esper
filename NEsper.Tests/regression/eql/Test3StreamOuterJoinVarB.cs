using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{

	[TestFixture]
	public class Test3StreamOuterJoinVarB
	{
		private EPServiceProvider epService;
		private EPStatement joinView;
		private SupportUpdateListener updateListener;

		private static readonly String _event_S0;
		private static readonly String _event_S1;
		private static readonly String _event_S2;

		[SetUp]
		public virtual void setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.EPRuntime.SendEvent( new TimerControlEvent( TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL ) );
			epService.Initialize();
			updateListener = new SupportUpdateListener();
		}

		private Object[][] getAndResetNewEvents()
		{
			EventBean[] newEvents = updateListener.LastNewData;
			Assert.IsNotNull( newEvents, "no events received" );
			updateListener.Reset();
			return ArrayHandlingUtil.GetUnderlyingEvents( newEvents, new String[] { "s0", "s1", "s2" } );
		}

		[Test]
		public void testOuterInnerJoin_root_s0()
		{
			/**
			* Query:
			*                  s0
			*           s1 <-      <- s2
			*/
			String joinStatement =
				"select * from " +
				_event_S0 + ".win:length(1000) as s0 " + " left outer join " +
				_event_S1 + ".win:length(1000) as s1 on s0.p00 = s1.p10 " + " right outer join " +
				_event_S2 + ".win:length(1000) as s2 on s0.p00 = s2.p20 ";

			joinView = epService.EPAdministrator.CreateEQL( joinStatement );
			joinView.AddListener(updateListener);

			runAsserts();
		}

		[Test]
		public void testOuterInnerJoin_root_s1()
		{
			/**
			* Query:
			*                  s0
			*           s1 <-      <- s2
			*/
			String joinStatement =
				"select * from " +
				_event_S1 + ".win:length(1000) as s1 " + " right outer join " +
				_event_S0 + ".win:length(1000) as s0 on s0.p00 = s1.p10 " + " right outer join " +
				_event_S2 + ".win:length(1000) as s2 on s0.p00 = s2.p20 ";

			joinView = epService.EPAdministrator.CreateEQL( joinStatement );
			joinView.AddListener(updateListener);

			runAsserts();
		}

		[Test]
		public void testOuterInnerJoin_root_s2()
		{
			/**
			* Query:
			*                  s0
			*           s1 <-      <- s2
			*/
			String joinStatement =
				"select * from " +
				_event_S2 + ".win:length(1000) as s2 " + " left outer join " +
				_event_S0 + ".win:length(1000) as s0 on s0.p00 = s2.p20 " + " left outer join " +
				_event_S1 + ".win:length(1000) as s1 on s0.p00 = s1.p10 ";

			joinView = epService.EPAdministrator.CreateEQL( joinStatement );
			joinView.AddListener(updateListener);

			runAsserts();
		}

		private void runAsserts()
		{
			Object[] s0Events = null;
			Object[] s1Events = null;
			Object[] s2Events = null;

			// Test s0 ... s1 with 1 rows, s2 with 0 rows
			//
			s1Events = SupportBean_S1.makeS1( "A", new String[] { "A-s1-1" } );
			SendEvent( s1Events );
			Assert.IsFalse( updateListener.IsInvoked );

			s0Events = SupportBean_S0.makeS0( "A", new String[] { "A-s0-1" } );
			SendEvent( s0Events );
			Assert.IsFalse( updateListener.IsInvoked );

			// Test s0 ... s1 with 0 rows, s2 with 1 rows
			//
			s2Events = SupportBean_S2.makeS2( "B", new String[] { "B-s2-1" } );
			sendEventsAndReset( s2Events );

			s0Events = SupportBean_S0.makeS0( "B", new String[] { "B-s0-1" } );
			SendEvent( s0Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], null, s2Events[0] } }, getAndResetNewEvents() );

			// Test s0 ... s1 with 1 rows, s2 with 1 rows
			//
			s1Events = SupportBean_S1.makeS1( "C", new String[] { "C-s1-1" } );
			SendEvent( s1Events );
			Assert.IsFalse( updateListener.IsInvoked );

			s2Events = SupportBean_S2.makeS2( "C", new String[] { "C-s2-1" } );
			sendEventsAndReset( s2Events );

			s0Events = SupportBean_S0.makeS0( "C", new String[] { "C-s0-1" } );
			SendEvent( s0Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] } }, getAndResetNewEvents() );

			// Test s0 ... s1 with 2 rows, s2 with 1 rows
			//
			s1Events = SupportBean_S1.makeS1( "D", new String[] { "D-s1-1", "D-s1-2" } );
			SendEvent( s1Events );
			Assert.IsFalse( updateListener.IsInvoked );

			s2Events = SupportBean_S2.makeS2( "D", new String[] { "D-s2-1" } );
			sendEventsAndReset( s2Events );

			s0Events = SupportBean_S0.makeS0( "D", new String[] { "D-s0-1" } );
			SendEvent( s0Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] }, new Object[] { s0Events[0], s1Events[1], s2Events[0] } }, getAndResetNewEvents() );

			// Test s0 ... s1 with 2 rows, s2 with 2 rows
			//
			s1Events = SupportBean_S1.makeS1( "E", new String[] { "E-s1-1", "E-s1-2" } );
			SendEvent( s1Events );
			Assert.IsFalse( updateListener.IsInvoked );

			s2Events = SupportBean_S2.makeS2( "E", new String[] { "E-s2-1", "E-s2-2" } );
			sendEventsAndReset( s2Events );

			s0Events = SupportBean_S0.makeS0( "E", new String[] { "E-s0-1" } );
			SendEvent( s0Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] }, new Object[] { s0Events[0], s1Events[1], s2Events[0] }, new Object[] { s0Events[0], s1Events[0], s2Events[1] }, new Object[] { s0Events[0], s1Events[1], s2Events[1] } }, getAndResetNewEvents() );

			// Test s0 ... s1 with 0 rows, s2 with 2 rows
			//
			s2Events = SupportBean_S2.makeS2( "F", new String[] { "F-s2-1", "F-s2-2" } );
			sendEventsAndReset( s2Events );

			s0Events = SupportBean_S0.makeS0( "F", new String[] { "F-s0-1" } );
			SendEvent( s0Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], null, s2Events[0] }, new Object[] { s0Events[0], null, s2Events[1] } }, getAndResetNewEvents() );

			// Test s1 ... s0 with 0 rows, s2 with 1 rows
			//
			s2Events = SupportBean_S2.makeS2( "H", new String[] { "H-s2-1" } );
			sendEventsAndReset( s2Events );

			s1Events = SupportBean_S1.makeS1( "H", new String[] { "H-s1-1" } );
			SendEvent( s1Events );
			Assert.IsFalse( updateListener.IsInvoked );

			// Test s1 ... s0 with 1 rows, s2 with 0 rows
			//
			s0Events = SupportBean_S0.makeS0( "I", new String[] { "I-s0-1" } );
			sendEventsAndReset( s0Events );

			s1Events = SupportBean_S1.makeS1( "I", new String[] { "I-s1-1" } );
			SendEvent( s1Events );
			Assert.IsFalse( updateListener.IsInvoked );

			// Test s1 ... s0 with 1 rows, s2 with 1 rows
			//
			s0Events = SupportBean_S0.makeS0( "J", new String[] { "J-s0-1" } );
			sendEventsAndReset( s0Events );

			s2Events = SupportBean_S2.makeS2( "J", new String[] { "J-s2-1" } );
			sendEventsAndReset( s2Events );

			s1Events = SupportBean_S1.makeS1( "J", new String[] { "J-s1-1" } );
			SendEvent( s1Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] } }, getAndResetNewEvents() );

			// Test s1 ... s0 with 1 rows, s2 with 2 rows
			//
			s0Events = SupportBean_S0.makeS0( "K", new String[] { "K-s0-1" } );
			sendEventsAndReset( s0Events );

			s2Events = SupportBean_S2.makeS2( "K", new String[] { "K-s2-1", "K-s2-2" } );
			sendEventsAndReset( s2Events );

			s1Events = SupportBean_S1.makeS1( "K", new String[] { "K-s1-1" } );
			SendEvent( s1Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] }, new Object[] { s0Events[0], s1Events[0], s2Events[1] } }, getAndResetNewEvents() );

			// Test s1 ... s0 with 2 rows, s2 with 2 rows
			//
			s0Events = SupportBean_S0.makeS0( "L", new String[] { "L-s0-1", "L-s0-2" } );
			sendEventsAndReset( s0Events );

			s2Events = SupportBean_S2.makeS2( "L", new String[] { "L-s2-1", "L-s2-2" } );
			sendEventsAndReset( s2Events );

			s1Events = SupportBean_S1.makeS1( "L", new String[] { "L-s1-1" } );
			SendEvent( s1Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] }, new Object[] { s0Events[0], s1Events[0], s2Events[1] }, new Object[] { s0Events[1], s1Events[0], s2Events[0] }, new Object[] { s0Events[1], s1Events[0], s2Events[1] } }, getAndResetNewEvents() );

			// Test s2 ... s0 with 0 rows, s1 with 1 rows
			//
			s1Events = SupportBean_S1.makeS1( "P", new String[] { "P-s1-1" } );
			sendEventsAndReset( s1Events );

			s2Events = SupportBean_S2.makeS2( "P", new String[] { "P-s2-1" } );
			SendEvent( s2Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { null, null, s2Events[0] } }, getAndResetNewEvents() );

			// Test s2 ... s1 with 0 rows, s0 with 1 rows
			//
			s0Events = SupportBean_S0.makeS0( "Q", new String[] { "Q-s0-1" } );
			sendEventsAndReset( s0Events );

			s2Events = SupportBean_S2.makeS2( "Q", new String[] { "Q-s2-1" } );
			SendEvent( s2Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], null, s2Events[0] } }, getAndResetNewEvents() );

			// Test s2 ... s1 with 1 rows, s0 with 1 rows
			//
			s0Events = SupportBean_S0.makeS0( "R", new String[] { "R-s0-1" } );
			sendEventsAndReset( s0Events );

			s1Events = SupportBean_S1.makeS1( "R", new String[] { "R-s1-1" } );
			sendEventsAndReset( s1Events );

			s2Events = SupportBean_S2.makeS2( "R", new String[] { "R-s2-1" } );
			SendEvent( s2Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] } }, getAndResetNewEvents() );

			// Test s2 ... s1 with 2 rows, s0 with 1 rows
			//
			s0Events = SupportBean_S0.makeS0( "S", new String[] { "S-s0-1" } );
			sendEventsAndReset( s0Events );

			s1Events = SupportBean_S1.makeS1( "S", new String[] { "S-s1-1", "S-s1-2" } );
			sendEventsAndReset( s1Events );

			s2Events = SupportBean_S2.makeS2( "S", new String[] { "S-s2-1" } );
			SendEvent( s2Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] }, new Object[] { s0Events[0], s1Events[1], s2Events[0] } }, getAndResetNewEvents() );

			// Test s2 ... s1 with 0 rows, s0 with 2 rows
			//
			s0Events = SupportBean_S0.makeS0( "T", new String[] { "T-s0-1", "T-s0-1" } );
			sendEventsAndReset( s0Events );

			s2Events = SupportBean_S2.makeS2( "T", new String[] { "T-s2-1" } );
			SendEvent( s2Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], null, s2Events[0] }, new Object[] { s0Events[1], null, s2Events[0] } }, getAndResetNewEvents() );

			// Test s2 ... s1 with 1 rows, s0 with 2 rows
			//
			s0Events = SupportBean_S0.makeS0( "U", new String[] { "U-s0-1", "U-s0-1" } );
			sendEventsAndReset( s0Events );

			s1Events = SupportBean_S1.makeS1( "U", new String[] { "U-s1-1" } );
			sendEventsAndReset( s1Events );

			s2Events = SupportBean_S2.makeS2( "U", new String[] { "U-s2-1" } );
			SendEvent( s2Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] }, new Object[] { s0Events[1], s1Events[0], s2Events[0] } }, getAndResetNewEvents() );

			// Test s2 ... s1 with 2 rows, s0 with 2 rows
			//
			s0Events = SupportBean_S0.makeS0( "V", new String[] { "V-s0-1", "V-s0-1" } );
			sendEventsAndReset( s0Events );

			s1Events = SupportBean_S1.makeS1( "V", new String[] { "V-s1-1", "V-s1-1" } );
			sendEventsAndReset( s1Events );

			s2Events = SupportBean_S2.makeS2( "V", new String[] { "V-s2-1" } );
			SendEvent( s2Events );
			ArrayAssertionUtil.AssertRefAnyOrderArr( new Object[][] { new Object[] { s0Events[0], s1Events[0], s2Events[0] }, new Object[] { s0Events[0], s1Events[1], s2Events[0] }, new Object[] { s0Events[1], s1Events[0], s2Events[0] }, new Object[] { s0Events[1], s1Events[1], s2Events[0] } }, getAndResetNewEvents() );
		}

		private void SendEvent( Object _event )
		{
			epService.EPRuntime.SendEvent( _event );
		}

		private void sendEventsAndReset( Object[] events )
		{
			SendEvent( events );
			updateListener.Reset();
		}

		private void SendEvent( Object[] events )
		{
			for ( int i = 0 ; i < events.Length ; i++ )
			{
				epService.EPRuntime.SendEvent( events[i] );
			}
		}
		static Test3StreamOuterJoinVarB()
		{
            _event_S0 = typeof(SupportBean_S0).FullName;
            _event_S1 = typeof(SupportBean_S1).FullName;
            _event_S2 = typeof(SupportBean_S2).FullName;
		}
	}
}
