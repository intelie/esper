package net.esper.eql.expression;

import java.util.LinkedList;
import java.util.List;

/**
 * Visitor that collects {@link ExprSubselectNode} instances. 
 */
public class ExprNodeSubselectVisitor implements ExprNodeVisitor
{
    private final List<ExprSubselectNode> subselects;

    public ExprNodeSubselectVisitor()
    {
        subselects = new LinkedList<ExprSubselectNode>();
    }

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
