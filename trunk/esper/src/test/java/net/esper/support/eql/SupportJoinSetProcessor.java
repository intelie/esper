package net.esper.support.eql;

import net.esper.eql.join.JoinSetProcessor;
import net.esper.event.EventBean;
import net.esper.collection.MultiKey;

import java.util.Set;

public class SupportJoinSetProcessor implements JoinSetProcessor
{
    private Set<MultiKey<EventBean>> lastNewEvents;
    private Set<MultiKey<EventBean>> lastOldEvents;

    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        lastNewEvents = newEvents;
        lastOldEvents = oldEvents;
    }

    public Set<MultiKey<EventBean>> getLastNewEvents()
    {
        return lastNewEvents;
    }

    public Set<MultiKey<EventBean>> getLastOldEvents()
    {
        return lastOldEvents;
    }

    public void reset()
    {
        lastNewEvents = null;
        lastOldEvents = null;
    }
}
