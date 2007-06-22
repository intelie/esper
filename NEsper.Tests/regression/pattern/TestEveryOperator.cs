using System;

using net.esper.regression.support;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{

	[TestFixture]
	public class TestEveryOperator : SupportBeanConstants
	{
		[Test]
		public virtual void  testOp()
		{
			EventCollection events = EventCollectionFactory.GetEventSetOne(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;

			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS);
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("B3", "b", events.GetEvent("B3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS);
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (every (every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "))");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			for (int i = 0; i < 3; i++)
			{
				testCase.Add("B2", "b", events.GetEvent("B2"));
			}
			for (int i = 0; i < 9; i++)
			{
				testCase.Add("B3", "b", events.GetEvent("B3"));
			}
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "())");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			for (int i = 0; i < 4; i++)
			{
				testCase.Add("B3", "b", events.GetEvent("B3"));
			}
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every( every (every (every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "())))");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			for (int i = 0; i < 4; i++)
			{
				testCase.Add("B2", "b", events.GetEvent("B2"));
			}
			for (int i = 0; i < 16; i++)
			{
				testCase.Add("B3", "b", events.GetEvent("B3"));
			}
			testCaseList.AddTest(testCase);

			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.RunTest();
		}
	}
}
