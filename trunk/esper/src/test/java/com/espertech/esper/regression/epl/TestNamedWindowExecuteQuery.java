package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.epl.parse.EPStatementSyntaxException;
import com.espertech.esper.core.EPPreparedQuery;
import com.espertech.esper.core.EPQueryResult;
import com.espertech.esper.core.EPRuntimeSPI;
import junit.framework.TestCase;

public class TestNamedWindowExecuteQuery extends TestCase
{
    private EPServiceProvider epService;
    private final String[] fields = new String[] {"string", "intPrimitive"};

    // test expressions + current timestamp
    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("SupportBean", SupportBean.class.getName());
        config.addEventTypeAlias("SupportBean_A", SupportBean_A.class.getName());
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
        EPPreparedQuery prepared = ((EPRuntimeSPI) epService.getEPRuntime()).prepareQuery(query);
        EPQueryResult result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, null);
        ArrayAssertionUtil.assertEqualsExactOrder(prepared.execute().iterator(), fields, null);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertEqualsExactOrder(prepared.execute().iterator(), fields, new Object[][] {{"E1", 1}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
        ArrayAssertionUtil.assertEqualsExactOrder(prepared.execute().iterator(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
    }

    public void testExecuteFilter() throws Exception
    {
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 5));

        String query = "select * from MyWindow(intPrimitive > 1, intPrimitive < 10)";
        runAssertionFilter(query);

        query = "select * from MyWindow(intPrimitive > 1) where intPrimitive < 10";
        runAssertionFilter(query);

        query = "select * from MyWindow where intPrimitive < 10 and intPrimitive > 1";
        runAssertionFilter(query);
    }

    public void testAggUngroupedRowForAll() throws Exception
    {
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 5));
        String fields[] = new String[] {"total"};

        String query = "select sum(intPrimitive) as total from MyWindow";
        EPQueryResult result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{16}});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", -2));
        result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{14}});
    }

    public void testAggUngroupedRowForEvent() throws Exception
    {
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 5));
        String fields[] = new String[] {"string", "total"};

        String query = "select string, sum(intPrimitive) as total from MyWindow";
        EPQueryResult result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E1", 16}, {"E2", 16}, {"E3", 16}});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", -2));
        result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E1", 14}, {"E2", 14}, {"E3", 14}, {"E4", 14}});
    }

    public void testAggUngroupedRowForGroup() throws Exception
    {
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        String fields[] = new String[] {"string", "total"};

        String query = "select string, sum(intPrimitive) as total from MyWindow group by string order by string asc";
        EPQueryResult result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E1", 6}, {"E2", 11}});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", -2));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E1", 6}, {"E2", 9}, {"E3", 3}});
    }

    public void testJoin() throws Exception
    {
        String namedWin = "create window MySecondWindow.win:keepall() as select * from SupportBean_A";
        epService.getEPAdministrator().createEPL(namedWin);
        String insert = "insert into MySecondWindow select * from SupportBean_A";
        epService.getEPAdministrator().createEPL(insert);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 5));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        String fields[] = new String[] {"string", "intPrimitive", "id"};

        String query = "select string, intPrimitive, id from MyWindow nw1, " +
                            "MySecondWindow nw2 where nw1.string = nw2.id";
        EPQueryResult result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E2", 11, "E2"}});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E3"));

        result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E2", 11, "E2"}, {"E3", 1, "E3"}, {"E3", 2, "E3"}});
    }

    public void testInvalid()
    {
        String epl = "selectoo man";
        tryInvalidSyntax(epl, "Incorrect syntax near 'selectoo' [selectoo man]");

        epl = "select (select * from MyWindow) from MyWindow";
        tryInvalid(epl, "Error executing statement: Subqueries are not a supported feature of on-demand queries [select (select * from MyWindow) from MyWindow]");

        epl = "select * from MyWindow.stat:uni(intPrimitive)";
        tryInvalid(epl, "Error executing statement: Views are not a supported feature of on-demand queries [select * from MyWindow.stat:uni(intPrimitive)]");

        epl = "select * from MyWindow output every 10 seconds";
        tryInvalid(epl, "Error executing statement: Output rate limiting is not a supported feature of on-demand queries [select * from MyWindow output every 10 seconds]");

        epl = "insert into AStream select * from MyWindow";
        tryInvalid(epl, "Error executing statement: Insert-into is not a supported feature of on-demand queries [insert into AStream select * from MyWindow]");

        epl = "select * from pattern [every MyWindow]";
        tryInvalid(epl, "Error executing statement: On-demand queries require named windows and do not allow event streams or patterns [select * from pattern [every MyWindow]]");

        epl = "select prev(1, string) from MyWindow";
        tryInvalid(epl, "Error executing statement: Previous function cannot be used in this context [select prev(1, string) from MyWindow]");
    }

    private void tryInvalid(String epl, String message)
    {
        try
        {
            ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(epl);
            fail();
        }
        catch(EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    private void tryInvalidSyntax(String epl, String message)
    {
        try
        {
            ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(epl);
            fail();
        }
        catch(EPStatementSyntaxException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    private void runAssertionFilter(String query)
    {
        EPQueryResult result = ((EPRuntimeSPI) epService.getEPRuntime()).executeQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(result.iterator(), fields, new Object[][] {{"E3", 5}});

        EPPreparedQuery prepared = ((EPRuntimeSPI) epService.getEPRuntime()).prepareQuery(query);
        ArrayAssertionUtil.assertEqualsExactOrder(prepared.execute().iterator(), fields, new Object[][] {{"E3", 5}});
    }
}