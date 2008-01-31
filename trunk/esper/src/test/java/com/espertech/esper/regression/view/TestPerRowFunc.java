package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.SerializableObjectCopier;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestPerRowFunc extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testCoalesceBeans()
    {
        tryCoalesceBeans("select coalesce(a.string, b.string) as myString, coalesce(a, b) as myBean" +
                          " from pattern [every (a=" + SupportBean.class.getName() + "(string='s0') or b=" + SupportBean.class.getName() + "(string='s1'))]");

        tryCoalesceBeans("SELECT COALESCE(a.string, b.string) AS myString, COALESCE(a, b) AS myBean" +
                          " FROM PATTERN [EVERY (a=" + SupportBean.class.getName() + "(string='s0') OR b=" + SupportBean.class.getName() + "(string='s1'))]");
    }

    private void tryCoalesceBeans(String viewExpr)
    {
        epService.initialize();
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        SupportBean event = sendEvent("s0");
        EventBean eventReceived = testListener.assertOneGetNewAndReset();
        assertEquals("s0", eventReceived.get("myString"));
        assertSame(event, eventReceived.get("myBean"));

        event = sendEvent("s1");
        eventReceived = testListener.assertOneGetNewAndReset();
        assertEquals("s1", eventReceived.get("myString"));
        assertSame(event, eventReceived.get("myBean"));
    }

    public void testCoalesceLong()
    {
        setupCoalesce("coalesce(longBoxed, intBoxed, shortBoxed)");
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("result"));

        sendEvent(1L, 2, (short) 3);
        assertEquals(1L, testListener.assertOneGetNewAndReset().get("result"));

        sendBoxedEvent(null, 2, null);
        assertEquals(2L, testListener.assertOneGetNewAndReset().get("result"));

        sendBoxedEvent(null, null, Short.parseShort("3"));
        assertEquals(3L, testListener.assertOneGetNewAndReset().get("result"));

        sendBoxedEvent(null, null, null);
        assertEquals(null, testListener.assertOneGetNewAndReset().get("result"));
    }

    public void testCoalesceLong_OM() throws Exception
    {
        String viewExpr = "select coalesce(longBoxed, intBoxed, shortBoxed) as result" +
                          " from " + SupportBean.class.getName() + ".win:length(1000)";

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create().add(Expressions.coalesce(
                "longBoxed", "intBoxed", "shortBoxed"), "result"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName()).addView("win", "length", 1000)));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(viewExpr, model.toEQL());

        epService.initialize();
        selectTestView = epService.getEPAdministrator().create(model);
        selectTestView.addListener(testListener);
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("result"));

        runCoalesceLong();
    }

    public void testCoalesceLong_Compile()
    {
        String viewExpr = "select coalesce(longBoxed, intBoxed, shortBoxed) as result" +
                          " from " + SupportBean.class.getName() + ".win:length(1000)";

        EPStatementObjectModel model = epService.getEPAdministrator().compileEQL(viewExpr);
        assertEquals(viewExpr, model.toEQL());

        epService.initialize();
        selectTestView = epService.getEPAdministrator().create(model);
        selectTestView.addListener(testListener);
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("result"));

        runCoalesceLong();
    }

    private void runCoalesceLong()
    {
        sendEvent(1L, 2, (short) 3);
        assertEquals(1L, testListener.assertOneGetNewAndReset().get("result"));

        sendBoxedEvent(null, 2, null);
        assertEquals(2L, testListener.assertOneGetNewAndReset().get("result"));

        sendBoxedEvent(null, null, Short.parseShort("3"));
        assertEquals(3L, testListener.assertOneGetNewAndReset().get("result"));

        sendBoxedEvent(null, null, null);
        assertEquals(null, testListener.assertOneGetNewAndReset().get("result"));
    }

    public void testCoalesceDouble()
    {
        setupCoalesce("coalesce(null, byteBoxed, shortBoxed, intBoxed, longBoxed, floatBoxed, doubleBoxed)");
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("result"));

        sendEventWithDouble(null, null, null, null, null, null);
        assertEquals(null, testListener.assertOneGetNewAndReset().get("result"));

        sendEventWithDouble(null, Short.parseShort("2"), null, null, null, 1d);
        assertEquals(2d, testListener.assertOneGetNewAndReset().get("result"));

        sendEventWithDouble(null, null, null, null, null, 100d);
        assertEquals(100d, testListener.assertOneGetNewAndReset().get("result"));

        sendEventWithDouble(null, null, null, null, 10f, 100d);
        assertEquals(10d, testListener.assertOneGetNewAndReset().get("result"));

        sendEventWithDouble(null, null, 1, 5l, 10f, 100d);
        assertEquals(1d, testListener.assertOneGetNewAndReset().get("result"));

        sendEventWithDouble(Byte.parseByte("3"), null, null, null, null, null);
        assertEquals(3d, testListener.assertOneGetNewAndReset().get("result"));

        sendEventWithDouble(null, null, null, 5l, 10f, 100d);
        assertEquals(5d, testListener.assertOneGetNewAndReset().get("result"));
    }

    private void setupCoalesce(String coalesceExpr)
    {
        epService.initialize();
        String viewExpr = "select " + coalesceExpr + " as result" +
                          " from " + SupportBean.class.getName() + ".win:length(1000) ";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);
    }

    public void testCoalesceInvalid()
    {
        String viewExpr = "select coalesce(null, null) as result" +
                          " from " + SupportBean.class.getName() + ".win:length(3) ";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        assertEquals(null, selectTestView.getEventType().getPropertyType("result"));

        tryCoalesceInvalid("coalesce(intPrimitive)");
        tryCoalesceInvalid("coalesce(intPrimitive, string)");
        tryCoalesceInvalid("coalesce(intPrimitive, xxx)");
        tryCoalesceInvalid("coalesce(intPrimitive, booleanBoxed)");
        tryCoalesceInvalid("coalesce(charPrimitive, longBoxed)");
        tryCoalesceInvalid("coalesce(charPrimitive, string, string)");
        tryCoalesceInvalid("coalesce(string, longBoxed)");
        tryCoalesceInvalid("coalesce(null, longBoxed, string)");
        tryCoalesceInvalid("coalesce(null, null, boolBoxed, 1l)");
    }

    private void tryCoalesceInvalid(String coalesceExpr)
    {
        String viewExpr = "select " + coalesceExpr + " as result" +
                          " from " + SupportBean.class.getName() + ".win:length(3) ";

        try {
            selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        }
        catch (EPStatementException ex)
        {
            // expected
        }
    }

    public void testMinMaxEventType()
    {
        setUpMinMax();
        EventType type = selectTestView.getEventType();
        log.debug(".testGetEventType properties=" + Arrays.toString(type.getPropertyNames()));
        assertEquals(Long.class, type.getPropertyType("myMax"));
        assertEquals(Long.class, type.getPropertyType("myMin"));
        assertEquals(Long.class, type.getPropertyType("myMinEx"));
        assertEquals(Long.class, type.getPropertyType("myMaxEx"));
    }

    public void testMinMaxWindowStats()
    {
        setUpMinMax();
        testListener.reset();
        runMinMaxWindowStats();
    }

    public void testMinMaxWindowStats_OM() throws Exception
    {
        String viewExpr = "select max(longBoxed, intBoxed) as myMax, " +
                                 "max(longBoxed, intBoxed, shortBoxed) as myMaxEx, " +
                                 "min(longBoxed, intBoxed) as myMin, " +
                                 "min(longBoxed, intBoxed, shortBoxed) as myMinEx" +
                          " from " + SupportBean.class.getName() + ".win:length(3)";

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create()
                .add(Expressions.max("longBoxed", "intBoxed"), "myMax")
                .add(Expressions.max(Expressions.property("longBoxed"), Expressions.property("intBoxed"), Expressions.property("shortBoxed")), "myMaxEx")
                .add(Expressions.min("longBoxed", "intBoxed"), "myMin")
                .add(Expressions.min(Expressions.property("longBoxed"), Expressions.property("intBoxed"), Expressions.property("shortBoxed")), "myMinEx")
                );
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName()).addView("win", "length", 3)));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(viewExpr, model.toEQL());

        selectTestView = epService.getEPAdministrator().create(model);
        selectTestView.addListener(testListener);
        testListener.reset();

        runMinMaxWindowStats();
    }

    public void testMinMaxWindowStats_Compile() throws Exception
    {
        String viewExpr = "select max(longBoxed, intBoxed) as myMax, " +
                                 "max(longBoxed, intBoxed, shortBoxed) as myMaxEx, " +
                                 "min(longBoxed, intBoxed) as myMin, " +
                                 "min(longBoxed, intBoxed, shortBoxed) as myMinEx" +
                          " from " + SupportBean.class.getName() + ".win:length(3)";

        EPStatementObjectModel model = epService.getEPAdministrator().compileEQL(viewExpr);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(viewExpr, model.toEQL());

        selectTestView = epService.getEPAdministrator().create(model);
        selectTestView.addListener(testListener);
        testListener.reset();

        runMinMaxWindowStats();
    }

    private void runMinMaxWindowStats()
    {
        sendEvent(10, 20, (short)4);
        EventBean received = testListener.getAndResetLastNewData()[0];
        assertEquals(20L, received.get("myMax"));
        assertEquals(10L, received.get("myMin"));
        assertEquals(4L, received.get("myMinEx"));
        assertEquals(20L, received.get("myMaxEx"));

        sendEvent(-10, -20, (short)-30);
        received = testListener.getAndResetLastNewData()[0];
        assertEquals(-10L, received.get("myMax"));
        assertEquals(-20L, received.get("myMin"));
        assertEquals(-30L, received.get("myMinEx"));
        assertEquals(-10L, received.get("myMaxEx"));
    }

    public void testOperators()
    {
        String viewExpr = "select longBoxed % intBoxed as myMod " +
                          " from " + SupportBean.class.getName() + ".win:length(3) where not(longBoxed > intBoxed)";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        sendEvent(1, 1, (short)0);
        assertEquals(0l, testListener.getLastNewData()[0].get("myMod"));
        testListener.reset();

        sendEvent(2, 1, (short)0);
        assertFalse(testListener.getAndClearIsInvoked());

        sendEvent(2, 3, (short)0);
        assertEquals(2l, testListener.getLastNewData()[0].get("myMod"));
        testListener.reset();
    }

    public void testConcat()
    {
        String viewExpr = "select p00 || p01 as c1, p00 || p01 || p02 as c2, p00 || '|' || p01 as c3" +
                          " from " + SupportBean_S0.class.getName() + ".win:length(10)";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "a", "b", "c"));
        assertConcat("ab", "abc", "a|b");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, null, "b", "c"));
        assertConcat(null, null, null);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "", "b", "c"));
        assertConcat("b", "bc", "|b");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "123", null, "c"));
        assertConcat(null, null, null);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "123", "456", "c"));
        assertConcat("123456", "123456c", "123|456");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "123", "456", null));
        assertConcat("123456", null, "123|456");
    }

    private void setUpMinMax()
    {
        String viewExpr = "select max(longBoxed, intBoxed) as myMax, " +
                                 "max(longBoxed, intBoxed, shortBoxed) as myMaxEx," +
                                 "min(longBoxed, intBoxed) as myMin," +
                                 "min(longBoxed, intBoxed, shortBoxed) as myMinEx" +
                          " from " + SupportBean.class.getName() + ".win:length(3) ";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);
    }

    private SupportBean sendEvent(String string)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendEvent(long longBoxed, int intBoxed, short shortBoxed)
    {
        sendBoxedEvent(longBoxed, intBoxed, shortBoxed);
    }

    private void sendBoxedEvent(Long longBoxed, Integer intBoxed, Short shortBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setLongBoxed(longBoxed);
        bean.setIntBoxed(intBoxed);
        bean.setShortBoxed(shortBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEventWithDouble(Byte byteBoxed, Short shortBoxed, Integer intBoxed, Long longBoxed, Float floatBoxed, Double doubleBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setByteBoxed(byteBoxed);
        bean.setShortBoxed(shortBoxed);
        bean.setIntBoxed(intBoxed);
        bean.setLongBoxed(longBoxed);
        bean.setFloatBoxed(floatBoxed);
        bean.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void assertConcat(String c1, String c2, String c3)
    {
        EventBean event = testListener.getLastNewData()[0];
        assertEquals(c1, event.get("c1"));
        assertEquals(c2, event.get("c2"));
        assertEquals(c3, event.get("c3"));
        testListener.reset();
    }

    private static final Log log = LogFactory.getLog(TestSelectExpr.class);
}
