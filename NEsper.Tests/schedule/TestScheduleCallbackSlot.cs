using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.schedule
{
	
	[TestFixture]
	public class TestScheduleCallbackSlot 
	{
		[Test]
		public virtual void  testCompare()
		{
			ScheduleSlot[] slots = new ScheduleSlot[10];
			slots[0] = new ScheduleSlot(1, 1);
			slots[1] = new ScheduleSlot(1, 2);
			slots[2] = new ScheduleSlot(2, 1);
			slots[3] = new ScheduleSlot(2, 2);
			
			Assert.AreEqual(- 1, slots[0].CompareTo(slots[1]));
			Assert.AreEqual(1, slots[1].CompareTo(slots[0]));
			Assert.AreEqual(0, slots[0].CompareTo(slots[0]));
			
			Assert.AreEqual(- 1, slots[0].CompareTo(slots[2]));
			Assert.AreEqual(- 1, slots[1].CompareTo(slots[2]));
			Assert.AreEqual(1, slots[2].CompareTo(slots[0]));
			Assert.AreEqual(1, slots[2].CompareTo(slots[1]));
		}
	}
}
