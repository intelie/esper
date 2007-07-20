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
using net.esper.support.bean;
using net.esper.support.eql;

namespace net.esper.multithread
{
	/// <summary>Test for multithread-safety for database joins.</summary>
	[TestFixture]
	public class TestMTStmtDatabaseJoin
	{
	    private EPServiceProvider engine;

	    private readonly static String _event_NAME = typeof(SupportBean).FullName;

	    [SetUp]
	    public void SetUp()
	    {
	        ConfigurationDBRef configDB = new ConfigurationDBRef();
	        configDB.SetDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
	        configDB.ConnectionLifecycle = (ConnectionLifecycleEnum.RETAIN);
	        configDB.ConnectionCatalog = ("test");
	        configDB.ConnectionReadOnly = (true);
	        configDB.ConnectionTransactionIsolation = (1);
	        configDB.ConnectionAutoCommit = (true);
	        Configuration configuration = new Configuration();
	        configuration.AddDatabaseReference("MyDB", configDB);

	        engine = EPServiceProviderManager.GetProvider("TestMTStmtDatabaseJoin", configuration);
	    }

	    public void TearDown()
	    {
	        engine.Initialize();
	    }

	    [Test]
	    public void TestJoin()
	    {
	        EPStatement stmt = engine.EPAdministrator.CreateEQL("select * \n" +
	                "  from " + _event_NAME + ".win:length(1000) as s0,\n" +
	                "      sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.mybigint'] as s1"
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
	            Callable callable = new StmtDatabaseJoinCallable(engine, statement, numRepeats);
	            future[i] = threadPool.Submit(callable);
	        }

	        threadPool.Shutdown();
	        threadPool.AwaitTermination(10, TimeUnit.SECONDS);

	        for (int i = 0; i < numThreads; i++)
	        {
                Assert.IsTrue((bool)future[i].Get(), "Failed in " + statement.Text);
	        }
	    }
	}
} // End of namespace
