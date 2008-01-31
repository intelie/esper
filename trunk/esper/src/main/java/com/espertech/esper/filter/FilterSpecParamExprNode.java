/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.filter;

import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.eql.expression.ExprNodeVariableVisitor;
import com.espertech.esper.eql.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.pattern.MatchedEventMap;

import java.util.LinkedHashMap;

/**
 * This class represents an arbitrary expression node returning a boolean value as a filter parameter in an {@link FilterSpecCompiled} filter specification.
 */
public final class FilterSpecParamExprNode extends FilterSpecParam
{
    private final ExprNode exprNode;
    private final LinkedHashMap<String, EventType> taggedEventTypes;
    private final VariableService variableService;
    private final boolean hasVariable;

    /**
     * Ctor.
     * @param propertyName is the event property name
     * @param filterOperator is expected to be the BOOLEAN_EXPR operator
     * @param exprNode represents the boolean expression
     * @param taggedEventTypes is null if the expression doesn't need other streams, or is filled with a ordered list of stream names and types
     * @param variableService - provides access to variables
     * @throws IllegalArgumentException for illegal args
     */
    public FilterSpecParamExprNode(String propertyName,
                             FilterOperator filterOperator,
                             ExprNode exprNode,
                             LinkedHashMap<String, EventType> taggedEventTypes,
                             VariableService variableService)
        throws IllegalArgumentException
    {
        super(propertyName, filterOperator);
        if (filterOperator != FilterOperator.BOOLEAN_EXPRESSION)
        {
            throw new IllegalArgumentException("Invalid filter operator for filter expression node");
        }
        this.exprNode = exprNode;
        this.taggedEventTypes = taggedEventTypes;
        this.variableService = variableService;

        ExprNodeVariableVisitor visitor = new ExprNodeVariableVisitor();
        exprNode.accept(visitor);
        this.hasVariable = visitor.isHasVariables();
    }

    /**
     * Returns the expression node of the boolean expression this filter parameter represents.
     * @return expression node
     */
    public ExprNode getExprNode()
    {
        return exprNode;
    }

    /**
     * Returns the map of tag/stream names to event types that the filter expressions map use (for patterns)
     * @return map
     */
    public LinkedHashMap<String, EventType> getTaggedEventTypes()
    {
        return taggedEventTypes;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        EventBean[] events = null;

        if (taggedEventTypes != null)
        {
            events = new EventBean[taggedEventTypes.size() + 1];
            int count = 1;
            for (String tag : taggedEventTypes.keySet())
            {
                events[count] = matchedEvents.getMatchingEvent(tag);
                count++;
            }
        }

        if (hasVariable)
        {
            return new ExprNodeAdapter(exprNode, events, variableService);
        }
        else
        {
            return new ExprNodeAdapter(exprNode, events, null);
        }
    }

    public final String toString()
    {
        return super.toString() + "  exprNode=" + exprNode.toString();
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecParamExprNode))
        {
            return false;
        }

        FilterSpecParamExprNode other = (FilterSpecParamExprNode) obj;
        if (!super.equals(other))
        {
            return false;
        }

        if (exprNode != other.exprNode)
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + exprNode.hashCode();
        return result;
    }
}
