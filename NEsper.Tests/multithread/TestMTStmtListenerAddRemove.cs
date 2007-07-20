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
	/// <summary>Test for multithread-safety for adding and removing listener.</summary>
	[TestFixture]
	public class TestMTStmtListenerAddRemove
	{
	    private EPServiceProvider engine;

	    private readonly static String _event_NAME = typeof(SupportMarketDataBean).FullName;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetProvider("TestMTStmtListenerAddRemove");
	        // Less much debug output can be obtained by using external times
	        //engine.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    public void TearDown()
	    {
	        engine.Initialize();
	    }

	    [Test]
	    public void testPatterns()
	    {
	        EPStatement stmt = engine.EPAdministrator.CreatePattern("every a=" + _event_NAME + "(symbol='IBM')");
	        int numThreads = 2;
	        TryStatementListenerAddRemove(numThreads, stmt, false, 10000);
	    }

	    [Test]
	    public void testEQL()
	    {
	        EPStatement stmt = engine.EPAdministrator.CreateEQL("select * from " + _event_NAME + " (symbol='IBM', feed='RT')");
	        int numThreads = 2;
	        TryStatementListenerAddRemove(numThreads, stmt, true, 10000);
	    }

	    private void TryStatementListenerAddRemove(int numThreads, EPStatement statement, bool isEQL, int numRepeats)
	    {
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new StmtListenerAddRemoveCallable(engine, statement, isEQL, numRepeats);
	            future[i] = threadPool.Submit(callable);
	        }

	        threadPool.Shutdown();
	        threadPool.AwaitTermination(new TimeSpan(0, 0, 0, 10));

	        for (int i = 0; i < numThreads; i++)
	        {
                Assert.IsTrue((Boolean)future[i].Get(), "Failed stmt=" + statement.Text);
	        }
	    }
	}
} // End of namespace
