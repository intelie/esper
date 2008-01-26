package net.esper.regression.event;

import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPServiceProvider;
import net.esper.client.Configuration;
import net.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestInitializeEngine extends TestCase
{
    public void testInitialize()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        EPServiceProvider epService = EPServiceProviderManager.getProvider("TestInitializeEngine", config);

        String eqlOne = "insert into A(a) select 1 from " + SupportBean.class.getName() + ".win:length(100)";
        String eqlTwo = "insert into A(a, b) select 1,2 from " + SupportBean.class.getName() + ".win:length(100)";

        // Asserting that the engine allows to use the new event stream A with more properties then the old A
        epService.getEPAdministrator().createEQL(eqlOne);
        epService.initialize();
        epService.getEPAdministrator().createEQL(eqlTwo);
    }
}
