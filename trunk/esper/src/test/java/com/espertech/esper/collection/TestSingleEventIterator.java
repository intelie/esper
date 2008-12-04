package com.espertech.esper.collection;

import junit.framework.*;
import java.util.NoSuchElementException;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;

public class TestSingleEventIterator extends TestCase
{
    private SingleEventIterator iterator;
    private EventBean eventBean;

    public void setUp()
    {
        eventBean = SupportEventBeanFactory.createObject("a");
        iterator = new SingleEventIterator(eventBean);
    }

    public void testNext()
    {
        assertEquals(eventBean, iterator.next());
        try
        {
            iterator.next();
            TestCase.fail();
        }
        catch (NoSuchElementException ex)
        {
            // Expected exception
        }
    }

    public void testHasNext()
    {
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    public void testRemove()
    {
        try
        {
            iterator.remove();
            assertTrue(false);
        }
        catch (UnsupportedOperationException ex)
        {
            // Expected exception
        }
    }
}