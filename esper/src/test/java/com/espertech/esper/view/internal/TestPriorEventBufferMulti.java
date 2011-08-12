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

package com.espertech.esper.view.internal;

import junit.framework.TestCase;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.view.internal.PriorEventBufferMulti;

public class TestPriorEventBufferMulti extends TestCase
{
    private PriorEventBufferMulti buffer;
    private EventBean[] events;

    public void setUp()
    {
        int[] indexes = new int[] {1, 3};
        buffer = new PriorEventBufferMulti(indexes);

        events = new EventBean[100];
        for (int i = 0; i < events.length; i++)
        {
            SupportBean_S0 bean = new SupportBean_S0(i);
            events[i] = SupportEventBeanFactory.createObject(bean);
        }
    }

    public void testFlow()
    {
        buffer.update(new EventBean[] {events[0], events[1]}, null);
        assertEvents0And1();

        buffer.update(new EventBean[] {events[2]}, null);
        assertEvents0And1();
        assertEvents2();

        buffer.update(new EventBean[] {events[3], events[4]}, null);
        assertEvents0And1();
        assertEvents2();
        assertEvents3And4();

        buffer.update(null, new EventBean[] {events[0]});
        assertEvents0And1();
        assertEvents2();
        assertEvents3And4();

        buffer.update(null, new EventBean[] {events[1], events[3]});
        tryInvalid(events[0], 0);
        tryInvalid(events[0], 1);
        assertEquals(events[0], buffer.getRelativeToEvent(events[1], 0));
        assertNull(buffer.getRelativeToEvent(events[1], 1));
        assertEvents2();
        assertEvents3And4();

        buffer.update(new EventBean[] {events[5]}, null);
        tryInvalid(events[0], 0);
        tryInvalid(events[1], 0);
        tryInvalid(events[3], 0);
        assertEvents2();
        assertEquals(events[3], buffer.getRelativeToEvent(events[4], 0));
        assertEquals(events[1], buffer.getRelativeToEvent(events[4], 1));
        assertEquals(events[4], buffer.getRelativeToEvent(events[5], 0));
        assertEquals(events[2], buffer.getRelativeToEvent(events[5], 1));
    }

    private void assertEvents0And1()
    {
        assertNull(buffer.getRelativeToEvent(events[0], 0));     // getting 0 is getting prior 1 (see indexes)
        assertNull(buffer.getRelativeToEvent(events[0], 1));     // getting 1 is getting prior 3 (see indexes)
        assertEquals(events[0], buffer.getRelativeToEvent(events[1], 0));
        assertNull(buffer.getRelativeToEvent(events[1], 1));
    }

    private void assertEvents2()
    {
        assertEquals(events[1], buffer.getRelativeToEvent(events[2], 0));
        assertNull(buffer.getRelativeToEvent(events[2], 1));
    }

    private void assertEvents3And4()
    {
        assertEquals(events[2], buffer.getRelativeToEvent(events[3], 0));
        assertEquals(events[0], buffer.getRelativeToEvent(events[3], 1));
        assertEquals(events[3], buffer.getRelativeToEvent(events[4], 0));
        assertEquals(events[1], buffer.getRelativeToEvent(events[4], 1));
    }

    public void tryInvalid(EventBean event, int index)
    {
        try
        {
            buffer.getRelativeToEvent(event, index);
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
    }

    public void testInvalid()
    {
        try
        {
            buffer.getRelativeToEvent(events[1], 2);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }
}
