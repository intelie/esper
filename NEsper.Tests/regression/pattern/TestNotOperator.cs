///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.regression.support;
using net.esper.support.bean;

namespace net.esper.regression.pattern
{
	[TestFixture]
	public class TestNotOperator : SupportBeanConstants
	{
	    [Test]
	    public void TestOp()
	    {
	        EventCollection events = EventCollectionFactory.GetEventSetOne(0, 1000);
	        CaseList testCaseList = new CaseList();
	        EventExpressionCase testCase = null;

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " and not d=" + EVENT_D_CLASS);
	        testCase.Add("B1", "b", events.GetEvent("B1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " and not g=" + EVENT_G_CLASS);
	        testCase.Add("B1", "b", events.GetEvent("B1"));
	        testCase.Add("B2", "b", events.GetEvent("B2"));
	        testCase.Add("B3", "b", events.GetEvent("B3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " and not d=" + EVENT_D_CLASS);
	        testCase.Add("B1", "b", events.GetEvent("B1"));
	        testCase.Add("B2", "b", events.GetEvent("B2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " and not a=" + EVENT_A_CLASS + "(id='A1')");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " and not a2=" + EVENT_A_CLASS + "(id='A2')");
	        testCase.Add("B1", "b", events.GetEvent("B1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " and not b3=" + EVENT_B_CLASS + "(id='B3'))");
	        testCase.Add("B1", "b", events.GetEvent("B1"));
	        testCase.Add("B2", "b", events.GetEvent("B2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " or not " + EVENT_D_CLASS + "())");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (every b=" + EVENT_B_CLASS + " and not " + EVENT_B_CLASS + "(id='B2'))");
	        testCase.Add("B1", "b", events.GetEvent("B1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") and " +
	                " not " + EVENT_A_CLASS);
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("(b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") and " +
                    " not " + EVENT_G_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") and " +
                    " not " + EVENT_G_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ") and " +
                    " not " + EVENT_G_CLASS + "(id='x')");
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
	        testCaseList.AddTest(testCase);

	        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
	        util.RunTest();

	        /**
	         * As of release 1.6 this no longer updates listeners when the statement is started.
	         * The reason is that the dispatch view only gets attached after a pattern started, therefore
	         * ZeroDepthEventStream looses the event.
	         * There should be no use case requiring this
	         *
	        testCase = new EventExpressionCase("not (b=" + EVENT_B_CLASS + " -> d=" + EVENT_D_CLASS + ")");
	        testCase.Add(EventCollection.ON_START__event_ID);
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("not a=" + EVENT_A_CLASS);
	        testCase.Add(EventCollection.ON_START__event_ID);
	        testCaseList.AddTest(testCase);
	         */
	    }

	    [Test]
	    public void TestUniformEvents()
	    {
	        EventCollection events = EventCollectionFactory.GetSetTwoExternalClock(0, 1000);
	        CaseList results = new CaseList();
	        EventExpressionCase desc = null;

	        desc = new EventExpressionCase("every a=" + EVENT_A_CLASS + "() and not a1=" + EVENT_A_CLASS + "(id=\"A4\")");
	        desc.Add("B1", "a", events.GetEvent("B1"));
	        desc.Add("B2", "a", events.GetEvent("B2"));
	        desc.Add("B3", "a", events.GetEvent("B3"));
	        results.AddTest(desc);

	        PatternTestHarness util = new PatternTestHarness(events, results);
	        util.RunTest();
	    }
	}
} // End of namespace
