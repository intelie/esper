package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPRuntime;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestMemoryMinAllHaving extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private Random random = new Random();

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testMemory() throws Exception
    {
        String statementText = "select price, min(price) as minPrice " +
                "from " + SupportMarketDataBean.class.getName() + ".win:time(30)" +
                "having price >= min(price) * (1.02)";

        EPStatement testView = epService.getEPAdministrator().createEQL(statementText);
        testView.addListener(listener);

        sendClockingInternal();

        //sendClockingExternal();
    }

    private void sendClockingInternal() throws Exception
    {
        // Change to perform a long-running tests, each loop is 1 second
        final int LOOP_COUNT = 2;
        int loopCount = 0;

        while(true)
        {
            log.info("Sending batch " + loopCount);

            // send events
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 5000; i++)
            {
                double price = 50 + 49 * random.nextInt(100) / 100.0;
                sendEvent(price);
            }
            long endTime = System.currentTimeMillis();

            // sleep remainder of 1 second
            long delta = startTime - endTime;
            if (delta < 950)
            {
                Thread.sleep(950 - delta);
            }

            listener.reset();
            loopCount++;
            if (loopCount > LOOP_COUNT)
            {
                break;
            }
        }
    }

    private void sendClockingExternal()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        long memoryBefore = Runtime.getRuntime().freeMemory();
        for (int i = 0; i < 1000; i++)
        {
            sendEvents(i);

            //Runtime.getRuntime().gc();
            long memoryAfter = Runtime.getRuntime().freeMemory();

            log.info("Memory before=" + memoryBefore +
                        " after=" + memoryAfter +
                        " delta=" + (memoryAfter - memoryBefore));
        }
    }

    private void sendEvents(int loop)
    {
        long startTime = loop * 31000;
        long endTime = loop * 31000 + 30500;
        log.info("Sending batch " + loop + " startTime=" + startTime + " endTime=" + endTime);
        sendTimer(startTime);

        for (int i = 0; i < 100000; i++)
        {
            double price = 50 + 49 * random.nextInt(20) / 100.0;
            sendEvent(price);
        }

        sendTimer(endTime);
        listener.reset();
    }

    private void sendEvent(double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean("DELL", price, -1L, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendTimer(long time)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private static Log log = LogFactory.getLog(TestMemoryMinAllHaving.class);
}
