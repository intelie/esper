using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.pattern.observer
{
	
	[TestFixture]
	public class TestObserverEnum 
	{
		[Test]
		public virtual void  testForName()
		{
			ObserverEnum enumValue = ObserverEnum.ForName(ObserverEnum.TIMER_INTERVAL.Namespace, ObserverEnum.TIMER_INTERVAL.Name);
			Assert.AreEqual(enumValue, ObserverEnum.TIMER_INTERVAL);
			
			enumValue = ObserverEnum.ForName(ObserverEnum.TIMER_INTERVAL.Namespace, "dummy");
			Assert.IsNull(enumValue);
			
			enumValue = ObserverEnum.ForName("dummy", ObserverEnum.TIMER_INTERVAL.Name);
			Assert.IsNull(enumValue);
		}
	}
}
