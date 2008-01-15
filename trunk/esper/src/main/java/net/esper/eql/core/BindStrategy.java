package net.esper.eql.core;

import net.esper.event.EventBean;

public interface BindStrategy
{
    public Object[] process(EventBean[] eventsPerStream, boolean isNewData);
}
