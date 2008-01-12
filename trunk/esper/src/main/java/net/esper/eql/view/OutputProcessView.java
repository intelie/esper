/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.view;

import net.esper.collection.MultiKey;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.join.JoinExecutionStrategy;
import net.esper.eql.join.JoinSetIndicator;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.View;
import net.esper.view.Viewable;
import net.esper.core.UpdateDispatchView;
import net.esper.core.EPStatementListenerSetCallback;
import net.esper.core.EPStatementListenerSet;

import java.util.*;

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
    protected final OutputStrategy outputStrategy;
    private final boolean isInsertInto;
    protected UpdateDispatchView childView;
    protected Viewable parentView;
    protected boolean isGenerateSynthetic;

    /**
     * Ctor.
     * @param resultSetProcessor processes the results posted by parent view or joins
     */
    protected OutputProcessView(ResultSetProcessor resultSetProcessor, OutputStrategy outputStrategy, boolean isInsertInto)
    {
        this.resultSetProcessor = resultSetProcessor;
        this.outputStrategy = outputStrategy;
        this.isInsertInto = isInsertInto;

        // by default, generate synthetic events only if we insert-into or if there are listeners to a statement
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
        childView.registerCallback(new EPStatementListenerSetCallback()
        {
            public void newListenerSet(EPStatementListenerSet epStatementListenerSet) {
                isGenerateSynthetic = (epStatementListenerSet.getListeners().size() != 0) ||
                        (epStatementListenerSet.getStmtAwareListeners().size() != 0) ||
                        isInsertInto;
            }
        });
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
    	if(resultSetProcessor != null)
    	{
            EventType eventType = resultSetProcessor.getResultEventType();
            if (eventType != null)
            {
                return eventType;
            }
            return parentView.getEventType();
    	}
    	else
    	{
    		return parentView.getEventType();
    	}
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
