package net.esper.filter;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.pattern.MatchedEventMap;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.filter.SupportFilterSpecBuilder;

import java.util.List;
import java.util.Vector;

public class TestFilterSpecCompiled extends TestCase
{
    private EventType eventType;
    private String eventTypeId;
    private String eventTypeAlias;

    public void setUp()
    {
        eventTypeAlias = SupportBean.class.getName();
        eventType = SupportEventAdapterService.getService().addBeanType(eventTypeAlias, SupportBean.class);
        eventTypeId = SupportEventAdapterService.getService().getIdByAlias(eventTypeAlias);
    }

    public void testHashCode()
    {
        FilterSpecCompiled spec = SupportFilterSpecBuilder.build(eventType, new Object[] { "intPrimitive", FilterOperator.EQUAL, 2,
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
        params.add(new FilterSpecParamEventProp("doubleBoxed", FilterOperator.EQUAL, "asName", "doublePrimitive", false, Double.class));
        FilterSpecCompiled filterSpec = new FilterSpecCompiled(eventType, params);

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
