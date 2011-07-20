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

package com.espertech.esper.support.epl;

import com.espertech.esper.epl.core.SelectExprProcessor;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;

public class SupportSelectExprProcessor implements SelectExprProcessor
{
    public EventType getResultEventType()
    {
        return SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize)
    {
        return eventsPerStream[0];
    }
}
