///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

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
	    public void SetUp()
	    {
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        feedListener = new SupportUpdateListener();
	        resultListenerDelta = new SupportUpdateListener();
	        resultListenerProduct = new SupportUpdateListener();

	        // Use external clocking for the test
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void TestVariantOne()
	    {
	        String stmtText = "insert into Event_1 (delta, product) " +
	                      "select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
	                      "from " + typeof(SupportBean).FullName + ".win:length(100)";

	        RunAsserts(stmtText);
	    }

	    [Test]
	    public void TestVariantOneWildcard()
	    {
	        String stmtText = "insert into Event_1 (delta, product) " +
	        "select * from " + typeof(SupportBean).FullName + ".win:length(100)";

	        try{
	        	epService.EPAdministrator.CreateEQL(stmtText);
	        	Assert.Fail();
	        }
	        catch(EPStatementException ex)
	        {
	        	// Expected
	        }
	    }

	    [Test]
	    public void TestVariantOneJoin()
	    {
	        String stmtText = "insert into Event_1 (delta, product) " +
	                      "select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
	                      "from " + typeof(SupportBean).FullName + ".win:length(100) as s0," +
	                                typeof(SupportBean_A).FullName + ".win:length(100) as s1 " +
	                      " where s0.string = s1.id";

	        RunAsserts(stmtText);
	    }

	    [Test]
	    public void TestVariantOneJoinWildcard()
	    {
	        String stmtText = "insert into Event_1 (delta, product) " +
	        "select * " +
	        "from " + typeof(SupportBean).FullName + ".win:length(100) as s0," +
	                  typeof(SupportBean_A).FullName + ".win:length(100) as s1 " +
	        " where s0.string = s1.id";

	        try{
	        	epService.EPAdministrator.CreateEQL(stmtText);
	        	Assert.Fail();
	        }
	        catch(EPStatementException ex)
	        {
	        	// Expected
	        }
	    }

	    [Test]
	    public void TestVariantTwo()
	    {
	        String stmtText = "insert into Event_1 " +
	                      "select intPrimitive - intBoxed as delta, intPrimitive * intBoxed as product " +
	                      "from " + typeof(SupportBean).FullName + ".win:length(100)";

	        RunAsserts(stmtText);
	    }

	    [Test]
	    public void TestVariantTwoWildcard()
	    {
	        String stmtText = "insert into event1 select * from " + typeof(SupportBean).FullName + ".win:length(100)";
	        String otherText = "select * from event1.win:length(10)";

	        // Attach listener to feed
	        EPStatement stmtOne = epService.EPAdministrator.CreateEQL(stmtText);
	        SupportUpdateListener listenerOne = new SupportUpdateListener();
	        stmtOne.AddListener(listenerOne);
	        EPStatement stmtTwo = epService.EPAdministrator.CreateEQL(otherText);
	        SupportUpdateListener listenerTwo = new SupportUpdateListener();
            stmtTwo.AddListener(listenerTwo);

	        SupportBean _event = SendEvent(10, 11);
	        Assert.IsTrue(listenerOne.GetAndClearIsInvoked());
            Assert.AreEqual(1, listenerOne.LastNewData.Length);
	        Assert.AreEqual(10, listenerOne.LastNewData[0]["intPrimitive"]);
	        Assert.AreEqual(11, listenerOne.LastNewData[0]["intBoxed"]);
	        Assert.AreEqual(18, listenerOne.LastNewData[0].EventType.PropertyNames.Count);
	        Assert.AreSame(_event, listenerOne.LastNewData[0].Underlying);

	        Assert.IsTrue(listenerTwo.GetAndClearIsInvoked());
            Assert.AreEqual(1, listenerTwo.LastNewData.Length);
	        Assert.AreEqual(10, listenerTwo.LastNewData[0]["intPrimitive"]);
	        Assert.AreEqual(11, listenerTwo.LastNewData[0]["intBoxed"]);
            Assert.AreEqual(18, listenerTwo.LastNewData[0].EventType.PropertyNames.Count);
	        Assert.AreSame(_event, listenerTwo.LastNewData[0].Underlying);
	    }

	    [Test]
	    public void TestVariantTwoJoin()
	    {
	        String stmtText = "insert into Event_1 " +
	                      "select intPrimitive - intBoxed as delta, intPrimitive * intBoxed as product " +
	                        "from " + typeof(SupportBean).FullName + ".win:length(100) as s0," +
	                                  typeof(SupportBean_A).FullName + ".win:length(100) as s1 " +
	                        " where s0.string = s1.id";

	        RunAsserts(stmtText);
	    }

	    [Test]
	    public void TestVariantTwoJoinWildcard()
	    {
	        String textOne = "insert into event2 select * " +
	        		          "from " + typeof(SupportBean).FullName + ".win:length(100) as s0, " +
	        		          typeof(SupportBean_A).FullName + ".win:length(5) as s1 " +
	        		          "where s0.string = s1.id";
	        String textTwo = "select * from event2.win:length(10)";

	        // Attach listener to feed
	        EPStatement stmtOne = epService.EPAdministrator.CreateEQL(textOne);
	        SupportUpdateListener listenerOne = new SupportUpdateListener();
            stmtOne.AddListener(listenerOne);
	        EPStatement stmtTwo = epService.EPAdministrator.CreateEQL(textTwo);
	        SupportUpdateListener listenerTwo = new SupportUpdateListener();
            stmtTwo.AddListener(listenerTwo);

	        // send event for joins to match on
	        SupportBean_A eventA = new SupportBean_A("myId");
	        epService.EPRuntime.SendEvent(eventA);

	        SupportBean eventOne = SendEvent(10, 11);
	        Assert.IsTrue(listenerOne.GetAndClearIsInvoked());
            Assert.AreEqual(1, listenerOne.LastNewData.Length);
            Assert.AreEqual(2, listenerOne.LastNewData[0].EventType.PropertyNames.Count);
	        Assert.IsTrue(listenerOne.LastNewData[0].EventType.IsProperty("s0"));
	        Assert.IsTrue(listenerOne.LastNewData[0].EventType.IsProperty("s1"));
	        Assert.AreSame(eventOne, listenerOne.LastNewData[0]["s0"]);
	        Assert.AreSame(eventA, listenerOne.LastNewData[0]["s1"]);

	        Assert.IsTrue(listenerTwo.GetAndClearIsInvoked());
            Assert.AreEqual(1, listenerTwo.LastNewData.Length);
            Assert.AreEqual(2, listenerTwo.LastNewData[0].EventType.PropertyNames.Count);
	        Assert.IsTrue(listenerTwo.LastNewData[0].EventType.IsProperty("s0"));
	        Assert.IsTrue(listenerTwo.LastNewData[0].EventType.IsProperty("s1"));
	        Assert.AreSame(eventOne, listenerOne.LastNewData[0]["s0"]);
	        Assert.AreSame(eventA, listenerOne.LastNewData[0]["s1"]);
	    }

	    [Test]
	    public void TestInvalidStreamUsed()
	    {
	        String stmtText = "insert into Event_1 (delta, product) " +
	                      "select intPrimitive - intBoxed as deltaTag, intPrimitive * intBoxed as productTag " +
	                      "from " + typeof(SupportBean).FullName + ".win:length(100)";
	        epService.EPAdministrator.CreateEQL(stmtText);

	        try
	        {
	            stmtText = "insert into Event_1 (delta) " +
	                      "select intPrimitive - intBoxed as deltaTag " +
	                      "from " + typeof(SupportBean).FullName + ".win:length(100)";
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            // expected
	            Assert.AreEqual("Error starting view: Event type named 'Event_1' has already been declared with differing column name or type information [insert into Event_1 (delta) select intPrimitive - intBoxed as deltaTag from net.esper.support.bean.SupportBean.win:length(100)]", ex.Message);
	        }
	    }

	    [Test]
	    public void TestWithOutputLimitAndSort()
	    {
	        // NOTICE: we are inserting the RSTREAM (removed events)
	        String stmtText = "insert rstream into StockTicks(mySymbol, myPrice) " +
	                          "select symbol, price from " + typeof(SupportMarketDataBean).FullName + ".win:time(60) " +
	                          "output every 5 seconds " +
	                          "order by symbol asc";
	        epService.EPAdministrator.CreateEQL(stmtText);

	        stmtText = "select mySymbol, Sum(myPrice) as pricesum from StockTicks.win:length(100)";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
            statement.AddListener(feedListener);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(0));
	        SendEvent("IBM", 50);
	        SendEvent("CSC", 10);
	        SendEvent("GE", 20);
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(10 * 1000));
	        SendEvent("DEF", 100);
	        SendEvent("ABC", 11);
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(20 * 1000));
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(30 * 1000));
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(40 * 1000));
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(50 * 1000));
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(55 * 1000));

	        Assert.IsFalse(feedListener.IsInvoked);
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(60 * 1000));

	        Assert.IsTrue(feedListener.IsInvoked);
	        Assert.AreEqual(3, feedListener.NewDataList.Count);
	        Assert.AreEqual("CSC", feedListener.NewDataList[0][0]["mySymbol"]);
	        Assert.AreEqual(10.0, feedListener.NewDataList[0][0]["pricesum"]);
	        Assert.AreEqual("GE", feedListener.NewDataList[1][0]["mySymbol"]);
	        Assert.AreEqual(30.0, feedListener.NewDataList[1][0]["pricesum"]);
	        Assert.AreEqual("IBM", feedListener.NewDataList[2][0]["mySymbol"]);
	        Assert.AreEqual(80.0, feedListener.NewDataList[2][0]["pricesum"]);
	        feedListener.Reset();

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(65 * 1000));
	        Assert.IsFalse(feedListener.IsInvoked);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(70 * 1000));
	        Assert.AreEqual("ABC", feedListener.NewDataList[0][0]["mySymbol"]);
	        Assert.AreEqual(91.0, feedListener.NewDataList[0][0]["pricesum"]);
	        Assert.AreEqual("DEF", feedListener.NewDataList[1][0]["mySymbol"]);
	        Assert.AreEqual(191.0, feedListener.NewDataList[1][0]["pricesum"]);
	    }

	    [Test]
	    public void TestStaggeredWithWildcard()
	    {
	    	String statementOne = "insert into streamA select * from " + typeof(SupportBeanSimple).FullName + ".win:length(5)";
	    	String statementTwo = "insert into streamB select *, myInt+myInt as summed, myString||myString as concat from streamA.win:length(5)";
	    	String statementThree = "insert into streamC select * from streamB.win:length(5)";

	    	SupportUpdateListener listenerOne = new SupportUpdateListener();
	    	SupportUpdateListener listenerTwo = new SupportUpdateListener();
	    	SupportUpdateListener listenerThree = new SupportUpdateListener();

            epService.EPAdministrator.CreateEQL(statementOne).AddListener(listenerOne);
            epService.EPAdministrator.CreateEQL(statementTwo).AddListener(listenerTwo);
            epService.EPAdministrator.CreateEQL(statementThree).AddListener(listenerThree);

	    	SendSimpleEvent("one", 1);
	    	AssertSimple(listenerOne, "one", 1, null, 0);
            AssertSimple(listenerTwo, "one", 1, "oneone", 2);
            AssertSimple(listenerThree, "one", 1, "oneone", 2);

	    	SendSimpleEvent("two", 2);
            AssertSimple(listenerOne, "two", 2, null, 0);
            AssertSimple(listenerTwo, "two", 2, "twotwo", 4);
            AssertSimple(listenerThree, "two", 2, "twotwo", 4);
	    }

	    [Test]
	    public void TestInsertIntoPlusPattern()
	    {
	        String stmtOneTxt = "insert into InZone " +
	                      "select 111 as statementId, mac, locationReportId " +
	                      "from " + typeof(SupportRFIDEvent).FullName + " " +
	                      "where mac in ('1','2','3') " +
	                      "and zoneID = '10'";
	        EPStatement stmtOne = epService.EPAdministrator.CreateEQL(stmtOneTxt);
	        SupportUpdateListener listenerOne = new SupportUpdateListener();
            stmtOne.AddListener(listenerOne);

	        String stmtTwoTxt = "insert into OutOfZone " +
	                      "select 111 as statementId, mac, locationReportId " +
	                      "from " + typeof(SupportRFIDEvent).FullName + " " +
	                      "where mac in ('1','2','3') " +
	                      "and zoneID != '10'";
	        EPStatement stmtTwo = epService.EPAdministrator.CreateEQL(stmtTwoTxt);
	        SupportUpdateListener listenerTwo = new SupportUpdateListener();
            stmtTwo.AddListener(listenerTwo);

	        String stmtThreeTxt = "select 111 as eventSpecId, A.locationReportId as locationReportId " +
	                      " from pattern [every A=InZone -> (timer:interval(1 sec) and not OutOfZone(mac=A.mac))]";
	        EPStatement stmtThree = epService.EPAdministrator.CreateEQL(stmtThreeTxt);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmtThree.AddListener(listener);

	        // try the alert case with 1 event for the mac in question
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(0));
	        epService.EPRuntime.SendEvent(new SupportRFIDEvent("LR1", "1", "10"));
	        Assert.IsFalse(listener.IsInvoked);
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(1000));

	        EventBean _event = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual("LR1", _event["locationReportId"]);

	        listenerOne.Reset();
	        listenerTwo.Reset();

	        // try the alert case with 2 events for zone 10 within 1 second for the mac in question
	        epService.EPRuntime.SendEvent(new SupportRFIDEvent("LR2", "2", "10"));
	        Assert.IsFalse(listener.IsInvoked);
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(1500));
	        epService.EPRuntime.SendEvent(new SupportRFIDEvent("LR3", "2", "10"));
	        Assert.IsFalse(listener.IsInvoked);
	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(2000));

	        _event = listener.AssertOneGetNewAndReset();
	        Assert.AreEqual("LR2", _event["locationReportId"]);
	    }

	    [Test]
	    public void TestNullType()
	    {
	        String stmtOneTxt = "insert into InZone select null as dummy from System.String";
	        EPStatement stmtOne = epService.EPAdministrator.CreateEQL(stmtOneTxt);
	        Assert.IsTrue(stmtOne.EventType.IsProperty("dummy"));

	        String stmtTwoTxt = "select dummy from InZone";
	        EPStatement stmtTwo = epService.EPAdministrator.CreateEQL(stmtTwoTxt);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmtTwo.AddListener(listener);

	        epService.EPRuntime.SendEvent("a");
	        Assert.IsNull(listener.AssertOneGetNewAndReset()["dummy"]);
	    }

	    private void AssertSimple(SupportUpdateListener listener, String myString, int myInt, String additionalString, int additionalInt)
	    {
	    	Assert.IsTrue(listener.GetAndClearIsInvoked());
	    	EventBean _eventBean = listener.LastNewData[0];
            Assert.AreEqual(myString, _eventBean["myString"]);
            Assert.AreEqual(myInt, _eventBean["myInt"]);
	    	if(additionalString != null)
	    	{
	    		Assert.AreEqual(additionalString, _eventBean["concat"]);
                Assert.AreEqual(additionalInt, _eventBean["summed"]);
	    	}
	    }

	    private void SendEvent(String symbol, double price)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, null, null);
	        epService.EPRuntime.SendEvent(bean);
	    }

	    private void SendSimpleEvent(String _string, int val)
	    {
            epService.EPRuntime.SendEvent(new SupportBeanSimple(_string, val));
	    }

	    private void RunAsserts(String stmtText)
	    {
	        // Attach listener to feed
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(feedListener);

	        // send event for joins to match on
	        epService.EPRuntime.SendEvent(new SupportBean_A("myId"));

	        // Attach delta statement to statement and add listener
	        stmtText = "select Min(delta) as minD, Max(delta) as maxD " +
	                   "from Event_1.win:time(60)";
	        stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(resultListenerDelta);

	        // Attach prodict statement to statement and add listener
	        stmtText = "select Min(product) as minP, Max(product) as maxP " +
	                   "from Event_1.win:time(60)";
	        stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(resultListenerProduct);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(0)); // Set the time to 0 seconds

	        // send events
	        SendEvent(20, 10);
	        AssertReceivedFeed(10, 200);
	        AssertReceivedMinMax(10, 10, 200, 200);

	        SendEvent(50, 25);
	        AssertReceivedFeed(25, 25 * 50);
	        AssertReceivedMinMax(10, 25, 200, 1250);

	        SendEvent(5, 2);
	        AssertReceivedFeed(3, 2 * 5);
	        AssertReceivedMinMax(3, 25, 10, 1250);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(10 * 1000)); // Set the time to 10 seconds

	        SendEvent(13, 1);
	        AssertReceivedFeed(12, 13);
	        AssertReceivedMinMax(3, 25, 10, 1250);

	        epService.EPRuntime.SendEvent(new CurrentTimeEvent(61 * 1000)); // Set the time to 61 seconds
	        AssertReceivedMinMax(12, 12, 13, 13);
	    }

	    private void AssertReceivedMinMax(int minDelta, int maxDelta, int minProduct, int maxProduct)
	    {
	        Assert.AreEqual(1, resultListenerDelta.NewDataList.Count);
            Assert.AreEqual(1, resultListenerDelta.LastNewData.Length);
	        Assert.AreEqual(1, resultListenerProduct.NewDataList.Count);
            Assert.AreEqual(1, resultListenerProduct.LastNewData.Length);
	        Assert.AreEqual(minDelta, resultListenerDelta.LastNewData[0]["minD"]);
	        Assert.AreEqual(maxDelta, resultListenerDelta.LastNewData[0]["maxD"]);
	        Assert.AreEqual(minProduct, resultListenerProduct.LastNewData[0]["minP"]);
	        Assert.AreEqual(maxProduct, resultListenerProduct.LastNewData[0]["maxP"]);
	        resultListenerDelta.Reset();
	        resultListenerProduct.Reset();
	    }

	    private void AssertReceivedFeed(int delta, int product)
	    {
	        Assert.AreEqual(1, feedListener.NewDataList.Count);
            Assert.AreEqual(1, feedListener.LastNewData.Length);
	        Assert.AreEqual(delta, feedListener.LastNewData[0]["delta"]);
	        Assert.AreEqual(product, feedListener.LastNewData[0]["product"]);
	        feedListener.Reset();
	    }

	    private SupportBean SendEvent(int intPrimitive, int intBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetString("myId");
	        bean.SetIntPrimitive(intPrimitive);
	        bean.SetIntBoxed(intBoxed);
	        epService.EPRuntime.SendEvent(bean);
	        return bean;
	    }
	}
} // End of namespace
