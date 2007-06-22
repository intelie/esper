///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestInBetweenLikeExpr
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
	    public void TestInStringExpr()
	    {
	        TryString("string in ('a', 'b', 'c')",
	                    new String[] {"0", "a", "b", "c", "d", null},
	                    new bool[] {false, true, true, true, false, false});

	        TryString("string in ('a')",
	                    new String[] {"0", "a", "b", "c", "d", null},
	                    new bool[] {false, true, false, false, false, false});

	        TryString("string in ('a', 'b')",
	                    new String[] {"0", "b", "a", "c", "d", null},
	                    new bool[] {false, true, true, false, false, false});

	        TryString("string in ('a', null)",
	                    new String[] {"0", "b", "a", "c", "d", null},
	                    new bool[] {false, false, true, false, false, true});

	        TryString("string in (null)",
	                    new String[] {"0", null, "b"},
	                    new bool[] {false, true, false});

	        TryString("string not in ('a', 'b', 'c')",
	                    new String[] {"0", "a", "b", "c", "d", null},
	                    new bool[] {true, false, false, false, true, true});

	        TryString("string not in (null)",
	                    new String[] {"0", null, "b"},
	                    new bool[] {true, false, true});
	    }

	    [Test]
	    public void TestBetweenStringExpr()
	    {
	        String[] input = null;
	        bool[] result = null;

	        input = new String[] {"0",    "a1", "a10", "c", "d",    null, "a0", "b9", "b90"};
	        result = new bool[] {false, true, true, false, false, false, true, true, false};
	        TryString("string between 'a0' and 'b9'", input, result);
	        TryString("string between 'b9' and 'a0'", input, result);

	        TryString("string between null and 'b9'",
	                    new String[] {"0", null, "a0", "b9"},
	                    new bool[] {false, false, false, false});

	        TryString("string between null and null",
	                    new String[] {"0", null, "a0", "b9"},
	                    new bool[] {false, false, false, false});

	        TryString("string between 'a0' and null",
	                    new String[] {"0", null, "a0", "b9"},
	                    new bool[] {false, false, false, false});

	        input = new String[] {"0",    "a1", "a10", "c", "d",    null, "a0", "b9", "b90"};
	        result = new bool[] {true, false, false, true, true, false, false, false, true};
	        TryString("string not between 'a0' and 'b9'", input, result);
	        TryString("string not between 'b9' and 'a0'", input, result);
	    }

	    [Test]
	    public void TestInNumericExpr()
	    {
	        double?[] input = new double?[] {1d, null, 1.1d, 1.0d, 1.0999999999, 2d, 4d};
	        bool[] result = new bool[] {false, false, true, false, false, true, true};
	        TryNumeric("doubleBoxed in (1.1d, 7/3, 2*7/3, 0)", input, result);

	        TryNumeric("doubleBoxed in (7/3d, null)",
	                    new double?[] {2d, 7/3d, null},
	                    new bool[] {false, true, true});

	        TryNumeric("doubleBoxed in (5,5,5,5,5, -1)",
	                    new double?[] {5.0, 5d, 0d, null, -1d},
	                    new bool[] {true, true, false, false, true});

	        TryNumeric("doubleBoxed not in (1.1d, 7/3, 2*7/3, 0)",
	                    new double?[] {1d, null, 1.1d, 1.0d, 1.0999999999, 2d, 4d},
	                    new bool[] {true, true, false, true, true, false, false});
	    }

	    [Test]
	    public void TestBetweenNumericExpr()
	    {
	        double?[] input = new double?[] {1d, null, 1.1d, 2d, 1.0999999999, 2d, 4d, 15d, 15.00001d};
	        bool[] result = new bool[] {false, false, true, true, false, true, true, true, false};
	        TryNumeric("doubleBoxed between 1.1 and 15", input, result);
	        TryNumeric("doubleBoxed between 15 and 1.1", input, result);

	        TryNumeric("doubleBoxed between null and 15",
	                    new double?[] {1d, null, 1.1d},
	                    new bool[] {false, false, false});

	        TryNumeric("doubleBoxed between 15 and null",
	                    new double?[] {1d, null, 1.1d},
	                    new bool[] {false, false, false});

	        TryNumeric("doubleBoxed between null and null",
	                    new double?[] {1d, null, 1.1d},
	                    new bool[] {false, false, false});

	        input = new double?[] {1d, null, 1.1d, 2d, 1.0999999999, 2d, 4d, 15d, 15.00001d};
	        result = new bool[] {true, false, false, false, true, false, false, false, true};
	        TryNumeric("doubleBoxed not between 1.1 and 15", input, result);
	        TryNumeric("doubleBoxed not between 15 and 1.1", input, result);

	        TryNumeric("doubleBoxed not between 15 and null",
	                    new double?[] {1d, null, 1.1d},
	                    new bool[] {false, false, false});
	    }

	    [Test]
	    public void TestInBoolExpr()
	    {
	        TryInBoolean("boolBoxed in (true, true)",
	                    new Boolean[] {true, false},
	                    new bool[] {true, false});

	        TryInBoolean("boolBoxed in (1>2, 2=3, 4<=2)",
	                    new Boolean[] {true, false},
	                    new bool[] {false, true});

	        TryInBoolean("boolBoxed not in (1>2, 2=3, 4<=2)",
	                    new Boolean[] {true, false},
	                    new bool[] {true, false});
	    }

	    [Test]
	    public void TestInNumericCoercionLong()
	    {
	        String caseExpr = "select intPrimitive in (shortBoxed, intBoxed, longBoxed) as result from " + typeof(SupportBean).FullName;

	        EPStatement selectTestCase = epService.EPAdministrator.CreateEQL(caseExpr);
	        selectTestCase.AddListener(testListener);
	        Assert.AreEqual(typeof(Boolean), selectTestCase.EventType.GetPropertyType("result"));

	        SendAndAssert(1, 2, 3, 4L, false);
	        SendAndAssert(1, 1, 3, 4L, true);
	        SendAndAssert(1, 3, 1, 4L, true);
	        SendAndAssert(1, 3, 7, 1L, true);
	        SendAndAssert(1, 3, 7, null, false);
	        SendAndAssert(1, 1, null, null, true);
	        SendAndAssert(1, 0, null, 1L, true);

	        selectTestCase.Stop();
	    }

	    [Test]
	    public void TestInNumericCoercionDouble()
	    {
	        String caseExpr = "select intBoxed in (floatBoxed, doublePrimitive, longBoxed) as result from " + typeof(SupportBean).FullName;

	        EPStatement selectTestCase = epService.EPAdministrator.CreateEQL(caseExpr);
	        selectTestCase.AddListener(testListener);
	        Assert.AreEqual(typeof(Boolean), selectTestCase.EventType.GetPropertyType("result"));

	        SendAndAssert(1, 2f, 3d, 4L, false);
	        SendAndAssert(1, 1f, 3d, 4L, true);
	        SendAndAssert(1, 1.1f, 1.0d, 4L, true);
	        SendAndAssert(1, 1.1f, 1.2d, 1L, true);
	        SendAndAssert(1, null, 1.2d, 1L, true);
	        SendAndAssert(null, null, 1.2d, 1L, true);
	        SendAndAssert(null, 11f, 1.2d, 1L, false);

	        selectTestCase.Stop();
	    }

	    [Test]
	    public void TestBetweenNumericCoercionLong()
	    {
	        String caseExpr = "select intPrimitive between shortBoxed and longBoxed as result from " + typeof(SupportBean).FullName;

	        EPStatement selectTestCase = epService.EPAdministrator.CreateEQL(caseExpr);
	        selectTestCase.AddListener(testListener);
	        Assert.AreEqual(typeof(Boolean), selectTestCase.EventType.GetPropertyType("result"));

	        SendAndAssert(1, 2, 3L, false);
	        SendAndAssert(2, 2, 3L, true);
	        SendAndAssert(3, 2, 3L, true);
	        SendAndAssert(4, 2, 3L, false);
	        SendAndAssert(5, 10, 1L, true);
	        SendAndAssert(1, 10, 1L, true);
	        SendAndAssert(10, 10, 1L, true);
	        SendAndAssert(11, 10, 1L, false);

	        selectTestCase.Stop();
	    }

	    [Test]
	    public void TestBetweenNumericCoercionDouble()
	    {
	        String caseExpr = "select intBoxed between floatBoxed and doublePrimitive as result from " + typeof(SupportBean).FullName;

	        EPStatement selectTestCase = epService.EPAdministrator.CreateEQL(caseExpr);
	        selectTestCase.AddListener(testListener);
	        Assert.AreEqual(typeof(Boolean), selectTestCase.EventType.GetPropertyType("result"));

	        SendAndAssert(1, 2f, 3d, false);
	        SendAndAssert(2, 2f, 3d, true);
	        SendAndAssert(3, 2f, 3d, true);
	        SendAndAssert(4, 2f, 3d, false);
	        SendAndAssert(null, 2f, 3d, false);
	        SendAndAssert(null, null, 3d, false);
	        SendAndAssert(1, 3f, 2d, false);
	        SendAndAssert(2, 3f, 2d, true);
	        SendAndAssert(3, 3f, 2d, true);
	        SendAndAssert(4, 3f, 2d, false);
	        SendAndAssert(null, 3f, 2d, false);
	        SendAndAssert(null, null, 2d, false);

	        selectTestCase.Stop();
	    }

	    private void SendAndAssert(int? intBoxed, float? floatBoxed, double doublePrimitive, bool result)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntBoxed(intBoxed);
	        bean.SetFloatBoxed(floatBoxed);
	        bean.SetDoublePrimitive(doublePrimitive);

	        epService.EPRuntime.SendEvent(bean);

	        EventBean _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual(result, _event["result"]);
	    }

	    private void SendAndAssert(int intPrimitive, int shortBoxed, int? intBoxed, long? longBoxed, bool result)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(intPrimitive);
	        bean.SetShortBoxed( (short) shortBoxed);
	        bean.SetIntBoxed(intBoxed);
	        bean.SetLongBoxed(longBoxed);

	        epService.EPRuntime.SendEvent(bean);

	        EventBean _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual(result, _event["result"]);
	    }

	    private void SendAndAssert(int intPrimitive, int shortBoxed, long? longBoxed, bool result)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(intPrimitive);
	        bean.SetShortBoxed( (short) shortBoxed);
	        bean.SetLongBoxed(longBoxed);

	        epService.EPRuntime.SendEvent(bean);

	        EventBean _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual(result, _event["result"]);
	    }

	    private void SendAndAssert(int? intBoxed, float? floatBoxed, double doublePrimitve, long? longBoxed, bool result)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntBoxed(intBoxed);
	        bean.SetFloatBoxed(floatBoxed);
	        bean.SetDoublePrimitive(doublePrimitve);
	        bean.SetLongBoxed(longBoxed);

	        epService.EPRuntime.SendEvent(bean);

	        EventBean _event = testListener.AssertOneGetNewAndReset();
	        Assert.AreEqual(result, _event["result"]);
	    }

        private void TryInBoolean(String expr, Boolean[] input, bool[] result)
	    {
	        String caseExpr = "select " + expr + " as result from " + typeof(SupportBean).FullName;

	        EPStatement selectTestCase = epService.EPAdministrator.CreateEQL(caseExpr);
	        selectTestCase.AddListener(testListener);
	        Assert.AreEqual(typeof(Boolean), selectTestCase.EventType.GetPropertyType("result"));

	        for (int i = 0; i < input.Length; i++)
	        {
	            SendSupportBeanEvent(input[i]);
	            EventBean _event = testListener.AssertOneGetNewAndReset();
                Assert.AreEqual(result[i], _event["result"], "Wrong result for " + input[i]);
	        }
	        selectTestCase.Stop();
	    }

        private void TryNumeric(String expr, double?[] input, bool[] result)
	    {
	        String caseExpr = "select " + expr + " as result from " + typeof(SupportBean).FullName;

	        EPStatement selectTestCase = epService.EPAdministrator.CreateEQL(caseExpr);
	        selectTestCase.AddListener(testListener);
	        Assert.AreEqual(typeof(Boolean), selectTestCase.EventType.GetPropertyType("result"));

	        for (int i = 0; i < input.Length; i++)
	        {
	            SendSupportBeanEvent(input[i]);
	            EventBean _event = testListener.AssertOneGetNewAndReset();
                Assert.AreEqual(result[i], _event["result"], "Wrong result for " + input[i]);
	        }
	        selectTestCase.Stop();
	    }

	    private void TryString(String expression, String[] input, bool[] result)
	    {
	        String caseExpr = "select " + expression + " as result from " + typeof(SupportBean).FullName;

	        EPStatement selectTestCase = epService.EPAdministrator.CreateEQL(caseExpr);
	        selectTestCase.AddListener(testListener);
	        Assert.AreEqual(typeof(Boolean), selectTestCase.EventType.GetPropertyType("result"));

	        for (int i = 0; i < input.Length; i++)
	        {
	            SendSupportBeanEvent(input[i]);
	            EventBean _event = testListener.AssertOneGetNewAndReset();
                Assert.AreEqual(result[i], _event["result"], "Wrong result for " + input[i]);
	        }
	        selectTestCase.Stop();
	    }

	    private void SendSupportBeanEvent(double? doubleBoxed)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetDoubleBoxed(doubleBoxed);
	        epService.EPRuntime.SendEvent(_event);
	    }

	    private void SendSupportBeanEvent(string _string)
	    {
	        SupportBean _event = new SupportBean();
            _event.SetString(_string);
	        epService.EPRuntime.SendEvent(_event);
	    }

	    private void SendSupportBeanEvent(bool boolBoxed)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetBoolBoxed(boolBoxed);
	        epService.EPRuntime.SendEvent(_event);
	    }
	}
} // End of namespace
