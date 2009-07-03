package com.espertech.esper.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.annotation.Priority;
import com.espertech.esper.client.annotation.Drop;
import com.espertech.esper.util.NullableObject;
import com.espertech.esper.util.TypeWidener;
import com.espertech.esper.epl.spec.OnTriggerInsertIntoUpdDesc;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventPropertyWriter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import java.lang.annotation.Annotation;

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
