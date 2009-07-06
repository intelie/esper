package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

import java.util.Map;

public class WrapperEventBeanUndWriter implements EventBeanWriter
{
    private final EventBeanWriter undWriter;

    public WrapperEventBeanUndWriter(EventBeanWriter undWriter)
    {
       this.undWriter = undWriter;
    }

    public void write(Object[] values, EventBean event)
    {
        DecoratingEventBean wrappedEvent = (DecoratingEventBean) event;
        EventBean eventWrapped = wrappedEvent.getUnderlyingEvent();
        undWriter.write(values, eventWrapped);
    }
}
