package net.esper.eql.join.rep;

import junit.framework.*;
import java.util.NoSuchElementException;

import net.esper.event.EventBean;
import net.esper.collection.SingleEventIterator;

public class TestSingleCursorIterator extends TestCase
{
    private SingleCursorIterator filledIterator;
    private SingleCursorIterator emptyIterator;
    private Cursor cursor;

    public void setUp()
    {
        cursor = makeAnonymousCursor();
        filledIterator = new SingleCursorIterator(cursor);
        emptyIterator = new SingleCursorIterator(null);
    }

    public void testNext()
    {
        assertSame(cursor, filledIterator.next());
        try
        {
            filledIterator.next();
            TestCase.fail();
        }
        catch (NoSuchElementException ex)
        {
            // Expected exception
        }

        try
        {
            emptyIterator.next();
            TestCase.fail();
        }
        catch (NoSuchElementException ex)
        {
            // Expected exception
        }
    }

    public void testHasNext()
    {
        assertTrue(filledIterator.hasNext());
        filledIterator.next();
        assertFalse(filledIterator.hasNext());

        assertFalse(emptyIterator.hasNext());
    }

    public void testRemove()
    {
        try
        {
            filledIterator.remove();
            assertTrue(false);
        }
        catch (UnsupportedOperationException ex)
        {
            // Expected exception
        }
    }

    private Cursor makeAnonymousCursor()
    {
        return new Cursor(null, 0, null) {

            public EventBean getLookupEvent()
            {
                return null;
            }

            public int getLookupStream()
            {
                return 0;
            }

            public int getIndexedStream()
            {
                return 0;
            }
        };
    }
}