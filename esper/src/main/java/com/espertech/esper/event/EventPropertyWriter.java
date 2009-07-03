package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

public interface EventPropertyWriter
{
    public void write(Object value, EventBean target);
}
