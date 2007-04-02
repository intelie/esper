using System;

using net.esper.eql.spec;
using net.esper.support.schedule;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.view
{
    [TestFixture]
    public class TestOutputConditionFirst
    {
        private const long TEST_INTERVAL_MSEC = 10000;

        private OutputCallback callback;
        private Boolean witnessedCallback;
        private Boolean? callbackDoesOutput;
        private Boolean? callbackForcesUpdate;

        [SetUp]
        public virtual void setUp()
        {
            callback = new OutputCallback(
                delegate(bool doOutput, bool forceUpdate)
                {
                    this.witnessedCallback = true;
                    this.callbackDoesOutput = doOutput;
                    this.callbackForcesUpdate = forceUpdate;
                });

            witnessedCallback = false;
        }

        [Test]
        public virtual void testUpdateTime()
        {
            OutputLimitSpec outputConditionSpec = new OutputLimitSpec(TEST_INTERVAL_MSEC / 1000d, OutputLimitSpec.DisplayLimit.FIRST);
            SupportSchedulingServiceImpl schedulingServiceStub = new SupportSchedulingServiceImpl();
            ViewServiceContext viewContext = SupportViewContextFactory.makeContext(schedulingServiceStub);

            OutputCondition condition = new OutputConditionFirst(outputConditionSpec, viewContext, callback);

            long startTime = 0;
            schedulingServiceStub.Time = startTime;

            // 2 new, 3 old
            condition.UpdateOutputCondition(2, 3);
            // update time
            schedulingServiceStub.Time = startTime + TEST_INTERVAL_MSEC;
            // check callback scheduled, pretend callback
            Assert.IsTrue(schedulingServiceStub.getAdded().Count == 1);
            Assert.IsTrue(schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null);
            schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC].ScheduledTrigger();

            // 2 new, 3 old
            condition.UpdateOutputCondition(2, 3);
            // 2 new, 3 old
            condition.UpdateOutputCondition(2, 3);
            // update time
            schedulingServiceStub.Time = startTime + 2 * TEST_INTERVAL_MSEC;
            // check callback scheduled, pretend callback
            Assert.IsTrue(schedulingServiceStub.getAdded().Count == 1);
            Assert.IsTrue(schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null);
            schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC].ScheduledTrigger();


            // 0 new, 0 old
            condition.UpdateOutputCondition(0, 0);
            // update time
            schedulingServiceStub.Time = startTime + 3 * TEST_INTERVAL_MSEC;
            // check update
            Assert.IsTrue(schedulingServiceStub.getAdded().Count == 1);
            Assert.IsTrue(schedulingServiceStub.getAdded()[TEST_INTERVAL_MSEC] != null);
            schedulingServiceStub.getAdded().Clear();
        }

        [Test]
        public virtual void testUpdateCount()
        {
            // 'output first every 3 events'
            OutputLimitSpec outputConditionSpec = new OutputLimitSpec(3, OutputLimitSpec.DisplayLimit.FIRST);
            ViewServiceContext viewContext = null;

            OutputCondition condition = OutputConditionFactory.CreateCondition(outputConditionSpec, viewContext, callback);

            // Send first event of the batch, callback should be made
            condition.UpdateOutputCondition(1, 0);
            bool doOutput = true;
            bool forceUpdate = false;
            assertCallbackAndReset(doOutput, forceUpdate);

            // Send more events in the same batch
            condition.UpdateOutputCondition(1, 1);
            Assert.IsFalse(witnessedCallback);

            // Send enough events to end the batch
            condition.UpdateOutputCondition(1, 0);
            doOutput = false;
            assertCallbackAndReset(doOutput, forceUpdate);

            // Start the next batch
            condition.UpdateOutputCondition(1, 1);
            doOutput = true;
            assertCallbackAndReset(doOutput, forceUpdate);

            // More events in the same batch, not enough to end
            condition.UpdateOutputCondition(1, 1);
            Assert.IsFalse(witnessedCallback);

            // Send enough events to end the batch
            condition.UpdateOutputCondition(1, 0);
            doOutput = false;
            assertCallbackAndReset(doOutput, forceUpdate);
        }

        public virtual void assertCallbackAndReset(bool? doOutput, bool? forceUpdate)
        {
            Assert.IsTrue(witnessedCallback);
            Assert.AreEqual(doOutput, callbackDoesOutput);
            Assert.AreEqual(forceUpdate, callbackForcesUpdate);
            witnessedCallback = false;
            callbackDoesOutput = null;
            callbackForcesUpdate = null;
        }
    }
}
