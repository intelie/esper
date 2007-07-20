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
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.multithread
{
	/// <summary>
	/// Test for multithread-safety for a simple aggregation case using Count(*).
	/// </summary>
	[TestFixture]
	public class TestMTStmtFilter
	{
	    private EPServiceProvider engine;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetProvider("TestMTStmtFilter");
	    }

	    public void TearDown()
	    {
	        engine.Initialize();
	    }

	    [Test]
	    public void testCount()
	    {
	        TryCount(2, 1000);
	        TryCount(4, 1000);
	    }

	    public void TryCount(int numThreads, int numMessages)
	    {
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        EPStatement stmt = engine.EPAdministrator.CreateEQL("select Count(*) as mycount from " + typeof(SupportBean).FullName);
	        MTListener listener = new MTListener("mycount");
	        stmt.AddListener(listener);

	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            future[i] = threadPool.Submit(new SendEventCallable(i, engine, Generator.Create(numMessages)));
	        }

	        threadPool.Shutdown();
	        threadPool.AwaitTermination(new TimeSpan(0, 0, 10));

	        for (int i = 0; i < numThreads; i++)
	        {
	            Assert.IsTrue((Boolean) future[i].Get());
	        }

	        // verify results
	        Assert.AreEqual(numMessages * numThreads, listener.Values.Count);
	        TreeSet<int> result = new TreeSet<int>();
	        foreach (Object row in listener.Values)
	        {
	            result.Add(Convert.ToInt32(row));
	        }
	        Assert.AreEqual(numMessages * numThreads, result.Count);
	        Assert.AreEqual(1, (Object) result.First);
	        Assert.AreEqual(numMessages * numThreads, result.Last);
	    }
	}
} // End of namespace
