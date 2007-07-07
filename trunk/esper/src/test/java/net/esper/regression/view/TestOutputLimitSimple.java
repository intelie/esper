package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPRuntime;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

public class TestOutputLimitSimple extends TestCase
{
    private final static String JOIN_KEY = "KEY";

	private EPServiceProvider epService;
    private long currentTime;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testLimitEventJoin()
	{
		String eventName1 = SupportBean.class.getName();
		String eventName2 = SupportBean_A.class.getName();
		String joinStatement =
			"select * from " +
				eventName1 + ".win:length(5) as event1," +
				eventName2 + ".win:length(5) as event2" +
			" where event1.string = event2.id";
		String outputStmt1 = joinStatement + " output every 1 events";
	   	String outputStmt3 = joinStatement + " output every 3 events";

	   	EPStatement fireEvery1 = epService.getEPAdministrator().createEQL(outputStmt1);
		EPStatement fireEvery3 = epService.getEPAdministrator().createEQL(outputStmt3);

	   	SupportUpdateListener updateListener1 = new SupportUpdateListener();
		fireEvery1.addListener(updateListener1);
		SupportUpdateListener updateListener3 = new SupportUpdateListener();
		fireEvery3.addListener(updateListener3);

		// send event 1
		sendJoinEvents("s1");

		assertTrue(updateListener1.getAndClearIsInvoked());
		assertEquals(1, updateListener1.getLastNewData().length);
		assertNull(updateListener1.getLastOldData());

		assertFalse(updateListener3.getAndClearIsInvoked());
		assertNull(updateListener3.getLastNewData());
		assertNull(updateListener3.getLastOldData());

		// send event 2
		sendJoinEvents("s2");

		assertTrue(updateListener1.getAndClearIsInvoked());
		assertEquals(1, updateListener1.getLastNewData().length);
		assertNull(updateListener1.getLastOldData());

	   	assertFalse(updateListener3.getAndClearIsInvoked());
		assertNull(updateListener3.getLastNewData());
		assertNull(updateListener3.getLastOldData());

		// send event 3
		sendJoinEvents("s3");

		assertTrue(updateListener1.getAndClearIsInvoked());
		assertEquals(1, updateListener1.getLastNewData().length);
		assertNull(updateListener1.getLastOldData());

		assertTrue(updateListener3.getAndClearIsInvoked());
		assertEquals(3, updateListener3.getLastNewData().length);
		assertNull(updateListener3.getLastOldData());
	}

    public void testLimitTime(){
    	String eventName = SupportBean.class.getName();
    	String selectStatement = "select * from " + eventName + ".win:length(5)";

    	// test integer seconds
    	String statementString1 = selectStatement +
    		" output every 3 seconds";
    	timeCallback(statementString1, 3000);

    	// test fractional seconds
    	String statementString2 = selectStatement +
    	" output every 3.3 seconds";
    	timeCallback(statementString2, 3300);

    	// test integer minutes
    	String statementString3 = selectStatement +
    	" output every 2 minutes";
    	timeCallback(statementString3, 120000);

    	// test fractional minutes
    	String statementString4 =
    		"select * from " +
    			eventName + ".win:length(5)" +
    		" output every .05 minutes";
    	timeCallback(statementString4, 3000);
    }
    
    public void testTimeBatchOutputEvents()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        String stmtText = "select * from " + SupportBean.class.getName() + ".win:time_batch(10 seconds) output every 10 seconds";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        sendTimer(0);
        sendTimer(10000);
        assertFalse(listener.isInvoked());
        sendTimer(20000);
        assertFalse(listener.isInvoked());

        sendEvent("e1");
        sendTimer(30000);
        assertFalse(listener.isInvoked());
        sendTimer(40000);
        EventBean[] newEvents = listener.getAndResetLastNewData();
        assertEquals(1, newEvents.length);
        assertEquals("e1", newEvents[0].get("string"));
        listener.reset();

        sendTimer(50000);
        assertTrue(listener.isInvoked());
        listener.reset();

        sendTimer(60000);
        assertTrue(listener.isInvoked());
        listener.reset();

        sendTimer(70000);
        assertTrue(listener.isInvoked());
        listener.reset();

        sendEvent("e2");
        sendEvent("e3");
        sendTimer(80000);
        newEvents = listener.getAndResetLastNewData();
        assertEquals(2, newEvents.length);
        assertEquals("e2", newEvents[0].get("string"));
        assertEquals("e3", newEvents[1].get("string"));

