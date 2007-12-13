package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.Configuration;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPerf2StreamSimpleJoinCoercion extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void tearDown()
    {
        epService.initialize();
    }

    public void testPerformanceCoercionForward()
    {
        String stmt = "select A.longBoxed as value from " +
                SupportBean.class.getName() + "(string='A').win:length(1000000) as A," +
                SupportBean.class.getName() + "(string='B').win:length(1000000) as B" +
            " where A.longBoxed=B.intPrimitive";

        EPStatement statement = epService.getEPAdministrator().createEQL(stmt);
        statement.addListener(listener);

        // preload
        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(makeSupportEvent("A", 0, i));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++)
        {
            int index = 5000 + i % 1000;
            epService.getEPRuntime().sendEvent(makeSupportEvent("B", index, 0));
            assertEquals((long)index, listener.assertOneGetNewAndReset().get("value"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }

    public void testPerformanceCoercionBack()
    {
        String stmt = "select A.intPrimitive as value from " +
                SupportBean.class.getName() + "(string='A').win:length(1000000) as A," +
                SupportBean.class.getName() + "(string='B').win:length(1000000) as B" +
            " where A.intPrimitive=B.longBoxed";

        EPStatement statement = epService.getEPAdministrator().createEQL(stmt);
        statement.addListener(listener);

        // preload
        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(makeSupportEvent("A", i, 0));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++)
        {
            int index = 5000 + i % 1000;
            epService.getEPRuntime().sendEvent(makeSupportEvent("B", 0, index));
            assertEquals(index, listener.assertOneGetNewAndReset().get("value"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }

    private Object makeSupportEvent(String string, int intPrimitive, long longBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setLongBoxed(longBoxed);
        return bean;
    }

    private static final Log log = LogFactory.getLog(TestPerf2StreamSimpleJoinCoercion.class);
}
