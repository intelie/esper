package net.esper.eql.db;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.UnindexedEventTable;

import java.util.List;
import java.util.LinkedList;

public class TestDataCacheLRUImpl extends TestCase
{
    private DataCacheLRUImpl cache;
    private EventTable[] lists = new EventTable[10];

    public void setUp()
    {
        cache = new DataCacheLRUImpl(3);
        for (int i = 0; i < lists.length; i++)
        {
            lists[i] = new UnindexedEventTable(0);
        }
    }

    public void testGet()
    {
        assertNull(cache.getCached(make("a")));
        assertTrue(cache.isActive());

        cache.put(make("a"), lists[0]);     // a
        assertSame(lists[0], cache.getCached(make("a")));

        cache.put(make("b"), lists[1]);     // b, a
        assertSame(lists[1], cache.getCached(make("b"))); // b, a

        assertSame(lists[0], cache.getCached(make("a"))); // a, b

        cache.put(make("c"), lists[2]);     // c, a, b
        cache.put(make("d"), lists[3]);     // d, c, a  (b gone)

        assertNull(cache.getCached(make("b")));

        assertEquals(lists[2], cache.getCached(make("c"))); // c, d, a
        assertEquals(lists[0], cache.getCached(make("a"))); // a, c, d

        cache.put(make("e"), lists[4]); // e, a, c (d and b gone)

        assertNull(cache.getCached(make("d")));
        assertNull(cache.getCached(make("b")));
    }

    private Object[] make(String key)
    {
        return new Object[] {key};
    }
}
