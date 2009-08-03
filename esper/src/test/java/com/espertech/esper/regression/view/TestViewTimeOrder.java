package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBeanTimestamp;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestViewTimeOrder extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration configuration = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testTimeOrderRemoveStream()
    {
        sendTimer(1000);
        epService.getEPAdministrator().createEPL(
                "insert rstream into OrderedStream select rstream * from " + SupportBeanTimestamp.class.getName() +
                ".ext:time_order(timestamp, 10 sec)");

        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(
                "select * from OrderedStream");
        stmtTwo.addListener(listener);

        // 1st event at 21 sec
        sendTimer(21000);
        sendEvent("E1", 21000);

        // 2nd event at 22 sec
        sendTimer(22000);
        sendEvent("E2", 22000);

        // 3nd event at 28 sec
        sendTimer(28000);
        sendEvent("E3", 28000);

        // 4th event at 30 sec, however is 27 sec (old 3 sec)
        sendTimer(30000);
        sendEvent("E4", 27000);

        // 5th event at 30 sec, however is 22 sec (old 8 sec)
        sendEvent("E5", 22000);

        // flush one
        sendTimer(30999);
        assertFalse(listener.isInvoked());

        sendTimer(31000);
        assertEquals(1, listener.getLastNewData().length);
        assertEquals("E1", listener.getLastNewData()[0].get("id"));
        listener.reset();

        // 6th event at 31 sec, however is 21 sec (old 10 sec)
        sendEvent("E6", 21000);
        assertEquals(1, listener.getLastNewData().length);
        assertEquals("E6", listener.getLastNewData()[0].get("id"));
        listener.reset();

        // 7th event at 31 sec, however is 21.3 sec (old 9.7 sec)
        sendEvent("E7", 21300);

        // flush one
        sendTimer(31299);
        assertFalse(listener.isInvoked());
        sendTimer(31300);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals("E7", listener.getLastNewData()[0].get("id"));
        listener.reset();

        // flush two
        sendTimer(31999);
        assertFalse(listener.isInvoked());
        sendTimer(32000);

        EventBean[] result = listener.getNewDataListFlattened();
        assertEquals(2, result.length);
        assertEquals("E2", result[0].get("id"));
        assertEquals("E5", result[1].get("id"));
        listener.reset();

        // flush one
        sendTimer(36999);
        assertFalse(listener.isInvoked());
        sendTimer(37000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals("E4", listener.getLastNewData()[0].get("id"));
        listener.reset();

        // rather old event
        sendEvent("E8", 21000);
        assertEquals(1, listener.getLastNewData().length);
        assertEquals("E8", listener.getLastNewData()[0].get("id"));
        listener.reset();

        // 9-second old event for posting at 38 sec
        sendEvent("E9", 28000);

        // flush two
        sendTimer(37999);
        assertFalse(listener.isInvoked());
        sendTimer(38000);
        result = listener.getNewDataListFlattened();
        assertEquals(2, result.length);
        assertEquals("E3", result[0].get("id"));
        assertEquals("E9", result[1].get("id"));
        listener.reset();
    }

    public void testTimeOrder()
    {
        sendTimer(1000);
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportBeanTimestamp.class.getName() +
                ".ext:time_order(timestamp, 10 sec)");
        stmt.addListener(listener);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, null);

        sendTimer(21000);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, null);

        // 1st event at 21 sec
        sendEvent("E1", 21000);
        assertEquals("E1", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E1"}});

        // 2nd event at 22 sec
        sendTimer(22000);
        sendEvent("E2", 22000);
        assertEquals("E2", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E1"}, {"E2"}});

        // 3nd event at 28 sec
        sendTimer(28000);
        sendEvent("E3", 28000);
        assertEquals("E3", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E1"}, {"E2"}, {"E3"}});

        // 4th event at 30 sec, however is 27 sec (old 3 sec)
        sendTimer(30000);
        sendEvent("E4", 27000);
        assertEquals("E4", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E1"}, {"E2"}, {"E4"}, {"E3"}});

        // 5th event at 30 sec, however is 22 sec (old 8 sec)
        sendEvent("E5", 22000);
        assertEquals("E5", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E1"}, {"E2"}, {"E5"}, {"E4"}, {"E3"}});

        // flush one
        sendTimer(30999);
        assertFalse(listener.isInvoked());
        sendTimer(31000);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E1", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E2"}, {"E5"}, {"E4"}, {"E3"}});

        // 6th event at 31 sec, however is 21 sec (old 10 sec)
        sendEvent("E6", 21000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals("E6", listener.getLastNewData()[0].get("id"));
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E6", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E2"}, {"E5"}, {"E4"}, {"E3"}});

        // 7th event at 31 sec, however is 21.3 sec (old 9.7 sec)
        sendEvent("E7", 21300);
        assertEquals("E7", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E7"}, {"E2"}, {"E5"}, {"E4"}, {"E3"}});

        // flush one
        sendTimer(31299);
        assertFalse(listener.isInvoked());
        sendTimer(31300);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E7", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E2"}, {"E5"}, {"E4"}, {"E3"}});

        // flush two
        sendTimer(31999);
        assertFalse(listener.isInvoked());
        sendTimer(32000);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(2, listener.getLastOldData().length);
        assertEquals("E2", listener.getLastOldData()[0].get("id"));
        assertEquals("E5", listener.getLastOldData()[1].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E4"}, {"E3"}});

        // flush one
        sendTimer(36999);
        assertFalse(listener.isInvoked());
        sendTimer(37000);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E4", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E3"}});

        // rather old event
        sendEvent("E8", 21000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals("E8", listener.getLastNewData()[0].get("id"));
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E8", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E3"}});

        // 9-second old event for posting at 38 sec
        sendEvent("E9", 28000);
        assertEquals("E9", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E3"}, {"E9"}});

        // flush two
        sendTimer(37999);
        assertFalse(listener.isInvoked());
        sendTimer(38000);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(2, listener.getLastOldData().length);
        assertEquals("E3", listener.getLastOldData()[0].get("id"));
        assertEquals("E9", listener.getLastOldData()[1].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, null);

        // new event
        sendEvent("E10", 38000);
        assertEquals("E10", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E10"}});

        // flush last
        sendTimer(47999);
        assertFalse(listener.isInvoked());
        sendTimer(48000);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E10", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, null);

        // last, in the future
        sendEvent("E11", 70000);
        assertEquals("E11", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E11"}});

        sendTimer(80000);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E11", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, null);

        sendTimer(100000);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, null);
    }

    public void testGroupedWindow()
    {
        sendTimer(20000);
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportBeanTimestamp.class.getName() +
                ".std:groupby(groupId).ext:time_order(timestamp, 10 sec)");
        stmt.addListener(listener);

        // 1st event is old
        sendEvent("E1", "G1", 10000);
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals("E1", listener.getLastNewData()[0].get("id"));
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E1", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, null);

        // 2nd just fits
        sendEvent("E2", "G2", 10001);
        assertEquals("E2", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E2"}});

        sendEvent("E3", "G3", 20000);
        assertEquals("E3", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E2"}, {"E3"}});

        sendEvent("E4", "G2", 20000);
        assertEquals("E4", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E2"}, {"E4"}, {"E3"}});

        sendTimer(20001);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E2", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E4"}, {"E3"}});

        sendTimer(22000);
        sendEvent("E5", "G2", 19000);
        assertEquals("E5", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E5"}, {"E4"}, {"E3"}});

        sendTimer(29000);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(1, listener.getLastOldData().length);
        assertEquals("E5", listener.getLastOldData()[0].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E4"}, {"E3"}});

        sendTimer(30000);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(2, listener.getLastOldData().length);
        assertEquals("E4", listener.getLastOldData()[0].get("id"));
        assertEquals("E3", listener.getLastOldData()[1].get("id"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, null);

        sendTimer(100000);
        assertFalse(listener.isInvoked());
    }

    public void testInvalid()
    {
        assertEquals("Error starting statement: Error attaching view to event stream: Invalid parameter expression 0: Property named 'bump' is not valid in any stream [select * from com.espertech.esper.support.bean.SupportBeanTimestamp.ext:time_order(bump, 10 sec)]",
                    tryInvalid("select * from " + SupportBeanTimestamp.class.getName() + ".ext:time_order(bump, 10 sec)"));

        assertEquals("Error starting statement: Error attaching view to event stream: Time order view requires the expression supplying timestamp values, and a numeric or time period parameter for interval size [select * from com.espertech.esper.support.bean.SupportBeanTimestamp.ext:time_order(10 sec)]",
                    tryInvalid("select * from " + SupportBeanTimestamp.class.getName() + ".ext:time_order(10 sec)"));

        assertEquals("Error starting statement: Error attaching view to event stream: Invalid parameter expression 1: Property named 'abc' is not valid in any stream (did you mean 'id'?) [select * from com.espertech.esper.support.bean.SupportBeanTimestamp.ext:time_order(timestamp, abc)]",
                    tryInvalid("select * from " + SupportBeanTimestamp.class.getName() + ".ext:time_order(timestamp, abc)"));
    }

    private String tryInvalid(String stmtText)
    {
        try
        {
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            return ex.getMessage();
        }
        return null;
    }

    public void testPreviousAndPrior()
    {
        sendTimer(1000);
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select irstream id, " +
                " prev(0, id) as prevIdZero, " +
                " prev(1, id) as prevIdOne, " +
                " prior(1, id) as priorIdOne from " + SupportBeanTimestamp.class.getName() +
                ".ext:time_order(timestamp, 10 sec)");
        stmt.addListener(listener);

        sendTimer(20000);
        sendEvent("E1", 25000);
        assertEquals("E1", listener.assertOneGetNewAndReset().get("id"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id"}, new Object[][] {{"E1"}});

        sendEvent("E2", 21000);
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("E2", event.get("id"));
        assertEquals("E2", event.get("prevIdZero"));
        assertEquals("E1", event.get("prevIdOne"));
        assertEquals("E1", event.get("priorIdOne"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id", "prevIdZero", "prevIdOne", "priorIdOne"},
                new Object[][] {{"E2", "E2", "E1", "E1"}, {"E1", "E2", "E1", null}});

        sendEvent("E3", 22000);
        event = listener.assertOneGetNewAndReset();
        assertEquals("E3", event.get("id"));
        assertEquals("E2", event.get("prevIdZero"));
        assertEquals("E3", event.get("prevIdOne"));
        assertEquals("E2", event.get("priorIdOne"));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id", "prevIdZero", "prevIdOne", "priorIdOne"},
                new Object[][] {{"E2", "E2", "E3", "E1"}, {"E3", "E2", "E3", "E2"}, {"E1", "E2", "E3", null}});

        sendTimer(31000);
        assertNull(listener.getLastNewData());
        assertEquals(1, listener.getOldDataList().size());
        assertEquals(1, listener.getLastOldData().length);
        event = listener.getLastOldData()[0];
        assertEquals("E2", event.get("id"));
        assertEquals(null, event.get("prevIdZero"));
        assertEquals(null, event.get("prevIdOne"));
        assertEquals("E1", event.get("priorIdOne"));
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), new String[] {"id", "prevIdZero", "prevIdOne", "priorIdOne"},
                new Object[][] {{"E3", "E3", "E1", "E2"}, {"E1", "E3", "E1", null}});
    }

    private SupportBeanTimestamp sendEvent(String id, String groupId, long timestamp)
    {
        SupportBeanTimestamp event = new SupportBeanTimestamp(id, groupId, timestamp);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
        return event;
    }

    private SupportBeanTimestamp sendEvent(String id, long timestamp)
    {
        SupportBeanTimestamp event = new SupportBeanTimestamp(id, timestamp);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
        return event;
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }
}
