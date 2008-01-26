/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.view;

import net.esper.collection.MultiKey;
import net.esper.core.UpdateDispatchView;
import net.esper.core.StatementResultService;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.join.JoinExecutionStrategy;
import net.esper.eql.join.JoinSetIndicator;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.View;
import net.esper.view.Viewable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Base output processing view that has the responsibility to serve up event type and
 * statement iterator.
 * <p>
 * Implementation classes may enforce an output rate stabilizing or limiting policy.
 */
public abstract class OutputProcessView implements View, JoinSetIndicator
{
    /**
     * Processes the parent views result set generating events for pushing out to child view.
     */
    protected final ResultSetProcessor resultSetProcessor;
    private JoinExecutionStrategy joinExecutionStrategy;

    /**
     * Strategy to performs the output once it's decided we need to output.
     */
    protected final OutputStrategy outputStrategy;

    /**
     * Manages listeners/subscribers to a statement, informing about
     * current result generation needs.
     */
    protected final StatementResultService statementResultService;

    /**
     * The view to ultimately dispatch to.
     */
    protected UpdateDispatchView childView;

    /**
     * The parent view for iteration.
     */
    protected Viewable parentView;

    /**
     * An indicator on whether we always need synthetic events such as for insert-into.
     */
    protected boolean isGenerateSynthetic;

    /**
     * Ctor.
     * @param resultSetProcessor processes the results posted by parent view or joins
     * @param outputStrategy the strategy to use for producing output
     * @param isInsertInto true if this is an insert-into
     * @param statementResultService for awareness of listeners and subscriber
     */
    protected OutputProcessView(ResultSetProcessor resultSetProcessor, OutputStrategy outputStrategy, boolean isInsertInto, StatementResultService statementResultService)
    {
        this.resultSetProcessor = resultSetProcessor;
        this.outputStrategy = outputStrategy;
        this.statementResultService = statementResultService;

        // by default, generate synthetic events only if we insert-into
        this.isGenerateSynthetic = isInsertInto;
    }

    public Viewable getParent() {
        return parentView;
    }

    public void setParent(Viewable parent) {
        this.parentView = parent;
    }

    public View addView(View view) {
        if (childView != null)
        {
            throw new IllegalStateException("Child view has already been supplied");
        }
        childView = (UpdateDispatchView) view;
        return this;
    }

    public List<View> getViews() {
        ArrayList<View> views = new ArrayList<View>();
        if (childView != null)
        {
            views.add(childView);
        }
        return views;
    }

    public boolean removeView(View view) {
        if (view != childView)
        {
            throw new IllegalStateException("Cannot remove child view, view has not been supplied");
        }
        childView = null;
        return true;
    }

    public boolean hasViews() {
        return childView != null;
    }

    public EventType getEventType()
    {
        EventType eventType = resultSetProcessor.getResultEventType();
        if (eventType != null)
        {
            return eventType;
        }
        return parentView.getEventType();
    }

    /**
     * For joins, supplies the join execution strategy that provides iteration over statement results.
     * @param joinExecutionStrategy executes joins including static (non-continuous) joins 
     */
    public void setJoinExecutionStrategy(JoinExecutionStrategy joinExecutionStrategy)
    {
        this.joinExecutionStrategy = joinExecutionStrategy;
    }

    public Iterator<EventBean> iterator()
    {
        if (joinExecutionStrategy != null)
        {
            Set<MultiKey<EventBean>> joinSet = joinExecutionStrategy.staticJoin();
            return resultSetProcessor.getIterator(joinSet);
        }
        if(resultSetProcessor != null)
    	{
            return resultSetProcessor.getIterator(parentView);
    	}
    	else
    	{
    		return parentView.iterator();
    	}
    }
}
