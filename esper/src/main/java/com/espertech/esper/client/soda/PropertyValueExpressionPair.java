package com.espertech.esper.client.soda;

import java.io.Serializable;

public class PropertyValueExpressionPair implements Serializable {

    private Expression left;
    private Expression right;

    public PropertyValueExpressionPair() {
    }

    public PropertyValueExpressionPair(PropertyValueExpression left, PropertyValueExpression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }
}
