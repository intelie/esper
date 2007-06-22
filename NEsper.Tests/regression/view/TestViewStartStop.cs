///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestViewStartStop
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener testListener;
	    private EPStatement sizeView;

	    [SetUp]
	    public void SetUp()
	    {
	        testListener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();

	        String viewExpr = "select * from " + typeof(SupportBean).FullName + ".std:size()";

	        sizeView = epService.EPAdministrator.CreateEQL(viewExpr);
	    }

	    [Test]
	    public void TestSameWindowReuse()
	    {
	        String viewExpr = "select * from " + typeof(SupportBean).FullName + ".win:length(3)";
	        EPStatement stmtOne = epService.EPAdministrator.CreateEQL(viewExpr);
	        stmtOne.AddListener(testListener);

	        // send a couple of events
	        SendEvent(1);
	        SendEvent(2);
	        SendEvent(3);
	        SendEvent(4);

	        // create same statement again
	        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
	        EPStatement stmtTwo = epService.EPAdministrator.CreateEQL(viewExpr);
            stmtTwo.AddListener(testListenerTwo);

	        // Send event, no old data should be received
	        SendEvent(5);
	        Assert.IsNull(testListenerTwo.LastOldData);
	    }

	    [Test]
	    public void TestStartStop()
	    {
	        // View created is automatically started
	        Assert.AreEqual(0l, CollectionHelper.First(sizeView)["size"]);
	        sizeView.Stop();

	        // Send an event, view stopped
	        SendEvent();
	        Assert.IsNull(sizeView.GetEnumerator());

	        // Start view
	        sizeView.Start();
	        Assert.AreEqual(0l, CollectionHelper.First(sizeView)["size"]);

	        // Send event
	        SendEvent();
	        Assert.AreEqual(1l, CollectionHelper.First(sizeView)["size"]);

	        // Stop view
	        sizeView.Stop();
            Assert.IsNull(sizeView.GetEnumerator());

	        // Start again, iterator is zero
	        sizeView.Start();
            Assert.AreEqual(0l, CollectionHelper.First(sizeView)["size"]);
	    }

	    [Test]
	    public void TestAddRemoveListener()
	    {
	        // View is started when created

	        // Add listener send event
	        sizeView.AddListener(testListener);
	        Assert.IsNull(testListener.LastNewData);
            Assert.AreEqual(0l, CollectionHelper.First(sizeView)["size"]);
	        SendEvent();
	        Assert.AreEqual(1l, testListener.GetAndResetLastNewData()[0]["size"]);
            Assert.AreEqual(1l, CollectionHelper.First(sizeView)["size"]);

	        // Stop view, send event, view
	        sizeView.Stop();
	        SendEvent();
            Assert.IsNull(sizeView.GetEnumerator());
	        Assert.IsNull(testListener.LastNewData);

	        // Start again
            sizeView.RemoveListener(testListener);
	        sizeView.AddListener(testListener);
	        sizeView.Start();

	        SendEvent();
	        Assert.AreEqual(1l, testListener.GetAndResetLastNewData()[0]["size"]);
            Assert.AreEqual(1l, CollectionHelper.First(sizeView)["size"]);

	        // Stop again, leave listeners
	        sizeView.Stop();
	        sizeView.Start();
	        SendEvent();
	        Assert.AreEqual(1l, testListener.GetAndResetLastNewData()[0]["size"]);

	        // Remove listener, send event
            sizeView.RemoveListener(testListener);
	        SendEvent();
	        Assert.IsNull(testListener.LastNewData);

	        // Add listener back, send event
	        sizeView.AddListener(testListener);
	        SendEvent();
	        Assert.AreEqual(3l, testListener.GetAndResetLastNewData()[0]["size"]);
	    }

	    private void SendEvent()
	    {
	        SendEvent(-1);
	    }

	    private void SendEvent(int intPrimitive)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(intPrimitive);
	        epService.EPRuntime.SendEvent(bean);
	    }
	}
} // End of namespace
