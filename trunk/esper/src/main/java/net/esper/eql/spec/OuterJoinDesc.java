/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.spec;

import net.esper.type.OuterJoinType;
import net.esper.eql.expression.ExprIdentNode;
import net.esper.util.MetaDefItem;

/**
 * Contains the ON-clause criteria in an outer join.
 */
public class OuterJoinDesc implements MetaDefItem
{
    private OuterJoinType outerJoinType;
    private ExprIdentNode leftNode;
    private ExprIdentNode rightNode;

    /**
     * Ctor.
     * @param outerJoinType - type of the outer join
     * @param leftNode - left hand identifier node
     * @param rightNode - right hand identifier node
     */
    public OuterJoinDesc(OuterJoinType outerJoinType, ExprIdentNode leftNode, ExprIdentNode rightNode)
    {
        this.outerJoinType = outerJoinType;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /**
     * Returns the type of outer join (left/right/full).
     * @return outer join type
     */
    public OuterJoinType getOuterJoinType()
    {
        return outerJoinType;
    }

    /**
     * Returns left hand identifier node.
     * @return left hand
     */
    public ExprIdentNode getLeftNode()
    {
        return leftNode;
    }

    /**
     * Returns right hand identifier node.
     * @return right hand
     */
    public ExprIdentNode getRightNode()
    {
        return rightNode;
    }
}
