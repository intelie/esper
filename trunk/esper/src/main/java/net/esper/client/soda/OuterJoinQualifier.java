package net.esper.client.soda;

import net.esper.type.OuterJoinType;

import java.io.Serializable;

public class OuterJoinQualifier implements Serializable
{
    private OuterJoinType type;
    private PropertyValueExpression left;
    private PropertyValueExpression right;

    public static OuterJoinQualifier create(String propertyLeft, OuterJoinType type, String propertyRight)
    {
        return new OuterJoinQualifier(type, new PropertyValueExpression(propertyLeft), new PropertyValueExpression(propertyRight));
    }

    public OuterJoinQualifier(OuterJoinType type, PropertyValueExpression left, PropertyValueExpression right)
    {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public OuterJoinType getType()
    {
        return type;
    }

    public void setType(OuterJoinType type)
    {
        this.type = type;
    }

    public PropertyValueExpression getLeft()
    {
        return left;
    }

    public void setLeft(PropertyValueExpression left)
    {
        this.left = left;
    }

    public PropertyValueExpression getRight()
    {
        return right;
    }

    public void setRight(PropertyValueExpression right)
    {
        this.right = right;
    }
}
