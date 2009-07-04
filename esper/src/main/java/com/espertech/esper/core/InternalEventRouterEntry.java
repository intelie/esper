package com.espertech.esper.core;

import com.espertech.esper.util.TypeWidener;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventPropertyWriter;

public class InternalEventRouterEntry 
{
    private final int priority;
    private final boolean isDrop;
    private final ExprNode optionalWhereClause;
    private final ExprNode[] assignments;
    private final EventPropertyWriter[] writers;
    private final TypeWidener[] wideners;

    public InternalEventRouterEntry(int priority, boolean drop, ExprNode optionalWhereClause, ExprNode[] assignments, EventPropertyWriter[] writers, TypeWidener[] wideners)
    {
        this.priority = priority;
        this.isDrop = drop;
        this.optionalWhereClause = optionalWhereClause;
        this.assignments = assignments;
        this.writers = writers;
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

    public EventPropertyWriter[] getWriters()
    {
        return writers;
    }

    public TypeWidener[] getWideners()
    {
        return wideners;
    }
}
