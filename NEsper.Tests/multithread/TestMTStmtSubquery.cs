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
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.multithread
{
	/// <summary>Test for multithread-safety of a subquery statement.</summary>
	[TestFixture]
	public class TestMTStmtSubquery
	{
	    private EPServiceProvider engine;
	    private SupportMTUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        Configuration config = new Configuration();
	        config.AddEventTypeAlias("S0", typeof(SupportBean_S0));
	        config.AddEventTypeAlias("S1", typeof(SupportBean_S1));
	        engine = EPServiceProviderManager.GetProvider("TestMTStmtSubquery", config);
	        // Use external time for this test, since time is not used here
	        engine.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    public void TearDown()
	    {
	        engine.Initialize();
	    }

	    [Test]
	    public void testSubquery()
	    {
	        TrySend(4, 10000);
	        TrySend(3, 10000);
	        TrySend(2, 10000);
	    }

	    private void TrySend(int numThreads, int numRepeats)
	    {
	        EPStatement stmt = engine.EPAdministrator.CreateEQL(
	                "select (select id from S0.win:length(1000000) where id = s1.id) as value from S1 as s1");

	        listener = new SupportMTUpdateListener();
	        stmt.AddListener(listener);

	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new StmtSubqueryCallable(i, engine, numRepeats);
	            future[i] = threadPool.Submit(callable);
	        }

	        threadPool.Shutdown();
            threadPool.AwaitTermination(new TimeSpan(0, 0, 10));

	        for (int i = 0; i < numThreads; i++)
	        {
	            Assert.IsTrue((Boolean) future[i].Get());
	        }

	        // Assert results
	        int totalExpected = numThreads * numRepeats;

	        // assert new data
	        EventBean[] resultNewData = listener.GetNewDataListFlattened();
	        Assert.AreEqual(totalExpected, resultNewData.Length);

	        Set<int> values = new HashSet<int>();
	        foreach (EventBean _event in resultNewData)
	        {
	            values.Add((int)_event["value"]);
	        }
	        Assert.AreEqual(totalExpected, values.Count, "Unexpected duplicates");

	        listener.Reset();
	        stmt.Stop();
	    }
	}
} // End of namespace
