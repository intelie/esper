package com.espertech.esper.regression.eql;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestLeftOuterJoinWhere extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    private SupportBean_S0 eventsS0[] = new SupportBean_S0[15];
    private SupportBean_S1 eventsS1[] = new SupportBean_S1[15];

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
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

    public void testWhereNotNullIs()
    {
        setupStatement("where s1.p11 is not null");
        tryWhereNotNull();
    }

    public void testWhereNotNullNE()
    {
        setupStatement("where s1.p11 != null");
        tryWhereNotNull();
    }

    public void testWhereNotNullSQLNE()
    {
        setupStatement("where s1.p11 <> null");
        tryWhereNotNull();
    }

    public void testWhereNullIs()
    {
        setupStatement("where s1.p11 is null");
        tryWhereNull();
    }

    public void testWhereNullEq()
    {
        setupStatement("where s1.p11 = null");
        tryWhereNull();
    }

    public void testWhereJoinOrNull()
    {
        setupStatement("where s0.p01 = s1.p11 or s1.p11 is null");

        // Send S0[0] p01=a
        eventsS0[0].setP01("[a]");
        sendEvent(eventsS0[0]);
        compareEvent(updateListener.assertOneGetNewAndReset(), eventsS0[0], null);

        // Send events to test the join for multiple rows incl. null value
        SupportBean_S1 s1_1 = new SupportBean_S1(1000, "5", "X");
        SupportBean_S1 s1_2 = new SupportBean_S1(1001, "5", "Y");
        SupportBean_S1 s1_3 = new SupportBean_S1(1002, "5", "X");
        SupportBean_S1 s1_4 = new SupportBean_S1(1003, "5", null);
        SupportBean_S0 s0 = new SupportBean_S0(1, "5", "X");
        sendEvent(new Object[] { s1_1, s1_2, s1_3, s1_4, s0});

        assertEquals(3, updateListener.getLastNewData().length);
        Object[] received = new Object[3];
        for (int i = 0; i < 3; i++)
        {
            assertSame(s0, updateListener.getLastNewData()[i].get("s0"));
            received[i] = updateListener.getLastNewData()[i].get("s1");
        }
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {s1_1, s1_3, s1_4}, received );
    }

    public void testWhereJoin()
    {
        setupStatement("where s0.p01 = s1.p11");

        // Send S0[0] p01=a
        eventsS0[0].setP01("[a]");
        sendEvent(eventsS0[0]);
        assertFalse(updateListener.isInvoked());

        // Send S1[1] p11=b
        eventsS1[1].setP11("[b]");
        sendEvent(eventsS1[1]);
        assertFalse(updateListener.isInvoked());

        // Send S0[1] p01=c, no match expected
        eventsS0[1].setP01("[c]");
        sendEvent(eventsS0[1]);
        assertFalse(updateListener.isInvoked());

        // Send S1[2] p11=d
        eventsS1[2].setP11("[d]");
        sendEvent(eventsS1[2]);
        // Send S0[2] p01=d
        eventsS0[2].setP01("[d]");
        sendEvent(eventsS0[2]);
        compareEvent(updateListener.assertOneGetNewAndReset(), eventsS0[2], eventsS1[2]);

        // Send S1[3] and S0[3] with differing props, no match expected 
        eventsS1[3].setP11("[e]");
        sendEvent(eventsS1[3]);
        eventsS0[3].setP01("[e1]");
        sendEvent(eventsS0[3]);
        assertFalse(updateListener.isInvoked());
    }

    public EPStatement setupStatement(String whereClause)
    {
        String joinStatement = "select * from " +
            SupportBean_S0.class.getName() + ".win:length(5) as s0 " +
            "left outer join " +
            SupportBean_S1.class.getName() + ".win:length(5) as s1" +
            " on s0.p00 = s1.p10 " +
            whereClause;

        EPStatement outerJoinView = epService.getEPAdministrator().createEQL(joinStatement);
        outerJoinView.addListener(updateListener);
        return outerJoinView;
    }

    public void testEventType()
    {
        EPStatement outerJoinView = setupStatement("");
        EventType type = outerJoinView.getEventType();
        assertEquals(SupportBean_S0.class, type.getPropertyType("s0"));
        assertEquals(SupportBean_S1.class, type.getPropertyType("s1"));
    }
    
    private void tryWhereNotNull()
    {
        SupportBean_S1 s1_1 = new SupportBean_S1(1000, "5", "X");
        SupportBean_S1 s1_2 = new SupportBean_S1(1001, "5", null);
        SupportBean_S1 s1_3 = new SupportBean_S1(1002, "6", null);
        sendEvent(new Object[] {s1_1, s1_2, s1_3});
        assertFalse(updateListener.isInvoked());

        SupportBean_S0 s0 = new SupportBean_S0(1, "5", "X");
        sendEvent(s0);
        compareEvent(updateListener.assertOneGetNewAndReset(), s0, s1_1);
    }

    private void tryWhereNull()
    {
        SupportBean_S1 s1_1 = new SupportBean_S1(1000, "5", "X");
        SupportBean_S1 s1_2 = new SupportBean_S1(1001, "5", null);
        SupportBean_S1 s1_3 = new SupportBean_S1(1002, "6", null);
        sendEvent(new Object[] {s1_1, s1_2, s1_3});
        assertFalse(updateListener.isInvoked());

        SupportBean_S0 s0 = new SupportBean_S0(1, "5", "X");
        sendEvent(s0);
        compareEvent(updateListener.assertOneGetNewAndReset(), s0, s1_2);
    }

    private void compareEvent(EventBean receivedEvent, SupportBean_S0 expectedS0, SupportBean_S1 expectedS1)
    {
        assertSame(expectedS0, receivedEvent.get("s0"));
        assertSame(expectedS1, receivedEvent.get("s1"));
    }

    private void sendEvent(Object[] events)
    {
        for (int i = 0; i < events.length; i++)
        {
            sendEvent(events[i]);
        }
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