        sendTimer(90000);
        assertTrue(listener.isInvoked());
        listener.reset();
    }

    public void testSimpleNoJoinAll()
	{
	    String viewExpr = "select longBoxed " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "output all every 2 events";

	    runAssertAll(createStmtAndListenerNoJoin(viewExpr));

	    viewExpr = "select longBoxed " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "output every 2 events";

	    runAssertAll(createStmtAndListenerNoJoin(viewExpr));

	    viewExpr = "select * " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "output every 2 events";

	    runAssertAll(createStmtAndListenerNoJoin(viewExpr));
	}

	public void testSimpleNoJoinLast()
    {
        String viewExpr = "select longBoxed " +
        "from " + SupportBean.class.getName() + ".win:length(3) " +
        "output last every 2 events";

        runAssertLast(createStmtAndListenerNoJoin(viewExpr));

        viewExpr = "select * " +
        "from " + SupportBean.class.getName() + ".win:length(3) " +
        "output last every 2 events";

        runAssertLast(createStmtAndListenerNoJoin(viewExpr));
    }

    public void testSimpleJoinAll()
	{
	    String viewExpr = "select longBoxed  " +
	    "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
	    SupportBean.class.getName() + ".win:length(3) as two " +
	    "output all every 2 events";

		runAssertAll(createStmtAndListenerJoin(viewExpr));
	}

    private SupportUpdateListener createStmtAndListenerNoJoin(String viewExpr) {
		epService.initialize();
		SupportUpdateListener updateListener = new SupportUpdateListener();
		EPStatement view = epService.getEPAdministrator().createEQL(viewExpr);
	    view.addListener(updateListener);

	    return updateListener;
	}

	private void runAssertAll(SupportUpdateListener updateListener)
	{
		// send an event
	    sendEvent(1);

	    // check no update
	    assertFalse(updateListener.getAndClearIsInvoked());

	    // send another event
	    sendEvent(2);

	    // check update, all events present
	    assertTrue(updateListener.getAndClearIsInvoked());
	    assertEquals(2, updateListener.getLastNewData().length);
	    assertEquals(1L, updateListener.getLastNewData()[0].get("longBoxed"));
	    assertEquals(2L, updateListener.getLastNewData()[1].get("longBoxed"));
	    assertNull(updateListener.getLastOldData());
	}

    private void sendEvent(long longBoxed, int intBoxed, short shortBoxed)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(JOIN_KEY);
	    bean.setLongBoxed(longBoxed);
	    bean.setIntBoxed(intBoxed);
	    bean.setShortBoxed(shortBoxed);
	    epService.getEPRuntime().sendEvent(bean);
	}

	private void sendEvent(long longBoxed)
	{
	    sendEvent(longBoxed, 0, (short)0);
	}

	public void testSimpleJoinLast()
	{
	    String viewExpr = "select longBoxed " +
	    "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
	    SupportBean.class.getName() + ".win:length(3) as two " +
	    "output last every 2 events";

		runAssertLast(createStmtAndListenerJoin(viewExpr));
	}

    public void testLimitEventSimple()
    {
        SupportUpdateListener updateListener1 = new SupportUpdateListener();
        SupportUpdateListener updateListener2 = new SupportUpdateListener();
        SupportUpdateListener updateListener3 = new SupportUpdateListener();

        String eventName = SupportBean.class.getName();
        String selectStmt = "select * from " + eventName + ".win:length(5)";
        String statement1 = selectStmt +
            " output every 1 events";
        String statement2 = selectStmt +
            " output every 2 events";
        String statement3 = selectStmt +
            " output every 3 events";

        EPStatement rateLimitStmt1 = epService.getEPAdministrator().createEQL(statement1);
        rateLimitStmt1.addListener(updateListener1);
        EPStatement rateLimitStmt2 = epService.getEPAdministrator().createEQL(statement2);
        rateLimitStmt2.addListener(updateListener2);
        EPStatement rateLimitStmt3 = epService.getEPAdministrator().createEQL(statement3);
        rateLimitStmt3.addListener(updateListener3);

        // send event 1
        sendEvent("s1");

        assertTrue(updateListener1.getAndClearIsInvoked());
        assertEquals(1,updateListener1.getLastNewData().length);
        assertNull(updateListener1.getLastOldData());

        assertFalse(updateListener2.getAndClearIsInvoked());
        assertNull(updateListener2.getLastNewData());
        assertNull(updateListener2.getLastOldData());

        assertFalse(updateListener3.getAndClearIsInvoked());
        assertNull(updateListener3.getLastNewData());
        assertNull(updateListener3.getLastOldData());

        // send event 2
        sendEvent("s2");

        assertTrue(updateListener1.getAndClearIsInvoked());
        assertEquals(1,updateListener1.getLastNewData().length);
        assertNull(updateListener1.getLastOldData());

        assertTrue(updateListener2.getAndClearIsInvoked());
        assertEquals(2,updateListener2.getLastNewData().length);
        assertNull(updateListener2.getLastOldData());

        assertFalse(updateListener3.getAndClearIsInvoked());

        // send event 3
        sendEvent("s3");

        assertTrue(updateListener1.getAndClearIsInvoked());
        assertEquals(1,updateListener1.getLastNewData().length);
        assertNull(updateListener1.getLastOldData());

        assertFalse(updateListener2.getAndClearIsInvoked());

        assertTrue(updateListener3.getAndClearIsInvoked());
        assertEquals(3,updateListener3.getLastNewData().length);
        assertNull(updateListener3.getLastOldData());
    }    

    private SupportUpdateListener createStmtAndListenerJoin(String viewExpr) {
		epService.initialize();

		SupportUpdateListener updateListener = new SupportUpdateListener();
		EPStatement view = epService.getEPAdministrator().createEQL(viewExpr);
	    view.addListener(updateListener);

	    epService.getEPRuntime().sendEvent(new SupportBeanString(JOIN_KEY));

	    return updateListener;
	}

	private void runAssertLast(SupportUpdateListener updateListener)
	{
		// send an event
	    sendEvent(1);

	    // check no update
	    assertFalse(updateListener.getAndClearIsInvoked());

	    // send another event
	    sendEvent(2);

	    // check update, only the last event present
	    assertTrue(updateListener.getAndClearIsInvoked());
	    assertEquals(1, updateListener.getLastNewData().length);
	    assertEquals(2L, updateListener.getLastNewData()[0].get("longBoxed"));
	    assertNull(updateListener.getLastOldData());
	}

    private void sendTimer(long time)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void sendEvent(String s)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(s);
	    bean.setDoubleBoxed(0.0);
	    bean.setIntPrimitive(0);
	    bean.setIntBoxed(0);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void timeCallback(String statementString, int timeToCallback) {
    	// clear any old events
        epService.initialize();

    	// turn off external clocking
    	epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

    	// set the clock to 0
    	currentTime = 0;
    	sendTimeEvent(0);

    	// create the eql statement and add a listener
    	EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
    	SupportUpdateListener updateListener = new SupportUpdateListener();
    	statement.addListener(updateListener);
    	updateListener.reset();

    	// send an event
    	sendEvent("s1");

    	// check that the listener hasn't been updated
    	assertFalse(updateListener.getAndClearIsInvoked());

    	// update the clock
    	sendTimeEvent(timeToCallback);

    	// check that the listener has been updated
    	assertTrue(updateListener.getAndClearIsInvoked());
    	assertEquals(1, updateListener.getLastNewData().length);
    	assertNull(updateListener.getLastOldData());

    	// send another event
    	sendEvent("s2");

    	// check that the listener hasn't been updated
    	assertFalse(updateListener.getAndClearIsInvoked());

    	// update the clock
    	sendTimeEvent(timeToCallback);

    	// check that the listener has been updated
    	assertTrue(updateListener.getAndClearIsInvoked());
    	assertEquals(1, updateListener.getLastNewData().length);
    	assertNull(updateListener.getLastOldData());

    	// don't send an event
    	// check that the listener hasn't been updated
    	assertFalse(updateListener.getAndClearIsInvoked());

    	// update the clock
    	sendTimeEvent(timeToCallback);

    	// check that the listener has been updated
    	assertTrue(updateListener.getAndClearIsInvoked());
    	assertNull(updateListener.getLastNewData());
    	assertNull(updateListener.getLastOldData());

    	// don't send an event
    	// check that the listener hasn't been updated
    	assertFalse(updateListener.getAndClearIsInvoked());

    	// update the clock
    	sendTimeEvent(timeToCallback);

    	// check that the listener has been updated
    	assertTrue(updateListener.getAndClearIsInvoked());
    	assertNull(updateListener.getLastNewData());
    	assertNull(updateListener.getLastOldData());

    	// send several events
    	sendEvent("s3");
    	sendEvent("s4");
    	sendEvent("s5");

    	// check that the listener hasn't been updated
    	assertFalse(updateListener.getAndClearIsInvoked());

    	// update the clock
    	sendTimeEvent(timeToCallback);

    	// check that the listener has been updated
    	assertTrue(updateListener.getAndClearIsInvoked());
    	assertEquals(3, updateListener.getLastNewData().length);
    	assertNull(updateListener.getLastOldData());
    }

    private void sendTimeEvent(int timeIncrement){
    	currentTime += timeIncrement;
        CurrentTimeEvent event = new CurrentTimeEvent(currentTime);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendJoinEvents(String s)
	{
	    SupportBean event1 = new SupportBean();
	    event1.setString(s);
	    event1.setDoubleBoxed(0.0);
	    event1.setIntPrimitive(0);
	    event1.setIntBoxed(0);


	    SupportBean_A event2 = new SupportBean_A(s);

	    epService.getEPRuntime().sendEvent(event1);
	    epService.getEPRuntime().sendEvent(event2);
	}    
}
