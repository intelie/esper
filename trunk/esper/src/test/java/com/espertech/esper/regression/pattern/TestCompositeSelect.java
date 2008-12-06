package com.espertech.esper.regression.pattern;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.core.EPServiceProviderSPI;

import java.util.Arrays;
import java.util.Map;

public class TestCompositeSelect extends TestCase
{
    public void testFollowedByFilter()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addEventTypeAlias("A", SupportBean_A.class.getName());
        config.addEventTypeAlias("B", SupportBean_B.class.getName());
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
           }, ((EPServiceProviderSPI) epService).getEventAdapterService().getExistsTypeByAlias("StreamOne").getPropertyDescriptors());

        stmtTwo.destroy();
        stmtTxtOne = "select * from pattern [[2] a=A -> b=B]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTxtOne);
        stmt.addListener(listener);
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("a", SupportBean_A.class, false, false, true, false, true),
            new EventPropertyDescriptor("b", SupportBean_B.class, false, false, false, false, true)
           }, stmt.getEventType().getPropertyDescriptors());

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        event = listener.assertOneGetNewAndReset();
        assertTrue(event.getUnderlying() instanceof Map);
    }
}
