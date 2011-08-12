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

package com.espertech.esper.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.view.SupportSchemaNeutralView;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.event.SupportEventBeanFactory;

public class TestZeroDepthStream extends TestCase
{
    private ZeroDepthStream stream;
    private SupportSchemaNeutralView testChildView;
    private EventType eventType;

    private EventBean eventBean;

    public void setUp()
    {
        eventType = SupportEventTypeFactory.createBeanType(SupportBean_A.class);

        stream = new ZeroDepthStream(eventType);

        testChildView = new SupportSchemaNeutralView();
        stream.addView(testChildView);
        testChildView.setParent(stream);

        eventBean = SupportEventBeanFactory.createObject(new SupportBean_A("a1"));
    }

    public void testInsert()
    {
        testChildView.clearLastNewData();
        stream.insert(eventBean);

        assertTrue(testChildView.getLastNewData() != null);
        assertEquals(1, testChildView.getLastNewData().length);
        assertEquals(eventBean, testChildView.getLastNewData()[0]);

        // Remove view
        testChildView.clearLastNewData();
        stream.removeView(testChildView);
        stream.insert(eventBean);
        assertTrue(testChildView.getLastNewData() == null);
    }
}
