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
import com.espertech.esper.view.internal.PriorEventBufferUnbound;

public class TestPriorEventBufferUnbound extends TestCase
{
    private PriorEventBufferUnbound buffer;
    private EventBean[] events;

    public void setUp()
    {
        buffer = new PriorEventBufferUnbound(3);

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
        assertEquals(events[1], buffer.getNewData(0));
        assertEquals(events[0], buffer.getNewData(1));
        assertNull(buffer.getNewData(2));
    }

    public void testInvalid()
    {
        try
        {
            buffer.getNewData(6);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }
}
