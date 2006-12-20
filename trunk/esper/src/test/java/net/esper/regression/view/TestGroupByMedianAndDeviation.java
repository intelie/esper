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

public class TestGroupByMedianAndDeviation extends TestCase
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

    public void testSumOneView()
    {
        String viewExpr = "select symbol," +
                                 "median(all price) as myMedian," +
                                 "median(distinct price) as myDistMedian," +
                                 "stddev(all price) as myStdev," +
                                 "avedev(all price) as myAvedev " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        runAssertion();
    }

    public void testSumJoin()
    {
        String viewExpr = "select symbol," +
                                 "median(price) as myMedian," +
                                 "median(distinct price) as myDistMedian," +
                                 "stddev(price) as myStdev," +
                                 "avedev(price) as myAvedev " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "       and one.string = two.symbol " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));
        epService.getEPRuntime().sendEvent(new SupportBeanString("AAA"));

        runAssertion();
    }

    private void runAssertion()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myMedian"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myDistMedian"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myStdev"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myAvedev"));

        sendEvent(SYMBOL_DELL, 10);
        assertEvents(SYMBOL_DELL,
                null, null, null, null,
                10d, 10d, null, 0d);

        sendEvent(SYMBOL_DELL, 20);
        assertEvents(SYMBOL_DELL,
                10d, 10d, null, 0d,
                15d, 15d, 7.071067812d, 5d);

        sendEvent(SYMBOL_DELL, 20);
        assertEvents(SYMBOL_DELL,
                15d, 15d, 7.071067812d, 5d,
                20d, 15d, 5.773502692, 4.444444444444444);

        sendEvent(SYMBOL_DELL, 90);
        assertEvents(SYMBOL_DELL,
                20d, 15d, 5.773502692, 4.444444444444444,
                20d, 20d, 36.96845502d, 27.5d);

        sendEvent(SYMBOL_DELL, 5);
        assertEvents(SYMBOL_DELL,
                20d, 20d, 36.96845502d, 27.5d,
                20d, 15d, 34.71310992d, 24.4d);

        sendEvent(SYMBOL_DELL, 90);
        assertEvents(SYMBOL_DELL,
                20d, 15d, 34.71310992d, 24.4d,
                20d, 20d, 41.53311931d, 36d);

        sendEvent(SYMBOL_DELL, 30);
        assertEvents(SYMBOL_DELL,
                20d, 20d, 41.53311931d, 36d,
                30d, 25d, 40.24922359d, 34.4d);
    }

    private void assertEvents(String symbol,
                              Double oldMedian, Double oldDistMedian, Double oldStdev, Double oldAvedev,
                              Double newMedian, Double newDistMedian, Double newStdev, Double newAvedev
                              )
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbol, oldData[0].get("symbol"));
        assertEquals("oldData.myMedian wrong", oldMedian, oldData[0].get("myMedian"));
        assertEquals("oldData.myDistMedian wrong", oldDistMedian, oldData[0].get("myDistMedian"));
        assertEquals("oldData.myAvedev wrong", oldAvedev, oldData[0].get("myAvedev"));

        Double oldStdevResult = (Double) oldData[0].get("myStdev");
        if (oldStdevResult  == null)
        {
            assertNull(oldStdev);
        }
        else
        {
            assertEquals("oldData.myStdev wrong", Math.round(oldStdev * 1000), Math.round(oldStdevResult * 1000));
        }

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals("newData.myMedian wrong", newMedian, newData[0].get("myMedian"));
        assertEquals("newData.myDistMedian wrong", newDistMedian, newData[0].get("myDistMedian"));
        assertEquals("newData.myAvedev wrong", newAvedev, newData[0].get("myAvedev"));

        Double newStdevResult = (Double) newData[0].get("myStdev");
        if (newStdevResult == null)
        {
            assertNull(newStdev);
        }
        else
        {
            assertEquals("newData.myStdev wrong", Math.round(newStdev * 1000), Math.round(newStdevResult * 1000));
        }

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
        epService.getEPRuntime().sendEvent(bean);
    }



    private static final Log log = LogFactory.getLog(TestGroupByMedianAndDeviation.class);
}
