package net.esper.util;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Simple read-write lock based on {@link java.util.concurrent.locks.ReentrantReadWriteLock} that associates a
 * name with the lock and traces read/write locking and unlocking.
 */
public class ManagedReadWriteLock
{
    /**
     * Acquire text.
     */
    protected final static String ACQUIRE_TEXT  = "Acquire ";

    /**
     * Acquired text.
     */
    protected final static String ACQUIRED_TEXT = "Got     ";

    /**
     * Release text.
     */
    protected final static String RELEASE_TEXT  = "Release ";

    /**
     * Released text.
     */
    protected final static String RELEASED_TEXT = "Freed   ";

    private final ReentrantReadWriteLock lock;
    private final String name;

    /**
     * Ctor.
     * @param name of lock
     */
    public ManagedReadWriteLock(String name)
    {
        this.name = name;
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * Lock write lock.
     */
    public void acquireWriteLock()
    {
        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(ACQUIRE_TEXT + " write " + name, lock);
        }

        lock.writeLock().lock();

        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(ACQUIRED_TEXT + " write " + name, lock);
        }
    }

    /**
     * Unlock write lock.
     */
    public void releaseWriteLock()
    {
        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(RELEASE_TEXT + " write " + name, lock);
        }

        lock.writeLock().unlock();

        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(RELEASED_TEXT + " write " + name, lock);
        }
    }

    /**
     * Lock read lock.
     */
    public void acquireReadLock()
    {
        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(ACQUIRE_TEXT + " read " + name, lock);
        }

        lock.readLock().lock();

        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(ACQUIRED_TEXT + " read " + name, lock);
        }
    }

    /**
     * Unlock read lock.
     */
    public void releaseReadLock()
    {
        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(RELEASE_TEXT + " read " + name, lock);
        }

        lock.readLock().unlock();

        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.traceLock(RELEASED_TEXT + " read " + name, lock);
        }
    }
}
