package net.esper.regression.client;

import net.esper.client.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import junit.framework.TestCase;

public class TestViewPlugin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();

        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("A", SupportMarketDataBean.class);
        configuration.addPlugInView("mynamespace", "trendspotter", MyTrendSpotterViewFactory.class.getName());
        configuration.addPlugInView("mynamespace", "invalid", String.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testPlugInView()
    {
        String text = "select * from A.mynamespace:trendspotter('price')";
        EPStatement stmt = epService.getEPAdministrator().createEQL(text);
        stmt.addListener(testListener);

        sendEvent(10);
        assertReceived(1L, null);

        sendEvent(11);
        assertReceived(2L, 1L);

        sendEvent(12);
        assertReceived(3L, 2L);

        sendEvent(11);
        assertReceived(0L, 3L);

        sendEvent(12);
        assertReceived(1L, 0L);

        sendEvent(0);
        assertReceived(0L, 1L);

        sendEvent(0);
        assertReceived(0L, 0L);

        sendEvent(1);
        assertReceived(1L, 0L);

        sendEvent(1);
        assertReceived(1L, 1L);

        sendEvent(2);
        assertReceived(2L, 1L);

        sendEvent(2);
        assertReceived(2L, 2L);
    }

    public void testInvalid()
    {
        tryInvalid("select * from A.mynamespace:xxx()",
                "Error starting view: View name 'xxx' is not a known view name [select * from A.mynamespace:xxx()]");
        tryInvalid("select * from A.mynamespace:invalid()", "Error starting view: Error casting view factory instance to net.esper.view.ViewFactory interface for view 'invalid' [select * from A.mynamespace:invalid()]");
    }

    private void sendEvent(double price)
    {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("", price, null, null));
    }

    private void assertReceived(Long newTrendCount, Long oldTrendCount)
    {
        testListener.assertFieldEqualsAndReset("trendcount", new Object[] {newTrendCount}, new Object[] {oldTrendCount});
    }

    private void tryInvalid(String stmtText, String expectedMsg)
    {
        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(expectedMsg, ex.getMessage());
        }
    }
}
