using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{
    [TestFixture]
    public class TestOutputLimitFirst
    {
        private static readonly String EVENT_NAME;
        private EPServiceProvider epService;
        private long currentTime;
        private SupportUpdateListener updateListener;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetProvider("TestOutputLimitFirst");
        }

        [TearDown]
        public virtual void tearDown()
        {
            EPServiceProviderManager.PurgeProvider("TestOutputLimitFirst");
            epService = null;
        }

        [Test]
        public virtual void testTime()
        {
            // Clear any old events
            epService.Initialize();

            // Turn off external clocking
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

            // Set the clock to 0
            currentTime = 0;
            sendTimeEvent(0);

            // Create the eql statement and add a listener
            String statementText = "select symbol, sum(volume) from " + EVENT_NAME + ".win:length(5) output first every 3 seconds";
            EPStatement statement = epService.EPAdministrator.CreateEQL(statementText);
            updateListener = new SupportUpdateListener();
            statement.AddListener(updateListener.Update);
            updateListener.reset();

            // Send the first event of the batch; should be output
            SendEvent(10L);
            assertEvent(10L);

            // Send another event, not the first, for aggregation
            // update only, no output
            SendEvent(20L);
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // Update time
            sendTimeEvent(3000);
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // Send first event of the next batch, should be output.
            // The aggregate value is computed over all events 
            // received: 10 + 20 + 30 = 60
            SendEvent(30L);
            assertEvent(60L);

            // Send the next event of the batch, no output
            SendEvent(40L);
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // Update time
            sendTimeEvent(3000);
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // Send first event of third batch
            SendEvent(1L);
            assertEvent(101L);

            // Update time
            sendTimeEvent(3000);
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // Update time: no first event this batch, so a callback
            // is made at the end of the interval
            sendTimeEvent(3000);
            Assert.IsTrue(updateListener.getAndClearIsInvoked());
            Assert.IsNull(updateListener.LastNewData);
            Assert.IsNull(updateListener.LastOldData);
        }

        [Test]
        public virtual void testCount()
        {
            // Create the eql statement and add a listener
            String statementText = "select symbol, sum(volume) from " + EVENT_NAME + ".win:length(5) output first every 3 events";
            EPStatement statement = epService.EPAdministrator.CreateEQL(statementText);
            updateListener = new SupportUpdateListener();
            statement.AddListener(updateListener.Update);
            updateListener.reset();

            // Send the first event of the batch, should be output
            SendEvent(10L);
            assertEvent(10L);

            // Send the second event of the batch, not output, used
            // for updating the aggregate value only
            SendEvent(20L);
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // Send the third event of the batch, still not output, 
            // but should reset the batch
            SendEvent(30L);
            Assert.IsFalse(updateListener.getAndClearIsInvoked());

            // First event, next batch, aggregate value should be
            // 10 + 20 + 30 + 40 = 100
            SendEvent(40L);
            assertEvent(100L);

            // Next event again not output
            SendEvent(50L);
            Assert.IsFalse(updateListener.getAndClearIsInvoked());
        }

        private void sendTimeEvent(int timeIncrement)
        {
            currentTime += timeIncrement;
            CurrentTimeEvent _event = new CurrentTimeEvent(currentTime);
            epService.EPRuntime.SendEvent(_event);
        }

        private void assertEvent(long volume)
        {
            Assert.IsTrue(updateListener.getAndClearIsInvoked());
            Assert.IsTrue(updateListener.LastNewData != null);
            Assert.AreEqual(1, updateListener.LastNewData.Length);
            Assert.AreEqual(volume, updateListener.LastNewData[0]["sum(volume)"]);
        }

        private void SendEvent(long volume)
        {
            epService.EPRuntime.SendEvent(new SupportMarketDataBean("DELL", 0.0, volume, null));
        }
        static TestOutputLimitFirst()
        {
            EVENT_NAME = typeof(SupportMarketDataBean).FullName;
        }
    }
}