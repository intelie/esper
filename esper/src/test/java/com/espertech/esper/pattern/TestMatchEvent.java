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

package com.espertech.esper.pattern;

import junit.framework.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;

import java.util.Map;
import java.util.HashMap;


public class TestMatchEvent extends TestCase
{
    private Map<String, EventBean> events;

    public void setUp()
    {
        events = new HashMap<String, EventBean>();
        String[] ids = { "0", "a", "b", "c", "d", "e", "f" };
        for (String id : ids)
        {
            events.put(id, SupportEventBeanFactory.createObject(id));
        }
    }

    public void testPutAndGet()
    {
        MatchedEventMap event = new MatchedEventMapImpl();
        event.add("tag", events.get("a"));
        event.add("test", events.get("b"));
        assertTrue(event.getMatchingEvents().get("tag") == events.get("a"));
        assertTrue(event.getMatchingEvent("tag") == events.get("a"));
        assertTrue(event.getMatchingEvent("test") == events.get("b"));
    }

    public void testEquals()
    {
        MatchedEventMap eventOne = new MatchedEventMapImpl();
        MatchedEventMap eventTwo = new MatchedEventMapImpl();
        assertTrue(eventOne.equals(eventTwo));

        eventOne.add("test", events.get("a"));
        assertFalse(eventOne.equals(eventTwo));
        assertFalse(eventTwo.equals(eventOne));

        eventTwo.add("test", events.get("a"));
        assertTrue(eventOne.equals(eventTwo));

        eventOne.add("abc", events.get("b"));
        eventTwo.add("abc", events.get("c"));
        assertFalse(eventOne.equals(eventTwo));

        eventOne.add("abc", events.get("c"));
        assertTrue(eventOne.equals(eventTwo));

        eventOne.add("1", events.get("d"));
        eventOne.add("2", events.get("e"));
        eventTwo.add("2", events.get("e"));
        eventTwo.add("1", events.get("d"));
        assertTrue(eventOne.equals(eventTwo));
    }

    public void testClone()
    {
        MatchedEventMap event = new MatchedEventMapImpl();

        event.add("tag", events.get("0"));
        MatchedEventMap copy = event.shallowCopy();

        assertTrue(copy.equals(event));
        event.add("a", events.get("a"));
        assertFalse(copy.equals(event));
        copy.add("a", events.get("a"));
        assertTrue(copy.equals(event));

        MatchedEventMap copyTwo = copy.shallowCopy();
        assertTrue(copy.equals(copyTwo));
        copyTwo.add("b", events.get("b"));

        assertTrue(copyTwo.getMatchingEvents().size() == 3);
        assertTrue(copyTwo.getMatchingEvent("tag") == events.get("0"));
        assertTrue(copyTwo.getMatchingEvent("a") == events.get("a"));
        assertTrue(copyTwo.getMatchingEvent("b") == events.get("b"));

        assertTrue(copy.getMatchingEvents().size() == 2);
        assertTrue(copy.getMatchingEvent("tag") == events.get("0"));
        assertTrue(copy.getMatchingEvent("a") == events.get("a"));

        assertTrue(event.getMatchingEvents().size() == 2);
        assertTrue(event.getMatchingEvent("tag") == events.get("0"));
        assertTrue(event.getMatchingEvent("a") == events.get("a"));
    }

    public void testMerge()
    {
        MatchedEventMap eventOne = new MatchedEventMapImpl();
        MatchedEventMap eventTwo = new MatchedEventMapImpl();

        eventOne.add("tagA", events.get("a"));
        eventOne.add("tagB", events.get("b"));

        eventTwo.add("tagB", events.get("c"));
        eventTwo.add("xyz", events.get("d"));

        eventOne.merge(eventTwo);
        assertTrue(eventOne.getMatchingEvent("tagA") == events.get("a"));
        assertTrue(eventOne.getMatchingEvent("tagB") == events.get("c"));
        assertTrue(eventOne.getMatchingEvent("xyz") == events.get("d"));
        assertTrue(eventOne.getMatchingEvents().size() == 3);
    }
}