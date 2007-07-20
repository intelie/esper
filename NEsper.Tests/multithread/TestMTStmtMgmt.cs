// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;
using System.Text;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.multithread
{
	/// <summary>
	/// Test for multithread-safety for creating and stopping various statements.
	/// </summary>
	[TestFixture]
	public class TestMTStmtMgmt
	{
	    private EPServiceProvider engine;

	    private readonly static String _event_NAME = typeof(SupportMarketDataBean).FullName;

	    private static readonly Object[][] STMT = new Object[][]
	        {
	            // true for EQL, false for Pattern; Statement text
	            new object[] {true, "select * from " + _event_NAME + " where symbol = 'IBM'"},
	            new object[] {true, "select * from " + _event_NAME + " (symbol = 'IBM')"},
	            new object[] {true, "select * from " + _event_NAME + " (price>1)"},
	            new object[] {true, "select * from " + _event_NAME + " (feed='RT')"},
	            new object[] {true, "select * from " + _event_NAME + " (symbol='IBM', price>1, feed='RT')"},
	            new object[] {true, "select * from " + _event_NAME + " (price>1, feed='RT')"},
	            new object[] {true, "select * from " + _event_NAME + " (symbol='IBM', feed='RT')"},
	            new object[] {true, "select * from " + _event_NAME + " (symbol='IBM', feed='RT') where price between 0 and 1000"},
	            new object[] {true, "select * from " + _event_NAME + " (symbol='IBM') where price between 0 and 1000 and feed='RT'"},
	            new object[] {true, "select * from " + _event_NAME + " (symbol='IBM') where 'a'='a'"},
	            new object[] {false, "every a=" + _event_NAME + "(symbol='IBM')"},
	            new object[] {false, "every a=" + _event_NAME + "(symbol='IBM', price < 1000)"},
	            new object[] {false, "every a=" + _event_NAME + "(feed='RT', price < 1000)"},
	            new object[] {false, "every a=" + _event_NAME + "(symbol='IBM', feed='RT')"},
	        };

	    [SetUp]
	    public void SetUp()
	    {
	        engine = EPServiceProviderManager.GetDefaultProvider();
	        // Less much debug output can be obtained by using external times
	        //engine.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	    }

	    [Test]
	    public void testPatterns()
	    {
	        int numThreads = 3;
	        Object[][] statements;

	        statements = new Object[][] {STMT[10]};
	        TryStatementCreateSendAndStop(numThreads, statements, 500);

	        statements = new Object[][] {STMT[10], STMT[11]};
	        TryStatementCreateSendAndStop(numThreads, statements, 500);

	        statements = new Object[][] {STMT[10], STMT[11], STMT[12]};
	        TryStatementCreateSendAndStop(numThreads, statements, 500);

	        statements = new Object[][] {STMT[10], STMT[11], STMT[12], STMT[13]};
	        TryStatementCreateSendAndStop(numThreads, statements, 500);
	    }

	    [Test]
	    public void testEachStatementAlone()
	    {
	        int numThreads = 4;
	        for (int i = 0; i < STMT.Length; i++)
	        {
	            Object[][] statements = new Object[][] {STMT[i]};
	            TryStatementCreateSendAndStop(numThreads, statements, 250);
	        }
	    }

	    [Test]
	    public void testStatementsMixed()
	    {
	        int numThreads = 2;
	        Object[][] statements = new Object[][] {STMT[1], STMT[4], STMT[6], STMT[7], STMT[8]};
	        TryStatementCreateSendAndStop(numThreads, statements, 500);

	        statements = new Object[][] {STMT[1], STMT[7], STMT[8], STMT[11], STMT[12]};
	        TryStatementCreateSendAndStop(numThreads, statements, 500);
	    }

	    [Test]
	    public void testStatementsAll()
	    {
	        int numThreads = 3;
	        TryStatementCreateSendAndStop(numThreads, STMT, 250);
	    }

	    private void TryStatementCreateSendAndStop(int numThreads, Object[][] statements, int numRepeats)
	    {
	        ExecutorService threadPool = Executors.NewFixedThreadPool(numThreads);
	        Future[] future = new Future[numThreads];
	        for (int i = 0; i < numThreads; i++)
	        {
	            Callable callable = new StmtMgmtCallable(engine, statements, numRepeats);
	            future[i] = threadPool.Submit(callable);
	        }

	        threadPool.Shutdown();
	        threadPool.AwaitTermination(new TimeSpan(0, 0, 10));

	        StringBuilder statementDigest = new StringBuilder();
	        for (int i = 0; i < statements.Length; i++)
	        {
	            statementDigest.Append(CollectionHelper.Render(statements[i]));
	        }

	        for (int i = 0; i < numThreads; i++)
	        {
	            Assert.IsTrue((Boolean) future[i].Get(),"Failed in " + statementDigest.ToString());
	        }
	    }
	}
} // End of namespace
