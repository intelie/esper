/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import net.esper.type.OuterJoinType;

import java.io.Serializable;

/**
 * Qualifies a join by providing the outer join type (full/left/right) and joined-on properties.
 */
public class OuterJoinQualifier implements Serializable
{
    private static final long serialVersionUID = 0L;

    private OuterJoinType type;
    private PropertyValueExpression left;
    private PropertyValueExpression right;

    /**
     * Creates qualifier.
     * @param propertyLeft is a property name providing joined-on values
     * @param type is the type of outer join
     * @param propertyRight is a property name providing joined-on values
     * @return qualifier
     */
    public static OuterJoinQualifier create(String propertyLeft, OuterJoinType type, String propertyRight)
    {
        return new OuterJoinQualifier(type, new PropertyValueExpression(propertyLeft), new PropertyValueExpression(propertyRight));
    }

    /**
     * Ctor.
     * @param left is a property providing joined-on values
     * @param type is the type of outer join
     * @param right is a property providing joined-on values
     */
    public OuterJoinQualifier(OuterJoinType type, PropertyValueExpression left, PropertyValueExpression right)
    {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the type of outer join.
     * @return outer join type
     */
    public OuterJoinType getType()
    {
        return type;
    }

    /**
     * Sets the type of outer join.
     * @param type is the outer join type
     */
    public void setType(OuterJoinType type)
    {
        this.type = type;
    }

    /**
     * Returns property value expression to join on.
     * @return expression providing joined-on values
     */
    public PropertyValueExpression getLeft()
    {
        return left;
    }

    /**
     * Sets the property value expression to join on.
     * @param left expression providing joined-on values
     */
    public void setLeft(PropertyValueExpression left)
    {
        this.left = left;
    }

    /**
     * Returns property value expression to join on.
     * @return expression providing joined-on values
     */
    public PropertyValueExpression getRight()
    {
        return right;
    }

    /**
     * Sets the property value expression to join on.
     * @param right expression providing joined-on values
     */
    public void setRight(PropertyValueExpression right)
    {
        this.right = right;
    }
}
