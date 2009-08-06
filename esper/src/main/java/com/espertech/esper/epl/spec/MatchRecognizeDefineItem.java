package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;

public class MatchRecognizeDefineItem implements MetaDefItem, Serializable
{

    private String identifier;
    private ExprNode expression;
    private static final long serialVersionUID = -7736241770279336651L;

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
