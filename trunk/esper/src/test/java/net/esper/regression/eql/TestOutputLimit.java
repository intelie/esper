package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOutputLimit extends TestCase
{
    private EPServiceProvider epService;
    private long currentTime;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
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
    
    public void testWithGroupBy()
    {
    	String eventName = SupportMarketDataBean.class.getName();  
    	String statementString = "select symbol, sum(price) from " + eventName + ".win:length(5) group by symbol output every 5 events";
    	EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
    	SupportUpdateListener updateListener = new SupportUpdateListener();
    	statement.addListener(updateListener);
    	
    	// send some events and check that only the most recent  
    	// ones are kept
    	sendMarketEvent("IBM", 1D);
    	sendMarketEvent("IBM", 2D);
    	sendMarketEvent("HP", 1D);
    	sendMarketEvent("IBM", 3D);
    	sendMarketEvent("MAC", 1D);
    	
    	assertTrue(updateListener.getAndClearIsInvoked());
    	EventBean[] newData = updateListener.getLastNewData();
    	assertEquals(3, newData.length);
    	assertSingleInstance(newData, "IBM");
    	assertSingleInstance(newData, "HP");
    	assertSingleInstance(newData, "MAC");
    	EventBean[] oldData = updateListener.getLastOldData();
    	assertSingleInstance(oldData, "IBM");
    	assertSingleInstance(oldData, "HP");
    	assertSingleInstance(oldData, "MAC");
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

	private void assertSingleInstance(EventBean[] data, String symbol)
    {
    	int instanceCount = 0;
    	for(EventBean event : data)
    	{
    		if(event.get("symbol").equals(symbol))
    		{
    			instanceCount++;
    		}
    	}
    	assertEquals(1, instanceCount);
    }
    
    private void sendTimeEvent(int timeIncrement){
    	currentTime += timeIncrement;
        CurrentTimeEvent event = new CurrentTimeEvent(currentTime);
        epService.getEPRuntime().sendEvent(event);                
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
    
    private void sendEvent(String s)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(s);
	    bean.setDoubleBoxed(0.0);
	    bean.setIntPrimitive(0);
	    bean.setIntBoxed(0);
	    epService.getEPRuntime().sendEvent(bean);
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

    private void sendMarketEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

	private static Log log = LogFactory.getLog(TestOutputLimit.class);
}


