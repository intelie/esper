package com.espertech.esper.client.deploy;

import java.util.Calendar;
import java.util.Set;

public class DeploymentInformationItem
{
    private String statementName;
    private String expression;

    public DeploymentInformationItem(String statementName, String expression) {
        this.statementName = statementName;
        this.expression = expression;
    }

    public String getStatementName() {
        return statementName;
    }

    public String getExpression() {
        return expression;
    }

    public String toString() {
        return "name '" + statementName + "' " +
               " expression " + expression;
    }
}