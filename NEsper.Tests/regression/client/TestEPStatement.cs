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
	public class TestEPStatement
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
	    public void testStartedDestroy()
	    {
	        String text = "select * from " + typeof(SupportBean).FullName;
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(text, "s1");
	        stmt.AddListener(testListener);
	        SendEvent();
	        testListener.AssertOneGetNewAndReset();

	        stmt.Destroy();
	        SendEvent();
	        Assert.IsFalse(testListener.IsInvoked);

	        AssertStmtDestroyed(stmt, text);
	    }

	    [Test]
	    public void testStopDestroy()
	    {
	        String text = "select * from " + typeof(SupportBean).FullName;
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(text, "s1");
	        stmt.AddListener(testListener);
	        SendEvent();
	        testListener.AssertOneGetNewAndReset();

	        stmt.Stop();
	        SendEvent();
	        Assert.IsFalse(testListener.IsInvoked);

	        stmt.Destroy();
	        SendEvent();
	        Assert.IsFalse(testListener.IsInvoked);

	        AssertStmtDestroyed(stmt, text);
	    }

	    private void AssertStmtDestroyed(EPStatement stmt, String text)
	    {
            Assert.AreEqual(EPStatementState.DESTROYED, stmt.State);
	        Assert.AreEqual(text, stmt.Text);
            Assert.AreEqual("s1", stmt.Name);
	        Assert.IsNull(epService.EPAdministrator.GetStatement("s1"));
	        ArrayAssertionUtil.AreEqualAnyOrder(new String[0], epService.EPAdministrator.StatementNames);

	        try
	        {
	            stmt.Destroy();
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	            Assert.AreEqual("Statement already destroyed", ex.Message);
	        }

	        try
	        {
	            stmt.Start();
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	            Assert.AreEqual("Cannot start statement, statement is in destroyed state", ex.Message);
	        }

	        try
	        {
	            stmt.Stop();
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	            Assert.AreEqual("Cannot stop statement, statement is in destroyed state", ex.Message);
	        }
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
