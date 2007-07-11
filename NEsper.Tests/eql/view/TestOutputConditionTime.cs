///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.core;
using net.esper.support.schedule;
using net.esper.support.view;

namespace net.esper.eql.view
{
	[TestFixture]
	public class TestOutputConditionTime
	{
	    private const long TEST_INTERVAL_MSEC = 10000;

	    private OutputConditionTime condition;
	    private OutputCallback callback;
	    private SupportSchedulingServiceImpl schedulingServiceStub;

		private StatementContext context;

	    [SetUp]
	    public void SetUp()
	    {
	        callback = new OutputCallback(
	            delegate(bool doOutput, bool forceUpdate) { });

	    	schedulingServiceStub = new SupportSchedulingServiceImpl();
	    	context = SupportStatementContextFactory.MakeContext(schedulingServiceStub);
			condition = new OutputConditionTime(TEST_INTERVAL_MSEC / 1000d, context, callback);
	    }

	    [Test]
	    public void testUpdateCondtion()
	    {
	    	Assert.AreEqual(TEST_INTERVAL_MSEC, condition.MsecIntervalSize);

	        long startTime = 0;
	        schedulingServiceStub.Time = (startTime);

	    	// 2 new, 3 old
	        condition.UpdateOutputCondition(2, 3);
	        // update time
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC);
	        // check callback scheduled, pretend callback
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        Object result = schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC);
	        ((EPStatementHandleCallback) result).ScheduleCallback.ScheduledTrigger(null);

	        // 2 new, 3 old
	        condition.UpdateOutputCondition(2, 3);
	    	// 2 new, 3 old
	        condition.UpdateOutputCondition(2, 3);
	        // update time
	        schedulingServiceStub.Time = (startTime + 2*TEST_INTERVAL_MSEC);
	        // check callback scheduled, pretend callback
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        ((EPStatementHandleCallback) result).ScheduleCallback.ScheduledTrigger(null);


	    	// 0 new, 0 old
	        condition.UpdateOutputCondition(0, 0);
	        // update time
	        schedulingServiceStub.Time = (startTime + 3*TEST_INTERVAL_MSEC);
	        // check update
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        schedulingServiceStub.GetAdded().Clear();
	    }

	    [Test]
	    public void testIncorrectUse()
	    {
		    try
		    {
		        condition = new OutputConditionTime(0.01, context, callback);
		        Assert.Fail();
		    }
		    catch (ArgumentException ex)
		    {
		        // Expected exception
		    }
		    try
		    {
		    	condition = new OutputConditionTime(1, context, null);
		    	Assert.Fail();
		    }
            catch (ArgumentException ex)
		    {
		    	// Expected exception
		    }
		    try
		    {
		    	condition = new OutputConditionTime(1, null, callback);
		    	Assert.Fail();
		    }
            catch (ArgumentException ex)
		    {
		    	// Expected exception
		    }
	    }
	}
} // End of namespace
