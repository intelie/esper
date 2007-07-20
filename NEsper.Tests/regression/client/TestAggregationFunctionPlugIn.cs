// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.util;

namespace net.esper.regression.client
{
	[TestFixture]
	public class TestAggregationFunctionPlugIn
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener testListener;

	    [SetUp]
	    public void SetUp()
	    {
	        testListener = new SupportUpdateListener();

	        Configuration configuration = new Configuration();
	        configuration.AddPlugInAggregationFunction("concatstring", typeof(MyConcatAggregationFunction).FullName);
	        epService = EPServiceProviderManager.GetProvider("TestAggregationFunctionPlugIn", configuration);
	        epService.Initialize();
	    }

	    public void TearDown()
	    {
	        epService.Initialize();
	    }

	    [Test]
	    public void testGrouped()
	    {
	        String text = "select Concatstring(string) as val from " + typeof(SupportBean).FullName + ".win:length(10) group by intPrimitive";
	        TryGrouped(text);

	        text = "select CONCATSTRING(string) as val from " + typeof(SupportBean).FullName + ".win:length(10) group by intPrimitive";
	        TryGrouped(text);
	    }

	    private void TryGrouped(String text)
	    {
	        EPStatement statement = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean("a", 1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

	        epService.EPRuntime.SendEvent(new SupportBean("b", 2));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"b"}, new Object[] {""});

	        epService.EPRuntime.SendEvent(new SupportBean("c", 1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a c"}, new Object[] {"a"});

	        epService.EPRuntime.SendEvent(new SupportBean("d", 2));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"b d"}, new Object[] {"b"});

	        epService.EPRuntime.SendEvent(new SupportBean("e", 1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a c e"}, new Object[] {"a c"});

	        epService.EPRuntime.SendEvent(new SupportBean("f", 2));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"b d f"}, new Object[] {"b d"});

