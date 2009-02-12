package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.*;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_B;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Map;

public class TestCompositeSelect extends TestCase
{
    public void testFollowedByFilter()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("A", SupportBean_A.class.getName());
        config.addEventType("B", SupportBean_B.class.getName());
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmtTxtOne = "insert into StreamOne select * from pattern [a=A -> b=B]";
        epService.getEPAdministrator().createEPL(stmtTxtOne);

        SupportUpdateListener listener = new SupportUpdateListener();
        String stmtTxtTwo = "select *, 1 as code from StreamOne";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(stmtTxtTwo);
        stmtTwo.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        EventBean event = listener.assertOneGetNewAndReset();

        Object[] values = new Object[stmtTwo.getEventType().getPropertyNames().length];
        int count = 0;
        for (String name : stmtTwo.getEventType().getPropertyNames())
        {
            values[count++] = event.get(name);
        }

        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("a", SupportBean_A.class, false, false, false, false, true),
            new EventPropertyDescriptor("b", SupportBean_B.class, false, false, false, false, true)
           }, ((EPServiceProviderSPI) epService).getEventAdapterService().getExistsTypeByName("StreamOne").getPropertyDescriptors());
    }

    public void testFragment()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("A", SupportBean_A.class.getName());
        config.addEventType("B", SupportBean_B.class.getName());

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String stmtTxtOne = "select * from pattern [[2] a=A -> b=B]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTxtOne);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);
        
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("a", SupportBean_A[].class, false, false, true, false, true),
            new EventPropertyDescriptor("b", SupportBean_B.class, false, false, false, false, true)
           }, stmt.getEventType().getPropertyDescriptors());

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        
        EventBean event = listener.assertOneGetNewAndReset();
        assertTrue(event.getUnderlying() instanceof Map);

        // test fragment B type and event
        FragmentEventType typeFragB = event.getEventType().getFragmentType("b");
        assertFalse(typeFragB.isIndexed());
        assertEquals("B", typeFragB.getFragmentType().getName());
        assertEquals(String.class, typeFragB.getFragmentType().getPropertyType("id"));

        EventBean eventFragB = (EventBean) event.getFragment("b");
        assertEquals("B", eventFragB.getEventType().getName());

        // test fragment A type and event
        FragmentEventType typeFragA = event.getEventType().getFragmentType("a");
        assertTrue(typeFragA.isIndexed());
        assertEquals("A", typeFragA.getFragmentType().getName());
        assertEquals(String.class, typeFragA.getFragmentType().getPropertyType("id"));

        assertTrue(event.getFragment("a") instanceof EventBean[]);
        EventBean eventFragA1 = (EventBean) event.getFragment("a[0]");
        assertEquals("A", eventFragA1.getEventType().getName());
        assertEquals("A1", eventFragA1.get("id"));
        EventBean eventFragA2 = (EventBean) event.getFragment("a[1]");
        assertEquals("A", eventFragA2.getEventType().getName());
        assertEquals("A2", eventFragA2.get("id"));
    }
}
