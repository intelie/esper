/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.view;

import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.spec.OutputLimitSpec;
import com.espertech.esper.epl.spec.OutputLimitLimitType;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.event.EventBeanUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

/**
 * Handles output rate limiting for FIRST, only applicable with a having-clause and no group-by clause.
 * <p>
 * Without having-clause the order of processing won't matter therefore its handled by the
 * {@link OutputProcessViewPolicy}. With group-by the {@link ResultSetProcessor} handles the per-group first criteria.
 */
public class OutputProcessViewPolicyFirst extends OutputProcessView
{
    private final OutputCondition outputCondition;

    // Posted events in ordered form (for applying to aggregates) and summarized per type
    // Using ArrayList as random access is a requirement.
    private List<UniformPair<EventBean[]>> viewEventsList = new ArrayList<UniformPair<EventBean[]>>();
	private List<UniformPair<Set<MultiKey<EventBean>>>> joinEventsSet = new ArrayList<UniformPair<Set<MultiKey<EventBean>>>>();
    private boolean witnessedFirst;

	private static final Log log = LogFactory.getLog(OutputProcessViewPolicyFirst.class);

    /**
     * Ctor.
     * @param resultSetProcessor is processing the result set for publishing it out
     * @param streamCount is the number of streams, indicates whether or not this view participates in a join
     * @param outputLimitSpec is the specification for limiting output (the output condition and the result set processor)
     * @param statementContext is the services the output condition may depend on
     * @param isInsertInto is true if the statement is a insert-into
     * @param outputStrategy is the method to use to produce output
     * @param isDistinct true for distinct
     * @throws com.espertech.esper.epl.expression.ExprValidationException if validation of the output expressions fails
     */
    public OutputProcessViewPolicyFirst(ResultSetProcessor resultSetProcessor,
                                        OutputStrategy outputStrategy,
                                        boolean isInsertInto,
                                        int streamCount,
                                        OutputLimitSpec outputLimitSpec,
                                        StatementContext statementContext,
                                        boolean isDistinct,
                                        boolean isGrouped,
                                        boolean isWithHavingClause)
            throws ExprValidationException
    {
        super(resultSetProcessor, outputStrategy, isInsertInto, statementContext, isDistinct, outputLimitSpec.getAfterTimePeriodExpr(), outputLimitSpec.getAfterNumberOfEvents());
        log.debug(".ctor");

    	if(streamCount < 1)
    	{
    		throw new IllegalArgumentException("Output process view is part of at least 1 stream");
    	}

    	OutputCallback outputCallback = getCallbackToLocal(streamCount);
    	this.outputCondition = statementContext.getOutputConditionFactory().createCondition(outputLimitSpec, statementContext, outputCallback, isGrouped, isWithHavingClause);
    }

    /**
     * The update method is called if the view does not participate in a join.
     * @param newData - new events
     * @param oldData - old events
     */
    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        if (!super.checkAfterCondition(newData))
        {
            return;
        }

        if (!witnessedFirst) {
            boolean isGenerateSynthetic = statementResultService.isMakeSynthetic();

            // Process the events and get the result
            viewEventsList.add(new UniformPair<EventBean[]>(newData, oldData));
            UniformPair<EventBean[]> newOldEvents = resultSetProcessor.processOutputLimitedView(viewEventsList, isGenerateSynthetic, OutputLimitLimitType.FIRST);
            viewEventsList.clear();

            if (newOldEvents == null || (newOldEvents.getFirst() == null && newOldEvents.getSecond() == null)) {
                return; // nothing to indicate
            }

            witnessedFirst = true;

            if (isDistinct)
            {
                newOldEvents.setFirst(EventBeanUtility.getDistinctByProp(newOldEvents.getFirst(), eventBeanReader));
                newOldEvents.setSecond(EventBeanUtility.getDistinctByProp(newOldEvents.getSecond(), eventBeanReader));
            }

            boolean isGenerateNatural = statementResultService.isMakeNatural();
            if ((!isGenerateSynthetic) && (!isGenerateNatural))
            {
                if (AuditPath.isAuditEnabled) {
                    super.indicateEarlyReturn(newOldEvents);
                }
                return;
            }

            output(true, newOldEvents);
        }
        else {
            viewEventsList.add(new UniformPair<EventBean[]>(newData, oldData));
            resultSetProcessor.processOutputLimitedView(viewEventsList, false, OutputLimitLimitType.FIRST);
            viewEventsList.clear();
        }

        int newDataLength = 0;
        int oldDataLength = 0;
        if(newData != null)
        {
        	newDataLength = newData.length;
        }
        if(oldData != null)
        {
        	oldDataLength = oldData.length;
        }

