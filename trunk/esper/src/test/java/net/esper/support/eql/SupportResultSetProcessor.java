package net.esper.support.eql;

import net.esper.eql.core.ResultSetProcessor;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.collection.Pair;
import net.esper.collection.MultiKey;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.view.Viewable;

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
