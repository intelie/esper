package net.esper.core;

import net.esper.util.ManagedLock;
import net.esper.util.ManagedLockImpl;

/**
 * Provides statement-level locks.
 */
public class StatementLockFactoryImpl implements StatementLockFactory
{
    public ManagedLock getStatementLock(String statementName, String expressionText)
    {
        return new ManagedLockImpl(statementName);
    }
}
