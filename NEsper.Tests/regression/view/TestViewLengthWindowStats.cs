///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;
using net.esper.view;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestViewLengthWindowStats
	{
	    private static String SYMBOL = "CSCO.O";

	    private EPServiceProvider epService;
	    private SupportUpdateListener testListener;
	    private EPStatement statement;

	    [SetUp]
	    public void SetUp()
	    {
	        testListener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	    }

	    [Test]
	    public void TestIterator()
	    {
	        String viewExpr = "select symbol, price from " + typeof(SupportMarketDataBean).FullName + ".win:length(2)";
	        statement = epService.EPAdministrator.CreateEQL(viewExpr);
	        statement.AddListener(testListener);

	        SendEvent("ABC", 20);
	        SendEvent("DEF", 100);

	        // check iterator results
	        IEnumerator<EventBean> events = statement.GetEnumerator();
            Assert.IsTrue(events.MoveNext());
	        EventBean _event = events.Current;
	        Assert.AreEqual("ABC", _event["symbol"]);
	        Assert.AreEqual(20d, _event["price"]);

            Assert.IsTrue(events.MoveNext());
            _event = events.Current;
	        Assert.AreEqual("DEF", _event["symbol"]);
	        Assert.AreEqual(100d, _event["price"]);
	        Assert.IsFalse(events.MoveNext());

	        SendEvent("EFG", 50);

	        // check iterator results
            events = statement.GetEnumerator();
            Assert.IsTrue(events.MoveNext());
            _event = events.Current;
            Assert.AreEqual("DEF", _event["symbol"]);
	        Assert.AreEqual(100d, _event["price"]);

            Assert.IsTrue(events.MoveNext());
	        _event = events.Current;
	        Assert.AreEqual("EFG", _event["symbol"]);
	        Assert.AreEqual(50d, _event["price"]);
	    }

	    [Test]
	    public void TestWindowStats()
	    {
	        String viewExpr = "select * from " + typeof(SupportMarketDataBean).FullName +
	                "(symbol='" + SYMBOL + "').win:length(3).stat:uni('price')";
	        statement = epService.EPAdministrator.CreateEQL(viewExpr);
	        statement.AddListener(testListener);
	        testListener.Reset();

	        SendEvent(SYMBOL, 100);
	        CheckOld(0, 0, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
	        CheckNew(1, 100, 100, 0, Double.NaN, Double.NaN);

	        SendEvent(SYMBOL, 100.5);
	        CheckOld(1, 100, 100, 0, Double.NaN, Double.NaN);
	        CheckNew(2, 200.5, 100.25, 0.25, 0.353553391, 0.125);

	        SendEvent("DUMMY", 100.5);
	        Assert.IsTrue(testListener.LastNewData == null);
	        Assert.IsTrue(testListener.LastOldData == null);

	        SendEvent(SYMBOL, 100.7);
	        CheckOld(2, 200.5, 100.25, 0.25, 0.353553391, 0.125);
	        CheckNew(3, 301.2, 100.4, 0.294392029, 0.360555128, 0.13);

	        SendEvent(SYMBOL, 100.6);
	        CheckOld(3, 301.2, 100.4, 0.294392029, 0.360555128, 0.13);
	        CheckNew(3, 301.8, 100.6, 0.081649658, 0.1, 0.01);

	        SendEvent(SYMBOL, 100.9);
	        CheckOld(3, 301.8, 100.6, 0.081649658, 0.1, 0.01);
	        CheckNew(3, 302.2, 100.733333333, 0.124721913, 0.152752523, 0.023333333);
	    }

	    private void SendEvent(String symbol, double price)
	    {
	        SupportMarketDataBean _event = new SupportMarketDataBean(symbol, price, 0L, "");
	        epService.EPRuntime.SendEvent(_event);
	    }

	    private void CheckNew(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
	    {
            IEnumerator<EventBean> iterator = statement.GetEnumerator();
            Assert.IsTrue(iterator.MoveNext());
	        CheckValues(iterator.Current, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
	        Assert.IsFalse(iterator.MoveNext());

	        Assert.IsTrue(testListener.LastNewData.Length == 1);
	        EventBean childViewValues = testListener.LastNewData[0];
	        CheckValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);

	        testListener.Reset();
	    }

	    private void CheckOld(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
	    {
	        Assert.IsTrue(testListener.LastOldData.Length == 1);
	        EventBean childViewValues = testListener.LastOldData[0];
	        CheckValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
	    }

	    private void CheckValues(EventBean values, long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
	    {
	        long count = GetLongValue(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT, values);
	        double sum = GetDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM, values);
	        double avg = GetDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE, values);
	        double stdevpa = GetDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA, values);
	        double stdev = GetDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV, values);
	        double variance = GetDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE, values);

	        Assert.AreEqual(count, countE);
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(sum,  sumE, 6));
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(avg,  avgE, 6));
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(stdevpa,  stdevpaE, 6));
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(stdev,  stdevE, 6));
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(variance,  varianceE, 6));
	    }

	    private double GetDoubleValue(ViewFieldEnum field, EventBean values)
	    {
	        return (double) values[field.Name];
	    }

	    private long GetLongValue(ViewFieldEnum field, EventBean values)
	    {
	        return (long) values[field.Name];
	    }
	}
} // End of namespace
