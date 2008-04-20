package com.espertech.esper.core;

import com.espertech.esper.event.EventBean;

public interface EPRuntimeEventSender
{
    public void processWrappedEvent(EventBean eventBean);   
}
