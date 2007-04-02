using System;

using net.esper.compat;
using net.esper.support.schedule;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.schedule
{
    [TestFixture]
    public class TestSchedulingServiceImpl
    {
        private SchedulingServiceImpl service;

        private ScheduleSlot[][] slots;
        private SupportScheduleCallback[] callbacks;

        [SetUp]
        public virtual void setUp()
        {
            service = new SchedulingServiceImpl();

            // 2-by-2 table of buckets and slots
            ScheduleBucket[] buckets = new ScheduleBucket[3];
            slots = new ScheduleSlot[buckets.Length][];
            for (int i = 0; i < buckets.Length; i++)
            {
                slots[i] = new ScheduleSlot[2];
            }

            for (int i = 0; i < buckets.Length; i++)
            {
                buckets[i] = service.AllocateBucket();
                slots[i] = new ScheduleSlot[2];
                for (int j = 0; j < slots[i].Length; j++)
                {
                    slots[i][j] = buckets[i].AllocateSlot();
                }
            }

            callbacks = new SupportScheduleCallback[5];
            for (int i = 0; i < callbacks.Length; i++)
            {
                callbacks[i] = new SupportScheduleCallback();
            }
        }

        [Test]
        public virtual void testTrigger()
        {
            long startTime = 0;

            service.Time = 0;

            // Add callbacks
            service.Add(20, callbacks[3], slots[1][1]);
            service.Add(20, callbacks[2], slots[1][0]);
            service.Add(20, callbacks[1], slots[0][1]);
            service.Add(21, callbacks[0], slots[0][0]);

            // Evaluate before the within time, expect not results
            startTime += 19;
            service.Time = startTime;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 0, 0, 0, 0 });

            // Evaluate exactly on the within time, expect a result
            startTime += 1;
            service.Time = startTime;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 1, 2, 3, 0 });

            // Evaluate after already evaluated once, no result
            startTime += 1;
            service.Time = startTime;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 4, 0, 0, 0, 0 });

            startTime += 1;
            service.Time = startTime;
            service.Evaluate();
            Assert.AreEqual(0, callbacks[3].ClearAndGetOrderTriggered());

            // Adding the same callback more than once should cause an exception
            service.Add(20, callbacks[0], slots[0][0]);
            try
            {
                service.Add(28, callbacks[0], slots[0][0]);
                Assert.Fail();
            }
            catch (ScheduleServiceException ex)
            {
                // Expected exception
            }
            service.Remove(callbacks[0], slots[0][0]);

            service.Add(20, callbacks[2], slots[1][0]);
            service.Add(25, callbacks[1], slots[0][1]);
            service.Remove(callbacks[1], slots[0][1]);
            service.Add(21, callbacks[0], slots[0][0]);
            service.Add(21, callbacks[3], slots[1][1]);
            service.Add(20, callbacks[1], slots[0][1]);
            SupportScheduleCallback.CallbackOrderNum = 0;

            startTime += 20;
            service.Time = startTime;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 1, 2, 0, 0 });

            startTime += 1;
            service.Time = startTime;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 3, 0, 0, 4, 0 });

            service.Time = startTime + Int32.MaxValue;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 0, 0, 0, 0 });
        }

        [Test]
        public virtual void testWaitAndSpecTogether()
        {
            DateTime startDateTime = new DateTime(2004, 12, 9, 15, 27, 10, 500, DateTimeKind.Local);
            long startTime = DateTimeHelper.TimeInMillis(startDateTime);

            service.Time = startTime;

            // Add a specification
            ScheduleSpec spec = new ScheduleSpec();
            spec.AddValue(ScheduleUnit.MONTHS, 12);
            spec.AddValue(ScheduleUnit.DAYS_OF_MONTH, 9);
            spec.AddValue(ScheduleUnit.HOURS, 15);
            spec.AddValue(ScheduleUnit.MINUTES, 27);
            spec.AddValue(ScheduleUnit.SECONDS, 20);

            service.Add(spec, callbacks[3], slots[1][1]);

            spec.AddValue(ScheduleUnit.SECONDS, 15);
            service.Add(spec, callbacks[4], slots[2][0]);

            // Add some more callbacks
            service.Add(5000, callbacks[0], slots[0][0]);
            service.Add(10000, callbacks[1], slots[0][1]);
            service.Add(15000, callbacks[2], slots[1][0]);

            // Now send a times reflecting various seconds later and check who got a callback
            service.Time = startTime + 1000;
            SupportScheduleCallback.CallbackOrderNum = 0;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 0, 0, 0, 0 });

            service.Time = startTime + 2000;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 0, 0, 0, 0 });

            service.Time = startTime + 4000;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 0, 0, 0, 0 });

            service.Time = startTime + 5000;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 1, 0, 0, 0, 2 });

            service.Time = startTime + 9000;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 0, 0, 0, 0 });

            service.Time = startTime + 10000;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 3, 0, 4, 0 });

            service.Time = startTime + 11000;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 0, 0, 0, 0 });

            service.Time = startTime + 15000;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 0, 5, 0, 0 });

            service.Time = startTime + Int32.MaxValue;
            service.Evaluate();
            checkCallbacks(callbacks, new Int32[] { 0, 0, 0, 0, 0 });
        }

        [Test]
        public virtual void testIncorrectRemove()
        {
            SchedulingServiceImpl evaluator = new SchedulingServiceImpl();
            SupportScheduleCallback callback = new SupportScheduleCallback();

            try
            {
                evaluator.Remove(callback, null);
                Assert.IsTrue(false);
            }
            catch (ScheduleServiceException ex)
            {
                // Expected exception
            }
        }

        private void checkCallbacks(SupportScheduleCallback[] callbacks, Int32[] results)
        {
            Assert.IsTrue(callbacks.Length == results.Length);

            for (int i = 0; i < callbacks.Length; i++)
            {
                Assert.AreEqual((int)results[i], (int)callbacks[i].ClearAndGetOrderTriggered());
            }
        }
    }
}