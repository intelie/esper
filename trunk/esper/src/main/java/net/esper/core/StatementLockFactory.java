package net.esper.core;

import net.esper.util.ManagedLock;

public interface StatementLockFactory
{
    public ManagedLock getStatementLock(String statementName, String expressionText);
}
