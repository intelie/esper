package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
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
        tryString("string between 'a0' and 'b9'",
                    new String[] {"0",    "a1", "a10", "c", "d",    null, "a0", "b9", "b90"},
                    new boolean[] {false, true, true, false, false, false, true, true, false});

        tryString("string between null and 'b9'",
                    new String[] {"0", null, "a0", "b9"},
                    new boolean[] {false, false, false, false});

        tryString("string between null and null",
                    new String[] {"0", null, "a0", "b9"},
                    new boolean[] {false, false, false, false});

        tryString("string between 'a0' and null",
                    new String[] {"0", null, "a0", "b9"},
                    new boolean[] {false, false, false, false});
    }

    public void testInNumericExpr()
    {
        tryInNumeric("doubleBoxed in (1.1d, 7/3, 2*7/3, 0)",
                    new Double[] {1d, null, 1.1d, 1.0d, 1.0999999999, 2d, 4d},
                    new boolean[] {false, false, true, false, false, true, true});

        tryInNumeric("doubleBoxed in (7/3d, null)",
                    new Double[] {2d, 7/3d, null},
                    new boolean[] {false, true, true});

        tryInNumeric("doubleBoxed in (5,5,5,5,5, -1)",
                    new Double[] {5.0, 5d, 0d, null, -1d},
                    new boolean[] {true, true, false, false, true});

        tryInNumeric("doubleBoxed not in (1.1d, 7/3, 2*7/3, 0)",
                    new Double[] {1d, null, 1.1d, 1.0d, 1.0999999999, 2d, 4d},
                    new boolean[] {true, true, false, true, true, false, false});
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

    private void tryInNumeric(String expr, Double[] input, boolean[] result)
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
