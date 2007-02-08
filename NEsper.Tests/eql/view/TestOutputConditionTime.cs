using System;

using net.esper.support.schedule;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.view
{
	
	
	[TestFixture]
	public class TestOutputConditionTime 
	{
	    private const long TEST_INTERVAL_MSEC = 10000;
		
		private OutputConditionTime condition;
		private OutputCallback callback;
		private SupportSchedulingServiceImpl schedulingServiceStub;
		
		private ViewServiceContext context;

        [SetUp]
        public virtual void setUp()
        {
            callback = new OutputCallback(
                delegate(bool doOutput, bool forceUpdate)
                {
                });

            schedulingServiceStub = new SupportSchedulingServiceImpl();
            context = SupportViewContextFactory.makeContext(schedulingServiceStub);
            condition = new OutputConditionTime(TEST_INTERVAL_MSEC / 1000d, context, callback);
        }
		
		[Test]
		public virtual void  testUpdateCondtion()
		{
			Assert.AreEqual(TEST_INTERVAL_MSEC, condition.MsecIntervalSize);
			
			long startTime = 0;
			schedulingServiceStub.Time = startTime;
			
			// 2 new, 3 old
			condition.updateOutputCondition(2, 3);
			// update time
			schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC;
			// check callback scheduled, pretend callback
			Assert.IsTrue(schedulingServiceStub.getAdded().Count == 1);
			Assert.IsTrue(schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null);
			schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC].scheduledTrigger();
			
			// 2 new, 3 old
			condition.updateOutputCondition(2, 3);
			// 2 new, 3 old
			condition.updateOutputCondition(2, 3);
			// update time
			schedulingServiceStub.Time = startTime + 2 * TEST_INTERVAL_MSEC;
			// check callback scheduled, pretend callback
			Assert.IsTrue(schedulingServiceStub.getAdded().Count == 1);
			Assert.IsTrue(schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null);
			schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC].scheduledTrigger();
			
			
			// 0 new, 0 old
			condition.updateOutputCondition(0, 0);
			// update time
			schedulingServiceStub.Time = startTime + 3 * TEST_INTERVAL_MSEC;
			// check update
			Assert.IsTrue(schedulingServiceStub.getAdded().Count == 1);
			Assert.IsTrue(schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null);
			schedulingServiceStub.getAdded().Clear();
		}
		
		[Test]
		public virtual void  testIncorrectUse()
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
			catch (System.NullReferenceException ex)
			{
				// Expected exception
			}
			try
			{
				condition = new OutputConditionTime(1, null, callback);
				Assert.Fail();
			}
			catch (System.NullReferenceException ex)
			{
				// Expected exception
			}
		}
	}
}
