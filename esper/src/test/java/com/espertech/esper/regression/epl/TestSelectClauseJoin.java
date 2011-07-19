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

package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;

import java.util.Iterator;

public class TestSelectClauseJoin extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String eventA = SupportBean.class.getName();
        String eventB = SupportBean.class.getName();

        String joinStatement = "select s0.doubleBoxed, s1.intPrimitive*s1.intBoxed/2.0 as div from " +
            eventA + "(string='s0').win:length(3) as s0," +
            eventB + "(string='s1').win:length(3) as s1" +
            " where s0.doubleBoxed = s1.doubleBoxed";

        joinView = epService.getEPAdministrator().createEPL(joinStatement);
        joinView.addListener(updateListener);
    }

    public void testJoinSelect()
    {
        assertNull(updateListener.getLastNewData());

        sendEvent("s0", 1, 4, 5);
        sendEvent("s1", 1, 3, 2);

        EventBean[] newEvents = updateListener.getLastNewData();
        assertEquals(1d, newEvents[0].get("s0.doubleBoxed"));
        assertEquals(3d, newEvents[0].get("div"));

        Iterator<EventBean> iterator = joinView.iterator();
        EventBean event = iterator.next();
        assertEquals(1d, event.get("s0.doubleBoxed"));
        assertEquals(3d, event.get("div"));
    }

    public void testEventType()
    {
        EventType result = joinView.getEventType();
        assertEquals(Double.class, result.getPropertyType("s0.doubleBoxed"));
        assertEquals(Double.class, result.getPropertyType("div"));
        assertEquals(2, joinView.getEventType().getPropertyNames().length);
    }

    private void sendEvent(String s, double doubleBoxed, int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(s);
        bean.setDoubleBoxed(doubleBoxed);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }
}
