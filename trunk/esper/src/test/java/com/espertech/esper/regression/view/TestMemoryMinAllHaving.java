package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

public class TestMemoryMinAllHaving extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private Random random = new Random();

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
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

    private void sendEvent(double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean("DELL", price, -1L, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static Log log = LogFactory.getLog(TestMemoryMinAllHaving.class);
}
