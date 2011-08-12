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
import com.espertech.esper.support.bean.SupportBeanConstants;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.util.SerializableObjectCopier;

public class TestOperators extends TestCase implements SupportBeanConstants
{
    public void testOp() throws Exception
    {
        EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") " +
                " and " +
                "(a=" + EVENT_A_CLASS + " -> e=" + EVENT_E_CLASS + ")"
                );
        testCase.add("E1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A1"), "e", events.getEvent("E1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " -> (d=" + EVENT_D_CLASS + "() or a=" + EVENT_A_CLASS + ")");
        testCase.add("A2", "b", events.getEvent("B1"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard());
        PatternExpr pattern = Patterns.followedBy(
                Patterns.filter(EVENT_B_CLASS, "b"),
                Patterns.or(Patterns.filter(EVENT_D_CLASS, "d"), Patterns.filter(EVENT_A_CLASS, "a")));
        model.setFromClause(FromClause.create(PatternStream.create(pattern)));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        String text = "select * from pattern [b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + " or a=" + EVENT_A_CLASS + "]";
        assertEquals(text, model.toEPL());
        testCase = new EventExpressionCase(model);
        testCase.add("A2", "b", events.getEvent("B1"), "a", events.getEvent("A2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() -> (" +
                "(d=" + EVENT_D_CLASS + "() -> a=" + EVENT_A_CLASS + "())" +
                " or " +
                "(a=" + EVENT_A_CLASS + "() -> e=" + EVENT_E_CLASS + "()))"
                );
        testCase.add("E1", "b", events.getEvent("B1"), "a", events.getEvent("A2"), "e", events.getEvent("E1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "() and d=" +
                EVENT_D_CLASS + "() or a=" +
                EVENT_A_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + "() -> d=" + EVENT_D_CLASS + "()) or a=" + EVENT_A_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + "() and " +
                "d=" + EVENT_D_CLASS + "()) or " +
                "a=" + EVENT_A_CLASS);
        testCase.add("A1", "a", events.getEvent("A1"));
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }
}