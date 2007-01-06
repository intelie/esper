package net.esper.eql.join;

import net.esper.dispatch.Dispatchable;
import net.esper.view.internal.BufferObserver;
import net.esper.event.EventBean;
import net.esper.collection.FlushedEventBuffer;

import java.util.Map;
import java.util.HashMap;

/**
 * This class reacts to any new data buffered by registring with the dispatch service.
 * When dispatched via execute, it takes the buffered events and hands these to the join execution strategy.
 */
public class JoinExecStrategyDispatchable implements Dispatchable, BufferObserver
{
    private final JoinExecutionStrategy joinExecutionStrategy;
    private final Map<Integer, FlushedEventBuffer> oldStreamBuffer;
    private final Map<Integer, FlushedEventBuffer> newStreamBuffer;
    private final int numStreams;

    private boolean hasNewData;

    /**
     * CTor.
     * @param joinExecutionStrategy - strategy for executing the join
     * @param numStreams - number of stream
     */
    public JoinExecStrategyDispatchable(JoinExecutionStrategy joinExecutionStrategy, int numStreams)
    {
        this.joinExecutionStrategy = joinExecutionStrategy;
        this.numStreams = numStreams;

        oldStreamBuffer = new HashMap<Integer, FlushedEventBuffer>();
        newStreamBuffer = new HashMap<Integer, FlushedEventBuffer>();
    }

    public void execute()
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
            oldDataPerStream[i] = getBufferData(oldStreamBuffer.get(i));
            newDataPerStream[i] = getBufferData(newStreamBuffer.get(i));
        }

        joinExecutionStrategy.join(newDataPerStream, oldDataPerStream);
    }

    private static EventBean[] getBufferData(FlushedEventBuffer buffer)
    {
        if (buffer == null)
        {
            return null;
        }
        EventBean[] events = buffer.getAndFlush();
        return events;
    }

    public void newData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
    {
        hasNewData = true;
        newStreamBuffer.put(streamId, newEventBuffer);
        oldStreamBuffer.put(streamId, oldEventBuffer);
    }
}
