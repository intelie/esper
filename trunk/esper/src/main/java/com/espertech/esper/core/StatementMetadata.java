package com.espertech.esper.core;

public class StatementMetadata
{
    private StatementType statementType;

    public StatementMetadata(StatementType statementType)
    {
        this.statementType = statementType;
    }

    public StatementType getStatementType()
    {
        return statementType;
    }
}
