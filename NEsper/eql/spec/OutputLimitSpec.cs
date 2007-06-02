using System;

namespace net.esper.eql.spec
{
	/// <summary>
    /// Spec for building an EventBatch.
	/// </summary>

    public class OutputLimitSpec
	{
        /// <summary>
        /// Determines how many items are displayed within an event batch.
        /// </summary>

		public enum DisplayLimit
		{
            /// <summary> Output first event.</summary>
            FIRST,
            /// <summary> Output last event.</summary>
            LAST,
            /// <summary> Output all events.</summary>
			ALL
		}

		/// <summary>
        /// Gets the event rate.
        /// </summary>
		
        virtual public int EventRate
		{
			get { return eventRate; }
		}

		/// <summary>
        /// Gets the number of events, or zero if no number of events was supplied.
        /// </summary>
		
        virtual public bool EventLimit
		{
			get { return isEventLimit; }
		}
		
        /// <summary>
        /// Gets the rate in seconds, if supplied, or zero if not supplied.
        /// </summary>
		
        virtual public double TimeRate
		{
			get { return timeRate; }
		}

		/// <summary>
        /// Returns true to output the last event only.
        /// </summary>
		
        virtual public bool IsDisplayLastOnly
		{
			get { return displayLimit == DisplayLimit.LAST; }
		}

        /// <summary>
        /// Returns true to output the first event only.
        /// </summary>
		
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
		/// <param name="eventRate">the number of events to batch.</param>
		/// <param name="displayLimit">indicates whether to output only the first, only the last, or all events</param>
		
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
		/// <param name="timeRate">the number of seconds to batch for.
		/// </param>
		/// <param name="displayLimit">indicates whether to output only the first, only the last, or all events
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