using System;

namespace net.esper.schedule
{
	/// <summary>
    /// This class is a slot in a {@link ScheduleBucket} for sorting schedule service callbacks.
    /// </summary>

    public class ScheduleSlot : IComparable<ScheduleSlot>
	{
		private int bucketNum;
		private int slotNum;
		
		/// <summary> Ctor.</summary>
		/// <param name="bucketNum">is the number of the bucket the slot belongs to
		/// </param>
		/// <param name="slotNum">is the slot number for ordering within the bucket
		/// </param>
		public ScheduleSlot(int bucketNum, int slotNum)
		{
			this.bucketNum = bucketNum;
			this.slotNum = slotNum;
		}
		
		public virtual int CompareTo(ScheduleSlot scheduleCallbackSlot)
		{
			if (this.bucketNum > scheduleCallbackSlot.bucketNum)
			{
				return 1;
			}
			if (this.bucketNum < scheduleCallbackSlot.bucketNum)
			{
				return - 1;
			}
			if (this.slotNum > scheduleCallbackSlot.slotNum)
			{
				return 1;
			}
			if (this.slotNum < scheduleCallbackSlot.slotNum)
			{
				return - 1;
			}
			
			return 0;
		}

        public virtual int CompareTo(Object obj)
		{
            return CompareTo(obj as ScheduleSlot);
		}
	}
}