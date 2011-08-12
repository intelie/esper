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

import junit.framework.*;
import com.espertech.esper.regression.support.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.PrintUpdateListener;
import com.espertech.esper.client.*;
import com.espertech.esper.client.EventBean;

public class TestOrOperator extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase;

        testCase = new EventExpressionCase("a=" + EVENT_A_CLASS + " or a=" + EVENT_A_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("a=" + EVENT_A_CLASS + " or b=" + EVENT_B_CLASS + " or c=" + EVENT_C_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " or every d=" + EVENT_D_CLASS);
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("D1", "d", events.getEvent("D1"));
        testCase.add("D2", "d", events.getEvent("D2"));
        testCase.add("B3", "b", events.getEvent("B3"));
        testCase.add("D3", "d", events.getEvent("D3"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("a=" + EVENT_A_CLASS + " or b=" + EVENT_B_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("a=" + EVENT_A_CLASS + " or every b=" + EVENT_B_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every a=" + EVENT_A_CLASS + " or d=" + EVENT_D_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCase.add("A2", "a", events.getEvent("A2"));
        testCase.add("D1", "d", events.getEvent("D1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (every b=" + EVENT_B_CLASS + "() or d=" + EVENT_D_CLASS + "())");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B2", "b", events.getEvent("B2"));
        for (int i = 0; i < 4; i++)
        {
            testCase.add("D1", "d", events.getEvent("D1"));
        }
        for (int i = 0; i < 4; i++)
        {
            testCase.add("D2", "d", events.getEvent("D2"));
        }
        for (int i = 0; i < 4; i++)
        {
            testCase.add("B3", "b", events.getEvent("B3"));
        }
        for (int i = 0; i < 8; i++)
        {
            testCase.add("D3", "d", events.getEvent("D3"));
        }
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + "() or every d=" + EVENT_D_CLASS + "())");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("D1", "d", events.getEvent("D1"));
        testCase.add("D2", "d", events.getEvent("D2"));
        testCase.add("D2", "d", events.getEvent("D2"));
        for (int i = 0; i < 4; i++)
        {
            testCase.add("B3", "b", events.getEvent("B3"));
        }
        for (int i = 0; i < 4; i++)
        {
            testCase.add("D3", "d", events.getEvent("D3"));
        }
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every (every d=" + EVENT_D_CLASS + "() or every b=" + EVENT_B_CLASS + "())");
        testCase.add("B1", "b", events.getEvent("B1"));
        testCase.add("B2", "b", events.getEvent("B2"));
        testCase.add("B2", "b", events.getEvent("B2"));
        for (int i = 0; i < 4; i++)
        {
            testCase.add("D1", "d", events.getEvent("D1"));
        }
        for (int i = 0; i < 8; i++)
        {
            testCase.add("D2", "d", events.getEvent("D2"));
        }
        for (int i = 0; i < 16; i++)
        {
            testCase.add("B3", "b", events.getEvent("B3"));
        }
        for (int i = 0; i < 32; i++)
        {
            testCase.add("D3", "d", events.getEvent("D3"));
        }
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }

    public void testOrAndNot()
    {
        tryOrAndNot("(a=A -> b=B) or (a=A -> not b=B)");
        tryOrAndNot("a=A -> (b=B or not B)");
    }

    private void tryOrAndNot(String pattern)
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("A", SupportBean_A.class.getName());
        config.addEventType("B", SupportBean_B.class.getName());
        config.addEventType("C", SupportBean_C.class.getName());
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String expression =
            "select * " +
            "from pattern [" + pattern + "]";

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);
        statement.addListener(new PrintUpdateListener());

        Object eventA1 = new SupportBean_A("A1");
        epService.getEPRuntime().sendEvent(eventA1);
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(eventA1, event.get("a"));
        assertNull(event.get("b"));

        Object eventB1 = new SupportBean_B("B1");
        epService.getEPRuntime().sendEvent(eventB1);
        event = listener.assertOneGetNewAndReset();
        assertEquals(eventA1, event.get("a"));
        assertEquals(eventB1, event.get("b"));
    }
}