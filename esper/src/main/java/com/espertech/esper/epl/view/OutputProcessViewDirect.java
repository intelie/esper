/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.view;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprTimePeriod;
import com.espertech.esper.util.AuditPath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * Output process view that does not enforce any output policies and may simply
 * hand over events to child views, does not handle distinct.
 */
public class OutputProcessViewDirect extends OutputProcessView
{
	private static final Log log = LogFactory.getLog(OutputProcessViewDirect.class);

    /**
     * Ctor.
     * @param resultSetProcessor is processing the result set for publishing it out
     * @param outputStrategy is the execution of output to sub-views or natively
     * @param isInsertInto is true if the statement is a insert-into
     * @param statementContext statement services
     * @param isDistinct true for distinct
     * @param afterTimePeriod after-keyword time period
     * @param afterConditionNumberOfEvents after-keyword number of events
     */
    public OutputProcessViewDirect(ResultSetProcessor resultSetProcessor, OutputStrategy outputStrategy, boolean isInsertInto, StatementContext statementContext, boolean isDistinct, ExprTimePeriod afterTimePeriod, Integer afterConditionNumberOfEvents)
    {
        super(resultSetProcessor, outputStrategy, isInsertInto, statementContext, isDistinct, afterTimePeriod, afterConditionNumberOfEvents);

        log.debug(".ctor");
        if (resultSetProcessor == null)
        {
            throw new IllegalArgumentException("Null result set processor, no output processor required");
        }
    }

    /**
     * The update method is called if the view does not participate in a join.
     * @param newData - new events
     * @param oldData - old events
     */
    public void update(EventBean[] newData, EventBean[] oldData)
    {
        boolean isGenerateSynthetic = statementResultService.isMakeSynthetic();
        boolean isGenerateNatural = statementResultService.isMakeNatural();

        UniformPair<EventBean[]> newOldEvents = resultSetProcessor.processViewResult(newData, oldData, isGenerateSynthetic);

        if ((!isGenerateSynthetic) && (!isGenerateNatural))
        {
            if (AuditPath.isAuditEnabled) {
                super.indicateEarlyReturn(newOldEvents);
            }
            return;
        }

        boolean forceOutput = false;
        if ((newData == null) && (oldData == null) &&
                ((newOldEvents == null) || (newOldEvents.getFirst() == null && newOldEvents.getSecond() == null)))
        {
            forceOutput = true;
        }

        // Child view can be null in replay from named window
        if (childView != null)
        {
            outputStrategy.output(forceOutput, newOldEvents, childView);
        }
    }

    /**
     * This process (update) method is for participation in a join.
     * @param newEvents - new events
     * @param oldEvents - old events
     */
    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents, ExprEvaluatorContext exprEvaluatorContext)
    {
        boolean isGenerateSynthetic = statementResultService.isMakeSynthetic();
        boolean isGenerateNatural = statementResultService.isMakeNatural();

        UniformPair<EventBean[]> newOldEvents = resultSetProcessor.processJoinResult(newEvents, oldEvents, isGenerateSynthetic);

        if ((!isGenerateSynthetic) && (!isGenerateNatural))
        {
            if (AuditPath.isAuditEnabled) {
                super.indicateEarlyReturn(newOldEvents);
            }
            return;
        }

        if (newOldEvents == null)
        {
            return;
        }

        // Child view can be null in replay from named window
        if (childView != null)
        {
            outputStrategy.output(false, newOldEvents, childView);
        }
    }
}
