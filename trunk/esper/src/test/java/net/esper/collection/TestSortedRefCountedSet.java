package net.esper.collection;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

public class TestSortedRefCountedSet extends TestCase
{
    private SortedRefCountedSet<String> refSet;
    private Random random = new Random();

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

    public void testMemoryUse()
    {
        SortedRefCountedSet<Double> set = new SortedRefCountedSet<Double>();

        long memoryBefore = Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().gc();
        
        for (int i = 0; i < 2; i++)
        {
            performLoop(i, set);

            Runtime.getRuntime().gc();
            long memoryAfter = Runtime.getRuntime().freeMemory();

            log.info("Memory before=" + memoryBefore +
                        " after=" + memoryAfter +
                        " delta=" + (memoryAfter - memoryBefore));

            assertTrue(memoryBefore + 10000 <= memoryAfter);
        }
    }

    private void performLoop(int loop, SortedRefCountedSet<Double> set)
    {
        for (int i = 0; i < 1000; i++)
        {
            double price = 500000 + 4900 * random.nextDouble();
            set.add(price);
            set.remove(price);
        }
    }


    private static Log log = LogFactory.getLog(TestSortedRefCountedSet.class);
}
