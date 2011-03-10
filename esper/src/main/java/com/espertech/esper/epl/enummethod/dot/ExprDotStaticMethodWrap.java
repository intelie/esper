package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;

import java.util.Collection;

public interface ExprDotStaticMethodWrap {
    public EventType getEventType();
    public Collection<EventBean> convert(Object result);
}



