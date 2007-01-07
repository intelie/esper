package net.esper.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator that never returns an elements.
 */
public class NullIterator<T> implements Iterator<T>
{
    public boolean hasNext()
    {
        return false;
    }

    public T next()
    {
        throw new NoSuchElementException();
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
