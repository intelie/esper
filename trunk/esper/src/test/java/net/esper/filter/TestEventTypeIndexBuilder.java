package net.esper.filter;

import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanSimple;
import net.esper.support.filter.SupportFilterHandle;
import net.esper.support.filter.SupportFilterSpecBuilder;
import net.esper.support.event.SupportEventTypeFactory;

import junit.framework.TestCase;

public class TestEventTypeIndexBuilder extends TestCase
{
    private EventTypeIndex eventTypeIndex;
    private EventTypeIndexBuilder indexBuilder;

    private EventType typeOne;
    private EventType typeTwo;

    private FilterValueSet valueSetOne;
    private FilterValueSet valueSetTwo;

    private FilterHandle callbackOne;
    private FilterHandle callbackTwo;

    public void setUp()
    {
        eventTypeIndex = new EventTypeIndex();
        indexBuilder = new EventTypeIndexBuilder(eventTypeIndex);

        typeOne = SupportEventTypeFactory.createBeanType(SupportBean.class);
        typeTwo = SupportEventTypeFactory.createBeanType(SupportBeanSimple.class);

        valueSetOne = SupportFilterSpecBuilder.build(typeOne, new Object[0]).getValueSet(typeOne, null);
        valueSetTwo = SupportFilterSpecBuilder.build(typeTwo, new Object[0]).getValueSet(typeTwo, null);

        callbackOne = new SupportFilterHandle();
        callbackTwo = new SupportFilterHandle();
    }

    public void testAddRemove()
    {
        assertNull(eventTypeIndex.get(typeOne));
        assertNull(eventTypeIndex.get(typeTwo));

        indexBuilder.add(valueSetOne, callbackOne);
        indexBuilder.add(valueSetTwo, callbackTwo);

        assertTrue(eventTypeIndex.get(typeOne) != null);
        assertTrue(eventTypeIndex.get(typeTwo) != null);

        try
        {
            indexBuilder.add(valueSetOne, callbackOne);
            assertTrue(false);
        }
        catch (IllegalStateException ex)
        {
            // Expected exception
        }

        indexBuilder.remove(callbackOne);
        indexBuilder.add(valueSetOne, callbackOne);
        indexBuilder.remove(callbackOne);

        // Try invalid remove
        try
        {
            indexBuilder.remove(callbackOne);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected Exception
        }
    }
}
