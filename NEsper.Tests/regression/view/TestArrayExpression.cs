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
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestArrayExpression
	{
	    // for use in testing a static method accepting array parameters
	    private static int[] callbackInts;
	    private static String[] callbackStrings;
	    private static Object[] callbackObjects;

	    private EPServiceProvider epService;

        [SetUp]
	    protected void SetUp()
	    {
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	    }

	    [Test]
	    public void testArrayExpressions()
	    {
	        String stmtText = "select {'a', 'b'} as stringArray," +
	                              "{} as emptyArray," +
	                              "{1} as oneEleArray," +
	                              "{1,2,3} as intArray," +
	                              "{1,null} as intNullArray," +
	                              "{1L,10L} as longArray," +
	                              "{'a',1, 1e20} as mixedArray," +
	                              "{1, 1.1d, 1e20} as doubleArray," +
	                              "{5, 6L} as intLongArray," +
	                              "{null} as nullArray," +
	                              typeof(TestArrayExpression).FullName + ".DoIt({'a'}, {1}, {1, 'd', null, true}) as func," +
	                              "{true, false} as boolArray," +
	                              "{intPrimitive} as dynIntArr," +
	                              "{intPrimitive, longPrimitive} as dynLongArr," +
	                              "{intPrimitive, string} as dynMixedArr," +
	                              "{intPrimitive, intPrimitive * 2, intPrimitive * 3} as dynCalcArr," +
	                              "{longBoxed, doubleBoxed * 2, string || 'a'} as dynCalcArrNulls" +
	                              " from " + typeof(SupportBean).FullName;

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmt.AddListener(listener);

	        SupportBean bean = new SupportBean("a", 10);
	        bean.SetLongPrimitive(999);
	        epService.EPRuntime.SendEvent(bean);

	        EventBean _event = listener.AssertOneGetNewAndReset();
	        ArrayAssertionUtil.AreEqualExactOrder(new String[] {"a", "b"}, (String[]) _event["stringArray"]);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[0], (Object[]) _event["emptyArray"]);
            ArrayAssertionUtil.AreEqualExactOrder(new int?[] { 1 }, (int?[])_event["oneEleArray"]);
            ArrayAssertionUtil.AreEqualExactOrder(new int?[] { 1, 2, 3 }, (int?[])_event["intArray"]);
            ArrayAssertionUtil.AreEqualExactOrder(new int?[] { 1, null }, (int?[])_event["intNullArray"]);
	        ArrayAssertionUtil.AreEqualExactOrder(new long?[] {1L,10L}, (long?[]) _event["longArray"]);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {"a", 1, 1e20}, (Object[]) _event["mixedArray"]);
	        ArrayAssertionUtil.AreEqualExactOrder(new double?[] {1d, 1.1,1e20}, (double?[]) _event["doubleArray"]);
            ArrayAssertionUtil.AreEqualExactOrder(new long?[] { 5L, 6L }, (long?[])_event["intLongArray"]);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {null}, (Object[]) _event["nullArray"]);
	        ArrayAssertionUtil.AreEqualExactOrder(new String[] {"a", "b"}, (String[]) _event["func"]);
	        ArrayAssertionUtil.AreEqualExactOrder(new bool?[] {true, false}, (bool?[]) _event["boolArray"]);
            ArrayAssertionUtil.AreEqualExactOrder(new int?[] { 10 }, (int?[])_event["dynIntArr"]);
            ArrayAssertionUtil.AreEqualExactOrder(new long?[] { 10L, 999L }, (long?[])_event["dynLongArr"]);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {10, "a"}, (Object[]) _event["dynMixedArr"]);
            ArrayAssertionUtil.AreEqualExactOrder(new int?[] { 10, 20, 30 }, (int?[])_event["dynCalcArr"]);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {null, null, "aa"}, (Object[]) _event["dynCalcArrNulls"]);

	        // assert function parameters
            ArrayAssertionUtil.AreEqualExactOrder(new int[] { 1 }, callbackInts);
	        ArrayAssertionUtil.AreEqualExactOrder(new String[] {"a"}, callbackStrings);
	        ArrayAssertionUtil.AreEqualExactOrder(new Object[] {1, "d", null, true}, callbackObjects);
	    }

	    [Test]
	    public void testComplexTypes()
	    {
	        String stmtText = "select {arrayProperty, nested} as field" +
	                              " from " + typeof(SupportBeanComplexProps).FullName;

	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        SupportUpdateListener listener = new SupportUpdateListener();
	        stmt.AddListener(listener);

	        SupportBeanComplexProps bean = SupportBeanComplexProps.MakeDefaultBean();
	        epService.EPRuntime.SendEvent(bean);

	        EventBean _event = listener.AssertOneGetNewAndReset();
	        Object[] arr = (Object[]) _event["field"];
	        Assert.AreSame(bean.ArrayProperty, arr[0]);
	        Assert.AreSame(bean.Nested, arr[1]);
	    }

	    // for testing EQL static method call
	    public static String[] DoIt(String[] strings, int[] ints, Object[] objects)
	    {
	        callbackInts = ints;
	        callbackStrings = strings;
	        callbackObjects = objects;
	        return new String[] {"a", "b"};
	    }
	}
} // End of namespace
