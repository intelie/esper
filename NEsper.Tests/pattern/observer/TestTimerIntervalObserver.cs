using System;

using net.esper.pattern;
using net.esper.schedule;
using net.esper.support.events;
using net.esper.support.guard;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.pattern.observer
{
	
	[TestFixture]
	public class TestTimerIntervalObserver 
	{
		private PatternContext context;
		private TimerIntervalObserver observer;
		private SchedulingServiceImpl scheduleService;
		private SupportObserverEvaluator evaluator;
		private MatchedEventMap beginState;
		
		[SetUp]
		public virtual void  setUp()
		{
			beginState = new MatchedEventMap();
			
			scheduleService = new SchedulingServiceImpl();
			context = new PatternContext(null, scheduleService, scheduleService.AllocateBucket(), SupportEventAdapterService.Service);
			
			evaluator = new SupportObserverEvaluator();
			
			observer = new TimerIntervalObserver(1000, context, beginState, evaluator);
		}
		
		[Test]
		public virtual void  testStartAndObserve()
		{
			scheduleService.Time = 0;
			observer.StartObserve();
			scheduleService.Time = 1000;
			scheduleService.Evaluate();
			Assert.AreEqual(beginState, evaluator.getAndClearMatchEvents()[0]);
			
			// Test Start again
			observer.StartObserve();
			scheduleService.Time = 1999;
			scheduleService.Evaluate();
			Assert.AreEqual(0, evaluator.getMatchEvents().Count);
			
			scheduleService.Time = 2000;
			scheduleService.Evaluate();
			Assert.AreEqual(beginState, evaluator.getAndClearMatchEvents()[0]);
		}
		
		[Test]
		public virtual void  testStartAndStop()
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
			scheduleService.Time = 2500;
			scheduleService.Evaluate();
			Assert.AreEqual(beginState, evaluator.getAndClearMatchEvents()[0]);
			
			observer.StopObserve();
			observer.StartObserve();
			
			scheduleService.Time = 3500;
			scheduleService.Evaluate();
			Assert.AreEqual(beginState, evaluator.getAndClearMatchEvents()[0]);
		}
		
		[Test]
		public virtual void  testImmediateTrigger()
		{
			// Should fire right away, wait time set to zero
			observer = new TimerIntervalObserver(0, context, beginState, evaluator);
			
			scheduleService.Time = 0;
			observer.StartObserve();
			Assert.AreEqual(beginState, evaluator.getAndClearMatchEvents()[0]);
			scheduleService.Time = 10000000;
			scheduleService.Evaluate();
			Assert.AreEqual(0, evaluator.getAndClearMatchEvents().Count);
		}
		
		[Test]
		public virtual void  testInvalid()
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
