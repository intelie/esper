package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;

public class NewItem {
    private final String name;
    private final ExprNode optExpression;

    public NewItem(String name, ExprNode optExpression) {
        this.name = name;
        this.optExpression = optExpression;
    }

    public String getName() {
        return name;
    }

    public ExprNode getOptExpression() {
        return optExpression;
    }
}
