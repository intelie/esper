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

public class TestHavingNoGroupBy extends TestCase
{
    private static String SYMBOL_DELL = "DELL";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testSumOneView()
    {
        String viewExpr = "select symbol, price, avg(price) as avgPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "having price < avg(price)";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        runAssertion();
    }

    public void testSumJoin()
    {
        String viewExpr = "select symbol, price, avg(price) as avgPrice " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
                          "where one.string = two.symbol " +
                          "having price < avg(price)";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));

        runAssertion();
    }

    private void runAssertion()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("price"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("avgPrice"));

        sendEvent(SYMBOL_DELL, 10);
        assertFalse(testListener.isInvoked());

        sendEvent(SYMBOL_DELL, 5);
        assertNewEvents(SYMBOL_DELL, 5d, 7.5d);

        sendEvent(SYMBOL_DELL, 15);
        assertFalse(testListener.isInvoked());

        sendEvent(SYMBOL_DELL, 8);  // avg = (10 + 5 + 15 + 8) / 4 = 38/4=9.5
        assertNewEvents(SYMBOL_DELL, 8d, 9.5d);

        sendEvent(SYMBOL_DELL, 10);  // avg = (10 + 5 + 15 + 8 + 10) / 5 = 48/5=9.5
        assertFalse(testListener.isInvoked());

        sendEvent(SYMBOL_DELL, 6);  // avg = (5 + 15 + 8 + 10 + 6) / 5 = 44/5=8.8
        // no old event posted, old event falls above current avg price
        assertNewEvents(SYMBOL_DELL, 6d, 8.8d);

        sendEvent(SYMBOL_DELL, 12);  // avg = (15 + 8 + 10 + 6 + 12) / 5 = 51/5=10.2
        assertOldEvents(SYMBOL_DELL, 5d, 8.8d);
    }

    private void assertNewEvents(String symbol,
                              Double newPrice, Double newAvgPrice
                              )
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(newPrice, newData[0].get("price"));
        assertEquals(newAvgPrice, newData[0].get("avgPrice"));

        testListener.reset();
    }

    private void assertOldEvents(String symbol,
                              Double oldPrice, Double oldAvgPrice
                              )
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(newData);
        assertEquals(1, oldData.length);

        assertEquals(symbol, oldData[0].get("symbol"));
        assertEquals(oldPrice, oldData[0].get("price"));
        assertEquals(oldAvgPrice, oldData[0].get("avgPrice"));

        testListener.reset();
    }

    private void assertEvents(String symbol,
                              Double oldPrice, Double oldAvgPrice,
                              Double newPrice, Double newAvgPrice
                              )
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbol, oldData[0].get("symbol"));
        assertEquals(oldPrice, oldData[0].get("price"));
        assertEquals(oldAvgPrice, oldData[0].get("avgPrice"));

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(newPrice, newData[0].get("price"));
        assertEquals(newAvgPrice, newData[0].get("avgPrice"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestHavingNoGroupBy.class);
}
