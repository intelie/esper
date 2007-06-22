///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

using org.apache.commons.logging;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterServiceImpl
	{
	    private EventType eventTypeOne;
	    private EventType eventTypeTwo;
	    private FilterServiceImpl filterService;
	    private List<FilterValueSet> filterSpecs;
	    private List<SupportFilterHandle> filterCallbacks;
	    private List<EventBean> events;
	    private List<int[]> matchesExpected;

        class MyFilterServiceImpl : FilterServiceImpl
        {
        }

	    [SetUp]
	    public void SetUp()
	    {
	        filterService = new MyFilterServiceImpl();

	        eventTypeOne = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
	        eventTypeTwo = SupportEventTypeFactory.CreateBeanType(typeof(SupportBeanSimple));

	        filterSpecs = new List<FilterValueSet>();
	        filterSpecs.Add(SupportFilterSpecBuilder.Build(eventTypeOne, new Object[0]).GetValueSet(null));
	        filterSpecs.Add(SupportFilterSpecBuilder.Build(eventTypeOne, new Object[] {
	                "intPrimitive", FilterOperator.RANGE_CLOSED, 10, 20,
	                "string", FilterOperator.EQUAL, "HELLO",
	                "boolPrimitive", FilterOperator.EQUAL, false,
	                "doubleBoxed", FilterOperator.GREATER, 100d} ).GetValueSet(null));
	        filterSpecs.Add(SupportFilterSpecBuilder.Build(eventTypeTwo, new Object[0]).GetValueSet(null));
	        filterSpecs.Add(SupportFilterSpecBuilder.Build(eventTypeTwo, new Object[] {
	                "myInt", FilterOperator.RANGE_HALF_CLOSED, 1, 10,
	                "myString", FilterOperator.EQUAL, "Hello" }).GetValueSet(null));

	        // Create callbacks and add
	        filterCallbacks = new List<SupportFilterHandle>();
	        for (int i = 0; i < filterSpecs.Count; i++)
	        {
	            filterCallbacks.Add(new SupportFilterHandle());
	            filterService.Add(filterSpecs[i], filterCallbacks[i]);
	        }

	        // Create events
	        matchesExpected = new List<int[]>();
	        events = new List<EventBean>();

	        events.Add(MakeTypeOneEvent(15, "HELLO", false, 101));
	        matchesExpected.Add(new int[] {1, 1, 0, 0});

	        events.Add(MakeTypeTwoEvent("Hello", 100));
	        matchesExpected.Add(new int[] {0, 0, 1, 0});

	        events.Add(MakeTypeTwoEvent("Hello", 1));       // eventNumber = 2
	        matchesExpected.Add(new int[] {0, 0, 1, 0});

	        events.Add(MakeTypeTwoEvent("Hello", 2));
	        matchesExpected.Add(new int[] {0, 0, 1, 1});

	        events.Add(MakeTypeOneEvent(15, "HELLO", true, 100));
	        matchesExpected.Add(new int[] {1, 0, 0, 0});

	        events.Add(MakeTypeOneEvent(15, "HELLO", false, 99));
	        matchesExpected.Add(new int[] {1, 0, 0, 0});

	        events.Add(MakeTypeOneEvent(9, "HELLO", false, 100));
	        matchesExpected.Add(new int[] {1, 0, 0, 0});

	        events.Add(MakeTypeOneEvent(10, "no", false, 100));
	        matchesExpected.Add(new int[] {1, 0, 0, 0});

	        events.Add(MakeTypeOneEvent(15, "HELLO", false, 999999));      // number 8
	        matchesExpected.Add(new int[] {1, 1, 0, 0});

	        events.Add(MakeTypeTwoEvent("Hello", 10));
	        matchesExpected.Add(new int[] {0, 0, 1, 1});

	        events.Add(MakeTypeTwoEvent("Hello", 11));
	        matchesExpected.Add(new int[] {0, 0, 1, 0});
	    }

	    [Test]
	    public void TestEvalEvents()
	    {
	        for (int i = 0; i < events.Count; i++)
	        {
                IList<FilterHandle> matchList = new List<FilterHandle>();
	            filterService.Evaluate(events[i], matchList);
	            foreach (FilterHandle match in matchList)
	            {
	                SupportFilterHandle handle = (SupportFilterHandle) match;
	                handle.MatchFound(events[i]);
	            }

	            int[] matches = matchesExpected[i];

	            for (int j = 0; j < matches.Length; j++)
	            {
	                SupportFilterHandle callback = filterCallbacks[j];

	                if (matches[j] != callback.GetAndResetCountInvoked())
	                {
	                    log.Debug(".testEvalEvents Match failed, event=" + events[i].Underlying);
	                    log.Debug(".testEvalEvents Match failed, eventNumber=" + i + " index=" + j);
	                    Assert.IsTrue(false);
	                }
	            }
	        }
	    }

	    [Test]
	    public void TestInvalidType()
	    {
	        try
	        {
	            FilterValueSet spec = SupportFilterSpecBuilder.Build(eventTypeTwo, new Object[] {
	                "myString", FilterOperator.GREATER, 2 }).GetValueSet(null);
	            filterService.Add(spec, new SupportFilterHandle());
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected exception
	        }
	    }

	    [Test]
	    public void TestReusedCallback()
	    {
	        try
	        {
	            filterService.Add(filterSpecs[0], filterCallbacks[0]);
	            Assert.IsTrue(false);
	        }
	        catch (IllegalStateException ex)
	        {
	            // Expected exception
	        }
	    }

	    [Test]
	    public void TestCallbackNoFound()
	    {
	        try
	        {
	            filterService.Remove(filterCallbacks[0]);
	            filterService.Remove(filterCallbacks[0]);
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected exception
	        }
	    }

	    /**
	     * Test for removing a callback that is waiting to occur,
	     * ie. a callback is removed which was a result of an evaluation and it
	     * thus needs to be removed from the tree AND the current dispatch list.
	     */
	    [Test]
	    public void TestActiveCallbackRemove()
	    {
	        FilterValueSet spec = SupportFilterSpecBuilder.Build(eventTypeOne, new Object[0]).GetValueSet(null);
	        SupportFilterHandle callbackTwo = new SupportFilterHandle();

	        // callback that removes another matching filter spec callback
	        FilterHandleCallback callbackOne =
	        	new FilterHandleCallbackImpl(
	        		new FilterHandleCallbackDelegate(
	        			delegate(EventBean _eventInDelegate)
			            {
			                log.Debug(".matchFound Removing callbackTwo");
			                filterService.Remove(callbackTwo);
	        			}));

	        filterService.Add(spec, callbackOne);
	        filterService.Add(spec, callbackTwo);

	        // send event
	        EventBean _event = MakeTypeOneEvent(1, "HELLO", false, 1);
	        List<FilterHandle> matches = new List<FilterHandle>();
	        filterService.Evaluate(_event, matches);
	        foreach (FilterHandle match in matches)
	        {
	            FilterHandleCallback handle = (FilterHandleCallback) match;
	            handle.MatchFound(_event);
	        }

	        // Callback two MUST be invoked, was removed by callback one, but since the
	        // callback invocation order should not matter, the second one MUST also execute
	        Assert.AreEqual(1, callbackTwo.GetAndResetCountInvoked());
	    }

	    private EventBean MakeTypeOneEvent(int intPrimitive, String _string, bool boolPrimitive, double doubleBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(intPrimitive);
	        bean.SetString(_string);
	        bean.SetBoolPrimitive(boolPrimitive);
	        bean.SetDoubleBoxed(doubleBoxed);
	        return SupportEventBeanFactory.CreateObject(bean);
	    }

	    private EventBean MakeTypeTwoEvent(String myString, int myInt)
	    {
	        SupportBeanSimple bean = new SupportBeanSimple(myString, myInt);
	        return SupportEventBeanFactory.CreateObject(bean);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
