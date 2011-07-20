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

package com.espertech.esper.view.window;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.event.EventFactoryHelper;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.view.SupportBeanClassView;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.view.SupportViewDataChecker;

import java.util.Map;

public class TestTimeWindowView extends TestCase
{
    private final static long TEST_WINDOW_MSEC = 60000;

    private TimeWindowView myView;
    private SupportBeanClassView childView;
    private SupportSchedulingServiceImpl schedulingServiceStub;

    public void setUp()
    {
        // Set the scheduling service to use
        schedulingServiceStub = new SupportSchedulingServiceImpl();

        // Set up length window view and a test child view
        myView = new TimeWindowView(SupportStatementContextFactory.makeContext(schedulingServiceStub), null, TEST_WINDOW_MSEC, null, false);
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    public void testViewPushAndExpire()
    {
        long startTime = 1000000;
        schedulingServiceStub.setTime(startTime);
        assertTrue(schedulingServiceStub.getAdded().size() == 0);

        Map<String, EventBean> events = EventFactoryHelper.makeEventMap(
            new String[] {"a1", "b1", "b2", "c1", "d1", "e1", "f1", "f2"});

        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),null);
        SupportViewDataChecker.checkOldData(childView,null);
        SupportViewDataChecker.checkNewData(childView, null);

        // Send new events to the view - should have scheduled a callback for X msec after
        myView.update(new EventBean[]{ events.get("a1") }, null);
        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(TEST_WINDOW_MSEC) != null);
        schedulingServiceStub.getAdded().clear();

        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[]{ events.get("a1") });
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[]{ events.get("a1") });

        // Send more events, check
        schedulingServiceStub.setTime(startTime + 10000);
        myView.update(new EventBean[]{ events.get("b1"), events.get("b2") }, null);
        assertTrue(schedulingServiceStub.getAdded().size() == 0);

        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[]{ events.get("a1"), events.get("b1"), events.get("b2") });
        SupportViewDataChecker.checkOldData(childView,null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[]{ events.get("b1"), events.get("b2") });

        // Send more events, check
        schedulingServiceStub.setTime(startTime + TEST_WINDOW_MSEC - 1);
        myView.update(new EventBean[]{ events.get("c1") }, null);
        assertTrue(schedulingServiceStub.getAdded().size() == 0);

        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[]{ events.get("a1"), events.get("b1"), events.get("b2"), events.get("c1") });
        SupportViewDataChecker.checkOldData(childView,null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[]{ events.get("c1") });

        // Pretend we are getting the callback from scheduling, check old data and check new scheduling
        schedulingServiceStub.setTime(startTime + TEST_WINDOW_MSEC);
        myView.expire();
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[]{ events.get("b1"), events.get("b2"), events.get("c1") });
        SupportViewDataChecker.checkOldData(childView,new EventBean[]{ events.get("a1") });
        SupportViewDataChecker.checkNewData(childView, null);

        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(10000L) != null);
        schedulingServiceStub.getAdded().clear();

        // Send another 2 events
        schedulingServiceStub.setTime(startTime + TEST_WINDOW_MSEC);
        myView.update(new EventBean[]{ events.get("d1") }, null);
        SupportViewDataChecker.checkOldData(childView,null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[]{ events.get("d1") });

        schedulingServiceStub.setTime(startTime + TEST_WINDOW_MSEC + 1);
        myView.update(new EventBean[]{ events.get("e1") }, null);
        assertTrue(schedulingServiceStub.getAdded().size() == 0);
        SupportViewDataChecker.checkOldData(childView,null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[]{ events.get("e1") });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[]{ events.get("b1"), events.get("b2"), events.get("c1"), events.get("d1"), events.get("e1") } );

        // Pretend callback received
        assertTrue(schedulingServiceStub.getAdded().size() == 0);
        schedulingServiceStub.setTime(startTime + TEST_WINDOW_MSEC + 10000);
        myView.expire();
        SupportViewDataChecker.checkOldData(childView,new EventBean[]{ events.get("b1"), events.get("b2") });
        SupportViewDataChecker.checkNewData(childView, null);
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[]{ events.get("c1"), events.get("d1"), events.get("e1") } );

        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(49999L) != null);
        schedulingServiceStub.getAdded().clear();

        // Pretend callback received
        schedulingServiceStub.setTime(startTime + TEST_WINDOW_MSEC + 59999);
        myView.expire();
        SupportViewDataChecker.checkOldData(childView,new EventBean[]{ events.get("c1") });
        SupportViewDataChecker.checkNewData(childView, null);
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[]{ events.get("d1"), events.get("e1") });

        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(1L) != null);
        schedulingServiceStub.getAdded().clear();

        // Send another event
        schedulingServiceStub.setTime(startTime + TEST_WINDOW_MSEC + 200);
        myView.update(new EventBean[]{ events.get("f1"), events.get("f2") }, null);
        assertTrue(schedulingServiceStub.getAdded().size() == 0);
        SupportViewDataChecker.checkOldData(childView,null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[]{ events.get("f1"), events.get("f2") });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[]{ events.get("d1"), events.get("e1"), events.get("f1"), events.get("f2") });

        // Pretend callback received, we didn't schedule for 1 msec after, but for 100 msec after
        // testing what happens when clock resolution or some other delay happens
        schedulingServiceStub.setTime(startTime + TEST_WINDOW_MSEC + 60099);
        myView.expire();
        SupportViewDataChecker.checkOldData(childView,new EventBean[]{ events.get("d1"), events.get("e1") });
        SupportViewDataChecker.checkNewData(childView, null);
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[]{ events.get("f1"), events.get("f2") } );

        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(101L) != null);
        schedulingServiceStub.getAdded().clear();

        // Pretend callback received
        schedulingServiceStub.setTime(startTime + TEST_WINDOW_MSEC + 60201);
        myView.expire();
        SupportViewDataChecker.checkOldData(childView,new EventBean[]{ events.get("f1"), events.get("f2") });
        SupportViewDataChecker.checkNewData(childView, null);
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),null);
        assertTrue(schedulingServiceStub.getAdded().size() == 0);
    }

    public EventBean[] makeEvents(String[] ids)
    {
        return EventFactoryHelper.makeEvents(ids);
    }
}