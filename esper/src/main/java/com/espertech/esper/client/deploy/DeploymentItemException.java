package com.espertech.esper.client.deploy;

import com.espertech.esper.client.EPException;

public class DeploymentItemException extends Exception {

    private String expression;
    private EPException inner;

    public DeploymentItemException(String message, String expression, EPException inner) {
        super(message);
        this.expression = expression;
        this.inner = inner;
    }

    public String getExpression() {
        return expression;
    }

    public EPException getInner() {
        return inner;
    }
}
