/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.view.ext;

import junit.framework.TestCase;
import java.util.*;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.event.EventFactoryHelper;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.util.MultiKeyComparator;

public class TestSortWindowIterator extends TestCase
{
    private Map<String, EventBean> events;
	private SortedMap<MultiKeyUntyped, LinkedList<EventBean>> testMap;
	private Comparator<MultiKeyUntyped> comparator;
	
    public void setUp()
    {
        events = EventFactoryHelper.makeEventMap(new String[] {"a", "b", "c", "d", "f", "g"});
        comparator = new MultiKeyComparator(new boolean[] {false});
        testMap = new TreeMap<MultiKeyUntyped, LinkedList<EventBean>>(comparator);
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
        MultiKeyUntyped key = new MultiKeyUntyped(new Object[] {"akey"});
        testMap.put(key, list);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a")} );
    }

    public void testTwoInOneEntryElement()
    {
        LinkedList<EventBean> list = new LinkedList<EventBean>();
        list.add(events.get("a"));
        list.add(events.get("b"));
        MultiKeyUntyped key = new MultiKeyUntyped(new Object[] {"keyA"});
        testMap.put(key, list);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("b")} );
    }

    public void testTwoSeparateEntryElement()
    {
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        MultiKeyUntyped keyB = new MultiKeyUntyped(new Object[] {"keyB"});
        testMap.put(keyB, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("b"));
        MultiKeyUntyped keyA = new MultiKeyUntyped(new Object[] {"keyA"});
        testMap.put(keyA, list2); // Actually before list1

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("b"), events.get("a")} );
    }

    public void testTwoByTwoEntryElement()
    {
        LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        list1.add(events.get("b"));
        MultiKeyUntyped keyB = new MultiKeyUntyped(new Object[] {"keyB"});
        testMap.put(keyB, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        MultiKeyUntyped keyC = new MultiKeyUntyped(new Object[] {"keyC"});
        testMap.put(keyC, list2);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("b"), events.get("c"), events.get("d")} );
    }

    public void testMixedEntryElement()
    {
		LinkedList<EventBean> list1 = new LinkedList<EventBean>();
        list1.add(events.get("a"));
        MultiKeyUntyped keyA = new MultiKeyUntyped(new Object[] {"keyA"});
        testMap.put(keyA, list1);
        LinkedList<EventBean> list2 = new LinkedList<EventBean>();
        list2.add(events.get("c"));
        list2.add(events.get("d"));
        MultiKeyUntyped keyB = new MultiKeyUntyped(new Object[] {"keyB"});
        testMap.put(keyB, list2);
        LinkedList<EventBean> list3 = new LinkedList<EventBean>();
        list3.add(events.get("e"));
        list3.add(events.get("f"));
        list3.add(events.get("g"));
        MultiKeyUntyped keyC = new MultiKeyUntyped(new Object[] {"keyC"});
        testMap.put(keyC, list3);

        Iterator<EventBean> it = new SortWindowIterator(testMap);
        ArrayAssertionUtil.assertEqualsExactOrder(it, new EventBean[] {events.get("a"), events.get("c"), events.get("d"),
                events.get("e"), events.get("f"), events.get("g")} );
    }
}


