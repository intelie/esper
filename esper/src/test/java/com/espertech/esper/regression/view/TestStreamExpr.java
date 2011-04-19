package com.espertech.esper.regression.view;

import com.espertech.esper.support.bean.*;
import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportStaticMethodLib;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

import java.util.HashMap;
import java.util.Map;

public class TestStreamExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testChainedParameterized() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportChainTop", SupportChainTop.class);

        String subexpr="top.getChildOne(\"abc\", 10).getChildTwo(\"append\")";
        String epl = "select " +
                subexpr +
                " from SupportChainTop as top";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertionChainedParam(stmt, subexpr);

        listener.reset();
        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        assertEquals(epl, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);

        runAssertionChainedParam(stmt, subexpr);

        // test property hosts a method
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanStaticOuter", SupportBeanStaticOuter.class);
        stmt = epService.getEPAdministrator().createEPL("select inside.getMyString() as val," +
                "inside.insideTwo.getMyOtherString() as val2 " +
                "from SupportBeanStaticOuter");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanStaticOuter());
        EventBean result = listener.assertOneGetNewAndReset();
        assertEquals("hello", result.get("val"));
        assertEquals("hello2", result.get("val2"));
    }

    private void runAssertionChainedParam(EPStatement stmt, String subexpr) {

        Object[][] rows = new Object[][] {
                {subexpr, SupportChainChildTwo.class}
                };
        for (int i = 0; i < rows.length; i++) {
            EventPropertyDescriptor prop = stmt.getEventType().getPropertyDescriptors()[i];
            assertEquals(rows[i][0], prop.getPropertyName());
            assertEquals(rows[i][1], prop.getPropertyType());
        }

        epService.getEPRuntime().sendEvent(new SupportChainTop());
        Object result = listener.assertOneGetNewAndReset().get(subexpr);
        assertEquals("abcappend", ((SupportChainChildTwo)result).getText());
    }

    public void testStreamFunction()
    {
        String textOne = "select * from " + SupportMarketDataBean.class.getName() + " as s0 where " +
                SupportStaticMethodLib.class.getName() + ".volumeGreaterZero(s0)";

        EPStatement stmtOne = epService.getEPAdministrator().createEPL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ACME", 0, 0L, null));
        assertFalse(listenerOne.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("ACME", 0, 100L, null));
        assertTrue(listenerOne.isInvoked());
    }

    public void testInstanceMethodOuterJoin()
    {
        String textOne = "select symbol, s1.getString() as string from " +
                            SupportMarketDataBean.class.getName() + ".win:keepall() as s0 " +
                            "left outer join " +
                            SupportBean.class.getName() + ".win:keepall() as s1 on s0.symbol=s1.string";

        EPStatement stmtOne = epService.getEPAdministrator().createEPL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);

        SupportMarketDataBean eventA = new SupportMarketDataBean("ACME", 0, 0L, null);
        epService.getEPRuntime().sendEvent(eventA);
        ArrayAssertionUtil.assertProps(listenerOne.assertOneGetNewAndReset(), new String[] {"symbol", "string"}, new Object[] {"ACME", null});
    }

    public void testInstanceMethodStatic()
    {
        String textOne = "select symbol, s1.getSimpleProperty() as simpleprop, s1.makeDefaultBean() as def from " +
                            SupportMarketDataBean.class.getName() + ".win:keepall() as s0 " +
                            "left outer join " +
                            SupportBeanComplexProps.class.getName() + ".win:keepall() as s1 on s0.symbol=s1.simpleProperty";

        EPStatement stmtOne = epService.getEPAdministrator().createEPL(textOne);
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

        EPStatement stmtOne = epService.getEPAdministrator().createEPL(textOne);
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

        EPStatement stmtOne = epService.getEPAdministrator().createEPL(textOne);
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
                            SupportMarketDataBean.class.getName() + ".win:keepall() as s0, " +
                            SupportBean.class.getName() + ".win:keepall() as s1";

        // Attach listener to feed
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(textOne);
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtOne.getText());
        assertEquals(textOne, model.toEPL());
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
                            SupportMarketDataBean.class.getName() + ".win:keepall() as s0, " +
                            SupportBean.class.getName() + ".win:keepall() as s1";

        // Attach listener to feed
        stmtOne = epService.getEPAdministrator().createEPL(textOne);
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
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(textOne);
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
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);
        EventType type = stmtOne.getEventType();
        assertEquals(SupportBean.class, type.getUnderlyingType());

        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(textTwo);
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
        Map<String, Object> types = new HashMap<String, Object>();
        types.put("one", String.class);
        types.put("two", String.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MapOne", types);
        epService.getEPAdministrator().getConfiguration().addEventType("MapTwo", types);

        String textOne = "insert into Stream0 select * from MapOne";
        String textTwo = "insert into Stream0 select " + SupportStaticMethodLib.class.getName() + ".convertEventMap(s0) from MapTwo as s0";

        // Attach listener to feed
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(textOne);
        SupportUpdateListener listenerOne = new SupportUpdateListener();
        stmtOne.addListener(listenerOne);
        EventType type = stmtOne.getEventType();
        assertEquals(Map.class, type.getUnderlyingType());

        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(textTwo);
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
        tryInvalid("select s0.getString(1,2,3) from " + SupportBean.class.getName() + " as s0", null);

        tryInvalid("select s0.abc() from " + SupportBean.class.getName() + " as s0",
                   "Error starting statement: Could not find enumeration method, date-time method or instance method named 'abc' in class 'com.espertech.esper.support.bean.SupportBean' taking no parameters [select s0.abc() from com.espertech.esper.support.bean.SupportBean as s0]");
    }

    private void tryInvalid(String clause, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(clause);
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
