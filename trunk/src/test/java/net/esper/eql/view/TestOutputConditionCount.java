package net.esper.eql.view;

import junit.framework.TestCase;
import net.esper.support.util.SupportUpdateListener;

public class TestOutputConditionCount extends TestCase
{

	private OutputConditionCount fireEvery1;	
	private OutputConditionCount fireEvery2;	
	private OutputConditionCount fireEvery3;	
    private SupportUpdateListener listener1;
    private SupportUpdateListener listener2;
    private SupportUpdateListener listener3;
	private OutputCallback callback1;
	private OutputCallback callback2;
	private OutputCallback callback3;    

    public void setUp()
    { 
    	listener1 = new SupportUpdateListener();
    	listener3 = new SupportUpdateListener();
    	listener2 = new SupportUpdateListener();
    	
    	callback1 = new OutputCallback() {
		    		public void continueOutputProcessing(boolean forceUpdate)
		    		{
		    			listener1.update(null, null);
		    		}
		    	};
		callback2 = new OutputCallback() {
		    		public void continueOutputProcessing(boolean forceUpdate)
		    		{
		    			listener2.update(null, null);
		    		}
		    	};
		callback3 = new OutputCallback() {
		    		public void continueOutputProcessing(boolean forceUpdate)
		    		{
		    			listener3.update(null, null);
		    		}
		    	};
		fireEvery1 = new OutputConditionCount(1, callback1);
    	fireEvery2 = new OutputConditionCount(2, callback2);
    	fireEvery3 = new OutputConditionCount(3, callback3);
    	
    }


    
    public void testUpdateCondition()
    {
    	// send 1 new event, 0 old events
    	sendEventToAll(1, 0);

    	assertTrue(listener1.getAndClearIsInvoked());
    	assertEquals(0,fireEvery1.getNewEventsCount());
    	assertEquals(0,fireEvery1.getOldEventsCount());

    	assertFalse(listener2.getAndClearIsInvoked());
    	assertEquals(1,fireEvery2.getNewEventsCount());
    	assertEquals(0,fireEvery2.getOldEventsCount());

    	assertFalse(listener3.getAndClearIsInvoked());
    	assertEquals(1,fireEvery3.getNewEventsCount());
    	assertEquals(0,fireEvery3.getOldEventsCount());
    	
    	// send 1 new event, 0 old events
    	sendEventToAll(1, 0);

    	assertTrue(listener1.getAndClearIsInvoked());
    	assertEquals(0,fireEvery1.getNewEventsCount());
    	assertEquals(0,fireEvery1.getOldEventsCount());

    	assertTrue(listener2.getAndClearIsInvoked());
    	assertEquals(0,fireEvery2.getNewEventsCount());
    	assertEquals(0,fireEvery2.getOldEventsCount());

    	assertFalse(listener3.getAndClearIsInvoked());
    	assertEquals(2,fireEvery3.getNewEventsCount());
    	assertEquals(0,fireEvery3.getOldEventsCount());
    	
    	// send 1 new event, 0 old events
    	sendEventToAll(1, 0);

    	assertTrue(listener1.getAndClearIsInvoked());
    	assertEquals(0,fireEvery1.getNewEventsCount());
    	assertEquals(0,fireEvery1.getOldEventsCount());

    	assertFalse(listener2.getAndClearIsInvoked());
    	assertEquals(1,fireEvery2.getNewEventsCount());
    	assertEquals(0,fireEvery2.getOldEventsCount());

    	assertTrue(listener3.getAndClearIsInvoked());
    	assertEquals(0,fireEvery3.getNewEventsCount());
    	assertEquals(0,fireEvery3.getOldEventsCount());
    	
       	// send 0 new event, 1 old events
    	sendEventToAll(0, 1);

    	assertTrue(listener1.getAndClearIsInvoked());
    	assertEquals(0,fireEvery1.getNewEventsCount());
    	assertEquals(0,fireEvery1.getOldEventsCount());

    	assertFalse(listener2.getAndClearIsInvoked());
    	assertEquals(1,fireEvery2.getNewEventsCount());
    	assertEquals(1,fireEvery2.getOldEventsCount());

    	assertFalse(listener3.getAndClearIsInvoked());
    	assertEquals(0,fireEvery3.getNewEventsCount());
    	assertEquals(1,fireEvery3.getOldEventsCount());
    	
    	// send 0 new event, 1 old events
    	sendEventToAll(0, 1);

    	assertTrue(listener1.getAndClearIsInvoked());
    	assertEquals(0,fireEvery1.getNewEventsCount());
    	assertEquals(0,fireEvery1.getOldEventsCount());

    	assertTrue(listener2.getAndClearIsInvoked());
    	assertEquals(0,fireEvery2.getNewEventsCount());
    	assertEquals(0,fireEvery2.getOldEventsCount());

    	assertFalse(listener3.getAndClearIsInvoked());
    	assertEquals(0,fireEvery3.getNewEventsCount());
    	assertEquals(2,fireEvery3.getOldEventsCount());
    	
    	// send 0 new event, 1 old events
    	sendEventToAll(0, 1);

    	assertTrue(listener1.getAndClearIsInvoked());
    	assertEquals(0,fireEvery1.getNewEventsCount());
    	assertEquals(0,fireEvery1.getOldEventsCount());

    	assertFalse(listener2.getAndClearIsInvoked());
    	assertEquals(0,fireEvery2.getNewEventsCount());
    	assertEquals(1,fireEvery2.getOldEventsCount());

    	assertTrue(listener3.getAndClearIsInvoked());
    	assertEquals(0,fireEvery3.getNewEventsCount());
    	assertEquals(0,fireEvery3.getOldEventsCount());
    
    	// send 5 new, 5 old events
    	sendEventToAll(5, 5);
    	
    	assertTrue(listener1.getAndClearIsInvoked());
    	assertEquals(0,fireEvery1.getNewEventsCount());
    	assertEquals(0,fireEvery1.getOldEventsCount());

    	assertTrue(listener2.getAndClearIsInvoked());
    	assertEquals(0,fireEvery2.getNewEventsCount());
    	assertEquals(0,fireEvery2.getOldEventsCount());

    	assertTrue(listener3.getAndClearIsInvoked());
    	assertEquals(0,fireEvery3.getNewEventsCount());
    	assertEquals(0,fireEvery3.getOldEventsCount());
    }
    

    

    private void sendEventToAll(int newEventsLength, int oldEventsLength)
	{   
    	fireEvery1.updateOutputCondition(newEventsLength, oldEventsLength);
    	fireEvery2.updateOutputCondition(newEventsLength, oldEventsLength);
    	fireEvery3.updateOutputCondition(newEventsLength, oldEventsLength);
	}


	public void testIncorrectUse()
	{
	    try
	    {
	        fireEvery1 = new OutputConditionCount(0, callback1);
	        fail();
	    }
	    catch (IllegalArgumentException ex)
	    {
	        // Expected exception
	    }
	    try
	    {
	    	fireEvery1 = new OutputConditionCount(1, null);
	    	fail();
	    }
	    catch (NullPointerException ex)
	    {
	    	// Expected exception
	    }
	}



}
