/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventBeanFactory;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventAdapterService;

import java.util.Collections;

public class EventBeanFactoryBeanWrapped implements EventBeanFactory {

    private final EventType beanEventType;
    private final EventType wrapperEventType;
    private final EventAdapterService eventAdapterService;

    public EventBeanFactoryBeanWrapped(EventType beanEventType, EventType wrapperEventType, EventAdapterService eventAdapterService) {
        this.beanEventType = beanEventType;
        this.wrapperEventType = wrapperEventType;
        this.eventAdapterService = eventAdapterService;
    }

    public EventBean wrap(Object underlying) {
        EventBean bean = eventAdapterService.adapterForTypedBean(underlying, beanEventType);
        return eventAdapterService.adaptorForTypedWrapper(bean, Collections.<String, Object>emptyMap(), wrapperEventType);
    }
}
