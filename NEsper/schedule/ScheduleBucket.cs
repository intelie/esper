using System;

namespace net.esper.schedule
{
	/// <summary> This class acts as a buckets for sorting schedule service callbacks that are scheduled to occur at the same
	/// time. Each buckets constists of {@link ScheduleSlot} slots that callbacks are
	/// assigned to.
	/// <p>
	/// At the time of timer evaluation, callbacks that become triggerable are ordered using the bucket
	/// as the first-level order, and slot as the second-level order.
	/// <p>
	/// Each statement at statement creation time allocates a buckets, and each timer within the
	/// statement allocates a slot. Thus statements that depend on other statements (such as for insert-into),
	/// and timers within their statement (such as time window or output rate limit timers) behave
	/// deterministically.
	/// </summary>

	public class ScheduleBucket
	{
		private readonly int bucketNum;
		private int lastSlot;
		
		/// <summary> Ctor.</summary>
		/// <param name="bucketNum">is a simple integer number for this bucket by which buckets can be sorted
		/// </param>
		public ScheduleBucket(int bucketNum)
		{
			this.bucketNum = bucketNum;
			lastSlot = 0;
		}
		
		/// <summary>
        /// Restart bucket slot numbering wuch as when a statement is reStarted
        /// and new slots are allocated.
        /// </summary>
		public virtual void Restart()
		{
			lastSlot = 0;
		}
		
		/// <summary> Returns a new slot in the bucket.</summary>
		/// <returns> slot
		/// </returns>
		public virtual ScheduleSlot AllocateSlot()
		{
			return new ScheduleSlot(bucketNum, lastSlot++);
		}
	}
}
