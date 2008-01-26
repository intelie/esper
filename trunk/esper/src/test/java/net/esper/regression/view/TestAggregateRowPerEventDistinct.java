package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestAggregateRowPerEventDistinct extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

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
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String viewExpr = "select symbol, sum(distinct volume) as volSum " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) ";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volSum"));

        sendEvent(SYMBOL_DELL, 10000);
        assertEvents(SYMBOL_DELL, 10000);

        sendEvent(SYMBOL_DELL, 10000);
        assertEvents(SYMBOL_DELL, 10000);       // still 10k since summing distinct volumes

        sendEvent(SYMBOL_DELL, 20000);
        assertEvents(SYMBOL_DELL, 30000);

        sendEvent(SYMBOL_IBM, 1000);
        assertEvents(SYMBOL_DELL, 30000, SYMBOL_IBM, 31000);

        sendEvent(SYMBOL_IBM, 1000);
        assertEvents(SYMBOL_DELL, 31000, SYMBOL_IBM, 21000);

        sendEvent(SYMBOL_IBM, 1000);
        assertEvents(SYMBOL_DELL, 21000, SYMBOL_IBM, 1000);
    }

    private void assertEvents(String symbol, long volSum)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(volSum, newData[0].get("volSum"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void assertEvents(String symbolOld, long volSumOld,
                              String symbolNew, long volSumNew)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbolOld, oldData[0].get("symbol"));
        assertEquals(volSumOld, oldData[0].get("volSum"));

        assertEquals(symbolNew, newData[0].get("symbol"));
        assertEquals(volSumNew, newData[0].get("volSum"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void sendEvent(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestAggregateRowPerEventDistinct.class);
}
