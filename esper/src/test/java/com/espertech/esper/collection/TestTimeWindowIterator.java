package com.espertech.esper.collection;

import junit.framework.TestCase;
import java.util.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.event.EventFactoryHelper;
import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestTimeWindowIterator extends TestCase
{
    private Map<String, EventBean> events;

    public void setUp()
    {
        events = EventFactoryHelper.makeEventMap(new String[] {"a", "b", "c", "d", "e", "f", "g"});
    }

    public void testEmpty()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();
        Iterator<EventBean> it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, null);
    }

    public void testOneElement()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();
        ArrayDequeJDK6Backport<EventBean> list = new ArrayDequeJDK6Backport<EventBean>();
        list.add(events.get("a"));
        addToWindow(testWindow, 10L, list);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a")} );
    }

    public void testTwoInOneEntryElement()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();
        ArrayDequeJDK6Backport<EventBean> list = new ArrayDequeJDK6Backport<EventBean>();
        list.add(events.get("a"));
        list.add(events.get("b"));
        addToWindow(testWindow, 10L, list);

        Iterator<EventBean> it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("b")} );
    }

    public void testTwoSeparateEntryElement()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();
        ArrayDequeJDK6Backport<EventBean> list2 = new ArrayDequeJDK6Backport<EventBean>();
        list2.add(events.get("b"));
        addToWindow(testWindow, 5L, list2); // Actually before list1
        ArrayDequeJDK6Backport<EventBean> list1 = new ArrayDequeJDK6Backport<EventBean>();
        list1.add(events.get("a"));
        addToWindow(testWindow, 10L, list1);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("b"), events.get("a")} );
    }

    public void testTwoByTwoEntryElement()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();
        ArrayDequeJDK6Backport<EventBean> list1 = new ArrayDequeJDK6Backport<EventBean>();
        list1.add(events.get("a"));
        list1.add(events.get("b"));
        addToWindow(testWindow, 10L, list1);
        ArrayDequeJDK6Backport<EventBean> list2 = new ArrayDequeJDK6Backport<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        addToWindow(testWindow, 15L, list2);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("b"), events.get("c"), events.get("d")} );
    }

    public void testMixedEntryElement()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();
        ArrayDequeJDK6Backport<EventBean> list1 = new ArrayDequeJDK6Backport<EventBean>();
        list1.add(events.get("a"));
        addToWindow(testWindow, 10L, list1);
        ArrayDequeJDK6Backport<EventBean> list2 = new ArrayDequeJDK6Backport<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        addToWindow(testWindow, 15L, list2);
        ArrayDequeJDK6Backport<EventBean> list3 = new ArrayDequeJDK6Backport<EventBean>();
        list3.add(events.get("e"));
        list3.add(events.get("f"));
        list3.add(events.get("g"));
        addToWindow(testWindow, 20L, list3);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("c"), events.get("d"),
            events.get("e"), events.get("f"), events.get("g")} );
    }
    
    public void testEmptyList()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();

        ArrayDequeJDK6Backport<EventBean> list1 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 10L, list1);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, (Object[]) null);
    }

    public void testTwoEmptyList()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();

        ArrayDequeJDK6Backport<EventBean> list1 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 10L, list1);
        ArrayDequeJDK6Backport<EventBean> list2 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 20L, list2);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, (Object[]) null);
    }

    public void testThreeEmptyList()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();

        ArrayDequeJDK6Backport<EventBean> list1 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 10L, list1);
        ArrayDequeJDK6Backport<EventBean> list2 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 20L, list2);
        ArrayDequeJDK6Backport<EventBean> list3 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 30L, list3);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, (Object[]) null);
    }

    public void testEmptyListFrontTail()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();

        ArrayDequeJDK6Backport<EventBean> list1 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 10L, list1);

        ArrayDequeJDK6Backport<EventBean> list2 = new ArrayDequeJDK6Backport<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        addToWindow(testWindow, 15L, list2);

        ArrayDequeJDK6Backport<EventBean> list3 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 20L, list3);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("c"), events.get("d")} );
    }

    public void testEmptyListSprinkle()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();

        ArrayDequeJDK6Backport<EventBean> list1 = new ArrayDequeJDK6Backport<EventBean>();
        list1.add(events.get("a"));
        addToWindow(testWindow, 10L, list1);

        ArrayDequeJDK6Backport<EventBean> list2 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 15L, list2);

        ArrayDequeJDK6Backport<EventBean> list3 = new ArrayDequeJDK6Backport<EventBean>();
        list3.add(events.get("c"));
        list3.add(events.get("d"));
        addToWindow(testWindow, 20L, list3);

        ArrayDequeJDK6Backport<EventBean> list4 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 40L, list4);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("c"), events.get("d")} );
    }

    public void testEmptyListFront()
    {
        ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow = new ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>>();

        ArrayDequeJDK6Backport<EventBean> list1 = new ArrayDequeJDK6Backport<EventBean>();
        addToWindow(testWindow, 10L, list1);

        ArrayDequeJDK6Backport<EventBean> list2 = new ArrayDequeJDK6Backport<EventBean>();
        list2.add(events.get("a"));
        addToWindow(testWindow, 15L, list2);

        ArrayDequeJDK6Backport<EventBean> list3 = new ArrayDequeJDK6Backport<EventBean>();
        list3.add(events.get("c"));
        list3.add(events.get("d"));
        addToWindow(testWindow, 20L, list3);

        ArrayDequeJDK6Backport<EventBean> list4 = new ArrayDequeJDK6Backport<EventBean>();
        list4.add(events.get("e"));
        addToWindow(testWindow, 40L, list4);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("c"), events.get("d"), events.get("e")} );
    }

    private void addToWindow(ArrayDequeJDK6Backport<Pair<Long, ArrayDequeJDK6Backport<EventBean>>> testWindow,
                             long key, 
                             ArrayDequeJDK6Backport<EventBean> value)
    {
        testWindow.add(new Pair<Long, ArrayDequeJDK6Backport<EventBean>> (key, value));
    }
}
