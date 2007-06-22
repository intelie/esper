using System;

using net.esper.regression.support;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{

	[TestFixture]
	public class TestOrOperator : SupportBeanConstants
	{
		[Test]
		public virtual void  testOp()
		{
			EventCollection events = EventCollectionFactory.GetEventSetOne(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;

			testCase = new EventExpressionCase("a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS + " or a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS);
			testCase.Add("A1", "a", events.GetEvent("A1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS + " or b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + " or c=" + net.esper.support.bean.SupportBeanConstants.EVENT_C_CLASS);
			testCase.Add("A1", "a", events.GetEvent("A1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + " or every d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS);
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("D1", "d", events.GetEvent("D1"));
			testCase.Add("D2", "d", events.GetEvent("D2"));
			testCase.Add("B3", "b", events.GetEvent("B3"));
			testCase.Add("D3", "d", events.GetEvent("D3"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS + " or b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS);
			testCase.Add("A1", "a", events.GetEvent("A1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS + " or every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS);
			testCase.Add("A1", "a", events.GetEvent("A1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS + " or d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS);
			testCase.Add("A1", "a", events.GetEvent("A1"));
			testCase.Add("A2", "a", events.GetEvent("A2"));
			testCase.Add("D1", "d", events.GetEvent("D1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() or d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "())");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			for (int i = 0; i < 4; i++)
			{
				testCase.Add("D1", "d", events.GetEvent("D1"));
			}
			for (int i = 0; i < 4; i++)
			{
				testCase.Add("D2", "d", events.GetEvent("D2"));
			}
			for (int i = 0; i < 4; i++)
			{
				testCase.Add("B3", "b", events.GetEvent("B3"));
			}
			for (int i = 0; i < 8; i++)
			{
				testCase.Add("D3", "d", events.GetEvent("D3"));
			}
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() or every d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "())");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("D1", "d", events.GetEvent("D1"));
			testCase.Add("D2", "d", events.GetEvent("D2"));
			testCase.Add("D2", "d", events.GetEvent("D2"));
			for (int i = 0; i < 4; i++)
			{
				testCase.Add("B3", "b", events.GetEvent("B3"));
			}
			for (int i = 0; i < 4; i++)
			{
				testCase.Add("D3", "d", events.GetEvent("D3"));
			}
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every (every d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() or every b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "())");
			testCase.Add("B1", "b", events.GetEvent("B1"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			testCase.Add("B2", "b", events.GetEvent("B2"));
			for (int i = 0; i < 4; i++)
			{
				testCase.Add("D1", "d", events.GetEvent("D1"));
			}
			for (int i = 0; i < 8; i++)
			{
				testCase.Add("D2", "d", events.GetEvent("D2"));
			}
			for (int i = 0; i < 16; i++)
			{
				testCase.Add("B3", "b", events.GetEvent("B3"));
			}
			for (int i = 0; i < 32; i++)
			{
				testCase.Add("D3", "d", events.GetEvent("D3"));
			}
			testCaseList.AddTest(testCase);

			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.RunTest();
		}
	}
}
