package net.esper.support.eql;

import net.esper.eql.join.JoinSetComposer;
import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.collection.UniformPair;

import java.util.Set;

public class SupportJoinSetComposer implements JoinSetComposer
{
    private UniformPair<Set<MultiKey<EventBean>>> result;

    public SupportJoinSetComposer(UniformPair<Set<MultiKey<EventBean>>> result)
    {
        this.result = result;
    }

    public void init(EventBean[][] eventsPerStream)
    {        
    }

    public UniformPair<Set<MultiKey<EventBean>>> join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream)
    {
        return result;
    }
}
