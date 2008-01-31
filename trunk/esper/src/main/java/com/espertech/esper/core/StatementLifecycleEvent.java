package com.espertech.esper.core;

public class StatementLifecycleEvent
{
    private String statementId;
    private String statementName;

    protected StatementLifecycleEvent(String statementId, String statementName)
    {
        this.statementId = statementId;
        this.statementName = statementName;
    }

    public String getStatementId()
    {
        return statementId;
    }

    public String getStatementName()
    {
        return statementName;
    }
}
