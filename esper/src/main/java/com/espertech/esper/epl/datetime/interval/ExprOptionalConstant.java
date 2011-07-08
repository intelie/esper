package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.epl.expression.ExprConstantNode;
import com.espertech.esper.epl.expression.ExprConstantNodeImpl;
import com.espertech.esper.epl.expression.ExprEvaluator;

public class ExprOptionalConstant {
    private final ExprEvaluator evaluator;
    private final Long optionalConstant;

    public ExprOptionalConstant(ExprEvaluator evaluator, Long optionalConstant) {
        this.evaluator = evaluator;
        this.optionalConstant = optionalConstant;
    }

    public ExprOptionalConstant(ExprEvaluator evaluator) {
        this.evaluator = evaluator;
        this.optionalConstant = null;
    }

    public Long getOptionalConstant() {
        return optionalConstant;
    }

    public ExprEvaluator getEvaluator() {
        return evaluator;
    }

    public static ExprOptionalConstant make(long maxValue) {
        ExprConstantNode constantNode = new ExprConstantNodeImpl(maxValue);
        return new ExprOptionalConstant(constantNode.getExprEvaluator(), maxValue);
    }
}
