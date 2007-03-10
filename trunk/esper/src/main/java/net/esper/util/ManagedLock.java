package net.esper.util;

import net.esper.core.StatementLockFactory;

/**
 * Interface for a lock for use to perform statement-level locking.
 */
public interface ManagedLock
{
    /**
     * Acquire a lock.
     * @param statementLockFactory is the engine lock factory service that the lock can use for engine lock services 
     */
    public void acquireLock(StatementLockFactory statementLockFactory);

    /**
     * Release a lock.
     * @param statementLockFactory is the engine lock factory service that the lock can use for engine lock services 
     */
    public void releaseLock(StatementLockFactory statementLockFactory);
}
