using System;

using net.esper.eql.spec;
using net.esper.core;

using org.apache.commons.logging;

namespace net.esper.eql.view
{
	/// <summary>
    /// Factory for output condition instances.
    /// </summary>
	
    public sealed class OutputConditionFactory
	{
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
		
		/// <summary> Creates an output condition instance.</summary>
		/// <param name="outputLimitSpec">specifies what kind of condition to create
		/// </param>
		/// <param name="statementContext">supplies the services required such as for scheduling callbacks
		/// </param>
		/// <param name="outputCallback">is the method to invoke for output
		/// </param>
		/// <returns> instance for performing output
		/// </returns>
		public static OutputCondition CreateCondition(
			OutputLimitSpec outputLimitSpec, 
			StatementContext statementContext,
			OutputCallback outputCallback)
		{
			if (outputCallback == null)
			{
				throw new System.NullReferenceException("Output condition by count requires a non-null callback");
			}
			
			if (outputLimitSpec == null)
			{
				return new OutputConditionNull(outputCallback);
			}
			else if (outputLimitSpec.IsDisplayFirstOnly)
			{
				log.Debug(".createCondition creating OutputConditionFirst");
				return new OutputConditionFirst(outputLimitSpec, statementContext, outputCallback);
			}
			if (outputLimitSpec.EventLimit)
			{
				log.Debug(".createCondition creating OutputConditionCount with event rate " + outputLimitSpec.EventRate);
				return new OutputConditionCount(outputLimitSpec.EventRate, outputCallback);
			}
			else
			{
				log.Debug(".createCondition creating OutputConditionTime with interval length " + outputLimitSpec.TimeRate);
				return new OutputConditionTime(outputLimitSpec.TimeRate, statementContext, outputCallback);
			}
		}
	}
}
