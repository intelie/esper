package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestVariablesTimer extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerSet;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(true);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
        listenerSet = new SupportUpdateListener();
    }

    public void testTimestamp() throws Exception
    {
        epService.getEPAdministrator().getConfiguration().addVariable("var1", long.class, "12");
        epService.getEPAdministrator().getConfiguration().addVariable("var2", Long.class, "2");
        epService.getEPAdministrator().getConfiguration().addVariable("var3", Long.class, null);

        long startTime = System.currentTimeMillis();        
        String stmtTextSet = "on pattern [every timer:interval(100 milliseconds)] set var1 = current_timestamp, var2 = var1 + 1, var3 = var1 + var2";
        EPStatement stmtSet = epService.getEPAdministrator().createEPL(stmtTextSet);
        stmtSet.addListener(listenerSet);

        Thread.sleep(1000);
        stmtSet.destroy();

        EventBean[] received = listenerSet.getNewDataListFlattened();
        assertTrue("received : " + received.length, received.length >= 5);

        for (int i = 0; i < received.length; i++)
        {
            long var1 = (Long) received[i].get("var1");
            long var2 = (Long) received[i].get("var2");
            long var3 = (Long) received[i].get("var3");
            assertTrue(var1 >= startTime);
            assertEquals(var1, var2 - 1);
            assertEquals(var3, var2 + var1);
        }
    }
}
