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

import com.espertech.esper.epl.join.base.JoinSetProcessor;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKey;

import java.util.Set;

public class SupportJoinSetProcessor implements JoinSetProcessor
{
    private Set<MultiKey<EventBean>> lastNewEvents;
    private Set<MultiKey<EventBean>> lastOldEvents;

    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents, ExprEvaluatorContext exprEvaluatorContext)
    {
        lastNewEvents = newEvents;
        lastOldEvents = oldEvents;
    }

    public Set<MultiKey<EventBean>> getLastNewEvents()
    {
        return lastNewEvents;
    }

    public Set<MultiKey<EventBean>> getLastOldEvents()
    {
        return lastOldEvents;
    }

    public void reset()
    {
        lastNewEvents = null;
        lastOldEvents = null;
    }
}
