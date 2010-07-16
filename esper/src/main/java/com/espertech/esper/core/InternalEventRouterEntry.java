package com.espertech.esper.core;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventBeanWriter;
import com.espertech.esper.util.TypeWidener;

/**
 * Pre-Processing entry for routing an event internally.
 */
public class InternalEventRouterEntry 
{
    private final int priority;
    private final boolean isDrop;
    private final ExprNode optionalWhereClause;
    private final ExprNode[] assignments;
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
        this.optionalWhereClause = optionalWhereClause;
        this.assignments = assignments;
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
    public ExprNode getOptionalWhereClause()
    {
        return optionalWhereClause;
    }

    /**
     * Returns the expressions providing values for assignment.
     * @return assignment expressions
     */
    public ExprNode[] getAssignments()
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
