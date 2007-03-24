package net.esper.regression.client;

import net.esper.client.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import junit.framework.TestCase;

public class TestAggregationFunctionPlugIn extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();

        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("A", SupportMarketDataBean.class);
        configuration.addPlugInAggregationFunction("concatstring", MyConcatAggregationFunction.class.getName());
        configuration.addPlugInAggregationFunction("xxx", String.class.getName());
        epService = EPServiceProviderManager.getProvider("TestAggregationFunctionPlugIn", configuration);
        epService.initialize();
    }

    public void tearDown()
    {
        epService.initialize();
    }

    // TODO: test adding at runtime
    public void testPlugInAggregation()
    {
        String text = "select concatstring(symbol) as myvalue from A.win:length(3)";
        EPStatement stmt = epService.getEPAdministrator().createEQL(text);
        stmt.addListener(testListener);

        sendEvent("abc");
        assertReceived("abc", null);
    }

    public void testInvalid()
    {
        tryInvalid("select xxx(id) from A ", "");
    }

    private void sendEvent(String symbol)
    {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean(symbol, 0, null, null));
    }

    private void assertReceived(String newValue, String oldValue)
    {
        testListener.assertFieldEqualsAndReset("myvalue", new Object[] {newValue}, new Object[] {oldValue});
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
