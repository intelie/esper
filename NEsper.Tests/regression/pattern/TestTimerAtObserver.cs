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
using net.esper.regression.support;
using net.esper.support.bean;

namespace net.esper.regression.pattern
{
	[TestFixture]
	public class TestTimerAtObserver : SupportBeanConstants
	{
	    [Test]
	    public void TestOp()
	    {
            DateTime dateTime = new DateTime(2005, 3, 9, 8, 0, 0, 0);

            long startTime = DateTimeHelper.TimeInMillis(dateTime);

	        /**
	         // Start a 2004-12-9 8:00:00am and send events every 10 minutes
	        "A1"    8:10
	        "B1"    8:20
	        "C1"    8:30
	        "B2"    8:40
	        "A2"    8:50
	        "D1"    9:00
	        "E1"    9:10
	        "F1"    9:20
	        "D2"    9:30
	        "B3"    9:40
	        "G1"    9:50
	        "D3"   10:00
	         */

	        EventCollection testData = EventCollectionFactory.GetEventSetOne(startTime, 1000 * 60 * 10);
	        CaseList testCaseList = new CaseList();
	        EventExpressionCase testCase = null;

	        testCase = new EventExpressionCase("timer:at(10, 8, *, *, *)");
	        testCase.Add("A1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(10, 8, *, *, *, 1)");
	        testCase.Add("B1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(5, 8, *, *, *)");
	        testCase.Add("A1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(10, 8, *, *, *, *)");
	        testCase.Add("A1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(25, 9, *, *, *)");
	        testCase.Add("D2");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(11, 8, *, *, *)");
	        testCase.Add("B1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(19, 8, *, *, *, 59)");
	        testCase.Add("B1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every timer:at(* / 5, *, *, *, *, *)");
	        AddAll(testCase);
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every timer:at(*, *, *, *, *, * / 10)");
	        AddAll(testCase);
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(20, 8, *, *, *, 20)");
	        testCase.Add("C1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every timer:at(*, *, *, *, *)");
	        AddAll(testCase);
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every timer:at(*, *, *, *, *, *)");
	        AddAll(testCase);
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every timer:at(* / 9, *, *, *, *, *)");
	        AddAll(testCase);
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every timer:at(* / 10, *, *, *, *, *)");
	        AddAll(testCase);
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("every timer:at(* / 30, *, *, *, *)");
	        testCase.Add("C1");
	        testCase.Add("D1");
	        testCase.Add("D2");
	        testCase.Add("D3");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(10, 9, *, *, *, 10) or timer:at(30, 9, *, *, *, *)");
	        testCase.Add("F1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id='B3') -> timer:at(20, 9, *, *, *, *)");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("b=" + EVENT_B_CLASS + "(id='B3') -> timer:at(45, 9, *, *, *, *)");
	        testCase.Add("G1", "b", testData.GetEvent("B3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(59, 8, *, *, *, 59) -> d=" + EVENT_D_CLASS);
	        testCase.Add("D1", "d", testData.GetEvent("D1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(*, 9, *, *, *, 59) -> d=" + EVENT_D_CLASS);
	        testCase.Add("D2", "d", testData.GetEvent("D2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(22, 8, *, *, *) -> b=" + EVENT_B_CLASS + "(id='B3') -> timer:at(55, *, *, *, *)");
	        testCase.Add("D3", "b", testData.GetEvent("B3"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(40, *, *, *, *, 1) and b=" + EVENT_B_CLASS);
	        testCase.Add("A2", "b", testData.GetEvent("B1"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(40, 9, *, *, *, 1) or d=" + EVENT_D_CLASS + "(id=\"D3\")");
	        testCase.Add("G1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(22, 8, *, *, *) -> b=" + EVENT_B_CLASS + "() -> timer:at(55, 8, *, *, *)");
	        testCase.Add("D1", "b", testData.GetEvent("B2"));
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(22, 8, *, *, *, 1) where timer:within(1 second)");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(22, 8, *, *, *, 1) where timer:within(30 minutes)");
	        testCase.Add("C1");
	        testCaseList.AddTest(testCase);

	        /**
	         * As of release 1.6 this no longer updates listeners when the statement is started.
	         * The reason is that the dispatch view only gets attached after a pattern started, therefore
	         * ZeroDepthEventStream looses the event.
	         * There should be no use case requiring this
	         *
	        testCase = new EventExpressionCase("not timer:at(22, 8, *, *, *, 1)");
	        testCase.Add(EventCollection.ON_START__event_ID);
	        testCaseList.AddTest(testCase);
	         */

	        testCase = new EventExpressionCase("timer:at(*, 9, *, *, *) and timer:at(55, *, *, *, *)");
	        testCase.Add("D1");
	        testCaseList.AddTest(testCase);

	        testCase = new EventExpressionCase("timer:at(40, 8, *, *, *, 1) and b=" + EVENT_B_CLASS);
	        testCase.Add("A2", "b", testData.GetEvent("B1"));
	        testCaseList.AddTest(testCase);

	        // Run all tests
	        PatternTestHarness util = new PatternTestHarness(testData, testCaseList);
	        util.RunTest();
	    }

	    private void AddAll (EventExpressionCase desc)
	    {
	        desc.Add("A1");
	        desc.Add("B1");
	        desc.Add("C1");
	        desc.Add("B2");
	        desc.Add("A2");
	        desc.Add("D1");
	        desc.Add("E1");
	        desc.Add("F1");
	        desc.Add("D2");
	        desc.Add("B3");
	        desc.Add("G1");
	        desc.Add("D3");
	    }
	}
} // End of namespace
