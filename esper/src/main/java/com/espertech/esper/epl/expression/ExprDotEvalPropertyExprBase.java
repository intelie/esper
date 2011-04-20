package com.espertech.esper.epl.expression;

import com.espertech.esper.util.JavaClassHelper;

import java.util.Map;

public abstract class ExprDotEvalPropertyExprBase implements ExprEvaluator {

    protected final String statementName;
    protected final String propertyName;
    protected final int streamNum;
    protected final ExprEvaluator exprEvaluator;
    private final Class propertyType;

    protected ExprDotEvalPropertyExprBase(String statementName, String propertyName, int streamNum, ExprEvaluator exprEvaluator, Class propertyType) {
        this.statementName = statementName;
        this.propertyName = propertyName;
        this.streamNum = streamNum;
        this.exprEvaluator = exprEvaluator;
        this.propertyType = propertyType;
    }

    public Class getType() {
        return propertyType;
    }

    public Map<String, Object> getEventType() throws ExprValidationException {
        return null;
    }

    protected String getWarningText(String expectedType, Object received) {
        return "Statement '" + statementName + "' property " + propertyName + " parameter expression expected a value of " +
                expectedType + " but received " + received == null ? "null" : JavaClassHelper.getClassNameFullyQualPretty(received.getClass());
    }
}
