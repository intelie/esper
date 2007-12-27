package net.esper.collection;

import junit.framework.TestCase;
import java.util.*;
import net.esper.event.EventBean;
import net.esper.support.event.EventFactoryHelper;
import net.esper.support.util.ArrayAssertionUtil;

public class TestTimeWindowIterator extends TestCase
{
    private Map<String, EventBean> events;

    public void setUp()
    {
        events = EventFactoryHelper.makeEventMap(new String[] {"a", "b", "c", "d", "f", "g"});
    }

    public void testEmpty()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();
        Iterator<EventBean> it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, null);
    }

    public void testOneElement()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();
        LinkedList<EventBean> list = new LinkedList<EventBean>();
        list.add(events.get("avalue"));
        addToWindow(testWindow, 10L, list);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("avalue")} );
    }

    public void testTwoInOneEntryElement()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();
        LinkedList<EventBean> list = new LinkedList<EventBean>();
        list.add(events.get("a"));
        list.add(events.get("b"));
        addToWindow(testWindow, 10L, list);

        Iterator<EventBean> it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("b")} );
    }

    public void testTwoSeparateEntryElement()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("b"));
        addToWindow(testWindow, 5L, list2); // Actually before list1
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        addToWindow(testWindow, 10L, list1);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("b"), events.get("a")} );
    }

    public void testTwoByTwoEntryElement()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        list1.add(events.get("b"));
        addToWindow(testWindow, 10L, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        addToWindow(testWindow, 15L, list2);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("b"), events.get("c"), events.get("d")} );
    }

    public void testMixedEntryElement()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        addToWindow(testWindow, 10L, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        addToWindow(testWindow, 15L, list2);
        LinkedList<EventBean> list3 = new LinkedList<EventBean>();
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
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();

        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        addToWindow(testWindow, 10L, list1);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, (Object[]) null);
    }

    public void testTwoEmptyList()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();

        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        addToWindow(testWindow, 10L, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        addToWindow(testWindow, 20L, list2);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, (Object[]) null);
    }

    public void testThreeEmptyList()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();

        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        addToWindow(testWindow, 10L, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        addToWindow(testWindow, 20L, list2);
        LinkedList<EventBean> list3 = new LinkedList<EventBean>();
        addToWindow(testWindow, 30L, list3);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, (Object[]) null);
    }

    public void testEmptyListFrontTail()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();

        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        addToWindow(testWindow, 10L, list1);

        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        addToWindow(testWindow, 15L, list2);

        LinkedList<EventBean> list3 = new LinkedList<EventBean>();
        addToWindow(testWindow, 20L, list3);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("c"), events.get("d")} );
    }

    public void testEmptyListSprinkle()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();

        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        addToWindow(testWindow, 10L, list1);

        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        addToWindow(testWindow, 15L, list2);

        LinkedList<EventBean> list3 = new LinkedList<EventBean>();
        list3.add(events.get("c"));
        list3.add(events.get("d"));
        addToWindow(testWindow, 20L, list3);

        LinkedList<EventBean> list4 = new LinkedList<EventBean>();
        addToWindow(testWindow, 40L, list4);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("c"), events.get("d")} );
    }

    public void testEmptyListFront()
    {
        LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow = new LinkedList<Pair<Long, LinkedList<EventBean>>>();

        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        addToWindow(testWindow, 10L, list1);

        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("a"));
        addToWindow(testWindow, 15L, list2);

        LinkedList<EventBean> list3 = new LinkedList<EventBean>();
        list3.add(events.get("c"));
        list3.add(events.get("d"));
        addToWindow(testWindow, 20L, list3);

        LinkedList<EventBean> list4 = new LinkedList<EventBean>();
        list4.add(events.get("e"));
        addToWindow(testWindow, 40L, list4);

        Iterator it = new TimeWindowIterator(testWindow);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new Object[] {events.get("a"), events.get("c"), events.get("d"), events.get("e")} );
    }

    private void addToWindow(LinkedList<Pair<Long, LinkedList<EventBean>>> testWindow,
                             long key, 
                             LinkedList<EventBean> value)
    {
        testWindow.add(new Pair<Long, LinkedList<EventBean>> (key, value));
    }
}
