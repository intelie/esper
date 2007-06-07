package net.esper.regression.client;

import net.esper.client.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestPatternGuardPlugIn extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = new Configuration();
        configuration.addPlugInPatternGuard("myplugin", "count_to", MyCountToPatternGuardFactory.class.getName());
        configuration.addEventTypeAlias("Bean", SupportBean.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        listener = new SupportUpdateListener();
    }

    public void testGuard()
    {
        String stmtText = "select * from pattern [(every Bean) where myplugin:count_to(10)]";
        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        statement.addListener(listener);

        for (int i = 0; i < 10; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean());
            assertTrue(listener.isInvoked());
            listener.reset();
        }
        
        epService.getEPRuntime().sendEvent(new SupportBean());
        assertFalse(listener.isInvoked());
    }

    public void testInvalid()
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.addPlugInPatternGuard("namespace", "name", String.class.getName());
            epService = EPServiceProviderManager.getDefaultProvider(configuration);
            epService.initialize();
            String stmtText = "select * from pattern [every " + SupportBean.class.getName() +
                               " where namespace:name(10)]";
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error casting guard factory instance to net.esper.pattern.guard.GuardFactory interface for guard 'name' [select * from pattern [every net.esper.support.bean.SupportBean where namespace:name(10)]]", ex.getMessage());
        }
    }


}
