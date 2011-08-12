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

package com.espertech.esper.regression.event;

import junit.framework.TestCase;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;

public class TestEventPropertyDynamicBean extends TestCase
{
    private SupportUpdateListener listener;
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testGetValue() throws Exception
    {
        String stmtText = "select item.id? as myid from " + SupportBeanDynRoot.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // check type
        assertEquals(Object.class, stmt.getEventType().getPropertyType("myid"));

        // check value with an object that has the property as an int
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean_S0(101)));
        assertEquals(101, listener.assertOneGetNewAndReset().get("myid"));

        // check value with an object that doesn't have the property
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new String("abc")));
        assertEquals(null, listener.assertOneGetNewAndReset().get("myid"));

        // check value with an object that has the property as a string
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean_A("e1")));
        assertEquals("e1", listener.assertOneGetNewAndReset().get("myid"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean_B("e2")));
        assertEquals("e2", listener.assertOneGetNewAndReset().get("myid"));

        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(new SupportBean_S1(102)));
        assertEquals(102, listener.assertOneGetNewAndReset().get("myid"));
    }

    public void testGetValueNested() throws Exception
    {
        String stmtText = "select item.nested?.nestedValue as n1, " +
                          " item.nested?.nestedValue? as n2, " +
                          " item.nested?.nestedNested.nestedNestedValue as n3, " +
                          " item.nested?.nestedNested?.nestedNestedValue as n4, " +
                          " item.nested?.nestedNested.nestedNestedValue? as n5, " +
                          " item.nested?.nestedNested?.nestedNestedValue? as n6 " +
                          " from " + SupportBeanDynRoot.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // check type
        assertEquals(Object.class, stmt.getEventType().getPropertyType("n1"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("n2"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("n3"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("n4"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("n5"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("n6"));

        SupportBeanComplexProps bean = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(bean));

        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(bean.getNested().getNestedValue(), event.get("n1"));
        assertEquals(bean.getNested().getNestedValue(), event.get("n2"));
        assertEquals(bean.getNested().getNestedNested().getNestedNestedValue(), event.get("n3"));
        assertEquals(bean.getNested().getNestedNested().getNestedNestedValue(), event.get("n4"));
        assertEquals(bean.getNested().getNestedNested().getNestedNestedValue(), event.get("n5"));
        assertEquals(bean.getNested().getNestedNested().getNestedNestedValue(), event.get("n6"));

        bean = SupportBeanComplexProps.makeDefaultBean();
        bean.getNested().setNestedValue("nested1");
        bean.getNested().getNestedNested().setNestedNestedValue("nested2");
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(bean));

        event = listener.assertOneGetNewAndReset();
        assertEquals("nested1", event.get("n1"));
        assertEquals("nested1", event.get("n2"));
        assertEquals("nested2", event.get("n3"));
        assertEquals("nested2", event.get("n4"));
        assertEquals("nested2", event.get("n5"));
        assertEquals("nested2", event.get("n6"));
    }

    public void testGetValueTop() throws Exception
    {
        String stmtText = "select id? as myid from " + SupportMarkerInterface.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // check type
        assertEquals(Object.class, stmt.getEventType().getPropertyType("myid"));

        epService.getEPRuntime().sendEvent(new SupportMarkerImplA("e1"));
        assertEquals("e1", listener.assertOneGetNewAndReset().get("myid"));

        epService.getEPRuntime().sendEvent(new SupportMarkerImplB(1));
        assertEquals(1, listener.assertOneGetNewAndReset().get("myid"));

        epService.getEPRuntime().sendEvent(new SupportMarkerImplC());
        assertEquals(null, listener.assertOneGetNewAndReset().get("myid"));
    }

    public void testGetValueTopNested() throws Exception
    {
        String stmtText = "select simpleProperty? as simple, "+
                          " nested?.nestedValue as nested, " +
                          " nested?.nestedNested.nestedNestedValue as nestedNested " +
                          "from " + SupportMarkerInterface.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        // check type
        assertEquals(Object.class, stmt.getEventType().getPropertyType("simple"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("nested"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("nestedNested"));

        epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("simple", event.get("simple"));
        assertEquals("nestedValue", event.get("nested"));
        assertEquals("nestedNestedValue", event.get("nestedNested"));
    }

    public void testGetValueTopComplex() throws Exception
    {
        String stmtText = "select item?.indexed[0] as indexed1, " +
                          "item?.indexed[1]? as indexed2, " +
                          "item?.arrayProperty[1]? as array, " +
                          "item?.mapped('keyOne') as mapped1, " +
                          "item?.mapped('keyTwo')? as mapped2,  " +
                          "item?.mapProperty('xOne')? as map " +
                          "from " + SupportBeanDynRoot.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        assertEquals(Object.class, stmt.getEventType().getPropertyType("indexed1"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("indexed2"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("mapped1"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("mapped2"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("array"));

        SupportBeanComplexProps inner = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(new SupportBeanDynRoot(inner));
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(inner.getIndexed(0), event.get("indexed1"));
        assertEquals(inner.getIndexed(1), event.get("indexed2"));
        assertEquals(inner.getMapped("keyOne"), event.get("mapped1"));
        assertEquals(inner.getMapped("keyTwo"), event.get("mapped2"));
        assertEquals(inner.getMapProperty().get("xOne"), event.get("map"));
        assertEquals(inner.getArrayProperty()[1], event.get("array"));
    }

    public void testGetValueRootComplex() throws Exception
    {
        String stmtText = "select indexed[0]? as indexed1, " +
                          "indexed[1]? as indexed2, " +
                          "mapped('keyOne')? as mapped1, " +
                          "mapped('keyTwo')? as mapped2  " +
                          "from " + SupportBeanComplexProps.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        assertEquals(Object.class, stmt.getEventType().getPropertyType("indexed1"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("indexed2"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("mapped1"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("mapped2"));

        SupportBeanComplexProps inner = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(inner);
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(inner.getIndexed(0), event.get("indexed1"));
        assertEquals(inner.getIndexed(1), event.get("indexed2"));
        assertEquals(inner.getMapped("keyOne"), event.get("mapped1"));
        assertEquals(inner.getMapped("keyTwo"), event.get("mapped2"));
    }

    public void testPerformance() throws Exception
    {
        String stmtText = "select simpleProperty?, " +
                          "indexed[1]? as indexed, " +
                          "mapped('keyOne')? as mapped " +
                          "from " + SupportBeanComplexProps.class.getName();
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        EventType type = stmt.getEventType();
        assertEquals(Object.class, type.getPropertyType("simpleProperty?"));
        assertEquals(Object.class, type.getPropertyType("indexed"));
        assertEquals(Object.class, type.getPropertyType("mapped"));

        SupportBeanComplexProps inner = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(inner);
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(inner.getSimpleProperty(), event.get("simpleProperty?"));
        assertEquals(inner.getIndexed(1), event.get("indexed"));
        assertEquals(inner.getMapped("keyOne"), event.get("mapped"));

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            epService.getEPRuntime().sendEvent(inner);
            if (i % 1000 == 0)
            {
                listener.reset();
            }
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("delta=" + delta, delta < 1000);
    }
}
