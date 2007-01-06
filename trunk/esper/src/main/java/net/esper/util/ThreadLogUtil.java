package net.esper.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadLogUtil
{
    public static int TRACE = 0;
    public static int INFO = 1;

    // TODO: disable
    protected final static Boolean ENABLED_TRACE = false;
    protected final static Boolean ENABLED_INFO = true;

    public static void trace(String text, Object ... objects)
    {
        if (!ENABLED_TRACE)
        {
            return;
        }
        write(text, objects);
    }

    public static void info(String text, Object ... objects)
    {
        if (!ENABLED_INFO)
        {
            return;
        }
        write(text, objects);
    }

    public static void traceLock(String lockAction, ReentrantLock lock)
    {
        if (!ENABLED_TRACE)
        {
            return;
        }
        write(lockAction + " " + getLockInfo(lock));
    }

    public static void traceLock(String lockAction, ReentrantReadWriteLock lock)
    {
        if (!ENABLED_TRACE)
        {
            return;
        }
        write(lockAction + " " + getLockInfo(lock));
    }

    private static String getLockInfo(ReentrantLock lock)
    {
        String lockid = "Lock@" + Integer.toHexString(lock.hashCode());
        return "lock " + lockid + " held=" + lock.getHoldCount() + " isHeldMe=" + lock.isHeldByCurrentThread() +
                " hasQueueThreads=" + lock.hasQueuedThreads();
    }

    private static String getLockInfo(ReentrantReadWriteLock lock)
    {
        String lockid = "RWLock@" + Integer.toHexString(lock.hashCode());
        return lockid +
               " readLockCount=" + lock.getReadLockCount() +
               " isWriteLocked=" + lock.isWriteLocked();
    }

    private static void write(String text, Object ... objects)
    {
        StringBuffer buf = new StringBuffer();
        buf.append(text);
        buf.append(' ');
        for (Object obj : objects)
        {
            if ((obj instanceof String) || (obj instanceof Number))
            {
                buf.append(obj.toString());
            }
            else
            {
                buf.append(obj.getClass().getSimpleName());
                buf.append('@');
                buf.append(Integer.toHexString(obj.hashCode()));
            }
            buf.append(' ');
        }
        write(buf.toString());
    }

    private static void write(String text)
    {
        log.info(".write Thread " + Thread.currentThread().getId() + " " + text);
    }

    private static final Log log = LogFactory.getLog(ThreadLogUtil.class);
}
