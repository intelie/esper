package net.esper.util;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ManagedReadWriteLock
{
    protected final static String ACQUIRE_TEXT  = "Acquire ";
    protected final static String ACQUIRED_TEXT = "Got     ";
    protected final static String RELEASE_TEXT  = "Release ";
    protected final static String RELEASED_TEXT = "Freed   ";

    private final ReentrantReadWriteLock lock;
    private final String name;

    public ManagedReadWriteLock(String name)
    {
        this.name = name;
        this.lock = new ReentrantReadWriteLock();
    }

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
