// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Threading;

using NUnit.Framework;

using net.esper.client.time;
using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.multithread
{
	/// <summary>Test for pattern statement parallel execution by threads.</summary>
	[TestFixture]
	public class TestMTStmtPattern
	{
	    private EPServiceProvider engine;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetDefaultProvider();
	        engine.Initialize();

	        // Less much debug output can be obtained by using external times
	        engine.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void testPattern()
	    {
	        String type = typeof(SupportBean).FullName;

	        String pattern = "a=" + type;
	        TryPattern(pattern, 4, 20);

	        pattern = "a=" + type + " or a=" + type;
	        TryPattern(pattern, 2, 20);
	    }

	    private void TryPattern(String pattern, int numThreads, int numEvents)
	    {
	        Object sendLock = new Object();
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new SendEventWaitCallable(i, engine, sendLock, Generator.Create(numEvents));
	            future[i] = threadPool.Submit(callable);
	        }

	        for (int i = 0; i < numEvents; i++)
	        {
	            EPStatement stmt = engine.EPAdministrator.CreatePattern(pattern);
	            SupportMTUpdateListener listener = new SupportMTUpdateListener();
	            stmt.AddListener(listener);

	            lock(sendLock)
	            {
	                Monitor.PulseAll(sendLock);
	            }
	            Thread.Sleep(100);
	            // Should be received exactly one
	            Assert.IsTrue(listener.AssertOneGetNewAndReset()["a"] is SupportBean);
	        }

	        threadPool.Shutdown();
	        threadPool.AwaitTermination(new TimeSpan(0, 0, 10));
	    }
	}
} // End of namespace
