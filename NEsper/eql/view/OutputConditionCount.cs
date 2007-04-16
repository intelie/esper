using System;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.eql.view
{
	/// <summary>
	/// Output limit condition that is satisfied when either
	/// the total number of new events arrived or the total number
	/// of old events arrived is greater than a preset value.
	/// </summary>
	
	public sealed class OutputConditionCount : OutputCondition
	{
		private const bool DO_OUTPUT = true;
		private const bool FORCE_UPDATE = false;
		
		private readonly int eventRate;
		private int newEventsCount;
		private int oldEventsCount;
		private readonly OutputCallback outputCallback;

		/// <summary> Returns the number of new events.</summary>
		/// <returns> number of new events
		/// </returns>
		public int NewEventsCount
		{
			get
			{
				return newEventsCount;
			}
			
		}
		/// <summary> Returns the number of old events.</summary>
		/// <returns> number of old events
		/// </returns>
		public int OldEventsCount
		{
			get
			{
				return oldEventsCount;
			}
			
		}
		/// <summary> Returns the event rate.</summary>
		/// <returns> event rate
		/// </returns>
		public long EventRate
		{
			get
			{
				return eventRate;
			}
			
		}
		private bool Satisfied
		{
			get
			{
				return (newEventsCount >= eventRate) || (oldEventsCount >= eventRate);
			}
			
		}
	
		/// <summary> Constructor.</summary>
		/// <param name="eventRate">is the number of old or new events that
		/// must arrive in order for the condition to be satisfied
		/// </param>
		/// <param name="outputCallback">is the callback that is made when the conditoin is satisfied
		/// </param>
		public OutputConditionCount(int eventRate, OutputCallback outputCallback)
		{
			if (eventRate < 1)
			{
				throw new ArgumentException("Limiting output by event count requires an event count of at least 1");
			}
			if (outputCallback == null)
			{
				throw new System.NullReferenceException("Output condition by count requires a non-null callback");
			}
			this.eventRate = eventRate;
			this.outputCallback = outputCallback;
		}

        /// <summary>
        /// Updates the output condition.
        /// </summary>
        /// <param name="newDataCount">The new data count.</param>
        /// <param name="oldDataCount">The old data count.</param>
		public void UpdateOutputCondition(int newDataCount, int oldDataCount)
		{
			this.newEventsCount += newDataCount;
			this.oldEventsCount += oldDataCount;
			
			if (log.IsDebugEnabled)
			{
				log.Debug(".updateBatchCondition, " + "  newEventsCount==" + newEventsCount + "  oldEventsCount==" + oldEventsCount);
			}
			
			if (Satisfied)
			{
				log.Debug(".updateOutputCondition() condition satisfied");
				this.newEventsCount = 0;
				this.oldEventsCount = 0;
				outputCallback(DO_OUTPUT, FORCE_UPDATE);
			}
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return this.GetType().FullName + " eventRate=" + eventRate;
		}
		
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
