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

package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestAggregateRowPerEvent extends TestCase
{
    private final static String JOIN_KEY = "KEY";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;
    private int eventCount;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        eventCount = 0;
    }

    public void testAggregatedSelectUnaggregatedHaving() {
        // ESPER-571
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        String epl = "select max(intPrimitive) as val from SupportBean.win:time(1) having max(intPrimitive) > intBoxed";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(testListener);

        sendEvent("E1", 10, 1);
        assertEquals(10, testListener.assertOneGetNewAndReset().get("val"));

        sendEvent("E2", 10, 11);
        assertFalse(testListener.isInvoked());

        sendEvent("E3", 15, 11);
        assertEquals(15, testListener.assertOneGetNewAndReset().get("val"));

        sendEvent("E4", 20, 11);
        assertEquals(20, testListener.assertOneGetNewAndReset().get("val"));

        sendEvent("E5", 25, 25);
        assertFalse(testListener.isInvoked());
    }

    public void testSumOneView()
    {
        String viewExpr = "select irstream longPrimitive, sum(longBoxed) as mySum " +
                          "from " + SupportBean.class.getName() + ".win:length(3)";
        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        runAssert();
    }

    public void testSumJoin()
    {
        String viewExpr = "select irstream longPrimitive, sum(longBoxed) as mySum " +
                          "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
                                    SupportBean.class.getName() + ".win:length(3) as two " +
                          "where one.string = two.string";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(JOIN_KEY));

        runAssert();
    }

    public void testSumAvgWithWhere()
    {
        String viewExpr = "select 'IBM stats' as title, volume, avg(volume) as myAvg, sum(volume) as mySum " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3)" +
                          "where symbol='IBM'";
        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        sendMarketDataEvent("GE", 10L);
        assertFalse(testListener.isInvoked());

        sendMarketDataEvent("IBM", 20L);
        assertPostedNew(20d, 20L);

        sendMarketDataEvent("XXX", 10000L);
        assertFalse(testListener.isInvoked());

        sendMarketDataEvent("IBM", 30L);
        assertPostedNew(25d, 50L);
    }

    private void assertPostedNew(Double newAvg, Long newSum)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEquals("IBM stats", newData[0].get("title"));
        assertEquals(newAvg, newData[0].get("myAvg"));
        assertEquals(newSum, newData[0].get("mySum"));

        testListener.reset();
    }

    private void runAssert()
    {
        String[] fields = new String[] {"longPrimitive", "mySum"};

        // assert select result type
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("mySum"));
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, null);

        sendEvent(10);
        assertEquals(10L, testListener.getAndResetLastNewData()[0].get("mySum"));
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{1L, 10L}});

        sendEvent(15);
        assertEquals(25L, testListener.getAndResetLastNewData()[0].get("mySum"));
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{1L, 25L}, {2L, 25L}});

        sendEvent(-5);
        assertEquals(20L, testListener.getAndResetLastNewData()[0].get("mySum"));
        assertNull(testListener.getLastOldData());
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{1L, 20L}, {2L, 20L}, {3L, 20L}});

        sendEvent(-2);
        assertEquals(8L, testListener.getLastOldData()[0].get("mySum"));
        assertEquals(8L, testListener.getAndResetLastNewData()[0].get("mySum"));
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{4L, 8L}, {2L, 8L}, {3L, 8L}});

        sendEvent(100);
        assertEquals(93L, testListener.getLastOldData()[0].get("mySum"));
        assertEquals(93L, testListener.getAndResetLastNewData()[0].get("mySum"));
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{4L, 93L}, {5L, 93L}, {3L, 93L}});

        sendEvent(1000);
        assertEquals(1098L, testListener.getLastOldData()[0].get("mySum"));
        assertEquals(1098L, testListener.getAndResetLastNewData()[0].get("mySum"));
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {{4L, 1098L}, {5L, 1098L}, {6L, 1098L}});
    }

    private void sendEvent(long longBoxed, int intBoxed, short shortBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(JOIN_KEY);
        bean.setLongBoxed(longBoxed);
        bean.setIntBoxed(intBoxed);
        bean.setShortBoxed(shortBoxed);
        bean.setLongPrimitive(++eventCount);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendMarketDataEvent(String symbol, Long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEvent(long longBoxed)
    {
        sendEvent(longBoxed, 0, (short)0);
    }

    private void sendEvent(String string, int intPrimitive, int intBoxed) {
        SupportBean event = new SupportBean(string, intPrimitive);
        event.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(event);
    }

    private static final Log log = LogFactory.getLog(TestAggregateRowPerEvent.class);
}
