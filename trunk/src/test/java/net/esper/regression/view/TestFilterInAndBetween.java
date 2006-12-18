package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;

public class TestFilterInAndBetween extends TestCase
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
        String expr = "select * from " + SupportBean.class.getName() + "(string in ('a', 'b'))";

        EPStatement selectTestCase = epService.getEPAdministrator().createEQL(expr);
        selectTestCase.addListener(testListener);

        sendAndAssertInvoked(new String[] {"a", "x", "b", "y"}, new boolean [] {true, false, true, false});
    }

    private void sendAndAssertInvoked(String[] stringValue, boolean[] isInvoked)
    {
        for (int i = 0; i < stringValue.length; i++)
        {
            sendBean(stringValue[i]);
            assertEquals(isInvoked[i], testListener.isInvoked());
            testListener.reset();
        }
    }

    private void sendBean(String string)
    {
        SupportBean event = new SupportBean();
        event.setString(string);
        epService.getEPRuntime().sendEvent(event);
    }
}
