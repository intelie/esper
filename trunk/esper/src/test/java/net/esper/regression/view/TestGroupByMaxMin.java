package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPRuntime;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestGroupByMaxMin extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testMinMaxView()
    {
        String viewExpr = "select symbol, " +
                                  "min(all volume) as minVol," +
                                  "max(all volume) as maxVol," +
                                  "min(distinct volume) as minDistVol," +
                                  "max(distinct volume) as maxDistVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        runAssertion();
    }

    public void testMinMaxJoin()
    {
        String viewExpr = "select symbol, " +
                                  "min(volume) as minVol," +
                                  "max(volume) as maxVol," +
                                  "min(distinct volume) as minDistVol," +
                                  "max(distinct volume) as maxDistVol" +
                          " from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(3) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "  and one.string = two.symbol " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));

        runAssertion();
    }

    public void testMinNoGroupHaving()
    {
        String stmtText = "select symbol from " + SupportMarketDataBean.class.getName() + ".win:time(5 sec) " +
                          "having volume > min(volume) * 1.3";

        selectTestView = epService.getEPAdministrator().createEQL(stmtText);
        selectTestView.addListener(testListener);

        sendEvent("DELL", 100L);
        sendEvent("DELL", 105L);
        sendEvent("DELL", 100L);
        assertFalse(testListener.isInvoked());

        sendEvent("DELL", 131L);
        assertEquals("DELL", testListener.assertOneGetNewAndReset().get("symbol"));

        sendEvent("DELL", 132L);
        assertEquals("DELL", testListener.assertOneGetNewAndReset().get("symbol"));

        sendEvent("DELL", 129L);
        assertFalse(testListener.isInvoked());
    }

    public void testMinNoGroupSelectHaving()
    {
        String stmtText = "select symbol, min(volume) as mymin from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "having volume > min(volume) * 1.3";

        selectTestView = epService.getEPAdministrator().createEQL(stmtText);
        selectTestView.addListener(testListener);

        sendEvent("DELL", 100L);
        sendEvent("DELL", 105L);
        sendEvent("DELL", 100L);
        assertFalse(testListener.isInvoked());

        sendEvent("DELL", 131L);
        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals("DELL", event.get("symbol"));
        assertEquals(100L, event.get("mymin"));

        sendEvent("DELL", 132L);
        event = testListener.assertOneGetNewAndReset();
        assertEquals("DELL", event.get("symbol"));
        assertEquals(100L, event.get("mymin"));

        sendEvent("DELL", 129L);
        sendEvent("DELL", 125L);
        sendEvent("DELL", 125L);
        assertFalse(testListener.isInvoked());

        sendEvent("DELL", 170L);
        event = testListener.assertOneGetNewAndReset();
        assertEquals("DELL", event.get("symbol"));
        assertEquals(125L, event.get("mymin"));
    }

    private void runAssertion()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("minVol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("maxVol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("minDistVol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("maxDistVol"));

        sendEvent(SYMBOL_DELL, 50L);
        assertEvents(SYMBOL_DELL, null, null, null, null,
                SYMBOL_DELL, 50L, 50L, 50L, 50L
                );

        sendEvent(SYMBOL_DELL, 30L);
        assertEvents(SYMBOL_DELL, 50L, 50L, 50L, 50L,
                SYMBOL_DELL, 30L, 50L, 30L, 50L
                );

        sendEvent(SYMBOL_DELL, 30L);
        assertEvents(SYMBOL_DELL, 30L, 50L, 30L, 50L,
                SYMBOL_DELL, 30L, 50L, 30L, 50L
                );

        sendEvent(SYMBOL_DELL, 90L);
        assertEvents(SYMBOL_DELL, 30L, 50L, 30L, 50L,
                SYMBOL_DELL, 30L, 90L, 30L, 90L
                );

        sendEvent(SYMBOL_DELL, 100L);
        assertEvents(SYMBOL_DELL, 30L, 90L, 30L, 90L,
                SYMBOL_DELL, 30L, 100L, 30L, 100L
                );

        sendEvent(SYMBOL_IBM, 20L);
        sendEvent(SYMBOL_IBM, 5L);
        sendEvent(SYMBOL_IBM, 15L);
        sendEvent(SYMBOL_IBM, 18L);
        assertEvents(SYMBOL_IBM, 5L, 20L, 5L, 20L,
                SYMBOL_IBM, 5L, 18L, 5L, 18L
                );

        sendEvent(SYMBOL_IBM, null);
        assertEvents(SYMBOL_IBM, 5L, 18L, 5L, 18L,
                SYMBOL_IBM, 15L, 18L, 15L, 18L
                );

        sendEvent(SYMBOL_IBM, null);
        assertEvents(SYMBOL_IBM, 15L, 18L, 15L, 18L,
                SYMBOL_IBM, 18L, 18L, 18L, 18L
                );

        sendEvent(SYMBOL_IBM, null);
        assertEvents(SYMBOL_IBM, 18L, 18L, 18L, 18L,
                SYMBOL_IBM, null, null, null, null
                );
    }

    private void assertEvents(String symbolOld, Long minVolOld, Long maxVolOld, Long minDistVolOld, Long maxDistVolOld,
                              String symbolNew, Long minVolNew, Long maxVolNew, Long minDistVolNew, Long maxDistVolNew)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbolOld, oldData[0].get("symbol"));
        assertEquals(minVolOld, oldData[0].get("minVol"));
        assertEquals(maxVolOld, oldData[0].get("maxVol"));
        assertEquals(minDistVolOld, oldData[0].get("minDistVol"));
        assertEquals(maxDistVolOld, oldData[0].get("maxDistVol"));

        assertEquals(symbolNew, newData[0].get("symbol"));
        assertEquals(minVolNew, newData[0].get("minVol"));
        assertEquals(maxVolNew, newData[0].get("maxVol"));
        assertEquals(minDistVolNew, newData[0].get("minDistVol"));
        assertEquals(maxDistVolNew, newData[0].get("maxDistVol"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void sendEvent(String symbol, Long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private static final Log log = LogFactory.getLog(TestGroupByMaxMin.class);
}
