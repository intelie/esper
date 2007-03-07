package net.esper.filter;

import net.esper.eql.expression.ExprNode;
import net.esper.pattern.MatchedEventMap;

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

    public ExprNode getExprNode()
    {
        return exprNode;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        // this is what the index gets
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
