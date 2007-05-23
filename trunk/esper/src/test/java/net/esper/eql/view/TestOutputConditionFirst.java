package net.esper.eql.view;

import net.esper.eql.spec.OutputLimitSpec;
import net.esper.eql.spec.OutputLimitType;
import net.esper.support.schedule.SupportSchedulingServiceImpl;
import net.esper.support.view.SupportStatementContextFactory;
import net.esper.core.StatementContext;
import net.esper.core.EPStatementHandleCallback;
import junit.framework.TestCase;

public class TestOutputConditionFirst extends TestCase
{
	private final static long TEST_INTERVAL_MSEC = 10000;
	
	private OutputCallback callback;
	private Boolean witnessedCallback;
	private Boolean callbackDoesOutput;
	private Boolean callbackForcesUpdate;
	
	protected void setUp()
	{
		callback = new OutputCallback()
		{
			public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
			{
				witnessedCallback = true;
				callbackDoesOutput = doOutput;
				callbackForcesUpdate = forceUpdate;
			}
		};
		witnessedCallback = false;
	}
	
	public void testUpdateTime()
	{
		OutputLimitSpec outputConditionSpec = new OutputLimitSpec(TEST_INTERVAL_MSEC/1000d, OutputLimitType.FIRST);
		SupportSchedulingServiceImpl schedulingServiceStub = new SupportSchedulingServiceImpl();
		StatementContext statementContext = SupportStatementContextFactory.makeContext(schedulingServiceStub);
		
		OutputCondition condition = new OutputConditionFirst(outputConditionSpec, statementContext, callback);

        long startTime = 0;
        schedulingServiceStub.setTime(startTime);
        
    	// 2 new, 3 old
        condition.updateOutputCondition(true, 2, 3);
        // update time
        schedulingServiceStub.setTime(startTime + TEST_INTERVAL_MSEC);
        // check callback scheduled, pretend callback
        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(TEST_INTERVAL_MSEC) != null);
        ((EPStatementHandleCallback) schedulingServiceStub.getAdded().get(TEST_INTERVAL_MSEC)).getScheduleCallback().scheduledTrigger(null);
        
        // 2 new, 3 old
        condition.updateOutputCondition(true, 2, 3);
    	// 2 new, 3 old
        condition.updateOutputCondition(true, 2, 3);
        // update time
        schedulingServiceStub.setTime(startTime + 2*TEST_INTERVAL_MSEC);
        // check callback scheduled, pretend callback
        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(TEST_INTERVAL_MSEC) != null);
        ((EPStatementHandleCallback) schedulingServiceStub.getAdded().get(TEST_INTERVAL_MSEC)).getScheduleCallback().scheduledTrigger(null);

        
    	// 0 new, 0 old
        condition.updateOutputCondition(true, 0, 0);
        // update time
        schedulingServiceStub.setTime(startTime + 3*TEST_INTERVAL_MSEC);
        // check update
        assertTrue(schedulingServiceStub.getAdded().size() == 1);
        assertTrue(schedulingServiceStub.getAdded().get(TEST_INTERVAL_MSEC) != null);
        schedulingServiceStub.getAdded().clear();
	}
	
	public void testUpdateCount()
	{
		// 'output first every 3 events'
		OutputLimitSpec outputConditionSpec = new OutputLimitSpec(3, OutputLimitType.FIRST);
		StatementContext statementContext = null;
		
		OutputCondition condition = OutputConditionFactory.createCondition(outputConditionSpec, statementContext, callback);

		// Send first event of the batch, callback should be made
		condition.updateOutputCondition(true, 1, 0);
		Boolean doOutput = true;
		Boolean forceUpdate = false;
		assertCallbackAndReset(doOutput, forceUpdate);
		
		// Send more events in the same batch
		condition.updateOutputCondition(true, 1, 1);
		assertFalse(witnessedCallback);
		
		// Send enough events to end the batch
		condition.updateOutputCondition(true, 1, 0);
		doOutput = false;
		assertCallbackAndReset(doOutput, forceUpdate);
		
		// Start the next batch
		condition.updateOutputCondition(true, 1, 1);
		doOutput = true;
		assertCallbackAndReset(doOutput, forceUpdate);
		
		// More events in the same batch, not enough to end
		condition.updateOutputCondition(true, 1, 1);
		assertFalse(witnessedCallback);
		
		// Send enough events to end the batch
		condition.updateOutputCondition(true, 1, 0);
		doOutput = false;
		assertCallbackAndReset(doOutput, forceUpdate);
	}
	
	public void assertCallbackAndReset(Boolean doOutput, Boolean forceUpdate)
	{
		assertTrue(witnessedCallback);
		assertEquals(doOutput, callbackDoesOutput);
		assertEquals(forceUpdate, callbackForcesUpdate);
		witnessedCallback = false;
		callbackDoesOutput = null;
		callbackForcesUpdate = null;
	}
}
