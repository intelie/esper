/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.core.EPStatementHandleCallback;
import com.espertech.esper.filter.FilterHandleCallback;
import com.espertech.esper.filter.FilterValueSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

/**
 * This class contains the state of a single filter expression in the evaluation state tree.
 */
public class EvalFilterStateNode extends EvalStateNode implements FilterHandleCallback
{
    private final EvalFilterNode evalFilterNode;
    private final MatchedEventMap beginState;

    private boolean isStarted;
    private EPStatementHandleCallback handle;

    /**
     * Constructor.
     * @param parentNode is the parent evaluator to call to indicate truth value
     * @param beginState contains the events that make up prior matches
     * @param evalFilterNode is the factory node associated to the state
     */
    public EvalFilterStateNode(Evaluator parentNode,
                                     EvalFilterNode evalFilterNode,
                                     MatchedEventMap beginState)
    {
        super(parentNode, null);

        this.evalFilterNode = evalFilterNode;
        this.beginState = beginState;
    }

    @Override
    public EvalNode getFactoryNode() {
        return evalFilterNode;
    }

    public String getStatementId()
    {
        return evalFilterNode.getContext().getStatementId();
    }

    public final void start()
    {
        if (isStarted)
        {
            throw new IllegalStateException("Filter state node already active");
        }

        // Start the filter
        isStarted = true;
        startFiltering();
    }

    public final void quit()
    {
        isStarted = false;
        stopFiltering();
    }

    private void evaluateTrue(MatchedEventMap event, boolean isQuitted)
    {
        this.getParentEvaluator().evaluateTrue(event, this, isQuitted);
    }

    public EvalFilterNode getEvalFilterNode() {
        return evalFilterNode;
    }

    public void matchFound(EventBean event, Collection<FilterHandleCallback> allStmtMatches)
    {
        if (!isStarted)
        {
            return;
        }

        MatchedEventMap passUp = beginState.shallowCopy();

        if (evalFilterNode.getFilterSpec().getOptionalPropertyEvaluator() != null)
        {
            EventBean[] propertyEvents = evalFilterNode.getFilterSpec().getOptionalPropertyEvaluator().getProperty(event, evalFilterNode.getContext());
            if (propertyEvents == null)
            {
                return; // no results, ignore match
            }
            // Add event itself to the match event structure if a tag was provided
            if (evalFilterNode.getEventAsName() != null)
            {
                passUp.add(evalFilterNode.getEventAsName(), propertyEvents);
            }
        }
        else
        {
            // Add event itself to the match event structure if a tag was provided
            if (evalFilterNode.getEventAsName() != null)
            {
                passUp.add(evalFilterNode.getEventAsName(), event);
            }
        }

        // Explanation for the type cast...
        // Each state node stops listening if it resolves to true, and all nodes newState
        // new listeners again. However this would be a performance drain since
        // and expression such as "on all b()" would remove the listener for b() for every match
        // and the all node would newState a new listener. The remove operation and the add operation
        // therefore don't take place if the EvalEveryStateNode node sits on top of a EvalFilterStateNode node.
        boolean isQuitted = false;
        if (!(this.getParentEvaluator().isFilterChildNonQuitting()))
        {
            stopFiltering();
            isQuitted = true;
        }

        this.evaluateTrue(passUp, isQuitted);
    }

    public final Object accept(EvalStateNodeVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public final Object childrenAccept(EvalStateNodeVisitor visitor, Object data)
    {
        return data;
    }

    public boolean isSubSelect()
    {
        return false;
    }

    public final String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("EvalFilterStateNode");
        buffer.append(" tag=");
        buffer.append(evalFilterNode.getFilterSpec());
        buffer.append(" spec=");
        buffer.append(evalFilterNode.getFilterSpec());
        return buffer.toString();
    }

    public boolean isFilterStateNode() {
        return true;
    }

    public boolean isNotOperator() {
        return false;
    }

    private void startFiltering()
    {
        PatternContext context = evalFilterNode.getContext();
        handle = new EPStatementHandleCallback(context.getEpStatementHandle(), this);
        FilterValueSet filterValues = evalFilterNode.getFilterSpec().getValueSet(beginState);
        context.getFilterService().add(filterValues, handle);
        long filtersVersion = context.getFilterService().getFiltersVersion();
        context.getStatementFilterVersion().setStmtFilterVersion(filtersVersion);
    }

    private void stopFiltering()
    {
        PatternContext context = evalFilterNode.getContext();
        if (handle != null) {
            context.getFilterService().remove(handle);
        }
        handle = null;
        isStarted = false;
        long filtersVersion = context.getFilterService().getFiltersVersion();
        context.getStatementFilterVersion().setStmtFilterVersion(filtersVersion);
    }

    private static final Log log = LogFactory.getLog(EvalFilterStateNode.class);
}
