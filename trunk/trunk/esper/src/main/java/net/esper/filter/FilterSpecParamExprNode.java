package net.esper.filter;

import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventType;
import net.esper.util.JavaClassHelper;
import net.esper.pattern.MatchedEventMap;
import net.esper.eql.expression.ExprNode;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 * This class represents an arbitrary expression node returning a boolean value as a filter parameter in an {@link FilterSpec} filter specification.
 */
public final class FilterSpecParamExprNode extends FilterSpecParam
{
    private ExprNode exprNode;

    /**
     * Ctor.
     * @param propertyName is the event property name
     * @param filterOperator is expected to be the IN-list operator
     * @throws IllegalArgumentException for illegal args
     */
    public FilterSpecParamExprNode(String propertyName,
                             FilterOperator filterOperator,
                             ExprNode exprNode)
        throws IllegalArgumentException
    {
        super(propertyName, filterOperator);
        if (filterOperator != FilterOperator.BOOLEAN_EXPRESSION)
        {
            throw new IllegalArgumentException("Invalid filter operator for filter expression node");
        }
        this.exprNode = exprNode;
    }

    public final Class getFilterValueClass(Map<String, EventType> taggedEventTypes)
    {
        return Boolean.class;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        return null;
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
}
