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

using org.apache.commons.logging;

namespace net.esper.multithread
{
	/// <summary>
	/// Test for multithread-safety of statements that are very similar that is share the same filter and views.
	/// &lt;p&gt;
	/// The engine shares locks between statements that share filters and views.
	/// </summary>
	[TestFixture]
	public class TestMTStmtSharedView
	{
	    private static String[] SYMBOLS = {"IBM", "MSFT", "GE"};
	    private EPServiceProvider engine;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetProvider("TestMTStmtSharedView");
	    }

	    public void TearDown()
	    {
	        engine.Initialize();
	    }

	    [Test]
	    public void testSharedViews()
	    {
	        TrySend(4, 1000, 100);
	        //trySend(2, 1000, 100);
	        //trySend(3, 2000, 20);
	    }

	    private void TrySend(int numThreads, int numRepeats, int numStatements)
	    {
	        // Create same statement X times
	        EPStatement[] stmt = new EPStatement[numStatements];
	        SupportMTUpdateListener[] listeners = new SupportMTUpdateListener[stmt.Length];
	        for (int i = 0; i < stmt.Length; i++)
	        {
	            stmt[i] = engine.EPAdministrator.CreateEQL(
	                " select * " +
	                " from " + typeof(SupportMarketDataBean).FullName + ".std:groupby('symbol').stat:uni('price')");
	            listeners[i] = new SupportMTUpdateListener();
                stmt[i].AddListener(listeners[i]);
	        }

	        // Start send threads
	        // Each threads sends each symbol with price = 0 to numRepeats
	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new StmtSharedViewCallable(numRepeats, engine, SYMBOLS);
	            future[i] = threadPool.Submit(callable);
	        }

	        // Shut down
	        threadPool.Shutdown();
            threadPool.AwaitTermination(new TimeSpan(0, 0, 10));
	        for (int i = 0; i < numThreads; i++)
	        {
	            Assert.IsTrue((Boolean) future[i].Get());
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;
	        Assert.IsTrue(delta < 5000,"delta=" + delta + " not less then 5 sec");

	        // Assert results
	        foreach (SupportMTUpdateListener listener in listeners)
	        {
	            Assert.AreEqual(numRepeats * numThreads * SYMBOLS.Length, listener.NewDataList.Count);
	            EventBean[] newDataLast = listener.NewDataList[listener.NewDataList.Count - 1];
	            Assert.AreEqual(1, newDataLast.Length);
	            EventBean result = newDataLast[0];
	            Assert.AreEqual(numRepeats*numThreads, ((long) result["count"]));
	            Assert.IsTrue(Array.IndexOf(SYMBOLS, "symbol") != -1);
	            Assert.AreEqual(SumToN(numRepeats) * numThreads, result["sum"]);
	            listener.Reset();
	        }

	        for (int i = 0; i < stmt.Length; i++)
	        {
	            stmt[i].Stop();
	        }
	    }

	    private double SumToN(int N)
	    {
	        double sum = 0;
	        for (int i = 0; i < N; i++)
	        {
	            sum += i;
	        }
	        return sum;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
