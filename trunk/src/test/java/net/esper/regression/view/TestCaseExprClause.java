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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Aug 13, 2006
 * Time: 1:50:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestCaseExprClause extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestCase;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testCaseStatements()
    {
        // Testing the two forms of the case expression
        // Furthermore the test checks the different when clauses and actions related.
        String caseExpr = "select case " +
              " when (symbol='GE') then volume " +
              " when (symbol='DELL') then sum(price) end as p1 from " +   SupportMarketDataBean.class.getName() + ".win:length(3)";

        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);

        sendSupportMarketDateEvent("DELL", 10000, 50);
        sendSupportMarketDateEvent("DELL", 10000, 50);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(100.0, event.get("p1"));
        sendSupportMarketDateEvent("CSCO", 4000, 5);
        assertTrue(testListener.isInvoked());
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(null,event.get("p1"));
        sendSupportMarketDateEvent("GE", 20, 30);
        sendSupportMarketDateEvent("GE", 20, 30);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(20L, event.get("p1"));
        // Adding to the EQL statement an else expression
        // when a CSCO ticker is sent the property for the else expression is selected
        caseExpr = "select case " +
              " when (symbol='GE') then volume " +
              " when (symbol='DELL') then sum(price) else symbol end as p1 from " +   SupportMarketDataBean.class.getName() + ".win:length(3)";
        testListener.reset();
        selectTestCase.removeListener(testListener);
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        sendSupportMarketDateEvent("CSCO", 4000, 5);
        assertTrue(testListener.isInvoked());
        event = testListener.getAndResetLastNewData()[0];
        // Compare to the same test few lines above where the case expression
        // was not matching any of the conditions on the event.
        // Here the value given by the case is symbol.
        assertEquals("CSCO",event.get("p1"));
        sendSupportMarketDateEvent("GE", 20, 30);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(20L, event.get("p1"));

        // Same test but the where clause doesn't match any of the condition of the case expresssion
        caseExpr = "select case " +
            " when (symbol='GE') then volume " +
            " when (symbol='DELL') then sum(price) end as p1 from " +   SupportMarketDataBean.class.getName() +
            ".win:length(3) where symbol='NT'";

        testListener.reset();
        selectTestCase.removeListener(testListener);
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        sendSupportMarketDateEvent("DELL", 10000, 50);
        assertFalse(testListener.getAndClearIsInvoked());

        caseExpr = "select case " +
                " intPrimitive when longPrimitive then (intPrimitive + longPrimitive) " +
                " when doublePrimitive then intPrimitive * doublePrimitive" +
                " when floatPrimitive then floatPrimitive / doublePrimitive " +
                " else (intPrimitive + longPrimitive + floatPrimitive + doublePrimitive) end as p1 " +
                " from " + SupportBean.class.getName() + ".win:length(3)";
        testListener.reset();
        selectTestCase.removeListener(testListener);
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);

        // intPrimitive = longPrimitive
        // case result is intPrimitive + longPrimitive
        sendSupportBeanEvent(2, 2L, 1.0f, 1.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(4l, event.get("p1"));
        // intPrimitive = doublePrimitive
        // case result is intPrimitive * doublePrimitive
        sendSupportBeanEvent(5, 1L, 1.0f, 5.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(25.0, event.get("p1"));
        // intPrimitive = floatPrimitive
        // case result is floatPrimitive / doublePrimitive
        sendSupportBeanEvent(12, 1L, 12.0f, 4.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(3.0, event.get("p1"));
        // all the properties of the event are different
        // The else part is computed: 1+2+3+4 = 10
        sendSupportBeanEvent(1, 2L, 3.0f, 4.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(10.0, event.get("p1"));
    }

    public void testCaseCoercion()
    {
        // Test of the various coercion user cases.
       String caseExpr = "select case " +
                " intPrimitive when 1 then boolPrimitive " +
                " when 2 then boolBoxed " +
                " when 3 then intPrimitive " +
                " when 4 then intBoxed" +
                " when 5 then longPrimitive " +
                " when 6 then longBoxed " +
                " when 7 then charPrimitive " +
                " when 8 then charBoxed " +
                " when 9 then shortPrimitive " +
                " when 10 then shortBoxed " +
                " when 11 then bytePrimitive " +
                " when 12 then byteBoxed " +
                " when 13 then floatPrimitive " +
                " when 14 then floatBoxed " +
                " when 15 then doublePrimitive " +
                " when 16 then doubleBoxed " +
                " when 17 then string " +
                " else enumValue end as p1 " +
                " from " + SupportBean.class.getName() + ".win:length(1)";

        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        sendSupportBeanEvent(true, new Boolean(false), 1, new Integer(0),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertTrue((Boolean)event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 2, new Integer(0),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertFalse((Boolean)event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 3, new Integer(0),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(3, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 4, new Integer(4),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(4, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 5, new Integer(0),5L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(5L, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 6, new Integer(0),0L,new Long(6L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(6L, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 7, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals('A', event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 8, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals('a', event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 9, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals((short)9, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 10, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals((short)10, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 11, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals((byte)11, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 12, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals((byte)12, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 13, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals((float)13.0, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 14, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals((float)14.0, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 15, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals((double)15.0, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 16, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals((double)16.0, event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 17, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("testCoercion", event.get("p1"));
        sendSupportBeanEvent(true, new Boolean(false), 18, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(SupportEnum.ENUM_VALUE_1, event.get("p1"));

        caseExpr = "select case " +
                " intPrimitive when true then boolPrimitive " +
                " when true then boolBoxed " +
                " when 1.0 then intPrimitive " +
                " when 2.0 then intBoxed" +
                " when 5L then longPrimitive " +
                " else enumValue end as p1 " +
                " from " + SupportBean.class.getName() + ".win:length(1)";

        testListener.reset();
        selectTestCase.removeListener(testListener);
        selectTestCase.removeAllListeners();
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        // No when conditions is satisfied: return value of else expression
        sendSupportBeanEvent(true, new Boolean(false), 0, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(SupportEnum.ENUM_VALUE_1, event.get("p1"));
        //  Comparing and coercing (int) 1 to (double)1.0: case expression returns value of intPrimitive which is 6L
        sendSupportBeanEvent(true, new Boolean(false), 1, new Integer(0),6L,new Long(7L),'A',new Character('a'),(short)8,new Short((short)9),(byte)10,new Byte((byte)12),13.0f,new Float((float)13),14.0,new Double(15.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(1, event.get("p1"));
        //  Comparing and coercing (int) 2 to (double)2.5: case expression returns value of intPrimitive which is 6L
        sendSupportBeanEvent(true, new Boolean(false), 2, new Integer(4),6L,new Long(7L),'A',new Character('a'),(short)8,new Short((short)9),(byte)10,new Byte((byte)12),13.0f,new Float((float)13),14.0,new Double(15.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(4, event.get("p1"));
        //  Comparing and coercing (int) 5 to 5L: case expression returns value of longPrimitive which is 6L
        sendSupportBeanEvent(true, new Boolean(false), 5, new Integer(0),6L,new Long(7L),'A',new Character('a'),(short)8,new Short((short)9),(byte)10,new Byte((byte)12),13.0f,new Float((float)13),14.0,new Double(15.0),"testCoercion",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals((long)6, event.get("p1"));

        caseExpr = "select case " +
                " string when true then boolPrimitive " +
                " when true then boolBoxed " +
                " when 'test' then intPrimitive " +
                " else enumValue end as p1 " +
                " from " + SupportBean.class.getName() + ".win:length(1)";

        testListener.reset();
        selectTestCase.removeListener(testListener);
        selectTestCase.removeAllListeners();
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        // No when conditions is satisfied: return value of else expression
        sendSupportBeanEvent(true, new Boolean(false), 100, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)9,new Short((short)10),(byte)11,new Byte((byte)12),13.0f,new Float((float)14),15.0,new Double(16.0),"test",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(100, event.get("p1"));

        caseExpr = "select case " +
                 " doubleBoxed when 1 then boolPrimitive " +
                 " when 2 then boolBoxed " +
                 " when 3 then doublePrimitive " +
                 " when 4 then doubleBoxed " +
                 " when 5 then string " +
                 " else enumValue end as p1 " +
                 " from " + SupportBean.class.getName() + ".win:length(1)";

        testListener.reset();
        selectTestCase.removeListener(testListener);
        selectTestCase.removeAllListeners();
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        // No when conditions is satisfied: return value of else expression
        sendSupportBeanEvent(true, new Boolean(false), 0, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(2.0),"test",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertFalse((Boolean)event.get("p1"));

        caseExpr = "select case " +
                 " floatPrimitive when true then boolPrimitive " +
                 " when 2.0F then boolBoxed " +
                 " when 3 then intPrimitive " +
                 " when 4 then intBoxed" +
                 " when 5.0F then longPrimitive " +
                 " when 6.1234F then floatPrimitive " +
                 " when 7 then floatBoxed " +
                 " else enumValue end as p1 " +
                 " from " + SupportBean.class.getName() + ".win:length(1)";

        testListener.reset();
        selectTestCase.removeListener(testListener);
        selectTestCase.removeAllListeners();
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        // No when conditions is satisfied: return value of else expression
        sendSupportBeanEvent(true, new Boolean(false), 0, new Integer(0),0L,new Long(0L),'A',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),6.1234f,new Float((float)0),0.0,new Double(0.0),"test",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(6.1234F,event.get("p1"));

        caseExpr = "select case " +
                 " intBoxed * longPrimitive when true then boolPrimitive " +
                 " when 2.0F then boolBoxed " +
                 " when 3 then intPrimitive " +
                 " when 4 then intBoxed" +
                 " when 520L then longPrimitive " +
                 " when 6F then floatPrimitive " +
                 " when 7 then floatBoxed " +
                 " else enumValue end as p1 " +
                 " from " + SupportBean.class.getName() + ".win:length(1)";

        testListener.reset();
        selectTestCase.removeListener(testListener);
        selectTestCase.removeAllListeners();
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        // No when conditions is satisfied: return value of else expression
        sendSupportBeanEvent(true, new Boolean(false), 0, new Integer(1),520L,new Long(0L),'A',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),"test",SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(520L,event.get("p1"));
    }

    public void testCaseWithinExpression()
    {
       String caseExpr = "select 2 * (case " +
                 " intPrimitive when 1 then 2 " +
                 " when 2 then 3 " +
                 " else 10 end) as p1 " +
                 " from " + SupportBean.class.getName() + ".win:length(1)";
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        sendSupportBeanEvent(true, new Boolean(false), 1, new Integer(0),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(4, event.get("p1"));
        testListener.reset();
        selectTestCase.removeListener(testListener);
        selectTestCase.removeAllListeners();
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        sendSupportBeanEvent(true, new Boolean(false), 4, new Integer(0),0L,new Long(0L),'0',new Character('a'),(short)0,new Short((short)0),(byte)0,new Byte((byte)0),0.0f,new Float((float)0),0.0,new Double(0.0),null,SupportEnum.ENUM_VALUE_1);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(20, event.get("p1"));

    }

    public void testCaseWithinAggregateExpression()
    {
       String caseExpr = "select case intPrimitive when 1 then sum(longPrimitive) " +
                 " when 2 then sum(floatPrimitive) " +
                 " else sum(intPrimitive) end as p1 " +
                 " from " + SupportBean.class.getName() + ".win:length(10)";
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        sendSupportBeanEvent(1, 10L, 3.0f, 4.0);
        sendSupportBeanEvent(1, 15L, 3.0f, 4.0);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(25L, event.get("p1"));
        sendSupportBeanEvent(2, 1L, 3.0f, 4.0);
        sendSupportBeanEvent(2, 1L, 3.0f, 4.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(12.0F, event.get("p1"));
        sendSupportBeanEvent(5, 1L, 1.0f, 1.0);
        sendSupportBeanEvent(5, 1L, 1.0f, 1.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(16, event.get("p1"));
    }

    public void testUnexpectedCaseClause()
    {
        String caseExpr = "select case intPrimitive when null then 1 " +
                  " when 2 then 3 end as p1 " +
                  " from " + SupportBean.class.getName() + ".win:length(11)";
         selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
         selectTestCase.addListener(testListener);
         sendSupportBeanEvent(1, 10L, 3.0f, 4.0);
         EventBean event = testListener.getAndResetLastNewData()[0];
         assertEquals(null, event.get("p1"));

       caseExpr = "select case intPrimitive when null then 1 " +
                 " when 2 then 3 else 4 end as p1 " +
                 " from " + SupportBean.class.getName() + ".win:length(11)";
        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        sendSupportBeanEvent(1, 10L, 3.0f, 4.0);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(4, event.get("p1"));
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

    private void sendSupportBeanEvent(int i_, long l_, float f_, double d_)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(i_);
        event.setLongPrimitive(l_);
        event.setFloatPrimitive(f_);
        event.setDoublePrimitive(d_);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendSupportMarketDateEvent(String symbol, long volume, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestCaseExprClause.class);
}
