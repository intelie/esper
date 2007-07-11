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
	public class TestCorrelationView
	{
	    CorrelationView myView;
	    SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up sum view and a test child view
	        myView = new CorrelationView(SupportStatementContextFactory.MakeContext(), "price", "volume");

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
	        CheckOld(Double.NaN);
	        CheckNew(Double.NaN);

	        // Send a second event, checkNew values
	        marketData = MakeBean("IBM", 70.5, 1500);
	        stream.Insert(marketData);
	        CheckOld(Double.NaN);
	        CheckNew(1);

	        // Send a third event, checkNew values
	        marketData = MakeBean("IBM", 70.1, 1200);
	        stream.Insert(marketData);
	        CheckOld(1);
	        CheckNew(0.97622104);

	        // Send a 4th event, this time the first event should be gone, checkNew values
	        marketData = MakeBean("IBM", 70.25, 1000);
	        stream.Insert(marketData);
	        CheckOld(0.97622104);
	        CheckNew(0.70463404);
	    }

	    [Test]
	    public void testGetSchema()
	    {
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.CORRELATION__CORRELATION.Name) == typeof(double));
	    }

	    [Test]
	    public void testCopyView()
	    {
	        CorrelationView copied = (CorrelationView) myView.CloneView(SupportStatementContextFactory.MakeContext());
	        Assert.IsTrue(myView.FieldNameX.Equals(copied.FieldNameX));
	        Assert.IsTrue(myView.FieldNameY.Equals(copied.FieldNameY));
	    }

	    private void CheckNew(double correlationE)
	    {
	        IEnumerator<EventBean> iterator = myView.GetEnumerator();
            Assert.IsTrue(iterator.MoveNext());
	        CheckValues(iterator.Current, correlationE);
	        Assert.IsFalse(iterator.MoveNext());

	        Assert.IsTrue(childView.LastNewData.Length == 1);
	        EventBean childViewValues = childView.LastNewData[0];
	        CheckValues(childViewValues, correlationE);
	    }

	    private void CheckOld(double correlationE)
	    {
	        Assert.IsTrue(childView.LastOldData.Length == 1);
	        EventBean childViewValues = childView.LastOldData[0];
	        CheckValues(childViewValues, correlationE);
	    }

	    private void CheckValues(EventBean values, double correlationE)
	    {
	        double correlation = GetDoubleValue(ViewFieldEnum.CORRELATION__CORRELATION, values);
	        Assert.IsTrue(DoubleValueAssertionUtil.Equals(correlation,  correlationE, 6));
	    }

	    private double GetDoubleValue(ViewFieldEnum field, EventBean values)
	    {
            return (double)values[field.Name];
	    }

	    private EventBean MakeBean(String symbol, double price, long volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
