package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestGroupByCount extends TestCase
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

    public void testCountOneView()
    {
        String viewExpr = "select symbol, " +
                                  "count(*) as countAll," +
                                  "count(distinct volume) as countDistVol," +
                                  "count(all volume) as countVol" +
                          " from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        runAssertion();
    }

    public void testCountJoin()
    {
        String viewExpr = "select symbol, " +
                                  "count(*) as countAll," +
                                  "count(distinct volume) as countDistVol," +
                                  "count(volume) as countVol " +
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

    private void runAssertion()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("countAll"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("countDistVol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("countVol"));

        sendEvent(SYMBOL_DELL, 50L);
        assertEvents(SYMBOL_DELL, 0L, 0L, 0L,
                SYMBOL_DELL, 1L, 1L, 1L
                );

        sendEvent(SYMBOL_DELL, null);
        assertEvents(SYMBOL_DELL, 1L, 1L, 1L,
                SYMBOL_DELL, 2L, 1L, 1L
                );

        sendEvent(SYMBOL_DELL, 25L);
        assertEvents(SYMBOL_DELL, 2L, 1L, 1L,
                SYMBOL_DELL, 3L, 2L, 2L
                );

        sendEvent(SYMBOL_DELL, 25L);
        assertEvents(SYMBOL_DELL, 3L, 2L, 2L,
                SYMBOL_DELL, 3L, 1L, 2L
                );

        sendEvent(SYMBOL_DELL, 25L);
        assertEvents(SYMBOL_DELL, 3L, 1L, 2L,
                SYMBOL_DELL, 3L, 1L, 3L
                );

        sendEvent(SYMBOL_IBM, 1L);
        sendEvent(SYMBOL_IBM, null);
        sendEvent(SYMBOL_IBM, null);
        sendEvent(SYMBOL_IBM, null);
        assertEvents(SYMBOL_IBM, 3L, 1L, 1L,
                SYMBOL_IBM, 3L, 0L, 0L
                );
    }

    private void assertEvents(String symbolOld, Long countAllOld, Long countDistVolOld, Long countVolOld,
                              String symbolNew, Long countAllNew, Long countDistVolNew, Long countVolNew)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbolOld, oldData[0].get("symbol"));
        assertEquals(countAllOld, oldData[0].get("countAll"));
        assertEquals(countDistVolOld, oldData[0].get("countDistVol"));
        assertEquals(countVolOld, oldData[0].get("countVol"));

        assertEquals(symbolNew, newData[0].get("symbol"));
        assertEquals(countAllNew, newData[0].get("countAll"));
        assertEquals(countDistVolNew, newData[0].get("countDistVol"));
        assertEquals(countVolNew, newData[0].get("countVol"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void sendEvent(String symbol, Long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestGroupByCount.class);
}
