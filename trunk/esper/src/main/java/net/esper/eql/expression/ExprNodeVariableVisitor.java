package net.esper.eql.expression;

import java.util.List;
import java.util.LinkedList;

public class ExprNodeVariableVisitor implements ExprNodeVisitor
{
    private boolean hasVariables;

    public boolean isVisit(ExprNode exprNode)
    {
        return true;
    }

    public boolean isHasVariables()
    {
        return hasVariables;
    }

    public void visit(ExprNode exprNode)
    {
        if (!(exprNode instanceof ExprVariableNode))
        {
            return;
        }
        hasVariables = true;
    }
}
