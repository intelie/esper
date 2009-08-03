package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;

public class MatchRecognizeDefineItem {

    private String identifier;
    private ExprNode expression;

    public MatchRecognizeDefineItem(String identifier, ExprNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ExprNode getExpression() {
        return expression;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setExpression(ExprNode expression) {
        this.expression = expression;
    }
}
