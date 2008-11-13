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
import com.espertech.esper.event.EventBean;

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

    private static final Log log = LogFactory.getLog(TestAggregateRowPerEvent.class);
}
