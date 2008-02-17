package com.espertech.esper.core;

import com.espertech.esper.util.ManagedLock;
import com.espertech.esper.util.ManagedLockImpl;

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
