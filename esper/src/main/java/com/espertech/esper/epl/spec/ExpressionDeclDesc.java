package com.espertech.esper.epl.spec;

import java.util.ArrayList;
import java.util.List;

public class ExpressionDeclDesc {

    private List<ExpressionDeclItem> expressions = new ArrayList<ExpressionDeclItem>();

    public List<ExpressionDeclItem> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<ExpressionDeclItem> expressions) {
        this.expressions = expressions;
    }
}
