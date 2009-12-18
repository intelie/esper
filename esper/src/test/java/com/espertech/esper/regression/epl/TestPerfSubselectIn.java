package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestPerfSubselectIn extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportBean.class);
        config.addEventType("S0", SupportBean_S0.class);
        config.addEventType("S1", SupportBean_S1.class);
        config.addEventType("S2", SupportBean_S2.class);
        config.addEventType("S3", SupportBean_S3.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testPerformanceWhereClauseCoercion()
    {
        String stmtText = "select intPrimitive from MyEvent(string='A') as s0 where intPrimitive in (" +
                            "select longBoxed from MyEvent(string='B').win:length(10000) where s0.intPrimitive = longBoxed)";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // preload with 10k events
        for (int i = 0; i < 10000; i++)
        {
            SupportBean bean = new SupportBean();
            bean.setString("B");
            bean.setLongBoxed((long)i);
            epService.getEPRuntime().sendEvent(bean);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            int index = 5000 + i % 1000;
            SupportBean bean = new SupportBean();
            bean.setString("A");
            bean.setIntPrimitive(index);
            epService.getEPRuntime().sendEvent(bean);
            //assertEquals(index, listener.assertOneGetNewAndReset().get("intPrimitive"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 2000);
    }

    public void testPerformanceWhereClause()
    {
        String stmtText = "select id from S0 as s0 where p00 in (" +
                            "select p10 from S1.win:length(10000) where s0.p00 = p10)";
        tryPerformanceOneCriteria(stmtText);
    }

    private void tryPerformanceOneCriteria(String stmtText)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // preload with 10k events
        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean_S1(i, Integer.toString(i)));
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            int index = 5000 + i % 1000;
            epService.getEPRuntime().sendEvent(new SupportBean_S0(index, Integer.toString(index)));
            assertEquals(index, listener.assertOneGetNewAndReset().get("id"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }
}
