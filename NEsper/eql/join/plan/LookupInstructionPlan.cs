using System;

using net.esper.compat;
using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.plan
{
    /// <summary>
    /// Plan for lookup using a from-stream event looking up one or more to-streams
    /// using a specified lookup plan for each to-stream.
    /// </summary>

    public class LookupInstructionPlan
    {
        private readonly int fromStream;
        private readonly String fromStreamName;
        private readonly int[] toStreams;
        private readonly TableLookupPlan[] lookupPlans;
        private readonly bool[] requiredPerStream;

        /// <summary> Ctor.</summary>
        /// <param name="fromStream">the stream supplying the lookup event
        /// </param>
        /// <param name="fromStreamName">the stream name supplying the lookup event
        /// </param>
        /// <param name="toStreams">the set of streams to look up in
        /// </param>
        /// <param name="lookupPlans">the plan to use for each stream to look up in
        /// </param>
        /// <param name="requiredPerStream">indicates which of the lookup streams are required to build a result and which are not
        /// </param>
        public LookupInstructionPlan(int fromStream, String fromStreamName, int[] toStreams, TableLookupPlan[] lookupPlans, bool[] requiredPerStream)
        {
            if (toStreams.Length != lookupPlans.Length)
            {
                throw new ArgumentException("Invalid number of lookup plans for each stream");
            }
            if (requiredPerStream.Length < lookupPlans.Length)
            {
                throw new ArgumentException("Invalid required per stream array");
            }
            if ((fromStream < 0) || (fromStream >= requiredPerStream.Length))
            {
                throw new ArgumentException("Invalid from stream");
            }

            this.fromStream = fromStream;
            this.fromStreamName = fromStreamName;
            this.toStreams = toStreams;
            this.lookupPlans = lookupPlans;
            this.requiredPerStream = requiredPerStream;
        }

        /// <summary> Constructs the executable from the plan.</summary>
        /// <param name="indexesPerStream">is the index objects for use in lookups
        /// </param>
        /// <param name="streamTypes">is the types of each stream
        /// </param>
        /// <returns> executable instruction
        /// </returns>
        public virtual LookupInstructionExec MakeExec(EventTable[][] indexesPerStream, EventType[] streamTypes)
        {
            TableLookupStrategy[] strategies = new TableLookupStrategy[lookupPlans.Length];
            for (int i = 0; i < lookupPlans.Length; i++)
            {
                strategies[i] = lookupPlans[i].MakeStrategy(indexesPerStream, streamTypes);
            }
            return new LookupInstructionExec(fromStream, fromStreamName, toStreams, strategies, requiredPerStream);
        }

        /// <summary> Output the planned instruction.</summary>
        /// <param name="writer">to output to
        /// </param>
        public virtual void Print(IndentWriter writer)
        {
            writer.WriteLine(
                "LookupInstructionPlan" +
                " fromStream=" + fromStream +
                " fromStreamName=" + fromStreamName +
                " toStreams=" + CollectionHelper.Render(toStreams));

            writer.IncrIndent();
            for (int i = 0; i < lookupPlans.Length; i++)
            {
                writer.WriteLine("plan " + i + " :" + lookupPlans[i].ToString());
            }
            writer.DecrIndent();
        }
    }
}
