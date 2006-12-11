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

    // TODO: test where, group-by, having, order, group-by window
    // TODO: test other data windows, multiple previous functions with sub-functions
    // TODO: window with subviews such as win:length(1).stat:uni() ...
    // TODO: previous with expressions

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

        // Test remove stream, always returns null as previous function
        // returns null for remove stream for time windows
        sendTimer(60000);
        assertOldEvents("D1", null, null);
        sendTimer(61000);
        assertOldEvents("D2", null, null);
        sendTimer(62000);
        assertOldEvents("D3", null, null);
        sendTimer(63000);
        assertOldEvents("D4", null, null);
        sendTimer(64000);
        assertOldEvents("D5", null, null);
        sendTimer(90000);
        assertOldEvents("D6", null, null);
    }

    public void testPreviousTimeBatchWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " previous(2, symbol) as prevSymbol, " +
                          " previous(2, price) as prevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:time_batch(1 min) ";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prevSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));

        sendTimer(0);
        assertFalse(testListener.isInvoked());

        sendEvent("A", 1);
        sendEvent("B", 2);
        assertFalse(testListener.isInvoked());

        sendTimer(60000);
        assertEquals(2, testListener.getLastNewData().length);
        assertNewEvents(testListener.getLastNewData()[0], "A", null, null);
        assertNewEvents(testListener.getLastNewData()[1], "B", null, null);
        assertNull(testListener.getLastOldData());
        testListener.reset();

        sendTimer(80000);
        sendEvent("C", 1);
        assertFalse(testListener.isInvoked());

        sendTimer(120000);
        assertEquals(1, testListener.getLastNewData().length);
        assertNewEvents(testListener.getLastNewData()[0], "C", null, null);
        assertNull(testListener.getLastOldData());
    }

    public void testPreviousLengthWindow()
    {
        String viewExpr =   "select symbol as currSymbol, " +
                            "previous(0, symbol) as prev0Symbol, " +
                            "previous(1, symbol) as prev1Symbol, " +
                            "previous(2, symbol) as prev2Symbol, " +
                            "previous(0, price) as prev0Price, " +
                            "previous(1, price) as prev1Price, " +
                            "previous(2, price) as prev2Price " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length(3) ";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prev0Symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prev0Price"));

        sendEvent("A", 1);
        assertNewEvents("A", "A", 1d, null, null, null, null);
        sendEvent("B", 2);
        assertNewEvents("B", "B", 2d, "A", 1d, null, null);
        sendEvent("C", 3);
        assertNewEvents("C", "C", 3d, "B", 2d, "A", 1d);
        sendEvent("D", 4);
        EventBean newEvent = testListener.getLastNewData()[0];
        EventBean oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "D", "D", 4d, "C", 3d, "B", 2d);
        assertEventProps(oldEvent, "A", null, null, null, null, null, null);
    }

    public void testPreviousSortWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " previous(0, symbol) as prev0Symbol, " +
                          " previous(1, symbol) as prev1Symbol, " +
                          " previous(2, symbol) as prev2Symbol, " +
                          " previous(0, price) as prev0Price, " +
                          " previous(1, price) as prev1Price, " +
                          " previous(2, price) as prev2Price " +
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort('symbol', true, 100)";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prev0Symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prev0Price"));

        sendEvent("COX", 30);
        assertNewEvents("COX", "COX", 30d, null, null, null, null);
    }

    private void assertNewEvents(String currSymbol,
                                 String prevSymbol,
                                 Double prevPrice)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertNewEvents(newData[0], currSymbol, prevSymbol, prevPrice);

        testListener.reset();
    }

    private void assertNewEvents(EventBean eventBean,
                                 String currSymbol,
                                 String prevSymbol,
                                 Double prevPrice)
    {
        assertEquals(currSymbol, eventBean.get("currSymbol"));
        assertEquals(prevSymbol, eventBean.get("prevSymbol"));
        assertEquals(prevPrice, eventBean.get("prevPrice"));
    }

    private void assertNewEvents(String currSymbol,
                                 String prev0Symbol,
                                 Double prev0Price,
                                 String prev1Symbol,
                                 Double prev1Price,
                                 String prev2Symbol,
                                 Double prev2Price)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);
        assertEventProps(newData[0], currSymbol, prev0Symbol, prev0Price, prev1Symbol, prev1Price, prev2Symbol, prev2Price);

        testListener.reset();
    }

    private void assertEventProps(EventBean eventBean,
                                  String currSymbol,
                                  String prev0Symbol,
                                  Double prev0Price,
                                  String prev1Symbol,
                                  Double prev1Price,
                                  String prev2Symbol,
                                  Double prev2Price)
    {
        assertEquals(currSymbol, eventBean.get("currSymbol"));
        assertEquals(prev0Symbol, eventBean.get("prev0Symbol"));
        assertEquals(prev0Price, eventBean.get("prev0Price"));
        assertEquals(prev1Symbol, eventBean.get("prev1Symbol"));
        assertEquals(prev1Price, eventBean.get("prev1Price"));
        assertEquals(prev2Symbol, eventBean.get("prev2Symbol"));
        assertEquals(prev2Price, eventBean.get("prev2Price"));

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

    private static final Log log = LogFactory.getLog(TestPreviousFunction.class);
}
