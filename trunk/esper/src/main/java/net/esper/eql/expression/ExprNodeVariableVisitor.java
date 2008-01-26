package net.esper.eql.expression;

import java.util.List;
import java.util.LinkedList;

/**
 * Visitor for expression node trees that determines if the expressions within contain a variable.
 */
public class ExprNodeVariableVisitor implements ExprNodeVisitor
{
    private boolean hasVariables;

    public boolean isVisit(ExprNode exprNode)
    {
        return true;
    }

    /**
     * Returns true if the visitor finds a variable value.
     * @return true for variable present in expression
     */
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
