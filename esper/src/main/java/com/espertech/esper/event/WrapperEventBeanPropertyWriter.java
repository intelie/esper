package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

public class WrapperEventBeanPropertyWriter implements EventBeanWriter
{
    private final EventPropertyWriter[] writerArr;

    public WrapperEventBeanPropertyWriter(EventPropertyWriter[] writerArr)
    {
        this.writerArr = writerArr;
    }

    public void write(Object[] values, EventBean event)
    {
        for (int i = 0; i < values.length; i++)
        {
            writerArr[i].write(values[i], event);
        }
    }
}
