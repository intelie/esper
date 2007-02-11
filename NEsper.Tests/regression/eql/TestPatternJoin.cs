using System;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestPatternJoin
	{
		private EPServiceProvider epService;

		[SetUp]
		public virtual void setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}

		[Test]
		public virtual void testPatternFilterJoin()
		{
			String stmtText = "select es0a.id as es0aId, " + "es0a.p00 as es0ap00, " + "es0b.id as es0bId, " + "es0b.p00 as es0bp00, " + "s1.id as s1Id, " + "s1.p10 as s1p10 " + " from " + " pattern [every (es0a=" + typeof( SupportBean_S0 ).FullName + "(p00='a') " + "or es0b=" + typeof( SupportBean_S0 ).FullName + "(p00='b'))].win:length(5) as s0," + typeof( SupportBean_S1 ).FullName + ".win:length(5) as s1" + " where (es0a.id = s1.id) or (es0b.id = s1.id)";
			EPStatement statement = epService.EPAdministrator.createEQL( stmtText );

			SupportUpdateListener updateListener = new SupportUpdateListener();
			statement.AddListener( updateListener );

			sendEventS1( 1, "s1A" );
			sendEventS0( 2, "a" );
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			sendEventS0( 1, "b" );
			EventBean _event = updateListener.assertOneGetNewAndReset();
			assertEventData( _event, null, null, 1, "b", 1, "s1A" );

			sendEventS1( 2, "s2A" );
			_event = updateListener.assertOneGetNewAndReset();
			assertEventData( _event, 2, "a", null, null, 2, "s2A" );

			sendEventS1( 20, "s20A" );
			sendEventS1( 30, "s30A" );
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			sendEventS0( 20, "a" );
			_event = updateListener.assertOneGetNewAndReset();
			assertEventData( _event, 20, "a", null, null, 20, "s20A" );

			sendEventS0( 20, "b" );
			_event = updateListener.assertOneGetNewAndReset();
			assertEventData( _event, null, null, 20, "b", 20, "s20A" );

			sendEventS0( 30, "c" ); // filtered out
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			sendEventS0( 40, "a" ); // not matching id in s1
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			sendEventS0( 50, "b" ); // pushing an event s0(2, "a") out the window
			_event = updateListener.assertOneGetOldAndReset();
			assertEventData( _event, 2, "a", null, null, 2, "s2A" );

			// Stop statement
			statement.Stop();

			sendEventS1( 60, "s20" );
			sendEventS0( 70, "a" );
			sendEventS0( 71, "b" );
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			// Start statement
			statement.Start();

			sendEventS1( 70, "s1-70" );
			sendEventS0( 60, "a" );
			sendEventS1( 20, "s1" );
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			sendEventS0( 70, "b" );
			_event = updateListener.assertOneGetNewAndReset();
			assertEventData( _event, null, null, 70, "b", 70, "s1-70" );
		}

		[Test]
		public virtual void test2PatternJoinSelect()
		{
			String stmtText =
				"select s0.es0.id as s0es0Id," +
				"s0.es1.id as s0es1Id, " +
				"s1.es2.id as s1es2Id, " +
				"s1.es3.id as s1es3Id, " +
				"es0.p00 as es0p00, " +
				"es1.p10 as es1p10, " +
				"es2.p20 as es2p20, " +
				"es3.p30 as es3p30" +
				" from " +
				" pattern [every (es0=" + typeof( SupportBean_S0 ).FullName +
				" and es1=" + typeof( SupportBean_S1 ).FullName + ")].win:length(3) as s0," + " pattern [every (es2=" + typeof( SupportBean_S2 ).FullName +
				" and es3=" + typeof( SupportBean_S3 ).FullName + ")].win:length(3) as s1" +
				" where s0.es0.id = s1.es2.id";

			EPStatement statement = epService.EPAdministrator.createEQL( stmtText );

			SupportUpdateListener updateListener = new SupportUpdateListener();
			statement.AddListener( updateListener );

			sendEventS3( 2, "d" );
			sendEventS0( 3, "a" );
			sendEventS2( 3, "c" );
			sendEventS1( 1, "b" );
			EventBean _event = updateListener.assertOneGetNewAndReset();
			assertEventData( _event, 3, 1, 3, 2, "a", "b", "c", "d" );

			sendEventS0( 11, "a1" );
			sendEventS2( 13, "c1" );
			sendEventS1( 12, "b1" );
			sendEventS3( 15, "d1" );
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			sendEventS3( 25, "d2" );
			sendEventS0( 21, "a2" );
			sendEventS2( 21, "c2" );
			sendEventS1( 26, "b2" );
			_event = updateListener.assertOneGetNewAndReset();
			assertEventData( _event, 21, 26, 21, 25, "a2", "b2", "c2", "d2" );

			sendEventS0( 31, "a3" );
			sendEventS1( 32, "b3" );
			_event = updateListener.assertOneGetOldAndReset(); // event moving out of window
			assertEventData( _event, 3, 1, 3, 2, "a", "b", "c", "d" );
			sendEventS2( 33, "c3" );
			sendEventS3( 35, "d3" );
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			sendEventS0( 41, "a4" );
			sendEventS2( 43, "c4" );
			sendEventS1( 42, "b4" );
			sendEventS3( 45, "d4" );
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			// Stop statement
			statement.Stop();

			sendEventS3( 52, "d5" );
			sendEventS0( 53, "a5" );
			sendEventS2( 53, "c5" );
			sendEventS1( 51, "b5" );
			Assert.IsFalse( updateListener.getAndClearIsInvoked() );

			// Start statement
			statement.Start();

			sendEventS3( 55, "d6" );
			sendEventS0( 51, "a6" );
			sendEventS2( 51, "c6" );
			sendEventS1( 56, "b6" );
			_event = updateListener.assertOneGetNewAndReset();
			assertEventData( _event, 51, 56, 51, 55, "a6", "b6", "c6", "d6" );
		}

		[Test]
		public virtual void test2PatternJoinWildcard()
		{
			String stmtText =
				"select * " +
				" from " +
				" pattern [every (es0=" + typeof( SupportBean_S0 ).FullName +
				" and es1=" + typeof( SupportBean_S1 ).FullName + ")].win:length(5) as s0," +
				" pattern [every (es2=" + typeof( SupportBean_S2 ).FullName +
				" and es3=" + typeof( SupportBean_S3 ).FullName + ")].win:length(5) as s1" +
				" where s0.es0.id = s1.es2.id";
			EPStatement statement = epService.EPAdministrator.createEQL( stmtText );

			SupportUpdateListener updateListener = new SupportUpdateListener();
			statement.AddListener( updateListener );

			SupportBean_S0 s0 = sendEventS0( 100, "" );
			SupportBean_S1 s1 = sendEventS1( 1, "" );
			SupportBean_S2 s2 = sendEventS2( 100, "" );
			SupportBean_S3 s3 = sendEventS3( 2, "" );

			EventBean _event = updateListener.assertOneGetNewAndReset();

			EDictionary<string, EventBean> result = (EDictionary<string, EventBean>) _event["s0"];
			Assert.AreSame( s0, result.Fetch( "es0" ).Underlying );
			Assert.AreSame( s1, result.Fetch( "es1" ).Underlying );

			result = (EDictionary<String, EventBean>) _event["s1"];
			Assert.AreSame( s2, result.Fetch( "es2" ).Underlying );
			Assert.AreSame( s3, result.Fetch( "es3" ).Underlying );
		}

		private SupportBean_S0 sendEventS0( int id, String p00 )
		{
			SupportBean_S0 _event = new SupportBean_S0( id, p00 );
			epService.EPRuntime.SendEvent( _event );
			return _event;
		}

		private SupportBean_S1 sendEventS1( int id, String p10 )
		{
			SupportBean_S1 _event = new SupportBean_S1( id, p10 );
			epService.EPRuntime.SendEvent( _event );
			return _event;
		}

		private SupportBean_S2 sendEventS2( int id, String p20 )
		{
			SupportBean_S2 _event = new SupportBean_S2( id, p20 );
			epService.EPRuntime.SendEvent( _event );
			return _event;
		}

		private SupportBean_S3 sendEventS3( int id, String p30 )
		{
			SupportBean_S3 _event = new SupportBean_S3( id, p30 );
			epService.EPRuntime.SendEvent( _event );
			return _event;
		}

		private void assertEventData( EventBean _event, int s0es0Id, int s0es1Id, int s1es2Id, int s1es3Id, String p00, String p10, String p20, String p30 )
		{
			Assert.AreEqual( s0es0Id, _event["s0es0Id"] );
			Assert.AreEqual( s0es1Id, _event["s0es1Id"] );
			Assert.AreEqual( s1es2Id, _event["s1es2Id"] );
			Assert.AreEqual( s1es3Id, _event["s1es3Id"] );
			Assert.AreEqual( p00, _event["es0p00"] );
			Assert.AreEqual( p10, _event["es1p10"] );
			Assert.AreEqual( p20, _event["es2p20"] );
			Assert.AreEqual( p30, _event["es3p30"] );
		}

		private void assertEventData(
            EventBean _event,
            int? es0aId, String es0ap00, 
            int? es0bId, String es0bp00,
            int s1Id, String s1p10 )
		{
			Assert.AreEqual( es0aId, _event["es0aId"] );
			Assert.AreEqual( es0ap00, _event["es0ap00"] );
			Assert.AreEqual( es0bId, _event["es0bId"] );
			Assert.AreEqual( es0bp00, _event["es0bp00"] );
			Assert.AreEqual( s1Id, _event["s1Id"] );
			Assert.AreEqual( s1p10, _event["s1p10"] );
		}
	}
}
