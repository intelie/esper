/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.declexpr.ExprDeclaredNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor that collects {@link ExprSubselectNode} instances.
 */
public class ExprNodeSubselectVisitor implements ExprNodeVisitorWithParent
{
    private final List<ExprSubselectNode> subselects;
    private final List<ExprDotNode> chainedExpressionsDot;
    private final List<ExprDeclaredNode> declaredExpressions;

    /**
     * Ctor.
     */
    public ExprNodeSubselectVisitor()
    {
        subselects = new ArrayList<ExprSubselectNode>();
        chainedExpressionsDot = new ArrayList<ExprDotNode>();
        declaredExpressions = new ArrayList<ExprDeclaredNode>();
    }

    public void reset() {
        subselects.clear();
        chainedExpressionsDot.clear();
        declaredExpressions.clear();
    }

    /**
     * Returns a list of lookup expression nodes.
     * @return lookup nodes
     */
    public List<ExprSubselectNode> getSubselects() {
        return subselects;
    }

    public List<ExprDotNode> getChainedExpressionsDot() {
        return chainedExpressionsDot;
    }

    public List<ExprDeclaredNode> getDeclaredExpressions() {
        return declaredExpressions;
    }

    public boolean isVisit(ExprNode exprNode)
    {
        return true;
    }

    public void visit(ExprNode exprNode, ExprNode parentExprNode) {

        if (exprNode instanceof ExprDotNode)
        {
            chainedExpressionsDot.add((ExprDotNode) exprNode);
        }

        if (exprNode instanceof ExprDeclaredNode)
        {
            declaredExpressions.add((ExprDeclaredNode) exprNode);
        }

        if (!(exprNode instanceof ExprSubselectNode))
        {
            return;
        }

        ExprSubselectNode subselectNode = (ExprSubselectNode) exprNode;
        subselects.add(subselectNode);
    }
}
