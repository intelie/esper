package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

public interface EventBeanCopyMethod
{
    public EventBean copy(EventBean event);
}
