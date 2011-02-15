package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.bean.SupportBean_ST1;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestNamedWindowSubqFilterRangeCorrel extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("ABean", SupportBean_S0.class);
        listener = new SupportUpdateListener();
    }

    // Problem:
    //   - between and 'in' automatically revert the range
    //   - >= and <= should not automatically revert the range
    public void test2StreamSubqueryCoercion() {
        epService.getEPAdministrator().getConfiguration().addEventType("S0", SupportBean_ST0.class);
        epService.getEPAdministrator().getConfiguration().addEventType("S1", SupportBean_ST1.class);

        String epl = "select (" +
                "select sum(intPrimitive) as sumi from SupportBean.win:keepall() where intPrimitive between s0.p01Long and s1.p11Long) " +
                "from S0.std:lastevent() s0, S1.std:lastevent() s1";
        runAssertion(epl, true);

        /*
        TODO
        epl = "select (" +
                "select sum(intPrimitive) as sumi from SupportBean.win:keepall() where intPrimitive >= s0.p01Long and intPrimitive <= s1.p11Long) " +
                "from S0.std:lastevent() s0, S1.std:lastevent() s1";
        runAssertion(epl, false);
        */
    }

    private void runAssertion(String epl, boolean isHasRangeReversal) {
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        
        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ST01", 10L));
        epService.getEPRuntime().sendEvent(new SupportBean_ST1("ST11", 20L));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 9));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 21));
        assertEquals(null, listener.assertOneGetNewAndReset().get("sumi")); // range 10 to 20
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 13));
        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ST0_1", 10L));  // range 10 to 20
        assertEquals(13, listener.assertOneGetNewAndReset().get("sumi"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST1("ST1_1", 13L));  // range 10 to 13
        assertEquals(13, listener.assertOneGetNewAndReset().get("sumi"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ST0_2", 13L));  // range 13 to 13
        assertEquals(13, listener.assertOneGetNewAndReset().get("sumi"));

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 14));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 12));
        epService.getEPRuntime().sendEvent(new SupportBean_ST1("ST1_3", 13L));  // range 13 to 13
        assertEquals(13, listener.assertOneGetNewAndReset().get("sumi"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST1("ST1_4", 20L));  // range 13 to 20
        assertEquals(27, listener.assertOneGetNewAndReset().get("sumi"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ST0_3", 11L));  // range 11 to 20
        assertEquals(39, listener.assertOneGetNewAndReset().get("sumi"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ST0_4", null));  // range null to 16
        assertEquals(null, listener.assertOneGetNewAndReset().get("sumi"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST1("ST1_5", null));  // range null to null
        assertEquals(null, listener.assertOneGetNewAndReset().get("sumi"));

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ST0_5", 20L));  // range 20 to null
        assertEquals(null, listener.assertOneGetNewAndReset().get("sumi"));

        if (isHasRangeReversal) {
            epService.getEPRuntime().sendEvent(new SupportBean_ST1("ST1_6", 14L));  // range 20 to 14
            assertEquals(29, listener.assertOneGetNewAndReset().get("sumi"));
        }
        else {
            epService.getEPRuntime().sendEvent(new SupportBean_ST1("ST1_6", 14L));  // range 20 to 14
            assertEquals(null, listener.assertOneGetNewAndReset().get("sumi"));
        }

        stmt.destroy();
    }

    /*
    TODO
    public void testNoShare() {
        runAssertion(false, false, false);
    }
    public void testNoShareCreate() {
        runAssertion(false, false, true);
    }

    public void testShare() {
        runAssertion(true, false, false);
    }

    public void testShareCreate() {
        runAssertion(true, false, true);
    }

    public void testDisableShare() {
        runAssertion(true, true, false);
    }

    public void testDisableShareCreate() {
        runAssertion(true, true, true);
    }
    */

    private void runAssertion(boolean enableIndexShareCreate, boolean disableIndexShareConsumer, boolean createExplicitIndex) {

        SupportUpdateListener listener = new SupportUpdateListener();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("S0", SupportBean_S0.class);

        String createEpl = "create window SupportWindow.win:keepall() as select * from SupportBean";
        if (enableIndexShareCreate) {
            createEpl = "@Hint('enable_window_subquery_indexshare') " + createEpl;
        }
        epService.getEPAdministrator().createEPL(createEpl);
        epService.getEPAdministrator().createEPL("insert into SupportWindow select * from SupportBean");

        EPStatement indexStmt = null;
        if (createExplicitIndex) {
            indexStmt = epService.getEPAdministrator().createEPL("create index MyIndex on SupportWindow(string)");
        }

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", -2));

        String consumeEpl = "select (select intPrimitive from SupportWindow(intPrimitive<0) sw where s0.p00=sw.string) as val from S0 s0";
        if (disableIndexShareConsumer) {
            consumeEpl = "@Hint('disable_window_subquery_indexshare') " + consumeEpl;
        }
        EPStatement consumeStmt = epService.getEPAdministrator().createEPL(consumeEpl);
        consumeStmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(10, "E1"));
        assertEquals(null, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(20, "E2"));
        assertEquals(-2, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(new SupportBean("E3", -3));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 4));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(-3, "E3"));
        assertEquals(-3, listener.assertOneGetNewAndReset().get("val"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(20, "E4"));
        assertEquals(null, listener.assertOneGetNewAndReset().get("val"));

        consumeStmt.stop();
        if (indexStmt != null) {
            indexStmt.stop();
        }
        consumeStmt.destroy();
        if (indexStmt != null) {
            indexStmt.destroy();
        }
    }
}
