package com.espertech.esper.event;

import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportBean_C;
import com.espertech.esper.support.bean.SupportBean_D;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.collection.Pair;

import java.util.Map;
import java.util.HashMap;

public class TestCompositeEventType extends TestCompositeEventBase
{
    public void testGetPropertyType()
    {
        assertEquals(SupportBean.class, eventType.getPropertyType("a"));
        assertEquals(int.class, eventType.getPropertyType("a.intPrimitive"));
        assertEquals(String.class, eventType.getPropertyType("b.nested.nestedValue"));
        assertEquals(SupportBean_C[].class, eventType.getPropertyType("c"));
        assertEquals(SupportBean_C.class, eventType.getPropertyType("c[0]"));
        assertEquals(String.class, eventType.getPropertyType("c[0].id"));

        assertEquals(null, eventType.getPropertyType("b.nested.xxx"));
        assertEquals(null, eventType.getPropertyType("b.xxx"));
        assertEquals(null, eventType.getPropertyType("y.nested.xxx"));
        assertEquals(null, eventType.getPropertyType("y"));
    }

    public void testGetGetter()
    {
        assertEquals(event, eventType.getGetter("a").get(eventBeanComplete));
        assertEquals(1, eventType.getGetter("a.intPrimitive").get(eventBeanComplete));
        assertEquals("nestedValue", eventType.getGetter("b.nested.nestedValue").get(eventBeanComplete));
        assertEquals(2, ((SupportBean_C[]) eventType.getGetter("c").get(eventBeanComplete)).length);
        assertEquals("C0", ((SupportBean_C) eventType.getGetter("c[0]").get(eventBeanComplete)).getId());
        assertEquals("C1", ((SupportBean_C) eventType.getGetter("c[1]").get(eventBeanComplete)).getId());
        assertEquals(null, eventType.getGetter("c[2]").get(eventBeanComplete));
        assertEquals("C0", eventType.getGetter("c[0].id").get(eventBeanComplete));
        assertEquals("C1", eventType.getGetter("c[1].id").get(eventBeanComplete));
        assertNull(null, eventType.getGetter("c[2].id").get(eventBeanComplete));

        assertEquals(event, eventType.getGetter("a").get(eventBeanInComplete));
        assertEquals(1, eventType.getGetter("a.intPrimitive").get(eventBeanInComplete));
        assertEquals(null, eventType.getGetter("b.nested.nestedValue").get(eventBeanInComplete));
        assertEquals(null, eventType.getGetter("b.nested").get(eventBeanInComplete));
        assertEquals(null, eventType.getGetter("b").get(eventBeanInComplete));

        assertEquals(null, eventType.getGetter("b.nested.xxx"));
        assertEquals(null, eventType.getGetter("b.xxx"));
        assertEquals(null, eventType.getGetter("y.nested.xxx"));
        assertEquals(null, eventType.getGetter("y"));
        assertEquals(null, eventType.getGetter("c.xxx"));
        assertEquals(null, eventType.getGetter("c[123].xxx"));
        assertEquals(null, eventType.getGetter("c('123').xxx"));
    }

    public void testGetPropertyNames()
    {
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"a", "b", "c[]"}, eventType.getPropertyNames());
    }

    public void testIsProperty()
    {
        assertTrue(eventType.isProperty("b.nested.nestedValue"));
        assertTrue(eventType.isProperty("c"));
        assertTrue(eventType.isProperty("c[10]"));
        assertTrue(eventType.isProperty("c[10].id"));
        assertFalse(eventType.isProperty("b.nested.xxx"));
    }

    public void testEquals()
    {
        Map<String, Pair<EventType, String>> taggedEventTypes = new HashMap<String, Pair<EventType, String>>();
        taggedEventTypes.put("a", new Pair<EventType, String>(SupportEventAdapterService.getService().addBeanType("A", SupportBean.class, true), "SupportBean"));
        taggedEventTypes.put("b", new Pair<EventType, String>(SupportEventAdapterService.getService().addBeanType("B", SupportBeanComplexProps.class, true), "SupportBeanComplexProps"));

        Map<String, Pair<EventType, String>> arrayEventTypes = new HashMap<String, Pair<EventType, String>>();
        arrayEventTypes.put("c", new Pair<EventType, String>(SupportEventAdapterService.getService().addBeanType("C", SupportBean_C.class, true), "SupportBean_C"));

        // construct compare-to types
        EventType eventTypeTwo = new CompositeEventType(null, "alias", taggedEventTypes, arrayEventTypes);
        EventType eventTypeThree = new CompositeEventType(null, "alias2", taggedEventTypes, arrayEventTypes);

        Map<String, Pair<EventType, String>> taggedEventTypesTwo = new HashMap<String, Pair<EventType, String>>();
        taggedEventTypesTwo.put("a", new Pair<EventType, String>(SupportEventAdapterService.getService().addBeanType("A", SupportBean.class, true), "SupportBean"));
        EventType eventTypeFour = new CompositeEventType(null, "alias", taggedEventTypesTwo, arrayEventTypes);

        assertEquals(eventTypeTwo, eventType);
        assertTrue(eventTypeThree.equals(eventType));   // different name is allowed - the type is anonymous
        assertFalse(eventTypeFour.equals(eventType));

        EventType eventTypeFive = new CompositeEventType(null, "alias", taggedEventTypes, new HashMap<String, Pair<EventType, String>>());
        assertFalse(eventTypeFive.equals(eventType));

        arrayEventTypes.put("c", new Pair<EventType, String>(SupportEventAdapterService.getService().addBeanType("D", SupportBean_D.class, true), "SupportBean_D"));
        EventType eventTypeSix = new CompositeEventType(null, "alias", taggedEventTypes, arrayEventTypes);
        assertFalse(eventTypeSix.equals(eventType));
    }
}
