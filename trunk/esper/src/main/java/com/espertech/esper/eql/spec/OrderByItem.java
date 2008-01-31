package com.espertech.esper.eql.spec;

import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.eql.expression.ExprNode;

/**
 * Specification object to an element in the order-by expression.
 */
public class OrderByItem implements MetaDefItem
{
    private ExprNode exprNode;
    private boolean isDescending;

    /**
     * Ctor.
     * @param exprNode is the order-by expression node
     * @param ascending is true for ascending, or false for descending sort
     */
    public OrderByItem(ExprNode exprNode, boolean ascending)
    {
        this.exprNode = exprNode;
        isDescending = ascending;
    }

    /**
     * Returns the order-by expression node.
     * @return expression node.
     */
    public ExprNode getExprNode()
    {
        return exprNode;
    }

    /**
     * Returns true for ascending, false for descending.
     * @return indicator if ascending or descending
     */
    public boolean isDescending()
    {
        return isDescending;
    }
}
