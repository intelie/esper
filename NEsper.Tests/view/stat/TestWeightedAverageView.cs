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
	public class TestWeightedAverageView
	{
	    private WeightedAverageView myView;
	    private SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up sum view and a test child view
	        myView = new WeightedAverageView(SupportStatementContextFactory.MakeContext(), "price", "volume");

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

	        // Send a first event, check values
	        EventBean marketData = MakeBean("IBM", 10, 1000);
	        stream.Insert(marketData);
	        CheckOld(Double.NaN);
	        CheckNew(10);

	        // Send a second event, check values
	        marketData = MakeBean("IBM", 11, 2000);
	        stream.Insert(marketData);
	        CheckOld(10);
	        CheckNew(10.66666667);

	        // Send a third event, check values
	        marketData = MakeBean("IBM", 10.5, 1500);
	        stream.Insert(marketData);
	        CheckOld(10.66666667);
	        CheckNew(10.61111111);

	        // Send a 4th event, this time the first event should be gone
	        marketData = MakeBean("IBM", 9.5, 600);
	        stream.Insert(marketData);
	        CheckOld(10.61111111);
	        CheckNew(10.59756098);
	    }

	    [Test]
	    public void testGetSchema()
	    {
	        Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.Name) == typeof(double));
	    }

	    [Test]
	    public void testCopyView()
	    {
	        WeightedAverageView copied = (WeightedAverageView) myView.CloneView(SupportStatementContextFactory.MakeContext());
	        Assert.IsTrue(myView.FieldNameWeight.Equals(copied.FieldNameWeight));
	        Assert.IsTrue(myView.FieldNameX.Equals(copied.FieldNameX));
	    }

	    private void CheckNew(double avgE)
	    {
            IEnumerator<EventBean> iterator = myView.GetEnumerator();
	        Assert.IsTrue(iterator.MoveNext());
	        CheckValues(iterator.Current, avgE);
	        Assert.IsFalse(iterator.MoveNext());

	        Assert.IsTrue(childView.LastNewData.Length == 1);
	        EventBean childViewValues = childView.LastNewData[0];
	        CheckValues(childViewValues, avgE);
	    }

	    private void CheckOld(double avgE)
	    {
	        Assert.IsTrue(childView.LastOldData.Length == 1);
	        EventBean childViewValues = childView.LastOldData[0];
	        CheckValues(childViewValues, avgE);
	    }

	    private void CheckValues(EventBean values, double avgE)
	    {
	        double avg = GetDoubleValue(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE, values);

	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(avg,  avgE, 6));
	    }

	    private double GetDoubleValue(ViewFieldEnum field, EventBean _eventBean)
	    {
	        return (double) _eventBean[field.Name];
	    }

	    private EventBean MakeBean(String symbol, double price, long volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
