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
	/// <summary>
	/// Test for multithread-safety of adding emitted event listeners and receiving events through emitted event listeners.
	/// </summary>
	[TestFixture]
	public class TestMTEmitListener
	{
	    private EPServiceProvider engine;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetDefaultProvider();
	    }

	    [Test]
	    public void testEQL()
	    {
	        TryEmitAndListen(10, 1000);
	        TryEmitAndListen(4, 1000);
	    }

	    private void TryEmitAndListen(int numThreads, int numRepeats)
	    {
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new EmitListenerCallable(i, engine, numRepeats);
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
