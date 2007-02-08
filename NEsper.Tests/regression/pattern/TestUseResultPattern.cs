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
			String EVENT = typeof(SupportBean_N).FullName;
			
			EventCollection events = EventCollectionFactory.getSetThreeExternalClock(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;
			
			testCase = new EventExpressionCase("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.doublePrimitive)");
			testCase.Add("N6", "na", events.getEvent("N1"), "nb", events.getEvent("N6"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=87) -> nb=" + EVENT + "(intPrimitive > na.IntPrimitive)");
			testCase.Add("N8", "na", events.getEvent("N3"), "nb", events.getEvent("N8"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=87) -> nb=" + EVENT + "(intPrimitive < na.IntPrimitive)");
			testCase.Add("N4", "na", events.getEvent("N3"), "nb", events.getEvent("N4"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=66) -> every nb=" + EVENT + "(intPrimitive >= na.IntPrimitive)");
			testCase.Add("N3", "na", events.getEvent("N2"), "nb", events.getEvent("N3"));
			testCase.Add("N4", "na", events.getEvent("N2"), "nb", events.getEvent("N4"));
			testCase.Add("N8", "na", events.getEvent("N2"), "nb", events.getEvent("N8"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=66) -> every nb=" + EVENT + "(intPrimitive >= na.IntPrimitive)");
			testCase.Add("N3", "na", events.getEvent("N2"), "nb", events.getEvent("N3"));
			testCase.Add("N4", "na", events.getEvent("N2"), "nb", events.getEvent("N4"));
			testCase.Add("N8", "na", events.getEvent("N2"), "nb", events.getEvent("N8"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(BoolBoxed=false) -> every nb=" + EVENT + "(boolPrimitive = na.boolPrimitive)");
			testCase.Add("N4", "na", events.getEvent("N2"), "nb", events.getEvent("N4"));
			testCase.Add("N5", "na", events.getEvent("N2"), "nb", events.getEvent("N5"));
			testCase.Add("N8", "na", events.getEvent("N2"), "nb", events.getEvent("N8"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every na=" + EVENT + " -> every nb=" + EVENT + "(intPrimitive=na.IntPrimitive)");
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive=na.doublePrimitive)");
			testCase.Add("N5", "na", events.getEvent("N2"), "nb", events.getEvent("N5"));
			testCase.Add("N6", "na", events.getEvent("N1"), "nb", events.getEvent("N6"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every na=" + EVENT + "(BoolBoxed=false) -> every nb=" + EVENT + "(BoolBoxed=na.BoolBoxed)");
			testCase.Add("N5", "na", events.getEvent("N2"), "nb", events.getEvent("N5"));
			testCase.Add("N8", "na", events.getEvent("N2"), "nb", events.getEvent("N8"));
			testCase.Add("N8", "na", events.getEvent("N5"), "nb", events.getEvent("N8"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(BoolBoxed=false) -> nb=" + EVENT + "(intPrimitive<na.IntPrimitive)" + " -> nc=" + EVENT + "(intPrimitive > nb.IntPrimitive)");
			testCase.Add("N6", "na", events.getEvent("N2"), "nb", events.getEvent("N5"), "nc", events.getEvent("N6"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=86) -> nb=" + EVENT + "(intPrimitive<na.IntPrimitive)" + " -> nc=" + EVENT + "(intPrimitive > na.IntPrimitive)");
			testCase.Add("N8", "na", events.getEvent("N4"), "nb", events.getEvent("N5"), "nc", events.getEvent("N8"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=86) -> (nb=" + EVENT + "(intPrimitive<na.IntPrimitive)" + " or nc=" + EVENT + "(intPrimitive > na.IntPrimitive))");
			testCase.Add("N5", "na", events.getEvent("N4"), "nb", events.getEvent("N5"), "nc", (Object) null);
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=86) -> (nb=" + EVENT + "(intPrimitive>na.IntPrimitive)" + " or nc=" + EVENT + "(intBoxed < na.IntBoxed))");
			testCase.Add("N8", "na", events.getEvent("N4"), "nb", events.getEvent("N8"), "nc", (Object) null);
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=86) -> (nb=" + EVENT + "(intPrimitive>na.IntPrimitive)" + " and nc=" + EVENT + "(intBoxed < na.IntBoxed))");
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive in [0:na.doublePrimitive])");
			testCase.Add("N4", "na", events.getEvent("N1"), "nb", events.getEvent("N4"));
			testCase.Add("N6", "na", events.getEvent("N1"), "nb", events.getEvent("N6"));
			testCase.Add("N7", "na", events.getEvent("N1"), "nb", events.getEvent("N7"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive in (0:na.doublePrimitive))");
			testCase.Add("N4", "na", events.getEvent("N1"), "nb", events.getEvent("N4"));
			testCase.Add("N7", "na", events.getEvent("N1"), "nb", events.getEvent("N7"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "() -> every nb=" + EVENT + "(intPrimitive in (na.IntPrimitive:na.doublePrimitive))");
			testCase.Add("N7", "na", events.getEvent("N1"), "nb", events.getEvent("N7"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("na=" + EVENT + "() -> every nb=" + EVENT + "(intPrimitive in (na.IntPrimitive:60))");
			testCase.Add("N6", "na", events.getEvent("N1"), "nb", events.getEvent("N6"));
			testCase.Add("N7", "na", events.getEvent("N1"), "nb", events.getEvent("N7"));
			testCaseList.AddTest(testCase);
			
			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.runTest();
		}
		
		[Test]
		public virtual void  testObjectId()
		{
			String EVENT = typeof(SupportBean_S0).FullName;
			
			EventCollection events = EventCollectionFactory.getSetFourExternalClock(0, 1000);
			CaseList testCaseList = new CaseList();
			EventExpressionCase testCase = null;
			
			testCase = new EventExpressionCase("X1=" + EVENT + "() -> X2=" + EVENT + "(p00=X1.p00)");
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("X1=" + EVENT + "(p00='B') -> X2=" + EVENT + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("X1=" + EVENT + "(p00='B') -> every X2=" + EVENT + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
			testCase.Add("e11", "X1", events.getEvent("e2"), "X2", events.getEvent("e11"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every X1=" + EVENT + "(p00='B') -> every X2=" + EVENT + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
			testCase.Add("e11", "X1", events.getEvent("e2"), "X2", events.getEvent("e11"));
			testCase.Add("e11", "X1", events.getEvent("e6"), "X2", events.getEvent("e11"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every X1=" + EVENT + "() -> X2=" + EVENT + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
			testCase.Add("e8", "X1", events.getEvent("e3"), "X2", events.getEvent("e8"));
			testCase.Add("e10", "X1", events.getEvent("e9"), "X2", events.getEvent("e10"));
			testCase.Add("e11", "X1", events.getEvent("e6"), "X2", events.getEvent("e11"));
			testCase.Add("e12", "X1", events.getEvent("e7"), "X2", events.getEvent("e12"));
			testCaseList.AddTest(testCase);
			
			testCase = new EventExpressionCase("every X1=" + EVENT + "() -> every X2=" + EVENT + "(p00=X1.p00)");
			testCase.Add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
			testCase.Add("e8", "X1", events.getEvent("e3"), "X2", events.getEvent("e8"));
			testCase.Add("e10", "X1", events.getEvent("e9"), "X2", events.getEvent("e10"));
			testCase.Add("e11", "X1", events.getEvent("e2"), "X2", events.getEvent("e11"));
			testCase.Add("e11", "X1", events.getEvent("e6"), "X2", events.getEvent("e11"));
			testCase.Add("e12", "X1", events.getEvent("e7"), "X2", events.getEvent("e12"));
			testCaseList.AddTest(testCase);
			
			PatternTestHarness util = new PatternTestHarness(events, testCaseList);
			util.runTest();
		}
	}
}
