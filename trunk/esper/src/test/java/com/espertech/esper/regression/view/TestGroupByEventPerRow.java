package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestGroupByEventPerRow extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testAggregationOverGroupedProps()
    {
        // test for ESPER-185
        String fields[] = "volume,symbol,price,mycount".split(",");
        String viewExpr = "select volume,symbol,price,count(price) as mycount " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by symbol, price";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        sendEvent(SYMBOL_DELL, 1000, 10);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1000L, "DELL", 10.0, 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1000L, "DELL", 10.0, 1L}});

        sendEvent(SYMBOL_DELL, 900, 11);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {900L, "DELL", 11.0, 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1000L, "DELL", 10.0, 1L}, {900L, "DELL", 11.0, 1L}});
        listener.reset();

        sendEvent(SYMBOL_DELL, 1500, 10);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {1500L, "DELL", 10.0, 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1000L, "DELL", 10.0, 2L}, {900L, "DELL", 11.0, 1L}, {1500L, "DELL", 10.0, 2L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 500, 5);
        assertEquals(1, listener.getNewDataList().size());
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {500L, "IBM", 5.0, 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1000L, "DELL", 10.0, 2L}, {900L, "DELL", 11.0, 1L}, {1500L, "DELL", 10.0, 2L}, {500L, "IBM", 5.0, 1L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 600, 5);
        assertEquals(1, listener.getLastNewData().length);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {600L, "IBM", 5.0, 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1000L, "DELL", 10.0, 2L}, {900L, "DELL", 11.0, 1L}, {1500L, "DELL", 10.0, 2L}, {500L, "IBM", 5.0, 2L}, {600L, "IBM", 5.0, 2L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 500, 5);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {500L, "IBM", 5.0, 3L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {1000L, "DELL", 10.0, 2L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{900L, "DELL", 11.0, 1L}, {1500L, "DELL", 10.0, 1L}, {500L, "IBM", 5.0, 3L}, {600L, "IBM", 5.0, 3L}, {500L, "IBM", 5.0, 3L}});
        listener.reset();

        sendEvent(SYMBOL_IBM, 600, 5);
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {600L, "IBM", 5.0, 4L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {900L, "DELL", 11.0, 1L});
        ArrayAssertionUtil.assertEqualsExactOrder(selectTestView.iterator(), fields, new Object[][] {{1500L, "DELL", 10.0, 1L}, {500L, "IBM", 5.0, 4L}, {600L, "IBM", 5.0, 4L}, {500L, "IBM", 5.0, 4L}, {600L, "IBM", 5.0, 4L}});
        listener.reset();
    }

    public void testSumOneView()
    {
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String viewExpr = "select symbol, volume, sum(price) as mySum " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        runAssertion();
    }

    public void testSumJoin()
    {
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String viewExpr = "select symbol, volume, sum(price) as mySum " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(3) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "  and one.string = two.symbol " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));

        runAssertion();
    }

    public void testInsertInto()
    {
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        String eventType = SupportMarketDataBean.class.getName();
        String stmt = " select symbol as symbol, avg(price) as average, sum(volume) as sumation from " + eventType + ".win:length(3000)";
        EPStatement statement = epService.getEPAdministrator().createEQL(stmt);
        statement.addListener(listenerOne);

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 10D, 20000L, null));
        EventBean eventBean = listenerOne.getLastNewData()[0];
        assertEquals("IBM", eventBean.get("symbol"));
        assertEquals(10d, eventBean.get("average"));
        assertEquals(20000L, eventBean.get("sumation"));

        // create insert into statements
        stmt =  "insert into StockAverages select symbol as symbol, avg(price) as average, sum(volume) as sumation " +
                    "from " + eventType + ".win:length(3000)";
        statement = epService.getEPAdministrator().createEQL(stmt);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        statement.addListener(listenerTwo);

        stmt = " select * from StockAverages";
        statement = epService.getEPAdministrator().createEQL(stmt);
        SupportUpdateListener listenerThree = new SupportUpdateListener();
        statement.addListener(listenerThree);

        // send event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 20D, 40000L, null));
        eventBean = listenerOne.getLastNewData()[0];
        assertEquals("IBM", eventBean.get("symbol"));
        assertEquals(15d, eventBean.get("average"));
        assertEquals(60000L, eventBean.get("sumation"));

        assertEquals(1, listenerThree.getNewDataList().size());
        assertEquals(1, listenerThree.getLastNewData().length);
        eventBean = listenerThree.getLastNewData()[0];
        assertEquals("IBM", eventBean.get("symbol"));
        assertEquals(20d, eventBean.get("average"));
        assertEquals(40000L, eventBean.get("sumation"));
    }

    private void runAssertion()
    {
        String[] fields = new String[] {"symbol", "volume", "mySum"};
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, null);

        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volume"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));

        sendEvent(SYMBOL_DELL, 10000, 51);
        assertEvents(SYMBOL_DELL, 10000, 51);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {
                {"DELL", 10000L, 51d}});

        sendEvent(SYMBOL_DELL, 20000, 52);
        assertEvents(SYMBOL_DELL, 20000, 103);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {
                {"DELL", 10000L, 103d}, {"DELL", 20000L, 103d}});

        sendEvent(SYMBOL_IBM, 30000, 70);
        assertEvents(SYMBOL_IBM, 30000, 70);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {
                {"DELL", 10000L, 103d}, {"DELL", 20000L, 103d}, {"IBM", 30000L, 70d}});

        sendEvent(SYMBOL_IBM, 10000, 20);
        assertEvents(SYMBOL_DELL, 10000, 103, SYMBOL_IBM, 10000, 90);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {
                {"DELL", 20000L, 52d}, {"IBM", 30000L, 90d}, {"IBM", 10000L, 90d}});

        sendEvent(SYMBOL_DELL, 40000, 45);
        assertEvents(SYMBOL_DELL, 20000, 52, SYMBOL_DELL, 40000, 45);
        ArrayAssertionUtil.assertEqualsAnyOrder(selectTestView.iterator(), fields, new Object[][] {
                {"IBM", 10000L, 90d}, {"IBM", 30000L, 90d}, {"DELL", 40000L, 45d}});
    }

    private void assertEvents(String symbol, long volume, double sum)
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(volume, newData[0].get("volume"));
        assertEquals(sum, newData[0].get("mySum"));

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private void assertEvents(String symbolOld, long volumeOld, double sumOld,
                              String symbolNew, long volumeNew, double sumNew)
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbolOld, oldData[0].get("symbol"));
        assertEquals(volumeOld, oldData[0].get("volume"));
        assertEquals(sumOld, oldData[0].get("mySum"));

        assertEquals(symbolNew, newData[0].get("symbol"));
        assertEquals(volumeNew, newData[0].get("volume"));
        assertEquals(sumNew, newData[0].get("mySum"));

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private void sendEvent(String symbol, long volume, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestGroupByEventPerRow.class);
}
