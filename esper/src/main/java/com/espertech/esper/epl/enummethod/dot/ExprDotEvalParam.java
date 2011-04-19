package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprNode;

public abstract class ExprDotEvalParam {
    private int parameterNum;
    private ExprNode body;
    private ExprEvaluator bodyEvaluator;

    protected ExprDotEvalParam(int parameterNum, ExprNode body, ExprEvaluator bodyEvaluator) {
        this.parameterNum = parameterNum;
        this.body = body;
        this.bodyEvaluator = bodyEvaluator;
    }

    public int getParameterNum() {
        return parameterNum;
    }

    public ExprNode getBody() {
        return body;
    }

    public ExprEvaluator getBodyEvaluator() {
        return bodyEvaluator;
    }
}
