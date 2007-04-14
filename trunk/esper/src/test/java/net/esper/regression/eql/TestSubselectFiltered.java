package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.bean.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;

public class TestSubselectFiltered extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = new Configuration();
        config.addEventTypeAlias("S0", SupportBean_S0.class);
        config.addEventTypeAlias("S1", SupportBean_S1.class);
        config.addEventTypeAlias("S2", SupportBean_S2.class);
        config.addEventTypeAlias("S3", SupportBean_S3.class);
        config.addEventTypeAlias("S4", SupportBean_S4.class);
        config.addEventTypeAlias("S5", SupportBean_S5.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        // Use external clocking for the test, reduces logging
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testWhereConstant()
    {
        String stmtText = "select (select id from S1.win:length(1000) where p10='X') as ids1 from S0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        // test no event, should return null
        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1, "Y"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertNull(listener.assertOneGetNewAndReset().get("ids1"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1, "X"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "Y"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(3, "Z"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertEquals(1, listener.assertOneGetNewAndReset().get("ids1"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        assertEquals(1, listener.assertOneGetNewAndReset().get("ids1"));

        // second X event
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "X"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids1"));
    }

    public void testWherePrevious()
    {
        String stmtText = "select (select prev(1, id) from S1.win:length(1000) where id=s0.id) as value from S0 as s0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertNull(listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(2));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(1, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertEquals(2, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testSelectWithWhereJoined()
    {
        String stmtText = "select (select id from S1.win:length(1000) where p10=s0.p00) as ids1 from S0 as s0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertNull(listener.assertOneGetNewAndReset().get("ids1"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1, "X"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "Y"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(3, "Z"));
        
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertNull(listener.assertOneGetNewAndReset().get("ids1"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "X"));
        assertEquals(1, listener.assertOneGetNewAndReset().get("ids1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "Y"));
        assertEquals(2, listener.assertOneGetNewAndReset().get("ids1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "Z"));
        assertEquals(3, listener.assertOneGetNewAndReset().get("ids1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "A"));
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids1"));
    }

    public void testSelectWithWhere2Subqery()
    {
        String stmtText = "select id from S0 as s0 where " +
                        " id = (select id from S1.win:length(1000) where s0.id = id) or id = (select id from S2.win:length(1000) where s0.id = id)";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        assertEquals(1, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S2(2));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(2, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertEquals(3, listener.assertOneGetNewAndReset().get("id"));
    }

    public void testJoinFilteredOne()
    {
        String stmtText = "select s0.id as s0id, s1.id as s1id, " +
                          "(select p20 from S2.win:length(1000) where id=s0.id) as s2p20, " +
                          "(select prior(1, p20) from S2.win:length(1000) where id=s0.id) as s2p20Prior, " +
                          "(select prev(1, p20) from S2.win:length(10) where id=s0.id) as s2p20Prev " +
                          "from S0 as s0, S1 as s1 " +
                          "where s0.id = s1.id and p00||p10 = (select p20 from S2.win:length(1000) where id=s0.id)";
        tryJoinFiltered(stmtText);
    }

    public void testJoinFilteredTwo()
    {
        String stmtText = "select s0.id as s0id, s1.id as s1id, " +
                          "(select p20 from S2.win:length(1000) where id=s0.id) as s2p20, " +
                          "(select prior(1, p20) from S2.win:length(1000) where id=s0.id) as s2p20Prior, " +
                          "(select prev(1, p20) from S2.win:length(10) where id=s0.id) as s2p20Prev " +
                          "from S0 as s0, S1 as s1 " +
                          "where s0.id = s1.id and (select s0.p00||s1.p10 = p20 from S2.win:length(1000) where id=s0.id)";
        tryJoinFiltered(stmtText);
    }

    public void tryJoinFiltered(String stmtText)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "X"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(0, "Y"));
        assertFalse(listener.isInvoked());
        
        epService.getEPRuntime().sendEvent(new SupportBean_S2(1, "ab"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "a"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(1, "b"));
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(1, event.get("s0id"));
        assertEquals(1, event.get("s1id"));
        assertEquals("ab", event.get("s2p20"));
        assertEquals(null, event.get("s2p20Prior"));
        assertEquals(null, event.get("s2p20Prev"));

        epService.getEPRuntime().sendEvent(new SupportBean_S2(2, "qx"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "q"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "x"));
        event = listener.assertOneGetNewAndReset();
        assertEquals(2, event.get("s0id"));
        assertEquals(2, event.get("s1id"));
        assertEquals("qx", event.get("s2p20"));
        assertEquals("ab", event.get("s2p20Prior"));
        assertEquals("ab", event.get("s2p20Prev"));
    }
}
