package net.esper.event;

import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.event.SupportEventAdapterService;

import java.util.Map;
import java.util.HashMap;

public class TestCompositeEventType extends TestCompositeEventBase
{
    public void testGetPropertyType()
    {
        assertEquals(SupportBean.class, eventType.getPropertyType("a"));
        assertEquals(int.class, eventType.getPropertyType("a.intPrimitive"));
        assertEquals(String.class, eventType.getPropertyType("b.nested.nestedValue"));

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

        assertEquals(event, eventType.getGetter("a").get(eventBeanInComplete));
        assertEquals(1, eventType.getGetter("a.intPrimitive").get(eventBeanInComplete));
        assertEquals(null, eventType.getGetter("b.nested.nestedValue").get(eventBeanInComplete));
        assertEquals(null, eventType.getGetter("b.nested").get(eventBeanInComplete));
        assertEquals(null, eventType.getGetter("b").get(eventBeanInComplete));

        assertEquals(null, eventType.getGetter("b.nested.xxx"));
        assertEquals(null, eventType.getGetter("b.xxx"));
        assertEquals(null, eventType.getGetter("y.nested.xxx"));
        assertEquals(null, eventType.getGetter("y"));
    }

    public void testGetPropertyNames()
    {
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"a", "b"}, eventType.getPropertyNames());
    }

    public void testIsProperty()
    {
        assertTrue(eventType.isProperty("b.nested.nestedValue"));
        assertFalse(eventType.isProperty("b.nested.xxx"));
    }

    public void testEquals()
    {
        Map<String, EventType> taggedEventTypes = new HashMap<String, EventType>();
        taggedEventTypes.put("a", SupportEventAdapterService.getService().addBeanType("A", SupportBean.class));
        taggedEventTypes.put("b", SupportEventAdapterService.getService().addBeanType("B", SupportBeanComplexProps.class));
        EventType eventTypeTwo = new CompositeEventType("alias", taggedEventTypes);
        EventType eventTypeThree = new CompositeEventType("alias2", taggedEventTypes);

        Map<String, EventType> taggedEventTypesTwo = new HashMap<String, EventType>();
        taggedEventTypesTwo.put("a", SupportEventAdapterService.getService().addBeanType("A", SupportBean.class));
        EventType eventTypeFour = new CompositeEventType("alias", taggedEventTypesTwo);

        assertEquals(eventTypeTwo, eventType);
        assertTrue(eventTypeThree.equals(eventType));   // different name is allowed - the type is anonymous
        assertFalse(eventTypeFour.equals(eventType));
    }
}
