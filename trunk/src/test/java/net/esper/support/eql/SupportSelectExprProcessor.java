package net.esper.support.eql;

import net.esper.eql.core.SelectExprProcessor;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;

public class SupportSelectExprProcessor implements SelectExprProcessor
{
    public EventType getResultEventType()
    {
        return SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public EventBean process(EventBean[] eventsPerStream)
    {
        return eventsPerStream[0];
    }
}
