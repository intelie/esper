package net.esper.view.ext;

import junit.framework.TestCase;
import java.util.*;

import net.esper.event.EventBean;
import net.esper.support.event.EventFactoryHelper;
import net.esper.support.util.ArrayAssertionUtil;

public class TestSortWindowIterator extends TestCase
{
    private Map<String, EventBean> events;

    public void setUp()
    {
        events = EventFactoryHelper.makeEventMap(new String[] {"a", "b", "c", "d", "f", "g"});
    }

    public void testEmpty()
    {
        SortedMap<Object, LinkedList<EventBean>> testMap = new TreeMap<Object, LinkedList<EventBean>>();
        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, null);
    }

    public void testOneElement()
    {
        SortedMap<Object, LinkedList<EventBean>> testMap = new TreeMap<Object, LinkedList<EventBean>>();
        LinkedList<EventBean> list = new LinkedList<EventBean>();
        list.add(events.get("a"));
        testMap.put("akey", list);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a")} );
    }

    public void testTwoInOneEntryElement()
    {
        SortedMap<Object, LinkedList<EventBean>> testMap = new TreeMap<Object, LinkedList<EventBean>>();
        LinkedList<EventBean> list = new LinkedList<EventBean>();
        list.add(events.get("a"));
        list.add(events.get("b"));
        testMap.put("keyA", list);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("b")} );
    }

    public void testTwoSeparateEntryElement()
    {
        SortedMap<Object, LinkedList<EventBean>> testMap = new TreeMap<Object, LinkedList<EventBean>>();
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        testMap.put("keyB", list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("b"));
        testMap.put("keyA", list2); // Actually before list1

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("b"), events.get("a")} );
    }

    public void testTwoByTwoEntryElement()
    {
        SortedMap<Object, LinkedList<EventBean>> testMap = new TreeMap<Object, LinkedList<EventBean>>();
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        list1.add(events.get("b"));
        testMap.put("keyB", list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        testMap.put("keyC", list2);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("b"), events.get("c"), events.get("d")} );
    }

    public void testMixedEntryElement()
    {
        SortedMap<Object, LinkedList<EventBean>> testMap = new TreeMap<Object, LinkedList<EventBean>>();
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        testMap.put("keyA", list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        testMap.put("keyB", list2);
        LinkedList<EventBean> list3 = new LinkedList<EventBean>();
        list3.add(events.get("e"));
        list3.add(events.get("f"));
        list3.add(events.get("g"));
        testMap.put("keyC", list3);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("c"), events.get("d"),
                events.get("e"), events.get("f"), events.get("g")} );
    }
}


