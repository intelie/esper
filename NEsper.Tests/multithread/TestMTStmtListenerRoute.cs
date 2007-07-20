// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.multithread
{
	/// <summary>Test for update listeners that route events.</summary>
	[TestFixture]
	public class TestMTStmtListenerRoute
	{
	    private EPServiceProvider engine;
	    private SupportMTUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetDefaultProvider();
	    }

	    [Test]
	    public void testListenerCreateStmt()
	    {
	        TryListener(4, 500);
	    }

	    private void TryListener(int numThreads, int numRoutes)
	    {
	        EPStatement stmtTrigger = engine.EPAdministrator.CreateEQL(
	                " select * from " + typeof(SupportBean).FullName);

	        EPStatement stmtListen = engine.EPAdministrator.CreateEQL(
	                " select * from " + typeof(SupportMarketDataBean).FullName);
	        SupportMTUpdateListener listener = new SupportMTUpdateListener();
	        stmtListen.AddListener(listener);

	        // Set of events routed by each listener
	        Set<SupportMarketDataBean> routed = new SynchronizedSet<SupportMarketDataBean>(new HashSet<SupportMarketDataBean>());

	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new StmtListenerCreateStmtCallable(i, engine, stmtTrigger, numRoutes, routed);
	            future[i] = threadPool.Submit(callable);
	        }

	        threadPool.Shutdown();
            threadPool.AwaitTermination(new TimeSpan(0, 0, 10));

	        for (int i = 0; i < numThreads; i++)
	        {
	            Assert.IsTrue((Boolean)future[i].Get());
	        }

	        // assert
	        EventBean[] results = listener.GetNewDataListFlattened();
	        Assert.IsTrue(results.Length >= numThreads * numRoutes);

	        foreach (SupportMarketDataBean routedEvent in routed)
	        {
	            bool found = false;
	            for (int i = 0; i < results.Length; i++)
	            {
	                if (results[i].Underlying == routedEvent)
	                {
	                    found = true;
	                }
	            }
	            Assert.IsTrue(found);
	        }
	    }
	}
} // End of namespace
