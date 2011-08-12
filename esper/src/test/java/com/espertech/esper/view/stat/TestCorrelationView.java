/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.view.stat;

import com.espertech.esper.client.EventType;
import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.util.DoubleValueAssertionUtil;
import com.espertech.esper.support.view.SupportBeanClassView;
import com.espertech.esper.support.view.SupportStreamImpl;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.view.ViewFieldEnum;

import java.util.Iterator;

public class TestCorrelationView extends TestCase
{
    CorrelationView myView;
    SupportBeanClassView childView;

    public void setUp() throws Exception
    {
        // Set up sum view and a test child view
        EventType type = CorrelationView.createEventType(SupportStatementContextFactory.makeContext(), null, 1);
        myView = new CorrelationView(SupportStatementContextFactory.makeContext(),
                SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("volume"), type, null);

        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    // Check values against Microsoft Excel computed values
    public void testViewComputedValues()
    {
        // Set up feed for sum view
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 3);
        stream.addView(myView);

        // Send a first event, checkNew values
        EventBean marketData = makeBean("IBM", 70, 1000);
        stream.insert(marketData);
        checkOld(Double.NaN);
        checkNew(Double.NaN);

        // Send a second event, checkNew values
        marketData = makeBean("IBM", 70.5, 1500);
        stream.insert(marketData);
        checkOld(Double.NaN);
        checkNew(1);

        // Send a third event, checkNew values
        marketData = makeBean("IBM", 70.1, 1200);
        stream.insert(marketData);
        checkOld(1);
        checkNew(0.97622104);

        // Send a 4th event, this time the first event should be gone, checkNew values
        marketData = makeBean("IBM", 70.25, 1000);
        stream.insert(marketData);
        checkOld(0.97622104);
        checkNew(0.70463404);
    }

    public void testGetSchema()
    {
        assertTrue(myView.getEventType().getPropertyType(ViewFieldEnum.CORRELATION__CORRELATION.getName()) == Double.class);
    }

    public void testCopyView() throws Exception
    {
        CorrelationView copied = (CorrelationView) myView.cloneView(SupportStatementContextFactory.makeContext());
        assertTrue(myView.getExpressionX().equals(copied.getExpressionX()));
        assertTrue(myView.getExpressionY().equals(copied.getExpressionY()));
    }

    private void checkNew(double correlationE)
    {
        Iterator<EventBean> iterator = myView.iterator();
        checkValues(iterator.next(), correlationE);
        assertTrue(iterator.hasNext() == false);

        assertTrue(childView.getLastNewData().length == 1);
        EventBean childViewValues = childView.getLastNewData()[0];
        checkValues(childViewValues, correlationE);
    }

    private void checkOld(double correlationE)
    {
        assertTrue(childView.getLastOldData().length == 1);
        EventBean childViewValues = childView.getLastOldData()[0];
        checkValues(childViewValues, correlationE);
    }

    private void checkValues(EventBean values, double correlationE)
    {
        double correlation = getDoubleValue(ViewFieldEnum.CORRELATION__CORRELATION, values);
        assertTrue(DoubleValueAssertionUtil.equals(correlation,  correlationE, 6));
    }

    private double getDoubleValue(ViewFieldEnum field, EventBean values)
    {
        return (Double) values.get(field.getName());
    }

    private EventBean makeBean(String symbol, double price, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}
