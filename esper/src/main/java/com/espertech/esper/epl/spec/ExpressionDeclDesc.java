package com.espertech.esper.epl.spec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpressionDeclDesc implements Serializable {

    private static final long serialVersionUID = -8155216999087913248L;

    private List<ExpressionDeclItem> expressions = new ArrayList<ExpressionDeclItem>();

    public List<ExpressionDeclItem> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<ExpressionDeclItem> expressions) {
        this.expressions = expressions;
    }
}
