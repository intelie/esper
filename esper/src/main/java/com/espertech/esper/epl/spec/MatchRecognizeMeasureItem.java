package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;

public class MatchRecognizeMeasureItem {
    private ExprNode expr;
    private String name;

    public MatchRecognizeMeasureItem(ExprNode expr, String name) {
        this.expr = expr;
        this.name = name;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public String getName() {
        return name;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    public void setName(String name) {
        this.name = name;
    }
}
