package net.esper.client.logstate;

import junit.framework.TestCase;

public class TestLogKey extends TestCase
{
    LogKey keys1 = new LogKey(new int[] {1, 2 });
    LogKey keys2 = new LogKey(new int[] {1, 2});
    LogKey keys6 = new LogKey(new int[] {1});
    LogKey keys7 = new LogKey(new int[] {1, 2, 3});
    LogKey keys9 = new LogKey(new int[] {1, 2, 3, 4});
    LogKey keys10 = new LogKey(new int[] {1, 2, 3, 4});

    public void testHashCode()
    {
        assertTrue(keys1.hashCode() == (Integer.valueOf(1).hashCode() ^ Integer.valueOf(2).hashCode()));
        assertTrue(keys10.hashCode() == (Integer.valueOf(1).hashCode() ^ Integer.valueOf(2).hashCode() ^ Integer.valueOf(3).hashCode() ^ Integer.valueOf(4).hashCode()));
        assertTrue(keys6.hashCode() == Integer.valueOf(1).hashCode());
        assertTrue(keys1.hashCode() == keys2.hashCode());
        assertTrue(keys9.hashCode() == keys10.hashCode());
    }

    public void testEquals()
    {
        assertEquals(keys2, keys1);
        assertEquals(keys1, keys2);

        assertTrue(keys1.equals(keys1));
        assertTrue(keys2.equals(keys2));

        assertFalse(keys1.equals(keys6));
        assertFalse(keys1.equals(keys7));
        assertFalse(keys1.equals(keys9));
        assertFalse(keys1.equals(keys10));

        assertTrue(keys9.equals(keys10));
    }

    public void testGet()
    {
        assertEquals(1, keys6.size());
        assertEquals(2, keys1.size());
        assertEquals(4, keys9.size());

        assertEquals(1, keys1.get(0));
        assertEquals(2, keys1.get(1));
        assertEquals(4, keys10.get(3));
    }
}
