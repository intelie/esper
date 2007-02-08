using System;

using net.esper.regression.support;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{
	
	[TestFixture]
	public class TestNotOperator : SupportBeanConstants
	{
		[Test]
		public virtual void  testOp()
		{
			EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and not d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("B1", "b", events.getEvent("B1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("not (b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ")");
			testCase.Add(EventCollection.ON_START_EVENT_ID);
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and not g=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_G_CLASS);
			testCase.Add("B1", "b", events.getEvent("B1"));
			testCase.Add("B2", "b", events.getEvent("B2"));
			testCase.Add("B3", "b", events.getEvent("B3"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("not a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS);
			testCase.Add(EventCollection.ON_START_EVENT_ID);
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and not d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
			testCase.Add("B1", "b", events.getEvent("B1"));
			testCase.Add("B2", "b", events.getEvent("B2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and not a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "(id='A1')");
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and not a2=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "(id='A2')");
			testCase.Add("B1", "b", events.getEvent("B1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and not b3=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "(id='B3'))");
			testCase.Add("B1", "b", events.getEvent("B1"));
			testCase.Add("B2", "b", events.getEvent("B2"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " or not " + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + "())");
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (every b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " and not " + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "(id='B2'))");
			testCase.Add("B1", "b", events.getEvent("B1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("(b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ") and " + " not " + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS);
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("(b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ") and " + " not " + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_G_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ") and " + " not " + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_G_CLASS);
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + ") and " + " not " + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_G_CLASS + "(id='x')");
			testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
			testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
			testCaseList.AddTest(testCase);
			
			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.runTest();
		}
		
		[Test]
		public virtual void  testUniformEvents()
		{
			EventCollection events = EventCollectionFactory.getSetTwoExternalClock(0, 1000);
			CaseList results = new CaseList();
			EventExpressionCase desc = null;
			
			desc = new EventExpressionCase("every a=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "() and not a1=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_A_CLASS + "(id=\"A4\")");
			desc.Add("B1", "a", events.getEvent("B1"));
			desc.Add("B2", "a", events.getEvent("B2"));
			desc.Add("B3", "a", events.getEvent("B3"));
			results.AddTest(desc);
			
			PatternTestHarness util = new PatternTestHarness(events, results);
			util.runTest();
		}
	}
}
