package net.esper.event;

public class TestCompositeEventBean extends TestCompositeEventBase
{
    public void testGet()
    {
        assertEquals(event, eventBeanComplete.get("a"));
        assertEquals(1, eventBeanComplete.get("a.intPrimitive"));
        assertEquals("nestedValue", eventBeanComplete.get("b.nested.nestedValue"));

        assertEquals(event, eventBeanInComplete.get("a"));
        assertEquals(1, eventBeanInComplete.get("a.intPrimitive"));
        assertEquals(null, eventBeanInComplete.get("b.nested.nestedValue"));
        assertEquals(null, eventBeanInComplete.get("b.nested"));
        assertEquals(null, eventBeanInComplete.get("b"));
    }
}
