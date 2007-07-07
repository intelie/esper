package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;

public class TestPreviousFunction extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testPrevCountStarWithStaticMethod()
    {
        String text = "select count(*) as total, " +
                      "prev(" + TestPreviousFunction.class.getName() + ".intToLong(count(*)) - 1, price) as firstPrice from " + SupportMarketDataBean.class.getName() + ".win:time(60)";
        EPStatement stmt = epService.getEPAdministrator().createEQL(text);
        stmt.addListener(testListener);

        assertPrevCount();
    }

    public void testPrevCountStar()
    {
        String text = "select count(*) as total, " +
                      "prev(count(*) - 1, price) as firstPrice from " + SupportMarketDataBean.class.getName() + ".win:time(60)";
        EPStatement stmt = epService.getEPAdministrator().createEQL(text);
        stmt.addListener(testListener);

        assertPrevCount();
    }

    private void assertPrevCount()
    {
        sendTimer(0);
        sendMarketEvent("IBM", 75);
        assertCountAndPrice(testListener.assertOneGetNewAndReset(), 1L, 75D);

        sendMarketEvent("IBM", 76);
        assertCountAndPrice(testListener.assertOneGetNewAndReset(), 2L, 75D);

        sendTimer(10000);
        sendMarketEvent("IBM", 77);
        assertCountAndPrice(testListener.assertOneGetNewAndReset(), 3L, 75D);

        sendTimer(20000);
        sendMarketEvent("IBM", 78);
        assertCountAndPrice(testListener.assertOneGetNewAndReset(), 4L, 75D);

        sendTimer(50000);
        sendMarketEvent("IBM", 79);
        assertCountAndPrice(testListener.assertOneGetNewAndReset(), 5L, 75D);

        sendTimer(60000);
        assertEquals(1, testListener.getOldDataList().size());
        EventBean[] oldData = testListener.getLastOldData();
        assertEquals(2, oldData.length);
        assertCountAndPrice(oldData[0], 5L, null);
        testListener.reset();

        sendMarketEvent("IBM", 80);
        assertCountAndPrice(testListener.assertOneGetNewAndReset(), 4L, 77D);

        sendTimer(65000);
        assertFalse(testListener.isInvoked());

        sendTimer(70000);
        assertEquals(1, testListener.getOldDataList().size());
        oldData = testListener.getLastOldData();
        assertEquals(1, oldData.length);
        assertCountAndPrice(oldData[0], 4L, null);
        testListener.reset();

        sendTimer(80000);
        testListener.reset();

        sendMarketEvent("IBM", 81);
        assertCountAndPrice(testListener.assertOneGetNewAndReset(), 3L, 79D);

        sendTimer(120000);
        testListener.reset();

        sendMarketEvent("IBM", 82);
        assertCountAndPrice(testListener.assertOneGetNewAndReset(), 2L, 81D);

        sendTimer(300000);
        testListener.reset();

        sendMarketEvent("IBM", 83);
        assertCountAndPrice(testListener.assertOneGetNewAndReset(), 1L, 83D);
    }

    public void testSortWindowPerGroup()
    {
        // descending sort
        String viewExpr = "select symbol, prev(1, price) as prevPrice, prev(2, price) as prevPrevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".std:groupby('symbol').ext:sort('price', false, 10) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrevPrice"));

        sendMarketEvent("IBM", 75);
        assertReceived("IBM", null, null);
        sendMarketEvent("IBM", 80);
        assertReceived("IBM", 80d, null);
        sendMarketEvent("IBM", 79);
        assertReceived("IBM", 79d, 80d);
        sendMarketEvent("IBM", 81);
        assertReceived("IBM", 79d, 80d);
        sendMarketEvent("IBM", 79.5);
        assertReceived("IBM", 79d, 79.5d);

        sendMarketEvent("MSFT", 10);
        assertReceived("MSFT", null, null);
        sendMarketEvent("MSFT", 20);
        assertReceived("MSFT", 20d, null);
        sendMarketEvent("MSFT", 21);
        assertReceived("MSFT", 20d, 21d);

        sendMarketEvent("IBM", 74d);
        assertReceived("IBM", 75d, 79d);

        sendMarketEvent("MSFT", 19);
        assertReceived("MSFT", 19d, 20d);
    }

    public void testTimeBatchPerGroup()
    {
        String viewExpr = "select symbol, prev(1, price) as prevPrice, prev(2, price) as prevPrevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".std:groupby('symbol').win:time_batch(1 sec) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrevPrice"));

        sendTimer(0);
        sendMarketEvent("IBM", 75);
        sendMarketEvent("MSFT", 40);
        sendMarketEvent("IBM", 76);
        sendMarketEvent("CIC", 1);
        sendTimer(1000);

        EventBean[] events = testListener.getLastNewData();
        // order not guaranteed as timed batch, however for testing the order is reliable as schedule buckets are created
        // in a predictable order
        // Previous is looking at the same batch, doesn't consider outside of window
        assertReceived(events[0], "IBM", null, null);
        assertReceived(events[1], "IBM", 75d, null);
        assertReceived(events[2], "MSFT", null, null);
        assertReceived(events[3], "CIC", null, null);

        // Next batch, previous is looking only within the same batch
        sendMarketEvent("MSFT", 41);
        sendMarketEvent("IBM", 77);
        sendMarketEvent("IBM", 78);
        sendMarketEvent("CIC", 2);
        sendMarketEvent("MSFT", 42);
        sendMarketEvent("CIC", 3);
        sendMarketEvent("CIC", 4);
        sendTimer(2000);

        events = testListener.getLastNewData();
        assertReceived(events[0], "IBM", null, null);
        assertReceived(events[1], "IBM", 77d, null);
        assertReceived(events[2], "MSFT", null, null);
        assertReceived(events[3], "MSFT", 41d, null);
        assertReceived(events[4], "CIC", null, null);
        assertReceived(events[5], "CIC", 2d, null);
        assertReceived(events[6], "CIC", 3d, 2d);

        // test for memory leak - comment in and run with large number
        /*
        for (int i = 0; i < 10000; i++)
        {
            sendMarketEvent("MSFT", 41);
            sendTimer(1000 * i);
            testListener.reset();
        }
        */
    }

    public void testLengthBatchPerGroup()
    {
        String viewExpr = "select symbol, prev(1, price) as prevPrice, prev(2, price) as prevPrevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".std:groupby('symbol').win:length_batch(3) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrevPrice"));

        sendMarketEvent("IBM", 75);
        sendMarketEvent("MSFT", 50);
        sendMarketEvent("IBM", 76);
        sendMarketEvent("CIC", 1);
        assertFalse(testListener.isInvoked());
        sendMarketEvent("IBM", 77);

        EventBean[] eventsNew = testListener.getLastNewData();
        assertEquals(3, eventsNew.length);
        assertReceived(eventsNew[0], "IBM", null, null);
        assertReceived(eventsNew[1], "IBM", 75d, null);
        assertReceived(eventsNew[2], "IBM", 76d, 75d);
        testListener.reset();

        // Next batch, previous is looking only within the same batch
        sendMarketEvent("MSFT", 51);
        sendMarketEvent("IBM", 78);
        sendMarketEvent("IBM", 79);
        sendMarketEvent("CIC", 2);
        sendMarketEvent("CIC", 3);

        eventsNew = testListener.getLastNewData();
        assertEquals(3, eventsNew.length);
        assertReceived(eventsNew[0], "CIC", null, null);
        assertReceived(eventsNew[1], "CIC", 1d, null);
        assertReceived(eventsNew[2], "CIC", 2d, 1d);
        testListener.reset();

        sendMarketEvent("MSFT", 52);

        eventsNew = testListener.getLastNewData();
        assertEquals(3, eventsNew.length);
        assertReceived(eventsNew[0], "MSFT", null, null);
        assertReceived(eventsNew[1], "MSFT", 50d, null);
        assertReceived(eventsNew[2], "MSFT", 51d, 50d);
        testListener.reset();

        sendMarketEvent("IBM", 80);

        eventsNew = testListener.getLastNewData();
        EventBean[] eventsOld = testListener.getLastOldData();
        assertEquals(3, eventsNew.length);
        assertEquals(3, eventsOld.length);
        assertReceived(eventsNew[0], "IBM", null, null);
        assertReceived(eventsNew[1], "IBM", 78d, null);
        assertReceived(eventsNew[2], "IBM", 79d, 78d);
        assertReceived(eventsOld[0], "IBM", null, null);
        assertReceived(eventsOld[1], "IBM", null, null);
        assertReceived(eventsOld[2], "IBM", null, null);
    }

    public void testTimeWindowPerGroup()
    {
        String viewExpr = "select symbol, prev(1, price) as prevPrice, prev(2, price) as prevPrevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".std:groupby('symbol').win:time(20 sec) ";
        assertPerGroup(viewExpr);
    }

    public void testExtTimeWindowPerGroup()
    {
        String viewExpr = "select symbol, prev(1, price) as prevPrice, prev(2, price) as prevPrevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".std:groupby('symbol').win:ext_timed('volume', 20 sec) ";
        assertPerGroup(viewExpr);
    }

    public void testLengthWindowPerGroup()
    {
        String viewExpr = "select symbol, prev(1, price) as prevPrice, prev(2, price) as prevPrevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".std:groupby('symbol').win:length(10) ";
        assertPerGroup(viewExpr);
    }

    public void testPreviousTimeWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prev(2, symbol) as prevSymbol, " +
                          " prev(2, price) as prevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:time(1 min) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prevSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));

        sendTimer(0);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("D1", 1);
        assertNewEvents("D1", null, null);

        sendTimer(1000);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("D2", 2);
        assertNewEvents("D2", null, null);

        sendTimer(2000);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("D3", 3);
        assertNewEvents("D3", "D1", 1d);

        sendTimer(3000);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("D4", 4);
        assertNewEvents("D4", "D2", 2d);

        sendTimer(4000);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("D5", 5);
        assertNewEvents("D5", "D3", 3d);

        sendTimer(30000);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("D6", 6);
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

    public void testPreviousExtTimedWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prev(2, symbol) as prevSymbol, " +
                          " prev(2, price) as prevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:ext_timed('volume', 1 min) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prevSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));

        sendMarketEvent("D1", 1, 0);
        assertNewEvents("D1", null, null);

        sendMarketEvent("D2", 2, 1000);
        assertNewEvents("D2", null, null);

        sendMarketEvent("D3", 3, 3000);
        assertNewEvents("D3", "D1", 1d);

        sendMarketEvent("D4", 4, 4000);
        assertNewEvents("D4", "D2", 2d);

        sendMarketEvent("D5", 5, 5000);
        assertNewEvents("D5", "D3", 3d);

        sendMarketEvent("D6", 6, 30000);
        assertNewEvents("D6", "D4", 4d);

        sendMarketEvent("D7", 7, 60000);
        assertEvent(testListener.getLastNewData()[0], "D7", "D5", 5d);
        assertEvent(testListener.getLastOldData()[0], "D1", null, null);
        testListener.reset();

        sendMarketEvent("D8", 8, 61000);
        assertEvent(testListener.getLastNewData()[0], "D8", "D6", 6d);
        assertEvent(testListener.getLastOldData()[0], "D2", null, null);
        testListener.reset();
    }

    public void testPreviousTimeBatchWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prev(2, symbol) as prevSymbol, " +
                          " prev(2, price) as prevPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:time_batch(1 min) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prevSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));

        sendTimer(0);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("A", 1);
        sendMarketEvent("B", 2);
        assertFalse(testListener.isInvoked());

        sendTimer(60000);
        assertEquals(2, testListener.getLastNewData().length);
        assertEvent(testListener.getLastNewData()[0], "A", null, null);
        assertEvent(testListener.getLastNewData()[1], "B", null, null);
        assertNull(testListener.getLastOldData());
        testListener.reset();

        sendTimer(80000);
        sendMarketEvent("C", 3);
        assertFalse(testListener.isInvoked());

        sendTimer(120000);
        assertEquals(1, testListener.getLastNewData().length);
        assertEvent(testListener.getLastNewData()[0], "C", null, null);
        assertEquals(2, testListener.getLastOldData().length);
        assertEvent(testListener.getLastOldData()[0], "A", null, null);
        testListener.reset();

        sendTimer(300000);
        sendMarketEvent("D", 4);
        sendMarketEvent("E", 5);
        sendMarketEvent("F", 6);
        sendMarketEvent("G", 7);
        sendTimer(360000);
        assertEquals(4, testListener.getLastNewData().length);
        assertEvent(testListener.getLastNewData()[0], "D", null, null);
        assertEvent(testListener.getLastNewData()[1], "E", null, null);
        assertEvent(testListener.getLastNewData()[2], "F", "D", 4d);
        assertEvent(testListener.getLastNewData()[3], "G", "E", 5d);
    }

    public void testPreviousTimeBatchWindowJoin()
    {
        String viewExpr = "select string as currSymbol, " +
                          " prev(2, symbol) as prevSymbol, " +
                          " prev(1, price) as prevPrice " +
                          "from " + SupportBean.class.getName() + ", " +
                          SupportMarketDataBean.class.getName() + ".win:time_batch(1 min)";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prevSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));

        sendTimer(0);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("A", 1);
        sendMarketEvent("B", 2);
        sendBeanEvent("X1");
        assertFalse(testListener.isInvoked());

        sendTimer(60000);
        assertEquals(2, testListener.getLastNewData().length);
        assertEvent(testListener.getLastNewData()[0], "X1", null, null);
        assertEvent(testListener.getLastNewData()[1], "X1", null, 1d);
        assertNull(testListener.getLastOldData());
        testListener.reset();

        sendMarketEvent("C1", 11);
        sendMarketEvent("C2", 12);
        sendMarketEvent("C3", 13);
        assertFalse(testListener.isInvoked());

        sendTimer(120000);
        assertEquals(3, testListener.getLastNewData().length);
        assertEvent(testListener.getLastNewData()[0], "X1", null, null);
        assertEvent(testListener.getLastNewData()[1], "X1", null, 11d);
        assertEvent(testListener.getLastNewData()[2], "X1", "C1", 12d);
    }

    public void testPreviousLengthWindow()
    {
        String viewExpr =   "select symbol as currSymbol, " +
                            "prev(0, symbol) as prev0Symbol, " +
                            "prev(1, symbol) as prev1Symbol, " +
                            "prev(2, symbol) as prev2Symbol, " +
                            "prev(0, price) as prev0Price, " +
                            "prev(1, price) as prev1Price, " +
                            "prev(2, price) as prev2Price " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length(3) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prev0Symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prev0Price"));

        sendMarketEvent("A", 1);
        assertNewEvents("A", "A", 1d, null, null, null, null);
        sendMarketEvent("B", 2);
        assertNewEvents("B", "B", 2d, "A", 1d, null, null);
        sendMarketEvent("C", 3);
        assertNewEvents("C", "C", 3d, "B", 2d, "A", 1d);
        sendMarketEvent("D", 4);
        EventBean newEvent = testListener.getLastNewData()[0];
        EventBean oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "D", "D", 4d, "C", 3d, "B", 2d);
        assertEventProps(oldEvent, "A", null, null, null, null, null, null);
    }

    public void testPreviousLengthBatch()
    {
        String viewExpr =   "select symbol as currSymbol, " +
                            "prev(0, symbol) as prev0Symbol, " +
                            "prev(1, symbol) as prev1Symbol, " +
                            "prev(2, symbol) as prev2Symbol, " +
                            "prev(0, price) as prev0Price, " +
                            "prev(1, price) as prev1Price, " +
                            "prev(2, price) as prev2Price " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length_batch(3) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prev0Symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prev0Price"));

        sendMarketEvent("A", 1);
        sendMarketEvent("B", 2);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("C", 3);
        EventBean[] newEvents = testListener.getLastNewData();
        assertEquals(3, newEvents.length);
        assertEventProps(newEvents[0], "A", "A", 1d, null, null, null, null);
        assertEventProps(newEvents[1], "B", "B", 2d, "A", 1d, null, null);
        assertEventProps(newEvents[2], "C", "C", 3d, "B", 2d, "A", 1d);
        testListener.reset();

        sendMarketEvent("D", 4);
        sendMarketEvent("E", 5);
        assertFalse(testListener.isInvoked());

        sendMarketEvent("F", 6);
        newEvents = testListener.getLastNewData();
        EventBean[] oldEvents = testListener.getLastOldData();
        assertEquals(3, newEvents.length);
        assertEquals(3, oldEvents.length);
        assertEventProps(newEvents[0], "D", "D", 4d, null, null, null, null);
        assertEventProps(newEvents[1], "E", "E", 5d, "D", 4d, null, null);
        assertEventProps(newEvents[2], "F", "F", 6d, "E", 5d, "D", 4d);
        assertEventProps(oldEvents[0], "A", null, null, null, null, null, null);
        assertEventProps(oldEvents[1], "B", null, null, null, null, null, null);
        assertEventProps(oldEvents[2], "C", null, null, null, null, null, null);
    }

    public void testPreviousLengthWindowWhere()
    {
        String viewExpr =   "select prev(2, symbol) as currSymbol " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length(100) " +
                            "where prev(2, price) > 100";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        sendMarketEvent("A", 1);
        sendMarketEvent("B", 130);
        sendMarketEvent("C", 10);
        assertFalse(testListener.isInvoked());
        sendMarketEvent("D", 5);
        assertEquals("B", testListener.assertOneGetNewAndReset().get("currSymbol"));
    }

    public void testPreviousLengthWindowDynamic()
    {
        String viewExpr =   "select prev(intPrimitive, string) as sPrev " +
                            "from " + SupportBean.class.getName() + ".win:length(100)";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        sendBeanEvent("A", 1);
        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals(null, event.get("sPrev"));

        sendBeanEvent("B", 0);
        event = testListener.assertOneGetNewAndReset();
        assertEquals("B", event.get("sPrev"));

        sendBeanEvent("C", 2);
        event = testListener.assertOneGetNewAndReset();
        assertEquals("A", event.get("sPrev"));

        sendBeanEvent("D", 1);
        event = testListener.assertOneGetNewAndReset();
        assertEquals("C", event.get("sPrev"));

        sendBeanEvent("E", 4);
        event = testListener.assertOneGetNewAndReset();
        assertEquals("A", event.get("sPrev"));
    }

    public void testPreviousSortWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prev(0, symbol) as prev0Symbol, " +
                          " prev(1, symbol) as prev1Symbol, " +
                          " prev(2, symbol) as prev2Symbol, " +
                          " prev(0, price) as prev0Price, " +
                          " prev(1, price) as prev1Price, " +
                          " prev(2, price) as prev2Price " +
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort('symbol', false, 100)";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prev0Symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prev0Price"));

        sendMarketEvent("COX", 30);
        assertNewEvents("COX", "COX", 30d, null, null, null, null);

        sendMarketEvent("IBM", 45);
        assertNewEvents("IBM", "COX", 30d, "IBM", 45d, null, null);

        sendMarketEvent("MSFT", 33);
        assertNewEvents("MSFT", "COX", 30d, "IBM", 45d, "MSFT", 33d);

        sendMarketEvent("XXX", 55);
        assertNewEvents("XXX", "COX", 30d, "IBM", 45d, "MSFT", 33d);

        sendMarketEvent("CXX", 56);
        assertNewEvents("CXX", "COX", 30d, "CXX", 56d, "IBM", 45d);

        sendMarketEvent("GE", 1);
        assertNewEvents("GE", "COX", 30d, "CXX", 56d, "GE", 1d);

        sendMarketEvent("AAA", 1);
        assertNewEvents("AAA", "AAA", 1d, "COX", 30d, "CXX", 56d);
    }

    public void testInvalid()
    {
        tryInvalid("select prev(0, average) " +
                "from " + SupportMarketDataBean.class.getName() + ".win:length(100).stat:uni('price')",
                "Error starting view: Previous function requires a single data window view onto the stream [select prev(0, average) from net.esper.support.bean.SupportMarketDataBean.win:length(100).stat:uni('price')]");
    }

    private void tryInvalid(String statement, String expectedError)
    {
        try
        {
            epService.getEPAdministrator().createEQL(statement);
            fail();
        }
        catch (EPException ex)
        {
            // expected
            assertEquals(expectedError, ex.getMessage());
        }
    }

    private void assertNewEvents(String currSymbol,
                                 String prevSymbol,
                                 Double prevPrice)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEvent(newData[0], currSymbol, prevSymbol, prevPrice);

        testListener.reset();
    }

    private void assertEvent(EventBean eventBean,
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

    private void sendMarketEvent(String symbol, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendMarketEvent(String symbol, double price, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendBeanEvent(String string)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendBeanEvent(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
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

    private void assertPerGroup(String statement)
    {
        EPStatement selectTestView = epService.getEPAdministrator().createEQL(statement);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrice"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prevPrevPrice"));

        sendMarketEvent("IBM", 75);
        assertReceived("IBM", null, null);

        sendMarketEvent("MSFT", 40);
        assertReceived("MSFT", null, null);

        sendMarketEvent("IBM", 76);
        assertReceived("IBM", 75d, null);

        sendMarketEvent("CIC", 1);
        assertReceived("CIC", null, null);

        sendMarketEvent("MSFT", 41);
        assertReceived("MSFT", 40d, null);

        sendMarketEvent("IBM", 77);
        assertReceived("IBM", 76d, 75d);

        sendMarketEvent("IBM", 78);
        assertReceived("IBM", 77d, 76d);

        sendMarketEvent("CIC", 2);
        assertReceived("CIC", 1d, null);

        sendMarketEvent("MSFT", 42);
        assertReceived("MSFT", 41d, 40d);

        sendMarketEvent("CIC", 3);
        assertReceived("CIC", 2d, 1d);
    }

    private void assertReceived(String symbol, Double prevPrice, Double prevPrevPrice)
    {
        EventBean event = testListener.assertOneGetNewAndReset();
        assertReceived(event, symbol, prevPrice, prevPrevPrice);
    }

    private void assertReceived(EventBean event, String symbol, Double prevPrice, Double prevPrevPrice)
    {
        assertEquals(symbol, event.get("symbol"));
        assertEquals(prevPrice, event.get("prevPrice"));
        assertEquals(prevPrevPrice, event.get("prevPrevPrice"));
    }

    private void assertCountAndPrice(EventBean event, Long total, Double price)
    {
        assertEquals(total, event.get("total"));
        assertEquals(price, event.get("firstPrice"));
    }

    public static Integer intToLong(Long longValue)
    {
        if (longValue == null)
        {
            return null;
        }
        else
        {
            return longValue.intValue();
        }
    }
}
