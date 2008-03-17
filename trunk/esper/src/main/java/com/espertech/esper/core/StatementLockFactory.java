package com.espertech.esper.core;

import com.espertech.esper.util.ManagedLock;

/**
 * Factory for the managed lock that provides statement resource protection.
 */
public interface StatementLockFactory
{
    /**
     * Create lock for statement
     * @param statementName is the statement name
     * @param expressionText is the statement expression text
     * @return lock
     */
    public ManagedLock getStatementLock(String statementName, String expressionText);
}
