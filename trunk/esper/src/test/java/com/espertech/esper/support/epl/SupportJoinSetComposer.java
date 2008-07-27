package com.espertech.esper.support.epl;

import com.espertech.esper.epl.join.JoinSetComposer;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.UniformPair;

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

    public Set<MultiKey<EventBean>> staticJoin()
    {
        return null;
    }

    public void destroy()
    {        
    }
}
