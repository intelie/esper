/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

/**
 * Visitor interface for use with expression node trees.
 */
public interface ExprNodeVisitor
{
    /**
     * Allows visitor to indicate whether to visit a given node.
     * Implicitly if a visitor doesn't visit a node it would also not visit any descendent child nodes of that node.
     * @param exprNode is the node in questions
     * @return true if the visitor wants to visit the child node (next call is visit), or false to skip child
     */
    public boolean isVisit(ExprNode exprNode);

    /**
     * Visit the given expression node.
     * @param exprNode is the expression node to visit
     */
    public void visit(ExprNode exprNode);
}
