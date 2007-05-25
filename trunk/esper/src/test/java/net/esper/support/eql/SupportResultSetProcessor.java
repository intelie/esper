package net.esper.support.eql;

import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.core.ResultSetProcessorResult;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;

import java.util.Set;

public class SupportResultSetProcessor implements ResultSetProcessor
{
    private ResultSetProcessorResult cachedResult;

    public EventType getResultEventType()
    {
        return SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public ResultSetProcessorResult processViewResult(EventBean[] newData, EventBean[] oldData)
    {
        return new ResultSetProcessorResult(newData, oldData);
    }

    public ResultSetProcessorResult processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        return new ResultSetProcessorResult(newEvents.iterator().next().getArray(), oldEvents.iterator().next().getArray());
    }

    public boolean addViewResult(EventBean[] newData, EventBean[] oldData)
    {
        cachedResult = new ResultSetProcessorResult(newData, oldData);
        return true;
    }

    public boolean addJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        cachedResult = new ResultSetProcessorResult(newEvents.iterator().next().getArray(), oldEvents.iterator().next().getArray());
        return true;
    }

    public ResultSetProcessorResult generateResult()
    {
        return cachedResult;
    }    
}
