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

import java.util.Map;

public class EventBeanFactoryMap implements EventBeanFactory {
    private final EventType type;
    private final EventAdapterService eventAdapterService;

    public EventBeanFactoryMap(EventType type, EventAdapterService eventAdapterService) {
        this.type = type;
        this.eventAdapterService = eventAdapterService;
    }

    public EventBean wrap(Object underlying) {
        return eventAdapterService.adaptorForTypedMap((Map)underlying, type);
    }
}
