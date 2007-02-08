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

        /**
         * CTor.
         * @param forStream - stream the strategy is for
         * @param numStreams - number of streams in total
         * @param execNode - execution node for building join tuple set
         */
        public ExecNodeQueryStrategy(int forStream, int numStreams, ExecNode execNode)
        {
            this.forStream = forStream;
            this.numStreams = numStreams;
            this.execNode = execNode;
        }

        public void lookup(EventBean[] lookupEvents, ISet<MultiKey<EventBean>> joinSet)
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
                IList<EventBean[]> results = new ELinkedList<EventBean[]>();
                execNode.Process(ev, prototype, results);

                // Convert results into unique set
                foreach (EventBean[] row in results)
                {
                    joinSet.Add(new MultiKey<EventBean>(row));
                }
            }
        }

        /**
         * Return stream number this strategy is for.
         * @return stream num
         */
        public int getForStream()
        {
            return forStream;
        }

        /**
         * Returns the total number of streams.
         * @return number of streams
         */
        public int NumStreams
        {
            get { return numStreams; }
        }

        /**
         * Returns execution node.
         * @return execution node
         */
        public ExecNode ExecNode
        {
			get { return execNode; }
        }
    }
}
