package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanSimple;
import net.esper.event.EventBean;

public class TestInBetweenLikeExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testInStringExpr()
    {
        tryString("string in ('a', 'b', 'c')",
                    new String[] {"0", "a", "b", "c", "d", null},
                    new boolean[] {false, true, true, true, false, false});

        tryString("string in ('a')",
                    new String[] {"0", "a", "b", "c", "d", null},
                    new boolean[] {false, true, false, false, false, false});

        tryString("string in ('a', 'b')",
                    new String[] {"0", "b", "a", "c", "d", null},
                    new boolean[] {false, true, true, false, false, false});

        tryString("string in ('a', null)",
                    new String[] {"0", "b", "a", "c", "d", null},
                    new boolean[] {false, false, true, false, false, true});

        tryString("string in (null)",
                    new String[] {"0", null, "b"},
                    new boolean[] {false, true, false});

        tryString("string not in ('a', 'b', 'c')",
                    new String[] {"0", "a", "b", "c", "d", null},
                    new boolean[] {true, false, false, false, true, true});

        tryString("string not in (null)",
                    new String[] {"0", null, "b"},
                    new boolean[] {true, false, true});
    }

    public void testBetweenStringExpr()
    {
        String[] input = null;
        boolean[] result = null;

        input = new String[] {"0",    "a1", "a10", "c", "d",    null, "a0", "b9", "b90"};
        result = new boolean[] {false, true, true, false, false, false, true, true, false};
        tryString("string between 'a0' and 'b9'", input, result);
        tryString("string between 'b9' and 'a0'", input, result);

        tryString("string between null and 'b9'",
                    new String[] {"0", null, "a0", "b9"},
                    new boolean[] {false, false, false, false});

        tryString("string between null and null",
                    new String[] {"0", null, "a0", "b9"},
                    new boolean[] {false, false, false, false});

        tryString("string between 'a0' and null",
                    new String[] {"0", null, "a0", "b9"},
                    new boolean[] {false, false, false, false});

        input = new String[] {"0",    "a1", "a10", "c", "d",    null, "a0", "b9", "b90"};
        result = new boolean[] {true, false, false, true, true, false, false, false, true};
        tryString("string not between 'a0' and 'b9'", input, result);
        tryString("string not between 'b9' and 'a0'", input, result);
    }

    public void testInNumericExpr()
    {
        Double[] input = new Double[] {1d, null, 1.1d, 1.0d, 1.0999999999, 2d, 4d};
        boolean[] result = new boolean[] {false, false, true, false, false, true, true};
        tryNumeric("doubleBoxed in (1.1d, 7/3, 2*7/3, 0)", input, result);

        tryNumeric("doubleBoxed in (7/3d, null)",
                    new Double[] {2d, 7/3d, null},
                    new boolean[] {false, true, true});

        tryNumeric("doubleBoxed in (5,5,5,5,5, -1)",
                    new Double[] {5.0, 5d, 0d, null, -1d},
                    new boolean[] {true, true, false, false, true});

        tryNumeric("doubleBoxed not in (1.1d, 7/3, 2*7/3, 0)",
                    new Double[] {1d, null, 1.1d, 1.0d, 1.0999999999, 2d, 4d},
                    new boolean[] {true, true, false, true, true, false, false});
    }

    public void testBetweenNumericExpr()
    {
        Double[] input = new Double[] {1d, null, 1.1d, 2d, 1.0999999999, 2d, 4d, 15d, 15.00001d};
        boolean[] result = new boolean[] {false, false, true, true, false, true, true, true, false};
        tryNumeric("doubleBoxed between 1.1 and 15", input, result);
        tryNumeric("doubleBoxed between 15 and 1.1", input, result);

        tryNumeric("doubleBoxed between null and 15",
                    new Double[] {1d, null, 1.1d},
                    new boolean[] {false, false, false});

        tryNumeric("doubleBoxed between 15 and null",
                    new Double[] {1d, null, 1.1d},
                    new boolean[] {false, false, false});

        tryNumeric("doubleBoxed between null and null",
                    new Double[] {1d, null, 1.1d},
                    new boolean[] {false, false, false});

        input = new Double[] {1d, null, 1.1d, 2d, 1.0999999999, 2d, 4d, 15d, 15.00001d};
        result = new boolean[] {true, false, false, false, true, false, false, false, true};
        tryNumeric("doubleBoxed not between 1.1 and 15", input, result);
        tryNumeric("doubleBoxed not between 15 and 1.1", input, result);

        tryNumeric("doubleBoxed not between 15 and null",
                    new Double[] {1d, null, 1.1d},
                    new boolean[] {false, false, false});
    }

    public void testInBoolExpr()
    {
        tryInBoolean("boolBoxed in (true, true)",
                    new Boolean[] {true, false},
                    new boolean[] {true, false});

        tryInBoolean("boolBoxed in (1>2, 2=3, 4<=2)",
                    new Boolean[] {true, false},
                    new boolean[] {false, true});

        tryInBoolean("boolBoxed not in (1>2, 2=3, 4<=2)",
                    new Boolean[] {true, false},
                    new boolean[] {true, false});
    }

    public void testInNumericCoercionLong()
    {
        String caseExpr = "select intPrimitive in (shortBoxed, intBoxed, longBoxed) as result from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("result"));

        sendAndAssert(1, 2, 3, 4L, false);
        sendAndAssert(1, 1, 3, 4L, true);
        sendAndAssert(1, 3, 1, 4L, true);
        sendAndAssert(1, 3, 7, 1L, true);
        sendAndAssert(1, 3, 7, null, false);
        sendAndAssert(1, 1, null, null, true);
        sendAndAssert(1, 0, null, 1L, true);

        selectTestCase.stop();
    }

    public void testInNumericCoercionDouble()
    {
        String caseExpr = "select intBoxed in (floatBoxed, doublePrimitive, longBoxed) as result from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("result"));

        sendAndAssert(1, 2f, 3d, 4L, false);
        sendAndAssert(1, 1f, 3d, 4L, true);
        sendAndAssert(1, 1.1f, 1.0d, 4L, true);
        sendAndAssert(1, 1.1f, 1.2d, 1L, true);
        sendAndAssert(1, null, 1.2d, 1L, true);
        sendAndAssert(null, null, 1.2d, 1L, true);
        sendAndAssert(null, 11f, 1.2d, 1L, false);

        selectTestCase.stop();
    }

    public void testBetweenNumericCoercionLong()
    {
        String caseExpr = "select intPrimitive between shortBoxed and longBoxed as result from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("result"));

        sendAndAssert(1, 2, 3l, false);
        sendAndAssert(2, 2, 3l, true);
        sendAndAssert(3, 2, 3l, true);
        sendAndAssert(4, 2, 3l, false);
        sendAndAssert(5, 10, 1L, true);
        sendAndAssert(1, 10, 1L, true);
        sendAndAssert(10, 10, 1L, true);
        sendAndAssert(11, 10, 1L, false);

        selectTestCase.stop();
    }

    public void testBetweenNumericCoercionDouble()
    {
        String caseExpr = "select intBoxed between floatBoxed and doublePrimitive as result from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("result"));

        sendAndAssert(1, 2f, 3d, false);
        sendAndAssert(2, 2f, 3d, true);
        sendAndAssert(3, 2f, 3d, true);
        sendAndAssert(4, 2f, 3d, false);
        sendAndAssert(null, 2f, 3d, false);
        sendAndAssert(null, null, 3d, false);
        sendAndAssert(1, 3f, 2d, false);
        sendAndAssert(2, 3f, 2d, true);
        sendAndAssert(3, 3f, 2d, true);
        sendAndAssert(4, 3f, 2d, false);
        sendAndAssert(null, 3f, 2d, false);
        sendAndAssert(null, null, 2d, false);

        selectTestCase.stop();
    }

    private void sendAndAssert(Integer intBoxed, Float floatBoxed, double doublePrimitive, boolean result)
    {
        SupportBean bean = new SupportBean();
        bean.setIntBoxed(intBoxed);
        bean.setFloatBoxed(floatBoxed);
        bean.setDoublePrimitive(doublePrimitive);

        epService.getEPRuntime().sendEvent(bean);

        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals(result, event.get("result"));
    }

    private void sendAndAssert(int intPrimitive, int shortBoxed, Integer intBoxed, Long longBoxed, boolean result)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setShortBoxed( (short) shortBoxed);
        bean.setIntBoxed(intBoxed);
        bean.setLongBoxed(longBoxed);

        epService.getEPRuntime().sendEvent(bean);

        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals(result, event.get("result"));
    }

    private void sendAndAssert(int intPrimitive, int shortBoxed, Long longBoxed, boolean result)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setShortBoxed( (short) shortBoxed);
        bean.setLongBoxed(longBoxed);

        epService.getEPRuntime().sendEvent(bean);

        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals(result, event.get("result"));
    }

    private void sendAndAssert(Integer intBoxed, Float floatBoxed, double doublePrimitve, Long longBoxed, boolean result)
    {
        SupportBean bean = new SupportBean();
        bean.setIntBoxed(intBoxed);
        bean.setFloatBoxed(floatBoxed);
        bean.setDoublePrimitive(doublePrimitve);
        bean.setLongBoxed(longBoxed);

        epService.getEPRuntime().sendEvent(bean);

        EventBean event = testListener.assertOneGetNewAndReset();
        assertEquals(result, event.get("result"));
    }

    private void tryInBoolean(String expr, Boolean[] input, boolean[] result)
    {
        String caseExpr = "select " + expr + " as result from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("result"));

        for (int i = 0; i < input.length; i++)
        {
            sendSupportBeanEvent(input[i]);
            EventBean event = testListener.assertOneGetNewAndReset();
            assertEquals("Wrong result for " + input[i], result[i], event.get("result"));
        }
        selectTestCase.stop();
    }

    private void tryNumeric(String expr, Double[] input, boolean[] result)
    {
        String caseExpr = "select " + expr + " as result from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("result"));

        for (int i = 0; i < input.length; i++)
        {
            sendSupportBeanEvent(input[i]);
            EventBean event = testListener.assertOneGetNewAndReset();
            assertEquals("Wrong result for " + input[i], result[i], event.get("result"));
        }
        selectTestCase.stop();
    }

    private void tryString(String expression, String[] input, boolean[] result)
    {
        String caseExpr = "select " + expression + " as result from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);
        assertEquals(Boolean.class, selectTestCase.getEventType().getPropertyType("result"));

        for (int i = 0; i < input.length; i++)
        {
            sendSupportBeanEvent(input[i]);
            EventBean event = testListener.assertOneGetNewAndReset();
            assertEquals("Wrong result for " + input[i], result[i], event.get("result"));
        }
        selectTestCase.stop();
    }

    private void sendSupportBeanEvent(Double doubleBoxed)
    {
        SupportBean event = new SupportBean();
        event.setDoubleBoxed(doubleBoxed);
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
}
