using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.dispatch;
using net.esper.events;
using net.esper.view.internal_Renamed;

namespace net.esper.eql.join
{
    /// <summary>
    /// This class reacts to any new data buffered by registring with the dispatch service.
    /// When dispatched via execute, it takes the buffered events and hands these to the join
    /// execution strategy.
    /// </summary>

    public class JoinExecStrategyDispatchable : Dispatchable, BufferObserver
    {
        private readonly DispatchService dispatchService;
        private readonly JoinExecutionStrategy joinExecutionStrategy;
        private readonly EDictionary<Int32, EventBuffer> oldStreamBuffer;
        private readonly EDictionary<Int32, EventBuffer> newStreamBuffer;
        private readonly int numStreams;

        private bool isDispatchRegistered;

        /// <summary> CTor.</summary>
        /// <param name="dispatchService">- dispatches to this object via execute method
        /// </param>
        /// <param name="joinExecutionStrategy">- strategy for executing the join
        /// </param>
        /// <param name="numStreams">- number of stream
        /// </param>

        public JoinExecStrategyDispatchable(DispatchService dispatchService, JoinExecutionStrategy joinExecutionStrategy, int numStreams)
        {
            this.dispatchService = dispatchService;
            this.joinExecutionStrategy = joinExecutionStrategy;
            this.numStreams = numStreams;

            oldStreamBuffer = new EHashDictionary<Int32, EventBuffer>();
            newStreamBuffer = new EHashDictionary<Int32, EventBuffer>();
        }

        public virtual void execute()
        {
            isDispatchRegistered = false;

            EventBean[][] oldDataPerStream = new EventBean[numStreams][];
            EventBean[][] newDataPerStream = new EventBean[numStreams][];

            for (int i = 0; i < numStreams; i++)
            {
                oldDataPerStream[i] = getBufferData(oldStreamBuffer.Fetch(i, null));
                newDataPerStream[i] = getBufferData(newStreamBuffer.Fetch(i, null));
            }

            joinExecutionStrategy.join(newDataPerStream, oldDataPerStream);
        }

        private EventBean[] getBufferData(EventBuffer buffer)
        {
            if (buffer == null)
            {
                return null;
            }
            EventBean[] events = buffer.GetAndFlush();
            return events;
        }

        public virtual void newData(int streamId, EventBuffer newEventBuffer, EventBuffer oldEventBuffer)
        {
            if (!isDispatchRegistered)
            {
                dispatchService.AddInternal(this);
                isDispatchRegistered = true;
            }
            newStreamBuffer[streamId] = newEventBuffer;
            oldStreamBuffer[streamId] = oldEventBuffer;
        }
    }
}
