package net.esper.example.transaction;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;

public abstract class TestStmtBase extends TestCase
{
    protected EPServiceProvider epService;

    public void setUp()
    {
        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("TxnEventA", TxnEventA.class.getName());
        configuration.addEventTypeAlias("TxnEventB", TxnEventB.class.getName());
        configuration.addEventTypeAlias("TxnEventC", TxnEventC.class.getName());

        epService = EPServiceProviderManager.getProvider("TestStmtBase", configuration);
        epService.initialize();
    }

    protected void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }

}