        outputCondition.updateOutputCondition(newDataLength, oldDataLength);
    }

    /**
     * This process (update) method is for participation in a join.
     * @param newEvents - new events
     * @param oldEvents - old events
     */
    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents, ExprEvaluatorContext exprEvaluatorContext)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".process Received update, " +
                    "  newData.length==" + ((newEvents == null) ? 0 : newEvents.size()) +
                    "  oldData.length==" + ((oldEvents == null) ? 0 : oldEvents.size()));
        }

        if (!super.checkAfterCondition(newEvents))
        {
            return;
        }

        // add the incoming events to the event batches
        if (!witnessedFirst) {
            Set<MultiKey<EventBean>> copyNew;
            if (newEvents != null)
            {
                copyNew = new LinkedHashSet<MultiKey<EventBean>>(newEvents);
            }
            else
            {
                copyNew = new LinkedHashSet<MultiKey<EventBean>>();
            }

            Set<MultiKey<EventBean>> copyOld;
            if (oldEvents != null)
            {
                copyOld = new LinkedHashSet<MultiKey<EventBean>>(oldEvents);
            }
            else
            {
                copyOld = new LinkedHashSet<MultiKey<EventBean>>();
            }

            joinEventsSet.add(new UniformPair<Set<MultiKey<EventBean>>>(copyNew, copyOld));
            boolean isGenerateSynthetic = statementResultService.isMakeSynthetic();

            // Process the events and get the result
            UniformPair<EventBean[]> newOldEvents = resultSetProcessor.processOutputLimitedJoin(joinEventsSet, isGenerateSynthetic, OutputLimitLimitType.FIRST);
            joinEventsSet.clear();

            if (newOldEvents == null || (newOldEvents.getFirst() == null && newOldEvents.getSecond() == null)) {
                return; // nothing to indicate
            }

            witnessedFirst = true;

            if (isDistinct)
            {
                newOldEvents.setFirst(EventBeanUtility.getDistinctByProp(newOldEvents.getFirst(), eventBeanReader));
                newOldEvents.setSecond(EventBeanUtility.getDistinctByProp(newOldEvents.getSecond(), eventBeanReader));
            }

            boolean isGenerateNatural = statementResultService.isMakeNatural();
            if ((!isGenerateSynthetic) && (!isGenerateNatural))
            {
                if (AuditPath.isAuditEnabled) {
                    super.indicateEarlyReturn(newOldEvents);
                }
                return;
            }

            output(true, newOldEvents);
        }
        else {
            Set<MultiKey<EventBean>> copyNew;
            if (newEvents != null)
            {
                copyNew = new LinkedHashSet<MultiKey<EventBean>>(newEvents);
            }
            else
            {
                copyNew = new LinkedHashSet<MultiKey<EventBean>>();
            }

            Set<MultiKey<EventBean>> copyOld;
            if (oldEvents != null)
            {
                copyOld = new LinkedHashSet<MultiKey<EventBean>>(oldEvents);
            }
            else
            {
                copyOld = new LinkedHashSet<MultiKey<EventBean>>();
            }
            joinEventsSet.add(new UniformPair<Set<MultiKey<EventBean>>>(copyNew, copyOld));

            // Process the events and get the result
            resultSetProcessor.processOutputLimitedJoin(joinEventsSet, false, OutputLimitLimitType.FIRST);
            joinEventsSet.clear();
        }

        int newEventsSize = 0;
        if (newEvents != null)
        {
            newEventsSize = newEvents.size();
        }

        int oldEventsSize = 0;
        if (oldEvents != null)
        {
            oldEventsSize = oldEvents.size();
        }

        outputCondition.updateOutputCondition(newEventsSize, oldEventsSize);
    }

	/**
	 * Called once the output condition has been met.
	 * Invokes the result set processor.
	 * Used for non-join event data.
	 * @param doOutput - true if the batched events should actually be output as well as processed, false if they should just be processed
	 * @param forceUpdate - true if output should be made even when no updating events have arrived
	 * */
	protected void continueOutputProcessingView(boolean doOutput, boolean forceUpdate)
	{
		if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".continueOutputProcessingView");
        }
        witnessedFirst = false;
	}

	private void output(boolean forceUpdate, UniformPair<EventBean[]> results)
	{
        // Child view can be null in replay from named window
        if (childView != null)
        {
            outputStrategy.output(forceUpdate, results, childView);
        }
	}

	/**
	 * Called once the output condition has been met.
	 * Invokes the result set processor.
	 * Used for join event data.
	 * @param doOutput - true if the batched events should actually be output as well as processed, false if they should just be processed
	 * @param forceUpdate - true if output should be made even when no updating events have arrived
	 */
	protected void continueOutputProcessingJoin(boolean doOutput, boolean forceUpdate)
	{
		if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".continueOutputProcessingJoin");
        }
        witnessedFirst = false;
	}

    private OutputCallback getCallbackToLocal(int streamCount)
    {
        // single stream means no join
        // multiple streams means a join
        if(streamCount == 1)
        {
            return new OutputCallback()
            {
                public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
                {
                    OutputProcessViewPolicyFirst.this.continueOutputProcessingView(doOutput, forceUpdate);
                }
            };
        }
        else
        {
            return new OutputCallback()
            {
                public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
                {
                    OutputProcessViewPolicyFirst.this.continueOutputProcessingJoin(doOutput, forceUpdate);
                }
            };
        }
    }
}
