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

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.EventBean;
import junit.framework.TestCase;

public class TestPatternEventProperties extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testWildcardSimplePattern()
    {
        setupSimplePattern("*");

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        EventBean eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("a"));
    }

    public void testWildcardOrPattern()
    {
        setupOrPattern("*");

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        EventBean eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("a"));
        assertNull(eventBean.get("b"));

        event = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(event);
        eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("b"));
        assertNull(eventBean.get("a"));
    }

    public void testPropertiesSimplePattern()
    {
        setupSimplePattern("a, a as myEvent, a.intPrimitive as myInt, a.string");

        SupportBean event = new SupportBean();
        event.setIntPrimitive(1);
        event.setString("test");
        epService.getEPRuntime().sendEvent(event);

        EventBean eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("a"));
        assertSame(event, eventBean.get("myEvent"));
        assertEquals(1, eventBean.get("myInt"));
        assertEquals("test", eventBean.get("a.string"));
    }

    public void testPropertiesOrPattern()
    {
        setupOrPattern("a, a as myAEvent, b, b as myBEvent, a.intPrimitive as myInt, " +
                "a.string, b.simpleProperty as simple, b.indexed[0] as indexed, b.nested.nestedValue as nestedVal");

        Object event = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(event);
        EventBean eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("b"));
        assertEquals("simple", eventBean.get("simple"));
        assertEquals(1, eventBean.get("indexed"));
        assertEquals("nestedValue", eventBean.get("nestedVal"));
        assertNull(eventBean.get("a"));
        assertNull(eventBean.get("myAEvent"));
        assertNull(eventBean.get("myInt"));
        assertNull(eventBean.get("a.string"));

        SupportBean eventTwo = new SupportBean();
        eventTwo.setIntPrimitive(2);
        eventTwo.setString("test2");
        epService.getEPRuntime().sendEvent(eventTwo);
        eventBean = updateListener.assertOneGetNewAndReset();
        assertEquals(2, eventBean.get("myInt"));
        assertEquals("test2", eventBean.get("a.string"));
        assertNull(eventBean.get("b"));
        assertNull(eventBean.get("myBEvent"));
        assertNull(eventBean.get("simple"));
        assertNull(eventBean.get("indexed"));
        assertNull(eventBean.get("nestedVal"));
    }

    private void setupSimplePattern(String selectCriteria)
    {
        String stmtText = "select " + selectCriteria + " from pattern [a=" + SupportBean.class.getName() + "]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(updateListener);
    }

    private void setupOrPattern(String selectCriteria)
    {
        String stmtText = "select " + selectCriteria + " from pattern [every(a=" + SupportBean.class.getName() +
                " or b=" + SupportBeanComplexProps.class.getName() + ")]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(updateListener);
    }
}
