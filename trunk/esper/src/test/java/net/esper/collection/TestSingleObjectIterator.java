package net.esper.collection;

import junit.framework.TestCase;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TestSingleObjectIterator extends TestCase
{
    public void testNext()
    {
        Iterator<String> it = new SingleObjectIterator<String>("a");
        assertTrue(it.hasNext());
        assertEquals("a", it.next());
        assertFalse(it.hasNext());

        try
        {
            it.next();
            fail();
        }
        catch (NoSuchElementException ex)
        {
            // expected
        }
    }
}
