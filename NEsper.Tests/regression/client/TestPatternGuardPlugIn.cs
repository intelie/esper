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
	public class TestPatternGuardPlugIn
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddPlugInPatternGuard("myplugin", "count_to", typeof(MyCountToPatternGuardFactory).FullName);
	        configuration.AddEventTypeAlias("Bean", typeof(SupportBean).FullName);
	        epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	        epService.Initialize();

	        listener = new SupportUpdateListener();
	    }

	    [Test]
	    public void testGuard()
	    {
	        String stmtText = "select * from pattern [(every Bean) where myplugin:count_to(10)]";
	        EPStatement statement = epService.EPAdministrator.CreateEQL(stmtText);
	        statement.AddListener(listener);

	        for (int i = 0; i < 10; i++)
	        {
	            epService.EPRuntime.SendEvent(new SupportBean());
	            Assert.IsTrue(listener.IsInvoked);
	            listener.Reset();
	        }

	        epService.EPRuntime.SendEvent(new SupportBean());
	        Assert.IsFalse(listener.IsInvoked);
	    }

	    [Test]
	    public void testInvalid()
	    {
	        try
	        {
	            Configuration configuration = new Configuration();
	            configuration.AddPlugInPatternGuard("namespace", "name", typeof(string).FullName);
	            epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	            epService.Initialize();
	            String stmtText = "select * from pattern [every " + typeof(SupportBean).FullName +
	                               " where namespace:name(10)]";
	            epService.EPAdministrator.CreateEQL(stmtText);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            Assert.AreEqual("Error casting guard factory instance to net.esper.pattern.guard.GuardFactory interface for guard 'name' [select * from pattern [every net.esper.support.bean.SupportBean where namespace:name(10)]]", ex.Message);
	        }
	    }


	}
} // End of namespace
