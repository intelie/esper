package net.esper.core;

public interface StatementContextFactory
{
    public StatementContext makeContext(String statementId, String statementName, String expression, EPServicesContext engineServices);
}
