using System;

using net.esper.eql.spec;
using net.esper.core;

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
        /// <param name="statementContext">supplies the services required such as for scheduling callbacks
        /// </param>
        /// <param name="outputCallback">is the method to invoke for output
        /// </param>
        public OutputConditionFirst(OutputLimitSpec outputLimitSpec, StatementContext statementContext, OutputCallback outputCallback)
        {
            if (outputCallback == null)
            {
                throw new System.NullReferenceException("Output condition by count requires a non-null callback");
            }
            this.outputCallback = outputCallback;
            OutputLimitSpec innerSpec = CreateInnerSpec(outputLimitSpec);
            OutputCallback localCallback = CreateCallbackToLocal();
            this.innerCondition = OutputConditionFactory.CreateCondition(innerSpec, statementContext, localCallback);
            this.witnessedFirst = false;
        }

        /// <summary>
        /// Update the output condition.
        /// </summary>
        /// <param name="newEventsCount">number of new events incoming</param>
        /// <param name="oldEventsCount">number of old events incoming</param>
        public virtual void UpdateOutputCondition(int newEventsCount, int oldEventsCount)
        {
            if (!witnessedFirst)
            {
                witnessedFirst = true;
                bool doOutput = true;
                bool forceUpdate = false;
                outputCallback(doOutput, forceUpdate);
            }
            innerCondition.UpdateOutputCondition(newEventsCount, oldEventsCount);
        }

        private static OutputLimitSpec CreateInnerSpec(OutputLimitSpec outputLimitSpec)
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

        private OutputCallback CreateCallbackToLocal()
        {
            return new OutputCallback(continueOutputProcessing);
        }

        private void ContinueOutputProcessing(bool doOutput, bool forceUpdate)
        {
            doOutput = !witnessedFirst;
            outputCallback(doOutput, forceUpdate);
            witnessedFirst = false;
        }
    }
}
