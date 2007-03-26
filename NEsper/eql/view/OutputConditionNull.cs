using System;

namespace net.esper.eql.view
{
	/// <summary>
	/// An empty output condition that is always satisfied.
	/// </summary>
	
	public class OutputConditionNull : OutputCondition
	{
		private const bool DO_OUTPUT = true;
		private const bool FORCE_UPDATE = false;
		
		private readonly OutputCallback outputCallback;
		
		/// <summary> Ctor.</summary>
		/// <param name="outputCallback">is the callback to make once the condition is satisfied
		/// </param>
		public OutputConditionNull(OutputCallback outputCallback)
		{
			if (outputCallback == null)
			{
				throw new System.NullReferenceException("Output condition requires a non-null callback");
			}
			this.outputCallback = outputCallback;
		}

        /// <summary>
        /// Update the output condition.
        /// </summary>
        /// <param name="newEventsCount">number of new events incoming</param>
        /// <param name="oldEventsCount">number of old events incoming</param>
		public virtual void UpdateOutputCondition(int newEventsCount, int oldEventsCount)
		{
			outputCallback(DO_OUTPUT, FORCE_UPDATE);
		}
	}
}