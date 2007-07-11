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
using net.esper.regression.support;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.pattern
{
	[TestFixture]
	public class TestComplexPropertyAccess
	{
	    private static String _event_COMPLEX = typeof(SupportBeanComplexProps).FullName;
	    private static String _event_NESTED = typeof(SupportBeanCombinedProps).FullName;

	    [Test]
	    public void testComplexProperties()
	    {
	        EventCollection events = EventCollectionFactory.GetSetSixComplexProperties();
	        CaseList testCaseList = new CaseList();
	        EventExpressionCase testCase = null;

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(mapped('keyOne') = 'valueOne')");
	        testCase.Add("e1", "s", events.GetEvent("e1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(indexed[1] = 2)");
	        testCase.Add("e1", "s", events.GetEvent("e1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(indexed[0] = 2)");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(arrayProperty[1] = 20)");
	        testCase.Add("e1", "s", events.GetEvent("e1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(arrayProperty[1] in (10:30))");
	        testCase.Add("e1", "s", events.GetEvent("e1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(arrayProperty[2] = 20)");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(nested.nestedValue = 'nestedValue')");
	        testCase.Add("e1", "s", events.GetEvent("e1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(nested.nestedValue = 'dummy')");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(nested.nestedNested.nestedNestedValue = 'nestedNestedValue')");
	        testCase.Add("e1", "s", events.GetEvent("e1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_COMPLEX + "(nested.nestedNested.nestedNestedValue = 'x')");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_NESTED + "(indexed[1].Mapped('1mb').value = '1ma1')");
	        testCase.Add("e2", "s", events.GetEvent("e2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_NESTED + "(indexed[0].Mapped('1ma').value = 'x')");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_NESTED + "(array[0].Mapped('0ma').value = '0ma0')");
	        testCase.Add("e2", "s", events.GetEvent("e2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_NESTED + "(array[2].Mapped('x').value = 'x')");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_NESTED + "(array[879787].Mapped('x').value = 'x')");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("s=" + _event_NESTED + "(array[0].Mapped('xxx').value = 'x')");
	        testCaseList.AddTest(testCase);

	        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
	        util.RunTest();
	    }

	    [Test]
	    public void testIndexedFilterProp()
	    {
	        SupportUpdateListener testListener = new SupportUpdateListener();
	        EPServiceProvider epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();

	        String type = typeof(SupportBeanComplexProps).FullName;
	        String pattern = "every a=" + type + "(indexed[0]=3)";

	        EPStatement stmt = epService.EPAdministrator.CreatePattern(pattern);
	        stmt.AddListener(testListener);

	        Object _event = new SupportBeanComplexProps(new int[] { 3, 4});
	        epService.EPRuntime.SendEvent(_event);
	        Assert.AreSame(_event, testListener.AssertOneGetNewAndReset()["a"]);

	        _event = new SupportBeanComplexProps(new int[] { 6});
	        epService.EPRuntime.SendEvent(_event);
	        Assert.IsFalse(testListener.IsInvoked);

	        _event = new SupportBeanComplexProps(new int[] { 3});
	        epService.EPRuntime.SendEvent(_event);
	        Assert.AreSame(_event, testListener.AssertOneGetNewAndReset()["a"]);
	    }

	    [Test]
	    public void testIndexedValueProp()
	    {
	        SupportUpdateListener testListener = new SupportUpdateListener();
	        EPServiceProvider epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();

	        String type = typeof(SupportBeanComplexProps).FullName;
	        String pattern = "every a=" + type + " -> b=" + type + "(indexed[0] = a.indexed[0])";

	        EPStatement stmt = epService.EPAdministrator.CreatePattern(pattern);
	        stmt.AddListener(testListener);

	        Object eventOne = new SupportBeanComplexProps(new int[] {3});
	        epService.EPRuntime.SendEvent(eventOne);
	        Assert.IsFalse(testListener.IsInvoked);

	        Object _event = new SupportBeanComplexProps(new int[] { 6});
	        epService.EPRuntime.SendEvent(_event);
	        Assert.IsFalse(testListener.IsInvoked);

	        Object eventTwo = new SupportBeanComplexProps(new int[] { 3});
	        epService.EPRuntime.SendEvent(eventTwo);
	        EventBean _eventBean = testListener.AssertOneGetNewAndReset();
	        Assert.AreSame(eventOne, _eventBean["a"]);
	        Assert.AreSame(eventTwo, _eventBean["b"]);
	    }
	}


} // End of namespace
