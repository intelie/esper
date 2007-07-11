// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

namespace net.esper.view.stat
{
	[TestFixture]
	public class TestUnivariateStatisticsView
	{
	    UnivariateStatisticsView myView;
	    SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up sum view and a test child view
	        myView = new UnivariateStatisticsView(SupportStatementContextFactory.MakeContext(), "price");

	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);
	    }

	    // Check values against Microsoft Excel computed values
	    [Test]
	    public void testViewComputedValues()
	    {
	        // Set up feed for sum view
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 3);
	        stream.AddView(myView);

	        // Send two events to the stream
	        Assert.IsTrue(childView.LastNewData == null);

	        // Send a first _event, checkNew values
	        EventBean marketData = MakeBean("IBM", 10, 0);
	        stream.Insert(marketData);
	        CheckOld(0, 0, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
	        CheckNew(1, 10, 10, 0, Double.NaN, Double.NaN);

	        // Send a second _event, checkNew values
	        marketData = MakeBean("IBM", 12, 0);
	        stream.Insert(marketData);
	        CheckOld(1, 10, 10, 0, Double.NaN, Double.NaN);
	        CheckNew(2, 22, 11, 1, Math.Sqrt(2.0), 2);

	        // Send a third _event, checkNew values
	        marketData = MakeBean("IBM", 9.5, 0);
	        stream.Insert(marketData);
	        CheckOld(2, 22, 11, 1, Math.Sqrt(2.0), 2);
	        CheckNew(3, 31.5, 10.5, 1.08012345, 1.322875656, 1.75);

	        // Send a 4th _event, this time the first event should be gone, checkNew values
	        marketData = MakeBean("IBM", 9, 0);
	        stream.Insert(marketData);
	        CheckOld(3, 31.5, 10.5, 1.08012345, 1.322875656, 1.75);
	        CheckNew(3, 30.5, 10.16666667, 1.312334646, 1.607275127, 2.583333333);
	    }

	    [Test]
	    public void testGetSchema()
	    {
	        Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name) == typeof(long));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.Name) == typeof(double));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.Name) == typeof(double));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.Name) == typeof(double));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.Name) == typeof(double));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name) == typeof(double));
	    }

	    [Test]
	    public void testCopyView()
	    {
	        UnivariateStatisticsView copied = (UnivariateStatisticsView) myView.CloneView(SupportStatementContextFactory.MakeContext());
	        Assert.IsTrue(myView.FieldName.Equals(copied.FieldName));
	    }

	    private void CheckNew(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
	    {
	        IEnumerator<EventBean> iterator = myView.GetEnumerator();
            Assert.IsTrue(iterator.MoveNext());
	        CheckValues(iterator.Current, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
	        Assert.IsFalse(iterator.MoveNext());

	        Assert.IsTrue(childView.LastNewData.Length == 1);
	        EventBean childViewValues = childView.LastNewData[0];
	        CheckValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
	    }

	    private void CheckOld(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
	    {
	        Assert.IsTrue(childView.LastOldData.Length == 1);
	        EventBean childViewValues = childView.LastOldData[0];
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
	        Assert.AreEqual(sum, sumE);
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(avg,  avgE, 6));
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(stdevpa,  stdevpaE, 6));
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(stdev,  stdevE, 6));
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(variance,  varianceE, 6));
	    }

	    private double GetDoubleValue(ViewFieldEnum field, EventBean _eventBean)
	    {
            return (double)_eventBean[field.Name];
	    }

	    private long GetLongValue(ViewFieldEnum field, EventBean _eventBean)
	    {
            return (long)_eventBean[field.Name];
	    }

	    private EventBean MakeBean(String symbol, double price, long volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
