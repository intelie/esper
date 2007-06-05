package net.esper.multithread;

import junit.framework.TestCase;

import java.util.concurrent.locks.ReentrantLock;

public class TestMTDeterminismLocking extends TestCase
{
    // TODO: remove
    public void testLock()
    {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lock();
        lock.unlock();
        lock.unlock();
        
        System.out.println(lock.isLocked());
    }

    public void testPerform()
    {
        ReentrantLock lock = new ReentrantLock();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++)
        {
            if (lock.isHeldByCurrentThread())
            {
                System.out.println(lock.isLocked());
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(start - end);
    }
}
