package com.espertech.esper.eql.expression;

import java.util.LinkedList;
import java.util.List;

/**
 * Visitor that collects {@link ExprSubselectNode} instances. 
 */
public class ExprNodeSubselectVisitor implements ExprNodeVisitor
{
    private final List<ExprSubselectNode> subselects;

    /**
     * Ctor.
     */
    public ExprNodeSubselectVisitor()
    {
        subselects = new LinkedList<ExprSubselectNode>();
    }

    /**
     * Returns a list of lookup expression nodes.
     * @return lookup nodes
     */
    public List<ExprSubselectNode> getSubselects() {
        return subselects;
    }

    public boolean isVisit(ExprNode exprNode)
    {
        return true;
    }

    public void visit(ExprNode exprNode)
    {
        if (!(exprNode instanceof ExprSubselectNode))
        {
            return;
        }

        ExprSubselectNode subselectNode = (ExprSubselectNode) exprNode;
        subselects.add(subselectNode);
    }
}
