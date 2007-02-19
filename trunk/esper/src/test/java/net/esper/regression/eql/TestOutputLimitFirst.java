package net.esper.regression.eql;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestOutputLimitFirst extends TestCase
{
	private static final String EVENT_NAME = SupportMarketDataBean.class.getName();
	private EPServiceProvider epService;
	private long currentTime;
	private SupportUpdateListener updateListener;
	
	protected void setUp()
	{
		epService = EPServiceProviderManager.getProvider("TestOutputLimitFirst");
	}
	
	public void testTime()
	{
    	// Clear any old events
        epService.initialize();
        
    	// Turn off external clocking
    	epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    	
    	// Set the clock to 0
    	currentTime = 0;
    	sendTimeEvent(0);
    	
    	// Create the eql statement and add a listener
		String statementText = "select symbol, sum(volume) from " + EVENT_NAME + ".win:length(5) output first every 3 seconds";
    	EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
    	updateListener = new SupportUpdateListener();
		statement.addListener(updateListener);
    	updateListener.reset();
    	
    	// Send the first event of the batch; should be output
    	sendEvent(10L);
    	assertEvent(10L);
    	
    	// Send another event, not the first, for aggregation
    	// update only, no output
    	sendEvent(20L);
    	assertFalse(updateListener.getAndClearIsInvoked());
    	
    	// Update time
    	sendTimeEvent(3000);
    	assertFalse(updateListener.getAndClearIsInvoked());
    	
    	// Send first event of the next batch, should be output.
    	// The aggregate value is computed over all events 
    	// received: 10 + 20 + 30 = 60
    	sendEvent(30L);
    	assertEvent(60L);
    	
    	// Send the next event of the batch, no output
    	sendEvent(40L);
    	assertFalse(updateListener.getAndClearIsInvoked());
    	
    	// Update time
    	sendTimeEvent(3000);
    	assertFalse(updateListener.getAndClearIsInvoked());
		
    	// Send first event of third batch
    	sendEvent(1L);
    	assertEvent(101L);
    	
    	// Update time
    	sendTimeEvent(3000);
    	assertFalse(updateListener.getAndClearIsInvoked());
    	
    	// Update time: no first event this batch, so a callback
    	// is made at the end of the interval
    	sendTimeEvent(3000);
    	assertTrue(updateListener.getAndClearIsInvoked());
    	assertNull(updateListener.getLastNewData());
    	assertNull(updateListener.getLastOldData());
	}
	
	public void testCount()
	{
    	// Create the eql statement and add a listener
		String statementText = "select symbol, sum(volume) from " + EVENT_NAME + ".win:length(5) output first every 3 events";
    	EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
    	updateListener = new SupportUpdateListener();
		statement.addListener(updateListener);
    	updateListener.reset();
    	
    	// Send the first event of the batch, should be output
    	sendEvent(10L);
    	assertEvent(10L);
    	
    	// Send the second event of the batch, not output, used
    	// for updating the aggregate value only
    	sendEvent(20L);
    	assertFalse(updateListener.getAndClearIsInvoked());
    	
    	// Send the third event of the batch, still not output, 
    	// but should reset the batch
    	sendEvent(30L);
    	assertFalse(updateListener.getAndClearIsInvoked());
    	
    	// First event, next batch, aggregate value should be
    	// 10 + 20 + 30 + 40 = 100
    	sendEvent(40L);
    	assertEvent(100L);
    	
    	// Next event again not output
    	sendEvent(50L);
    	assertFalse(updateListener.getAndClearIsInvoked());
	}
	
	private void sendTimeEvent(int timeIncrement){
		currentTime += timeIncrement;
	    CurrentTimeEvent event = new CurrentTimeEvent(currentTime);
	    epService.getEPRuntime().sendEvent(event);                
	}

	private void assertEvent(long volume)
	{
		assertTrue(updateListener.getAndClearIsInvoked());
    	assertTrue(updateListener.getLastNewData() != null);
    	assertEquals(1, updateListener.getLastNewData().length);
    	assertEquals(volume, updateListener.getLastNewData()[0].get("sum(volume)"));
	}
	
	private void sendEvent(long volume)
	{
		epService.getEPRuntime().sendEvent(new SupportMarketDataBean("DELL", 0.0, volume, null));
	}
}	
