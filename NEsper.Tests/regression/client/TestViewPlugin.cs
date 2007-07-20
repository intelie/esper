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
using net.esper.support.util;

namespace net.esper.regression.client
{
	[TestFixture]
	public class TestViewPlugin
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener testListener;

	    [SetUp]
	    public void SetUp()
	    {
	        testListener = new SupportUpdateListener();

	        Configuration configuration = new Configuration();
	        configuration.AddEventTypeAlias("A", typeof(SupportMarketDataBean));
	        configuration.AddPlugInView("mynamespace", "trendspotter", typeof(MyTrendSpotterViewFactory).FullName);
	        configuration.AddPlugInView("mynamespace", "invalid", typeof(string).FullName);
	        epService = EPServiceProviderManager.GetProvider("TestViewPlugin", configuration);
	        epService.Initialize();
	    }

	    public void TearDown()
	    {
	        epService.Initialize();
	    }

	    [Test]
	    public void testPlugInView()
	    {
	        String text = "select * from A.mynamespace:trendspotter('price')";
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(text);
	        stmt.AddListener(testListener);

	        SendEvent(10);
	        AssertReceived(1L, null);

	        SendEvent(11);
	        AssertReceived(2L, 1L);

	        SendEvent(12);
	        AssertReceived(3L, 2L);

	        SendEvent(11);
	        AssertReceived(0L, 3L);

	        SendEvent(12);
	        AssertReceived(1L, 0L);

	        SendEvent(0);
	        AssertReceived(0L, 1L);

	        SendEvent(0);
	        AssertReceived(0L, 0L);

	        SendEvent(1);
	        AssertReceived(1L, 0L);

	        SendEvent(1);
	        AssertReceived(1L, 1L);

	        SendEvent(2);
	        AssertReceived(2L, 1L);

	        SendEvent(2);
	        AssertReceived(2L, 2L);
	    }

	    [Test]
	    public void testInvalid()
	    {
	        TryInvalid("select * from A.mynamespace:xxx()",
	                "Error starting view: View name 'mynamespace:xxx' is not a known view name [select * from A.mynamespace:xxx()]");
	        TryInvalid("select * from A.mynamespace:invalid()", "Error starting view: Error casting view factory instance to net.esper.view.ViewFactory interface for view 'invalid' [select * from A.mynamespace:invalid()]");
	    }

	    private void SendEvent(double price)
	    {
	        epService.EPRuntime.SendEvent(new SupportMarketDataBean("", price, null, null));
	    }

	    private void AssertReceived(long? newTrendCount, long? oldTrendCount)
	    {
	        testListener.AssertFieldEqualsAndReset("trendcount", new Object[] {newTrendCount}, new Object[] {oldTrendCount});
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
