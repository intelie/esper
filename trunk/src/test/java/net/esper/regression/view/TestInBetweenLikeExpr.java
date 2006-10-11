package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    // tests:
    //   enums

    public void testInStringExpr()
    {
        tryInString("'a', 'b', 'c'",
                    new String[] {"0", "a", "b", "c", "d", null},
                    new boolean[] {false, true, true, true, false, false});

        tryInString("'a'",
                    new String[] {"0", "a", "b", "c", "d", null},
                    new boolean[] {false, true, false, false, false, false});

        tryInString("'a', 'b'",
                    new String[] {"0", "b", "a", "c", "d", null},
                    new boolean[] {false, true, true, false, false, false});

        tryInString("'a', null",
                    new String[] {"0", "b", "a", "c", "d", null},
                    new boolean[] {false, false, true, false, false, true});

        tryInString("null",
                    new String[] {"0", null, "b"},
                    new boolean[] {false, true, false});
    }

    public void testInNumericExpr()
    {
        tryInNumeric("1.1d, 7/3, 2*7/3, 0",
                    new Double[] {1d, null, 1.1d, 1.0d, 1.0999999999, 2d, 4d},
                    new boolean[] {false, false, true, false, false, true, true});

        tryInNumeric("7/3d, null",
                    new Double[] {2d, 7/3d, null},
                    new boolean[] {false, true, true});

        tryInNumeric("5,5,5,5,5, -1",
                    new Double[] {5.0, 5d, 0d, null, -1d},
                    new boolean[] {true, true, false, false, true});
    }

    public void testInBoolExpr()
    {
        tryInBoolean("true, true",
                    new Boolean[] {true, false},
                    new boolean[] {true, false});

        tryInBoolean("1>2, 2=3, 4<=2",
                    new Boolean[] {true, false},
                    new boolean[] {false, true});
    }

    private void tryInBoolean(String inList, Boolean[] input, boolean[] result)
    {
        String caseExpr = "select boolBoxed in (" + inList + ") as result from " + SupportBean.class.getName();

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

    private void tryInNumeric(String inList, Double[] input, boolean[] result)
    {
        String caseExpr = "select doubleBoxed in (" + inList + ") as result from " + SupportBean.class.getName();

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

    private void tryInString(String inList, String[] input, boolean[] result)
    {
        String caseExpr = "select string in (" + inList + ") as result from " + SupportBean.class.getName();

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

    private static final Log log = LogFactory.getLog(TestInBetweenLikeExpr.class);
}
