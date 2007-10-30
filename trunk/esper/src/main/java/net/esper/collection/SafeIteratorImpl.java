package net.esper.collection;

import net.esper.util.ManagedLock;
import net.esper.client.SafeIterator;

import java.util.Iterator;

public class SafeIteratorImpl<E> implements SafeIterator<E>
{
    private final ManagedLock iteratorLock;
    private final Iterator<E> underlying;
    private boolean lockTaken;

    public SafeIteratorImpl(ManagedLock iteratorLock, Iterator<E> underlying)
    {
        this.iteratorLock = iteratorLock;
        this.underlying = underlying;
        this.lockTaken = true;
    }

    public boolean hasNext()
    {
        return underlying.hasNext();
    }

    public E next()
    {
        return underlying.next();
    }

    public void close()
    {
        if (lockTaken)
        {
            iteratorLock.releaseLock(null);
            lockTaken = false;
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException("Remove operation not supported");
    }
}
