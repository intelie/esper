package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

public interface EventBeanManufacturer
{
    public EventBean make(Object[] properties);
}
