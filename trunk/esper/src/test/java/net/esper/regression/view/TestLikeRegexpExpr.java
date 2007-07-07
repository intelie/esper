package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPException;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.*;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestLikeRegexpExpr extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testLikeRegexStringAndNull()
    {
        String caseExpr = "select p00 like p01 as r1, " +
                                " p00 like p01 escape \"!\" as r2," +
                                " p02 regexp p03 as r3 " +
                          " from " + SupportBean_S0.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);

        sendS0Event("a", "b", "c", "d");
        assertReceived(new Object[][] {{"r1", false}, {"r2", false}, {"r3", false}});

        sendS0Event(null, "b", null, "d");
        assertReceived(new Object[][] {{"r1", null}, {"r2", null}, {"r3", null}});

        sendS0Event("a", null, "c", null);
        assertReceived(new Object[][] {{"r1", null}, {"r2", null}, {"r3", null}});

        sendS0Event(null, null, null, null);
        assertReceived(new Object[][] {{"r1", null}, {"r2", null}, {"r3", null}});

        sendS0Event("abcdef", "%de_", "a", "[a-c]");
        assertReceived(new Object[][] {{"r1", true}, {"r2", true}, {"r3", true}});

        sendS0Event("abcdef", "b%de_", "d", "[a-c]");
        assertReceived(new Object[][] {{"r1", false}, {"r2", false}, {"r3", false}});

        sendS0Event("!adex", "!%de_", "", ".");
        assertReceived(new Object[][] {{"r1", true}, {"r2", false}, {"r3", false}});

        sendS0Event("%dex", "!%de_", "a", ".");
        assertReceived(new Object[][] {{"r1", false}, {"r2", true}, {"r3", true}});
    }

    public void testInvalidLikeRegEx()
    {
        tryInvalid("intPrimitive like 'a' escape null");
        tryInvalid("intPrimitive like boolPrimitive");
        tryInvalid("boolPrimitive like string");
        tryInvalid("string like string escape intPrimitive");

        tryInvalid("intPrimitive regexp doublePrimitve");
        tryInvalid("intPrimitive regexp boolPrimitive");
        tryInvalid("boolPrimitive regexp string");
        tryInvalid("string regexp intPrimitive");
    }

    public void testLikeRegexNumericAndNull()
    {
        String caseExpr = "select intBoxed like '%01%' as r1, " +
                                " doubleBoxed regexp '[0-9][0-9].[0-9]' as r2 " +
                          " from " + SupportBean.class.getName();

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        selectTestCase.addListener(testListener);

        sendSupportBeanEvent(101, 1.1);
        assertReceived(new Object[][] {{"r1", true}, {"r2", false}});

        sendSupportBeanEvent(102, 11d);
        assertReceived(new Object[][] {{"r1", false}, {"r2", true}});

        sendSupportBeanEvent(null, null);
        assertReceived(new Object[][] {{"r1", null}, {"r2", null}});
    }

    private void tryInvalid(String expr)
    {
        try
        {
            String statement = "select " + expr + " from " + SupportBean.class.getName();
            epService.getEPAdministrator().createEQL(statement);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }
    }

    private void assertReceived(Object[][] objects)
    {
        EventBean event = testListener.assertOneGetNewAndReset();
        for (int i = 0; i < objects.length; i++)
        {
            String key = (String) objects[i][0];
            Object result = objects[i][1];
            assertEquals("key=" + key + " result=" + result, result, event.get(key));
        }
    }

    private void sendS0Event(String p00, String p01, String p02, String p03)
    {
        SupportBean_S0 bean = new SupportBean_S0(-1, p00, p01, p02, p03);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendSupportBeanEvent(Integer intBoxed, Double doubleBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setIntBoxed(intBoxed);
        bean.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestLikeRegexpExpr.class);
}
