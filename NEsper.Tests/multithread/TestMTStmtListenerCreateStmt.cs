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

namespace net.esper.multithread
{
	/// <summary>Test for update listeners that create and stop statements.</summary>
	[TestFixture]
	public class TestMTStmtListenerCreateStmt
	{
	    private EPServiceProvider engine;
	    private SupportMTUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetProvider("TestMTStmtListenerCreateStmt");
	    }

	    public void TearDown()
	    {
	        engine.Initialize();
	    }

	    [Test]
	    public void testListenerCreateStmt()
	    {
	        EPStatement stmt = engine.EPAdministrator.CreateEQL(
	                " select * from " + typeof(SupportBean).FullName);

	        TryListener(2, 100, stmt);
	    }

	    private void TryListener(int numThreads, int numRepeats, EPStatement stmt)
	    {
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new StmtListenerRouteCallable(i, engine, stmt, numRepeats);
	            future[i] = threadPool.Submit(callable);
	        }

	        threadPool.Shutdown();
            threadPool.AwaitTermination(new TimeSpan(0, 0, 10));

	        for (int i = 0; i < numThreads; i++)
	        {
	            Assert.IsTrue((Boolean) future[i].Get());
	        }
	    }
	}
} // End of namespace
