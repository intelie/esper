using System;

using net.esper.regression.support;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{

	[TestFixture]
	public class TestOperators : SupportBeanConstants
	{
		[Test]
		public virtual void  testOp()
		{
			EventCollection events = EventCollectionFactory.GetEventSetOne(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;

			testCase = new EventExpressionCase("(b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + ") " + " and " + "(a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS + " -> e=" + net.esper.support.bean.SupportBeanConstants.EVENT_E_CLASS + ")");
			testCase.Add("E1", "b", events.GetEvent("B1"), "d", events.GetEvent("D1"), "a", events.GetEvent("A1"), "e", events.GetEvent("E1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + " -> (d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() or a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS + ")");
			testCase.Add("A2", "b", events.GetEvent("B1"), "a", events.GetEvent("A2"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() -> (" + "(d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() -> a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS + "())" + " or " + "(a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS + "() -> e=" + net.esper.support.bean.SupportBeanConstants.EVENT_E_CLASS + "()))");
			testCase.Add("E1", "b", events.GetEvent("B1"), "a", events.GetEvent("A2"), "e", events.GetEvent("E1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() and d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "() or a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS);
			testCase.Add("A1", "a", events.GetEvent("A1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("(b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() -> d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "()) or a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS);
			testCase.Add("A1", "a", events.GetEvent("A1"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("(b=" + net.esper.support.bean.SupportBeanConstants.EVENT_B_CLASS + "() and " + "d=" + net.esper.support.bean.SupportBeanConstants.EVENT_D_CLASS + "()) or " + "a=" + net.esper.support.bean.SupportBeanConstants.EVENT_A_CLASS);
			testCase.Add("A1", "a", events.GetEvent("A1"));
			testCaseList.AddTest(testCase);

			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.RunTest();
		}
	}
}
