package com.espertech.esper.support.epl;

import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.spec.OutputLimitLimitType;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.view.Viewable;

import java.util.Set;
import java.util.Iterator;
import java.util.List;

public class SupportResultSetProcessor implements ResultSetProcessor
{
    public EventType getResultEventType()
    {
        return SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public UniformPair<EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData, boolean isSynthesize)
    {
        return new UniformPair<EventBean[]>(newData, oldData);
    }

    public UniformPair<EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents, boolean isSynthesize)
    {
        return new UniformPair<EventBean[]>(newEvents.iterator().next().getArray(), oldEvents.iterator().next().getArray());
    }

    public Iterator<EventBean> getIterator(Viewable parent)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Iterator<EventBean> getIterator(Set<MultiKey<EventBean>> joinSet)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clear()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public UniformPair<EventBean[]> processOutputLimitedJoin(List<UniformPair<Set<MultiKey<EventBean>>>> joinEventsSet, boolean generateSynthetic, OutputLimitLimitType outputLimitLimitType)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public UniformPair<EventBean[]> processOutputLimitedView(List<UniformPair<EventBean[]>> viewEventsList, boolean generateSynthetic, OutputLimitLimitType outputLimitLimitType)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasAggregation() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
