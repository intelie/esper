package net.esper.eql.expression;

import net.esper.type.OuterJoinType;

/**
 * Contains the ON-clause criteria in an outer join.
 */
public class OuterJoinDesc
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
