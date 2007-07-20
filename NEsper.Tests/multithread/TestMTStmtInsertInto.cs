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
using net.esper.client.time;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.multithread
{
	/// <summary>
	/// Test for multithread-safety of insert-into and aggregation per group.
	/// </summary>
	[TestFixture]
	public class TestMTStmtInsertInto
	{
	    private EPServiceProvider engine;
	    private SupportMTUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;
	        engine = EPServiceProviderManager.GetDefaultProvider();
	        // Less much debug output can be obtained by using external times
	        //engine.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void testInsertInto()
	    {
	        engine.EPAdministrator.CreateEQL(
	                "insert into XStream " +
	                " select string as key, Count(*) as mycount\n" +
	                " from " + typeof(SupportBean).FullName + ".win:time(5 min)" +
	                " group by string"
	                );
	        engine.EPAdministrator.CreateEQL(
	                "insert into XStream " +
	                " select symbol as key, Count(*) as mycount\n" +
	                " from " + typeof(SupportMarketDataBean).FullName + ".win:time(5 min)" +
	                " group by symbol"
	                );

	        EPStatement stmtConsolidated = engine.EPAdministrator.CreateEQL("select key, mycount from XStream");
	        listener = new SupportMTUpdateListener();
	        stmtConsolidated.AddListener(listener);

	        TrySend(10, 5000);
	        TrySend(4, 10000);
	    }

	    private void TrySend(int numThreads, int numRepeats)
	    {
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new StmtInsertIntoCallable(Convert.ToString(i), engine, numRepeats);
	            future[i] = threadPool.Submit(callable);
	        }

	        threadPool.Shutdown();
	        threadPool.AwaitTermination(new TimeSpan(0, 0, 10));

	        for (int i = 0; i < numThreads; i++)
	        {
	            Assert.IsTrue((Boolean) future[i].Get());
	        }

	        // Assert results
	        int totalExpected = numThreads * numRepeats * 2;
	        EventBean[] result = listener.GetNewDataListFlattened();
	        Assert.AreEqual(totalExpected, result.Length);
	        EDictionary<long, Set<String>> results = new LinkedDictionary<long, Set<String>>();
	        foreach (EventBean _event in result)
	        {
	            long count = (long) _event["mycount"];
	            String key = (String) _event["key"];

	            Set<String> entries = results.Fetch(count);
	            if (entries == null)
	            {
	                entries = new HashSet<String>();
	                results.Put(count, entries);
	            }
	            entries.Add(key);
	        }

	        Assert.AreEqual(numRepeats, results.Count);
	        foreach (Set<String> value in results.Values)
	        {
	            Assert.AreEqual(2 * numThreads, value.Count);
	            for (int i = 0; i < numThreads; i++)
	            {
	                Assert.IsTrue(value.Contains("E1_" + i));
	                Assert.IsTrue(value.Contains("E2_" + i));
	            }
	        }

	        listener.Reset();
	    }
	}
} // End of namespace
