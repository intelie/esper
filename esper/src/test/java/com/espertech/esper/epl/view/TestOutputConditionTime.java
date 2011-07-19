/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.view;

import com.espertech.esper.core.EPStatementHandleCallback;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprConstantNodeImpl;
import com.espertech.esper.epl.expression.ExprTimePeriod;
import com.espertech.esper.epl.expression.ExprTimePeriodImpl;
import com.espertech.esper.support.epl.SupportExprNodeUtil;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import junit.framework.TestCase;


public class TestOutputConditionTime extends TestCase
{
    private final static long TEST_INTERVAL_MSEC = 10000;

    private OutputConditionTime condition;
    private OutputCallback callback;
    private SupportSchedulingServiceImpl schedulingServiceStub;

	private StatementContext context;

    public void setUp() throws Exception
    {
    	callback = new OutputCallback() {
    		public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
    		{
    		}
    	};

        ExprTimePeriod timePeriod = new ExprTimePeriodImpl(false, false, false, false, false, false, true, false);
        timePeriod.addChildNode(new ExprConstantNodeImpl(TEST_INTERVAL_MSEC / 1000d));
        SupportExprNodeUtil.validate(timePeriod);

        schedulingServiceStub = new SupportSchedulingServiceImpl();
    	context = SupportStatementContextFactory.makeContext(schedulingServiceStub);
		condition = new OutputConditionTime(timePeriod, context, callback);
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
    
    public void testIncorrectUse() throws Exception
    {
        ExprTimePeriod timePeriodValid = new ExprTimePeriodImpl(false, false, false, false, false, false, false, true);
        timePeriodValid.addChildNode(new ExprConstantNodeImpl(1000));

        ExprTimePeriod timePeriodInvalid = new ExprTimePeriodImpl(false, false, false, false, false, false, false, true);
        timePeriodInvalid.addChildNode(new ExprConstantNodeImpl(0));
        SupportExprNodeUtil.validate(timePeriodInvalid);

	    try
	    {
            condition = new OutputConditionTime(timePeriodInvalid, context, callback);
	        fail();
	    }
	    catch (IllegalArgumentException ex)
	    {
	        // Expected exception
	    }
	    try
	    {
	    	condition = new OutputConditionTime(timePeriodValid, context, null);
	    	fail();
	    }
	    catch (NullPointerException ex)
	    {
	    	// Expected exception
	    }
	    try
	    {
	    	condition = new OutputConditionTime(timePeriodValid, null, callback);
	    	fail();
	    }
	    catch (NullPointerException ex)
	    {
	    	// Expected exception
	    }
    }
}
