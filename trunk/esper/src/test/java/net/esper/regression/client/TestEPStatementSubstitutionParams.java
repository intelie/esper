package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.EPPreparedStatement;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.SupportUpdateListener;

public class TestEPStatementSubstitutionParams extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    // TODO: test subqueries, all clauses, views, pattern observer and guard, all constant places, patterns
    // TODO: test OM substitution params, rendering to and from EQL
    // TODO: test with and without statement name
    // TOOD: negative test cases
    //  - trying to set values where no ? specified
    //  - trying to set too many values
    //  - not setting all values
    //  - try multiple times
    
    public void testSimple()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string=?)";
        EPPreparedStatement prepared = epService.getEPAdministrator().prepareEQL(stmt);
        prepared.setObject(1, "e1");
        EPStatement statement = epService.getEPAdministrator().create(prepared);
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("e2", 10));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("e1", 10));
        assertTrue(listener.isInvoked());
    }

}
