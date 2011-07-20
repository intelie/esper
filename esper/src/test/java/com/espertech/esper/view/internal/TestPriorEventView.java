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

package com.espertech.esper.view.internal;

import junit.framework.TestCase;
import com.espertech.esper.support.view.SupportBeanClassView;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.view.internal.PriorEventBufferSingle;

public class TestPriorEventView extends TestCase
{
    private PriorEventBufferSingle buffer;
    private PriorEventView view;
    private SupportBeanClassView childView;

    public void setUp()
    {
        buffer = new PriorEventBufferSingle(1);
        view = new PriorEventView(buffer);
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        view.addView(childView);
    }

    public void testUpdate()
    {
        // Send some data
        EventBean newEventsOne[] = makeBeans("a", 2);
        view.update(newEventsOne, null);

        // make sure received
        assertSame(newEventsOne, childView.getLastNewData());
        assertNull(childView.getLastOldData());
        childView.reset();

        // Assert random access
        assertSame(newEventsOne[0], buffer.getRelativeToEvent(newEventsOne[1], 0));

        EventBean[] newEventsTwo = makeBeans("b", 3);
        view.update(newEventsTwo, null);

        assertSame(newEventsTwo[1], buffer.getRelativeToEvent(newEventsTwo[2], 0));
        assertSame(newEventsTwo[0], buffer.getRelativeToEvent(newEventsTwo[1], 0));
        assertSame(newEventsOne[1], buffer.getRelativeToEvent(newEventsTwo[0], 0));
    }

    private EventBean[] makeBeans(String id, int numTrades)
    {
        EventBean[] trades = new EventBean[numTrades];
        for (int i = 0; i < numTrades; i++)
        {
            SupportBean_A bean = new SupportBean_A(id + i);
            trades[i] = SupportEventBeanFactory.createObject(bean);
        }
        return trades;
    }
}
