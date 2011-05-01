package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprNode;

public class ExprDotEvalParamExpr extends ExprDotEvalParam {

    public ExprDotEvalParamExpr(int parameterNum, ExprNode body, ExprEvaluator bodyEvaluator) {
        super(parameterNum, body, bodyEvaluator);
    }
}
