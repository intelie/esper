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

package com.espertech.esper.support.pattern;

import com.espertech.esper.pattern.EvalStateNodeNumber;
import com.espertech.esper.pattern.observer.ObserverFactory;
import com.espertech.esper.pattern.observer.EventObserver;
import com.espertech.esper.pattern.observer.ObserverEventEvaluator;
import com.espertech.esper.pattern.observer.ObserverParameterException;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.MatchedEventConvertor;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.core.StatementContext;

import java.util.List;

public class SupportObserverFactory implements ObserverFactory
{
    public void setObserverParameters(List<ExprNode> observerParameters, MatchedEventConvertor convertor) throws ObserverParameterException
    {
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, EvalStateNodeNumber stateNodeId, Object observerState)
    {
        return null;
    }
}
