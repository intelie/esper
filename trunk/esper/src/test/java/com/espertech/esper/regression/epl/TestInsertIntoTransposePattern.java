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
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.client.EventBean;

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
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        listener = new SupportUpdateListener();
        listenerInsertInto = new SupportUpdateListener();
    }

    public void testThisAsColumn()
    {
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("SupportBean", SupportBean.class);

        EPStatement stmt = epService.getEPAdministrator().createEPL("create window OneWindow.win:time(1 day) as select string as alertId, this from SupportBean");
        epService.getEPAdministrator().createEPL("insert into OneWindow select '1' as alertId, stream0.quote.this as this " +
                " from pattern [every quote=SupportBean(string='A')] as stream0");
        epService.getEPAdministrator().createEPL("insert into OneWindow select '2' as alertId, stream0.quote as this " +
                " from pattern [every quote=SupportBean(string='B')] as stream0");

        epService.getEPRuntime().sendEvent(new SupportBean("A", 10));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), new String[] {"alertId", "this.intPrimitive"}, new Object[][] {{"1", 10}});

        epService.getEPRuntime().sendEvent(new SupportBean("B", 20));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), new String[] {"alertId", "this.intPrimitive"}, new Object[][] {{"1", 10}, {"2", 20}});

        stmt = epService.getEPAdministrator().createEPL("create window TwoWindow.win:time(1 day) as select string as alertId, * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into TwoWindow select '3' as alertId, quote.* " +
                " from pattern [every quote=SupportBean(string='C')] as stream0");

        epService.getEPRuntime().sendEvent(new SupportBean("C", 30));
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), new String[] {"alertId", "intPrimitive"}, new Object[][] {{"3", 30}});
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

        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("AEvent", type);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("BEvent", type);

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
