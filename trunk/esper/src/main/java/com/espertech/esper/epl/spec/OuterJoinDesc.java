/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.type.OuterJoinType;
import com.espertech.esper.epl.expression.ExprIdentNode;
import com.espertech.esper.util.MetaDefItem;

/**
 * Contains the ON-clause criteria in an outer join.
 */
public class OuterJoinDesc implements MetaDefItem
{
    private OuterJoinType outerJoinType;
    private ExprIdentNode leftNode;
    private ExprIdentNode rightNode;
    private ExprIdentNode[] addLeftNode;
    private ExprIdentNode[] addRightNode;

    /**
     * Ctor.
     * @param outerJoinType - type of the outer join
     * @param leftNode - left hand identifier node
     * @param rightNode - right hand identifier node
     * @param addLeftNode - additional optional left hand identifier nodes for the on-clause in a logical-and
     * @param addRightNode - additional optional right hand identifier nodes for the on-clause in a logical-and
     */
    public OuterJoinDesc(OuterJoinType outerJoinType, ExprIdentNode leftNode, ExprIdentNode rightNode, ExprIdentNode[] addLeftNode, ExprIdentNode[] addRightNode)
    {
        this.outerJoinType = outerJoinType;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.addLeftNode = addLeftNode;
        this.addRightNode = addRightNode;
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

    /**
     * Returns additional properties in the on-clause, if any, that are connected via logical-and
     * @return additional properties
     */
    public ExprIdentNode[] getAdditionalLeftNodes()
    {
        return addLeftNode;
    }

    /**
     * Returns additional properties in the on-clause, if any, that are connected via logical-and
     * @return additional properties
     */
    public ExprIdentNode[] getAdditionalRightNodes()
    {
        return addRightNode;
    }
}
