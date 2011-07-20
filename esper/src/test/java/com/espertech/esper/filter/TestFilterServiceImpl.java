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

package com.espertech.esper.filter;

import com.espertech.esper.support.filter.SupportFilterSpecBuilder;
import com.espertech.esper.support.filter.SupportFilterHandle;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanSimple;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;

import java.util.Collection;
import java.util.Vector;
import java.util.List;
import java.util.LinkedList;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestFilterServiceImpl extends TestCase
{
    private EventType eventTypeOne;
    private EventType eventTypeTwo;
    private FilterServiceImpl filterService;
    private Vector<FilterValueSet> filterSpecs;
    private Vector<SupportFilterHandle> filterCallbacks;
    private Vector<EventBean> events;
    private Vector<int[]> matchesExpected;

    public void setUp()
    {
        filterService = new FilterServiceImpl();

        eventTypeOne = SupportEventTypeFactory.createBeanType(SupportBean.class);
        eventTypeTwo = SupportEventTypeFactory.createBeanType(SupportBeanSimple.class);

        filterSpecs = new Vector<FilterValueSet>();
        filterSpecs.add(SupportFilterSpecBuilder.build(eventTypeOne, new Object[0]).getValueSet(null));
        filterSpecs.add(SupportFilterSpecBuilder.build(eventTypeOne, new Object[] {
                "intPrimitive", FilterOperator.RANGE_CLOSED, 10, 20,
                "string", FilterOperator.EQUAL, "HELLO",
                "boolPrimitive", FilterOperator.EQUAL, false,
                "doubleBoxed", FilterOperator.GREATER, 100d} ).getValueSet(null));
        filterSpecs.add(SupportFilterSpecBuilder.build(eventTypeTwo, new Object[0]).getValueSet(null));
        filterSpecs.add(SupportFilterSpecBuilder.build(eventTypeTwo, new Object[] {
                "myInt", FilterOperator.RANGE_HALF_CLOSED, 1, 10,
                "myString", FilterOperator.EQUAL, "Hello" }).getValueSet(null));

        // Create callbacks and add
        filterCallbacks = new Vector<SupportFilterHandle>();
        for (int i = 0; i < filterSpecs.size(); i++)
        {
            filterCallbacks.add(new SupportFilterHandle());
            filterService.add(filterSpecs.get(i), filterCallbacks.get(i));
        }

        // Create events
        matchesExpected = new Vector<int[]>();
        events = new Vector<EventBean>();

        events.add(makeTypeOneEvent(15, "HELLO", false, 101));
        matchesExpected.add(new int[] {1, 1, 0, 0});

        events.add(makeTypeTwoEvent("Hello", 100));
        matchesExpected.add(new int[] {0, 0, 1, 0});

        events.add(makeTypeTwoEvent("Hello", 1));       // eventNumber = 2
        matchesExpected.add(new int[] {0, 0, 1, 0});

        events.add(makeTypeTwoEvent("Hello", 2));
        matchesExpected.add(new int[] {0, 0, 1, 1});

        events.add(makeTypeOneEvent(15, "HELLO", true, 100));
        matchesExpected.add(new int[] {1, 0, 0, 0});

        events.add(makeTypeOneEvent(15, "HELLO", false, 99));
        matchesExpected.add(new int[] {1, 0, 0, 0});

        events.add(makeTypeOneEvent(9, "HELLO", false, 100));
        matchesExpected.add(new int[] {1, 0, 0, 0});

        events.add(makeTypeOneEvent(10, "no", false, 100));
        matchesExpected.add(new int[] {1, 0, 0, 0});

        events.add(makeTypeOneEvent(15, "HELLO", false, 999999));      // number 8
        matchesExpected.add(new int[] {1, 1, 0, 0});

        events.add(makeTypeTwoEvent("Hello", 10));
        matchesExpected.add(new int[] {0, 0, 1, 1});

        events.add(makeTypeTwoEvent("Hello", 11));
        matchesExpected.add(new int[] {0, 0, 1, 0});
    }

    public void testEvalEvents()
    {
        for (int i = 0; i < events.size(); i++)
        {
            List<FilterHandle> matchList = new LinkedList<FilterHandle>();
            filterService.evaluate(events.get(i), matchList, null);
            for (FilterHandle match : matchList)
            {
                SupportFilterHandle handle = (SupportFilterHandle) match;
                handle.matchFound(events.get(i), null);
            }

            int[] matches = matchesExpected.get(i);

            for (int j = 0; j < matches.length; j++)
            {
                SupportFilterHandle callback = filterCallbacks.get(j);

                if (matches[j] != callback.getAndResetCountInvoked())
                {
                    log.debug(".testEvalEvents Match failed, event=" + events.get(i).getUnderlying());
                    log.debug(".testEvalEvents Match failed, eventNumber=" + i + " index=" + j);
                    assertTrue(false);
                }
            }
        }
    }

    public void testInvalidType()
    {
        try
        {
            FilterValueSet spec = SupportFilterSpecBuilder.build(eventTypeTwo, new Object[] {
                "myString", FilterOperator.GREATER, 2 }).getValueSet(null);
            filterService.add(spec, new SupportFilterHandle());
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    public void testReusedCallback()
    {
        try
        {
            filterService.add(filterSpecs.get(0), filterCallbacks.get(0));
            assertTrue(false);
        }
        catch (IllegalStateException ex)
        {
            // Expected exception
        }
    }

    /**
     * Test for removing a callback that is waiting to occur,
     * ie. a callback is removed which was a result of an evaluation and it
     * thus needs to be removed from the tree AND the current dispatch list.
     */
    public void testActiveCallbackRemove()
    {
        FilterValueSet spec = SupportFilterSpecBuilder.build(eventTypeOne, new Object[0]).getValueSet(null);
        final SupportFilterHandle callbackTwo = new SupportFilterHandle();

        // callback that removes another matching filter spec callback
        FilterHandleCallback callbackOne = new FilterHandleCallback()
        {
            public String getStatementId()
            {
                return "";
            }

            public void matchFound(EventBean event, Collection<FilterHandleCallback> allStmtMatches)
            {
                log.debug(".matchFound Removing callbackTwo");
                filterService.remove(callbackTwo);
            }

            public boolean isSubSelect()
            {
                return false;
            }
        };

        filterService.add(spec, callbackOne);
        filterService.add(spec, callbackTwo);

        // send event
        EventBean event = makeTypeOneEvent(1, "HELLO", false, 1);
        List<FilterHandle> matches = new LinkedList<FilterHandle>();
        filterService.evaluate(event, matches, null);
        for (FilterHandle match : matches)
        {
            FilterHandleCallback handle = (FilterHandleCallback) match;
            handle.matchFound(event, null);
        }

        // Callback two MUST be invoked, was removed by callback one, but since the
        // callback invocation order should not matter, the second one MUST also execute
        assertEquals(1, callbackTwo.getAndResetCountInvoked());
    }

    private EventBean makeTypeOneEvent(int intPrimitive, String string, boolean boolPrimitive, double doubleBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setString(string);
        bean.setBoolPrimitive(boolPrimitive);
        bean.setDoubleBoxed(doubleBoxed);
        return SupportEventBeanFactory.createObject(bean);
    }

    private EventBean makeTypeTwoEvent(String myString, int myInt)
    {
        SupportBeanSimple bean = new SupportBeanSimple(myString, myInt);
        return SupportEventBeanFactory.createObject(bean);
    }

    private static final Log log = LogFactory.getLog(TestFilterServiceImpl.class);
}
