package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;

public class MatchRecognizeMeasureItem implements MetaDefItem, Serializable 
{
    private ExprNode expr;
    private String name;
    private static final long serialVersionUID = 1609117378292500082L;

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
