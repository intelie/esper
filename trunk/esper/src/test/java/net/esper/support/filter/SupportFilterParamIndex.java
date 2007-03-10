package net.esper.support.filter;

import net.esper.filter.*;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.Collection;

public class SupportFilterParamIndex extends FilterParamIndexPropBase
{
    public SupportFilterParamIndex()
    {
        super("intPrimitive", FilterOperator.EQUAL, SupportEventTypeFactory.createBeanType(SupportBean.class));
    }

    protected EventEvaluator get(Object expressionValue)
    {
        return null;
    }

    protected void put(Object expressionValue, EventEvaluator evaluator)
    {
    }

    protected boolean remove(Object expressionValue)
    {
        return true;
    }

    protected int size()
    {
        return 0;
    }

    protected ReadWriteLock getReadWriteLock()
    {
        return null;
    }

    public void matchEvent(EventBean event, Collection<FilterHandle> matches)
    {
    }
}
