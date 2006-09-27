package net.esper.view.ext;

import junit.framework.TestCase;
import java.util.*;

import net.esper.collection.MultiKey;
import net.esper.event.EventBean;
import net.esper.support.event.EventFactoryHelper;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.util.MultiKeyComparator;

public class TestSortWindowIterator extends TestCase
{
    private Map<String, EventBean> events;
	private SortedMap<MultiKey, LinkedList<EventBean>> testMap;
	private Comparator<MultiKey> comparator;
	
    public void setUp()
    {
        events = EventFactoryHelper.makeEventMap(new String[] {"a", "b", "c", "d", "f", "g"});
        comparator = new MultiKeyComparator(new Boolean[] {false});
        testMap = new TreeMap<MultiKey, LinkedList<EventBean>>(comparator);
    }

    public void testEmpty()
    {
        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, null);
    }

    public void testOneElement()
    {
        LinkedList<EventBean> list = new LinkedList<EventBean>();
        list.add(events.get("a"));
        MultiKey key = new MultiKey<Object>(new Object[] {"akey"});
        testMap.put(key, list);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a")} );
    }

    public void testTwoInOneEntryElement()
    {
        LinkedList<EventBean> list = new LinkedList<EventBean>();
        list.add(events.get("a"));
        list.add(events.get("b"));
        MultiKey key = new MultiKey<Object>(new Object[] {"keyA"});
        testMap.put(key, list);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("b")} );
    }

    public void testTwoSeparateEntryElement()
    {
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        MultiKey keyB = new MultiKey<Object>(new Object[] {"keyB"});
        testMap.put(keyB, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("b"));
        MultiKey keyA = new MultiKey<Object>(new Object[] {"keyA"});
        testMap.put(keyA, list2); // Actually before list1

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("b"), events.get("a")} );
    }

    public void testTwoByTwoEntryElement()
    {
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        list1.add(events.get("b"));
        MultiKey keyB = new MultiKey<Object>(new Object[] {"keyB"});
        testMap.put(keyB, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        MultiKey keyC = new MultiKey<Object>(new Object[] {"keyC"});
        testMap.put(keyC, list2);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("b"), events.get("c"), events.get("d")} );
    }

    public void testMixedEntryElement()
    {
		LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        MultiKey keyA = new MultiKey<Object>(new Object[] {"keyA"});
        testMap.put(keyA, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        MultiKey keyB = new MultiKey<Object>(new Object[] {"keyB"});
        testMap.put(keyB, list2);
        LinkedList<EventBean> list3 = new LinkedList<EventBean>();
        list3.add(events.get("e"));
        list3.add(events.get("f"));
        list3.add(events.get("g"));
        MultiKey keyC = new MultiKey<Object>(new Object[] {"keyC"});
        testMap.put(keyC, list3);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("c"), events.get("d"),
                events.get("e"), events.get("f"), events.get("g")} );
    }
}


