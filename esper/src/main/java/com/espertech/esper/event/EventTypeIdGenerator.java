package com.espertech.esper.event;

import com.espertech.esper.event.bean.BeanEventType;

public interface EventTypeIdGenerator {
    public int getTypeId(String eventTypeName);
    public void assignedType(String name, BeanEventType eventType);
}
