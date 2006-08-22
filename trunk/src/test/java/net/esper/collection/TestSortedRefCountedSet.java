package net.esper.collection;

import junit.framework.TestCase;

public class TestSortedRefCountedSet extends TestCase
{
    private SortedRefCountedSet<String> refSet;

    public void setUp()
    {
        refSet = new SortedRefCountedSet<String>();
    }

    public void testMaxMinValue()
    {
        refSet.add("a");
        refSet.add("b");
        assertEquals("ba", refSet.maxValue() + refSet.minValue());
        refSet.remove("a");
        assertEquals("bb", refSet.maxValue() + refSet.minValue());
        refSet.remove("b");
        assertNull(refSet.maxValue());
        assertNull(refSet.minValue());

        refSet.add("b");
        refSet.add("a");
        refSet.add("d");
        refSet.add("a");
        refSet.add("c");
        refSet.add("a");
        refSet.add("c");
        assertEquals("da", refSet.maxValue() + refSet.minValue());

        refSet.remove("d");
        assertEquals("ca", refSet.maxValue() + refSet.minValue());

        refSet.remove("a");
        assertEquals("ca", refSet.maxValue() + refSet.minValue());

        refSet.remove("a");
        assertEquals("ca", refSet.maxValue() + refSet.minValue());

        refSet.remove("c");
        assertEquals("ca", refSet.maxValue() + refSet.minValue());

        refSet.remove("c");
        assertEquals("ba", refSet.maxValue() + refSet.minValue());

        refSet.remove("a");
        assertEquals("bb", refSet.maxValue() + refSet.minValue());

        refSet.remove("b");
        assertNull(refSet.maxValue());
        assertNull(refSet.minValue());
    }

    public void testAdd()
    {
        refSet.add("a");
        refSet.add("b");
        refSet.add("a");
        refSet.add("c");
        refSet.add("a");

        assertEquals("c", refSet.maxValue());
        assertEquals("a", refSet.minValue());
    }

    public void testRemove()
    {
        refSet.add("a");
        refSet.remove("a");
        assertNull(refSet.maxValue());
        assertNull(refSet.minValue());

        refSet.add("a");
        refSet.add("a");
        assertEquals("aa", refSet.maxValue() + refSet.minValue());

        refSet.remove("a");
        assertEquals("aa", refSet.maxValue() + refSet.minValue());

        refSet.remove("a");
        assertNull(refSet.maxValue());
        assertNull(refSet.minValue());

        try
        {
            refSet.remove("c");
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }

        try
        {
            refSet.add("a");
            refSet.remove("a");
            refSet.remove("a");
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
    }
}
