package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestAggregateRowPerEvent extends TestCase
{
    private final static String JOIN_KEY = "KEY";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testSumOneView()
    {
        String viewExpr = "select longPrimitive, sum(longBoxed) as mySum " +
                          "from " + SupportBean.class.getName() + ".win:length(3)";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        runAssert();
    }

    public void testSumJoin()
    {
        String viewExpr = "select longPrimitive, sum(longBoxed) as mySum " +
                          "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
                                    SupportBean.class.getName() + ".win:length(3) as two " +
                          "where one.string = two.string";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(JOIN_KEY));

        runAssert();
    }

    public void testSumAvgWithWhere()
    {
        String viewExpr = "select 'IBM stats' as title, volume, avg(volume) as myAvg, sum(volume) as mySum " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3)" +
                          "where symbol='IBM'";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
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
        // assert select result type
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("mySum"));

        sendEvent(10);
        assertEquals(10L, testListener.getAndResetLastNewData()[0].get("mySum"));
        sendEvent(15);
        assertEquals(25L, testListener.getAndResetLastNewData()[0].get("mySum"));
        sendEvent(-5);
        assertEquals(20L, testListener.getAndResetLastNewData()[0].get("mySum"));
        assertNull(testListener.getLastOldData());

        sendEvent(-2);
        assertEquals(20L, testListener.getLastOldData()[0].get("mySum"));
        assertEquals(8L, testListener.getAndResetLastNewData()[0].get("mySum"));

        sendEvent(100);
        assertEquals(8L, testListener.getLastOldData()[0].get("mySum"));
        assertEquals(93L, testListener.getAndResetLastNewData()[0].get("mySum"));

        sendEvent(1000);
        assertEquals(93L, testListener.getLastOldData()[0].get("mySum"));
        assertEquals(1098L, testListener.getAndResetLastNewData()[0].get("mySum"));
    }

    private void sendEvent(long longBoxed, int intBoxed, short shortBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(JOIN_KEY);
        bean.setLongBoxed(longBoxed);
        bean.setIntBoxed(intBoxed);
        bean.setShortBoxed(shortBoxed);
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
