package net.esper.collection;

import junit.framework.*;
import net.esper.support.util.ArrayAssertionUtil;

public class TestIndexedDataCollection extends TestCase
{
    public void testAddRemoveGet()
    {
        IndexedDataCollection index = new IndexedDataCollection();

        // Empty key should return null
        assertEquals(null, index.get("a"));

        // Add value for key 'a'
        index.add("a", "a1");

        // Empty key should return null
        assertEquals(null, index.get("a1"));
        assertEquals(null, index.get("b"));

        // Add more values
        index.add("b", "b1");
        index.add("b", "b2");
        index.add("c", "c1");
        index.add("c", "c2");
        index.add("c", "c3");
        index.add("d", "d1");
        index.add("d", "d2");
        index.add("d", "d3");

        // Check all values
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("a").toArray(), new Object[] { "a1" });
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("b").toArray(), new Object[] { "b1", "b2" });
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("c").toArray(), new Object[] { "c1", "c2", "c3" });
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("d").toArray(), new Object[] { "d1", "d2", "d3" });

        // Remove and check some
        assertTrue(index.remove("a", "a1"));
        assertEquals(null, index.get("a"));

        assertTrue(index.remove("c", "c1"));
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("c").toArray(), new Object[] { "c2", "c3" });
        assertTrue(index.remove("c", "c3"));
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("c").toArray(), new Object[] { "c2" });
        assertFalse(index.remove("c", "c1"));
        assertTrue(index.remove("c", "c2"));
        assertFalse(index.remove("c", "c2"));
        assertEquals(null, index.get("c"));

        assertTrue(index.remove("d", "d3"));
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("d").toArray(), new Object[] { "d1", "d2" });

        // Add some more
        index.add("d", "d1");
        index.add("d", "d2");
        index.add("d", "d3");
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("d").toArray(), new Object[] { "d1", "d2", "d1", "d2", "d3" });
        assertTrue(index.remove("d", "d1"));

        // Remove again
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("d").toArray(), new Object[] { "d2", "d1", "d2", "d3" });
        assertTrue(index.remove("d", "d1"));
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("d").toArray(), new Object[] { "d2", "d2", "d3" });
        assertFalse(index.remove("d", "d1"));
        assertTrue(index.remove("d", "d2"));
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("d").toArray(), new Object[] { "d2", "d3" });
        assertTrue(index.remove("d", "d3"));
        ArrayAssertionUtil.assertEqualsExactOrder(index.get("d").toArray(), new Object[] { "d2" });
        assertTrue(index.remove("d", "d2"));
        assertEquals(null, index.get("d"));

        index.remove("b", "b1");
        index.remove("b", "b2");

        assertEquals(null, index.get("a"));
        assertEquals(null, index.get("b"));
        assertEquals(null, index.get("c"));
        assertEquals(null, index.get("d"));
    }
}