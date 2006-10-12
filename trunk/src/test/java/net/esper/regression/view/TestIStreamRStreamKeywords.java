package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestIStreamRStreamKeywords extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private SupportUpdateListener testListenerInsertInto;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        testListenerInsertInto = new SupportUpdateListener();

        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testRStreamOnly()
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "select rstream * from " + SupportBean.class.getName() + ".win:length(3)");
        statement.addListener(testListener);

        Object event = sendEvent("a");
        assertFalse(testListener.isInvoked());

        sendEvents(new String[] {"a", "b"});
        assertFalse(testListener.isInvoked());

        sendEvent("d");
        assertSame(event, testListener.getLastNewData()[0].getUnderlying());    // receive 'a' as new data
        assertNull(testListener.getLastOldData());  // receive no more old data
        testListener.reset();
    }

    public void testRStreamInsertInto()
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "insert into NextStream " +
                "select rstream s0.string as string from " + SupportBean.class.getName() + ".win:length(3) as s0");
        statement.addListener(testListener);

        statement = epService.getEPAdministrator().createEQL("select * from NextStream");
        statement.addListener(testListenerInsertInto);

        sendEvent("a");
        assertFalse(testListener.isInvoked());
        assertEquals("a", testListenerInsertInto.assertOneGetNewAndReset().get("string"));    // insert into unchanged

        sendEvents(new String[] {"b", "c"});
        assertFalse(testListener.isInvoked());
        assertEquals(2, testListenerInsertInto.getNewDataList().size());    // insert into unchanged
        testListenerInsertInto.reset();

        sendEvent("d");
        assertSame("a", testListener.getLastNewData()[0].get("string"));    // receive 'a' as new data
        assertNull(testListener.getLastOldData());  // receive no more old data
        assertEquals("d", testListenerInsertInto.getLastNewData()[0].get("string"));    // insert into unchanged
        assertNull(testListenerInsertInto.getLastOldData());  // receive no old data in insert into
        testListener.reset();
    }

    public void testRStreamInsertIntoRStream()
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "insert rstream into NextStream " +
                "select rstream s0.string as string from " + SupportBean.class.getName() + ".win:length(3) as s0");
        statement.addListener(testListener);

        statement = epService.getEPAdministrator().createEQL("select * from NextStream");
        statement.addListener(testListenerInsertInto);

        sendEvent("a");
        assertFalse(testListener.isInvoked());
        assertFalse(testListenerInsertInto.isInvoked());

        sendEvents(new String[] {"b", "c"});
        assertFalse(testListener.isInvoked());
        assertFalse(testListenerInsertInto.isInvoked());

        sendEvent("d");
        assertSame("a", testListener.getLastNewData()[0].get("string"));    // receive 'a' as new data
        assertNull(testListener.getLastOldData());  // receive no more old data
        assertEquals("a", testListenerInsertInto.getLastNewData()[0].get("string"));    // insert into unchanged
        assertNull(testListener.getLastOldData());  // receive no old data in insert into
        testListener.reset();
    }

    public void testRStreamJoin()
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "select rstream s1.intPrimitive as aID, s2.intPrimitive as bID " +
                "from " + SupportBean.class.getName() + "(string='a').win:length(2) as s1, "
                        + SupportBean.class.getName() + "(string='b') as s2" +
                " where s1.intPrimitive = s2.intPrimitive");
        statement.addListener(testListener);

        sendEvent("a", 1);
        sendEvent("b", 1);
        assertFalse(testListener.isInvoked());

        sendEvent("a", 2);
        assertFalse(testListener.isInvoked());

        sendEvent("a", 3);
        assertEquals(1, testListener.getLastNewData()[0].get("aID"));    // receive 'a' as new data
        assertEquals(1, testListener.getLastNewData()[0].get("bID"));
        assertNull(testListener.getLastOldData());  // receive no more old data
        testListener.reset();
    }

    public void testIStreamOnly()
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "select istream * from " + SupportBean.class.getName() + ".win:length(1)");
        statement.addListener(testListener);

        Object event = sendEvent("a");
        assertSame(event, testListener.assertOneGetNewAndReset().getUnderlying());

        event = sendEvent("b");
        assertSame(event, testListener.getLastNewData()[0].getUnderlying());
        assertNull(testListener.getLastOldData()); // receive no old data, just istream events
        testListener.reset();
    }

    public void testIStreamInsertIntoRStream()
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "insert rstream into NextStream " +
                "select istream a.string as string from " + SupportBean.class.getName() + ".win:length(1) as a");
        statement.addListener(testListener);

        statement = epService.getEPAdministrator().createEQL("select * from NextStream");
        statement.addListener(testListenerInsertInto);

        sendEvent("a");
        assertEquals("a", testListener.assertOneGetNewAndReset().get("string"));
        assertFalse(testListenerInsertInto.isInvoked());

        sendEvent("b");
        assertEquals("b", testListener.getLastNewData()[0].get("string"));
        assertNull(testListener.getLastOldData());
        assertEquals("a", testListenerInsertInto.getLastNewData()[0].get("string"));
        assertNull(testListenerInsertInto.getLastOldData());
    }

    public void testIStreamJoin()
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(
                "select istream s1.intPrimitive as aID, s2.intPrimitive as bID " +
                "from " + SupportBean.class.getName() + "(string='a').win:length(2) as s1, "
                        + SupportBean.class.getName() + "(string='b') as s2" +
                " where s1.intPrimitive = s2.intPrimitive");
        statement.addListener(testListener);

        sendEvent("a", 1);
        sendEvent("b", 1);
        assertEquals(1, testListener.getLastNewData()[0].get("aID"));    // receive 'a' as new data
        assertEquals(1, testListener.getLastNewData()[0].get("bID"));
        assertNull(testListener.getLastOldData());  // receive no more old data
        testListener.reset();

        sendEvent("a", 2);
        assertFalse(testListener.isInvoked());

        sendEvent("a", 3);
        assertFalse(testListener.isInvoked());
    }

    private void sendEvents(String[] stringValue)
    {
        for (int i = 0; i < stringValue.length; i++)
        {
            sendEvent(stringValue[i]);
        }
    }

    private Object sendEvent(String stringValue)
    {
        return sendEvent(stringValue, -1);
    }

    private Object sendEvent(String stringValue, int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setString(stringValue);
        event.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(event);
        return event;
    }
}
