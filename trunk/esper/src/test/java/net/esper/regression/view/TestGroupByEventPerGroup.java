package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.collection.UniformPair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestGroupByEventPerGroup extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testSumOneView()
    {
        String viewExpr = "select symbol," +
                                 "sum(price) as mySum," +
                                 "avg(price) as myAvg " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        runAssertion();
    }

    public void testSumJoin()
    {
        String viewExpr = "select symbol," +
                                 "sum(price) as mySum," +
                                 "avg(price) as myAvg " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(3) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "       and one.string = two.symbol " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));
        epService.getEPRuntime().sendEvent(new SupportBeanString("AAA"));

        runAssertion();
    }

    public void testUniqueInBatch()
    {
        String stmtOne = "insert into MyStream select symbol, price from " +
                SupportMarketDataBean.class.getName() + ".win:time_batch(1 sec)";
        epService.getEPAdministrator().createEQL(stmtOne);
        sendTimer(0);

        String viewExpr = "select symbol " +
                          "from MyStream.win:time_batch(1 sec).std:unique('symbol') " +
                          "group by symbol";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(listener);

        sendEvent("IBM", 100);
        sendEvent("IBM", 101);
        sendEvent("IBM", 102);
        sendTimer(1000);
        assertFalse(listener.isInvoked());
        
        sendTimer(2000);
        UniformPair<EventBean[]> received = listener.getDataListsFlattened();
        assertEquals("IBM", received.getFirst()[0].get("symbol"));
    }

    private void runAssertion()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("myAvg"));

        sendEvent(SYMBOL_DELL, 10);
        assertEvents(SYMBOL_DELL,
                null, null,
                10d, 10d);

        sendEvent(SYMBOL_DELL, 20);
        assertEvents(SYMBOL_DELL,
                10d, 10d,
                30d, 15d);

        sendEvent(SYMBOL_DELL, 100);
        assertEvents(SYMBOL_DELL,
                30d, 15d,
                130d, 130d/3d);

        sendEvent(SYMBOL_DELL, 50);
        assertEvents(SYMBOL_DELL,
                130d, 130/3d,
                170d, 170/3d);    // 20 + 100 + 50

        sendEvent(SYMBOL_DELL, 5);
        assertEvents(SYMBOL_DELL,
                170d, 170/3d,
                155d, 155/3d);    // 100 + 50 + 5

        sendEvent("AAA", 1000);
        assertEvents(SYMBOL_DELL,
                155d, 155d/3,
                55d, 55d/2);    // 50 + 5

        sendEvent(SYMBOL_IBM, 70);
        assertEvents(SYMBOL_DELL,
                55d, 55/2d,
                5, 5,
                SYMBOL_IBM,
                null, null,
                70, 70);    // Dell:5

        sendEvent("AAA", 2000);
        assertEvents(SYMBOL_DELL,
                5d, 5d,
                null, null);

        sendEvent("AAA", 3000);
        assertFalse(listener.isInvoked());

        sendEvent("AAA", 4000);
        assertEvents(SYMBOL_IBM,
                70d, 70d,
                null, null);
    }

    private void assertEvents(String symbol,
                              Double oldSum, Double oldAvg,
                              Double newSum, Double newAvg)
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertEquals(1, oldData.length);
        assertEquals(1, newData.length);

        assertEquals(symbol, oldData[0].get("symbol"));
        assertEquals(oldSum, oldData[0].get("mySum"));
        assertEquals(oldAvg, oldData[0].get("myAvg"));

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(newSum, newData[0].get("mySum"));
        assertEquals("newData myAvg wrong", newAvg, newData[0].get("myAvg"));

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private void assertEvents(String symbolOne,
                              Double oldSumOne, Double oldAvgOne,
                              double newSumOne, double newAvgOne,
                              String symbolTwo,
                              Double oldSumTwo, Double oldAvgTwo,
                              double newSumTwo, double newAvgTwo)
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertEquals(2, oldData.length);
        assertEquals(2, newData.length);

        int indexOne = 0;
        int indexTwo = 1;
        if (oldData[0].get("symbol").equals(symbolTwo))
        {
            indexTwo = 0;
            indexOne = 1;
        }
        assertEquals(newSumOne, newData[indexOne].get("mySum"));
        assertEquals(newSumTwo, newData[indexTwo].get("mySum"));
        assertEquals(oldSumOne, oldData[indexOne].get("mySum"));
        assertEquals(oldSumTwo, oldData[indexTwo].get("mySum"));

        assertEquals(newAvgOne, newData[indexOne].get("myAvg"));
        assertEquals(newAvgTwo, newData[indexTwo].get("myAvg"));
        assertEquals(oldAvgOne, oldData[indexOne].get("myAvg"));
        assertEquals(oldAvgTwo, oldData[indexTwo].get("myAvg"));

        listener.reset();
        assertFalse(listener.isInvoked());
    }

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private static final Log log = LogFactory.getLog(TestGroupByEventPerGroup.class);
}
