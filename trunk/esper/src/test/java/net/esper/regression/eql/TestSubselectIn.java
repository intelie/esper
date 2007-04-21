package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.bean.SupportBean_S1;
import net.esper.support.bean.SupportBean_S2;
import net.esper.support.bean.SupportBean;

public class TestSubselectIn extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = new Configuration();
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
