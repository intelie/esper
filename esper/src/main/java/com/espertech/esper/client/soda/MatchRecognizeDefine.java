package com.espertech.esper.client.soda;

import java.io.Serializable;

public class MatchRecognizeDefine implements Serializable {
    private String name;
    private Expression expression;

    public MatchRecognizeDefine() {
    }

    public MatchRecognizeDefine(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
