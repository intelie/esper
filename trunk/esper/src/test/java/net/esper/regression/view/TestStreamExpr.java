package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.soda.EPStatementObjectModel;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.eql.SupportStaticMethodLib;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

import java.util.HashMap;
import java.util.Map;

public class TestStreamExpr extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testInstanceMethodOuterJoin()
    {
        String textOne = "select symbol, s1.getString() as string from " +
                            SupportMarketDataBean.class.getName() + " as s0 " +
                            "left outer join " +
                            SupportBean.class.getName() + " as s1 on s0.symbol=s1.string";

        EPStatement stmtOne = epService.getEPAdministrator().createEQL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        SupportMarketDataBean eventA = new SupportMarketDataBean("ACME", 0, 0L, null);
        epService.getEPRuntime().sendEvent(eventA);
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), new String[] {"symbol", "string"}, new Object[] {"ACME", null});
    }

    public void testInstanceMethodStatic()
    {
        String textOne = "select symbol, s1.getSimpleProperty() as simpleprop, s1.makeDefaultBean() as def from " +
                            SupportMarketDataBean.class.getName() + " as s0 " +
                            "left outer join " +
                            SupportBeanComplexProps.class.getName() + " as s1 on s0.symbol=s1.simpleProperty";

        EPStatement stmtOne = epService.getEPAdministrator().createEQL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        SupportMarketDataBean eventA = new SupportMarketDataBean("ACME", 0, 0L, null);
        epService.getEPRuntime().sendEvent(eventA);
        EventBean event = listenerOne.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, new String[] {"symbol", "simpleprop"}, new Object[] {"ACME", null});
        assertNull(event.get("def"));

        SupportBeanComplexProps eventComplexProps = SupportBeanComplexProps.makeDefaultBean();
        eventComplexProps.setSimpleProperty("ACME");
        epService.getEPRuntime().sendEvent(eventComplexProps);
        event = listenerOne.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, new String[] {"symbol", "simpleprop"}, new Object[] {"ACME", "ACME"});
        assertNotNull(event.get("def"));
    }

    public void testStreamInstanceMethodAliased()
    {
        String textOne = "select s0.getVolume() as volume, s0.getSymbol() as symbol, s0.getPriceTimesVolume(2) as pvf from " +
                            SupportMarketDataBean.class.getName() + " as s0 ";

        EPStatement stmtOne = epService.getEPAdministrator().createEQL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        EventType type = stmtOne.getEventType();
        assertEquals(3, type.getPropertyNames().length);
        assertEquals(Map.class, type.getUnderlyingType());
        assertEquals(Long.class, type.getPropertyType("volume"));
        assertEquals(String.class, type.getPropertyType("symbol"));
        assertEquals(double.class, type.getPropertyType("pvf"));

        SupportMarketDataBean eventA = new SupportMarketDataBean("ACME", 4, 99L, null);
        epService.getEPRuntime().sendEvent(eventA);
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), new String[] {"volume", "symbol", "pvf"}, new Object[] {99L, "ACME", 4d * 99L * 2});
    }

    public void testStreamInstanceMethodNoAlias()
    {
        String textOne = "select s0.getVolume(), s0.getPriceTimesVolume(3) from " +
                            SupportMarketDataBean.class.getName() + " as s0 ";

        EPStatement stmtOne = epService.getEPAdministrator().createEQL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        EventType type = stmtOne.getEventType();
        assertEquals(2, type.getPropertyNames().length);
        assertEquals(Map.class, type.getUnderlyingType());
        assertEquals(Long.class, type.getPropertyType("s0.getVolume()"));
        assertEquals(double.class, type.getPropertyType("s0.getPriceTimesVolume(3)"));

        SupportMarketDataBean eventA = new SupportMarketDataBean("ACME", 4, 2L, null);
        epService.getEPRuntime().sendEvent(eventA);
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), new String[] {"s0.getVolume()", "s0.getPriceTimesVolume(3)"}, new Object[] {2L, 4d * 2L * 3d});
    }

    public void testJoinStreamSelectNoWildcard()
    {
        // try with alias
        String textOne = "select s0 as s0stream, s1 as s1stream from " +
                            SupportMarketDataBean.class.getName() + " as s0, " +
                            SupportBean.class.getName() + " as s1";

        // Attach listener to feed
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(textOne);
        EPStatementObjectModel model = epService.getEPAdministrator().compileEQL(stmtOne.getText());
        assertEquals(textOne, model.toEQL());
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        EventType type = stmtOne.getEventType();
        assertEquals(2, type.getPropertyNames().length);
        assertEquals(Map.class, type.getUnderlyingType());
        assertEquals(SupportMarketDataBean.class, type.getPropertyType("s0stream"));
        assertEquals(SupportBean.class, type.getPropertyType("s1stream"));

        SupportMarketDataBean eventA = new SupportMarketDataBean("ACME", 0, 0L, null);
        epService.getEPRuntime().sendEvent(eventA);

        SupportBean eventB = new SupportBean();
        epService.getEPRuntime().sendEvent(eventB);
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), new String[] {"s0stream", "s1stream"}, new Object[] {eventA, eventB});

        stmtOne.destroy();

        // try no alias
        textOne = "select s0, s1 from " +
                            SupportMarketDataBean.class.getName() + " as s0, " +
                            SupportBean.class.getName() + " as s1";

        // Attach listener to feed
        stmtOne = epService.getEPAdministrator().createEQL(textOne);
        stmtOne.addListener(listenerOne);

        type = stmtOne.getEventType();
        assertEquals(2, type.getPropertyNames().length);
        assertEquals(Map.class, type.getUnderlyingType());
        assertEquals(SupportMarketDataBean.class, type.getPropertyType("s0"));
        assertEquals(SupportBean.class, type.getPropertyType("s1"));

        epService.getEPRuntime().sendEvent(eventA);
        epService.getEPRuntime().sendEvent(eventB);
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), new String[] {"s0", "s1"}, new Object[] {eventA, eventB});
    }

    public void testPatternStreamSelectNoWildcard()
    {
        // try with alias
        String textOne = "select * from pattern [every e1=" + SupportMarketDataBean.class.getName() + " -> e2=" +
                            SupportBean.class.getName() + "(" + SupportStaticMethodLib.class.getName() + ".compareEvents(e1, e2))]";

        // Attach listener to feed
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        SupportMarketDataBean eventA = new SupportMarketDataBean("ACME", 0, 0L, null);
        epService.getEPRuntime().sendEvent(eventA);

        SupportBean eventB = new SupportBean("ACME", 1);
        epService.getEPRuntime().sendEvent(eventB);
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), new String[] {"e1", "e2"}, new Object[] {eventA, eventB});

        stmtOne.destroy();
    }

    public void testStreamSelectConversionFunctionObject()
    {
        String textOne = "insert into EventStream select * from " + SupportBean.class.getName() + ".win:length(100)";
        String textTwo = "insert into EventStream select " + SupportStaticMethodLib.class.getName() + ".convertEvent(s0) from " + SupportMarketDataBean.class.getName() + ".win:length(100) as s0";

        // Attach listener to feed
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);
        EventType type = stmtOne.getEventType();
        assertEquals(SupportBean.class, type.getUnderlyingType());

        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(textTwo);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);
        type = stmtTwo.getEventType();
        assertEquals(SupportBean.class, type.getUnderlyingType());

        // send event for joins to match on
        SupportMarketDataBean eventA = new SupportMarketDataBean("ACME", 0, 0L, null);
        epService.getEPRuntime().sendEvent(eventA);
        EventBean event = listenerTwo.assertOneGetNewAndReset();
        assertTrue (event.getUnderlying() instanceof SupportBean);
        assertEquals("ACME", event.get("string"));
    }

    public void testStreamSelectConversionFunctionMap()
    {
        // try the same with a map
        Map<String, Class> types = new HashMap<String, Class>();
        types.put("one", String.class);
        types.put("two", String.class);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MapOne", types);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MapTwo", types);

        String textOne = "insert into Stream0 select * from MapOne";
        String textTwo = "insert into Stream0 select " + SupportStaticMethodLib.class.getName() + ".convertEventMap(s0) from MapTwo as s0";

        // Attach listener to feed
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);
        EventType type = stmtOne.getEventType();
        assertEquals(Map.class, type.getUnderlyingType());

        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(textTwo);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);
        type = stmtTwo.getEventType();
        assertEquals(Map.class, type.getUnderlyingType());

        Map<String, Object> values = new HashMap<String, Object>();
        values.put("one", "1");
        values.put("two", "2");
        epService.getEPRuntime().sendEvent(values, "MapTwo");

        EventBean event = listenerTwo.assertOneGetNewAndReset();
        assertTrue (event.getUnderlying() instanceof Map);
        assertEquals("1", event.get("one"));
        assertEquals("|2|", event.get("two"));
    }

    public void testInvalidSelect()
    {
        tryInvalid("select s0.abc() from " + SupportBean.class.getName() + " as s0",
                   "Error starting view: Could not find a method named 'abc' in class 'net.esper.support.bean.SupportBean' and matching the required parameter types [select s0.abc() from net.esper.support.bean.SupportBean as s0]");

        tryInvalid("select s0.getString(1,2,3) from " + SupportBean.class.getName() + " as s0", null);
    }

    private void tryInvalid(String clause, String message)
    {
        try
        {
            epService.getEPAdministrator().createEQL(clause);
            fail();
        }
        catch (EPStatementException ex)
        {
            if (message != null)
            {
                assertEquals(message, ex.getMessage());
            }
        }
    }

    private SupportMarketDataBean sendMarketEvent(String s)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(s, 0d, 0L, "");
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
