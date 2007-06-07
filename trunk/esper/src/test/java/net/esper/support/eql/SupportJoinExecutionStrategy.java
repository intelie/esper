package net.esper.support.eql;

import net.esper.eql.join.JoinExecutionStrategy;
import net.esper.event.EventBean;

public class SupportJoinExecutionStrategy implements JoinExecutionStrategy
{
    private EventBean[][] lastNewDataPerStream;
    private EventBean[][] lastOldDataPerStream;

    public void join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream)
    {
        lastNewDataPerStream = newDataPerStream;
        lastOldDataPerStream = oldDataPerStream;
    }

    public EventBean[][] getLastNewDataPerStream()
    {
        return lastNewDataPerStream;
    }

    public EventBean[][] getLastOldDataPerStream()
    {
        return lastOldDataPerStream;
    }
}
