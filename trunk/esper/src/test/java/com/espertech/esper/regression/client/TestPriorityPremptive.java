package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestPriorityPremptive extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerTwo;
    private SupportUpdateListener[] listeners;

    // TODO
    // test same filter: reuse via shared views
    // test named window order
    // test scheduling priority
    // test priority alone for listener delivery
    // test preemptive alone for same filters: one receives it
    // test statement rewrite
    
    public void setUp()
    {
        listener = new SupportUpdateListener();
        listenerTwo = new SupportUpdateListener();

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.getEngineDefaults().getExecution().setPrioritized(true);     // also sets share-views to false

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        listeners = new SupportUpdateListener[10];
        for (int i = 0; i < listeners.length; i++)
        {
            listeners[i] = new SupportUpdateListener();
        }
    }

    public void testNamedWindow()
    {
        String stmtText;
        stmtText = "create window MyWindow.std:lastevent() as select * from SupportBean";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "insert into MyWindow select * from SupportBean";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "on MyWindow e delete from MyWindow win where win.string=e.string and e.intPrimitive = 7";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "on MyWindow e delete from MyWindow win where win.string=e.string and e.intPrimitive = 5";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "on MyWindow e insert into ResultStream select e.* from MyWindow";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "select * from ResultStream";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 7));
        assertFalse("E1", listener.isInvoked());
    }

    public void testPriority()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL("@Priority(1) select *, 1 as prio from SupportBean");
        stmt.addListener(listener);

        stmt = epService.getEPAdministrator().createEPL("@Priority(3) select *, 3 as prio from SupportBean");
        stmt.addListener(listener);

        stmt = epService.getEPAdministrator().createEPL("@Priority(2) select *, 2 as prio from SupportBean");
        stmt.addListener(listener);
        
        stmt = epService.getEPAdministrator().createEPL("@Priority(4) select *, 4 as prio from SupportBean");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        EventBean[] events = listener.getNewDataListFlattened();
        assertEquals(4, events.length);
        assertEquals(4, events[0].get("prio"));
        assertEquals(3, events[1].get("prio"));
        assertEquals(2, events[2].get("prio"));
        assertEquals(1, events[3].get("prio"));
    }

    public void testAddRemoveStmts()
    {
        String stmtSelectText = "insert into ABCStream select * from SupportBean";
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL(stmtSelectText);
        stmtSelect.addListener(listener);

        String stmtOneText = "@Priority(1) @Preemptive select * from SupportBean where intPrimitive = 1";
        EPStatement statementOne = epService.getEPAdministrator().createEPL(stmtOneText);
        statementOne.addListener(listeners[0]);

        String stmtTwoText = "@Priority(1) @Preemptive select * from SupportBean where intPrimitive = 2";
        EPStatement statementTwo = epService.getEPAdministrator().createEPL(stmtTwoText);
        statementTwo.addListener(listeners[1]);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertFalse(listener.isInvoked());
        assertReceivedSingle(0, "E1");

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        assertFalse(listener.isInvoked());
        assertReceivedSingle(1, "E2");

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 1));
        assertFalse(listener.isInvoked());
        assertReceivedSingle(0, "E3");

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 3));
        assertEquals("E4", listener.assertOneGetNewAndReset().get("string"));
        assertReceivedNone();

        String stmtThreeText = "@Priority(1) @Preemptive select * from SupportBean where intPrimitive = 3";
        EPStatement statementThree = epService.getEPAdministrator().createEPL(stmtThreeText);
        statementThree.addListener(listeners[2]);

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 3));
        assertFalse(listener.isInvoked());
        assertReceivedSingle(2, "E5");

        epService.getEPRuntime().sendEvent(new SupportBean("E6", 1));
        assertFalse(listener.isInvoked());
        assertReceivedSingle(0, "E6");
        
        statementOne.destroy();
        epService.getEPRuntime().sendEvent(new SupportBean("E7", 1));
        assertEquals("E7", listener.assertOneGetNewAndReset().get("string"));
        assertReceivedNone();

        String stmtSelectTextTwo = "@Priority(50) select * from SupportBean";
        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEPL(stmtSelectTextTwo);
        stmtSelectTwo.addListener(listenerTwo);

        epService.getEPRuntime().sendEvent(new SupportBean("E8", 1));
        assertEquals("E8", listener.assertOneGetNewAndReset().get("string"));
        assertEquals("E8", listenerTwo.assertOneGetNewAndReset().get("string"));
        assertReceivedNone();

        epService.getEPRuntime().sendEvent(new SupportBean("E9", 2));
        assertFalse(listener.isInvoked());
        assertReceivedSingle(1, "E9");
    }

    private void assertReceivedEach(String[] stringValue)
    {
        for (int i = 0; i < stringValue.length; i++)
        {
            if (stringValue[i] != null)
            {
                assertEquals(stringValue[i], listeners[i].assertOneGetNewAndReset().get("string"));
            }
            else
            {
                assertFalse(listeners[i].isInvoked());
            }
        }
    }

    private void assertReceivedSingle(int index, String stringValue)
    {
        for (int i = 0; i < listeners.length; i++)
        {
            if (i == index)
            {
                continue;
            }
            assertFalse(listeners[i].isInvoked());
        }
        assertEquals(stringValue, listeners[index].assertOneGetNewAndReset().get("string"));
    }

    private void assertReceivedNone()
    {
        for (int i = 0; i < listeners.length; i++)
        {
            assertFalse(listeners[i].isInvoked());
        }
    }
}
