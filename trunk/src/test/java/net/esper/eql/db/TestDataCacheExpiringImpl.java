package net.esper.eql.db;

import net.esper.event.EventBean;
import net.esper.support.schedule.SupportSchedulingServiceImpl;

import java.util.List;
import java.util.LinkedList;

import junit.framework.TestCase;

public class TestDataCacheExpiringImpl extends TestCase
{
    private SupportSchedulingServiceImpl scheduler;
    private DataCacheExpiringImpl cache;
    private List<EventBean>[] lists = new LinkedList[10];

    public void setUp()
    {
        scheduler = new SupportSchedulingServiceImpl();
        cache = new DataCacheExpiringImpl(10, 1000, scheduler);   // age 10 sec
        for (int i = 0; i < lists.length; i++)
        {
            lists[i] = new LinkedList<EventBean>();
        }
    }

    public void testGet()
    {
        assertNull(cache.getCached(make("a")));

        scheduler.setTime(5000);
        cache.put(make("a"), lists[0]); // a at 5 sec
        assertSame(lists[0], cache.getCached(make("a")));

        scheduler.setTime(10000);
        cache.put(make("b"), lists[1]); // b at 10 sec
        assertSame(lists[0], cache.getCached(make("a")));
        assertSame(lists[1], cache.getCached(make("b")));

        scheduler.setTime(11000);
        cache.put(make("c"), lists[2]); // c at 11 sec
        cache.put(make("d"), lists[3]); // d at 11 sec

        scheduler.setTime(14999);
        assertSame(lists[0], cache.getCached(make("a")));

        scheduler.setTime(15000);
        assertSame(lists[0], cache.getCached(make("a")));

        scheduler.setTime(15001);
        assertNull(cache.getCached(make("a")));

        scheduler.setTime(15001);
        assertNull(cache.getCached(make("a")));

        scheduler.setTime(15001);
        assertNull(cache.getCached(make("a")));
        assertSame(lists[1], cache.getCached(make("b")));
        assertSame(lists[2], cache.getCached(make("c")));
        assertSame(lists[3], cache.getCached(make("d")));

        scheduler.setTime(20000);
        assertSame(lists[1], cache.getCached(make("b")));

        scheduler.setTime(20001);
        assertNull(cache.getCached(make("b")));

        scheduler.setTime(21001);
        assertNull(cache.getCached(make("a")));
        assertNull(cache.getCached(make("b")));
        assertNull(cache.getCached(make("c")));
        assertNull(cache.getCached(make("d")));

        scheduler.setTime(22000);
        cache.put(make("b"), lists[1]); // b at 22 sec
        cache.put(make("d"), lists[3]); // d at 22 sec

        scheduler.setTime(32000);
        assertSame(lists[1], cache.getCached(make("b")));
        assertSame(lists[3], cache.getCached(make("d")));
    }

    private Object[] make(String key)
    {
        return new Object[] {key};
    }
}
