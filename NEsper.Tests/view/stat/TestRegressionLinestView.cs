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

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

namespace net.esper.view.stat
{
	[TestFixture]
	public class TestRegressionLinestView
	{
	    RegressionLinestView myView;
	    SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up sum view and a test child view
	        myView = new RegressionLinestView(SupportStatementContextFactory.MakeContext(), "price", "volume");

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

	        // Send a first event, checkNew values
	        EventBean marketData = MakeBean("IBM", 70, 1000);
	        stream.Insert(marketData);
	        CheckOld(Double.NaN, Double.NaN);
	        CheckNew(Double.NaN, Double.NaN);

	        // Send a second event, checkNew values
	        marketData = MakeBean("IBM", 70.5, 1500);
	        stream.Insert(marketData);
	        CheckOld(Double.NaN, Double.NaN);
	        CheckNew(1000, -69000);

	        // Send a third event, checkNew values
	        marketData = MakeBean("IBM", 70.1, 1200);
	        stream.Insert(marketData);
	        CheckOld(1000, -69000);
	        CheckNew(928.5714286, -63952.380953);

	        // Send a 4th event, this time the first event should be gone, checkNew values
	        marketData = MakeBean("IBM", 70.25, 1000);
	        stream.Insert(marketData);
	        CheckOld(928.5714286, -63952.380953);
	        CheckNew(877.5510204, -60443.877555);
	    }

	    [Test]
	    public void testGetSchema()
	    {
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.REGRESSION__SLOPE.Name) == typeof(double));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.REGRESSION__YINTERCEPT.Name) == typeof(double));
	    }

	    [Test]
	    public void testCopyView()
	    {
	        RegressionLinestView copied = (RegressionLinestView) myView.CloneView(SupportStatementContextFactory.MakeContext());
	        Assert.IsTrue(myView.FieldNameX.Equals(copied.FieldNameX));
	        Assert.IsTrue(myView.FieldNameY.Equals(copied.FieldNameY));
	    }

	    private void CheckNew(double slopeE, double yinterceptE)
	    {
	        IEnumerator<EventBean> iterator = myView.GetEnumerator();
	        Assert.IsTrue(iterator.MoveNext());
	        CheckValues(iterator.Current, slopeE, yinterceptE);
	        Assert.IsFalse(iterator.MoveNext());

	        Assert.IsTrue(childView.LastNewData.Length == 1);
	        EventBean childViewValues = childView.LastNewData[0];
	        CheckValues(childViewValues, slopeE, yinterceptE);
	    }

	    private void CheckOld(double slopeE, double yinterceptE)
	    {
	        Assert.IsTrue(childView.LastOldData.Length == 1);
	        EventBean childViewValues = childView.LastOldData[0];
	        CheckValues(childViewValues, slopeE, yinterceptE);
	    }

	    private void CheckValues(EventBean _eventBean, double slopeE, double yinterceptE)
	    {
	        double slope = GetDoubleValue(ViewFieldEnum.REGRESSION__SLOPE, _eventBean);
	        double yintercept = GetDoubleValue(ViewFieldEnum.REGRESSION__YINTERCEPT, _eventBean);
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(slope,  slopeE, 6));
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(yintercept,  yinterceptE, 6));
	    }

	    private double GetDoubleValue(ViewFieldEnum field, EventBean _event)
	    {
            return (double)_event[field.Name];
	    }

	    private EventBean MakeBean(String symbol, double price, long volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
