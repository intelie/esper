using System;

using net.esper.regression.support;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{
	
	[TestFixture]
	public class TestAndOperator : SupportBeanConstants
	{
		[Test]
		public virtual void  testOp()
		{
			EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every(b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ")");
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every( b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ")");
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D2"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
			testCase.Add("D2", "b", events.getEvent("B2"), "d", events.getEvent("D2"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D1"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D2"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
			testCase.Add("D3", "b", events.getEvent("B2"), "d", events.getEvent("D3"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every( every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ")");
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D1"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D2"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + " and d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + " and b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A1"));
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every( every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ")");
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
			testCase.Add("D2", "b", events.getEvent("B2"), "d", events.getEvent("D2"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D1"));
			for (int i = 0; i < 3; i++)
			{
				testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D2"));
			}
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"));
			testCase.Add("D3", "b", events.getEvent("B2"), "d", events.getEvent("D3"));
			for (int i = 0; i < 5; i++)
			{
				testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
			}
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + " and d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + " and b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + " and every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + " and b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A1"));
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A2"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"), "a", events.getEvent("A1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"), "a", events.getEvent("A2"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"), "a", events.getEvent("A1"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"), "a", events.getEvent("A2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
			testCase.Add("B1", "b", events.getEvent("B1"), "b", events.getEvent("B1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + " and every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + " and every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A1"));
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A2"));
			testCase.Add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"), "a", events.getEvent("A1"));
			testCase.Add("D1", "b", events.getEvent("B2"), "d", events.getEvent("D1"), "a", events.getEvent("A2"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"), "a", events.getEvent("A1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"), "a", events.getEvent("A2"));
			testCase.Add("D2", "b", events.getEvent("B2"), "d", events.getEvent("D2"), "a", events.getEvent("A1"));
			testCase.Add("D2", "b", events.getEvent("B2"), "d", events.getEvent("D2"), "a", events.getEvent("A2"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D1"), "a", events.getEvent("A1"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D1"), "a", events.getEvent("A2"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D2"), "a", events.getEvent("A1"));
			testCase.Add("B3", "b", events.getEvent("B3"), "d", events.getEvent("D2"), "a", events.getEvent("A2"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"), "a", events.getEvent("A1"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"), "a", events.getEvent("A2"));
			testCase.Add("D3", "b", events.getEvent("B2"), "d", events.getEvent("D3"), "a", events.getEvent("A1"));
			testCase.Add("D3", "b", events.getEvent("B2"), "d", events.getEvent("D3"), "a", events.getEvent("A2"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"), "a", events.getEvent("A1"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"), "a", events.getEvent("A2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + " and every d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + " and b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + ")");
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"), "a", events.getEvent("A1"));
			testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"), "a", events.getEvent("A1"));
			testCase.Add("D3", "b", events.getEvent("B1"), "d", events.getEvent("D3"), "a", events.getEvent("A1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + ")");
			testCase.Add("B1", "b", events.getEvent("B1"), "b", events.getEvent("B1"));
			testCase.Add("B2", "b", events.getEvent("B2"), "b", events.getEvent("B2"));
			testCase.Add("B3", "b", events.getEvent("B3"), "b", events.getEvent("B3"));
			testCaseList.AddTest(testCase);
			
			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.runTest();
		}
	}
}
