package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPQueryResult;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

// TODO: remove
public class TestNamedWindowExecuteQuery extends TestCase
{
    private EPServiceProvider epService;
    private final String[] fields = new String[] {"string", "intPrimitive"};

    // test prev and next
    // test expressions + current timestamp
    // test filter, etc
    // test join, subselect
    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("SupportBean", SupportBean.class.getName());
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String namedWin = "create window MyWindow.win:keepall() as select * from SupportBean";
        epService.getEPAdministrator().createEPL(namedWin);
        String insert = "insert into MyWindow select * from SupportBean";
        epService.getEPAdministrator().createEPL(insert);
    }

    public void testExecuteSimple() throws Exception
    {
        String query = "select * from MyWindow";
        EPQueryResult result = epService.getEPRuntime().executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, null);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        result = epService.getEPRuntime().executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E1", 1}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        result = epService.getEPRuntime().executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
    }

    public void testExecuteFilter() throws Exception
    {
        String query = "select * from MyWindow(intPrimitive > 1, intPrimitive < 10)";
        EPQueryResult result = epService.getEPRuntime().executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, null);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 5));
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E3", 5}});
    }
}
