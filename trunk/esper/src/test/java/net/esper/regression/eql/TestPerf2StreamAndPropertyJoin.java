package net.esper.regression.eql;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.SupportUpdateListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestPerf2StreamAndPropertyJoin extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testPerf2Properties()
    {
        String methodName = ".testPerformanceJoinNoResults";

        String joinStatement = "select * from " +
                SupportMarketDataBean.class.getName() + ".win:length(1000000)," +
                SupportBean.class.getName() + ".win:length(1000000)" +
            " where symbol=string and volume=longBoxed";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);

        // Send events for each stream
        log.info(methodName + " Preloading events");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            sendEvent(makeMarketEvent("IBM_" + i, 1));
            sendEvent(makeSupportEvent("CSCO_" + i, 2));
        }
        log.info(methodName + " Done preloading");

        long endTime = System.currentTimeMillis();
        log.info(methodName + " delta=" + (endTime - startTime));

        // Stay at 250, belwo 500ms
        assertTrue((endTime - startTime) < 500);
    }

    public void testPerf3Properties()
    {
        String methodName = ".testPerformanceJoinNoResults";

        String joinStatement = "select * from " +
                SupportMarketDataBean.class.getName() + "().win:length(1000000)," +
                SupportBean.class.getName() + ".win:length(1000000)" +
            " where symbol=string and volume=longBoxed and doublePrimitive=price";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);

        // Send events for each stream
        log.info(methodName + " Preloading events");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            sendEvent(makeMarketEvent("IBM_" + i, 1));
            sendEvent(makeSupportEvent("CSCO_" + i, 2));
        }
        log.info(methodName + " Done preloading");

        long endTime = System.currentTimeMillis();
        log.info(methodName + " delta=" + (endTime - startTime));

        // Stay at 250, belwo 500ms
        assertTrue((endTime - startTime) < 500);
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }

    private Object makeSupportEvent(String id, long longBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(id);
        bean.setLongBoxed(longBoxed);
        return bean;
    }

    private Object makeMarketEvent(String id, long volume)
    {
        return new SupportMarketDataBean(id, 0, (long) volume, "");
    }

    private static final Log log = LogFactory.getLog(TestPerf2StreamAndPropertyJoin.class);
}