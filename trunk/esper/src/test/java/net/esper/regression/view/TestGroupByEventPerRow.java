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

public class TestGroupByEventPerRow extends TestCase
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
        String viewExpr = "select symbol, volume, sum(price) as mySum " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

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
        selectTestView.addListener(testListener);

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
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volume"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));

        sendEvent(SYMBOL_DELL, 10000, 51);
        assertEvents(SYMBOL_DELL, 10000, 51);

        sendEvent(SYMBOL_DELL, 20000, 52);
        assertEvents(SYMBOL_DELL, 20000, 103);

        sendEvent(SYMBOL_IBM, 30000, 70);
        assertEvents(SYMBOL_IBM, 30000, 70);

        sendEvent(SYMBOL_IBM, 10000, 20);
        assertEvents(SYMBOL_DELL, 10000, 103, SYMBOL_IBM, 10000, 90);

        sendEvent(SYMBOL_DELL, 40000, 45);
        assertEvents(SYMBOL_DELL, 20000, 52, SYMBOL_DELL, 40000, 45);
    }

    private void assertEvents(String symbol, long volume, double sum)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(volume, newData[0].get("volume"));
        assertEquals(sum, newData[0].get("mySum"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void assertEvents(String symbolOld, long volumeOld, double sumOld,
                              String symbolNew, long volumeNew, double sumNew)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbolOld, oldData[0].get("symbol"));
        assertEquals(volumeOld, oldData[0].get("volume"));
        assertEquals(sumOld, oldData[0].get("mySum"));

        assertEquals(symbolNew, newData[0].get("symbol"));
        assertEquals(volumeNew, newData[0].get("volume"));
        assertEquals(sumNew, newData[0].get("mySum"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void sendEvent(String symbol, long volume, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestGroupByEventPerRow.class);
}
