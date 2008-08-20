package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.event.EventBean;

public class TestLiteralConstants extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addEventTypeAlias("SupportBean", SupportBean.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testLiteral()
    {
        String statement = "select 0x23 as mybyte, " +
                           "'\u0041' as myunicode " +
                           "from SupportBean";

        EPStatement stmt = epService.getEPAdministrator().createEPL(statement);
        stmt.addListener(updateListener);

        epService.getEPRuntime().sendEvent(new SupportBean("e1", 100));

        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals((byte) 35, event.get("mybyte"));
        assertEquals("A", event.get("myunicode"));
    }
}
