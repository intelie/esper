package net.esper.regression.view;

import net.esper.client.*;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportPriceEvent;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestAggregateRowForAll extends TestCase
{
    private final static String JOIN_KEY = "KEY";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testSumOneView()
    {
        String viewExpr = "select sum(longBoxed) as mySum " +
                          "from " + SupportBean.class.getName() + ".win:time(10 sec)";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        runAssert();
    }

    public void testSumJoin()
    {
        String viewExpr = "select sum(longBoxed) as mySum " +
                          "from " + SupportBeanString.class.getName() + ".win:time(10) as one, " +
                                    SupportBean.class.getName() + ".win:time(10 sec) as two " +
                          "where one.string = two.string";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(JOIN_KEY));

        runAssert();
    }

    private void runAssert()
    {
        // assert select result type
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("mySum"));

        sendTimerEvent(0);
        sendEvent(10);
        assertEquals(10L, listener.getAndResetLastNewData()[0].get("mySum"));

        sendTimerEvent(5000);
        sendEvent(15);
        assertEquals(25L, listener.getAndResetLastNewData()[0].get("mySum"));

        sendTimerEvent(8000);
        sendEvent(-5);
        assertEquals(20L, listener.getAndResetLastNewData()[0].get("mySum"));
        assertNull(listener.getLastOldData());

        sendTimerEvent(10000);
        assertEquals(20L, listener.getLastOldData()[0].get("mySum"));
        assertEquals(10L, listener.getAndResetLastNewData()[0].get("mySum"));

        sendTimerEvent(15000);
        assertEquals(10L, listener.getLastOldData()[0].get("mySum"));
        assertEquals(-5L, listener.getAndResetLastNewData()[0].get("mySum"));

        sendTimerEvent(18000);
        assertEquals(-5L, listener.getLastOldData()[0].get("mySum"));
        assertNull(listener.getAndResetLastNewData()[0].get("mySum"));
    }

    public void testSumDivideZero()
    {
        String eventName = SupportBean.class.getName();
        String stmt;

        stmt = "select ((sum(floatBoxed) - floatBoxed) / (count(*) - 1)) as laggingAvg  from " + eventName + " .win:time(60) as a";
        selectTestView = epService.getEPAdministrator().createEQL(stmt);
        selectTestView.addListener(listener);
        sendEventFloat(1.1f);

        stmt = "select ((sum(intBoxed) - intBoxed) / (count(*) - 1)) as laggingAvg  from " + eventName + " .win:time(60) as a";
        selectTestView = epService.getEPAdministrator().createEQL(stmt);
        selectTestView.addListener(listener);

        try
        {
            sendEventInt(10);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }
    }

    public void testAvgPerSym() throws Throwable
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(
                "select avg(price) as avgp, sym from " + SupportPriceEvent.class.getName() + ".std:groupby('sym').win:length(2)"
        );
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportPriceEvent(1, "A"));
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("A", event.get("sym"));
        assertEquals(1.0, event.get("avgp"));

        epService.getEPRuntime().sendEvent(new SupportPriceEvent(2, "B"));
        event = listener.assertOneGetNewAndReset();
        assertEquals("B", event.get("sym"));
        assertEquals(1.5, event.get("avgp"));

        epService.getEPRuntime().sendEvent(new SupportPriceEvent(9, "A"));
        event = listener.assertOneGetNewAndReset();
        assertEquals("A", event.get("sym"));
        assertEquals((1 + 2 + 9) / 3.0, event.get("avgp"));

        epService.getEPRuntime().sendEvent(new SupportPriceEvent(18, "B"));
        event = listener.assertOneGetNewAndReset();
        assertEquals("B", event.get("sym"));
        assertEquals((1 + 2 + 9 + 18) / 4.0, event.get("avgp"));

        epService.getEPRuntime().sendEvent(new SupportPriceEvent(5, "A"));
        event = listener.getLastNewData()[0];
        assertEquals("A", event.get("sym"));
        assertEquals((2 + 9 + 18 + 5) / 4.0, event.get("avgp"));
        event = listener.getLastOldData()[0];
        assertEquals("A", event.get("sym"));
        assertEquals((1 + 2 + 9 + 18) / 4.0, event.get("avgp"));
    }

    public void testSelectStarStdGroupBy() {
        String stmtText = "select istream * from "+ SupportMarketDataBean.class.getName()
                +".std:groupby('symbol').win:length(2)";
        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        statement.addListener(listener);

        sendEvent("A", 1);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1.0, listener.getLastNewData()[0].get("price"));
        assertTrue(listener.getLastNewData()[0].getUnderlying() instanceof SupportMarketDataBean);
    }

    public void testSelectExprStdGroupBy() {
        String stmtText = "select istream price from "+ SupportMarketDataBean.class.getName()
                +".std:groupby('symbol').win:length(2)";
        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        statement.addListener(listener);

        sendEvent("A", 1);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1.0, listener.getLastNewData()[0].get("price"));
    }

    public void testSelectAvgExprStdGroupBy() {
        String stmtText = "select istream avg(price) as aprice from "+ SupportMarketDataBean.class.getName()
                +".std:groupby('symbol').win:length(2)";
        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        statement.addListener(listener);

        sendEvent("A", 1);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1.0, listener.getLastNewData()[0].get("aprice"));
        sendEvent("B", 3);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(2.0, listener.getLastNewData()[0].get("aprice"));
    }

    public void testSelectAvgStdGroupByUni() {
        String stmtText = "select istream average as aprice from "+ SupportMarketDataBean.class.getName()
                +".std:groupby('symbol').win:length(2).stat:uni('price')";
        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        statement.addListener(listener);

        sendEvent("A", 1);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(1.0, listener.getLastNewData()[0].get("aprice"));
        sendEvent("B", 3);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(3.0, listener.getLastNewData()[0].get("aprice"));
        sendEvent("A", 3);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(2.0, listener.getLastNewData()[0].get("aprice"));
        sendEvent("A", 10);
        sendEvent("A", 20);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(15.0, listener.getLastNewData()[0].get("aprice"));
    }

    public void testSelectAvgExprGroupBy() {
        String stmtText = "select istream avg(price) as aprice, symbol from "+ SupportMarketDataBean.class.getName()
                +".win:length(2) group by symbol";
        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        statement.addListener(listener);

        sendEvent("A", 1);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1.0, listener.getLastNewData()[0].get("aprice"));
        assertEquals("A", listener.getLastNewData()[0].get("symbol"));
        sendEvent("B", 3);
        //there is no A->1 as we already got it out
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(3.0, listener.getLastNewData()[0].get("aprice"));
        assertEquals("B", listener.getLastNewData()[0].get("symbol"));
        sendEvent("B", 5);
        // there is NOW a A->null entry
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(2, listener.getLastNewData().length);
        assertEquals(null, listener.getLastNewData()[0].get("aprice"));
        assertEquals(4.0, listener.getLastNewData()[1].get("aprice"));
        assertEquals("B", listener.getLastNewData()[1].get("symbol"));
        sendEvent("A", 10);
        sendEvent("A", 20);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(2, listener.getLastNewData().length);
        assertEquals(15.0, listener.getLastNewData()[0].get("aprice"));//A
        assertEquals(null, listener.getLastNewData()[1].get("aprice"));//B
    }

    private void sendEvent(String symbol, double price) {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean(symbol, price, null, null));
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

    private void sendEventInt(int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEventFloat(float floatBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setFloatBoxed(floatBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendTimerEvent(long msec)
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(msec));
    }

    private static final Log log = LogFactory.getLog(TestAggregateRowForAll.class);
}