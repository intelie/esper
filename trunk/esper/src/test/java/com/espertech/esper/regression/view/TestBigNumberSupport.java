package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBeanNumeric;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.epl.SupportStaticMethodLib;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestBigNumberSupport extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("SupportBeanNumeric", SupportBeanNumeric.class);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("SupportBean", SupportBean.class);
    }

    public void testEquals()
    {
        // test equals BigDecimal
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBeanNumeric where bigdec = 1 or bigdec = intOne or bigdec = doubleOne");
        stmt.addListener(listener);

        sendBigNumEvent(-1, 1);
        assertTrue(listener.getAndClearIsInvoked());
        sendBigNumEvent(-1, 2);
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(2, 0, null, BigDecimal.valueOf(2), 0, 0));
        assertTrue(listener.getAndClearIsInvoked());
        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(3, 0, null, BigDecimal.valueOf(2), 0, 0));
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(0, 0, null, BigDecimal.valueOf(3d), 3d, 0));
        assertTrue(listener.getAndClearIsInvoked());
        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(0, 0, null, BigDecimal.valueOf(3.9999d), 4d, 0));
        assertFalse(listener.getAndClearIsInvoked());

        // test equals BigInteger
        stmt = epService.getEPAdministrator().createEPL("select * from SupportBeanNumeric where bigdec = bigint or bigint = intOne or bigint = 1");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(0, 0, BigInteger.valueOf(2), BigDecimal.valueOf(2), 0, 0));
        assertTrue(listener.getAndClearIsInvoked());
        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(0, 0, BigInteger.valueOf(3), BigDecimal.valueOf(2), 0, 0));
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(2, 0, BigInteger.valueOf(2), null, 0, 0));
        assertTrue(listener.getAndClearIsInvoked());
        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(3, 0, BigInteger.valueOf(2), null, 0, 0));
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(0, 0, BigInteger.valueOf(1), null, 0, 0));
        assertTrue(listener.getAndClearIsInvoked());
        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(0, 0, BigInteger.valueOf(4), null, 0, 0));
        assertFalse(listener.getAndClearIsInvoked());
    }

    public void testRelOp()
    {
        // relational op tests handled by relational op unit test
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBeanNumeric where bigdec < 10 and bigint > 10");
        stmt.addListener(listener);

        sendBigNumEvent(10, 10);
        assertFalse(listener.getAndClearIsInvoked());

        sendBigNumEvent(11, 9);
        assertTrue(listener.getAndClearIsInvoked());
        stmt.destroy();

        stmt = epService.getEPAdministrator().createEPL("select * from SupportBeanNumeric where bigdec < 10.0");
        stmt.addListener(listener);

        sendBigNumEvent(0, 11);
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(null, BigDecimal.valueOf(9.999)));
        assertTrue(listener.getAndClearIsInvoked());
        stmt.destroy();
    }

    public void testBetween()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBeanNumeric where bigdec between 10 and 20 or bigint between 100 and 200");
        stmt.addListener(listener);

        sendBigNumEvent(0, 9);
        assertFalse(listener.getAndClearIsInvoked());

        sendBigNumEvent(0, 10);
        assertTrue(listener.getAndClearIsInvoked());

        sendBigNumEvent(99, 0);
        assertFalse(listener.getAndClearIsInvoked());

        sendBigNumEvent(100, 0);
        assertTrue(listener.getAndClearIsInvoked());
        stmt.destroy();
    }

    public void testIn()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBeanNumeric where bigdec in (10, 20d) or bigint in (0x02, 3)");
        stmt.addListener(listener);

        sendBigNumEvent(0, 9);
        assertFalse(listener.getAndClearIsInvoked());

        sendBigNumEvent(0, 10);
        assertTrue(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(null, BigDecimal.valueOf(20d)));
        assertTrue(listener.getAndClearIsInvoked());

        sendBigNumEvent(99, 0);
        assertFalse(listener.getAndClearIsInvoked());

        sendBigNumEvent(2, 0);
        assertTrue(listener.getAndClearIsInvoked());

        sendBigNumEvent(3, 0);
        assertTrue(listener.getAndClearIsInvoked());
        stmt.destroy();
    }

    public void testMath()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBeanNumeric " +
                        "where bigdec+bigint=100 or bigdec+1=2 or bigdec+2d=5.0 or bigint+5L=8 or bigint+5d=9.0");
        stmt.addListener(listener);

        sendBigNumEvent(50, 49);
        assertFalse(listener.getAndClearIsInvoked());

        sendBigNumEvent(50, 50);
        assertTrue(listener.getAndClearIsInvoked());

        sendBigNumEvent(0, 1);
        assertTrue(listener.getAndClearIsInvoked());

        sendBigNumEvent(0, 2);
        assertFalse(listener.getAndClearIsInvoked());

        sendBigNumEvent(0, 3);
        assertTrue(listener.getAndClearIsInvoked());

        sendBigNumEvent(0, 0);
        assertFalse(listener.getAndClearIsInvoked());

        sendBigNumEvent(3, 0);
        assertTrue(listener.getAndClearIsInvoked());

        sendBigNumEvent(4, 0);
        assertTrue(listener.getAndClearIsInvoked());
        stmt.destroy();

        stmt = epService.getEPAdministrator().createEPL(
                "select bigdec+bigint as v1, bigdec+2 as v2, bigdec+3d as v3, bigint+5L as v4, bigint+5d as v5 " +
                " from SupportBeanNumeric");
        stmt.addListener(listener);
        listener.reset();

        assertEquals(BigDecimal.class, stmt.getEventType().getPropertyType("v1"));
        assertEquals(BigDecimal.class, stmt.getEventType().getPropertyType("v2"));
        assertEquals(BigDecimal.class, stmt.getEventType().getPropertyType("v3"));
        assertEquals(BigInteger.class, stmt.getEventType().getPropertyType("v4"));
        assertEquals(BigDecimal.class, stmt.getEventType().getPropertyType("v5"));

        sendBigNumEvent(1, 2);
        EventBean event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, "v1,v2,v3,v4,v5".split(","),
                new Object[] {BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5d), BigInteger.valueOf(6), BigDecimal.valueOf(6d)});
    }

    public void testAggregation()
    {
        String fields = "sum(bigint),sum(bigdec)," +
                "avg(bigint),avg(bigdec)," +
                "median(bigint),median(bigdec)," +
                "stddev(bigint),stddev(bigdec)," +
                "avedev(bigint),avedev(bigdec)," +
                "min(bigint),min(bigdec)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select " + fields + " from SupportBeanNumeric");
        stmt.addListener(listener);
        listener.reset();

        String[] fieldList = fields.split(",");
        sendBigNumEvent(1, 2);
        EventBean event = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(event, fieldList,
                new Object[] {BigInteger.valueOf(1), BigDecimal.valueOf(2d),        // sum
                        BigDecimal.valueOf(1), BigDecimal.valueOf(2),               // avg
                        1d, 2d,               // median
                        null, null,
                        0.0, 0.0,
                        BigInteger.valueOf(1), BigDecimal.valueOf(2),
                });
    }

    public void testMinMax()
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select min(bigint, 10) as v1, min(10, bigint) as v2, " +
                "max(bigdec, 10) as v3, max(10, 100d, bigint, bigdec) as v4 from SupportBeanNumeric");
        stmt.addListener(listener);
        listener.reset();

        String[] fieldList = "v1,v2,v3,v4".split(",");

        sendBigNumEvent(1, 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldList,
                new Object[] {BigInteger.valueOf(1), BigInteger.valueOf(1), BigDecimal.valueOf(10), BigDecimal.valueOf(100d)});

        sendBigNumEvent(40, 300);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldList,
                new Object[] {BigInteger.valueOf(10), BigInteger.valueOf(10), BigDecimal.valueOf(300), BigDecimal.valueOf(300)});

        sendBigNumEvent(250, 200);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldList,
                new Object[] {BigInteger.valueOf(10), BigInteger.valueOf(10),  BigDecimal.valueOf(200), BigDecimal.valueOf(250)});
    }

    public void testFilterEquals()
    {
        String[] fieldList = "bigdec".split(",");

        EPStatement stmt = epService.getEPAdministrator().createEPL("select bigdec from SupportBeanNumeric(bigdec = 4)");
        stmt.addListener(listener);

        sendBigNumEvent(0, 2);
        assertFalse(listener.isInvoked());

        sendBigNumEvent(0, 4);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldList, new Object[] {BigDecimal.valueOf(4)});

        stmt.destroy();
        stmt = epService.getEPAdministrator().createEPL("select bigdec from SupportBeanNumeric(bigdec = 4d)");
        stmt.addListener(listener);

        sendBigNumEvent(0, 4);
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(BigInteger.valueOf(0), BigDecimal.valueOf(4d)));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldList, new Object[] {BigDecimal.valueOf(4.0)});

        stmt.destroy();
        stmt = epService.getEPAdministrator().createEPL("select bigdec from SupportBeanNumeric(bigint = 4)");
        stmt.addListener(listener);

        sendBigNumEvent(3, 4);
        assertFalse(listener.isInvoked());

        sendBigNumEvent(4, 3);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldList, new Object[] {BigDecimal.valueOf(3)});
    }

    public void testJoin()
    {
        String[] fieldList = "bigint,bigdec".split(",");
        EPStatement stmt = epService.getEPAdministrator().createEPL("select bigint,bigdec from SupportBeanNumeric, SupportBean " +
                "where intPrimitive = bigint and doublePrimitive = bigdec");
        stmt.addListener(listener);

        sendSupportBean(2, 3);
        sendBigNumEvent(0, 2);
        sendBigNumEvent(2, 0);
        sendBigNumEvent(2, 3);  // still no match, 3.0 != 3 for BigDecimal
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(BigInteger.valueOf(2), BigDecimal.valueOf(3.0)));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldList, new Object[] {BigInteger.valueOf(2), BigDecimal.valueOf(3.0)});
    }

    public void testCastAndUDF()
    {
        epService.getEPAdministrator().getConfiguration().addImport(SupportStaticMethodLib.class.getName());
        EPStatement stmt = epService.getEPAdministrator().createEPL(
                "select SupportStaticMethodLib.myBigIntFunc(cast(2, BigInteger)) as v1, SupportStaticMethodLib.myBigDecFunc(cast(3d, BigDecimal)) as v2 from SupportBeanNumeric");
        stmt.addListener(listener);

        String[] fieldList = "v1,v2".split(",");
        sendBigNumEvent(0, 2);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldList, new Object[] {BigInteger.valueOf(2), BigDecimal.valueOf(3.0)});
    }

    private void sendBigNumEvent(int bigInt, int bigDec)
    {
        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(BigInteger.valueOf(bigInt), BigDecimal.valueOf(bigDec)));
    }

    private void sendSupportBean(int intPrimitive, double doublePrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setDoublePrimitive(doublePrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }
}