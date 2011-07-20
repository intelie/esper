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

package com.espertech.esper.collection;

import junit.framework.TestCase;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.event.EventFactoryHelper;
import com.espertech.esper.client.EventBean;

public class TestIterablesListIterator extends TestCase
{
    private Map<String, EventBean> events;

    public void setUp()
    {
        events = EventFactoryHelper.makeEventMap(new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "z"});
    }

    public void testIterator()
    {
        List<Iterable<EventBean>> iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "a", "b", "c" } ));
        checkResults(iterables, EventFactoryHelper.makeArray(events, new String[] { "a", "b", "c" }));

        iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "a" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "b" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "c" } ));
        checkResults(iterables, EventFactoryHelper.makeArray(events, new String[] { "a", "b", "c" }));

        iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "a", "b" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "c" } ));
        checkResults(iterables, EventFactoryHelper.makeArray(events, new String[] { "a", "b", "c" }));

        iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "a", "b" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "c" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        checkResults(iterables, EventFactoryHelper.makeArray(events, new String[] { "a", "b", "c" }));

        iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        checkResults(iterables, null);

        iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        checkResults(iterables, null);

        iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "d" } ));
        checkResults(iterables, EventFactoryHelper.makeArray(events, new String[] { "d" } ));

        iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "d" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        checkResults(iterables, EventFactoryHelper.makeArray(events, new String[] { "d" } ));

        iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "a", "b", "c" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "d" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "e", "f" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "g" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "h", "i" } ));
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "z" } ));
        checkResults(iterables, EventFactoryHelper.makeArray(events, new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "z" }));

        iterables = new LinkedList<Iterable<EventBean>>();
        checkResults(iterables, null);
    }

    public void testRemove()
    {
        List<Iterable<EventBean>> iterables = new LinkedList<Iterable<EventBean>>();
        iterables.add(EventFactoryHelper.makeList(events, new String[] { "a", "b", "c" } ));
        IterablesListIterator iterator = new IterablesListIterator(iterables);

        try
        {
            iterator.remove();
            assertTrue(false);
        }
        catch (UnsupportedOperationException ex)
        {
            // Expected
        }
    }

    private void checkResults(List<Iterable<EventBean>> iterables, EventBean[] expectedValues)
    {
        IterablesListIterator iterator = new IterablesListIterator(iterables);
        ArrayAssertionUtil.assertEqualsExactOrder(iterator, expectedValues);
    }
}
