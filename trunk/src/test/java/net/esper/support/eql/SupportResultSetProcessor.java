package net.esper.support.eql;

import net.esper.eql.expression.ResultSetProcessor;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.event.BeanEventAdapter;
import net.esper.collection.Pair;
import net.esper.collection.MultiKey;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;

import java.util.Set;

public class SupportResultSetProcessor implements ResultSetProcessor
{
    public EventType getResultEventType()
    {
        return SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        return new Pair<EventBean[], EventBean[]>(newData, oldData);
    }

    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        return new Pair<EventBean[], EventBean[]>(newEvents.iterator().next().getArray(), oldEvents.iterator().next().getArray());
    }
}
