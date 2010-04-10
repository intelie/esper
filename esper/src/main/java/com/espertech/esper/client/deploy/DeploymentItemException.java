package com.espertech.esper.client.deploy;

import com.espertech.esper.client.EPException;

/**
 * Inner exception to {@link DeploymentException} available on statement level.
 */
public class DeploymentItemException extends Exception {

    private String expression;
    private EPException inner;

    /**
     * Ctor.
     * @param message exception text
     * @param expression EPL
     * @param inner compile or start exception
     */
    public DeploymentItemException(String message, String expression, EPException inner) {
        super(message);
        this.expression = expression;
        this.inner = inner;
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
}