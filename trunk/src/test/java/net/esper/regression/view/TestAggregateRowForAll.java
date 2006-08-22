package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestAggregateRowForAll extends TestCase
{
    private final static String JOIN_KEY = "KEY";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testSumOneView()
    {
        String viewExpr = "select sum(longBoxed) as mySum " +
                          "from " + SupportBean.class.getName() + ".win:time(10)";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        runAssert();
    }

    public void testSumJoin()
    {
        String viewExpr = "select sum(longBoxed) as mySum " +
                          "from " + SupportBeanString.class.getName() + ".win:time(10) as one, " +
                                    SupportBean.class.getName() + ".win:time(10) as two " +
                          "where one.string = two.string";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(JOIN_KEY));

        runAssert();
    }

    private void runAssert()
    {
        // assert select result type
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("mySum"));

        sendTimerEvent(0);
        sendEvent(10);
        assertEquals(10L, testListener.getAndResetLastNewData()[0].get("mySum"));

        sendTimerEvent(5000);
        sendEvent(15);
        assertEquals(25L, testListener.getAndResetLastNewData()[0].get("mySum"));

        sendTimerEvent(8000);
        sendEvent(-5);
        assertEquals(20L, testListener.getAndResetLastNewData()[0].get("mySum"));
        assertNull(testListener.getLastOldData());

        sendTimerEvent(10000);
        assertEquals(20L, testListener.getLastOldData()[0].get("mySum"));
        assertEquals(10L, testListener.getAndResetLastNewData()[0].get("mySum"));

        sendTimerEvent(15000);
        assertEquals(10L, testListener.getLastOldData()[0].get("mySum"));
        assertEquals(-5L, testListener.getAndResetLastNewData()[0].get("mySum"));

        sendTimerEvent(18000);
        assertEquals(-5L, testListener.getLastOldData()[0].get("mySum"));
        assertNull(testListener.getAndResetLastNewData()[0].get("mySum"));
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

    private void sendEvent(long longBoxed)
    {
        sendEvent(longBoxed, 0, (short)0);
    }

    private void sendTimerEvent(long msec)
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(msec));
    }

    private static final Log log = LogFactory.getLog(TestAggregateRowForAll.class);
}
