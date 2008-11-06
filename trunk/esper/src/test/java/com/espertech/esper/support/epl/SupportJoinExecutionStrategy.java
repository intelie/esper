package com.espertech.esper.support.epl;

import com.espertech.esper.epl.join.JoinExecutionStrategy;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.MultiKey;

import java.util.Set;

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

    public Set<MultiKey<EventBean>> staticJoin()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
