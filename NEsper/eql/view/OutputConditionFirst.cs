using System;

using net.esper.eql.spec;
using net.esper.view;

namespace net.esper.eql.view
{
	/// <summary> An output condition that is satisfied at the first event
	/// of either a time-based or count-based batch.
	/// </summary>
	public class OutputConditionFirst : OutputCondition
	{
		private readonly OutputCallback outputCallback;
		private readonly OutputCondition innerCondition;
		private bool witnessedFirst;
		
		/// <summary> Ctor.</summary>
		/// <param name="outputLimitSpec">specifies what kind of condition to create
		/// </param>
		/// <param name="viewContext">supplies the services required such as for scheduling callbacks
		/// </param>
		/// <param name="outputCallback">is the method to invoke for output
		/// </param>
		public OutputConditionFirst(OutputLimitSpec outputLimitSpec, ViewServiceContext viewContext, OutputCallback outputCallback)
		{
			if (outputCallback == null)
			{
				throw new System.NullReferenceException("Output condition by count requires a non-null callback");
			}
			this.outputCallback = outputCallback;
			OutputLimitSpec innerSpec = createInnerSpec(outputLimitSpec);
            OutputCallback localCallback = createCallbackToLocal();
			this.innerCondition = OutputConditionFactory.createCondition(innerSpec, viewContext, localCallback);
			this.witnessedFirst = false;
		}
		
		public virtual void  updateOutputCondition(int newEventsCount, int oldEventsCount)
		{
			if (!witnessedFirst)
			{
				witnessedFirst = true;
				bool doOutput = true;
				bool forceUpdate = false;
				outputCallback(doOutput, forceUpdate);
			}
			innerCondition.updateOutputCondition(newEventsCount, oldEventsCount);
		}
		
		private OutputLimitSpec createInnerSpec(OutputLimitSpec outputLimitSpec)
		{
			if (outputLimitSpec.EventLimit)
			{
				return new OutputLimitSpec(outputLimitSpec.EventRate, OutputLimitSpec.DisplayLimit.ALL);
			}
			else
			{
				return new OutputLimitSpec(outputLimitSpec.TimeRate, OutputLimitSpec.DisplayLimit.ALL);
			}
		}
		
		private OutputCallback createCallbackToLocal()
		{
            return new OutputCallback(continueOutputProcessing);
		}
		
		private void  continueOutputProcessing(bool doOutput, bool forceUpdate)
		{
			doOutput = !witnessedFirst;
			outputCallback(doOutput, forceUpdate);
			witnessedFirst = false;
		}
	}
}
