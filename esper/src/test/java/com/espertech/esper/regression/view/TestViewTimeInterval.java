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

package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

public class TestViewTimeInterval extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
    }

    public void testTimeWindowPreparedStmt()
    {
        sendTimer(0);
        String text = "select rstream string from SupportBean.win:time(?)";
        EPPreparedStatement prepared = epService.getEPAdministrator().prepareEPL(text);

        prepared.setObject(1, 4);
        EPStatement stmtOne = epService.getEPAdministrator().create(prepared);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        prepared.setObject(1, 3);
        EPStatement stmtTwo = epService.getEPAdministrator().create(prepared);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);

        runAssertion(listenerOne, listenerTwo);
    }

    public void testTimeWindowVariableStmt()
    {
        sendTimer(0);
        String text = "select rstream string from SupportBean.win:time(TIME_WIN)";
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        epService.getEPAdministrator().getConfiguration().addVariable("TIME_WIN", int.class, 4);
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        epService.getEPRuntime().setVariableValue("TIME_WIN", 3);
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);

        runAssertion(listenerOne, listenerTwo);
    }

    public void testTimeWindowTimePeriod()
    {
        sendTimer(0);

        String text = "select rstream string from SupportBean.win:time(4 sec)";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        text = "select rstream string from SupportBean.win:time(3000 milliseconds)";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);

        runAssertion(listenerOne, listenerTwo);
    }

    public void testTimeWindowVariableTimePeriodStmt()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("TIME_WIN", double.class, 4000);
        sendTimer(0);
        
        String text = "select rstream string from SupportBean.win:time(TIME_WIN milliseconds)";
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        text = "select rstream string from SupportBean.win:time(TIME_WIN minutes)";
        epService.getEPRuntime().setVariableValue("TIME_WIN", 0.05);
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);

        runAssertion(listenerOne, listenerTwo);
    }

    public void testTimeWindow()
    {
        tryTimeWindow("30000");
        tryTimeWindow("30E6 milliseconds");
        tryTimeWindow("30000 seconds");
        tryTimeWindow("500 minutes");
        tryTimeWindow("8.33333333333333333333 hours");
        tryTimeWindow("0.34722222222222222222222222222222 days");
        tryTimeWindow("0.1 hour 490 min 240 sec");
    }

    public void testTimeBatchNoRefPoint()
    {
        // Set up a time window with a unique view attached
        EPStatement view = epService.getEPAdministrator().createEPL(
                "select * from " + SupportBean.class.getName() +
                ".win:time_batch(10 minutes)");
        testListener = new SupportUpdateListener();
        view.addListener(testListener);

        sendTimer(0);

        sendEvent();
        testListener.reset();

        sendTimerAssertNotInvoked(10*60*1000 - 1);
        sendTimerAssertInvoked(10*60*1000);
    }

    public void testTimeBatchRefPoint()
    {
        // Set up a time window with a unique view attached
        EPStatement view = epService.getEPAdministrator().createEPL(
                "select * from " + SupportBean.class.getName() +
                ".win:time_batch(10 minutes, 10L)");
        testListener = new SupportUpdateListener();
        view.addListener(testListener);

        sendTimer(10);

        sendEvent();
        testListener.reset();

        sendTimerAssertNotInvoked(10*60*1000 - 1 + 10);
        sendTimerAssertInvoked(10*60*1000 + 10);
    }

    public void testExternallyTimed()
    {
        // Set up a time window with a unique view attached
        EPStatement view = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportBean.class.getName() +
                ".win:ext_timed(longPrimitive, 10 minutes)");
        testListener = new SupportUpdateListener();
        view.addListener(testListener);

        sendExtTimeEvent(0);

        testListener.reset();
        sendExtTimeEvent(10*60*1000-1);
        assertNull(testListener.getOldDataList().get(0));

        testListener.reset();
        sendExtTimeEvent(10*60*1000+1);
        assertEquals(1, testListener.getOldDataList().get(0).length);
    }

    private void tryTimeWindow(String intervalSpec)
    {
        // Set up a time window with a unique view attached
        EPStatement view = epService.getEPAdministrator().createEPL(
                "select irstream * from " + SupportBean.class.getName() +
                ".win:time(" + intervalSpec + ")");
        testListener = new SupportUpdateListener();
        view.addListener(testListener);

        sendTimer(0);

        sendEvent();
        testListener.reset();

        sendTimerAssertNotInvoked(29999*1000);
        sendTimerAssertInvoked(30000*1000);
    }

    private void sendTimerAssertNotInvoked(long timeInMSec)
    {
        sendTimer(timeInMSec);
        assertFalse(testListener.isInvoked());
        testListener.reset();
    }

    private void sendTimerAssertInvoked(long timeInMSec)
    {
        sendTimer(timeInMSec);
        assertTrue(testListener.isInvoked());
        testListener.reset();
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void sendEvent()
    {
        SupportBean event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendEvent(String string)
    {
        SupportBean event = new SupportBean(string, 1);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendExtTimeEvent(long longPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setLongPrimitive(longPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }

    private void runAssertion(SupportUpdateListener listenerOne, SupportUpdateListener listenerTwo)
    {
        sendTimer(1000);
        sendEvent("E1");

        sendTimer(2000);
        sendEvent("E2");

        sendTimer(3000);
        sendEvent("E3");

        assertFalse(listenerOne.isInvoked());
        assertFalse(listenerTwo.isInvoked());

        sendTimer(4000);
        assertEquals("E1", listenerTwo.assertOneGetNewAndReset().get("string"));
        assertFalse(listenerTwo.isInvoked());

        sendTimer(5000);
        assertEquals("E1", listenerOne.assertOneGetNewAndReset().get("string"));
        assertFalse(listenerOne.isInvoked());
    }
}
