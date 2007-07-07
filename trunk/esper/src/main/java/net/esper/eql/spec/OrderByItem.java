package net.esper.eql.spec;

import net.esper.util.MetaDefItem;
import net.esper.eql.expression.ExprNode;

public class OrderByItem implements MetaDefItem
{
    private ExprNode exprNode;
    private boolean isDescending;

    public OrderByItem(ExprNode exprNode, boolean ascending)
    {
        this.exprNode = exprNode;
        isDescending = ascending;
    }

    public ExprNode getExprNode()
    {
        return exprNode;
    }

    public boolean isDescending()
    {
        return isDescending;
    }
}
