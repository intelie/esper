package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBeanInt;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportJoinMethods;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Random;

public class TestPerfHistoricalMethodJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAliasSimpleName(SupportBeanInt.class);

        ConfigurationMethodRef configMethod = new ConfigurationMethodRef();
        configMethod.setLRUCache(10);
        config.addMethodRef(SupportJoinMethods.class.getName(), configMethod);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    // TODO
    // Multiple indexes if a historical is accessed from 2 sides
    // Multiple fields part of the index
    // 
    public void test1Stream2HistPerformance()
    {
        String expression;

        expression = "select s0.id as id, h0.val as valh0, h1.val as valh1 " +
                   "from SupportBeanInt.std:lastevent() as s0, " +
                   "method:SupportJoinMethods.fetchVal('H0', 100) as h0, " +
                   "method:SupportJoinMethods.fetchVal('H1', 100) as h1 " +
                   "where h0.index = p00 and h1.index = p00";

        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "id,valh0,valh1".split(",");
        Random random = new Random();

        long start = System.currentTimeMillis();
        for (int i = 1; i < 10000; i++)
        {
            int num = random.nextInt(98) + 1;
            sendBeanInt("E1", num);

            Object[][] result = new Object[][] {{"E1", "H0" + num, "H1" + num}};
            ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, result);
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("Delta to large, at " + delta + " msec", delta < 1000);
    }

    private void sendBeanInt(String id, int p00, int p01, int p02, int p03)
    {
        epService.getEPRuntime().sendEvent(new SupportBeanInt(id, p00, p01, p02, p03, -1, -1));
    }

    private void sendBeanInt(String id, int p00, int p01, int p02)
    {
        sendBeanInt(id, p00, p01, p02, -1);
    }

    private void sendBeanInt(String id, int p00, int p01)
    {
        sendBeanInt(id, p00, p01, -1, -1);
    }

    private void sendBeanInt(String id, int p00)
    {
        sendBeanInt(id, p00, -1, -1, -1);
    }
}
