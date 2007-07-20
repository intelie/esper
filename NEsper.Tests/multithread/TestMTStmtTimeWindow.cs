// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;
using System.Threading;

using NUnit.Framework;

using net.esper.client.time;
using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.multithread
{
	/// <summary>Test for multithread-safety of a time window -based statement.</summary>
	[TestFixture]
	public class TestMTStmtTimeWindow
	{
	    private EPServiceProvider engine;
	    private SupportMTUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetProvider("TestMTStmtTimeWindow");
	        // Need external time for this test
	        engine.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    public void TearDown()
	    {
	        engine.Initialize();
	    }

	    [Test]
	    public void testTimeWin()
	    {
	        EPStatement stmt = engine.EPAdministrator.CreateEQL(
	                " select intPrimitive, string as key " +
	                " from " + typeof(SupportBean).FullName + ".win:time(1 sec)");

	        listener = new SupportMTUpdateListener();
	        stmt.AddListener(listener);

	        TrySend(10, 5000);
	        TrySend(6, 2000);
	        TrySend(2, 10000);
	        TrySend(3, 5000);
	        TrySend(5, 2500);
	    }

	    private void TrySend(int numThreads, int numRepeats)
	    {
	        // set time to 0
	        engine.EPRuntime.SendEvent(new CurrentTimeEvent(0));

	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new SendEventCallable(i, engine, Generator.Create(numRepeats));
	            future[i] = threadPool.Submit(callable);
	        }

	        // Advance time window every 100 milliseconds for 1 second
	        for (int i = 0; i < 10; i++)
	        {
	            engine.EPRuntime.SendEvent(new CurrentTimeEvent(i * 1000));
	            Thread.Sleep(100);
	        }

	        threadPool.Shutdown();
	        threadPool.AwaitTermination(new TimeSpan(0, 0, 10));

	        for (int i = 0; i < numThreads; i++)
	        {
	            Assert.IsTrue((Boolean) future[i].Get());
	        }

	        // set time to a large value
	        engine.EPRuntime.SendEvent(new CurrentTimeEvent(10000000000L));

	        // Assert results
	        int totalExpected = numThreads * numRepeats;

	        // assert new data
	        EventBean[] resultNewData = listener.GetNewDataListFlattened();
	        Assert.AreEqual(totalExpected, resultNewData.Length);
	        EDictionary<int, IList<String>> resultsNewData = SortPerIntKey(resultNewData);
	        AssertResult(numRepeats, numThreads, resultsNewData);

	        // assert old data
	        EventBean[] resultOldData = listener.GetOldDataListFlattened();
	        Assert.AreEqual(totalExpected, resultOldData.Length);
            EDictionary<int, IList<String>> resultsOldData = SortPerIntKey(resultOldData);
	        AssertResult(numRepeats, numThreads, resultsOldData);

	        listener.Reset();
	    }

	    private EDictionary<int, IList<String>> SortPerIntKey(EventBean[] result)
	    {
	        EDictionary<int, IList<String>> results = new LinkedDictionary<int, IList<String>>();
	        foreach (EventBean _event in result)
	        {
	            int count = (int) _event["intPrimitive"];
	            String key = (String) _event["key"];

	            IList<String> entries = results.Fetch(count);
	            if (entries == null)
	            {
	                entries = new List<String>();
	                results.Put(count, entries);
	            }
	            entries.Add(key);
	        }
	        return results;
	    }

	    // Each integer value must be there with 2 entries of the same value
	    private void AssertResult(int numRepeats, int numThreads, EDictionary<int, IList<String>> results)
	    {
	        for (int i = 0; i < numRepeats; i++)
	        {
	            IList<String> values = results.Fetch(i);
	            Assert.AreEqual(numThreads, values.Count);
	            foreach (String value in values)
	            {
	                Assert.AreEqual(Convert.ToString(i), value);
	            }
	        }
	    }
	}
} // End of namespace
