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

package com.espertech.esper.epl.core.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.SelectExprProcessor;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.MappedEventBean;

public class EvalInsertMapTypeCoercion implements SelectExprProcessor {

    private EventType resultEventType;
    private EventAdapterService eventAdapterService;

    public EvalInsertMapTypeCoercion(EventType resultEventType, EventAdapterService eventAdapterService) {
        this.resultEventType = resultEventType;
        this.eventAdapterService = eventAdapterService;
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize) {
        MappedEventBean event = (MappedEventBean) eventsPerStream[0];
        return eventAdapterService.adaptorForTypedMap(event.getProperties(), resultEventType);
    }

    public EventType getResultEventType() {
        return resultEventType;
    }
}
