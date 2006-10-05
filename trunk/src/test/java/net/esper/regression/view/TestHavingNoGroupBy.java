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
import junit.framework.Assert;

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

    public void testNoAggregationJoinHaving()
    {
        runNoAggregationJoin("having");
    }

    public void testNoAggregationJoinWhere()
    {
        runNoAggregationJoin("where");
    }

    private void runNoAggregationJoin(String filterClause)
    {
        String viewExpr = "select a.price as aPrice, b.price as bPrice, Math.max(a.price, b.price) - Math.min(a.price, b.price) as spread " +
                          "from " + SupportMarketDataBean.class.getName() + "(symbol='SYM1').win:length(1) as a, " +
                                    SupportMarketDataBean.class.getName() + "(symbol='SYM2').win:length(1) as b " +
                          filterClause + " Math.max(a.price, b.price) - Math.min(a.price, b.price) >= 1.4";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        sendPriceEvent("SYM1", 20);
        assertFalse(testListener.isInvoked());

        sendPriceEvent("SYM2", 10);
        assertNewSpreadEvent(20, 10, 10);

        sendPriceEvent("SYM2", 20);
        assertOldSpreadEvent(20, 10, 10);

        sendPriceEvent("SYM2", 20);
        sendPriceEvent("SYM2", 20);
        sendPriceEvent("SYM1", 20);
        assertFalse(testListener.isInvoked());

        sendPriceEvent("SYM1", 18.7);
        assertFalse(testListener.isInvoked());

        sendPriceEvent("SYM2", 20);
        assertFalse(testListener.isInvoked());

        sendPriceEvent("SYM1", 18.5);
        assertNewSpreadEvent(18.5, 20, 1.5d);

        sendPriceEvent("SYM2", 16);
        assertOldNewSpreadEvent(18.5, 20, 1.5d, 18.5, 16, 2.5d);

        sendPriceEvent("SYM1", 12);
        assertOldNewSpreadEvent(18.5, 16, 2.5d, 12, 16, 4);
    }

    private void assertOldNewSpreadEvent(double oldaprice, double oldbprice, double oldspread,
                                         double newaprice, double newbprice, double newspread)
    {
        Assert.assertEquals(1, testListener.getOldDataList().size());
        Assert.assertEquals(1, testListener.getLastOldData().length);
        Assert.assertEquals(1, testListener.getNewDataList().size());   // since event null is put into the list
        Assert.assertEquals(1, testListener.getLastNewData().length);

        EventBean oldEvent = testListener.getLastOldData()[0];
        EventBean newEvent = testListener.getLastNewData()[0];

        compareSpreadEvent(oldEvent, oldaprice, oldbprice, oldspread);
        compareSpreadEvent(newEvent, newaprice, newbprice, newspread);

        testListener.reset();
    }

    private void assertOldSpreadEvent(double aprice, double bprice, double spread)
    {
        Assert.assertEquals(1, testListener.getOldDataList().size());
        Assert.assertEquals(1, testListener.getLastOldData().length);
        Assert.assertEquals(1, testListener.getNewDataList().size());   // since event null is put into the list
        Assert.assertNull(testListener.getLastNewData());

        EventBean event = testListener.getLastOldData()[0];

        compareSpreadEvent(event, aprice, bprice, spread);
        testListener.reset();
    }

    private void assertNewSpreadEvent(double aprice, double bprice, double spread)
    {
        Assert.assertEquals(1, testListener.getNewDataList().size());
        Assert.assertEquals(1, testListener.getLastNewData().length);
        Assert.assertEquals(1, testListener.getOldDataList().size());
        Assert.assertNull(testListener.getLastOldData());

        EventBean event = testListener.getLastNewData()[0];
        compareSpreadEvent(event, aprice, bprice, spread);
        testListener.reset();
    }

    private void compareSpreadEvent(EventBean event, double aprice, double bprice, double spread)
    {
        assertEquals(aprice, event.get("aPrice"));
        assertEquals(bprice, event.get("bPrice"));
        assertEquals(spread, event.get("spread"));
    }

    private void sendPriceEvent(String symbol, double price)
    {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean(symbol, price, -1L, null));
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
