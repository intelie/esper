// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client.time;
using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.multithread
{
	/// <summary>Test for multithread-safety for joins.</summary>
	[TestFixture]
	public class TestMTStmtJoin
	{
	    private EPServiceProvider engine;

	    private readonly static String _event_NAME = typeof(SupportBean).FullName;

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetProvider("TestMTStmtJoin");
	    }

	    public void TearDown()
	    {
	        engine.Initialize();
	    }

	    [Test]
	    public void testJoin()
	    {
	        EPStatement stmt = engine.EPAdministrator.CreateEQL("select istream * \n" +
	                "  from " + _event_NAME + "(string='s0').win:length(1000000) as s0,\n" +
	                "       " + _event_NAME + "(string='s1').win:length(1000000) as s1\n" +
	                "where s0.longPrimitive = s1.longPrimitive\n"
	                );
	        TrySendAndReceive(4, stmt, 1000);
	        TrySendAndReceive(2, stmt, 2000);
	    }

	    private void TrySendAndReceive(int numThreads, EPStatement statement, int numRepeats)
	    {
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new StmtJoinCallable(i, engine, statement, numRepeats);
	            future[i] = threadPool.Submit(callable);
	        }

	        threadPool.Shutdown();
            threadPool.AwaitTermination(new TimeSpan(0, 0, 10));

	        for (int i = 0; i < numThreads; i++)
	        {
	            Assert.IsTrue((Boolean) future[i].Get(),"Failed in " + statement.Text);
	        }
	    }
	}
} // End of namespace
