package com.espertech.esper.core;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventBeanWriter;
import com.espertech.esper.util.TypeWidener;

public class InternalEventRouterEntry 
{
    private final int priority;
    private final boolean isDrop;
    private final ExprNode optionalWhereClause;
    private final ExprNode[] assignments;
    private final EventBeanWriter writer;
    private final TypeWidener[] wideners;

    public InternalEventRouterEntry(int priority, boolean drop, ExprNode optionalWhereClause, ExprNode[] assignments, EventBeanWriter writer, TypeWidener[] wideners)
    {
        this.priority = priority;
        this.isDrop = drop;
        this.optionalWhereClause = optionalWhereClause;
        this.assignments = assignments;
        this.writer = writer;
        this.wideners = wideners;
    }

    public int getPriority()
    {
        return priority;
    }

    public boolean isDrop()
    {
        return isDrop;
    }

    public ExprNode getOptionalWhereClause()
    {
        return optionalWhereClause;
    }

    public ExprNode[] getAssignments()
    {
        return assignments;
    }

    public EventBeanWriter getWriter()
    {
        return writer;
    }

    public TypeWidener[] getWideners()
    {
        return wideners;
    }
}
