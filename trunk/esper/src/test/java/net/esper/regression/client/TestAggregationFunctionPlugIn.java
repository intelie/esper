package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.eql.SupportPluginAggregationMethodTwo;
import net.esper.support.eql.SupportPluginAggregationMethodOne;
import net.esper.support.util.SupportUpdateListener;

public class TestAggregationFunctionPlugIn extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();

        Configuration configuration = new Configuration();
        configuration.addPlugInAggregationFunction("concatstring", MyConcatAggregationFunction.class.getName());
        epService = EPServiceProviderManager.getProvider("TestAggregationFunctionPlugIn", configuration);
        epService.initialize();
    }

    public void tearDown()
    {
        epService.initialize();
    }

    public void testGrouped()
    {
        String text = "select concatstring(string) as val from " + SupportBean.class.getName() + ".win:length(10) group by intPrimitive";
        tryGrouped(text);

        text = "select CONCATSTRING(string) as val from " + SupportBean.class.getName() + ".win:length(10) group by intPrimitive";
        tryGrouped(text);
    }

    private void tryGrouped(String text)
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("a", 1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean("b", 2));
        listener.assertFieldEqualsAndReset("val", new Object[] {"b"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean("c", 1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a c"}, new Object[] {"a"});

        epService.getEPRuntime().sendEvent(new SupportBean("d", 2));
        listener.assertFieldEqualsAndReset("val", new Object[] {"b d"}, new Object[] {"b"});

        epService.getEPRuntime().sendEvent(new SupportBean("e", 1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a c e"}, new Object[] {"a c"});

        epService.getEPRuntime().sendEvent(new SupportBean("f", 2));
        listener.assertFieldEqualsAndReset("val", new Object[] {"b d f"}, new Object[] {"b d"});

        listener.reset();
    }

    public void testWindow()
    {
        String text = "select concatstring(string) as val from " + SupportBean.class.getName() + ".win:length(2)";
        EPStatement statement = epService.getEPAdministrator().createEQL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("a", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean("b", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b"}, new Object[] {"a"});

        epService.getEPRuntime().sendEvent(new SupportBean("c", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"b c"}, new Object[] {"a b"});

        epService.getEPRuntime().sendEvent(new SupportBean("d", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"c d"}, new Object[] {"b c"});
    }

    public void testDistinct()
    {
        String text = "select concatstring(distinct string) as val from " + SupportBean.class.getName();
        EPStatement statement = epService.getEPAdministrator().createEQL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("a", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean("b", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b"}, new Object[] {"a"});

        epService.getEPRuntime().sendEvent(new SupportBean("b", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b"}, new Object[] {"a b"});

        epService.getEPRuntime().sendEvent(new SupportBean("c", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b c"}, new Object[] {"a b"});

        epService.getEPRuntime().sendEvent(new SupportBean("a", -1));
        listener.assertFieldEqualsAndReset("val", new Object[] {"a b c"}, new Object[] {"a b c"});
    }

    public void testArrayParams()
    {
        epService.getEPAdministrator().getConfiguration().addPlugInAggregationFunction("countback", SupportPluginAggregationMethodOne.class.getName());

        String text = "select countback({1,2,intPrimitive}) as val from " + SupportBean.class.getName();
        EPStatement statement = epService.getEPAdministrator().createEQL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {-1}, new Object[] {0});
    }

    public void testNoSubnodesRuntimeAdd()
    {
        epService.getEPAdministrator().getConfiguration().addPlugInAggregationFunction("countback", SupportPluginAggregationMethodOne.class.getName());
        
        String text = "select countback() as val from " + SupportBean.class.getName();
        EPStatement statement = epService.getEPAdministrator().createEQL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {-1}, new Object[] {0});

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {-2}, new Object[] {-1});
    }

    public void testMappedPropertyLookAlike()
    {
        String text = "select concatstring('a') as val from " + SupportBean.class.getName();
        EPStatement statement = epService.getEPAdministrator().createEQL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);
        assertEquals(String.class, statement.getEventType().getPropertyType("val"));

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {"a a"}, new Object[] {"a"});

        epService.getEPRuntime().sendEvent(new SupportBean());
        listener.assertFieldEqualsAndReset("val", new Object[] {"a a a"}, new Object[] {"a a"});
    }

    public void testFailedValidation()
    {
        Configuration configuration = new Configuration();
        configuration.addPlugInAggregationFunction("concat", SupportPluginAggregationMethodTwo.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        try
        {
            String text = "select * from " + SupportBean.class.getName() + " group by concat(1)";
            epService.getEPAdministrator().createEQL(text);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Plug-in aggregation function 'concat' failed validation: Invalid node type: java.lang.Integer [select * from net.esper.support.bean.SupportBean group by concat(1)]", ex.getMessage());
        }

        try
        {
            String text = "select * from " + SupportBean.class.getName() + " group by concat(1, 1)";
            epService.getEPAdministrator().createEQL(text);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Plug-in aggregation function 'concat' requires a single parameter [select * from net.esper.support.bean.SupportBean group by concat(1, 1)]", ex.getMessage());
        }
    }

    public void testInvalidUse()
    {
        Configuration configuration = new Configuration();
        configuration.addPlugInAggregationFunction("xxx", String.class.getName());
        configuration.addPlugInAggregationFunction("yyy", "com.NoSuchClass");
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        try
        {
            String text = "select * from " + SupportBean.class.getName() + " group by xxx(1)";
            epService.getEPAdministrator().createEQL(text);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error resolving aggregation: Aggregation class by name 'java.lang.String' does not subclass AggregationSupport [select * from net.esper.support.bean.SupportBean group by xxx(1)]", ex.getMessage());
        }

        try
        {
            String text = "select * from " + SupportBean.class.getName() + " group by yyy(1)";
            epService.getEPAdministrator().createEQL(text);
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error resolving aggregation: Could not load aggregation class by name 'com.NoSuchClass' [select * from net.esper.support.bean.SupportBean group by yyy(1)]", ex.getMessage());
        }
    }
    
    public void testInvalidConfigure()
    {
        tryInvalidConfigure("a b", "MyClass");
        tryInvalidConfigure("abc", "My Class");

        // configure twice
        Configuration configuration = new Configuration();
        configuration.addPlugInAggregationFunction("abcdef", String.class.getName());
        configuration.addPlugInAggregationFunction("abcdef", String.class.getName());
        try
        {
            EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(configuration);
            engine.initialize();
            fail();
        }
        catch (ConfigurationException ex)
        {
            System.out.println(ex.getMessage());
            // expected
        }
    }

    private void tryInvalidConfigure(String funcName, String className)
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.addPlugInAggregationFunction(funcName, className);
            EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider(configuration);
            provider.initialize();
            fail();
        }
        catch (ConfigurationException ex)
        {
            System.out.println(ex.getMessage());
            // expected
        }        
    }

    public void testInvalid()
    {
        tryInvalid("select xxx(id) from A ", "Unknown method named 'xxx' could not be resolved [select xxx(id) from A ]");
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