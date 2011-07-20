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

package com.espertech.esper.regression.pattern;

import junit.framework.TestCase;
import com.espertech.esper.regression.support.CaseList;
import com.espertech.esper.regression.support.EventCollection;
import com.espertech.esper.regression.support.EventCollectionFactory;
import com.espertech.esper.regression.support.EventExpressionCase;
import com.espertech.esper.regression.support.PatternTestHarness;
import com.espertech.esper.support.bean.SupportBeanConstants;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;

public class TestEveryOperator extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (every (every b=" + EVENT_B_CLASS + "))");
        testCase.add("B1", "b", events.getEvent("B1"));
        for (int i = 0; i < 3; i++)
        {
            testCase.add("B2", "b", events.getEvent("B2"));
        }
        for (int i = 0; i < 9; i++)
        {
            testCase.add("B3", "b", events.getEvent("B3"));
        }
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (every b=" + EVENT_B_CLASS + "())");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B2", "b", events.getEvent("B2"));
        for (int i = 0; i < 4; i++)
        {
            testCase.add("B3", "b", events.getEvent("B3"));
        }
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every( every (every (every b=" + EVENT_B_CLASS + "())))");
        testCase.add("B1", "b", events.getEvent("B1"));
        for (int i = 0; i < 4; i++)
        {
            testCase.add("B2", "b", events.getEvent("B2"));
        }
        for (int i = 0; i < 16; i++)
        {
            testCase.add("B3", "b", events.getEvent("B3"));
        }
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }

    public void testEveryAndNot()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        EPServiceProvider engine = EPServiceProviderManager.getProvider("testRFIDZoneExit", config);
        engine.initialize();

        sendTimer(engine, 0);
        String expression =
            "select 'No event within 6 seconds' as alert\n" +
                    "from pattern [ every (timer:interval(6) and not " + SupportBean.class.getName() + ") ]";

        EPStatement statement = engine.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        sendTimer(engine, 2000);
        engine.getEPRuntime().sendEvent(new SupportBean());

        sendTimer(engine, 6000);
        sendTimer(engine, 7000);
        sendTimer(engine, 7999);
        assertFalse(listener.isInvoked());

        sendTimer(engine, 8000);
        assertEquals("No event within 6 seconds", listener.assertOneGetNewAndReset().get("alert"));

        sendTimer(engine, 12000);
        engine.getEPRuntime().sendEvent(new SupportBean());
        sendTimer(engine, 13000);
        engine.getEPRuntime().sendEvent(new SupportBean());

        sendTimer(engine, 18999);
        assertFalse(listener.isInvoked());

        sendTimer(engine, 19000);
        assertEquals("No event within 6 seconds", listener.assertOneGetNewAndReset().get("alert"));
    }

    private void sendTimer(EPServiceProvider engine, long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = engine.getEPRuntime();
        runtime.sendEvent(event);
    }
}