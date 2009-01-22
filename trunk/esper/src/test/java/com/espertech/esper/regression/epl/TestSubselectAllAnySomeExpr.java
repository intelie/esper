package com.espertech.esper.regression.epl;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanArrayCollMap;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestSubselectAllAnySomeExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("ArrayBean", SupportBeanArrayCollMap.class);
    }

    // TODO:
    // - test IN with (select *) and (null in)
    // - test filter optimization removing filter expression entirely
    // - review null values and their return result
    // - test subselect with where-clause
    // - test coercion subselect result
    // - test OM
    // - test BigDec and BigInt
    // - test null value returned by a boolean expression and NOT not handling it
    // replace "error starting view" with "error starting statement".
    // - look at entity SQL, expose Collections methods
    //
    // Collections.unionSet(expression, ...expression)
    // Collections.intersectionSet(expression, ...expression)
    // Collections.differenceSet(expression, ...expression)
    // Collections.subselect(subselect)
    // Collections.isEmpty(expression)
    // Collections.overlaps(expression, SETOF(subselect))
    // Collections.overlaps(expression, {1, 2, 3})
    // Collections.overlaps(expression, {1, 2, 3})
    // ROWS(subselect)
    // entity SQL en-us/library/bb738456.aspx

    public void testRelationalOpAll()
    {
        String[] fields = "g,ge,l,le".split(",");
        String stmtText = "select " +
            "intPrimitive > all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as g, " +
            "intPrimitive >= all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as ge, " +
            "intPrimitive < all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as l, " +
            "intPrimitive <= all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as le " +
            " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 1));

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("S2", 2));

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E6", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});

        try
        {
            epService.getEPAdministrator().createEPL("select intArr > all (select intPrimitive from SupportBean.win:keepall()) from ArrayBean");
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords [select intArr > all (select intPrimitive from SupportBean.win:keepall()) from ArrayBean]", ex.getMessage());
        }
    }

    public void testRelationalOpSome()
    {
        String[] fields = "g,ge,l,le".split(",");
        String stmtText = "select " +
            "intPrimitive > any (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as g, " +
            "intPrimitive >= any (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as ge, " +
            "intPrimitive < any (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as l, " +
            "intPrimitive <= any (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as le " +
            " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 1));

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E2a", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("S2", 2));

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E6", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});
    }

    public void testEqualsNotEqualsAll()
    {
        String[] fields = "eq,neq,sqlneq,nneq".split(",");
        String stmtText = "select " +
                          "intPrimitive = all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as eq, " +
                          "intPrimitive != all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as neq, " +
                          "intPrimitive <> all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as sqlneq, " +
                          "not intPrimitive = all (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as nneq " +
                          " from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, true, false});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 11));

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 12));

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 14));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, true, true});
    }

    // Test "value = SOME (subselect)" which is the same as "value IN (subselect)"
    public void testEqualsAnyOrSome()
    {
        String[] fields = "r1,r2,r3,r4".split(",");
        String stmtText = "select " +
                    "intPrimitive = SOME (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as r1, " +
                    "intPrimitive = ANY (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as r2, " +
                    "intPrimitive != SOME (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as r3, " +
                    "intPrimitive <> ANY (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as r4 " +
                    "from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 12));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("S2", 12));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 12));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 13));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, true, true});
    }

    public void testInvalid()
    {
        try
        {
            String stmtText = "select intArr = all (select intPrimitive from SupportBean.win:keepall()) as r1 from ArrayBean";
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords [select intArr = all (select intPrimitive from SupportBean.win:keepall()) as r1 from ArrayBean]", ex.getMessage());
        }
    }
}
