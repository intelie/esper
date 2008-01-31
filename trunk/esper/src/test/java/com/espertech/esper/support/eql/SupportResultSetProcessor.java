package com.espertech.esper.support.eql;

import com.espertech.esper.eql.core.ResultSetProcessor;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.view.Viewable;

import java.util.Set;
import java.util.Iterator;

public class SupportResultSetProcessor implements ResultSetProcessor
{
    public EventType getResultEventType()
    {
        return SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData, boolean isSynthesize)
    {
        return new Pair<EventBean[], EventBean[]>(newData, oldData);
    }

    public Pair<EventBean[], EventBean[]> processJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents, boolean isSynthesize)
    {
        return new Pair<EventBean[], EventBean[]>(newEvents.iterator().next().getArray(), oldEvents.iterator().next().getArray());
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
}
