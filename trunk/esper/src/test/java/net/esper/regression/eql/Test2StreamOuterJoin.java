package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.bean.SupportBean_S1;
import net.esper.support.util.SupportUpdateListener;

public class Test2StreamOuterJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    private SupportBean_S0 eventsS0[] = new SupportBean_S0[15];
    private SupportBean_S1 eventsS1[] = new SupportBean_S1[15];

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        updateListener = new SupportUpdateListener();

        int count = 100;
        for (int i = 0; i < eventsS0.length; i++)
        {
            eventsS0[i] = new SupportBean_S0(count++, Integer.toString(i));
        }
        count = 200;
        for (int i = 0; i < eventsS1.length; i++)
        {
            eventsS1[i] = new SupportBean_S1(count++, Integer.toString(i));
        }
    }

    public void testFullOuterJoin()
    {
        setupStatement("full");

        // Send S0[0]
        sendEvent(eventsS0[0]);
        compareEvent(updateListener.assertOneGetNewAndReset(), 100, "0", null, null);

        // Send S1[1]
        sendEvent(eventsS1[1]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 201, "1");

        // Send S1[2] and S0[2]
        sendEvent(eventsS1[2]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 202, "2");
        sendEvent(eventsS0[2]);
        compareEvent(updateListener.assertOneGetNewAndReset(), 102, "2", 202, "2");

        // Send S0[3] and S1[3]
        sendEvent(eventsS0[3]);
        compareEvent(updateListener.assertOneGetNewAndReset(), 103, "3", null, null);
        sendEvent(eventsS1[3]);
        compareEvent(updateListener.assertOneGetNewAndReset(), 103, "3", 203, "3");

        // Send S0[4], pushes S0[0] out of window
        sendEvent(eventsS0[4]);
        EventBean oldEvent = updateListener.getLastOldData()[0];
        EventBean newEvent = updateListener.getLastNewData()[0];
        compareEvent(oldEvent, 100, "0", null, null);
        compareEvent(newEvent, 104, "4", null, null);
        updateListener.reset();

        // Send S1[4]
        sendEvent(eventsS1[4]);
        compareEvent(updateListener.assertOneGetNewAndReset(), 104, "4", 204, "4");

        // Send S1[5]
        sendEvent(eventsS1[5]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 205, "5");

        // Send S1[6], pushes S1[1] out of window
        sendEvent(eventsS1[5]);
        oldEvent = updateListener.getLastOldData()[0];
        newEvent = updateListener.getLastNewData()[0];
        compareEvent(oldEvent, null, null, 201, "1");
        compareEvent(newEvent, null, null, 205, "5");
    }

    public void testRightOuterJoin()
    {
        setupStatement("right");

        // Send S0 events, no events expected
        sendEvent(eventsS0[0]);
        sendEvent(eventsS0[1]);
        assertFalse(updateListener.isInvoked());

        // Send S1[2]
        sendEvent(eventsS1[2]);
        EventBean event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, null, null, 202, "2");

        // Send S0[2] events, joined event expected
        sendEvent(eventsS0[2]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 102, "2", 202, "2");

        // Send S1[3]
        sendEvent(eventsS1[3]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, null, null, 203, "3");

        // Send some more S0 events
        sendEvent(eventsS0[3]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 103, "3", 203, "3");

        // Send some more S0 events
        sendEvent(eventsS0[4]);
        assertFalse(updateListener.isInvoked());

        // Push S0[2] out of the window
        sendEvent(eventsS0[5]);
        event = updateListener.assertOneGetOldAndReset();
        compareEvent(event, 102, "2", 202, "2");

        // Some more S1 events
        sendEvent(eventsS1[6]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 206, "6");
        sendEvent(eventsS1[7]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 207, "7");
        sendEvent(eventsS1[8]);
        compareEvent(updateListener.assertOneGetNewAndReset(), null, null, 208, "8");

        // Push S1[2] out of the window
        sendEvent(eventsS1[9]);
        EventBean oldEvent = updateListener.getLastOldData()[0];
        EventBean newEvent = updateListener.getLastNewData()[0];
        compareEvent(oldEvent, null, null, 202, "2");
        compareEvent(newEvent, null, null, 209, "9");
    }

    public void testLeftOuterJoin()
    {
        setupStatement("left");

        // Send S1 events, no events expected
        sendEvent(eventsS1[0]);
        sendEvent(eventsS1[1]);
        sendEvent(eventsS1[3]);
        assertNull(updateListener.getLastNewData());    // No events expected

        // Send S0 event, expect event back from outer join
        sendEvent(eventsS0[2]);
        EventBean event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 102, "2", null, null);

        // Send S1 event matching S0, expect event back
        sendEvent(eventsS1[2]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 102, "2", 202, "2");

        // Send some more unmatched events
        sendEvent(eventsS1[4]);
        sendEvent(eventsS1[5]);
        sendEvent(eventsS1[6]);
        assertNull(updateListener.getLastNewData());    // No events expected

        // Send event, expect a join result
        sendEvent(eventsS0[5]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 105, "5", 205, "5");

        // Let S1[2] go out of the window (lenght 5), expected old join event
        sendEvent(eventsS1[7]);
        sendEvent(eventsS1[8]);
        event = updateListener.assertOneGetOldAndReset();
        compareEvent(event, 102, "2", 202, "2");

        // S0[9] should generate an outer join event
        sendEvent(eventsS0[9]);
        event = updateListener.assertOneGetNewAndReset();
        compareEvent(event, 109, "9", null, null);

        // S0[2] Should leave the window (length 3), should get OLD and NEW event
        sendEvent(eventsS0[10]);
        EventBean oldEvent = updateListener.getLastOldData()[0];
        EventBean newEvent = updateListener.getLastNewData()[0];
        compareEvent(oldEvent, 102, "2", null, null);     // S1[2] has left the window already
        compareEvent(newEvent, 110, "10", null, null);
    }

    public void testEventType()
    {
        EPStatement outerJoinView = setupStatement("left");

        assertEquals(String.class, outerJoinView.getEventType().getPropertyType("s0.p00"));
        assertEquals(int.class, outerJoinView.getEventType().getPropertyType("s0.id"));
        assertEquals(String.class, outerJoinView.getEventType().getPropertyType("s1.p10"));
        assertEquals(int.class, outerJoinView.getEventType().getPropertyType("s1.id"));
        assertEquals(4, outerJoinView.getEventType().getPropertyNames().length);
    }
    
    private void compareEvent(EventBean receivedEvent, Integer idS0, String p00, Integer idS1, String p10)
    {
        assertEquals(idS0, receivedEvent.get("s0.id"));
        assertEquals(idS1, receivedEvent.get("s1.id"));
        assertEquals(p00, receivedEvent.get("s0.p00"));
        assertEquals(p10, receivedEvent.get("s1.p10"));
    }

    private EPStatement setupStatement(String outerJoinType)
    {
        String joinStatement = "select s0.id, s0.p00, s1.id, s1.p10 from " +
            SupportBean_S0.class.getName() + ".win:length(3) as s0 " +
            outerJoinType + " outer join " +
            SupportBean_S1.class.getName() + ".win:length(5) as s1" +
            " on s0.p00 = s1.p10";

        EPStatement outerJoinView = epService.getEPAdministrator().createEQL(joinStatement);
        outerJoinView.addListener(updateListener);

        return outerJoinView;
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
