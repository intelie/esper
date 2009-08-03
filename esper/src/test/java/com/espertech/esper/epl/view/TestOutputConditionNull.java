package com.espertech.esper.epl.view;

import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestOutputConditionNull extends TestCase
{
	private OutputConditionNull condition;	
    private SupportUpdateListener listener;
	private OutputCallback callback;

    public void setUp()
    { 
    	listener = new SupportUpdateListener();
    	
    	callback = new OutputCallback() {
		    		public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
		    		{
		    			listener.update(null, null);
		    		}
		    	};
		condition = new OutputConditionNull(callback);    	
    }
    
    public void testUpdateCondition()
    {
    	// the callback should be made regardles of the update
    	condition.updateOutputCondition(1,1);
    	assertTrue(listener.getAndClearIsInvoked());
    	condition.updateOutputCondition(1,0);
    	assertTrue(listener.getAndClearIsInvoked());
    	condition.updateOutputCondition(0,1);
    	assertTrue(listener.getAndClearIsInvoked());
    	condition.updateOutputCondition(0,0);
    	assertTrue(listener.getAndClearIsInvoked());
    }

    public void testIncorrectUse()
	{
	    try
	    {
	    	condition = new OutputConditionNull(null);
	    	fail();
	    }
	    catch (NullPointerException ex)
	    {
	    	// Expected exception
	    }
	}
}