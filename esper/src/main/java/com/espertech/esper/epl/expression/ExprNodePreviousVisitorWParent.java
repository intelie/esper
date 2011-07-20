/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.expression;

import com.espertech.esper.collection.Pair;

import java.util.List;
import java.util.ArrayList;

/**
 * Visitor for getting a list of "prev" functions.
 */
public class ExprNodePreviousVisitorWParent implements ExprNodeVisitorWithParent
{
    private List<Pair<ExprNode, ExprPreviousNode>> previous;

    public boolean isVisit(ExprNode exprNode)
    {
        return true;
    }

    public void visit(ExprNode exprNode, ExprNode parentExprNode)
    {
        if (exprNode instanceof ExprPreviousNode)
        {
            if (previous == null)
            {
                previous = new ArrayList<Pair<ExprNode, ExprPreviousNode>>();
            }
            previous.add(new Pair<ExprNode, ExprPreviousNode>(parentExprNode, (ExprPreviousNode) exprNode));
        }
    }

    /**
     * Returns the pair of previous nodes and their parent expression.
     * @return nodes
     */
    public List<Pair<ExprNode, ExprPreviousNode>> getPrevious() {
        return previous;
    }
}
