package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestSumWinTime extends TestCase
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

    public void testWinTimeSum()
    {
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String sumTimeExpr = "select symbol, volume, sum(price) as mySum " +
                             "from " + SupportMarketDataBean.class.getName() + ".win:time(30)";

        selectTestView = epService.getEPAdministrator().createEQL(sumTimeExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        runAssertion();
    }

    public void testWinTimeSumGroupBy()
    {
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String sumTimeUniExpr = "select symbol, volume, sum(price) as mySum " +
                             "from " + SupportMarketDataBean.class.getName() +
                             ".win:time(30) group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(sumTimeUniExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        runGroupByAssertions();
    }

    public void testWinTimeSumSingle()
    {
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String sumTimeUniExpr = "select symbol, volume, sum(price) as mySum " +
                             "from " + SupportMarketDataBean.class.getName() +
                             "(symbol = 'IBM').win:time(30)";

        selectTestView = epService.getEPAdministrator().createEQL(sumTimeUniExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        runSingleAssertion();
    }

    private void runAssertion()
    {
        assertSelectResultType();

        CurrentTimeEvent currentTime = new CurrentTimeEvent(0);
        epService.getEPRuntime().sendEvent(currentTime);

        sendEvent(SYMBOL_DELL, 10000, 51);
        assertEvents(SYMBOL_DELL, 10000, 51,false);

        sendEvent(SYMBOL_IBM, 20000, 52);
        assertEvents(SYMBOL_IBM, 20000, 103,false);

        sendEvent(SYMBOL_DELL, 40000, 45);
        assertEvents(SYMBOL_DELL, 40000, 148,false);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(35000));

        //These events are out of the window and new sums are generated

        sendEvent(SYMBOL_IBM, 30000, 70);
        assertEvents(SYMBOL_IBM, 30000,70,false);

        sendEvent(SYMBOL_DELL, 10000, 20);
        assertEvents(SYMBOL_DELL, 10000, 90,false);

    }

    private void runGroupByAssertions()
    {
        assertSelectResultType();

        CurrentTimeEvent currentTime = new CurrentTimeEvent(0);
        epService.getEPRuntime().sendEvent(currentTime);

        sendEvent(SYMBOL_DELL, 10000, 51);
        assertEvents(SYMBOL_DELL, 10000, 51,false);

        sendEvent(SYMBOL_IBM, 30000, 70);
        assertEvents(SYMBOL_IBM, 30000, 70,false);

        sendEvent(SYMBOL_DELL, 20000, 52);
        assertEvents(SYMBOL_DELL, 20000, 103,false);

        sendEvent(SYMBOL_IBM, 30000, 70);
        assertEvents(SYMBOL_IBM, 30000, 140,false);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(35000));

        //These events are out of the window and new sums are generated
        sendEvent(SYMBOL_DELL, 10000, 90);
        assertEvents(SYMBOL_DELL, 10000, 90,false);

        sendEvent(SYMBOL_IBM, 30000, 120);
        assertEvents(SYMBOL_IBM, 30000, 120,false);

        sendEvent(SYMBOL_DELL, 20000, 90);
        assertEvents(SYMBOL_DELL, 20000, 180,false);

        sendEvent(SYMBOL_IBM, 30000, 120);
        assertEvents(SYMBOL_IBM, 30000, 240,false);
     }

    private void runSingleAssertion()
    {
        assertSelectResultType();

        CurrentTimeEvent currentTime = new CurrentTimeEvent(0);
        epService.getEPRuntime().sendEvent(currentTime);

        sendEvent(SYMBOL_IBM, 20000, 52);
        assertEvents(SYMBOL_IBM, 20000, 52,false);

        sendEvent(SYMBOL_IBM, 20000, 100);
        assertEvents(SYMBOL_IBM, 20000, 152,false);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(35000));

        //These events are out of the window and new sums are generated
        sendEvent(SYMBOL_IBM, 20000, 252);
        assertEvents(SYMBOL_IBM, 20000, 252,false);

        sendEvent(SYMBOL_IBM, 20000, 100);
        assertEvents(SYMBOL_IBM, 20000, 352,false);
    }

    private void assertEvents(String symbol, long volume, double sum,boolean unique)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        if( ! unique)
         assertNull(oldData);

        assertEquals(1, newData.length);

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(volume, newData[0].get("volume"));
        assertEquals(sum, newData[0].get("mySum"));

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void assertSelectResultType()
    {
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volume"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
    }

    private void sendEvent(String symbol, long volume, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);

    }

    private static final Log log = LogFactory.getLog(TestSumWinTime.class);
}
