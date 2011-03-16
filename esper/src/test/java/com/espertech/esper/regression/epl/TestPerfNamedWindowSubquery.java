package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanRange;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;

public class TestPerfNamedWindowSubquery extends TestCase
{
    private static final Log log = LogFactory.getLog(TestPerfNamedWindowSubquery.class);

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        listener = new SupportUpdateListener();
    }

    public void testConstantValue() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        runConstantValueAssertion(false, false);
        runConstantValueAssertion(true, false);
        runConstantValueAssertion(true, true);
    }

    private void runConstantValueAssertion(boolean indexShare, boolean buildIndex) {
        String createEpl = "create window MyWindow.win:keepall() as select * from SupportBean";
        if (indexShare) {
            createEpl = "@Hint('enable_window_subquery_indexshare') " + createEpl;
        }
        epService.getEPAdministrator().createEPL(createEpl);

        if (buildIndex) {
            epService.getEPAdministrator().createEPL("create index idx1 on MyWindow(string hash, intPrimitive btree)");
        }
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        // preload
        for (int i = 0; i < 10000; i++) {
            SupportBean bean = new SupportBean("E" + i, i);
            bean.setDoublePrimitive(i);
            epService.getEPRuntime().sendEvent(bean);
        }

        // single-field compare
        String[] fields = "val".split(",");
        String eplSingle = "select (select intPrimitive from MyWindow where string = 'E9734') as val from SupportBeanRange sbr";
        EPStatement stmtSingle = epService.getEPAdministrator().createEPL(eplSingle);
        stmtSingle.addListener(listener);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "", -1, -1));
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {9734});
        }
        long delta = System.currentTimeMillis() - startTime;
        assertTrue("delta=" + delta, delta < 500);
        stmtSingle.destroy();

        // two-field compare
        String eplTwoHash = "select (select intPrimitive from MyWindow where string = 'E9736' and intPrimitive = 9736) as val from SupportBeanRange sbr";
        EPStatement stmtTwoHash = epService.getEPAdministrator().createEPL(eplTwoHash);
        stmtTwoHash.addListener(listener);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "", -1, -1));
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {9736});
        }
        delta = System.currentTimeMillis() - startTime;
        assertTrue("delta=" + delta, delta < 500);
        stmtTwoHash.destroy();

        // range compare single
        if (buildIndex) {
            epService.getEPAdministrator().createEPL("create index idx2 on MyWindow(intPrimitive btree)");
        }
        String eplSingleBTree = "select (select intPrimitive from MyWindow where intPrimitive between 9735 and 9735) as val from SupportBeanRange sbr";
        EPStatement stmtSingleBtree = epService.getEPAdministrator().createEPL(eplSingleBTree);
        stmtSingleBtree.addListener(listener);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "", -1, -1));
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {9735});
        }
        delta = System.currentTimeMillis() - startTime;
        assertTrue("delta=" + delta, delta < 500);
        stmtSingleBtree.destroy();

        // range compare composite
        String eplComposite = "select (select intPrimitive from MyWindow where string = 'E9738' and intPrimitive between 9738 and 9738) as val from SupportBeanRange sbr";
        EPStatement stmtComposite = epService.getEPAdministrator().createEPL(eplComposite);
        stmtComposite.addListener(listener);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBeanRange("R", "", -1, -1));
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {9738});
        }
        delta = System.currentTimeMillis() - startTime;
        assertTrue("delta=" + delta, delta < 500);
        stmtComposite.destroy();

        // destroy all
        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testKeyAndRange() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        runKeyAndRangeAssertion(false, false);
        runKeyAndRangeAssertion(true, false);
        runKeyAndRangeAssertion(true, true);
    }

    private void runKeyAndRangeAssertion(boolean indexShare, boolean buildIndex) {
        String createEpl = "create window MyWindow.win:keepall() as select * from SupportBean";
        if (indexShare) {
            createEpl = "@Hint('enable_window_subquery_indexshare') " + createEpl;
        }
        epService.getEPAdministrator().createEPL(createEpl);

        if (buildIndex) {
            epService.getEPAdministrator().createEPL("create index idx1 on MyWindow(string hash, intPrimitive btree)");
        }
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        // preload
        for (int i = 0; i < 10000; i++) {
            String string = i < 5000 ? "A" : "B";
            epService.getEPRuntime().sendEvent(new SupportBean(string, i));
        }

        String[] fields = "cols.mini,cols.maxi".split(",");
        String queryEpl = "select (select min(intPrimitive) as mini, max(intPrimitive) as maxi from MyWindow where string = sbr.key and intPrimitive between sbr.rangeStart and sbr.rangeEnd) as cols from SupportBeanRange sbr";
        EPStatement stmt = epService.getEPAdministrator().createEPL(queryEpl);
        stmt.addListener(listener);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBeanRange("R1", "A", 300, 312));
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {300, 312});
        }
        long delta = System.currentTimeMillis() - startTime;
        assertTrue("delta=" + delta, delta < 500);
        log.info("delta=" + delta);

        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testRange() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        
        runRangeAssertion(false, false);
        runRangeAssertion(true, false);
        runRangeAssertion(true, true);
    }

    private void runRangeAssertion(boolean indexShare, boolean buildIndex) {
        String createEpl = "create window MyWindow.win:keepall() as select * from SupportBean";
        if (indexShare) {
            createEpl = "@Hint('enable_window_subquery_indexshare') " + createEpl;
        }
        epService.getEPAdministrator().createEPL(createEpl);

        if (buildIndex) {
            epService.getEPAdministrator().createEPL("create index idx1 on MyWindow(intPrimitive btree)");
        }
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        // preload
        for (int i = 0; i < 10000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", i));
        }

        String[] fields = "cols.mini,cols.maxi".split(",");
        String queryEpl = "select (select min(intPrimitive) as mini, max(intPrimitive) as maxi from MyWindow where intPrimitive between sbr.rangeStart and sbr.rangeEnd) as cols from SupportBeanRange sbr";
        EPStatement stmt = epService.getEPAdministrator().createEPL(queryEpl);
        stmt.addListener(listener);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBeanRange("R1", "K", 300, 312));
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {300, 312});
        }
        long delta = System.currentTimeMillis() - startTime;
        assertTrue("delta=" + delta, delta < 500);
        log.info("delta=" + delta);

        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testKeyedRange() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        String createEpl = "create window MyWindow.win:keepall() as select * from SupportBean";
        epService.getEPAdministrator().createEPL(createEpl);
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        // preload
        for (int i = 0; i < 10000; i++) {
            String key = i < 5000 ? "A" : "B";
            epService.getEPRuntime().sendEvent(new SupportBean(key, i));
        }

        String[] fields = "cols.mini,cols.maxi".split(",");
        String queryEpl = "select (select min(intPrimitive) as mini, max(intPrimitive) as maxi from MyWindow " +
                "where string = sbr.key and intPrimitive between sbr.rangeStart and sbr.rangeEnd) as cols from SupportBeanRange sbr";
        EPStatement stmt = epService.getEPAdministrator().createEPL(queryEpl);
        stmt.addListener(listener);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            epService.getEPRuntime().sendEvent(new SupportBeanRange("R1", "A", 299, 313));
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {299, 313});

            epService.getEPRuntime().sendEvent(new SupportBeanRange("R2", "B", 7500, 7510));
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {7500, 7510});
        }
        long delta = System.currentTimeMillis() - startTime;
        assertTrue("delta=" + delta, delta < 500);
        log.info("delta=" + delta);
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
        consumeStmt.addListener(listener);

        String[] fields = "e0,val".split(",");

        // test once
        epService.getEPRuntime().sendEvent(new SupportBean("WX", 10));
        sendEvent("E1", 10, "WX");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", "WX"});

        // preload
        for (int i = 0; i < 10000; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("W" + i, i));
        }
        
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            sendEvent("E" + i, i, "W" + i);
            ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E" + i, "W" + i});
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
