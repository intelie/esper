package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestNamedWindowSubqCorrelIndex extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerStmtOne;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("ABean", SupportBean_S0.class);
        listenerStmtOne = new SupportUpdateListener();
    }

    // TODO
    // Test cleanup of subquery when last reference to it is gone
    // Test join
    // Test partial match of index
    // Test order of columns as provided by where-clause should not matter
    // Test coercion
    // Test performance
    // DOC: if named window and no index, then subquery is eligible to be shared
    // DOC: if subquery index is shared, then more locking and less overhead and less memory and more temporary objects as copies of index nodes are made
    // DOC: if subquery index not shared then less locking and more overhead(consumer) and more memory and less temporary objects
    // DOC: named window filter is equivalent to where-clause and makes it simpler to write as properties are for the same stream, also a hint to subscribe and not query named window
    
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

    private void runAssertion(boolean enableIndexShareCreate, boolean disableIndexShareConsumer, boolean createExplicitIndex) {
        String createEpl = "create window MyWindow.std:unique(string) as select * from SupportBean";
        if (enableIndexShareCreate) {
            createEpl = "@Hint('enable_window_subquery_indexshare') " + createEpl;
        }
        epService.getEPAdministrator().createEPL(createEpl);
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        if (createExplicitIndex) {
            epService.getEPAdministrator().createEPL("create index MyIndex on MyWindow (string)");
        }

        String consumeEpl = "select status.*, (select * from MyWindow where string = ABean.p00) as details from ABean as status";
        if (disableIndexShareConsumer) {
            consumeEpl = "@Hint('disable_window_subquery_indexshare') " + consumeEpl;
        }
        EPStatement consumeStmt = epService.getEPAdministrator().createEPL(consumeEpl);
        consumeStmt.addListener(listenerStmtOne);

        String[] fields = "id,details.string,details.intPrimitive".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 10));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 20));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 30));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E1"));
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {1, "E1", 10});

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "E2"));
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {2, "E2", 20});

        // test late start
        consumeStmt.destroy();
        consumeStmt = epService.getEPAdministrator().createEPL(consumeEpl);
        consumeStmt.addListener(listenerStmtOne);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E1"));
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {1, "E1", 10});

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "E2"));
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {2, "E2", 20});
    }
}
