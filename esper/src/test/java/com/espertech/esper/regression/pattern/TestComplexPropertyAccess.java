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
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.regression.support.*;
import com.espertech.esper.support.bean.SupportBeanCombinedProps;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.SerializableObjectCopier;

public class TestComplexPropertyAccess extends TestCase
{
    private static String EVENT_COMPLEX = SupportBeanComplexProps.class.getName();
    private static String EVENT_NESTED = SupportBeanCombinedProps.class.getName();

    public void testComplexProperties() throws Exception
    {
        EventCollection events = EventCollectionFactory.getSetSixComplexProperties();
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(mapped('keyOne') = 'valueOne')");
        testCase.add("e1", "s", events.getEvent("e1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(indexed[1] = 2)");
        testCase.add("e1", "s", events.getEvent("e1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(indexed[0] = 2)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(arrayProperty[1] = 20)");
        testCase.add("e1", "s", events.getEvent("e1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(arrayProperty[1] in (10:30))");
        testCase.add("e1", "s", events.getEvent("e1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(arrayProperty[2] = 20)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(nested.nestedValue = 'nestedValue')");
        testCase.add("e1", "s", events.getEvent("e1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(nested.nestedValue = 'dummy')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(nested.nestedNested.nestedNestedValue = 'nestedNestedValue')");
        testCase.add("e1", "s", events.getEvent("e1"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(nested.nestedNested.nestedNestedValue = 'x')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(indexed[1].mapped('1mb').value = '1ma1')");
        testCase.add("e2", "s", events.getEvent("e2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(indexed[0].mapped('1ma').value = 'x')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(array[0].mapped('0ma').value = '0ma0')");
        testCase.add("e2", "s", events.getEvent("e2"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(array[2].mapped('x').value = 'x')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(array[879787].mapped('x').value = 'x')");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(array[0].mapped('xxx').value = 'x')");
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }

    public void testIndexedFilterProp() throws Exception
    {
        SupportUpdateListener testListener = new SupportUpdateListener();
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        String type = SupportBeanComplexProps.class.getName();
        String pattern = "every a=" + type + "(indexed[0]=3)";

        EPStatement stmt = epService.getEPAdministrator().createPattern(pattern);
        stmt.addListener(testListener);

        Object event = new SupportBeanComplexProps(new int[] { 3, 4});
        epService.getEPRuntime().sendEvent(event);
        assertSame(event, testListener.assertOneGetNewAndReset().get("a"));

        event = new SupportBeanComplexProps(new int[] { 6});
        epService.getEPRuntime().sendEvent(event);
        assertFalse(testListener.isInvoked());

        event = new SupportBeanComplexProps(new int[] { 3});
        epService.getEPRuntime().sendEvent(event);
        assertSame(event, testListener.assertOneGetNewAndReset().get("a"));
    }

    public void testIndexedValueProp() throws Exception
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        String type = SupportBeanComplexProps.class.getName();
        String pattern = "every a=" + type + " -> b=" + type + "(indexed[0] = a.indexed[0])";

        EPStatement stmt = epService.getEPAdministrator().createPattern(pattern);
        runIndexedValueProp(epService, stmt);
    }

    public void testIndexedValuePropOM() throws Exception
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        String type = SupportBeanComplexProps.class.getName();

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard());
        PatternExpr pattern = Patterns.followedBy(Patterns.everyFilter(type, "a"),
                Patterns.filter(Filter.create(type, Expressions.eqProperty("indexed[0]", "a.indexed[0]")), "b"));
        model.setFromClause(FromClause.create(PatternStream.create(pattern)));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        String patternText = "select * from pattern [every a=" + type + " -> b=" + type + "(indexed[0] = a.indexed[0])]";
        assertEquals(patternText, model.toEPL());

        EPStatement stmt = epService.getEPAdministrator().create(model);
        runIndexedValueProp(epService, stmt);
    }

    public void testIndexedValuePropCompile() throws Exception
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        String type = SupportBeanComplexProps.class.getName();

        String patternText = "select * from pattern [every a=" + type + " -> b=" + type + "(indexed[0] = a.indexed[0])]";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(patternText);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(patternText, model.toEPL());

        EPStatement stmt = epService.getEPAdministrator().create(model);
        runIndexedValueProp(epService, stmt);
    }

    private void runIndexedValueProp(EPServiceProvider epService, EPStatement stmt)
    {
        SupportUpdateListener testListener = new SupportUpdateListener();
        stmt.addListener(testListener);

        Object eventOne = new SupportBeanComplexProps(new int[] {3});
        epService.getEPRuntime().sendEvent(eventOne);
        assertFalse(testListener.isInvoked());

        Object event = new SupportBeanComplexProps(new int[] { 6});
        epService.getEPRuntime().sendEvent(event);
        assertFalse(testListener.isInvoked());

        Object eventTwo = new SupportBeanComplexProps(new int[] { 3});
        epService.getEPRuntime().sendEvent(eventTwo);
        EventBean eventBean = testListener.assertOneGetNewAndReset();
        assertSame(eventOne, eventBean.get("a"));
        assertSame(eventTwo, eventBean.get("b"));
    }
}


