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
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.*;

public class TestOutputLimitFirstHaving extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getLogging().setEnableExecutionDebug(true);
        config.getEngineDefaults().getLogging().setEnableTimerDebug(false);
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_ST0", SupportBean_ST0.class);
        listener = new SupportUpdateListener();
    }

    public void testHavingNoAvgOutputFirstEvents() {
        String query = "select doublePrimitive from SupportBean having doublePrimitive > 1 output first every 2 events";
        EPStatement statement = epService.getEPAdministrator().createEPL(query);
        statement.addListener(listener);
        runAssertion2Events();
        statement.destroy();

        // test joined
        query = "select doublePrimitive from SupportBean.std:lastevent(),SupportBean_ST0.std:lastevent() st0 having doublePrimitive > 1 output first every 2 events";
        statement = epService.getEPAdministrator().createEPL(query);
        epService.getEPRuntime().sendEvent(new SupportBean_ST0("ID", 1));
        statement.addListener(listener);
        runAssertion2Events();
    }

    public void testHavingNoAvgOutputFirstMinutes() {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        String[] fields = "val0".split(",");
        String query = "select sum(doublePrimitive) as val0 from SupportBean.win:length(5) having sum(doublePrimitive) > 100 output first every 2 seconds";
        EPStatement statement = epService.getEPAdministrator().createEPL(query);
        statement.addListener(listener);

        sendBeanEvent(10);
        sendBeanEvent(80);
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1000));
        sendBeanEvent(11);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {101d});

        sendBeanEvent(1);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(2999));
        sendBeanEvent(1);
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(3000));
        sendBeanEvent(1);
        assertFalse(listener.isInvoked());

        sendBeanEvent(100);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {114d});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(4999));
        sendBeanEvent(0);
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(5000));
        sendBeanEvent(0);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {102d});
    }

    public void testHavingAvgOutputFirstEveryTwoMinutes()
    {
        String query = "select doublePrimitive, avg(doublePrimitive) from SupportBean having doublePrimitive > 2*avg(doublePrimitive) output first every 2 minutes";
        EPStatement statement = epService.getEPAdministrator().createEPL(query);
        statement.addListener(listener);

        sendBeanEvent(1);
        assertFalse(listener.isInvoked());

        sendBeanEvent(2);
        assertFalse(listener.isInvoked());
    
        sendBeanEvent(9);
        assertTrue(listener.isInvoked());
     }


    private void runAssertion2Events() {

        sendBeanEvent(1);
        assertFalse(listener.getAndClearIsInvoked());

        sendBeanEvent(2);
        assertTrue(listener.getAndClearIsInvoked());

        sendBeanEvent(9);
        assertFalse(listener.getAndClearIsInvoked());

        sendBeanEvent(1);
        assertFalse(listener.getAndClearIsInvoked());

        sendBeanEvent(1);
        assertFalse(listener.getAndClearIsInvoked());

        sendBeanEvent(2);
        assertTrue(listener.getAndClearIsInvoked());

        sendBeanEvent(1);
        assertFalse(listener.getAndClearIsInvoked());

        sendBeanEvent(2);
        assertTrue(listener.getAndClearIsInvoked());

        sendBeanEvent(2);
        assertFalse(listener.getAndClearIsInvoked());

        sendBeanEvent(2);
        assertTrue(listener.getAndClearIsInvoked());
    }

    private void sendBeanEvent(double doublePrimitive) {
        SupportBean b = new SupportBean();
        b.setDoublePrimitive(doublePrimitive);
        epService.getEPRuntime().sendEvent(b);
    }
}

