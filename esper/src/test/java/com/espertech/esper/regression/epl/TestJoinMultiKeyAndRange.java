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

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportBeanRange;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestJoinMultiKeyAndRange extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    private final int[][] eventData = {{1, 100},
                                       {2, 100},
                                       {1, 200},
                                       {2, 200}};
    private SupportBean eventsA[] = new SupportBean[eventData.length];
    private SupportBean eventsB[] = new SupportBean[eventData.length];

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testRangeNullAndDupAndInvalid() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanRange", SupportBeanRange.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanComplexProps", SupportBeanComplexProps.class);

        String eplOne = "select sb.* from SupportBean.win:keepall() sb, SupportBeanRange.std:lastevent() where intBoxed between rangeStart and rangeEnd";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(eplOne);
        stmtOne.addListener(listener);

        String eplTwo = "select sb.* from SupportBean.win:keepall() sb, SupportBeanRange.std:lastevent() where string = key and intBoxed in [rangeStart: rangeEnd]";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(eplTwo);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);

        // null join lookups
        sendEvent(new SupportBeanRange("R1", "G", (Integer) null, null));
        sendEvent(new SupportBeanRange("R2", "G", null, 10));
        sendEvent(new SupportBeanRange("R3", "G", 10, null));
        sendSupportBean("G", -1, null);

        // range invalid
        sendEvent(new SupportBeanRange("R4", "G", 10, 0));
        assertFalse(listener.isInvoked());
        assertFalse(listenerTwo.isInvoked());

        // duplicates
        Object eventOne = sendSupportBean("G", 100, 5);
        Object eventTwo = sendSupportBean("G", 101, 5);
        sendEvent(new SupportBeanRange("R4", "G", 0, 10));
        EventBean[] events = listener.getAndResetLastNewData();
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {eventOne, eventTwo}, ArrayAssertionUtil.getUnderlying(events));
        events = listenerTwo.getAndResetLastNewData();
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {eventOne, eventTwo}, ArrayAssertionUtil.getUnderlying(events));

        // test string compare
        String eplThree = "select sb.* from SupportBeanRange.win:keepall() sb, SupportBean.std:lastevent() where string in [rangeStartStr:rangeEndStr]";
        epService.getEPAdministrator().createEPL(eplThree);

        sendSupportBean("P", 1, 1);
        sendEvent(new SupportBeanRange("R5", "R5", "O", "Q"));
        assertTrue(listener.isInvoked());

    }

    public void testMultiKeyed() {

        String eventClass = SupportBean.class.getName();

        String joinStatement = "select * from " +
            eventClass + "(string='A').win:length(3) as streamA," +
            eventClass + "(string='B').win:length(3) as streamB" +
            " where streamA.intPrimitive = streamB.intPrimitive " +
               "and streamA.intBoxed = streamB.intBoxed";

        EPStatement stmt = epService.getEPAdministrator().createEPL(joinStatement);
        stmt.addListener(listener);

        assertEquals(SupportBean.class, stmt.getEventType().getPropertyType("streamA"));
        assertEquals(SupportBean.class, stmt.getEventType().getPropertyType("streamB"));
        assertEquals(2, stmt.getEventType().getPropertyNames().length);

        for (int i = 0; i < eventData.length; i++)
        {
            eventsA[i] = new SupportBean();
            eventsA[i].setString("A");
            eventsA[i].setIntPrimitive(eventData[i][0]);
            eventsA[i].setIntBoxed(eventData[i][1]);

            eventsB[i] = new SupportBean();
            eventsB[i].setString("B");
            eventsB[i].setIntPrimitive(eventData[i][0]);
            eventsB[i].setIntBoxed(eventData[i][1]);
        }

        sendEvent(eventsA[0]);
        sendEvent(eventsB[1]);
        sendEvent(eventsB[2]);
        sendEvent(eventsB[3]);
        assertNull(listener.getLastNewData());    // No events expected
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }

    private SupportBean sendSupportBean(String string, int intPrimitive, Integer intBoxed) {
        SupportBean bean = new SupportBean(string, intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
