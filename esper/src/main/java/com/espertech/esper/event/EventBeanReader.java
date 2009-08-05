package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

public interface EventBeanReader
{
    public Object[] read(EventBean event);
}
