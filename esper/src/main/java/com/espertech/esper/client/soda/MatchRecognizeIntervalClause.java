package com.espertech.esper.client.soda;

import java.io.Serializable;

public class MatchRecognizeIntervalClause implements Serializable {
    private Expression expression;

    public MatchRecognizeIntervalClause() {
    }

    public MatchRecognizeIntervalClause(TimePeriodExpression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
