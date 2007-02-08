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
			EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;
			
			testCase = new EventExpressionCase("(b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ") " + " and " + "(a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + " -> e=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_E_CLASS + ")");
			testCase.Add("E1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A1"), "e", events.getEvent("E1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> (d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + "() or a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + ")");
			testCase.Add("A2", "b", events.getEvent("B1"), "a", events.getEvent("A2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() -> (" + "(d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + "() -> a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "())" + " or " + "(a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "() -> e=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_E_CLASS + "()))");
			testCase.Add("E1", "b", events.getEvent("B1"), "a", events.getEvent("A2"), "e", events.getEvent("E1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() and d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + "() or a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS);
			testCase.Add("A1", "a", events.getEvent("A1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("(b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + "()) or a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS);
			testCase.Add("A1", "a", events.getEvent("A1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("(b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() and " + "d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + "()) or " + "a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS);
			testCase.Add("A1", "a", events.getEvent("A1"));
			testCaseList.AddTest(testCase);
			
			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.runTest();
		}
	}
}
