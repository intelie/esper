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

using net.esper.client.time;
using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.multithread
{
	/// <summary>
	/// Test for multithread-safety (or lack thereof) for iterators: iterators fail with concurrent mods as expected behavior
	/// </summary>
	[TestFixture]
	public class TestMTStmtIterate
	{
	    private EPServiceProvider engine;
	    private SupportMTUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetProvider("TestMTStmtIterate");
	    }

	    public void TearDown()
	    {
	        engine.Initialize();
	    }

	    [Test]
	    public void testIterator()
	    {
	        EPStatement stmt = engine.EPAdministrator.CreateEQL(
	                " select string from " + typeof(SupportBean).FullName + ".win:time(5 min)");

	        /// <summary>
	        /// Iterator fail with concurrent mod exception.
	        /// (1) copy-on-write would be a performance drag
	        /// (2) clients may want to fail if a concurrent mod happened
	        /// (3) statement lock could prevent concurrent mod but could also become an issue for deadlock and lock contention
	        /// </summary>

	        /// <summary>NOTE: just 1 thread</summary>
	        TrySend(1, 10, stmt);
	    }

	    private void TrySend(int numThreads, int numRepeats, EPStatement stmt)
	    {
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new StmtIterateCallable(i, engine, stmt, numRepeats);
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
