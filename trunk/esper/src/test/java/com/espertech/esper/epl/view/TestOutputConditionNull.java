package com.espertech.esper.epl.view;

import junit.framework.TestCase;
import com.espertech.esper.support.util.SupportUpdateListener;

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
    	condition.updateOutputCondition(1,1, null, null);
    	assertTrue(listener.getAndClearIsInvoked());
    	condition.updateOutputCondition(1,0, null, null);
    	assertTrue(listener.getAndClearIsInvoked());
    	condition.updateOutputCondition(0,1, null, null);
    	assertTrue(listener.getAndClearIsInvoked());
    	condition.updateOutputCondition(0,0, null, null);
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
