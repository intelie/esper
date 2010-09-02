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
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();
        Iterator<EventBean> it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, null);
    }

    public void testOneElement()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();
        ArrayDeque<EventBean> list = new ArrayDeque<EventBean>();
        list.add(events.get("a"));
        addToWindow(testWindow, 10L, list);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a")} );
    }

    public void testTwoInOneEntryElement()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();
        ArrayDeque<EventBean> list = new ArrayDeque<EventBean>();
        list.add(events.get("a"));
        list.add(events.get("b"));
        addToWindow(testWindow, 10L, list);

        Iterator<EventBean> it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("b")} );
    }

    public void testTwoSeparateEntryElement()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();
        ArrayDeque<EventBean> list2 = new ArrayDeque<EventBean>();
        list2.add(events.get("b"));
        addToWindow(testWindow, 5L, list2); // Actually before list1
        ArrayDeque<EventBean> list1 = new ArrayDeque<EventBean>();
        list1.add(events.get("a"));
        addToWindow(testWindow, 10L, list1);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("b"), events.get("a")} );
    }

    public void testTwoByTwoEntryElement()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();
        ArrayDeque<EventBean> list1 = new ArrayDeque<EventBean>();
        list1.add(events.get("a"));
        list1.add(events.get("b"));
        addToWindow(testWindow, 10L, list1);
        ArrayDeque<EventBean> list2 = new ArrayDeque<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        addToWindow(testWindow, 15L, list2);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("b"), events.get("c"), events.get("d")} );
    }

    public void testMixedEntryElement()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();
        ArrayDeque<EventBean> list1 = new ArrayDeque<EventBean>();
        list1.add(events.get("a"));
        addToWindow(testWindow, 10L, list1);
        ArrayDeque<EventBean> list2 = new ArrayDeque<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        addToWindow(testWindow, 15L, list2);
        ArrayDeque<EventBean> list3 = new ArrayDeque<EventBean>();
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
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();

        ArrayDeque<EventBean> list1 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 10L, list1);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, (Object[]) null);
    }

    public void testTwoEmptyList()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();

        ArrayDeque<EventBean> list1 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 10L, list1);
        ArrayDeque<EventBean> list2 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 20L, list2);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, (Object[]) null);
    }

    public void testThreeEmptyList()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();

        ArrayDeque<EventBean> list1 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 10L, list1);
        ArrayDeque<EventBean> list2 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 20L, list2);
        ArrayDeque<EventBean> list3 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 30L, list3);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, (Object[]) null);
    }

    public void testEmptyListFrontTail()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();

        ArrayDeque<EventBean> list1 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 10L, list1);

        ArrayDeque<EventBean> list2 = new ArrayDeque<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        addToWindow(testWindow, 15L, list2);

        ArrayDeque<EventBean> list3 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 20L, list3);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("c"), events.get("d")} );
    }

    public void testEmptyListSprinkle()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();

        ArrayDeque<EventBean> list1 = new ArrayDeque<EventBean>();
        list1.add(events.get("a"));
        addToWindow(testWindow, 10L, list1);

        ArrayDeque<EventBean> list2 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 15L, list2);

        ArrayDeque<EventBean> list3 = new ArrayDeque<EventBean>();
        list3.add(events.get("c"));
        list3.add(events.get("d"));
        addToWindow(testWindow, 20L, list3);

        ArrayDeque<EventBean> list4 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 40L, list4);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("c"), events.get("d")} );
    }

    public void testEmptyListFront()
    {
        ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow = new ArrayDeque<Pair<Long, ArrayDeque<EventBean>>>();

        ArrayDeque<EventBean> list1 = new ArrayDeque<EventBean>();
        addToWindow(testWindow, 10L, list1);

        ArrayDeque<EventBean> list2 = new ArrayDeque<EventBean>();
        list2.add(events.get("a"));
        addToWindow(testWindow, 15L, list2);

        ArrayDeque<EventBean> list3 = new ArrayDeque<EventBean>();
        list3.add(events.get("c"));
        list3.add(events.get("d"));
        addToWindow(testWindow, 20L, list3);

        ArrayDeque<EventBean> list4 = new ArrayDeque<EventBean>();
        list4.add(events.get("e"));
        addToWindow(testWindow, 40L, list4);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("c"), events.get("d"), events.get("e")} );
    }

    private void addToWindow(ArrayDeque<Pair<Long, ArrayDeque<EventBean>>> testWindow,
                             long key, 
                             ArrayDeque<EventBean> value)
    {
        testWindow.add(new Pair<Long, ArrayDeque<EventBean>> (key, value));
    }
}
