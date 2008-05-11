package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_B;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.event.EventBean;

import java.util.Map;
import java.util.HashMap;

public class TestInsertIntoTransposePattern extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener listenerInsertInto;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        listener = new SupportUpdateListener();
        listenerInsertInto = new SupportUpdateListener();
    }

    public void testTransposePOJOEventPattern()
    {
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("AEvent", SupportBean_A.class);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("BEvent", SupportBean_B.class);

        String stmtTextOne = "insert into MyStream select a, b from pattern [a=AEvent -> b=BEvent]";
        epService.getEPAdministrator().createEPL(stmtTextOne);

        String stmtTextTwo = "select a.id, b.id from MyStream";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTextTwo);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        epService.getEPRuntime().sendEvent(new SupportBean_B("B1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "a.id,b.id".split(","), new Object[] {"A1", "B1"});
    }

    public void testTransposeMapEventPattern()
    {
        Map<String, Object> type = makeMap(new Object[][] {{"id", String.class}});

        epService.getEPAdministrator().getConfiguration().addEventTypeAliasNestable("AEvent", type);
        epService.getEPAdministrator().getConfiguration().addEventTypeAliasNestable("BEvent", type);

        String stmtTextOne = "insert into MyStream select a, b from pattern [a=AEvent -> b=BEvent]";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listenerInsertInto);
        assertEquals(Map.class, stmtOne.getEventType().getPropertyType("a"));
        assertEquals(Map.class, stmtOne.getEventType().getPropertyType("b"));

        String stmtTextTwo = "select a.id, b.id from MyStream";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(stmtTextTwo);
        stmtTwo.addListener(listener);
        assertEquals(String.class, stmtTwo.getEventType().getPropertyType("a.id"));
        assertEquals(String.class, stmtTwo.getEventType().getPropertyType("b.id"));

        Map<String, Object> eventOne = makeMap(new Object[][] {{"id", "A1"}});
        Map<String, Object> eventTwo = makeMap(new Object[][] {{"id", "B1"}});

        epService.getEPRuntime().sendEvent(eventOne, "AEvent");
        epService.getEPRuntime().sendEvent(eventTwo, "BEvent");

        EventBean event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, "a.id,b.id".split(","), new Object[] {"A1", "B1"});

        event = listenerInsertInto.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, "a,b".split(","), new Object[] {eventOne, eventTwo});
    }

    private Map<String, Object> makeMap(Object[][] entries)
    {
        Map result = new HashMap<String, Object>();
        for (Object[] entry : entries)
        {
            result.put(entry[0], entry[1]);
        }
        return result;
    }
}
