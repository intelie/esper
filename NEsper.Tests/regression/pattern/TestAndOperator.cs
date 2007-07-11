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
	public class TestAndOperator : SupportBeanConstants
	{
	    [Test]
	    public void testOp()
	    {
	        EventCollection events = EventCollectionFactory.GetEventSetOne(0, 1000);
	        CaseList testCaseList = new CaseList();
	        EventExpressionCase testCase;

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " and every d=" + EVENT_D_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " and d=" + EVENT_D_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " and d=" + EVENT_D_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every(b=" + EVENT_B_CLASS + " and d=" + EVENT_D_CLASS + ")");
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every( b=" + EVENT_B_CLASS + " and every d=" + EVENT_D_CLASS + ")");
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D2"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every b=" + EVENT_B_CLASS + " and every d=" + EVENT_D_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"));
	        testCase.Add("D2", "b", events.GetEvent("B2"), "d", events.GetEvent("D2"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D1"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D2"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"));
	        testCase.Add("D3", "b", events.GetEvent("B2"), "d", events.GetEvent("D3"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every( every b=" + EVENT_B_CLASS + " and d=" + EVENT_D_CLASS + ")");
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D1"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D2"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every a=" + EVENT_A_CLASS + " and d=" + EVENT_D_CLASS + " and b=" + EVENT_B_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"), "a", events.GetEvent("A1"));
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"), "a", events.GetEvent("A2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every( every b=" + EVENT_B_CLASS + " and every d=" + EVENT_D_CLASS + ")");
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"));
	        testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"));
	        testCase.Add("D2", "b", events.GetEvent("B2"), "d", events.GetEvent("D2"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D1"));
	        for (int i = 0; i < 3; i++)
	        {
	            testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D2"));
	        }
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"));
	        testCase.Add("D3", "b", events.GetEvent("B2"), "d", events.GetEvent("D3"));
	        for (int i = 0; i < 5; i++)
	        {
	            testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"));
	        }
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("a=" + EVENT_A_CLASS + " and d=" + EVENT_D_CLASS + " and b=" + EVENT_B_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"), "a", events.GetEvent("A1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every a=" + EVENT_A_CLASS + " and every d=" + EVENT_D_CLASS + " and b=" + EVENT_B_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"), "a", events.GetEvent("A1"));
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"), "a", events.GetEvent("A2"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"), "a", events.GetEvent("A1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"), "a", events.GetEvent("A2"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"), "a", events.GetEvent("A1"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"), "a", events.GetEvent("A2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + " and b=" + EVENT_B_CLASS);
	        testCase.Add("B1", "b", events.GetEvent("B1"), "b", events.GetEvent("B1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every a=" + EVENT_A_CLASS + " and every d=" + EVENT_D_CLASS + " and every b=" + EVENT_B_CLASS);
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"), "a", events.GetEvent("A1"));
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"), "a", events.GetEvent("A2"));
	        testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"), "a", events.GetEvent("A1"));
	        testCase.Add("D1", "b", events.GetEvent("B2"), "d", events.GetEvent("D1"), "a", events.GetEvent("A2"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"), "a", events.GetEvent("A1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"), "a", events.GetEvent("A2"));
	        testCase.Add("D2", "b", events.GetEvent("B2"), "d", events.GetEvent("D2"), "a", events.GetEvent("A1"));
	        testCase.Add("D2", "b", events.GetEvent("B2"), "d", events.GetEvent("D2"), "a", events.GetEvent("A2"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D1"), "a", events.GetEvent("A1"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D1"), "a", events.GetEvent("A2"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D2"), "a", events.GetEvent("A1"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "d", events.GetEvent("D2"), "a", events.GetEvent("A2"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"), "a", events.GetEvent("A1"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"), "a", events.GetEvent("A2"));
	        testCase.Add("D3", "b", events.GetEvent("B2"), "d", events.GetEvent("D3"), "a", events.GetEvent("A1"));
	        testCase.Add("D3", "b", events.GetEvent("B2"), "d", events.GetEvent("D3"), "a", events.GetEvent("A2"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"), "a", events.GetEvent("A1"));
	        testCase.Add("D3", "b", events.GetEvent("B3"), "d", events.GetEvent("D3"), "a", events.GetEvent("A2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (a=" + EVENT_A_CLASS + " and every d=" + EVENT_D_CLASS + " and b=" + EVENT_B_CLASS + ")");
	        testCase.Add("D1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"), "a", events.GetEvent("A1"));
	        testCase.Add("D2", "b", events.GetEvent("B1"), "d", events.GetEvent("D2"), "a", events.GetEvent("A1"));
	        testCase.Add("D3", "b", events.GetEvent("B1"), "d", events.GetEvent("D3"), "a", events.GetEvent("A1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every (b=" + EVENT_B_CLASS + " and b=" + EVENT_B_CLASS + ")");
	        testCase.Add("B1", "b", events.GetEvent("B1"), "b", events.GetEvent("B1"));
	        testCase.Add("B2", "b", events.GetEvent("B2"), "b", events.GetEvent("B2"));
	        testCase.Add("B3", "b", events.GetEvent("B3"), "b", events.GetEvent("B3"));
	        testCaseList.AddTest(testCase);

	        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
	        util.RunTest();
	    }
	}
} // End of namespace
