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

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestFilterInAndBetween
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
	    public void TestInDynamic()
	    {
	        String expr = "select * from pattern [a=" + typeof(SupportBeanNumeric).FullName + " -> every b=" + typeof(SupportBean).FullName
	                + "(intPrimitive in (a.intOne, a.intTwo))]";
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(expr);
	        stmt.AddListener(testListener);

	        SendBeanNumeric(10, 20);
	        SendBeanInt(10);
	        Assert.IsTrue(testListener.GetAndClearIsInvoked());
	        SendBeanInt(11);
	        Assert.IsFalse(testListener.GetAndClearIsInvoked());
	        SendBeanInt(20);
	        Assert.IsTrue(testListener.GetAndClearIsInvoked());
	        stmt.Stop();

	        expr = "select * from pattern [a=" + typeof(SupportBean_S0).FullName + " -> every b=" + typeof(SupportBean).FullName
	                + "(string in (a.p00, a.p01, a.p02))]";
	        stmt = epService.EPAdministrator.CreateEQL(expr);
	        stmt.AddListener(testListener);

	        epService.EPRuntime.SendEvent(new SupportBean_S0(1, "a", "b", "c", "d"));
	        SendBeanString("a");
	        Assert.IsTrue(testListener.GetAndClearIsInvoked());
	        SendBeanString("x");
	        Assert.IsFalse(testListener.GetAndClearIsInvoked());
	        SendBeanString("b");
	        Assert.IsTrue(testListener.GetAndClearIsInvoked());
	        SendBeanString("c");
	        Assert.IsTrue(testListener.GetAndClearIsInvoked());
	        SendBeanString("d");
	        Assert.IsFalse(testListener.GetAndClearIsInvoked());
	    }

	    [Test]
	    public void TestSimpleInt()
	    {
	        String expr = "select * from " + typeof(SupportBean).FullName + "(intPrimitive in (1, 10))";
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(expr);
	        stmt.AddListener(testListener);

	        SendBeanInt(10);
	        Assert.IsTrue(testListener.GetAndClearIsInvoked());
	        SendBeanInt(11);
	        Assert.IsFalse(testListener.GetAndClearIsInvoked());
	        SendBeanInt(1);
	        Assert.IsTrue(testListener.GetAndClearIsInvoked());
	    }

	    [Test]
	    public void TestInvalid()
	    {
	        // we do not coerce
	        TryInvalid("select * from " + typeof(SupportBean).FullName + "(intPrimitive in (1L, 10L))");
	        TryInvalid("select * from " + typeof(SupportBean).FullName + "(intPrimitive in (1, 10L))");
	        TryInvalid("select * from " + typeof(SupportBean).FullName + "(intPrimitive in (1, 'x'))");

	        String expr = "select * from pattern [a=" + typeof(SupportBean).FullName + " -> b=" + typeof(SupportBean).FullName
	                + "(intPrimitive in (a.longPrimitive, a.longBoxed))]";
	        TryInvalid(expr);
	    }

	    [Test]
	    public void TestInExpr()
	    {
	        TryExpr("(string in ('a', 'b'))", "string", new String[] {"a", "x", "b", "y"}, new bool [] {true, false, true, false});
	        TryExpr("(boolPrimitive in (false))", "boolPrimitive", new Object[] {true, false}, new bool [] {false, true});
	        TryExpr("(boolPrimitive in (false, false, false))", "boolPrimitive", new Object[] {true, false}, new bool [] {false, true});
	        TryExpr("(boolPrimitive in (false, true, false))", "boolPrimitive", new Object[] {true, false}, new bool [] {true, true});
	        TryExpr("(intBoxed in (4, 6, 1))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {false, true, false, false, true, false, true});
	        TryExpr("(intBoxed in (3))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {false, false, false, true, false, false, false});
	        TryExpr("(longBoxed in (3))", "longBoxed", new Object[] {0L, 1L, 2L, 3L, 4L, 5L, 6L}, new bool [] {false, false, false, true, false, false, false});
	        TryExpr("(intBoxed between 4 and 6)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {false, false, false, false, true, true, true});
	        TryExpr("(intBoxed between 2 and 1)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {false, true, true, false, false, false, false});
	        TryExpr("(intBoxed between 4 and -1)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {true, true, true, true, true, false, false});
	        TryExpr("(intBoxed in [2:4])", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {false, false, true, true, true, false, false});
	        TryExpr("(intBoxed in (2:4])", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {false, false, false, true, true, false, false});
	        TryExpr("(intBoxed in [2:4))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {false, false, true, true, false, false, false});
	        TryExpr("(intBoxed in (2:4))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {false, false, false, true, false, false, false});

	    }

	    [Test]
	    public void TestNotInExpr()
	    {
	        TryExpr("(string not in ('a', 'b'))", "string", new String[] {"a", "x", "b", "y"}, new bool [] {false, true, false, true});
	        TryExpr("(boolPrimitive not in (false))", "boolPrimitive", new Object[] {true, false}, new bool [] {true, false});
	        TryExpr("(boolPrimitive not in (false, false, false))", "boolPrimitive", new Object[] {true, false}, new bool [] {true, false});
	        TryExpr("(boolPrimitive not in (false, true, false))", "boolPrimitive", new Object[] {true, false}, new bool [] {false, false});
	        TryExpr("(intBoxed not in (4, 6, 1))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {true, false, true, true, false, true, false});
	        TryExpr("(intBoxed not in (3))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {true, true, true, false, true, true, true});
	        TryExpr("(longBoxed not in (3))", "longBoxed", new Object[] {0L, 1L, 2L, 3L, 4L, 5L, 6L}, new bool [] {true, true, true, false, true, true, true});
	        TryExpr("(intBoxed not between 4 and 6)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {true, true, true, true, false, false, false});
	        TryExpr("(intBoxed not between 2 and 1)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {true, false, false, true, true, true, true});
	        TryExpr("(intBoxed not between 4 and -1)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {false, false, false, false, false, true, true});
	        TryExpr("(intBoxed not in [2:4])", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {true, true, false, false, false, true, true});
	        TryExpr("(intBoxed not in (2:4])", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {true, true, true, false, false, true, true});
	        TryExpr("(intBoxed not in [2:4))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {true, true, false, false, true, true, true});
	        TryExpr("(intBoxed not in (2:4))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new bool [] {true, true, true, false, true, true, true});
	    }

	    [Test]
	    public void TestReuse()
	    {
	        String expr = "select * from " + typeof(SupportBean).FullName + "(intBoxed in [2:4])";
	        TryReuse(new String[] {expr, expr});

	        expr = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (1, 2, 3))";
	        TryReuse(new String[] {expr, expr});

	        String exprOne = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (2:3])";
	        String exprTwo = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (1:3])";
	        TryReuse(new String[] {exprOne, exprTwo});

	        exprOne = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (2, 3, 4))";
	        exprTwo = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (1, 3))";
	        TryReuse(new String[] {exprOne, exprTwo});

	        exprOne = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (2, 3, 4))";
	        exprTwo = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (1, 3))";
	        String exprThree = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (8, 3))";
	        TryReuse(new String[] {exprOne, exprTwo, exprThree});

	        exprOne = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (3, 1, 3))";
	        exprTwo = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (3, 3))";
	        exprThree = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (1, 3))";
	        TryReuse(new String[] {exprOne, exprTwo, exprThree});

	        exprOne = "select * from " + typeof(SupportBean).FullName + "(boolPrimitive=false, intBoxed in (1, 2, 3))";
	        exprTwo = "select * from " + typeof(SupportBean).FullName + "(boolPrimitive=false, intBoxed in (3, 4))";
	        exprThree = "select * from " + typeof(SupportBean).FullName + "(boolPrimitive=false, intBoxed in (3))";
	        TryReuse(new String[] {exprOne, exprTwo, exprThree});

	        exprOne = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (1, 2, 3), longPrimitive >= 0)";
	        exprTwo = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (3, 4), intPrimitive >= 0)";
	        exprThree = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (3), bytePrimitive < 1)";
	        TryReuse(new String[] {exprOne, exprTwo, exprThree});
	    }

	    [Test]
	    public void TestReuseNot()
	    {
	        String expr = "select * from " + typeof(SupportBean).FullName + "(intBoxed not in [1:2])";
	        TryReuse(new String[] {expr, expr});

	        String exprOne = "select * from " + typeof(SupportBean).FullName + "(intBoxed in (3, 1, 3))";
	        String exprTwo = "select * from " + typeof(SupportBean).FullName + "(intBoxed not in (2, 1))";
	        String exprThree = "select * from " + typeof(SupportBean).FullName + "(intBoxed not between 0 and -3)";
	        TryReuse(new String[] {exprOne, exprTwo, exprThree});

	        exprOne = "select * from " + typeof(SupportBean).FullName + "(intBoxed not in (1, 4, 5))";
	        exprTwo = "select * from " + typeof(SupportBean).FullName + "(intBoxed not in (1, 4, 5))";
	        exprThree = "select * from " + typeof(SupportBean).FullName + "(intBoxed not in (4, 5, 1))";
	        TryReuse(new String[] {exprOne, exprTwo, exprThree});

	        exprOne = "select * from " + typeof(SupportBean).FullName + "(intBoxed not in (3:4))";
	        exprTwo = "select * from " + typeof(SupportBean).FullName + "(intBoxed not in [1:3))";
	        exprThree = "select * from " + typeof(SupportBean).FullName + "(intBoxed not in (1,1,1,33))";
	        TryReuse(new String[] {exprOne, exprTwo, exprThree});
	    }

	    private void TryReuse(String[] statements)
	    {
	    	SupportUpdateListener[] testListener = new SupportUpdateListener[statements.Length];
	    	EPStatement[] stmt = new EPStatement[statements.Length];

	        // create all statements
	        for (int i = 0; i < statements.Length; i++)
	        {
	            testListener[i] = new SupportUpdateListener();
	            stmt[i] = epService.EPAdministrator.CreateEQL(statements[i]);
	            stmt[i].AddListener(testListener[i]);
	        }

	        // send _event, all should receive the event
	        SendBean("intBoxed", 3);
	        for (int i = 0; i < testListener.Length; i++)
	        {
	            Assert.IsTrue(testListener[i].IsInvoked);
	            testListener[i].Reset();
	        }

	        // stop first, then second, then third etc statement
	        for (int toStop = 0; toStop < statements.Length; toStop++)
	        {
	            stmt[toStop].Stop();

	            // send _event, all remaining statement received it
	            SendBean("intBoxed", 3);
	            for (int i = 0; i <= toStop; i++)
	            {
	                Assert.IsFalse(testListener[i].IsInvoked);
	                testListener[i].Reset();
	            }
	            for (int i = toStop + 1; i < testListener.Length; i++)
	            {
	                Assert.IsTrue(testListener[i].IsInvoked);
	                testListener[i].Reset();
	            }
	        }

	        // now all statements are stopped, send event and verify no listener received
	        SendBean("intBoxed", 3);
	        for (int i = 0; i < testListener.Length; i++)
	        {
	            Assert.IsFalse(testListener[i].IsInvoked);
	        }
	    }

	    private void TryExpr(String filterExpr, String fieldName, Object[] values, bool[] isInvoked)
	    {
	        String expr = "select * from " + typeof(SupportBean).FullName + filterExpr;
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(expr);
	        stmt.AddListener(testListener);

	        for (int i = 0; i < values.Length; i++)
	        {
	            SendBean(fieldName, values[i]);
	            Assert.AreEqual(isInvoked[i], testListener.IsInvoked, "Listener invocation unexpected for " + fieldName + "=" + values[i]);
	            testListener.Reset();
	        }

	        stmt.Stop();
	    }

	    private void SendBeanInt(int intPrimitive)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetIntPrimitive(intPrimitive);
	        epService.EPRuntime.SendEvent(_event);
	    }

	    private void SendBeanString(String value)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetString(value);
	        epService.EPRuntime.SendEvent(_event);
	    }

	    private void SendBeanNumeric(int intOne, int intTwo)
	    {
	        SupportBeanNumeric num = new SupportBeanNumeric(intOne, intTwo);
	        epService.EPRuntime.SendEvent(num);
	    }

	    private void SendBean(String fieldName, Object value)
	    {
	        SupportBean _event = new SupportBean();
	        if (fieldName.Equals("string"))
	        {
	            _event.SetString((string) value);
	        }
	        if (fieldName.Equals("boolPrimitive"))
	        {
	            _event.SetBoolPrimitive((bool) value);
	        }
	        if (fieldName.Equals("intBoxed"))
	        {
	            _event.SetIntBoxed((int) value);
	        }
	        if (fieldName.Equals("longBoxed"))
	        {
	            _event.SetLongBoxed((long) value);
	        }
	        epService.EPRuntime.SendEvent(_event);
	    }

	    private void TryInvalid(String expr)
	    {
	        try
	        {
	            epService.EPAdministrator.CreateEQL(expr);
	            Assert.Fail();
	        }
	        catch (EPException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
