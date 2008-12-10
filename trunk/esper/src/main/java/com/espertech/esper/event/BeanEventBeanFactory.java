package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

public interface BeanEventBeanFactory
{
    public EventBean adapterForBean(Object bean, BeanEventType eventType);
}
