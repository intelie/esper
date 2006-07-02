package net.esper.filter;

import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.pattern.MatchedEventMap;
import net.esper.support.bean.SupportBean;
import net.esper.support.filter.SupportFilterSpecBuilder;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventBeanFactory;

public class TestFilterSpec extends TestCase
{
    private EventType eventType;

    public void setUp()
    {
        eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public void testHashCode()
    {
        FilterSpec spec = SupportFilterSpecBuilder.build(eventType, new Object[] { "intPrimitive", FilterOperator.EQUAL, 2,
                                                                 "intBoxed", FilterOperator.EQUAL, 3 });
        int expectedHash = eventType.hashCode() ^ "intPrimitive".hashCode() ^ "intBoxed".hashCode();
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

        Vector<FilterSpec> specVec = new Vector<FilterSpec>();
        for (Object[] param : paramList)
        {
            FilterSpec spec = SupportFilterSpecBuilder.build(eventType, param);
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
        params.add(new FilterSpecParamEventProp("doubleBoxed", FilterOperator.EQUAL, "asName", "doublePrimitive"));
        FilterSpec filterSpec = new FilterSpec(eventType, params);

        SupportBean eventBean = new SupportBean();
        eventBean.setDoublePrimitive(999.999);
        EventBean event = SupportEventBeanFactory.createObject(eventBean);
        MatchedEventMap matchedEvents = new MatchedEventMap();
        matchedEvents.add("asName", event);
        FilterValueSet valueSet = filterSpec.getValueSet(matchedEvents);

        // Assert the generated filter value container
        assertSame(eventType, valueSet.getEventType());
        assertEquals(2, valueSet.getParameters().size());

        // Assert the first param
        FilterValueSetParam param = valueSet.getParameters().get(0);
        assertEquals("intPrimitive", param.getPropertyName());
        assertEquals(FilterOperator.EQUAL, param.getFilterOperator());
        assertEquals(2, param.getFilterForValue());

        // Assert the second param
        param = valueSet.getParameters().get(1);
        assertEquals("doubleBoxed", param.getPropertyName());
        assertEquals(FilterOperator.EQUAL, param.getFilterOperator());
        assertEquals(999.999, param.getFilterForValue());
    }
}
