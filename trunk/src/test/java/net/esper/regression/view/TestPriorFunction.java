package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPException;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.event.EventBean;

import java.util.Random;

public class TestPriorFunction extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    // TODO: test ExprPriorNode class itself
    // TODO: test joins
    // TODO: rename factoryCallbacks
    // TODO: test prior and previous together, 2 orders
    // TODO: test for PriorEventBufferUnbound
    // TODO: view capability refactoring

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testPriorTimeWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prior(2, symbol) as priorSymbol, " +
                          " prior(2, price) as priorPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:time(1 min) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("priorSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("priorPrice"));

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
        assertOldEvents("D1", null, null);
        sendTimer(61000);
        assertOldEvents("D2", null, null);
        sendTimer(62000);
        assertOldEvents("D3", "D1", 1d);
        sendTimer(63000);
        assertOldEvents("D4", "D2", 2d);
        sendTimer(64000);
        assertOldEvents("D5", "D3", 3d);
        sendTimer(90000);
        assertOldEvents("D6", "D4", 4d);

        sendEvent("D7", 7);
        assertNewEvents("D7", "D5", 5d);
        sendEvent("D8", 8);
        sendEvent("D9", 9);
        sendEvent("D10", 10);
        sendEvent("D11", 11);
        testListener.reset();

        // release batch
        sendTimer(150000);
        EventBean[] oldData = testListener.getLastOldData();
        assertNull(testListener.getLastNewData());
        assertEquals(5, oldData.length);
        assertEvent(oldData[0], "D7", "D5", 5d);
        assertEvent(oldData[1], "D8", "D6", 6d);
        assertEvent(oldData[2], "D9", "D7", 7d);
        assertEvent(oldData[3], "D10", "D8", 8d);
        assertEvent(oldData[4], "D11", "D9", 9d);
    }

    public void testPriorExtTimedWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prior(2, symbol) as priorSymbol, " +
                          " prior(3, price) as priorPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:ext_timed('volume', 1 min) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("priorSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("priorPrice"));

        sendEvent("D1", 1, 0);
        assertNewEvents("D1", null, null);

        sendEvent("D2", 2, 1000);
        assertNewEvents("D2", null, null);

        sendEvent("D3", 3, 3000);
        assertNewEvents("D3", "D1", null);

        sendEvent("D4", 4, 4000);
        assertNewEvents("D4", "D2", 1d);

        sendEvent("D5", 5, 5000);
        assertNewEvents("D5", "D3", 2d);

        sendEvent("D6", 6, 30000);
        assertNewEvents("D6", "D4", 3d);

        sendEvent("D7", 7, 60000);
        assertEvent(testListener.getLastNewData()[0], "D7", "D5", 4d);
        assertEvent(testListener.getLastOldData()[0], "D1", null, null);
        testListener.reset();

        sendEvent("D8", 8, 61000);
        assertEvent(testListener.getLastNewData()[0], "D8", "D6", 5d);
        assertEvent(testListener.getLastOldData()[0], "D2", null, null);
        testListener.reset();

        sendEvent("D9", 9, 63000);
        assertEvent(testListener.getLastNewData()[0], "D9", "D7", 6d);
        assertEvent(testListener.getLastOldData()[0], "D3", "D1", null);
        testListener.reset();

        sendEvent("D10", 10, 64000);
        assertEvent(testListener.getLastNewData()[0], "D10", "D8", 7d);
        assertEvent(testListener.getLastOldData()[0], "D4", "D2", 1d);
        testListener.reset();

        sendEvent("D10", 10, 150000);
        EventBean[] oldData = testListener.getLastOldData();
        assertEquals(6, oldData.length);
        assertEvent(oldData[0], "D5", "D3", 2d);
    }

    public void testPriorTimeBatchWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prior(3, symbol) as priorSymbol, " +
                          " prior(2, price) as priorPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:time_batch(1 min) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("priorSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("priorPrice"));

        sendTimer(0);
        assertFalse(testListener.isInvoked());

        sendEvent("A", 1);
        sendEvent("B", 2);
        assertFalse(testListener.isInvoked());

        sendTimer(60000);
        assertEquals(2, testListener.getLastNewData().length);
        assertEvent(testListener.getLastNewData()[0], "A", null, null);
        assertEvent(testListener.getLastNewData()[1], "B", null, null);
        assertNull(testListener.getLastOldData());
        testListener.reset();

        sendTimer(80000);
        sendEvent("C", 3);
        assertFalse(testListener.isInvoked());

        sendTimer(120000);
        assertEquals(1, testListener.getLastNewData().length);
        assertEvent(testListener.getLastNewData()[0], "C", null, 1d);
        assertEquals(2, testListener.getLastOldData().length);
        assertEvent(testListener.getLastOldData()[0], "A", null, null);
        testListener.reset();

        sendTimer(300000);
        sendEvent("D", 4);
        sendEvent("E", 5);
        sendEvent("F", 6);
        sendEvent("G", 7);
        sendTimer(360000);
        assertEquals(4, testListener.getLastNewData().length);
        assertEvent(testListener.getLastNewData()[0], "D", "A", 2d);
        assertEvent(testListener.getLastNewData()[1], "E", "B", 3d);
        assertEvent(testListener.getLastNewData()[2], "F", "C", 4d);
        assertEvent(testListener.getLastNewData()[3], "G", "D", 5d);
    }

    public void testPriorUnbound()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prior(3, symbol) as priorSymbol, " +
                          " prior(2, price) as priorPrice " +
                          "from " + SupportMarketDataBean.class.getName();

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("priorSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("priorPrice"));

        sendEvent("A", 1);
        assertNewEvents("A", null, null);

        sendEvent("B", 2);
        assertNewEvents("B", null, null);

        sendEvent("C", 3);
        assertNewEvents("C", null, 1d);

        sendEvent("D", 4);
        assertNewEvents("D", "A", 2d);

        sendEvent("E", 5);
        assertNewEvents("E", "B", 3d);
    }

    public void testLongRunningSingle()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prior(3, symbol) as prior0Symbol " +
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort('symbol', false, 3)";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        Random random = new Random();
        // 200000 is a better number for a memory test, however for short unit tests this is 2000
        for (int i = 0; i < 2000; i++)
        {
            if (i % 10000 == 0)
            {
                System.out.println(i);
            }

            sendEvent(Integer.toString(random.nextInt()), 4);

            if (i % 1000 == 0)
            {
                testListener.reset();
            }
        }
    }

    public void testLongRunningUnbound()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prior(3, symbol) as prior0Symbol " +
                          "from " + SupportMarketDataBean.class.getName();

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        Random random = new Random();
        // 200000 is a better number for a memory test, however for short unit tests this is 2000
        for (int i = 0; i < 2000; i++)
        {
            if (i % 10000 == 0)
            {
                System.out.println(i);
            }

            sendEvent(Integer.toString(random.nextInt()), 4);

            if (i % 1000 == 0)
            {
                testListener.reset();
            }
        }
    }

    public void testLongRunningMultiple()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prior(3, symbol) as prior0Symbol, " +
                          " prior(2, symbol) as prior1Symbol, " +
                          " prior(1, symbol) as prior2Symbol, " +
                          " prior(0, symbol) as prior3Symbol, " +
                          " prior(0, price) as prior0Price, " +
                          " prior(1, price) as prior1Price, " +
                          " prior(2, price) as prior2Price, " +
                          " prior(3, price) as prior3Price " +
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort('symbol', false, 3)";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        Random random = new Random();
        // 200000 is a better number for a memory test, however for short unit tests this is 2000
        for (int i = 0; i < 2000; i++)
        {
            if (i % 10000 == 0)
            {
                System.out.println(i);
            }

            sendEvent(Integer.toString(random.nextInt()), 4);

            if (i % 1000 == 0)
            {
                testListener.reset();
            }
        }
    }

    public void testPriorLengthWindow()
    {
        String viewExpr =   "select symbol as currSymbol, " +
                            "prior(0, symbol) as prior0Symbol, " +
                            "prior(1, symbol) as prior1Symbol, " +
                            "prior(2, symbol) as prior2Symbol, " +
                            "prior(3, symbol) as prior3Symbol, " +
                            "prior(0, price) as prior0Price, " +
                            "prior(1, price) as prior1Price, " +
                            "prior(2, price) as prior2Price, " +
                            "prior(3, price) as prior3Price " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length(3) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prior0Symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prior0Price"));

        sendEvent("A", 1);
        assertNewEvents("A", "A", 1d, null, null, null, null, null, null);
        sendEvent("B", 2);
        assertNewEvents("B", "B", 2d, "A", 1d, null, null, null, null);
        sendEvent("C", 3);
        assertNewEvents("C", "C", 3d, "B", 2d, "A", 1d, null, null);

        sendEvent("D", 4);
        EventBean newEvent = testListener.getLastNewData()[0];
        EventBean oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "D", "D", 4d, "C", 3d, "B", 2d, "A", 1d);
        assertEventProps(oldEvent, "A", "A", 1d, null, null, null, null, null, null);

        sendEvent("E", 5);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "E", "E", 5d, "D", 4d, "C", 3d, "B", 2d);
        assertEventProps(oldEvent, "B", "B", 2d, "A", 1d, null, null, null, null);

        sendEvent("F", 6);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "F", "F", 6d, "E", 5d, "D", 4d, "C", 3d);
        assertEventProps(oldEvent, "C", "C", 3d, "B", 2d, "A", 1d, null, null);

        sendEvent("G", 7);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "G", "G", 7d, "F", 6d, "E", 5d, "D", 4d);
        assertEventProps(oldEvent, "D", "D", 4d, "C", 3d, "B", 2d, "A", 1d);

        sendEvent("G", 8);
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(oldEvent, "E", "E", 5d, "D", 4d, "C", 3d, "B", 2d);
    }

    public void testPriorLengthWindowWhere()
    {
        String viewExpr =   "select prior(2, symbol) as currSymbol " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length(1) " +
                            "where prior(2, price) > 100";

        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        sendEvent("A", 1);
        sendEvent("B", 130);
        sendEvent("C", 10);
        assertFalse(testListener.isInvoked());
        sendEvent("D", 5);
        assertEquals("B", testListener.assertOneGetNewAndReset().get("currSymbol"));
    }

    public void testPriorSortWindow()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prior(0, symbol) as prior0Symbol, " +
                          " prior(1, symbol) as prior1Symbol, " +
                          " prior(2, symbol) as prior2Symbol, " +
                          " prior(3, symbol) as prior3Symbol, " +
                          " prior(0, price) as prior0Price, " +
                          " prior(1, price) as prior1Price, " +
                          " prior(2, price) as prior2Price, " +
                          " prior(3, price) as prior3Price " +
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort('symbol', false, 3)";
        tryPriorSortWindow(viewExpr);

        viewExpr = "select symbol as currSymbol, " +
                          " prior(3, symbol) as prior3Symbol, " +
                          " prior(1, symbol) as prior1Symbol, " +
                          " prior(2, symbol) as prior2Symbol, " +
                          " prior(0, symbol) as prior0Symbol, " +
                          " prior(2, price) as prior2Price, " +
                          " prior(1, price) as prior1Price, " +
                          " prior(0, price) as prior0Price, " +
                          " prior(3, price) as prior3Price " +
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort('symbol', false, 3)";
        tryPriorSortWindow(viewExpr);
    }


    public void tryPriorSortWindow(String viewExpr)
    {
        EPStatement selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        sendEvent("COX", 30);
        assertNewEvents("COX", "COX", 30d, null, null, null, null, null, null);

        sendEvent("IBM", 45);
        assertNewEvents("IBM", "IBM", 45d, "COX", 30d, null, null, null, null);

        sendEvent("MSFT", 33);
        assertNewEvents("MSFT", "MSFT", 33d, "IBM", 45d, "COX", 30d, null, null);

        sendEvent("XXX", 55);
        EventBean newEvent = testListener.getLastNewData()[0];
        EventBean oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "XXX", "XXX", 55d, "MSFT", 33d, "IBM", 45d, "COX", 30d);
        assertEventProps(oldEvent, "XXX", "XXX", 55d, "MSFT", 33d, "IBM", 45d, "COX", 30d);

        sendEvent("BOO", 20);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "BOO", "BOO", 20d, "XXX", 55d, "MSFT", 33d, "IBM", 45d);
        assertEventProps(oldEvent, "MSFT", "MSFT", 33d, "IBM", 45d, "COX", 30d, null, null);

        sendEvent("DOR", 1);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "DOR", "DOR", 1d, "BOO", 20d, "XXX", 55d, "MSFT", 33d);
        assertEventProps(oldEvent, "IBM", "IBM", 45d, "COX", 30d, null, null, null, null);

        sendEvent("AAA", 2);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "AAA", "AAA", 2d, "DOR", 1d, "BOO", 20d, "XXX", 55d);
        assertEventProps(oldEvent, "DOR", "DOR", 1d, "BOO", 20d, "XXX", 55d, "MSFT", 33d);

        sendEvent("AAB", 2);
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(oldEvent, "COX", "COX", 30d, null, null, null, null, null, null);
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
                                 String priorSymbol,
                                 Double priorPrice)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEvent(newData[0], currSymbol, priorSymbol, priorPrice);

        testListener.reset();
    }

    private void assertEvent(EventBean eventBean,
                             String currSymbol,
                             String priorSymbol,
                             Double priorPrice)
    {
        assertEquals(currSymbol, eventBean.get("currSymbol"));
        assertEquals(priorSymbol, eventBean.get("priorSymbol"));
        assertEquals(priorPrice, eventBean.get("priorPrice"));
    }

    private void assertNewEvents(String currSymbol,
                                 String prior0Symbol,
                                 Double prior0Price,
                                 String prior1Symbol,
                                 Double prior1Price,
                                 String prior2Symbol,
                                 Double prior2Price,
                                 String prior3Symbol,
                                 Double prior3Price)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);
        assertEventProps(newData[0], currSymbol, prior0Symbol, prior0Price, prior1Symbol, prior1Price, prior2Symbol, prior2Price, prior3Symbol, prior3Price);

        testListener.reset();
    }

    private void assertEventProps(EventBean eventBean,
                                  String currSymbol,
                                  String prior0Symbol,
                                  Double prior0Price,
                                  String prior1Symbol,
                                  Double prior1Price,
                                  String prior2Symbol,
                                  Double prior2Price,
                                  String prior3Symbol,
                                  Double prior3Price)
    {
        assertEquals(currSymbol, eventBean.get("currSymbol"));
        assertEquals(prior0Symbol, eventBean.get("prior0Symbol"));
        assertEquals(prior0Price, eventBean.get("prior0Price"));
        assertEquals(prior1Symbol, eventBean.get("prior1Symbol"));
        assertEquals(prior1Price, eventBean.get("prior1Price"));
        assertEquals(prior2Symbol, eventBean.get("prior2Symbol"));
        assertEquals(prior2Price, eventBean.get("prior2Price"));
        assertEquals(prior3Symbol, eventBean.get("prior3Symbol"));
        assertEquals(prior3Price, eventBean.get("prior3Price"));

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

    private void sendEvent(String symbol, double price, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void assertOldEvents(String currSymbol,
                                 String priorSymbol,
                                 Double priorPrice)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(newData);
        assertEquals(1, oldData.length);

        assertEquals(currSymbol, oldData[0].get("currSymbol"));
        assertEquals(priorSymbol, oldData[0].get("priorSymbol"));
        assertEquals(priorPrice, oldData[0].get("priorPrice"));

        testListener.reset();
    }
}
