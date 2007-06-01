using System;

namespace net.esper.adapter
{
	/// <summary> 
	/// A comparator that orders SendableEvents first on sendTime, and
	/// then on schedule slot.
	/// </summary>

    public class SendableEventComparator : Comparator<SendableEvent>
	{
		public int Compare(SendableEvent one, SendableEvent two)
		{
			if(one.SendTime < two.SendTime)
			{
				return -1;
			}
			else if(one.SendTime > two.SendTime)
			{
				return 1;
			}
			else
			{
				return one.ScheduleSlot.CompareTo(two.ScheduleSlot);
			}
		}
	}
}