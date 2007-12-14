package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementException;
import net.esper.client.soda.EPStatementObjectModel;
import net.esper.client.soda.SelectClause;
import net.esper.client.soda.FromClause;
import net.esper.client.soda.FilterStream;
import net.esper.client.time.TimerControlEvent;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.eql.SupportStaticMethodLib;

import java.util.Map;
import java.util.HashMap;

public class TestSelectExprStreamSelector extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
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

    public void testInsertFromPattern()
    {
    	String stmtOneText = "insert into streamA select a.* from pattern [every a=" + SupportBean.class.getName() + "]";
    	SupportUpdateListener listenerOne = new SupportUpdateListener();
    	EPStatement stmtOne = epService.getEPAdministrator().createEQL(stmtOneText);
        stmtOne.addListener(listenerOne);

        String stmtTwoText = "insert into streamA select a.* from pattern [every a=" + SupportBean.class.getName() + " where timer:within(30 sec)]";
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(stmtTwoText);
        stmtTwo.addListener(listenerTwo);

        EventType eventType = stmtOne.getEventType();
        assertEquals(SupportBean.class, eventType.getUnderlyingType());

        Object event = sendBeanEvent("E1", 10);
        assertSame(event, listenerTwo.assertOneGetNewAndReset().getUnderlying());

        event = sendBeanEvent("E2", 10);
        assertSame(event, listenerTwo.assertOneGetNewAndReset().getUnderlying());

        String stmtThreeText = "insert into streamB select a.*, 'abc' as abc from pattern [every a=" + SupportBean.class.getName() + " where timer:within(30 sec)]";
        EPStatement stmtThree = epService.getEPAdministrator().createEQL(stmtThreeText);
        assertEquals(Pair.class, stmtThree.getEventType().getUnderlyingType());
        assertEquals(String.class, stmtThree.getEventType().getPropertyType("abc"));
        assertEquals(String.class, stmtThree.getEventType().getPropertyType("string"));
    }

    public void testObjectModelJoinAlias()
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create()
                .addStreamWildcard("s0")
                .addStreamWildcard("s1", "s1stream")
                .addWithAlias("string", "sym"));
        model.setFromClause(FromClause.create()
                .add(FilterStream.create(SupportBean.class.getName(), "s0"))
                .add(FilterStream.create(SupportMarketDataBean.class.getName(), "s1")));

        selectTestView = epService.getEPAdministrator().create(model);
        selectTestView.addListener(testListener);

        String viewExpr = "select s0.*, s1.* as s1stream, string as sym from " + SupportBean.class.getName() + " as s0, " +
                SupportMarketDataBean.class.getName() + " as s1";
        assertEquals(viewExpr, model.toEQL());
        EPStatementObjectModel modelReverse = epService.getEPAdministrator().compileEQL(model.toEQL());
        assertEquals(viewExpr, modelReverse.toEQL());

        EventType type = selectTestView.getEventType();
        assertEquals(SupportMarketDataBean.class, type.getPropertyType("s1stream"));
        assertEquals(Pair.class, type.getUnderlyingType());

        sendBeanEvent("E1");
        assertFalse(testListener.isInvoked());

        Object event = sendMarketEvent("E1");
        assertSame(event, testListener.assertOneGetNewAndReset().get("s1stream"));
    }

    public void testNoJoinWildcardNoAlias()
    {
        String viewExpr = "select *, win.* from " + SupportBean.class.getName() + ".win:length(3) as win";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertTrue(type.getPropertyNames().length > 15);
        assertEquals(SupportBean.class, type.getUnderlyingType());

        Object event = sendBeanEvent("E1", 16);
        assertSame(event, testListener.assertOneGetNewAndReset().getUnderlying());
    }

    public void testJoinWildcardNoAlias()
    {
        String viewExpr = "select *, s1.* from " + SupportBean.class.getName() + ".win:length(3) as s0, " +
                SupportMarketDataBean.class.getName() + " as s1";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertEquals(7, type.getPropertyNames().length);
        assertEquals(Long.class, type.getPropertyType("volume"));
        assertEquals(SupportBean.class, type.getPropertyType("s0"));
        assertEquals(SupportMarketDataBean.class, type.getPropertyType("s1"));
        assertEquals(Pair.class, type.getUnderlyingType());

        Object eventOne = sendBeanEvent("E1", 13);
        assertFalse(testListener.isInvoked());

        Object eventTwo = sendMarketEvent("E2");
        String[] fields = new String[] {"s0", "s1", "symbol", "volume"};
        EventBean received = testListener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {eventOne, eventTwo, "E2", 0L});
    }

    public void testNoJoinWildcardWithAlias()
    {
        String viewExpr = "select *, win.* as s0 from " + SupportBean.class.getName() + ".win:length(3) as win";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertTrue(type.getPropertyNames().length > 15);
        assertEquals(Pair.class, type.getUnderlyingType());
        assertEquals(SupportBean.class, type.getPropertyType("s0"));

        Object event = sendBeanEvent("E1", 15);
        String[] fields = new String[] {"string", "intPrimitive", "s0"};
        ArrayAssertionUtil.assertProps(testListener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 15, event});
    }

    public void testJoinWildcardWithAlias()
    {
        String viewExpr = "select *, s1.* as s1stream, s0.* as s0stream from " + SupportBean.class.getName() + ".win:length(3) as s0, " +
                SupportMarketDataBean.class.getName() + " as s1";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertEquals(4, type.getPropertyNames().length);
        assertEquals(SupportBean.class, type.getPropertyType("s0stream"));
        assertEquals(SupportBean.class, type.getPropertyType("s0"));
        assertEquals(SupportMarketDataBean.class, type.getPropertyType("s1stream"));
        assertEquals(SupportMarketDataBean.class, type.getPropertyType("s1"));
        assertEquals(Map.class, type.getUnderlyingType());

        Object eventOne = sendBeanEvent("E1", 13);
        assertFalse(testListener.isInvoked());

        Object eventTwo = sendMarketEvent("E2");
        String[] fields = new String[] {"s0", "s1", "s0stream", "s1stream"};
        EventBean received = testListener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {eventOne, eventTwo, eventOne, eventTwo});
    }

    public void testNoJoinWithAliasWithProperties()
    {
        String viewExpr = "select string.* as s0, intPrimitive as a, string.* as s1, intPrimitive as b from " + SupportBean.class.getName() + ".win:length(3) as string";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertEquals(4, type.getPropertyNames().length);
        assertEquals(Map.class, type.getUnderlyingType());
        assertEquals(int.class, type.getPropertyType("a"));
        assertEquals(int.class, type.getPropertyType("b"));
        assertEquals(SupportBean.class, type.getPropertyType("s0"));
        assertEquals(SupportBean.class, type.getPropertyType("s1"));

        Object event = sendBeanEvent("E1", 12);
        String[] fields = new String[] {"s0", "s1", "a", "b"};
        ArrayAssertionUtil.assertProps(testListener.assertOneGetNewAndReset(), fields, new Object[] {event, event, 12, 12});
    }

    public void testJoinWithAliasWithProperties()
    {
        String viewExpr = "select intPrimitive, s1.* as s1stream, string, symbol as sym, s0.* as s0stream from " + SupportBean.class.getName() + ".win:length(3) as s0, " +
                SupportMarketDataBean.class.getName() + " as s1";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertEquals(5, type.getPropertyNames().length);
        assertEquals(int.class, type.getPropertyType("intPrimitive"));
        assertEquals(SupportMarketDataBean.class, type.getPropertyType("s1stream"));
        assertEquals(SupportBean.class, type.getPropertyType("s0stream"));
        assertEquals(String.class, type.getPropertyType("sym"));
        assertEquals(String.class, type.getPropertyType("string"));
        assertEquals(Map.class, type.getUnderlyingType());

        Object eventOne = sendBeanEvent("E1", 13);
        assertFalse(testListener.isInvoked());

        Object eventTwo = sendMarketEvent("E2");
        String[] fields = new String[] {"intPrimitive", "sym", "string", "s0stream", "s1stream"};
        EventBean received = testListener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {13, "E2", "E1", eventOne, eventTwo});
        assertSame(eventOne, ((Map)received.getUnderlying()).get("s0stream"));
    }

    public void testNoJoinNoAliasWithProperties()
    {
        String viewExpr = "select intPrimitive as a, string.*, intPrimitive as b from " + SupportBean.class.getName() + ".win:length(3) as string";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertEquals(20, type.getPropertyNames().length);
        assertEquals(Pair.class, type.getUnderlyingType());
        assertEquals(int.class, type.getPropertyType("a"));
        assertEquals(int.class, type.getPropertyType("b"));
        assertEquals(String.class, type.getPropertyType("string"));

        sendBeanEvent("E1", 10);
        String[] fields = new String[] {"a", "string", "intPrimitive", "b"};
        ArrayAssertionUtil.assertProps(testListener.assertOneGetNewAndReset(), fields, new Object[] {10, "E1", 10, 10});
    }

    public void testJoinNoAliasWithProperties()
    {
        String viewExpr = "select intPrimitive, s1.*, symbol as sym from " + SupportBean.class.getName() + ".win:length(3) as s0, " +
                SupportMarketDataBean.class.getName() + " as s1";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertEquals(7, type.getPropertyNames().length);
        assertEquals(int.class, type.getPropertyType("intPrimitive"));
        assertEquals(Pair.class, type.getUnderlyingType());

        sendBeanEvent("E1", 11);
        assertFalse(testListener.isInvoked());

        Object event = sendMarketEvent("E1");
        String[] fields = new String[] {"intPrimitive", "sym", "symbol"};
        EventBean received = testListener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {11, "E1", "E1"});
        assertSame(event, ((Pair)received.getUnderlying()).getFirst());
    }

    public void testAloneNoJoinNoAlias()
    {
        String viewExpr = "select string.* from " + SupportBean.class.getName() + ".win:length(3) as string";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertTrue(type.getPropertyNames().length > 10);
        assertEquals(SupportBean.class, type.getUnderlyingType());

        Object event = sendBeanEvent("E1");
        assertSame(event, testListener.assertOneGetNewAndReset().getUnderlying());
    }

    public void testAloneNoJoinAlias()
    {
        String viewExpr = "select string.* as s0 from " + SupportBean.class.getName() + ".win:length(3) as string";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertEquals(1, type.getPropertyNames().length);
        assertEquals(SupportBean.class, type.getPropertyType("s0"));
        assertEquals(Map.class, type.getUnderlyingType());

        Object event = sendBeanEvent("E1");
        assertSame(event, testListener.assertOneGetNewAndReset().get("s0"));
    }

    public void testAloneJoinAlias()
    {
        String viewExpr = "select s1.* as s1 from " + SupportBean.class.getName() + ".win:length(3) as s0, " +
                SupportMarketDataBean.class.getName() + " as s1";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertEquals(SupportMarketDataBean.class, type.getPropertyType("s1"));
        assertEquals(Map.class, type.getUnderlyingType());

        sendBeanEvent("E1");
        assertFalse(testListener.isInvoked());

        Object event = sendMarketEvent("E1");
        assertSame(event, testListener.assertOneGetNewAndReset().get("s1"));

        selectTestView.destroy();

        // reverse streams
        viewExpr = "select s0.* as szero from " + SupportBean.class.getName() + ".win:length(3) as s0, " +
                SupportMarketDataBean.class.getName() + " as s1";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        type = selectTestView.getEventType();
        assertEquals(SupportBean.class, type.getPropertyType("szero"));
        assertEquals(Map.class, type.getUnderlyingType());

        sendMarketEvent("E1");
        assertFalse(testListener.isInvoked());

        event = sendBeanEvent("E1");
        assertSame(event, testListener.assertOneGetNewAndReset().get("szero"));
    }

    public void testAloneJoinNoAlias()
    {
        String viewExpr = "select s1.* from " + SupportBean.class.getName() + ".win:length(3) as s0, " +
                SupportMarketDataBean.class.getName() + " as s1";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        EventType type = selectTestView.getEventType();
        assertEquals(Long.class, type.getPropertyType("volume"));
        assertEquals(SupportMarketDataBean.class, type.getUnderlyingType());

        sendBeanEvent("E1");
        assertFalse(testListener.isInvoked());

        Object event = sendMarketEvent("E1");
        assertSame(event, testListener.assertOneGetNewAndReset().getUnderlying());

        selectTestView.destroy();

        // reverse streams
        viewExpr = "select s0.* from " + SupportBean.class.getName() + ".win:length(3) as s0, " +
                SupportMarketDataBean.class.getName() + " as s1";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        type = selectTestView.getEventType();
        assertEquals(String.class, type.getPropertyType("string"));
        assertEquals(SupportBean.class, type.getUnderlyingType());

        sendMarketEvent("E1");
        assertFalse(testListener.isInvoked());

        event = sendBeanEvent("E1");
        assertSame(event, testListener.assertOneGetNewAndReset().getUnderlying());
    }

    public void testInvalidSelect()
    {
        tryInvalid("select string.* as string, string from " + SupportBean.class.getName() + ".win:length(3) as string",
                   "Error starting view: Property alias name 'string' appears more then once in select clause [select string.* as string, string from net.esper.support.bean.SupportBean.win:length(3) as string]");

        tryInvalid("select s1.* as abc from " + SupportBean.class.getName() + ".win:length(3) as s0",
                   "Error starting view: Stream selector 's1.*' does not match any stream alias name in the from clause [select s1.* as abc from net.esper.support.bean.SupportBean.win:length(3) as s0]");

        tryInvalid("select s0.* as abc, s0.* as abc from " + SupportBean.class.getName() + ".win:length(3) as s0",
                   "Error starting view: Property alias name 'abc' appears more then once in select clause [select s0.* as abc, s0.* as abc from net.esper.support.bean.SupportBean.win:length(3) as s0]");

        tryInvalid("select s0.*, s1.* from " + SupportBean.class.getName() + " as s0, " + SupportBean.class.getName() + " as s1",
                   "Error starting view: A column alias must be supplied for all but one stream if multiple streams are selected via the stream.* notation [select s0.*, s1.* from net.esper.support.bean.SupportBean as s0, net.esper.support.bean.SupportBean as s1]");
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
            assertEquals(message, ex.getMessage());
        }
    }

    private SupportBean sendBeanEvent(String s)
    {
        SupportBean bean = new SupportBean();
        bean.setString(s);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendBeanEvent(String s, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(s);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportMarketDataBean sendMarketEvent(String s)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(s, 0d, 0L, "");
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
