using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestInsertInto
	{
		private EPServiceProvider epService;
		private SupportUpdateListener feedListener;
		private SupportUpdateListener resultListenerDelta;
		private SupportUpdateListener resultListenerProduct;

		[SetUp]
		public virtual void setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			feedListener = new SupportUpdateListener();
			resultListenerDelta = new SupportUpdateListener();
			resultListenerProduct = new SupportUpdateListener();

			// Use external clocking for the test
			epService.EPRuntime.SendEvent( new TimerControlEvent( TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL ) );
		}

		[Test]
		public virtual void testVariantOne()
		{
			String stmtText =
				"insert into Event_1 (delta, product) " +
				"select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
				"from " + typeof( SupportBean ).FullName + ".win:length(100)";

			runAsserts( stmtText );
		}

		[Test]
		public virtual void testVariantOneJoin()
		{
			String stmtText =
				"insert into Event_1 (delta, product) " +
				"select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
				"from " + typeof( SupportBean ).FullName + ".win:length(100) as s0," + typeof( SupportBean_A ).FullName + ".win:length(100) as s1 " +
				" where s0.str = s1.id";

			runAsserts( stmtText );
		}

		[Test]
		public virtual void testVariantTwo()
		{
			String stmtText =
				"insert into Event_1 " +
				"select intPrimitive - intBoxed as delta, intPrimitive * intBoxed as product " +
				"from " + typeof( SupportBean ).FullName + ".win:length(100)";

			runAsserts( stmtText );
		}

		[Test]
		public virtual void testVariantTwoJoin()
		{
			String stmtText =
				"insert into Event_1 " +
				"select intPrimitive - intBoxed as delta, intPrimitive * intBoxed as product " +
				"from " + typeof( SupportBean ).FullName + ".win:length(100) as s0," + typeof( SupportBean_A ).FullName + ".win:length(100) as s1 " +
				" where s0.str = s1.id";

			runAsserts( stmtText );
		}

		[Test]
		public virtual void testInvalidStreamUsed()
		{
			String stmtText =
				"insert into Event_1 (delta, product) " +
				"select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
				"from " + typeof( SupportBean ).FullName + ".win:length(100)";
			epService.EPAdministrator.CreateEQL( stmtText );

			try
			{
				stmtText =
					"insert into Event_1 (delta) " +
					"select intPrimitive - intBoxed as deltaTag " +
					"from " + typeof( SupportBean ).FullName + ".win:length(100)";
				epService.EPAdministrator.CreateEQL( stmtText );
				Assert.Fail();
			}
			catch ( EPStatementException ex )
			{
				// expected
				Assert.AreEqual( "Error Starting view: Event type named 'Event_1' has already been declared with differing type information [insert into Event_1 (delta) select intPrimitive - intBoxed as deltaTag from net.esper.support.bean.SupportBean.win:length(100)]", ex.Message );
			}
		}

		[Test]
		public virtual void testWithOutputLimitAndSort()
		{
			// NOTICE: we are inserting the RSTREAM (removed events)
			String stmtText =
				"insert rstream into StockTicks(mySymbol, myPrice) " +
				"select symbol, price from " + typeof( SupportMarketDataBean ).FullName + ".win:time(60) " + "output every 5 seconds " +
				"order by symbol asc";
			epService.EPAdministrator.CreateEQL( stmtText );

			stmtText = "select mySymbol, sum(myPrice) as pricesum from StockTicks.win:length(100)";
			EPStatement statement = epService.EPAdministrator.CreateEQL( stmtText );
            statement.AddListener(feedListener.Update);

			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 0 ) );
			SendEvent( "IBM", 50 );
			SendEvent( "CSC", 10 );
			SendEvent( "GE", 20 );
			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 10 * 1000 ) );
			SendEvent( "DEF", 100 );
			SendEvent( "ABC", 11 );
			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 20 * 1000 ) );
			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 30 * 1000 ) );
			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 40 * 1000 ) );
			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 50 * 1000 ) );
			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 55 * 1000 ) );

			Assert.IsFalse( feedListener.Invoked );
			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 60 * 1000 ) );

			Assert.IsTrue( feedListener.Invoked );
			Assert.AreEqual( 3, feedListener.NewDataList.Count );
			Assert.AreEqual( "CSC", feedListener.NewDataList[0][0]["mySymbol"] );
			Assert.AreEqual( 10.0, feedListener.NewDataList[0][0]["pricesum"] );
			Assert.AreEqual( "GE", feedListener.NewDataList[1][0]["mySymbol"] );
			Assert.AreEqual( 30.0, feedListener.NewDataList[1][0]["pricesum"] );
			Assert.AreEqual( "IBM", feedListener.NewDataList[2][0]["mySymbol"] );
			Assert.AreEqual( 80.0, feedListener.NewDataList[2][0]["pricesum"] );
			feedListener.reset();

			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 65 * 1000 ) );
			Assert.IsFalse( feedListener.Invoked );

			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 70 * 1000 ) );
			Assert.AreEqual( "ABC", feedListener.NewDataList[0][0]["mySymbol"] );
			Assert.AreEqual( 91.0, feedListener.NewDataList[0][0]["pricesum"] );
			Assert.AreEqual( "DEF", feedListener.NewDataList[1][0]["mySymbol"] );
			Assert.AreEqual( 191.0, feedListener.NewDataList[1][0]["pricesum"] );
		}

		private void SendEvent( String symbol, double price )
		{
			SupportMarketDataBean bean = new SupportMarketDataBean( symbol, price, 0L, null );
			epService.EPRuntime.SendEvent( bean );
		}

		private void runAsserts( String stmtText )
		{
			// Attach listener to feed
			EPStatement stmt = epService.EPAdministrator.CreateEQL( stmtText );
            stmt.AddListener(feedListener.Update);

			// send event for joins to match on
			epService.EPRuntime.SendEvent( new SupportBean_A( "myId" ) );

			// Attach delta statement to statement and add listener
			stmtText =
				"select min(delta) as minD, max(delta) as maxD " +
				"from Event_1.win:time(60 seconds)";
			stmt = epService.EPAdministrator.CreateEQL( stmtText );
            stmt.AddListener(resultListenerDelta.Update);

			// Attach prodict statement to statement and add listener
			stmtText =
				"select min(product) as minP, max(product) as maxP " +
				"from Event_1.win:time(60 seconds)";
			stmt = epService.EPAdministrator.CreateEQL( stmtText );
            stmt.AddListener(resultListenerProduct.Update);

			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 0 ) ); // Set the time to 0 seconds

			// send events
			SendEvent( 20, 10 );
			assertReceivedFeed( 10, 200 );
			assertReceivedMinMax( 10, 10, 200, 200 );

			SendEvent( 50, 25 );
			assertReceivedFeed( 25, 25 * 50 );
			assertReceivedMinMax( 10, 25, 200, 1250 );

			SendEvent( 5, 2 );
			assertReceivedFeed( 3, 2 * 5 );
			assertReceivedMinMax( 3, 25, 10, 1250 );

			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 10 * 1000 ) ); // Set the time to 10 seconds

			SendEvent( 13, 1 );
			assertReceivedFeed( 12, 13 );
			assertReceivedMinMax( 3, 25, 10, 1250 );

			epService.EPRuntime.SendEvent( new CurrentTimeEvent( 61 * 1000 ) ); // Set the time to 61 seconds
			assertReceivedMinMax( 12, 12, 13, 13 );
		}

		private void assertReceivedMinMax( int minDelta, int maxDelta, int minProduct, int maxProduct )
		{
			Assert.AreEqual( 1, resultListenerDelta.NewDataList.Count );
			Assert.AreEqual( 1, resultListenerDelta.LastNewData.Length );
			Assert.AreEqual( 1, resultListenerProduct.NewDataList.Count );
			Assert.AreEqual( 1, resultListenerProduct.LastNewData.Length );
			Assert.AreEqual( minDelta, resultListenerDelta.LastNewData[0]["minD"] );
			Assert.AreEqual( maxDelta, resultListenerDelta.LastNewData[0]["maxD"] );
			Assert.AreEqual( minProduct, resultListenerProduct.LastNewData[0]["minP"] );
			Assert.AreEqual( maxProduct, resultListenerProduct.LastNewData[0]["maxP"] );
			resultListenerDelta.reset();
			resultListenerProduct.reset();
		}

		private void assertReceivedFeed( int delta, int product )
		{
			Assert.AreEqual( 1, feedListener.NewDataList.Count );
			Assert.AreEqual( 1, feedListener.LastNewData.Length );
			Assert.AreEqual( delta, feedListener.LastNewData[0]["delta"] );
			Assert.AreEqual( product, feedListener.LastNewData[0]["product"] );
			feedListener.reset();
		}

		private void SendEvent( int intPrimitive, int intBoxed )
		{
			SupportBean bean = new SupportBean();
			bean.str = "myId";
			bean.intPrimitive = intPrimitive;
			bean.intBoxed = intBoxed ;
			epService.EPRuntime.SendEvent( bean );
		}
	}
}
