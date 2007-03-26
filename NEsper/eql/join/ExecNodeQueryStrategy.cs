using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.join.exec;
using net.esper.events;

namespace net.esper.eql.join
{
	/// <summary>
    /// Query strategy for building a join tuple set by using an execution node tree.
    /// </summary>
    
    public class ExecNodeQueryStrategy : QueryStrategy
    {
        private int forStream;
        private int numStreams;
        private ExecNode execNode;

        /// <summary>
        /// Initializes a new instance of the <see cref="ExecNodeQueryStrategy"/> class.
        /// </summary>
        /// <param name="forStream">stream the strategy is for.</param>
        /// <param name="numStreams">number of streams in total.</param>
        /// <param name="execNode">execution node for building join tuple set.</param>
        public ExecNodeQueryStrategy(int forStream, int numStreams, ExecNode execNode)
        {
            this.forStream = forStream;
            this.numStreams = numStreams;
            this.execNode = execNode;
        }

        /// <summary>
        /// Look up events returning tuples of joined events.
        /// </summary>
        /// <param name="lookupEvents">events to use to perform the join</param>
        /// <param name="joinSet">result join tuples of events</param>
        public void Lookup(EventBean[] lookupEvents, ISet<MultiKey<EventBean>> joinSet)
        {
            if (lookupEvents == null)
            {
                return;
            }

            foreach (EventBean ev in lookupEvents)
            {
                // Set up prototype row
                EventBean[] prototype = new EventBean[numStreams];
                prototype[forStream] = ev;

                // Perform execution
                IList<EventBean[]> results = new List<EventBean[]>();
                execNode.Process(ev, prototype, results);

                // Convert results into unique set
                foreach (EventBean[] row in results)
                {
                    joinSet.Add(new MultiKey<EventBean>(row));
                }
            }
        }

        /// <summary>Return stream number this strategy is for.</summary>
        /// <returns>stream num</returns>

        public int getForStream()
        {
            return forStream;
        }

        /// <summary>Returns the total number of streams.</summary>
        /// <returns>number of streams</returns>

        public int NumStreams
        {
            get { return numStreams; }
        }

        /// <summary>Returns execution node.</summary>
        /// <returns>execution node</returns>

        public ExecNode ExecNode
        {
			get { return execNode; }
        }
    }
}
