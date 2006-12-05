package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPreviousFunction extends TestCase
{
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

    public void testPreviousTimeWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " previous(2, symbol) as prevSymbol, " +
                          " previous(2, price) as prevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:time(1 min) ";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prevSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));

        sendTimer(0);
        assertFalse(testListener.isInvoked());

        sendEvent("D1", 1);
        assertNewEvents("D1", null, null);

        sendTimer(1000);
        assertFalse(testListener.isInvoked());

        sendEvent("D2", 2);
        assertNewEvents("D2", null, null);

        sendTimer(2000);
        assertFalse(testListener.isInvoked());

        sendEvent("D3", 3);
        assertNewEvents("D3", "D1", 1d);

        sendTimer(3000);
        assertFalse(testListener.isInvoked());

        sendEvent("D4", 4);
        assertNewEvents("D4", "D2", 2d);

        sendTimer(4000);
        assertFalse(testListener.isInvoked());

        sendEvent("D5", 5);
        assertNewEvents("D5", "D3", 3d);

        sendTimer(30000);
        assertFalse(testListener.isInvoked());

        sendEvent("D6", 6);
        assertNewEvents("D6", "D4", 4d);

        sendTimer(60000);
        assertOldEvents("D1", "D4", 4d); 

        sendTimer(61000);
        assertOldEvents("D2", "D4", 4d);

        sendTimer(62000);
        assertOldEvents("D3", "D4", 4d);

        sendTimer(63000);
        assertOldEvents("D4", "D4", 4d); // is D4 since the 3rd newest row is D4

        sendTimer(64000);
        assertOldEvents("D5", null, null); // is null since we have no 2 newest row

        sendTimer(90000);
        assertOldEvents("D6", null, null);
    }

    private void assertNewEvents(String currSymbol,
                                 String prevSymbol,
                                 Double prevPrice)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEquals(currSymbol, newData[0].get("currSymbol"));
        assertEquals(prevSymbol, newData[0].get("prevSymbol"));
        assertEquals(prevPrice, newData[0].get("prevPrice"));

        testListener.reset();
    }

    private void assertOldEvents(String currSymbol,
                                 String prevSymbol,
                                 Double prevPrice)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(newData);
        assertEquals(1, oldData.length);

        assertEquals(currSymbol, oldData[0].get("currSymbol"));
        assertEquals(prevSymbol, oldData[0].get("prevSymbol"));
        assertEquals(prevPrice, oldData[0].get("prevPrice"));

        testListener.reset();
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestPreviousFunction.class);
}
