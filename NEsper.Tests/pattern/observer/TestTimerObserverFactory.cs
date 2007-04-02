using System;

using net.esper.pattern;
using net.esper.schedule;
using net.esper.support.pattern;
using net.esper.support.schedule;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.pattern.observer
{
	
	[TestFixture]
	public class TestTimerObserverFactory 
	{
		private PatternContext patternContext;
		
		[SetUp]
		public virtual void  setUp()
		{
			patternContext = SupportPatternContextFactory.makeContext();
		}
		
		[Test]
		public virtual void  testCron()
		{
			ScheduleSpec spec = new ScheduleSpec();
			TimerObserverFactory factory = new TimerObserverFactory(spec);
			
			EventObserver eventObserver = factory.MakeObserver(patternContext, null, null);
			
			Assert.IsTrue(eventObserver is TimerAtObserver);
		}
		
		[Test]
		public virtual void  testIntervalWait()
		{
			TimerObserverFactory factory = new TimerObserverFactory(10);
			
			EventObserver eventObserver = factory.MakeObserver(patternContext, null, null);
			
			Assert.IsTrue(eventObserver is TimerIntervalObserver);
		}
	}
}
