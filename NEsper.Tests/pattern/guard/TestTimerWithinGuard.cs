using System;

using net.esper.pattern;
using net.esper.schedule;
using net.esper.support.events;
using net.esper.support.guard;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.pattern.guard
{
	
	[TestFixture]
	public class TestTimerWithinGuard 
	{
		private TimerWithinGuard guard;
		private SchedulingServiceImpl scheduleService;
		private SupportQuitable quitable;
		
		[SetUp]
		public virtual void  setUp()
		{
			scheduleService = new SchedulingServiceImpl();
			PatternContext context = new PatternContext(null, scheduleService, scheduleService.allocateBucket(), SupportEventAdapterService.Service);
			
			quitable = new SupportQuitable();
			
			guard = new TimerWithinGuard(1000, context, quitable);
		}
		
		[Test]
		public virtual void  testInspect()
		{
			Assert.IsTrue(guard.inspect(null));
		}
		
		/// <summary> Make sure the timer calls guardQuit after the set time period</summary>
		[Test]
		public virtual void  testStartAndTrigger()
		{
			scheduleService.Time = 0;
			
			guard.StartGuard();
			
			Assert.AreEqual(0, quitable.AndResetQuitCounter);
			
			scheduleService.Time = 1000;
			scheduleService.Evaluate();
			
			Assert.AreEqual(1, quitable.AndResetQuitCounter);
		}
		
		[Test]
		public virtual void  testStartAndStop()
		{
			scheduleService.Time = 0;
			
			guard.StartGuard();
			
			guard.StopGuard();
			
			scheduleService.Time = 1001;
			scheduleService.Evaluate();
			
			Assert.AreEqual(0, quitable.AndResetQuitCounter);
		}
		
		[Test]
		public virtual void  testInvalid()
		{
			try
			{
				guard.StartGuard();
				guard.StartGuard();
				Assert.Fail();
			}
			catch (System.SystemException ex)
			{
				// Expected exception
			}
		}
	}
}
