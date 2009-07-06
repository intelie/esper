package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

import java.util.Map;
import java.util.HashMap;

public class WrapperEventBeanMapCopyMethod implements EventBeanCopyMethod
{
    private final WrapperEventType wrapperEventType;
    private final EventAdapterService eventAdapterService;

    public WrapperEventBeanMapCopyMethod(WrapperEventType wrapperEventType, EventAdapterService eventAdapterService)
    {
        this.wrapperEventType = wrapperEventType;
        this.eventAdapterService = eventAdapterService;
    }

    public EventBean copy(EventBean event)
    {
        DecoratingEventBean decorated = (DecoratingEventBean) event;
        EventBean decoratedUnderlying = decorated.getUnderlyingEvent();
        Map<String, Object> copiedMap = new HashMap<String, Object>(decorated.getDecoratingProperties());
        return eventAdapterService.adaptorForTypedWrapper(decoratedUnderlying, copiedMap, wrapperEventType);        
    }
}
