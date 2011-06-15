package com.espertech.esper.event;

public interface EventTypeIdGeneratorFactory {
    public EventTypeIdGenerator create(EventTypeIdGeneratorContext context);
}
