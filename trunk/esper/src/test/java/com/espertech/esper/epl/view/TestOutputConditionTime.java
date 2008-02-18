package com.espertech.esper.epl.view;

import junit.framework.TestCase;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.core.EPStatementHandleCallback;


public class TestOutputConditionTime extends TestCase
{
    private final static long TEST_INTERVAL_MSEC = 10000;

    private OutputConditionTime condition;
    private OutputCallback callback;
    private SupportSchedulingServiceImpl schedulingServiceStub;

	private StatementContext context;

    public void setUp()
    {
    	callback = new OutputCallback() {
    		public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
    		{
    		}
    	};
    	
    	schedulingServiceStub = new SupportSchedulingServiceImpl();
    	context = SupportStatementContextFactory.makeContext(schedulingServiceStub);
		condition = new OutputConditionTime(TEST_INTERVAL_MSEC / 1000d, false, null, context, callback);
    }

    public void testUpdateCondtion()
    {
    	assertEquals(TEST_INTERVAL_MSEC, condition.getMsecIntervalSize());
    	
        long startTime = 0;
        schedulingServiceStub.setTime(startTime);
        
    	// 2 new, 3 old
        condition.updateOutputCondition(2, 3);
        // update time
        schedulingServiceStub.setTime(startTime + TEST_INTERVAL_MSEC);
        // check callback scheduled, pretend callback
        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(TEST_INTERVAL_MSEC) != null);
        Object result = schedulingServiceStub.getAdded().get(TEST_INTERVAL_MSEC);
        ((EPStatementHandleCallback) result).getScheduleCallback().scheduledTrigger(null);
  
        // 2 new, 3 old
        condition.updateOutputCondition(2, 3);
    	// 2 new, 3 old
        condition.updateOutputCondition(2, 3);
        // update time
        schedulingServiceStub.setTime(startTime + 2*TEST_INTERVAL_MSEC);
        // check callback scheduled, pretend callback
        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(TEST_INTERVAL_MSEC) != null);
        ((EPStatementHandleCallback) result).getScheduleCallback().scheduledTrigger(null);

        
    	// 0 new, 0 old
        condition.updateOutputCondition(0, 0);
        // update time
        schedulingServiceStub.setTime(startTime + 3*TEST_INTERVAL_MSEC);
        // check update
        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(TEST_INTERVAL_MSEC) != null);
        schedulingServiceStub.getAdded().clear();
    }
    
    public void testIncorrectUse()
    {
	    try
	    {
	        condition = new OutputConditionTime(0d, false, null, context, callback);
	        fail();
	    }
	    catch (IllegalArgumentException ex)
	    {
	        // Expected exception
	    }
	    try
	    {
	    	condition = new OutputConditionTime(1d, false, null, context, null);
	    	fail();
	    }
	    catch (NullPointerException ex)
	    {
	    	// Expected exception
	    }
	    try
	    {
	    	condition = new OutputConditionTime(1d, false, null, null, callback);
	    	fail();
	    }
	    catch (NullPointerException ex)
	    {
	    	// Expected exception
	    }
    }
}
