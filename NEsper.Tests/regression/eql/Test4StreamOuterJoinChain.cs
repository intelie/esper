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
	public class Test4StreamOuterJoinChain
	{
		private EPServiceProvider epService;
		private EPStatement joinView;
		private SupportUpdateListener updateListener;

		private static readonly String _event_S0;
		private static readonly String _event_S1;
		private static readonly String _event_S2;
		private static readonly String _event_S3;

		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
			epService.Initialize();
			updateListener = new SupportUpdateListener();
		}

		private Object[][] getAndResetNewEvents()
		{
			EventBean[] newEvents = updateListener.LastNewData;
			updateListener.Reset();
			return ArrayHandlingUtil.GetUnderlyingEvents( newEvents, new String[] { "s0", "s1", "s2", "s3" } );
		}

		[Test]
		public virtual void  testLeftOuterJoin_root_s0()
		{
			/**
			* Query:
			*          s0
			*             -> s1
			*                  -> s2
			*                      -> s3
			*/
			String joinStatement =
				"select * from " +
				_event_S0 + ".win:length(1000) as s0 " + " left outer join " +
				_event_S1 + ".win:length(1000) as s1 on s0.p00 = s1.p10 " + " left outer join " +
				_event_S2 + ".win:length(1000) as s2 on s1.p10 = s2.p20 " + " left outer join " +
				_event_S3 + ".win:length(1000) as s3 on s2.p20 = s3.p30 ";

			joinView = epService.EPAdministrator.CreateEQL(joinStatement);
			joinView.AddListener(updateListener);

			runAsserts();
		}

		[Test]
		public virtual void  testLeftOuterJoin_root_s1()
		{
			/**
			* Query:
			*          s0
			*             -> s1
			*                  -> s2
			*                      -> s3
			*/
			String joinStatement =
				"select * from " +
				_event_S1 + ".win:length(1000) as s1 " + " right outer join " +
				_event_S0 + ".win:length(1000) as s0 on s0.p00 = s1.p10 " + " left outer join " +
				_event_S2 + ".win:length(1000) as s2 on s1.p10 = s2.p20 " + " left outer join " +
				_event_S3 + ".win:length(1000) as s3 on s2.p20 = s3.p30 ";

			joinView = epService.EPAdministrator.CreateEQL(joinStatement);
			joinView.AddListener(updateListener);

			runAsserts();
		}

		[Test]
		public virtual void  testLeftOuterJoin_root_s2()
		{
			/**
			* Query:
			*          s0
			*             -> s1
			*                  -> s2
			*                      -> s3
			*/
			String joinStatement =
				"select * from " +
				_event_S2 + ".win:length(1000) as s2 " + " right outer join " +
				_event_S1 + ".win:length(1000) as s1 on s2.p20 = s1.p10 " + " right outer join " +
				_event_S0 + ".win:length(1000) as s0 on s1.p10 = s0.p00 " + " left outer join " +
				_event_S3 + ".win:length(1000) as s3 on s2.p20 = s3.p30 ";

			joinView = epService.EPAdministrator.CreateEQL(joinStatement);
			joinView.AddListener(updateListener);

			runAsserts();
		}

		[Test]
		public virtual void  testLeftOuterJoin_root_s3()
		{
			/**
			* Query:
			*          s0
			*             -> s1
			*                  -> s2
			*                      -> s3
			*/
			String joinStatement =
				"select * from " +
				_event_S3 + ".win:length(1000) as s3 " + " right outer join " +
				_event_S2 + ".win:length(1000) as s2 on s3.p30 = s2.p20 " + " right outer join " +
				_event_S1 + ".win:length(1000) as s1 on s2.p20 = s1.p10 " + " right outer join " +
				_event_S0 + ".win:length(1000) as s0 on s1.p10 = s0.p00 ";

			joinView = epService.EPAdministrator.CreateEQL(joinStatement);
			joinView.AddListener(updateListener);

			runAsserts();
		}

		private void  runAsserts()
		{
			Object[] s0Events, s1Events, s2Events, s3Events;

			// Test s0 and s1=1, s2=1, s3=1
			//
			s1Events = SupportBean_S1.makeS1("A", new String[]{"A-s1-1"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s2Events = SupportBean_S2.makeS2("A", new String[]{"A-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s3Events = SupportBean_S3.makeS3("A", new String[]{"A-s3-1"});
			SendEvent(s3Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s0Events = SupportBean_S0.makeS0("A", new String[]{"A-s0-1"});
			SendEvent(s0Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}}, getAndResetNewEvents());

			// Test s0 and s1=1, s2=0, s3=0
			//
			s1Events = SupportBean_S1.makeS1("B", new String[]{"B-s1-1"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s0Events = SupportBean_S0.makeS0("B", new String[]{"B-s0-1"});
			SendEvent(s0Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], null, null}}, getAndResetNewEvents());

			// Test s0 and s1=1, s2=1, s3=0
			//
			s1Events = SupportBean_S1.makeS1("C", new String[]{"C-s1-1"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s2Events = SupportBean_S2.makeS2("C", new String[]{"C-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s0Events = SupportBean_S0.makeS0("C", new String[]{"C-s0-1"});
			SendEvent(s0Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], null}}, getAndResetNewEvents());

			// Test s0 and s1=2, s2=0, s3=0
			//
			s1Events = SupportBean_S1.makeS1("D", new String[]{"D-s1-1", "D-s1-2"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s2Events = SupportBean_S2.makeS2("D", new String[]{"D-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s0Events = SupportBean_S0.makeS0("D", new String[]{"D-s0-1"});
			SendEvent(s0Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], null}, new Object[]{s0Events[0], s1Events[1], s2Events[0], null}}, getAndResetNewEvents());

			// Test s0 and s1=2, s2=2, s3=0
			//
			s1Events = SupportBean_S1.makeS1("E", new String[]{"E-s1-1", "E-s1-2"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s2Events = SupportBean_S2.makeS2("E", new String[]{"E-s2-1", "E-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s0Events = SupportBean_S0.makeS0("E", new String[]{"E-s0-1"});
			SendEvent(s0Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], null}, new Object[]{s0Events[0], s1Events[1], s2Events[0], null}, new Object[]{s0Events[0], s1Events[0], s2Events[1], null}, new Object[]{s0Events[0], s1Events[1], s2Events[1], null}}, getAndResetNewEvents());

			// Test s0 and s1=2, s2=2, s3=1
			//
			s1Events = SupportBean_S1.makeS1("F", new String[]{"F-s1-1", "F-s1-2"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s2Events = SupportBean_S2.makeS2("F", new String[]{"F-s2-1", "F-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s3Events = SupportBean_S3.makeS3("F", new String[]{"F-s3-1"});
			SendEvent(s3Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s0Events = SupportBean_S0.makeS0("F", new String[]{"F-s0-1"});
			SendEvent(s0Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[1], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[1], s3Events[0]}}, getAndResetNewEvents());

			// Test s0 and s1=2, s2=2, s3=2
			//
			s1Events = SupportBean_S1.makeS1("G", new String[]{"G-s1-1", "G-s1-2"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s2Events = SupportBean_S2.makeS2("G", new String[]{"G-s2-1", "G-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s3Events = SupportBean_S3.makeS3("G", new String[]{"G-s3-1", "G-s3-2"});
			SendEvent(s3Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s0Events = SupportBean_S0.makeS0("G", new String[]{"G-s0-1"});
			SendEvent(s0Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[1], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[1], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[1]}, new Object[]{s0Events[0], s1Events[1], s2Events[0], s3Events[1]}, new Object[]{s0Events[0], s1Events[0], s2Events[1], s3Events[1]}, new Object[]{s0Events[0], s1Events[1], s2Events[1], s3Events[1]}}, getAndResetNewEvents());

			// Test s0 and s1=1, s2=1, s3=3
			//
			s1Events = SupportBean_S1.makeS1("H", new String[]{"H-s1-1"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s2Events = SupportBean_S2.makeS2("H", new String[]{"H-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s3Events = SupportBean_S3.makeS3("H", new String[]{"H-s3-1", "H-s3-2", "H-s3-3"});
			SendEvent(s3Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s0Events = SupportBean_S0.makeS0("H", new String[]{"H-s0-1"});
			SendEvent(s0Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[1]}, new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[2]}}, getAndResetNewEvents());

			// Test s3 and s0=0, s1=0, s2=0
			//
			s3Events = SupportBean_S3.makeS3("I", new String[]{"I-s3-1"});
			SendEvent(s3Events);
			Assert.IsFalse(updateListener.IsInvoked);

			// Test s3 and s0=0, s1=0, s2=1
			//
			s2Events = SupportBean_S2.makeS2("J", new String[]{"J-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s3Events = SupportBean_S3.makeS3("J", new String[]{"J-s3-1"});
			SendEvent(s3Events);
			Assert.IsFalse(updateListener.IsInvoked);

			// Test s3 and s0=0, s1=1, s2=1
			//
			s2Events = SupportBean_S2.makeS2("K", new String[]{"K-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s1Events = SupportBean_S1.makeS1("K", new String[]{"K-s1-1"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			s3Events = SupportBean_S3.makeS3("K", new String[]{"K-s3-1"});
			SendEvent(s3Events);
			Assert.IsFalse(updateListener.IsInvoked);

			// Test s3 and s0=1, s1=1, s2=1
			//
			s0Events = SupportBean_S0.makeS0("M", new String[]{"M-s0-1"});
			sendEventsAndReset(s0Events);

			s1Events = SupportBean_S1.makeS1("M", new String[]{"M-s1-1"});
			sendEventsAndReset(s1Events);

			s2Events = SupportBean_S2.makeS2("M", new String[]{"M-s2-1"});
			sendEventsAndReset(s2Events);

			s3Events = SupportBean_S3.makeS3("M", new String[]{"M-s3-1"});
			SendEvent(s3Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}}, getAndResetNewEvents());

			// Test s3 and s0=1, s1=2, s2=1
			//
			s0Events = SupportBean_S0.makeS0("N", new String[]{"N-s0-1"});
			sendEventsAndReset(s0Events);

			s1Events = SupportBean_S1.makeS1("N", new String[]{"N-s1-1", "N-s1-2"});
			sendEventsAndReset(s1Events);

			s2Events = SupportBean_S2.makeS2("N", new String[]{"N-s2-1"});
			sendEventsAndReset(s2Events);

			s3Events = SupportBean_S3.makeS3("N", new String[]{"N-s3-1"});
			SendEvent(s3Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[0], s3Events[0]}}, getAndResetNewEvents());

			// Test s3 and s0=1, s1=2, s2=3
			//
			s0Events = SupportBean_S0.makeS0("O", new String[]{"O-s0-1"});
			sendEventsAndReset(s0Events);

			s1Events = SupportBean_S1.makeS1("O", new String[]{"O-s1-1", "O-s1-2"});
			sendEventsAndReset(s1Events);

			s2Events = SupportBean_S2.makeS2("O", new String[]{"O-s2-1", "O-s2-2", "O-s2-3"});
			sendEventsAndReset(s2Events);

			s3Events = SupportBean_S3.makeS3("O", new String[]{"O-s3-1"});
			SendEvent(s3Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[1], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[1], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[2], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[2], s3Events[0]}}, getAndResetNewEvents());

			// Test s3 and s0=2, s1=2, s2=3
			//
			s0Events = SupportBean_S0.makeS0("P", new String[]{"P-s0-1", "P-s0-2"});
			sendEventsAndReset(s0Events);

			s1Events = SupportBean_S1.makeS1("P", new String[]{"P-s1-1", "P-s1-2"});
			sendEventsAndReset(s1Events);

			s2Events = SupportBean_S2.makeS2("P", new String[]{"P-s2-1", "P-s2-2", "P-s2-3"});
			sendEventsAndReset(s2Events);

			s3Events = SupportBean_S3.makeS3("P", new String[]{"P-s3-1"});
			SendEvent(s3Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[1], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[1], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[2], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[2], s3Events[0]}, new Object[]{s0Events[1], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[1], s1Events[1], s2Events[0], s3Events[0]}, new Object[]{s0Events[1], s1Events[0], s2Events[1], s3Events[0]}, new Object[]{s0Events[1], s1Events[1], s2Events[1], s3Events[0]}, new Object[]{s0Events[1], s1Events[0], s2Events[2], s3Events[0]}, new Object[]{s0Events[1], s1Events[1], s2Events[2], s3Events[0]}}, getAndResetNewEvents());

			// Test s1 and s0=0, s2=1, s3=0
			//
			s2Events = SupportBean_S2.makeS2("Q", new String[]{"Q-s2-1"});
			sendEventsAndReset(s2Events);

			s1Events = SupportBean_S1.makeS1("Q", new String[]{"Q-s1-1"});
			SendEvent(s1Events);
			Assert.IsFalse(updateListener.IsInvoked);

			// Test s1 and s0=2, s2=1, s3=0
			//
			s0Events = SupportBean_S0.makeS0("R", new String[]{"R-s0-1", "R-s0-2"});
			sendEventsAndReset(s0Events);

			s2Events = SupportBean_S2.makeS2("R", new String[]{"R-s2-1"});
			sendEventsAndReset(s2Events);

			s1Events = SupportBean_S1.makeS1("R", new String[]{"R-s1-1"});
			SendEvent(s1Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], null}, new Object[]{s0Events[1], s1Events[0], s2Events[0], null}}, getAndResetNewEvents());

			// Test s1 and s0=2, s2=2, s3=2
			//
			s0Events = SupportBean_S0.makeS0("S", new String[]{"S-s0-1", "S-s0-2"});
			sendEventsAndReset(s0Events);

			s2Events = SupportBean_S2.makeS2("S", new String[]{"S-s2-1"});
			sendEventsAndReset(s2Events);

			s3Events = SupportBean_S3.makeS3("S", new String[]{"S-s3-1", "S-s3-1"});
			sendEventsAndReset(s3Events);

			s1Events = SupportBean_S1.makeS1("S", new String[]{"S-s1-1"});
			SendEvent(s1Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[1], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[1]}, new Object[]{s0Events[1], s1Events[0], s2Events[0], s3Events[1]}}, getAndResetNewEvents());

			// Test s2 and s0=0, s1=0, s3=1
			//
			s3Events = SupportBean_S3.makeS3("T", new String[]{"T-s3-1"});
			sendEventsAndReset(s3Events);

			s2Events = SupportBean_S2.makeS2("T", new String[]{"T-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			// Test s2 and s0=0, s1=1, s3=1
			//
			s3Events = SupportBean_S3.makeS3("U", new String[]{"U-s3-1"});
			sendEventsAndReset(s3Events);

			s1Events = SupportBean_S1.makeS1("U", new String[]{"U-s1-1"});
			SendEvent(s1Events);

			s2Events = SupportBean_S2.makeS2("U", new String[]{"U-s2-1"});
			SendEvent(s2Events);
			Assert.IsFalse(updateListener.IsInvoked);

			// Test s2 and s0=1, s1=1, s3=1
			//
			s0Events = SupportBean_S0.makeS0("V", new String[]{"V-s0-1"});
			sendEventsAndReset(s0Events);

			s1Events = SupportBean_S1.makeS1("V", new String[]{"V-s1-1"});
			SendEvent(s1Events);

			s3Events = SupportBean_S3.makeS3("V", new String[]{"V-s3-1"});
			sendEventsAndReset(s3Events);

			s2Events = SupportBean_S2.makeS2("V", new String[]{"V-s2-1"});
			SendEvent(s2Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}}, getAndResetNewEvents());

			// Test s2 and s0=2, s1=2, s3=0
			//
			s0Events = SupportBean_S0.makeS0("W", new String[]{"W-s0-1", "W-s0-2"});
			sendEventsAndReset(s0Events);

			s1Events = SupportBean_S1.makeS1("W", new String[]{"W-s1-1", "W-s1-2"});
			SendEvent(s1Events);

			s2Events = SupportBean_S2.makeS2("W", new String[]{"W-s2-1"});
			SendEvent(s2Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], null}, new Object[]{s0Events[0], s1Events[1], s2Events[0], null}, new Object[]{s0Events[1], s1Events[0], s2Events[0], null}, new Object[]{s0Events[1], s1Events[1], s2Events[0], null}}, getAndResetNewEvents());

			// Test s2 and s0=2, s1=2, s3=2
			//
			s0Events = SupportBean_S0.makeS0("X", new String[]{"X-s0-1", "X-s0-2"});
			sendEventsAndReset(s0Events);

			s1Events = SupportBean_S1.makeS1("X", new String[]{"X-s1-1", "X-s1-2"});
			SendEvent(s1Events);

			s3Events = SupportBean_S3.makeS3("X", new String[]{"X-s3-1", "X-s3-2"});
			sendEventsAndReset(s3Events);

			s2Events = SupportBean_S2.makeS2("X", new String[]{"X-s2-1"});
			SendEvent(s2Events);
			ArrayAssertionUtil.AssertRefAnyOrderArr(new Object[][]{new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[1], s2Events[0], s3Events[0]}, new Object[]{s0Events[1], s1Events[0], s2Events[0], s3Events[0]}, new Object[]{s0Events[1], s1Events[1], s2Events[0], s3Events[0]}, new Object[]{s0Events[0], s1Events[0], s2Events[0], s3Events[1]}, new Object[]{s0Events[0], s1Events[1], s2Events[0], s3Events[1]}, new Object[]{s0Events[1], s1Events[0], s2Events[0], s3Events[1]}, new Object[]{s0Events[1], s1Events[1], s2Events[0], s3Events[1]}}, getAndResetNewEvents());
		}

		private void  SendEvent(Object _event)
		{
			epService.EPRuntime.SendEvent(_event);
		}

		private void  sendEventsAndReset(Object[] events)
		{
			SendEvent(events);
			updateListener.Reset();
		}

		private void  SendEvent(Object[] events)
		{
			for (int i = 0; i < events.Length; i++)
			{
				epService.EPRuntime.SendEvent(events[i]);
			}
		}
		static Test4StreamOuterJoinChain()
		{
			_event_S0 = typeof(SupportBean_S0).FullName;
			_event_S1 = typeof(SupportBean_S1).FullName;
			_event_S2 = typeof(SupportBean_S2).FullName;
			_event_S3 = typeof(SupportBean_S3).FullName;
		}
	}
}
