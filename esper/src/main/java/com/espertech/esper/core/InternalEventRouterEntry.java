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

package com.espertech.esper.core;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNodeUtility;
import com.espertech.esper.event.EventBeanWriter;
import com.espertech.esper.util.TypeWidener;

/**
 * Pre-Processing entry for routing an event internally.
 */
public class InternalEventRouterEntry
{
    private final int priority;
    private final boolean isDrop;
    private final ExprEvaluator optionalWhereClause;
    private final ExprEvaluator[] assignments;
    private final EventBeanWriter writer;
    private final TypeWidener[] wideners;
    private final InternalRoutePreprocessView outputView;

    /**
     * Ctor.
     * @param priority priority of statement
     * @param drop whether to drop the event if matched
     * @param optionalWhereClause where clause, or null if none provided
     * @param assignments event property assignments
     * @param writer writes values to an event
     * @param wideners for widening types to write
     * @param outputView for indicating output
     */
    public InternalEventRouterEntry(int priority, boolean drop, ExprNode optionalWhereClause, ExprNode[] assignments, EventBeanWriter writer, TypeWidener[] wideners, InternalRoutePreprocessView outputView)
    {
        this.priority = priority;
        this.isDrop = drop;
        this.optionalWhereClause = optionalWhereClause == null ? null : optionalWhereClause.getExprEvaluator();
        this.assignments = ExprNodeUtility.getEvaluators(assignments);
        this.writer = writer;
        this.wideners = wideners;
        this.outputView = outputView;
    }

    /**
     * Returns the execution priority.
     * @return priority
     */
    public int getPriority()
    {
        return priority;
    }

    /**
     * Returns indicator whether dropping events if the where-clause matches.
     * @return drop events
     */
    public boolean isDrop()
    {
        return isDrop;
    }

    /**
     * Returns the where-clause or null if none defined
     * @return where-clause
     */
    public ExprEvaluator getOptionalWhereClause()
    {
        return optionalWhereClause;
    }

    /**
     * Returns the expressions providing values for assignment.
     * @return assignment expressions
     */
    public ExprEvaluator[] getAssignments()
    {
        return assignments;
    }

    /**
     * Returns the writer to the event for writing property values.
     * @return writer
     */
    public EventBeanWriter getWriter()
    {
        return writer;
    }

    /**
     * Returns the type wideners to use or null if none required.
     * @return wideners.
     */
    public TypeWidener[] getWideners()
    {
        return wideners;
    }

    /**
     * Returns the output view.
     * @return output view
     */
    public InternalRoutePreprocessView getOutputView() {
        return outputView;
    }
}
