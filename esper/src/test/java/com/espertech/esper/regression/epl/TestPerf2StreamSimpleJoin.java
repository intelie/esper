package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPerf2StreamSimpleJoin extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getProvider("TestPerf2StreamSimpleJoin", configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String joinStatement = "select * from " +
                SupportMarketDataBean.class.getName() + ".win:length(1000000)," +
                SupportBean.class.getName() + ".win:length(1000000)" +
            " where symbol=string";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);
    }

    public void tearDown()
    {
        epService.initialize();
    }

    public void testPerformanceJoinNoResults()
    {
        String methodName = ".testPerformanceJoinNoResults";

        // Send events for each stream
        log.info(methodName + " Preloading events");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            sendEvent(makeMarketEvent("IBM_" + i));
            sendEvent(makeSupportEvent("CSCO_" + i));
        }
        log.info(methodName + " Done preloading");

        long endTime = System.currentTimeMillis();
        log.info(methodName + " delta=" + (endTime - startTime));

        // Stay below 50 ms
        assertTrue((endTime - startTime) < 500);
    }

    public void testJoinPerformanceStreamA()
    {
        String methodName = ".testJoinPerformanceStreamA";

        // Send 100k events
        log.info(methodName + " Preloading events");
        for (int i = 0; i < 50000; i++)
        {
            sendEvent(makeMarketEvent("IBM_" + i));
        }
        log.info(methodName + " Done preloading");

        long startTime = System.currentTimeMillis();
        sendEvent(makeSupportEvent("IBM_10"));
        long endTime = System.currentTimeMillis();
        log.info(methodName + " delta=" + (endTime - startTime));

        assertEquals(1, updateListener.getLastNewData().length);
        // Stay below 50 ms
        assertTrue((endTime - startTime) < 50);
    }

    public void testJoinPerformanceStreamB()
    {
        String methodName = ".testJoinPerformanceStreamB";

        // Send 100k events
        log.info(methodName + " Preloading events");
        for (int i = 0; i < 50000; i++)
        {
            sendEvent(makeSupportEvent("IBM_" + i));
        }
        log.info(methodName + " Done preloading");

        long startTime = System.currentTimeMillis();

        updateListener.reset();
        sendEvent(makeMarketEvent("IBM_" + 10));

        long endTime = System.currentTimeMillis();
        log.info(methodName + " delta=" + (endTime - startTime));

        assertEquals(1, updateListener.getLastNewData().length);
        // Stay below 50 ms
        assertTrue((endTime - startTime) < 25);
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }

    private Object makeSupportEvent(String id)
    {
        SupportBean bean = new SupportBean();
        bean.setString(id);
        return bean;
    }

    private Object makeMarketEvent(String id)
    {
        return new SupportMarketDataBean(id, 0, (long) 0, "");
    }

    private static final Log log = LogFactory.getLog(TestPerf2StreamSimpleJoin.class);
}
