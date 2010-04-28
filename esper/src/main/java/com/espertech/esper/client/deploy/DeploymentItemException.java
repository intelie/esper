package com.espertech.esper.client.deploy;

import com.espertech.esper.client.EPException;

/**
 * Inner exception to {@link DeploymentActionException} available on statement level.
 */
public class DeploymentItemException extends DeploymentException {

    private String expression;
    private EPException inner;
    private int lineNumber;

    /**
     * Ctor.
     * @param message exception text
     * @param expression EPL
     * @param inner compile or start exception
     */
    public DeploymentItemException(String message, String expression, EPException inner, int lineNumber) {
        super(message);
        this.expression = expression;
        this.inner = inner;
        this.lineNumber = lineNumber;
    }

    /**
     * Returns EPL expression.
     * @return expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Returns EPL compile or start exception.
     * @return exception
     */
    public EPException getInner() {
        return inner;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }
}