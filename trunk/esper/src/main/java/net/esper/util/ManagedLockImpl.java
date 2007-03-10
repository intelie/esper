package net.esper.util;

import net.esper.core.StatementLockFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple lock based on {@link ReentrantLock} that associates a name with the lock and traces locking and unlocking.
 */
public class ManagedLockImpl implements ManagedLock
{
    private final ReentrantLock lock;
    private final String name;

    /**
     * Ctor.
     * @param name of lock
     */
    public ManagedLockImpl(String name)
    {
        this.name = name;
        this.lock = new ReentrantLock();
    }

    /**
     * Lock.
     */
    public void acquireLock(StatementLockFactory statementLockFactory)
    {
        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(ManagedReadWriteLock.ACQUIRE_TEXT + name, lock);
        }

        lock.lock();

        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(ManagedReadWriteLock.ACQUIRED_TEXT + name, lock);
        }
    }

    /**
     * Unlock.
     */
    public void releaseLock(StatementLockFactory statementLockFactory)
    {
        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(ManagedReadWriteLock.RELEASE_TEXT + name, lock);
        }

        lock.unlock();

        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(ManagedReadWriteLock.RELEASED_TEXT + name, lock);
        }
    }
}
