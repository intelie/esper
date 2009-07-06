package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

public interface EventBeanWriter
{
    public void write(Object[] values, EventBean event);
}
