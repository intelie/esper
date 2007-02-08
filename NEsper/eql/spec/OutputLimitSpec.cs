using System;
namespace net.esper.eql.spec
{
	/// <summary>
    /// Spec for building an EventBatch.
	/// </summary>

    public class OutputLimitSpec
	{
		public enum DisplayLimit
		{
            /// <summary> Output first event.</summary>
            FIRST,
            /// <summary> Output last event.</summary>
            LAST,
            /// <summary> Output all events.</summary>
			ALL
		}

		/// <summary> Returns the event rate.</summary>
		/// <returns> event rate
		/// </returns>
		virtual public int EventRate
		{
			get { return eventRate; }
		}

		/// <summary> Returns the number of events, or zero if no number of events was supplied.</summary>
		/// <returns> event limit
		/// </returns>
		
        virtual public bool EventLimit
		{
			get { return isEventLimit; }
		}
		
        /// <summary> Returns the rate in seconds, if supplied, or zero if not supplied.</summary>
		/// <returns> rate
		/// </returns>
		
        virtual public double TimeRate
		{
			get { return timeRate; }
		}

		/// <summary> Returns true to output the last event only.</summary>
		/// <returns> true if last only, false otherwise
		/// </returns>
		
        virtual public bool IsDisplayLastOnly
		{
			get { return displayLimit == DisplayLimit.LAST; }
		}

        /// <summary> Returns true to output the first event only.</summary>
		/// <returns> true if first only, false otherwise
		/// </returns>
		
        virtual public bool IsDisplayFirstOnly
		{
			get { return displayLimit == DisplayLimit.FIRST; }
			
		}
		
		private readonly bool isEventLimit;
		private readonly DisplayLimit displayLimit;
		
		private readonly int eventRate;
		private readonly double timeRate;
		
		/// <summary> Ctor.
		/// For batching events by event count.
		/// </summary>
		/// <param name="eventRate">- the number of events to batch.
		/// </param>
		/// <param name="displayLimit">- indicates whether to output only the first, only the last, or all events
		/// </param>
		
        public OutputLimitSpec(int eventRate, DisplayLimit displayLimit)
		{
			this.isEventLimit = true;
			this.eventRate = eventRate;
			this.timeRate = - 1.0;
			this.displayLimit = displayLimit;
		}
		
		/// <summary> Ctor.
		/// Used for creating batching events by time.
		/// </summary>
		/// <param name="timeRate">- the number of seconds to batch for.
		/// </param>
		/// <param name="displayLimit">- indicates whether to output only the first, only the last, or all events
		/// </param>
		
        public OutputLimitSpec(double timeRate, DisplayLimit displayLimit)
		{
			this.isEventLimit = false;
			this.timeRate = timeRate;
			this.eventRate = - 1;
			this.displayLimit = displayLimit;
		}
	}
}