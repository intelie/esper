package net.esper.collection;

import junit.framework.*;

import java.util.List;
import java.util.Iterator;
import java.util.ConcurrentModificationException;

import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.event.EventBean;
import net.esper.client.EPException;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class TestTimeWindow extends TestCase
{
    private final TimeWindow window = new TimeWindow();
    private final EventBean[] beans = new EventBean[6];

    public void setUp()
    {
        for (int i = 0; i < beans.length; i++)
        {
            beans[i] = createBean();
        }
    }

    public void testAdd()
    {
        assertTrue(window.getOldestTimestamp() == null);
        assertTrue(window.isEmpty());

        window.add(19,beans[0]);
        assertTrue(window.getOldestTimestamp() == 19L);
        assertFalse(window.isEmpty());
        window.add(19,beans[1]);
        assertTrue(window.getOldestTimestamp() == 19L);
        window.add(20,beans[2]);
        assertTrue(window.getOldestTimestamp() == 19L);
        window.add(20,beans[3]);
        window.add(21, beans[4]);
        window.add(22, beans[5]);
        assertTrue(window.getOldestTimestamp() == 19L);

        List<EventBean> beanList = window.expireEvents(19);
        assertTrue(beanList == null);

        beanList = window.expireEvents(20);
        assertTrue(beanList.size() == 2);
        assertTrue(beanList.get(0) == beans[0]);
        assertTrue(beanList.get(1) == beans[1]);

        beanList = window.expireEvents(21);
        assertTrue(beanList.size() == 2);
        assertTrue(beanList.get(0) == beans[2]);
        assertTrue(beanList.get(1) == beans[3]);
        assertFalse(window.isEmpty());
        assertTrue(window.getOldestTimestamp() == 21);

        beanList = window.expireEvents(22);
        assertTrue(beanList.size() == 1);
        assertTrue(beanList.get(0) == beans[4]);
        assertFalse(window.isEmpty());
        assertTrue(window.getOldestTimestamp() == 22);

        beanList = window.expireEvents(23);
        assertTrue(beanList.size() == 1);
        assertTrue(beanList.get(0) == beans[5]);
        assertTrue(window.isEmpty());
        assertTrue(window.getOldestTimestamp() == null);

        beanList = window.expireEvents(23);
        assertTrue(beanList == null);
        assertTrue(window.isEmpty());
        assertTrue(window.getOldestTimestamp() == null);
    }

    public void testTimeWindowPerformance()
    {
        log.info(".testTimeWindowPerformance Starting");

        TimeWindow window = new TimeWindow();

        // 1E7 yields for implementations...on 2.8GHz JDK 1.5
        // about 7.5 seconds for a LinkedList-backed
        // about 20 seconds for a LinkedHashMap-backed
        // about 30 seconds for a TreeMap-backed-backed
        for (int i = 0; i < 10; i++)
        {
            window.add(i, SupportEventBeanFactory.createObject("a"));

            window.expireEvents(i - 100);
        }

        log.info(".testTimeWindowPerformance Done");
    }

    public void testConcurrentIterator()
    {
        window.add(10000, beans[0]);
        Iterator<EventBean> it = window.iterator();
        window.add(10000, beans[1]);

        try
        {
            ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {beans[0], beans[1]});
            fail();
        }
        catch (ConcurrentModificationException ex)
        {
            // expected 
        }
    }

    private EventBean createBean()
    {
        return SupportEventBeanFactory.createObject(new SupportBean());
    }

    private static final Log log = LogFactory.getLog(TestTimeWindow.class);
}