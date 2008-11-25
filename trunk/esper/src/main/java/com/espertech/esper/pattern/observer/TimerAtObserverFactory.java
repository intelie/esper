/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern.observer;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.pattern.MatchedEventConvertor;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.PatternExpressionUtil;
import com.espertech.esper.schedule.ScheduleParameterException;
import com.espertech.esper.schedule.ScheduleSpec;
import com.espertech.esper.schedule.ScheduleSpecUtil;
import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.client.EPException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Factory for 'crontab' observers that indicate truth when a time point was reached.
 */
public class TimerAtObserverFactory implements ObserverFactory, MetaDefItem
{
    protected List<ExprNode> params;
    protected MatchedEventConvertor convertor;

    /**
     * The schedule specification for the timer-at.
     */
    protected ScheduleSpec spec = null;

    public void setObserverParameters(List<ExprNode> params, MatchedEventConvertor convertor) throws ObserverParameterException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".setObserverParameters " + params);
        }

        if ((params.size() < 5) || (params.size() > 6))
        {
            throw new ObserverParameterException("Invalid number of parameters for timer:at");
        }

        this.params = params;
        this.convertor = convertor; 
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator,
                                      Object stateNodeId, Object observerState)
    {
        List<Object> observerParameters = PatternExpressionUtil.evaluate("Timer-at observer", beginState, params, convertor);

        try
        {
            spec = ScheduleSpecUtil.computeValues(observerParameters.toArray());
        }
        catch (ScheduleParameterException e)
        {
            throw new EPException("Error computing crontab schedule specification: " + e.getMessage(), e);
        }
        return new TimerAtObserver(spec, context, beginState, observerEventEvaluator);
    }

    private static final Log log = LogFactory.getLog(TimerAtObserverFactory.class);
}
