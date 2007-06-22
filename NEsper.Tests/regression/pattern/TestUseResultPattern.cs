using System;

using net.esper.regression.support;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{

	[TestFixture]
	public class TestUseResultPattern : SupportBeanConstants
	{
		[Test]
		public virtual void  testNumeric()
		{
			String _event = typeof(SupportBean_N).FullName;

			EventCollection events = EventCollectionFactory.GetSetThreeExternalClock(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;

			testCase = new EventExpressionCase("na=" + _event + " -> nb=" + _event + "(doublePrimitive = na.doublePrimitive)");
			testCase.Add("N6", "na", events.GetEvent("N1"), "nb", events.GetEvent("N6"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "(intPrimitive=87) -> nb=" + _event + "(intPrimitive > na.intPrimitive)");
			testCase.Add("N8", "na", events.GetEvent("N3"), "nb", events.GetEvent("N8"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "(intPrimitive=87) -> nb=" + _event + "(intPrimitive < na.intPrimitive)");
			testCase.Add("N4", "na", events.GetEvent("N3"), "nb", events.GetEvent("N4"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "(intPrimitive=66) -> every nb=" + _event + "(intPrimitive >= na.intPrimitive)");
			testCase.Add("N3", "na", events.GetEvent("N2"), "nb", events.GetEvent("N3"));
			testCase.Add("N4", "na", events.GetEvent("N2"), "nb", events.GetEvent("N4"));
			testCase.Add("N8", "na", events.GetEvent("N2"), "nb", events.GetEvent("N8"));
			testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("na=" + _event + "(intPrimitive=66) -> every nb=" + _event + "(intPrimitive >= na.intPrimitive)");
			testCase.Add("N3", "na", events.GetEvent("N2"), "nb", events.GetEvent("N3"));
			testCase.Add("N4", "na", events.GetEvent("N2"), "nb", events.GetEvent("N4"));
			testCase.Add("N8", "na", events.GetEvent("N2"), "nb", events.GetEvent("N8"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "(boolBoxed=false) -> every nb=" + _event + "(boolPrimitive = na.boolPrimitive)");
			testCase.Add("N4", "na", events.GetEvent("N2"), "nb", events.GetEvent("N4"));
			testCase.Add("N5", "na", events.GetEvent("N2"), "nb", events.GetEvent("N5"));
			testCase.Add("N8", "na", events.GetEvent("N2"), "nb", events.GetEvent("N8"));
			testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every na=" + _event + " -> every nb=" + _event + "(intPrimitive=na.intPrimitive)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every na=" + _event + "() -> every nb=" + _event + "(doublePrimitive=na.doublePrimitive)");
			testCase.Add("N5", "na", events.GetEvent("N2"), "nb", events.GetEvent("N5"));
			testCase.Add("N6", "na", events.GetEvent("N1"), "nb", events.GetEvent("N6"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every na=" + _event + "(boolBoxed=false) -> every nb=" + _event + "(boolBoxed=na.boolBoxed)");
			testCase.Add("N5", "na", events.GetEvent("N2"), "nb", events.GetEvent("N5"));
			testCase.Add("N8", "na", events.GetEvent("N2"), "nb", events.GetEvent("N8"));
			testCase.Add("N8", "na", events.GetEvent("N5"), "nb", events.GetEvent("N8"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "(boolBoxed=false) -> nb=" + _event + "(intPrimitive<na.intPrimitive)" + " -> nc=" + _event + "(intPrimitive > nb.intPrimitive)");
			testCase.Add("N6", "na", events.GetEvent("N2"), "nb", events.GetEvent("N5"), "nc", events.GetEvent("N6"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "(intPrimitive=86) -> nb=" + _event + "(intPrimitive<na.intPrimitive)" + " -> nc=" + _event + "(intPrimitive > na.intPrimitive)");
			testCase.Add("N8", "na", events.GetEvent("N4"), "nb", events.GetEvent("N5"), "nc", events.GetEvent("N8"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "(intPrimitive=86) -> (nb=" + _event + "(intPrimitive<na.intPrimitive)" + " or nc=" + _event + "(intPrimitive > na.intPrimitive))");
			testCase.Add("N5", "na", events.GetEvent("N4"), "nb", events.GetEvent("N5"), "nc", (Object) null);
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "(intPrimitive=86) -> (nb=" + _event + "(intPrimitive>na.intPrimitive)" + " or nc=" + _event + "(intBoxed < na.intBoxed))");
			testCase.Add("N8", "na", events.GetEvent("N4"), "nb", events.GetEvent("N8"), "nc", (Object) null);
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "(intPrimitive=86) -> (nb=" + _event + "(intPrimitive>na.intPrimitive)" + " and nc=" + _event + "(intBoxed < na.intBoxed))");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "() -> every nb=" + _event + "(doublePrimitive in [0:na.doublePrimitive])");
			testCase.Add("N4", "na", events.GetEvent("N1"), "nb", events.GetEvent("N4"));
			testCase.Add("N6", "na", events.GetEvent("N1"), "nb", events.GetEvent("N6"));
			testCase.Add("N7", "na", events.GetEvent("N1"), "nb", events.GetEvent("N7"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "() -> every nb=" + _event + "(doublePrimitive in (0:na.doublePrimitive))");
			testCase.Add("N4", "na", events.GetEvent("N1"), "nb", events.GetEvent("N4"));
			testCase.Add("N7", "na", events.GetEvent("N1"), "nb", events.GetEvent("N7"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "() -> every nb=" + _event + "(intPrimitive in (na.intPrimitive:na.doublePrimitive))");
			testCase.Add("N7", "na", events.GetEvent("N1"), "nb", events.GetEvent("N7"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("na=" + _event + "() -> every nb=" + _event + "(intPrimitive in (na.intPrimitive:60))");
			testCase.Add("N6", "na", events.GetEvent("N1"), "nb", events.GetEvent("N6"));
			testCase.Add("N7", "na", events.GetEvent("N1"), "nb", events.GetEvent("N7"));
			testCaseList.AddTest(testCase);

			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.RunTest();
		}

		[Test]
		public virtual void  testObjectId()
		{
			String _event = typeof(SupportBean_S0).FullName;

			EventCollection events = EventCollectionFactory.GetSetFourExternalClock(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;

			testCase = new EventExpressionCase("X1=" + _event + "() -> X2=" + _event + "(p00=X1.p00)");
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("X1=" + _event + "(p00='B') -> X2=" + _event + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.GetEvent("e2"), "X2", events.GetEvent("e6"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("X1=" + _event + "(p00='B') -> every X2=" + _event + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.GetEvent("e2"), "X2", events.GetEvent("e6"));
			testCase.Add("e11", "X1", events.GetEvent("e2"), "X2", events.GetEvent("e11"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every X1=" + _event + "(p00='B') -> every X2=" + _event + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.GetEvent("e2"), "X2", events.GetEvent("e6"));
			testCase.Add("e11", "X1", events.GetEvent("e2"), "X2", events.GetEvent("e11"));
			testCase.Add("e11", "X1", events.GetEvent("e6"), "X2", events.GetEvent("e11"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every X1=" + _event + "() -> X2=" + _event + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.GetEvent("e2"), "X2", events.GetEvent("e6"));
			testCase.Add("e8", "X1", events.GetEvent("e3"), "X2", events.GetEvent("e8"));
			testCase.Add("e10", "X1", events.GetEvent("e9"), "X2", events.GetEvent("e10"));
			testCase.Add("e11", "X1", events.GetEvent("e6"), "X2", events.GetEvent("e11"));
			testCase.Add("e12", "X1", events.GetEvent("e7"), "X2", events.GetEvent("e12"));
			testCaseList.AddTest(testCase);

			testCase = new EventExpressionCase("every X1=" + _event + "() -> every X2=" + _event + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.GetEvent("e2"), "X2", events.GetEvent("e6"));
			testCase.Add("e8", "X1", events.GetEvent("e3"), "X2", events.GetEvent("e8"));
			testCase.Add("e10", "X1", events.GetEvent("e9"), "X2", events.GetEvent("e10"));
			testCase.Add("e11", "X1", events.GetEvent("e2"), "X2", events.GetEvent("e11"));
			testCase.Add("e11", "X1", events.GetEvent("e6"), "X2", events.GetEvent("e11"));
			testCase.Add("e12", "X1", events.GetEvent("e7"), "X2", events.GetEvent("e12"));
			testCaseList.AddTest(testCase);

			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.RunTest();
		}
	}
}
