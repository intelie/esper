/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.core.ViewResourceCallback;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Visitor that collects expression nodes that require view resources as provided by {@link com.espertech.esper.epl.core.ViewResourceCallback}.
 */
public class ExprNodeViewResourceVisitor implements ExprNodeVisitor
{
    private final List<ExprNode> exprNodes;

    /**
     * Ctor.
     */
    public ExprNodeViewResourceVisitor()
    {
        exprNodes = new ArrayList<ExprNode>();
    }

    public boolean isVisit(ExprNode exprNode)
    {
        return true;
    }

    public List<ExprNode> getExprNodes()
    {
        return exprNodes;
    }

    public void visit(ExprNode exprNode)
    {
        if (exprNode instanceof ViewResourceCallback)
        {
            exprNodes.add(exprNode);
        }
    }
}
