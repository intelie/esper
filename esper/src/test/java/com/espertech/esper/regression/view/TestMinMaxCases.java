package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

public class TestMinMaxCases extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private Random random = new Random();

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("S0", SupportBean_S0.class);
    }

    public void testMinMaxNoDataWindowSubquery() {

        String[] fields = "maxi,mini,max0,min0".split(",");
        String epl = "select max(intPrimitive) as maxi, min(intPrimitive) as mini," +
                     "(select max(id) from S0.std:lastevent()) as max0, (select min(id) from S0.std:lastevent()) as min0" +
                     " from SupportBean";
        epService.getEPAdministrator().createEPL(epl).addListener(listener);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3, 3, null, null});
        
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 4));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4, 3, null, null});

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 4));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4, 3, 2, 2});

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 5));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {5, 3, 1, 1});

        /**
         * Comment out here for sending many more events.
         *
        for (int i = 0; i < 10000000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean(null, i));
            if (i % 10000 == 0) {
                System.out.println("Sent " + i + " events");
            }
        }
         */
    }

    public void testMemoryMinHaving() throws Exception
    {
        String statementText = "select price, min(price) as minPrice " +
                "from " + SupportMarketDataBean.class.getName() + ".win:time(30)" +
                "having price >= min(price) * (1.02)";

        EPStatement testView = epService.getEPAdministrator().createEPL(statementText);
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

    private static Log log = LogFactory.getLog(TestMinMaxCases.class);
}
