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
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.client
{
	[TestFixture]
	public class TestEPAdministrator
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener testListener;

	    [SetUp]
	    public void SetUp()
	    {
	        testListener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	    }

	    [Test]
	    public void test1kValidStmtsPerformance()
	    {
	        long start = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 1000; i++)
	        {
	            String text = "select * from " + typeof(SupportBean).FullName;
	            EPStatement stmt = epService.EPAdministrator.CreateEQL(text, "s1");
                Assert.AreEqual("s1", stmt.Name);
	            stmt.Stop();
	            stmt.Start();
	            stmt.Stop();
	            stmt.Destroy();
	        }
	        long end = DateTimeHelper.CurrentTimeMillis;
	        long delta = end - start;
	        Assert.IsTrue(delta < 2000, ".test10kValid delta=" + delta);
	    }

	    [Test]
	    public void test1kInvalidStmts()
	    {
	        long start = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 1000; i++)
	        {
	            try
	            {
	                String text = "select xxx from " + typeof(SupportBean).FullName;
	                epService.EPAdministrator.CreateEQL(text, "s1");
	            }
	            catch (Exception ex)
	            {
	                // expected
	            }
	        }
	        long end = DateTimeHelper.CurrentTimeMillis;
	        long delta = end - start;
	        Assert.IsTrue(delta < 2500, ".test1kInvalid delta=" + delta);
	    }

	    [Test]
	    public void testGetStmtByName()
	    {
	        String[] names = new String[] {"s1", "s2", "s3--0", "s3", "s3"};
	        String[] expected = new String[] {"s1", "s2", "s3--0", "s3", "s3--1"};
	        EPStatement[] stmts = CreateStmts(names);
	        for (int i = 0; i < stmts.Length; i++)
	        {
	            Assert.AreSame(stmts[i], epService.EPAdministrator.GetStatement(expected[i]), "failed for " + names[i]);
                Assert.AreEqual(expected[i], epService.EPAdministrator.GetStatement(expected[i]).Name, "failed for " + names[i]);
	        }
	    }

	    [Test]
	    public void testStatementArray()
	    {
	        Assert.AreEqual(0, epService.EPAdministrator.StatementNames.Count);

	        String[] names = new String[] {"s1"};
	        EPStatement[] stmtsSetOne = CreateStmts(names);
	        ArrayAssertionUtil.AreEqualAnyOrder(names, epService.EPAdministrator.StatementNames);

	        names = new String[] {"s1", "s2"};
	        EPStatement[] stmtsSetTwo = CreateStmts(names);
	        ArrayAssertionUtil.AreEqualAnyOrder(new String[] {"s1", "s1--0", "s2"} , epService.EPAdministrator.StatementNames);
	    }

	    [Test]
	    public void testCreateEQLByName()
	    {
	        String stmt = "select * from " + typeof(SupportBean).FullName;
	        EPStatement stmtOne = epService.EPAdministrator.CreateEQL(stmt, "s1");
	        stmtOne.AddListener(testListener);
            Assert.AreEqual("s1", stmtOne.Name);
	        Assert.AreEqual(stmt, stmtOne.Text);

	        // check working
	        SendEvent();
	        testListener.AssertOneGetNewAndReset();

	        // create a second with the same name
	        stmt = "select intPrimitive from " + typeof(SupportBean).FullName;
	        EPStatement stmtTwo = epService.EPAdministrator.CreateEQL(stmt, "s1");
            Assert.AreEqual("s1--0", stmtTwo.Name);
	        Assert.AreEqual(stmt, stmtTwo.Text);

	        // create a third invalid statement with the same name
	        stmt = "select xxx from " + typeof(SupportBean).FullName;
	        try
	        {
	            epService.EPAdministrator.CreateEQL(stmt, "s1");
	            Assert.Fail();
	        }
	        catch (Exception ex)
	        {
	            // expected
	        }

	        // create a forth statement with the same name
	        stmt = "select string from " + typeof(SupportBean).FullName;
	        EPStatement stmtFour = epService.EPAdministrator.CreateEQL(stmt, "s1");
            Assert.AreEqual("s1--1", stmtFour.Name);
	        Assert.AreEqual(stmt, stmtFour.Text);

	        // create a fifth pattern statement with the same name
	        stmt = typeof(SupportBean).FullName;
	        EPStatement stmtFive = epService.EPAdministrator.CreatePattern(stmt, "s1");
            Assert.AreEqual("s1--2", stmtFive.Name);
	        Assert.AreEqual(stmt, stmtFive.Text);

	        try
	        {
	            epService.EPAdministrator.CreatePattern(stmt, null);
	            Assert.Fail();
	        }
	        catch (ArgumentException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testCreatePatternByName()
	    {
	        String stmt = typeof(SupportBean).FullName;
	        EPStatement stmtOne = epService.EPAdministrator.CreatePattern(stmt, "s1");
	        stmtOne.AddListener(testListener);
            Assert.AreEqual("s1", stmtOne.Name);
	        Assert.AreEqual(stmt, stmtOne.Text);

	        // check working
	        SendEvent();
	        testListener.AssertOneGetNewAndReset();

	        // create a second with the same name
	        stmt = typeof(SupportMarketDataBean).FullName;
	        EPStatement stmtTwo = epService.EPAdministrator.CreatePattern(stmt, "s1");
            Assert.AreEqual("s1--0", stmtTwo.Name);
	        Assert.AreEqual(stmt, stmtTwo.Text);

	        // create a third invalid statement with the same name
	        stmt = "xxx" + typeof(SupportBean).FullName;
	        try
	        {
	            epService.EPAdministrator.CreatePattern(stmt, "s1");
	            Assert.Fail();
	        }
	        catch (Exception ex)
	        {
	            // expected
	        }

	        // create a forth statement with the same name
	        stmt = typeof(SupportBean).FullName;
	        EPStatement stmtFour = epService.EPAdministrator.CreatePattern(stmt, "s1");
            Assert.AreEqual("s1--2", stmtFour.Name);
	        Assert.AreEqual(stmt, stmtFour.Text);

	        // create a fifth pattern statement with the same name
	        stmt = "select * from " + typeof(SupportBean).FullName;
	        EPStatement stmtFive = epService.EPAdministrator.CreateEQL(stmt, "s1");
            Assert.AreEqual("s1--3", stmtFive.Name);
	        Assert.AreEqual(stmt, stmtFive.Text);

	        try
	        {
	            epService.EPAdministrator.CreatePattern(stmt, null);
	            Assert.Fail();
	        }
	        catch (ArgumentException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testDestroyAll()
	    {
	        EPStatement[] stmts = CreateStmts(new String[] {"s1", "s2", "s3"});
	        stmts[0].AddListener(testListener);
	        stmts[1].AddListener(testListener);
	        stmts[2].AddListener(testListener);
	        SendEvent();
	        Assert.AreEqual(3, testListener.NewDataList.Count);
	        testListener.Reset();

	        epService.EPAdministrator.DestroyAllStatements();
	        AssertDestroyed(stmts);
	    }

	    [Test]
	    public void testStopStartAll()
	    {
	        EPStatement[] stmts = CreateStmts(new String[] {"s1", "s2", "s3"});
	        stmts[0].AddListener(testListener);
	        stmts[1].AddListener(testListener);
	        stmts[2].AddListener(testListener);

	        AssertStarted(stmts);

	        epService.EPAdministrator.StopAllStatements();
	        AssertStopped(stmts);

	        epService.EPAdministrator.StartAllStatements();
	        AssertStarted(stmts);

	        epService.EPAdministrator.DestroyAllStatements();
	        AssertDestroyed(stmts);
	    }

	    [Test]
	    public void testStopStartSome()
	    {
	        EPStatement[] stmts = CreateStmts(new String[] {"s1", "s2", "s3"});
	        stmts[0].AddListener(testListener);
	        stmts[1].AddListener(testListener);
	        stmts[2].AddListener(testListener);
	        AssertStarted(stmts);

	        stmts[0].Stop();
	        SendEvent();
	        Assert.AreEqual(2, testListener.NewDataList.Count);
	        testListener.Reset();

	        epService.EPAdministrator.StopAllStatements();
	        AssertStopped(stmts);

	        stmts[1].Start();
	        SendEvent();
	        Assert.AreEqual(1, testListener.NewDataList.Count);
	        testListener.Reset();

	        epService.EPAdministrator.StartAllStatements();
	        AssertStarted(stmts);

	        epService.EPAdministrator.DestroyAllStatements();
	        AssertDestroyed(stmts);
	    }

	    private void AssertStopped(EPStatement[] stmts)
	    {
	        for (int i = 0; i < stmts.Length; i++)
	        {
	            Assert.AreEqual(EPStatementState.STOPPED, stmts[i].State);
	        }
	        SendEvent();
	        Assert.AreEqual(0, testListener.NewDataList.Count);
	        testListener.Reset();
	    }

	    private void AssertStarted(EPStatement[] stmts)
	    {
	        for (int i = 0; i < stmts.Length; i++)
	        {
                Assert.AreEqual(EPStatementState.STARTED, stmts[i].State);
	        }
	        SendEvent();
	        Assert.AreEqual(stmts.Length, testListener.NewDataList.Count);
	        testListener.Reset();
	    }

	    private void AssertDestroyed(EPStatement[] stmts)
	    {
	        for (int i = 0; i < stmts.Length; i++)
	        {
                Assert.AreEqual(EPStatementState.DESTROYED, stmts[i].State);
	        }
	        SendEvent();
	        Assert.AreEqual(0, testListener.NewDataList.Count);
	        testListener.Reset();
	    }

	    private EPStatement[] CreateStmts(String[] statementNames)
	    {
	        EPStatement[] statements = new EPStatement[statementNames.Length];
	        for (int i = 0; i < statementNames.Length; i++)
	        {
	            statements[i] = epService.EPAdministrator.CreateEQL("select * from " + typeof(SupportBean).FullName, statementNames[i]);
	        }
	        return statements;
	    }

	    private EPStatement CreateStmt(String statementName)
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL("select * from " + typeof(SupportBean).FullName, statementName);
	        return stmt;
	    }

	    private void SendEvent()
	    {
	        SupportBean bean = new SupportBean();
	        epService.EPRuntime.SendEvent(bean);
	    }
	}
} // End of namespace
