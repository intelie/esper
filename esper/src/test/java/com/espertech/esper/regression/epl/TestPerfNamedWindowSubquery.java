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

import java.util.HashMap;

public class TestPerfNamedWindowSubquery extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerStmtOne;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        listenerStmtOne = new SupportUpdateListener();
    }

    public void testNoShare() {
        runAssertion(false, false, false);
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
        epService.getEPAdministrator().createEPL("create schema EventSchema(e0 string, e1 int, e2 string)");

        String createEpl = "create window MyWindow.win:keepall() as select * from SupportBean";
        if (enableIndexShareCreate) {
            createEpl = "@Hint('enable_window_subquery_indexshare') " + createEpl;
        }
        epService.getEPAdministrator().createEPL(createEpl);
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        if (createExplicitIndex) {
            epService.getEPAdministrator().createEPL("create index MyIndex on MyWindow (string)");
        }

        String consumeEpl = "select e0, (select string from MyWindow where intPrimitive = es.e1 and string = es.e2) as val from EventSchema as es";
        if (disableIndexShareConsumer) {
            consumeEpl = "@Hint('disable_window_subquery_indexshare') " + consumeEpl;
        }
        EPStatement consumeStmt = epService.getEPAdministrator().createEPL(consumeEpl);
        consumeStmt.addListener(listenerStmtOne);

        String[] fields = "e0,val".split(",");

        // test once
        epService.getEPRuntime().sendEvent(new SupportBean("WX", 10));
        sendEvent("E1", 10, "WX");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", "WX"});

        // preload
        for (int i = 0; i < 10000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("W" + i, i));
        }
        
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            sendEvent("E" + i, i, "W" + i);
            ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E" + i, "W" + i});
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        assertTrue("delta=" + delta, delta < 500);

        epService.getEPAdministrator().destroyAllStatements();
    }

    private void sendEvent(String e0, int e1, String e2) {
        HashMap<String, Object> event = new HashMap<String, Object>();
        event.put("e0", e0);
        event.put("e1", e1);
        event.put("e2", e2);
        epService.getEPRuntime().sendEvent(event, "EventSchema");
    }
}
