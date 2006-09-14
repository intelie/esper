package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean_S0;
import net.esper.event.EventBean;

public class TestPatternTimeWindow extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        String stmtText = "select a.id as idA, b.id as idB, " +
                "a.p00 as p00A, b.p00 as p00B from pattern [every a=" + SupportBean_S0.class.getName() +
                " -> every b=" + SupportBean_S0.class.getName() + "(p00=a.p00)].win:time(1)";
        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);

        updateListener = new SupportUpdateListener();
        statement.addListener(updateListener);
    }

    public void testFollowedByAndWindow()
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        sendEvent(1, "e1a");
        assertFalse(updateListener.isInvoked());
        sendEvent(2, "e1a");
        assertNewEvent(1, 2, "e1a");

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(500));
        sendEvent(10, "e2a");
        sendEvent(11, "e2b");
        sendEvent(12, "e2c");
        assertFalse(updateListener.isInvoked());
        sendEvent(13, "e2b");
        assertNewEvent(11, 13, "e2b");

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1000));
        assertOldEvent(1, 2, "e1a");

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1500));
        assertOldEvent(11, 13, "e2b");
    }

    private void assertNewEvent(int idA, int idB, String p00)
    {
        EventBean eventBean = updateListener.assertOneGetNewAndReset();
        compareEvent(eventBean, idA, idB, p00);
    }

    private void assertOldEvent(int idA, int idB, String p00)
    {
        EventBean eventBean = updateListener.assertOneGetOldAndReset();
        compareEvent(eventBean, idA, idB, p00);
    }

    private void compareEvent(EventBean eventBean, int idA, int idB, String p00)
    {
        assertEquals(idA, eventBean.get("idA"));
        assertEquals(idB, eventBean.get("idB"));
        assertEquals(p00, eventBean.get("p00A"));
        assertEquals(p00, eventBean.get("p00B"));
    }

    private void sendEvent(int id, String p00)
    {
        SupportBean_S0 event = new SupportBean_S0(id, p00);
        epService.getEPRuntime().sendEvent(event);
    }

}
