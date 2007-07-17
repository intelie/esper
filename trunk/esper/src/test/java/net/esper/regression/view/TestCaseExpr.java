package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.event.EventBean;

import net.esper.support.bean.SupportEnum;
import net.esper.support.bean.SupportBeanWithEnum;
import net.esper.support.client.SupportConfigFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestCaseExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testCaseSyntax1Sum()
    {
        // Testing the two forms of the case expression
        // Furthermore the test checks the different when clauses and actions related.
        String caseExpr = "select case " +
              " when symbol='GE' then volume " +
              " when symbol='DELL' then sum(price) " +
              "end as p1 from " +   SupportMarketDataBean.class.getName() + ".win:length(10)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Double.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendMarketDataEvent("DELL", 10000, 50);
        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals(50.0, event.get("p1"));

        sendMarketDataEvent("DELL", 10000, 50);
        event = testListener.assertOneGetNewAndReset();
        assertEquals(100.0, event.get("p1"));

        sendMarketDataEvent("CSCO", 4000, 5);
        event = testListener.assertOneGetNewAndReset();
        assertEquals(null,event.get("p1"));

        sendMarketDataEvent("GE", 20, 30);
        event = testListener.assertOneGetNewAndReset();
        assertEquals(20.0, event.get("p1"));
    }

    public void testCaseSyntax1WithElse()
    {
        // Adding to the EQL statement an else expression
        // when a CSCO ticker is sent the property for the else expression is selected
        String caseExpr = "select case " +
              " when symbol='DELL' then 3 * volume " +
              " else volume " +
              "end as p1 from " + SupportMarketDataBean.class.getName() + ".win:length(3)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Long.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendMarketDataEvent("CSCO", 4000, 0);
        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals(4000l,event.get("p1"));

        sendMarketDataEvent("DELL", 20, 0);
        event = testListener.assertOneGetNewAndReset();
        assertEquals(3 * 20L, event.get("p1"));
    }

    public void testCaseSyntax1Branches3()
    {
        // Same test but the where clause doesn't match any of the condition of the case expresssion
        String caseExpr = "select case " +
            " when (symbol='GE') then volume " +
            " when (symbol='DELL') then volume / 2.0 " +
            " when (symbol='MSFT') then volume / 3.0 " +
            " end as p1 from " +   SupportMarketDataBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Double.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendMarketDataEvent("DELL", 10000, 0);
        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals(10000 / 2.0,event.get("p1"));

        sendMarketDataEvent("MSFT", 10000, 0);
        event = testListener.assertOneGetNewAndReset();
        assertEquals(10000 / 3.0,event.get("p1"));

        sendMarketDataEvent("GE", 10000, 0);
        event = testListener.assertOneGetNewAndReset();
        assertEquals(10000.0,event.get("p1"));
    }

    public void testCaseSyntax2()
    {
        String caseExpr = "select case intPrimitive " +
                " when longPrimitive then (intPrimitive + longPrimitive) " +
                " when doublePrimitive then intPrimitive * doublePrimitive" +
                " when floatPrimitive then floatPrimitive / doublePrimitive " +
                " else (intPrimitive + longPrimitive + floatPrimitive + doublePrimitive) end as p1 " +
                " from " + SupportBean.class.getName() + ".win:length(10)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Double.class, selectTestCase.getEventType().getPropertyType("p1"));

        // intPrimitive = longPrimitive
        // case result is intPrimitive + longPrimitive
        sendSupportBeanEvent(2, 2L, 1.0f, 1.0);
        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals(4.0, event.get("p1"));
        // intPrimitive = doublePrimitive
        // case result is intPrimitive * doublePrimitive
        sendSupportBeanEvent(5, 1L, 1.0f, 5.0);
        event = testListener.assertOneGetNewAndReset();
        assertEquals(25.0, event.get("p1"));
        // intPrimitive = floatPrimitive
        // case result is floatPrimitive / doublePrimitive
        sendSupportBeanEvent(12, 1L, 12.0f, 4.0);
        event = testListener.assertOneGetNewAndReset();
        assertEquals(3.0, event.get("p1"));
        // all the properties of the event are different
        // The else part is computed: 1+2+3+4 = 10
        sendSupportBeanEvent(1, 2L, 3.0f, 4.0);
        event = testListener.assertOneGetNewAndReset();
        assertEquals(10.0, event.get("p1"));
    }

    public void testCaseSyntax2StringsNBranches()
    {
        // Test of the various coercion user cases.
       String caseExpr = "select case intPrimitive" +
                " when 1 then Boolean.toString(boolPrimitive) " +
                " when 2 then Boolean.toString(boolBoxed) " +
                " when 3 then Integer.toString(intPrimitive) " +
                " when 4 then Integer.toString(intBoxed)" +
                " when 5 then Long.toString(longPrimitive) " +
                " when 6 then Long.toString(longBoxed) " +
                " when 7 then Character.toString(charPrimitive) " +
                " when 8 then Character.toString(charBoxed) " +
                " when 9 then Short.toString(shortPrimitive) " +
                " when 10 then Short.toString(shortBoxed) " +
                " when 11 then Byte.toString(bytePrimitive) " +
                " when 12 then Byte.toString(byteBoxed) " +
                " when 13 then Float.toString(floatPrimitive) " +
                " when 14 then Float.toString(floatBoxed) " +
                " when 15 then Double.toString(doublePrimitive) " +
                " when 16 then Double.toString(doubleBoxed) " +
                " when 17 then string " +
                " else 'x' end as p1 " +
                " from " + SupportBean.class.getName() + ".win:length(1)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(String.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 1, new Integer(0),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals("true", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 2, new Integer(0),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("false", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 3, new Integer(0),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("3", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 4, new Integer(4),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("4", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 5, new Integer(0),5L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("5", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 6, new Integer(0),0L,new Long(6L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("6", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 7, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("A", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 8, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("a", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 9, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("9", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 10, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("10", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 11, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("11", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 12, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("12", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 13, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("13.0", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 14, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("14.0", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 15, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("15.0", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 16, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("16.0", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), 17, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("testCoercion", event.get("p1"));

        sendSupportBeanEvent(true, new Boolean(false), -1, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("x", event.get("p1"));
    }

    public void testCaseSyntax2NoElseWithNull()
    {
       String caseExpr = "select case string " +
                 " when null then true " +
                 " when '' then false end as p1" +
                 " from " + SupportBean.class.getName() + ".win:length(100)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent("x");
        assertEquals(null, testListener.assertOneGetNewAndReset().get("p1"));

        sendSupportBeanEvent("null");
        assertEquals(null, testListener.assertOneGetNewAndReset().get("p1"));

        sendSupportBeanEvent(null);
        assertEquals(true, testListener.assertOneGetNewAndReset().get("p1"));

        sendSupportBeanEvent("");
        assertEquals(false, testListener.assertOneGetNewAndReset().get("p1"));
    }

    public void testCaseSyntax1WithNull()
    {
       String caseExpr = "select case " +
                 " when string = null then true " +
                 " when string = '' then false end as p1" +
                 " from " + SupportBean.class.getName() + ".win:length(100)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent("x");
        assertEquals(null, testListener.assertOneGetNewAndReset().get("p1"));

        sendSupportBeanEvent("null");
        assertEquals(null, testListener.assertOneGetNewAndReset().get("p1"));

        sendSupportBeanEvent(null);
        assertEquals(true, testListener.assertOneGetNewAndReset().get("p1"));

        sendSupportBeanEvent("");
        assertEquals(false, testListener.assertOneGetNewAndReset().get("p1"));
    }

    public void testCaseSyntax2WithNull()
    {
       String caseExpr = "select case intPrimitive " +
                 " when 1 then null " +
                 " when 2 then 1.0" +
                 " when 3 then null " +
                 " else 2 " +
                 " end as p1 from " + SupportBean.class.getName() + ".win:length(100)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Double.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent(4);
        assertEquals(2.0, testListener.assertOneGetNewAndReset().get("p1"));
        sendSupportBeanEvent(1);
        assertEquals(null, testListener.assertOneGetNewAndReset().get("p1"));
        sendSupportBeanEvent(2);
        assertEquals(1.0, testListener.assertOneGetNewAndReset().get("p1"));
        sendSupportBeanEvent(3);
        assertEquals(null, testListener.assertOneGetNewAndReset().get("p1"));
    }

    public void testCaseSyntax2WithNullBool()
    {
       String caseExpr = "select case boolBoxed " +
                 " when null then 1 " +
                 " when true then 2l" +
                 " when false then 3 " +
                 " end as p1 from " + SupportBean.class.getName() + ".win:length(100)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Long.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent(null);
        assertEquals(1L, testListener.assertOneGetNewAndReset().get("p1"));
        sendSupportBeanEvent(false);
        assertEquals(3L, testListener.assertOneGetNewAndReset().get("p1"));
        sendSupportBeanEvent(true);
        assertEquals(2L, testListener.assertOneGetNewAndReset().get("p1"));
    }

    public void testCaseSyntax2WithCoercion()
    {
       String caseExpr = "select case intPrimitive " +
                 " when 1.0 then null " +
                 " when 4/2.0 then 'x'" +
                 " end as p1 from " + SupportBean.class.getName() + ".win:length(100)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(String.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent(1);
        assertEquals(null, testListener.assertOneGetNewAndReset().get("p1"));
        sendSupportBeanEvent(2);
        assertEquals("x", testListener.assertOneGetNewAndReset().get("p1"));
        sendSupportBeanEvent(3);
        assertEquals(null, testListener.assertOneGetNewAndReset().get("p1"));
    }

    public void testCaseSyntax2WithinExpression()
    {
       String caseExpr = "select 2 * (case " +
                 " intPrimitive when 1 then 2 " +
                 " when 2 then 3 " +
                 " else 10 end) as p1 " +
                 " from " + SupportBean.class.getName() + ".win:length(1)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Integer.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent(1);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(4, event.get("p1"));

        sendSupportBeanEvent(2);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(6, event.get("p1"));

        sendSupportBeanEvent(3);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(20, event.get("p1"));
    }

    public void testCaseSyntax2Sum()
    {
       String caseExpr = "select case intPrimitive when 1 then sum(longPrimitive) " +
                 " when 2 then sum(floatPrimitive) " +
                 " else sum(intPrimitive) end as p1 " +
                 " from " + SupportBean.class.getName() + ".win:length(10)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Float.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent(1, 10L, 3.0f, 4.0);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(10f, event.get("p1"));

        sendSupportBeanEvent(1, 15L, 3.0f, 4.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(25f, event.get("p1"));

        sendSupportBeanEvent(2, 1L, 3.0f, 4.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(9f, event.get("p1"));

        sendSupportBeanEvent(2, 1L, 3.0f, 4.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(12.0F, event.get("p1"));

        sendSupportBeanEvent(5, 1L, 1.0f, 1.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(11.0F, event.get("p1"));

        sendSupportBeanEvent(5, 1L, 1.0f, 1.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(16f, event.get("p1"));
    }

    public void testCaseSyntax2EnumChecks()
    {
       String caseExpr = "select case supportEnum " +
                 " when net.esper.support.bean.SupportEnum.getValueForEnum(0) then 1 " +
                 " when net.esper.support.bean.SupportEnum.getValueForEnum(1) then 2 " +
                 " end as p1 " +
                 " from " + SupportBeanWithEnum.class.getName() + ".win:length(10)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Integer.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent("a", SupportEnum.ENUM_VALUE_1);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(1, event.get("p1"));

        sendSupportBeanEvent("b", SupportEnum.ENUM_VALUE_2);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(2, event.get("p1"));

        sendSupportBeanEvent("c", SupportEnum.ENUM_VALUE_3);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(null, event.get("p1"));
    }

    public void testCaseSyntax2EnumResult()
    {
       String caseExpr = "select case intPrimitive * 2 " +
                 " when 2 then net.esper.support.bean.SupportEnum.getValueForEnum(0) " +
                 " when 4 then net.esper.support.bean.SupportEnum.getValueForEnum(1) " +
                 " else net.esper.support.bean.SupportEnum.getValueForEnum(2) " +
                 " end as p1 " +
                 " from " + SupportBean.class.getName() + ".win:length(10)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(SupportEnum.class, selectTestCase.getEventType().getPropertyType("p1"));

        sendSupportBeanEvent(1);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(SupportEnum.ENUM_VALUE_1, event.get("p1"));

        sendSupportBeanEvent(2);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(SupportEnum.ENUM_VALUE_2, event.get("p1"));

        sendSupportBeanEvent(3);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(SupportEnum.ENUM_VALUE_3, event.get("p1"));
    }

    public void testCaseSyntax2NoAsName()
    {
        String caseSubExpr = "case intPrimitive when 1 then 0 end";
        String caseExpr = "select " + caseSubExpr +
                 " from " + SupportBean.class.getName() + ".win:length(10)";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Integer.class, selectTestCase.getEventType().getPropertyType(caseSubExpr));

        sendSupportBeanEvent(1);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(0, event.get(caseSubExpr));
    }

    private void sendSupportBeanEvent(boolean b_, Boolean boolBoxed_, int i_, Integer intBoxed_, long l_, Long longBoxed_,
                                      char c_, Character charBoxed_, short s_, Short shortBoxed_, byte by_, Byte byteBoxed_,
                                      float f_, Float floatBoxed_, double d_, Double doubleBoxed_, String str_, SupportEnum enum_)
    {
        SupportBean event = new SupportBean();
        event.setBoolPrimitive(b_);
        event.setBoolBoxed(boolBoxed_);
        event.setIntPrimitive(i_);
        event.setIntBoxed(intBoxed_);
        event.setLongPrimitive(l_);
        event.setLongBoxed(longBoxed_);
        event.setCharPrimitive(c_);
        event.setCharBoxed(charBoxed_);
        event.setShortPrimitive(s_);
        event.setShortBoxed(shortBoxed_);
        event.setBytePrimitive(by_);
        event.setByteBoxed(byteBoxed_);
        event.setFloatPrimitive(f_);
        event.setFloatBoxed(floatBoxed_);
        event.setDoublePrimitive(d_);
        event.setDoubleBoxed(doubleBoxed_);
        event.setString(str_);
        event.setEnumValue(enum_);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendSupportBeanEvent(int intPrimitive, long longPrimitive, float floatPrimitive, double doublePrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        event.setLongPrimitive(longPrimitive);
        event.setFloatPrimitive(floatPrimitive);
        event.setDoublePrimitive(doublePrimitive);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendSupportBeanEvent(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendSupportBeanEvent(String string)
    {
        SupportBean event = new SupportBean();
        event.setString(string);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendSupportBeanEvent(boolean boolBoxed)
    {
        SupportBean event = new SupportBean();
        event.setBoolBoxed(boolBoxed);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendSupportBeanEvent(String string, SupportEnum supportEnum)
    {
        SupportBeanWithEnum event = new SupportBeanWithEnum(string, supportEnum);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendMarketDataEvent(String symbol, long volume, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestCaseExpr.class);
}
