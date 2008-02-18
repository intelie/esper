package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;

import java.util.Random;

public class TestPriorFunction extends TestCase
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

    public void testPriorTimeWindow()
    {
        String viewExpr = "select irstream symbol as currSymbol, " +
                          " prior(2, symbol) as priorSymbol, " +
                          " prior(2, price) as priorPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:time(1 min) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("priorSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("priorPrice"));

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

        sendMarketEvent("D7", 7);
        assertNewEvents("D7", "D5", 5d);
        sendMarketEvent("D8", 8);
        sendMarketEvent("D9", 9);
        sendMarketEvent("D10", 10);
        sendMarketEvent("D11", 11);
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
        String viewExpr = "select irstream symbol as currSymbol, " +
                          " prior(2, symbol) as priorSymbol, " +
                          " prior(3, price) as priorPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:ext_timed('volume', 1 min) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("priorSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("priorPrice"));

        sendMarketEvent("D1", 1, 0);
        assertNewEvents("D1", null, null);

        sendMarketEvent("D2", 2, 1000);
        assertNewEvents("D2", null, null);

        sendMarketEvent("D3", 3, 3000);
        assertNewEvents("D3", "D1", null);

        sendMarketEvent("D4", 4, 4000);
        assertNewEvents("D4", "D2", 1d);

        sendMarketEvent("D5", 5, 5000);
        assertNewEvents("D5", "D3", 2d);

        sendMarketEvent("D6", 6, 30000);
        assertNewEvents("D6", "D4", 3d);

        sendMarketEvent("D7", 7, 60000);
        assertEvent(testListener.getLastNewData()[0], "D7", "D5", 4d);
        assertEvent(testListener.getLastOldData()[0], "D1", null, null);
        testListener.reset();

        sendMarketEvent("D8", 8, 61000);
        assertEvent(testListener.getLastNewData()[0], "D8", "D6", 5d);
        assertEvent(testListener.getLastOldData()[0], "D2", null, null);
        testListener.reset();

        sendMarketEvent("D9", 9, 63000);
        assertEvent(testListener.getLastNewData()[0], "D9", "D7", 6d);
        assertEvent(testListener.getLastOldData()[0], "D3", "D1", null);
        testListener.reset();

        sendMarketEvent("D10", 10, 64000);
        assertEvent(testListener.getLastNewData()[0], "D10", "D8", 7d);
        assertEvent(testListener.getLastOldData()[0], "D4", "D2", 1d);
        testListener.reset();

        sendMarketEvent("D10", 10, 150000);
        EventBean[] oldData = testListener.getLastOldData();
        assertEquals(6, oldData.length);
        assertEvent(oldData[0], "D5", "D3", 2d);
    }

    public void testPriorTimeBatchWindow()
    {
        String viewExpr = "select irstream symbol as currSymbol, " +
                          " prior(3, symbol) as priorSymbol, " +
                          " prior(2, price) as priorPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:time_batch(1 min) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("priorSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("priorPrice"));

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
        assertEvent(testListener.getLastNewData()[0], "C", null, 1d);
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

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("priorSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("priorPrice"));

        sendMarketEvent("A", 1);
        assertNewEvents("A", null, null);

        sendMarketEvent("B", 2);
        assertNewEvents("B", null, null);

        sendMarketEvent("C", 3);
        assertNewEvents("C", null, 1d);

        sendMarketEvent("D", 4);
        assertNewEvents("D", "A", 2d);

        sendMarketEvent("E", 5);
        assertNewEvents("E", "B", 3d);
    }

    public void testLongRunningSingle()
    {
        String viewExpr = "select symbol as currSymbol, " +
                          " prior(3, symbol) as prior0Symbol " +
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort(symbol, false, 3)";

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        Random random = new Random();
        // 200000 is a better number for a memory test, however for short unit tests this is 2000
        for (int i = 0; i < 2000; i++)
        {
            if (i % 10000 == 0)
            {
                //System.out.println(i);
            }

            sendMarketEvent(Integer.toString(random.nextInt()), 4);

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

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        Random random = new Random();
        // 200000 is a better number for a memory test, however for short unit tests this is 2000
        for (int i = 0; i < 2000; i++)
        {
            if (i % 10000 == 0)
            {
                //System.out.println(i);
            }

            sendMarketEvent(Integer.toString(random.nextInt()), 4);

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
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort(symbol, false, 3)";

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        Random random = new Random();
        // 200000 is a better number for a memory test, however for short unit tests this is 2000
        for (int i = 0; i < 2000; i++)
        {
            if (i % 10000 == 0)
            {
                //System.out.println(i);
            }

            sendMarketEvent(Integer.toString(random.nextInt()), 4);

            if (i % 1000 == 0)
            {
                testListener.reset();
            }
        }
    }

    public void testPriorLengthWindow()
    {
        String viewExpr =   "select irstream symbol as currSymbol, " +
                            "prior(0, symbol) as prior0Symbol, " +
                            "prior(1, symbol) as prior1Symbol, " +
                            "prior(2, symbol) as prior2Symbol, " +
                            "prior(3, symbol) as prior3Symbol, " +
                            "prior(0, price) as prior0Price, " +
                            "prior(1, price) as prior1Price, " +
                            "prior(2, price) as prior2Price, " +
                            "prior(3, price) as prior3Price " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length(3) ";

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("prior0Symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("prior0Price"));

        sendMarketEvent("A", 1);
        assertNewEvents("A", "A", 1d, null, null, null, null, null, null);
        sendMarketEvent("B", 2);
        assertNewEvents("B", "B", 2d, "A", 1d, null, null, null, null);
        sendMarketEvent("C", 3);
        assertNewEvents("C", "C", 3d, "B", 2d, "A", 1d, null, null);

        sendMarketEvent("D", 4);
        EventBean newEvent = testListener.getLastNewData()[0];
        EventBean oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "D", "D", 4d, "C", 3d, "B", 2d, "A", 1d);
        assertEventProps(oldEvent, "A", "A", 1d, null, null, null, null, null, null);

        sendMarketEvent("E", 5);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "E", "E", 5d, "D", 4d, "C", 3d, "B", 2d);
        assertEventProps(oldEvent, "B", "B", 2d, "A", 1d, null, null, null, null);

        sendMarketEvent("F", 6);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "F", "F", 6d, "E", 5d, "D", 4d, "C", 3d);
        assertEventProps(oldEvent, "C", "C", 3d, "B", 2d, "A", 1d, null, null);

        sendMarketEvent("G", 7);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "G", "G", 7d, "F", 6d, "E", 5d, "D", 4d);
        assertEventProps(oldEvent, "D", "D", 4d, "C", 3d, "B", 2d, "A", 1d);

        sendMarketEvent("G", 8);
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(oldEvent, "E", "E", 5d, "D", 4d, "C", 3d, "B", 2d);
    }

    public void testPriorLengthWindowWhere()
    {
        String viewExpr =   "select prior(2, symbol) as currSymbol " +
                            "from " + SupportMarketDataBean.class.getName() + ".win:length(1) " +
                            "where prior(2, price) > 100";

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        sendMarketEvent("A", 1);
        sendMarketEvent("B", 130);
        sendMarketEvent("C", 10);
        assertFalse(testListener.isInvoked());
        sendMarketEvent("D", 5);
        assertEquals("B", testListener.assertOneGetNewAndReset().get("currSymbol"));
    }

    public void testPriorSortWindow()
    {
        String viewExpr = "select irstream symbol as currSymbol, " +
                          " prior(0, symbol) as prior0Symbol, " +
                          " prior(1, symbol) as prior1Symbol, " +
                          " prior(2, symbol) as prior2Symbol, " +
                          " prior(3, symbol) as prior3Symbol, " +
                          " prior(0, price) as prior0Price, " +
                          " prior(1, price) as prior1Price, " +
                          " prior(2, price) as prior2Price, " +
                          " prior(3, price) as prior3Price " +
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort(symbol, false, 3)";
        tryPriorSortWindow(viewExpr);

        viewExpr = "select irstream symbol as currSymbol, " +
                          " prior(3, symbol) as prior3Symbol, " +
                          " prior(1, symbol) as prior1Symbol, " +
                          " prior(2, symbol) as prior2Symbol, " +
                          " prior(0, symbol) as prior0Symbol, " +
                          " prior(2, price) as prior2Price, " +
                          " prior(1, price) as prior1Price, " +
                          " prior(0, price) as prior0Price, " +
                          " prior(3, price) as prior3Price " +
                          "from " + SupportMarketDataBean.class.getName() + ".ext:sort(symbol, false, 3)";
        tryPriorSortWindow(viewExpr);
    }

    public void testPreviousTimeBatchWindowJoin()
    {
        String viewExpr = "select string as currSymbol, " +
                          "prior(2, symbol) as priorSymbol, " +
                          "prior(1, price) as priorPrice " +
                          "from " + SupportBean.class.getName() + ", " +
                          SupportMarketDataBean.class.getName() + ".win:time_batch(1 min)";

        EPStatement selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("priorSymbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("priorPrice"));

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
        assertEvent(testListener.getLastNewData()[0], "X1", "A", 2d);
        assertEvent(testListener.getLastNewData()[1], "X1", "B", 11d);
        assertEvent(testListener.getLastNewData()[2], "X1", "C1", 12d);
    }

    private void tryPriorSortWindow(String viewExpr)
    {
        EPStatement statement = epService.getEPAdministrator().createEPL(viewExpr);
        statement.addListener(testListener);

        sendMarketEvent("COX", 30);
        assertNewEvents("COX", "COX", 30d, null, null, null, null, null, null);

        sendMarketEvent("IBM", 45);
        assertNewEvents("IBM", "IBM", 45d, "COX", 30d, null, null, null, null);

        sendMarketEvent("MSFT", 33);
        assertNewEvents("MSFT", "MSFT", 33d, "IBM", 45d, "COX", 30d, null, null);

        sendMarketEvent("XXX", 55);
        EventBean newEvent = testListener.getLastNewData()[0];
        EventBean oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "XXX", "XXX", 55d, "MSFT", 33d, "IBM", 45d, "COX", 30d);
        assertEventProps(oldEvent, "XXX", "XXX", 55d, "MSFT", 33d, "IBM", 45d, "COX", 30d);

        sendMarketEvent("BOO", 20);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "BOO", "BOO", 20d, "XXX", 55d, "MSFT", 33d, "IBM", 45d);
        assertEventProps(oldEvent, "MSFT", "MSFT", 33d, "IBM", 45d, "COX", 30d, null, null);

        sendMarketEvent("DOR", 1);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "DOR", "DOR", 1d, "BOO", 20d, "XXX", 55d, "MSFT", 33d);
        assertEventProps(oldEvent, "IBM", "IBM", 45d, "COX", 30d, null, null, null, null);

        sendMarketEvent("AAA", 2);
        newEvent = testListener.getLastNewData()[0];
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(newEvent, "AAA", "AAA", 2d, "DOR", 1d, "BOO", 20d, "XXX", 55d);
        assertEventProps(oldEvent, "DOR", "DOR", 1d, "BOO", 20d, "XXX", 55d, "MSFT", 33d);

        sendMarketEvent("AAB", 2);
        oldEvent = testListener.getLastOldData()[0];
        assertEventProps(oldEvent, "COX", "COX", 30d, null, null, null, null, null, null);
        testListener.reset();

        statement.stop();
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
