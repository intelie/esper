package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.bean.SupportBean_S1;
import net.esper.support.bean.SupportBean_S2;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;

public class TestSubselectExists extends TestCase
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

    public void testExistsInSelect()
    {
        String stmtText = "select exists (select * from S1.win:length(1000)) as value from S0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(false, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(true, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testExists()
    {
        String stmtText = "select id from S0 where exists (select * from S1.win:length(1000))";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(2, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-2));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertEquals(3, listener.assertOneGetNewAndReset().get("id"));
    }

    public void testExistsFiltered()
    {
        String stmtText = "select id from S0 as s0 where exists (select * from S1.win:length(1000) as s1 where s1.id=s0.id)";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-2));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(-2));
        assertEquals(-2, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(3));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertEquals(3, listener.assertOneGetNewAndReset().get("id"));
    }

    public void testTwoExistsFiltered()
    {
        String stmtText = "select id from S0 as s0 where " +
                "exists (select * from S1.win:length(1000) as s1 where s1.id=s0.id) " +
                "and " +
                "exists (select * from S2.win:length(1000) as s2 where s2.id=s0.id) "
                ;

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S2(3));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertEquals(3, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        assertEquals(1, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertFalse(listener.isInvoked());
    }

    public void testNotExists()
    {
        String stmtText = "select id from S0 where not exists (select * from S1.win:length(1000))";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(2, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(-2));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertFalse(listener.isInvoked());
    }
}
