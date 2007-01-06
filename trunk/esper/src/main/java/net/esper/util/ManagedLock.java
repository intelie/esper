package net.esper.util;

import java.util.concurrent.locks.ReentrantLock;

public class ManagedLock
{
    private final ReentrantLock lock;
    private final String name;

    public ManagedLock(String name)
    {
        this.name = name;
        this.lock = new ReentrantLock();
    }

    public void acquireLock()
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

    public void releaseLock()
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
