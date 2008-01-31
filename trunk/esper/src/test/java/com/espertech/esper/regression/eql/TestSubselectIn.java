package com.espertech.esper.regression.eql;

import junit.framework.TestCase;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.bean.SupportBean_S2;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.SerializableObjectCopier;

public class TestSubselectIn extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("S0", SupportBean_S0.class);
        config.addEventTypeAlias("S1", SupportBean_S1.class);
        config.addEventTypeAlias("S2", SupportBean_S2.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        // Use external clocking for the test, reduces logging
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testInSelect()
    {
        String stmtText = "select id in (select id from S1.win:length(1000)) as value from S0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        runTestInSelect();
    }

    public void testInSelectOM() throws Exception
    {
        EPStatementObjectModel subquery = new EPStatementObjectModel();
        subquery.setSelectClause(SelectClause.create("id"));
        subquery.setFromClause(FromClause.create(FilterStream.create("S1").addView(View.create("win", "length", 1000))));

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setFromClause(FromClause.create(FilterStream.create("S0")));
        model.setSelectClause(SelectClause.create().add(Expressions.subqueryIn("id", subquery), "value"));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        String stmtText = "select id in (select id from S1.win:length(1000)) as value from S0";
        assertEquals(stmtText, model.toEQL());

        EPStatement stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

        runTestInSelect();
    }

    public void testInSelectCompile() throws Exception
    {
        String stmtText = "select id in (select id from S1.win:length(1000)) as value from S0";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEQL(stmtText);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(stmtText, model.toEQL());

        EPStatement stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

        runTestInSelect();
    }

    private void runTestInSelect()
    {
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(-1));
        assertEquals(true, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(5));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(4));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(5));
        assertEquals(true, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testInSelectWhere()
    {
        String stmtText = "select id in (select id from S1.win:length(1000) where id > 0) as value from S0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(-1));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(5));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(4));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(5));
        assertEquals(true, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testInSelectWhereExpressions()
    {
        String stmtText = "select 3*id in (select 2*id from S1.win:length(1000)) as value from S0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(-1));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(6));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(4));
        assertEquals(true, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testInNullable()
    {
        String stmtText = "select id from S0 as s0 where p00 in (select p10 from S1.win:length(1000))";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "a"));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, null));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1, "A"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3, null));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S0(4, "A"));
        assertEquals(4, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-2, null));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(5, null));
        assertEquals(5, listener.assertOneGetNewAndReset().get("id"));
    }

    public void testInNullableCoercion()
    {
        String stmtText = "select longBoxed from " + SupportBean.class.getName() + "(string='A') as s0 " +
                          "where longBoxed in " +
                          "(select intBoxed from " + SupportBean.class.getName() + "(string='B').win:length(1000))";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        sendBean("A", 0, 0L);
        sendBean("A", null, null);
        assertFalse(listener.isInvoked());

        sendBean("B", null, null);

        sendBean("A", 0, 0L);
        assertFalse(listener.isInvoked());
        sendBean("A", null, null);
        assertEquals(null, listener.assertOneGetNewAndReset().get("longBoxed"));

        sendBean("B", 99, null);

        sendBean("A", null, null);
        assertEquals(null, listener.assertOneGetNewAndReset().get("longBoxed"));
        sendBean("A", null, 99l);
        assertEquals(99L, listener.assertOneGetNewAndReset().get("longBoxed"));

        sendBean("B", 98, null);

        sendBean("A", null, 98l);
        assertEquals(98L, listener.assertOneGetNewAndReset().get("longBoxed"));
    }

    public void testInNullRow()
    {
        String stmtText = "select intBoxed from " + SupportBean.class.getName() + "(string='A') as s0 " +
                          "where intBoxed in " +
                          "(select longBoxed from " + SupportBean.class.getName() + "(string='B').win:length(1000))";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        sendBean("B", 1, 1l);

        sendBean("A", null, null);
        assertFalse(listener.isInvoked());

        sendBean("A", 1, 1l);
        assertEquals(1, listener.assertOneGetNewAndReset().get("intBoxed"));

        sendBean("B", null, null);

        sendBean("A", null, null);
        assertEquals(null, listener.assertOneGetNewAndReset().get("intBoxed"));

        sendBean("A", 1, 1l);
        assertEquals(1, listener.assertOneGetNewAndReset().get("intBoxed"));
    }

    public void testNotInNullRow()
    {
        String stmtText = "select intBoxed from " + SupportBean.class.getName() + "(string='A') as s0 " +
                          "where intBoxed not in " +
                          "(select longBoxed from " + SupportBean.class.getName() + "(string='B').win:length(1000))";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        sendBean("B", 1, 1l);

        sendBean("A", null, null);
        assertEquals(null, listener.assertOneGetNewAndReset().get("intBoxed"));

        sendBean("A", 1, 1l);
        assertFalse(listener.isInvoked());

        sendBean("B", null, null);

        sendBean("A", null, null);
        assertFalse(listener.isInvoked());

        sendBean("A", 1, 1l);
        assertFalse(listener.isInvoked());
    }

    public void testNotInSelect()
    {
        String stmtText = "select not id in (select id from S1.win:length(1000)) as value from S0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(true, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(true, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(-1));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(5));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(4));
        assertEquals(true, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(5));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testNotInNullableCoercion()
    {
        String stmtText = "select longBoxed from " + SupportBean.class.getName() + "(string='A') as s0 " +
                          "where longBoxed not in " +
                          "(select intBoxed from " + SupportBean.class.getName() + "(string='B').win:length(1000))";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        sendBean("A", 0, 0L);
        assertEquals(0L, listener.assertOneGetNewAndReset().get("longBoxed"));

        sendBean("A", null, null);
        assertEquals(null, listener.assertOneGetNewAndReset().get("longBoxed"));

        sendBean("B", null, null);

        sendBean("A", 1, 1L);
        assertEquals(1L, listener.assertOneGetNewAndReset().get("longBoxed"));
        sendBean("A", null, null);
        assertFalse(listener.isInvoked());

        sendBean("B", 99, null);

        sendBean("A", null, null);
        assertFalse(listener.isInvoked());
        sendBean("A", null, 99l);
        assertFalse(listener.isInvoked());

        sendBean("B", 98, null);

        sendBean("A", null, 98l);
        assertFalse(listener.isInvoked());

        sendBean("A", null, 97l);
        assertEquals(97L, listener.assertOneGetNewAndReset().get("longBoxed"));
    }

    private void sendBean(String string, Integer intBoxed, Long longBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntBoxed(intBoxed);
        bean.setLongBoxed(longBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }
}
