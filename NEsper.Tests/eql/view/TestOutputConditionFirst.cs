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
using net.esper.eql.spec;
using net.esper.support.schedule;
using net.esper.support.view;

namespace net.esper.eql.view
{
	[TestFixture]
	public class TestOutputConditionFirst
	{
		private readonly static long TEST_INTERVAL_MSEC = 10000;

		private OutputCallback callback;
		private bool? witnessedCallback;
		private bool? callbackDoesOutput;
		private bool? callbackForcesUpdate;

        [SetUp]
		protected void SetUp()
		{
		    callback = new OutputCallback(
		        delegate(bool doOutput, bool forceUpdate)
		            {
		                witnessedCallback = true;
		                callbackDoesOutput = doOutput;
		                callbackForcesUpdate = forceUpdate;
		            });

            witnessedCallback = false;
		}

		[Test]
		public void testUpdateTime()
		{
			OutputLimitSpec outputConditionSpec = new OutputLimitSpec(TEST_INTERVAL_MSEC/1000d, OutputLimitSpec.DisplayLimit.FIRST);
			SupportSchedulingServiceImpl schedulingServiceStub = new SupportSchedulingServiceImpl();
			StatementContext statementContext = SupportStatementContextFactory.MakeContext(schedulingServiceStub);

			OutputCondition condition = new OutputConditionFirst(outputConditionSpec, statementContext, callback);

	        long startTime = 0;
	        schedulingServiceStub.Time = (startTime);

	    	// 2 new, 3 old
	        condition.UpdateOutputCondition(2, 3);
	        // update time
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC);
	        // check callback scheduled, pretend callback
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        ((EPStatementHandleCallback) schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC)).ScheduleCallback.ScheduledTrigger(null);

	        // 2 new, 3 old
	        condition.UpdateOutputCondition(2, 3);
	    	// 2 new, 3 old
	        condition.UpdateOutputCondition(2, 3);
	        // update time
	        schedulingServiceStub.Time = (startTime + 2*TEST_INTERVAL_MSEC);
	        // check callback scheduled, pretend callback
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        ((EPStatementHandleCallback) schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC)).ScheduleCallback.ScheduledTrigger(null);


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
		public void testUpdateCount()
		{
			// 'output first every 3 events'
			OutputLimitSpec outputConditionSpec = new OutputLimitSpec(3, OutputLimitSpec.DisplayLimit.FIRST);
			StatementContext statementContext = null;

			OutputCondition condition = OutputConditionFactory.CreateCondition(outputConditionSpec, statementContext, callback);

			// Send first event of the batch, callback should be made
			condition.UpdateOutputCondition(1, 0);
			Boolean doOutput = true;
			Boolean forceUpdate = false;
			AssertCallbackAndReset(doOutput, forceUpdate);

			// Send more events in the same batch
			condition.UpdateOutputCondition(1, 1);
			Assert.IsFalse(witnessedCallback.Value);

			// Send enough events to end the batch
			condition.UpdateOutputCondition(1, 0);
			doOutput = false;
			AssertCallbackAndReset(doOutput, forceUpdate);

			// Start the next batch
			condition.UpdateOutputCondition(1, 1);
			doOutput = true;
			AssertCallbackAndReset(doOutput, forceUpdate);

			// More events in the same batch, not enough to end
			condition.UpdateOutputCondition(1, 1);
            Assert.IsFalse(witnessedCallback.Value);

			// Send enough events to end the batch
			condition.UpdateOutputCondition(1, 0);
			doOutput = false;
			AssertCallbackAndReset(doOutput, forceUpdate);
		}

		public void AssertCallbackAndReset(bool? doOutput, bool? forceUpdate)
		{
			Assert.IsTrue(witnessedCallback.Value);
			Assert.AreEqual(doOutput, callbackDoesOutput);
			Assert.AreEqual(forceUpdate, callbackForcesUpdate);
			witnessedCallback = false;
			callbackDoesOutput = null;
			callbackForcesUpdate = null;
		}
	}
} // End of namespace
