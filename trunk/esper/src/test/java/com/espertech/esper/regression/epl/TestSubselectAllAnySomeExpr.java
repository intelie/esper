package com.espertech.esper.regression.epl;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
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
    }

    // TODO:
    // - test invalid when array selected as part of in (provide hint that ANY/SOME/ALL should be used instead)
    // - ANY
    //   Subselect+ANY/SOME:  SELECT s1 FROM t1 WHERE s1 > ANY (SELECT s1 FROM t2);
    //   Value+ANY/SOME:      select intValue = ANY (1, 2, 3)  (same as IN, also test >, <, <=, >=, !=, <>)
    //   Array+ANY/SOME:      select intArray = ANY (1, 2, 3, {3,4})  (also test !=; also test {1, 2}; invalid: >, <, >=, <=)
    // - ALL
    // - test IN with (select *) and (null in)
    // - test filter optimization removing filter expression entirely
    // - review null values and their return result
    // - test subselect with where-clause

    public void testRelationalOpAll()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

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
    }

    public void testEqualsNotEqualsAll()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

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
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 11));

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {true, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, true, true});

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 12));

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 11));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, false, false, false});

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 14));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {false, true, true, true});
    }

    // Test "value = SOME (subselect)" which is the same as "value IN (subselect)"
    public void testEqualsAnyOrSome()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        String stmtText = "select intPrimitive = SOME (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as result from SupportBean(string like 'E%')";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        runAssertionSubselectAnySome();

        stmt.destroy();
        stmtText = "select intPrimitive = ANY (select intPrimitive from SupportBean(string like 'S%').win:keepall()) as result from SupportBean(string like 'E%')";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        runAssertionSubselectAnySome();
    }

    private void runAssertionSubselectAnySome()
    {
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        assertFalse((Boolean) listener.assertOneGetNewAndReset().get("result"));

        epService.getEPRuntime().sendEvent(new SupportBean("S1", 11));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 11));
        assertTrue((Boolean) listener.assertOneGetNewAndReset().get("result"));

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 12));
        assertFalse((Boolean) listener.assertOneGetNewAndReset().get("result"));

        epService.getEPRuntime().sendEvent(new SupportBean("S2", 12));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 12));
        assertTrue((Boolean) listener.assertOneGetNewAndReset().get("result"));
    }

    /**
     * TODO
    public void testArrayProperty()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("ArrayBean", SupportBeanArrayCollMap.class);

        String stmtText = "select intArr = (1, 2, 3) as result from ArrayBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanArrayCollMap(new int[] {4, 5, 3}));
        assertTrue((Boolean) listener.assertOneGetNewAndReset().get("result"));
    }
     */
}
