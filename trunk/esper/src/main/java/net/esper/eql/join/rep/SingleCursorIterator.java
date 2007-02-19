package net.esper.eql.join.rep;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A utility class for an iterator that has one element.
 */
public class SingleCursorIterator implements Iterator<Cursor>
{
    private Cursor cursor;

    /**
     * Ctor.
     * @param cursor is the single element.
     */
    public SingleCursorIterator(Cursor cursor)
    {
        this.cursor = cursor;
    }

    public boolean hasNext()
    {
        return (cursor != null);
    }

    public Cursor next()
    {
        if (cursor == null)
        {
            throw new NoSuchElementException();
        }
        Cursor c = cursor;
        this.cursor = null;
        return c;
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}

