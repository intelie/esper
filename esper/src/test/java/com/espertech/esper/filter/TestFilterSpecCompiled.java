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

package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.MatchedEventMapImpl;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.filter.SupportFilterSpecBuilder;
import com.espertech.esper.util.SimpleNumberCoercer;
import com.espertech.esper.util.SimpleNumberCoercerFactory;
import junit.framework.TestCase;

import java.util.ArrayDeque;
import java.util.List;
import java.util.SortedSet;
import java.util.Vector;

public class TestFilterSpecCompiled extends TestCase
{
    private EventType eventType;
    private String eventTypeName;

    public void setUp()
    {
        eventTypeName = SupportBean.class.getName();
        eventType = SupportEventAdapterService.getService().addBeanType(eventTypeName, SupportBean.class, true, true, true);
    }

    public void testHashCode()
    {
        FilterSpecCompiled spec = SupportFilterSpecBuilder.build(eventType, new Object[] { "intPrimitive", FilterOperator.EQUAL, 2,
                                                                 "intBoxed", FilterOperator.EQUAL, 3 });

        int expectedHash = eventType.hashCode();
        expectedHash *= 31;
        expectedHash ^= "intPrimitive".hashCode();
        expectedHash ^= 31*Integer.valueOf(2).hashCode();
        expectedHash *= 31;
        expectedHash ^= "intBoxed".hashCode();
        expectedHash ^= 31*Integer.valueOf(3).hashCode();
        
        assertEquals(expectedHash, spec.hashCode());
    }

    public void testEquals()
    {
        Object[][] paramList = new Object[][] {
            { "intPrimitive", FilterOperator.EQUAL, 2, "intBoxed", FilterOperator.EQUAL, 3 },
            { "intPrimitive", FilterOperator.EQUAL, 3, "intBoxed", FilterOperator.EQUAL, 3 },
            { "intPrimitive", FilterOperator.EQUAL, 2 },
            { "intPrimitive", FilterOperator.RANGE_CLOSED, 1, 10 },
            { "intPrimitive", FilterOperator.EQUAL, 2, "intBoxed", FilterOperator.EQUAL, 3 },
            { },
            { },
            };

        Vector<FilterSpecCompiled> specVec = new Vector<FilterSpecCompiled>();
        for (Object[] param : paramList)
        {
            FilterSpecCompiled spec = SupportFilterSpecBuilder.build(eventType, param);
            specVec.add(spec);
        }

        assertFalse(specVec.get(0).equals(specVec.get(1)));
        assertFalse(specVec.get(0).equals(specVec.get(2)));
        assertFalse(specVec.get(0).equals(specVec.get(3)));
        assertEquals(specVec.get(0), specVec.get(4));
        assertFalse(specVec.get(0).equals(specVec.get(5)));
        assertEquals(specVec.get(5), specVec.get(6));

        assertFalse(specVec.get(2).equals(specVec.get(4)));
    }

    public void testGetValueSet()
    {
        List<FilterSpecParam> params = SupportFilterSpecBuilder.buildList(new Object[]
                                    { "intPrimitive", FilterOperator.EQUAL, 2 });
        SimpleNumberCoercer numberCoercer = SimpleNumberCoercerFactory.getCoercer(int.class, Double.class);
        params.add(new FilterSpecParamEventProp("doubleBoxed", FilterOperator.EQUAL, "asName", "doublePrimitive", false, numberCoercer, Double.class, "Test"));
        FilterSpecCompiled filterSpec = new FilterSpecCompiled(eventType, "SupportBean", params, null);

        SupportBean eventBean = new SupportBean();
        eventBean.setDoublePrimitive(999.999);
        EventBean event = SupportEventBeanFactory.createObject(eventBean);
        MatchedEventMap matchedEvents = new MatchedEventMapImpl();
        matchedEvents.add("asName", event);
        FilterValueSet valueSet = filterSpec.getValueSet(matchedEvents);

        // Assert the generated filter value container
        assertSame(eventType, valueSet.getEventType());
        assertEquals(2, valueSet.getParameters().size());

        // Assert the first param
        FilterValueSetParam param = valueSet.getParameters().getFirst();
        assertEquals("intPrimitive", param.getPropertyName());
        assertEquals(FilterOperator.EQUAL, param.getFilterOperator());
        assertEquals(2, param.getFilterForValue());

        // Assert the second param
        param = (FilterValueSetParam) valueSet.getParameters().toArray()[1];
        assertEquals("doubleBoxed", param.getPropertyName());
        assertEquals(FilterOperator.EQUAL, param.getFilterOperator());
        assertEquals(999.999, param.getFilterForValue());
    }

    public void testPresortParameters()
    {
        FilterSpecCompiled spec = makeFilterValues(
                "doublePrimitive", FilterOperator.LESS, 1.1,
                "doubleBoxed", FilterOperator.LESS, 1.1,
                "intPrimitive", FilterOperator.EQUAL, 1,
                "string", FilterOperator.EQUAL, "jack",
                "intBoxed", FilterOperator.EQUAL, 2,
                "floatBoxed", FilterOperator.RANGE_CLOSED, 1.1d, 2.2d);

        ArrayDeque<FilterSpecParam> copy = spec.getParameters();

        assertEquals("intPrimitive", copy.remove().getPropertyName());
        assertEquals("string", copy.remove().getPropertyName());
        assertEquals("intBoxed", copy.remove().getPropertyName());
        assertEquals("floatBoxed", copy.remove().getPropertyName());
        assertEquals("doublePrimitive", copy.remove().getPropertyName());
        assertEquals("doubleBoxed", copy.remove().getPropertyName());
    }

    private FilterSpecCompiled makeFilterValues(Object ... filterSpecArgs)
    {
        return SupportFilterSpecBuilder.build(eventType, filterSpecArgs);
    }
}
