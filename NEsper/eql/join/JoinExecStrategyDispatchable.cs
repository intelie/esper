using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.core;
using net.esper.dispatch;
using net.esper.events;
using net.esper.view.internals;

namespace net.esper.eql.join
{
    /// <summary>
    /// This class reacts to any new data buffered by registring with the dispatch service.
    /// When dispatched via execute, it takes the buffered events and hands these to the join
    /// execution strategy.
    /// </summary>

    public class JoinExecStrategyDispatchable
		: EPStatementDispatch
        , BufferObserver
    {
        private readonly JoinExecutionStrategy joinExecutionStrategy;
        private readonly EDictionary<Int32, FlushedEventBuffer> oldStreamBuffer;
        private readonly EDictionary<Int32, FlushedEventBuffer> newStreamBuffer;
        private readonly int numStreams;

        private bool hasNewData;

        /// <summary> CTor.</summary>
        /// <param name="joinExecutionStrategy">strategy for executing the join</param>
        /// <param name="numStreams">number of stream</param>

        public JoinExecStrategyDispatchable(JoinExecutionStrategy joinExecutionStrategy, int numStreams)
        {
            this.joinExecutionStrategy = joinExecutionStrategy;
            this.numStreams = numStreams;

            oldStreamBuffer = new HashDictionary<Int32, FlushedEventBuffer>();
            newStreamBuffer = new HashDictionary<Int32, FlushedEventBuffer>();
        }

        /// <summary>
        /// Execute pending dispatchable items.
        /// </summary>
        public virtual void Execute()
        {
            if (!hasNewData)
	        {
	            return;
	        }
	        hasNewData = false;

            EventBean[][] oldDataPerStream = new EventBean[numStreams][];
            EventBean[][] newDataPerStream = new EventBean[numStreams][];

            for (int i = 0; i < numStreams; i++)
            {
                oldDataPerStream[i] = GetBufferData(oldStreamBuffer.Fetch(i, null));
                newDataPerStream[i] = GetBufferData(newStreamBuffer.Fetch(i, null));
            }

            joinExecutionStrategy.Join(newDataPerStream, oldDataPerStream);
        }

        private static EventBean[] GetBufferData(FlushedEventBuffer buffer)
        {
            if (buffer == null)
            {
                return null;
            }
            EventBean[] events = buffer.GetAndFlush();
            return events;
        }

        /// <summary>
        /// Receive new and old events from a stream.
        /// </summary>
        /// <param name="streamId">the stream number sending the events</param>
        /// <param name="newEventBuffer">buffer for new events</param>
        /// <param name="oldEventBuffer">buffer for old events</param>
        public virtual void NewData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
        {
			hasNewData = true;
            newStreamBuffer[streamId] = newEventBuffer;
            oldStreamBuffer[streamId] = oldEventBuffer;
        }
    }
}
