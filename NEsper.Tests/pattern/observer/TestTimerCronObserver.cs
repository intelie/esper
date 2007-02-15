using System;
using System.Collections.Generic;

using net.esper.pattern;
using net.esper.schedule;
using net.esper.support.events;
using net.esper.support.guard;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.pattern.observer
{
    [TestFixture]
    public class TestTimerCronObserver
    {
        private TimerAtObserver observer;
        private SchedulingServiceImpl scheduleService;
        private SupportObserverEvaluator evaluator;
        private MatchedEventMap beginState;

        [SetUp]
        public virtual void setUp()
        {
            beginState = new MatchedEventMap();

            scheduleService = new SchedulingServiceImpl();
            PatternContext context = new PatternContext(null, scheduleService, scheduleService.allocateBucket(), SupportEventAdapterService.Service);

            ScheduleSpec scheduleSpec = new ScheduleSpec();
            scheduleSpec.addValue(ScheduleUnit.SECONDS, 1);

            evaluator = new SupportObserverEvaluator();

            observer = new TimerAtObserver(scheduleSpec, context, beginState, evaluator);
        }

        [Test]
        public virtual void testStartAndObserve()
        {
            scheduleService.Time = 0;
            observer.StartObserve();
            scheduleService.Time = 1000;
            scheduleService.Evaluate();

            IList<MatchedEventMap> tempList = evaluator.getAndClearMatchEvents();
            MatchedEventMap postEvaluateMap = tempList[0];
            Assert.AreEqual(beginState, tempList[0]);

            // Test Start again
            observer.StartObserve();
            scheduleService.Time = 60999;
            scheduleService.Evaluate();
            Assert.AreEqual(0, evaluator.getMatchEvents().Count);

            scheduleService.Time = 61000; // 1 minute plus 1 second
            scheduleService.Evaluate();
            Assert.AreEqual(beginState, evaluator.getAndClearMatchEvents()[0]);
        }

        [Test]
        public virtual void testStartAndStop()
        {
            // Start then Stop
            scheduleService.Time = 0;
            observer.StartObserve();
            observer.StopObserve();
            scheduleService.Time = 1000;
            scheduleService.Evaluate();
            Assert.AreEqual(0, evaluator.getAndClearMatchEvents().Count);

            // Test Start again
            observer.StartObserve();
            scheduleService.Time = 61000;
            scheduleService.Evaluate();
            Assert.AreEqual(beginState, evaluator.getAndClearMatchEvents()[0]);

            observer.StopObserve();
            observer.StartObserve();

            scheduleService.Time = 150000;
            scheduleService.Evaluate();
            Assert.AreEqual(beginState, evaluator.getAndClearMatchEvents()[0]);
        }

        [Test]
        public virtual void testInvalid()
        {
            try
            {
                observer.StartObserve();
                observer.StartObserve();
                Assert.Fail();
            }
            catch (System.SystemException ex)
            {
                // Expected exception
            }
        }
    }
}