/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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

    public boolean isHeldByCurrentThread();
}