	        listener.Reset();
	    }

	    [Test]
	    public void testWindow()
	    {
	        String text = "select Concatstring(string) as val from " + typeof(SupportBean).FullName + ".win:length(2)";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean("a", -1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

	        epService.EPRuntime.SendEvent(new SupportBean("b", -1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a b"}, new Object[] {"a"});

	        epService.EPRuntime.SendEvent(new SupportBean("c", -1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"b c"}, new Object[] {"a b"});

	        epService.EPRuntime.SendEvent(new SupportBean("d", -1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"c d"}, new Object[] {"b c"});
	    }

	    [Test]
	    public void testDistinct()
	    {
	        String text = "select Concatstring(distinct string) as val from " + typeof(SupportBean).FullName;
	        EPStatement statement = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean("a", -1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

	        epService.EPRuntime.SendEvent(new SupportBean("b", -1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a b"}, new Object[] {"a"});

	        epService.EPRuntime.SendEvent(new SupportBean("b", -1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a b"}, new Object[] {"a b"});

	        epService.EPRuntime.SendEvent(new SupportBean("c", -1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a b c"}, new Object[] {"a b"});

	        epService.EPRuntime.SendEvent(new SupportBean("a", -1));
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a b c"}, new Object[] {"a b c"});
	    }

	    [Test]
	    public void testArrayParams()
	    {
	    	epService.EPAdministrator.Configuration.AddPlugInAggregationFunction("countback", typeof(SupportPluginAggregationMethodOne).FullName);

	        String text = "select Countback({1,2,intPrimitive}) as val from " + typeof(SupportBean).FullName;
	        EPStatement statement = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean());
	        listener.AssertFieldEqualsAndReset("val", new Object[] {-1}, new Object[] {0});
	    }

	    [Test]
	    public void testNoSubnodesRuntimeAdd()
	    {
	    	epService.EPAdministrator.Configuration.AddPlugInAggregationFunction("countback", typeof(SupportPluginAggregationMethodOne).FullName);

	        String text = "select Countback() as val from " + typeof(SupportBean).FullName;
	        EPStatement statement = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean());
	        listener.AssertFieldEqualsAndReset("val", new Object[] {-1}, new Object[] {0});

	        epService.EPRuntime.SendEvent(new SupportBean());
	        listener.AssertFieldEqualsAndReset("val", new Object[] {-2}, new Object[] {-1});
	    }

	    [Test]
	    public void testMappedPropertyLookAlike()
	    {
	        String text = "select Concatstring('a') as val from " + typeof(SupportBean).FullName;
	        EPStatement statement = epService.EPAdministrator.CreateEQL(text);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        statement.AddListener(listener);
	        Assert.AreEqual(typeof(string), statement.EventType.GetPropertyType("val"));

	        epService.EPRuntime.SendEvent(new SupportBean());
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a"}, new Object[] {""});

	        epService.EPRuntime.SendEvent(new SupportBean());
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a a"}, new Object[] {"a"});

	        epService.EPRuntime.SendEvent(new SupportBean());
	        listener.AssertFieldEqualsAndReset("val", new Object[] {"a a a"}, new Object[] {"a a"});
	    }

	    [Test]
	    public void testFailedValidation()
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddPlugInAggregationFunction("concat", typeof(SupportPluginAggregationMethodTwo).FullName);
	        epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	        epService.Initialize();

	        try
	        {
	            String text = "select * from " + typeof(SupportBean).FullName + " group by Concat(1)";
	            epService.EPAdministrator.CreateEQL(text);
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Plug-in aggregation function 'concat' failed validation: Invalid node type: System.Int32 [select * from net.esper.support.bean.SupportBean group by Concat(1)]", ex.Message);
	        }

	        try
	        {
	            String text = "select * from " + typeof(SupportBean).FullName + " group by Concat(1, 1)";
	            epService.EPAdministrator.CreateEQL(text);
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error starting view: Plug-in aggregation function 'concat' requires a single parameter [select * from net.esper.support.bean.SupportBean group by Concat(1, 1)]", ex.Message);
	        }
	    }

	    [Test]
	    public void testInvalidUse()
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddPlugInAggregationFunction("xxx", typeof(string).FullName);
	        configuration.AddPlugInAggregationFunction("yyy", "com.NoSuchClass");
	        epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	        epService.Initialize();

	        try
	        {
	            String text = "select * from " + typeof(SupportBean).FullName + " group by Xxx(1)";
	            epService.EPAdministrator.CreateEQL(text);
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error resolving aggregation: Aggregation class by name 'System.String' does not subclass AggregationSupport [select * from net.esper.support.bean.SupportBean group by Xxx(1)]", ex.Message);
	        }

	        try
	        {
	            String text = "select * from " + typeof(SupportBean).FullName + " group by Yyy(1)";
	            epService.EPAdministrator.CreateEQL(text);
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error resolving aggregation: Could not load aggregation class by name 'com.NoSuchClass' [select * from net.esper.support.bean.SupportBean group by Yyy(1)]", ex.Message);
	        }
	    }

	    [Test]
	    public void testInvalidConfigure()
	    {
	        TryInvalidConfigure("a b", "MyClass");
	        TryInvalidConfigure("abc", "My Type");

	        // configure twice
	        Configuration configuration = new Configuration();
	        configuration.AddPlugInAggregationFunction("abcdef", typeof(string).FullName);
	        configuration.AddPlugInAggregationFunction("abcdef", typeof(string).FullName);
	        try
	        {
	            EPServiceProvider engine = EPServiceProviderManager.GetDefaultProvider(configuration);
	            engine.Initialize();
	            Assert.Fail();
	        }
	        catch (ConfigurationException ex)
	        {
	            Console.WriteLine(ex.Message);
	            // expected
	        }
	    }

	    private void TryInvalidConfigure(String funcName, String className)
	    {
	        try
	        {
	            Configuration configuration = new Configuration();
	            configuration.AddPlugInAggregationFunction(funcName, className);
	            EPServiceProvider provider = EPServiceProviderManager.GetDefaultProvider(configuration);
	            provider.Initialize();
	            Assert.Fail();
	        }
	        catch (ConfigurationException ex)
	        {
	            Console.WriteLine(ex.Message);
	            // expected
	        }
	    }

	    [Test]
	    public void testInvalid()
	    {
	        TryInvalid("select Xxx(id) from A ", "Unknown method named 'xxx' could not be resolved [select Xxx(id) from A ]");
	    }

	    private void SendEvent(String symbol)
	    {
	        epService.EPRuntime.SendEvent(new SupportMarketDataBean(symbol, 0, null, null));
	    }

	    private void AssertReceived(String newValue, String oldValue)
	    {
	        testListener.AssertFieldEqualsAndReset("myvalue", new Object[] {newValue}, new Object[] {oldValue});
	    }

	    private void TryInvalid(String stmtText, String expectedMsg)
	    {
	        try
	        {
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual(expectedMsg, ex.Message);
	        }
	    }
	}
} // End of namespace
