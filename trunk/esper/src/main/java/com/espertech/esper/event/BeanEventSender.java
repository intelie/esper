package com.espertech.esper.event;

import com.espertech.esper.client.EventSender;
import com.espertech.esper.client.EPException;
import com.espertech.esper.core.EPRuntimeSPI;

public class BeanEventSender implements EventSender
{
    private final EPRuntimeSPI runtime;
    private final BeanEventType beanEventType;

    public BeanEventSender(EPRuntimeSPI runtime, BeanEventType beanEventType)
    {
        this.runtime = runtime;
        this.beanEventType = beanEventType;
    }

    public void sendEvent(Object event)
    {
        if (event.getClass() != beanEventType.getUnderlyingType())
        {
            // TODO - test
            throw new EPException("");
        }

        runtime.processWrappedEvent(new BeanEventBean(event, beanEventType));        
    }
}
