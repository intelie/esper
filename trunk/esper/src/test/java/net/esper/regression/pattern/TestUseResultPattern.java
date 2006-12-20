package net.esper.regression.pattern;

import junit.framework.*;
import net.esper.regression.support.*;
import net.esper.support.bean.SupportBeanConstants;
import net.esper.support.bean.SupportBean_N;
import net.esper.support.bean.SupportBean_S0;

public class TestUseResultPattern extends TestCase implements SupportBeanConstants
{
    public void testNumeric() throws Exception
    {
        final String EVENT = SupportBean_N.class.getName();

        EventCollection events = EventCollectionFactory.getSetThreeExternalClock(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.doublePrimitive)");
        testCase.add("N6", "na", events.getEvent("N1"), "nb", events.getEvent("N6"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=87) -> nb=" + EVENT + "(intPrimitive > na.intPrimitive)");
        testCase.add("N8", "na", events.getEvent("N3"), "nb", events.getEvent("N8"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=87) -> nb=" + EVENT + "(intPrimitive < na.intPrimitive)");
        testCase.add("N4", "na", events.getEvent("N3"), "nb", events.getEvent("N4"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=66) -> every nb=" + EVENT + "(intPrimitive >= na.intPrimitive)");
        testCase.add("N3", "na", events.getEvent("N2"), "nb", events.getEvent("N3"));
        testCase.add("N4", "na", events.getEvent("N2"), "nb", events.getEvent("N4"));
        testCase.add("N8", "na", events.getEvent("N2"), "nb", events.getEvent("N8"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=66) -> every nb=" + EVENT + "(intPrimitive >= na.intPrimitive)");
        testCase.add("N3", "na", events.getEvent("N2"), "nb", events.getEvent("N3"));
        testCase.add("N4", "na", events.getEvent("N2"), "nb", events.getEvent("N4"));
        testCase.add("N8", "na", events.getEvent("N2"), "nb", events.getEvent("N8"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(boolBoxed=false) -> every nb=" + EVENT + "(boolPrimitive = na.boolPrimitive)");
        testCase.add("N4", "na", events.getEvent("N2"), "nb", events.getEvent("N4"));
        testCase.add("N5", "na", events.getEvent("N2"), "nb", events.getEvent("N5"));
        testCase.add("N8", "na", events.getEvent("N2"), "nb", events.getEvent("N8"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every na=" + EVENT + " -> every nb=" + EVENT + "(intPrimitive=na.intPrimitive)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive=na.doublePrimitive)");
        testCase.add("N5", "na", events.getEvent("N2"), "nb", events.getEvent("N5"));
        testCase.add("N6", "na", events.getEvent("N1"), "nb", events.getEvent("N6"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every na=" + EVENT + "(boolBoxed=false) -> every nb=" + EVENT + "(boolBoxed=na.boolBoxed)");
        testCase.add("N5", "na", events.getEvent("N2"), "nb", events.getEvent("N5"));
        testCase.add("N8", "na", events.getEvent("N2"), "nb", events.getEvent("N8"));
        testCase.add("N8", "na", events.getEvent("N5"), "nb", events.getEvent("N8"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(boolBoxed=false) -> nb=" + EVENT + "(intPrimitive<na.intPrimitive)" +
                " -> nc=" + EVENT + "(intPrimitive > nb.intPrimitive)");
        testCase.add("N6", "na", events.getEvent("N2"), "nb", events.getEvent("N5"), "nc", events.getEvent("N6"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=86) -> nb=" + EVENT + "(intPrimitive<na.intPrimitive)" +
                " -> nc=" + EVENT + "(intPrimitive > na.intPrimitive)");
        testCase.add("N8", "na", events.getEvent("N4"), "nb", events.getEvent("N5"), "nc", events.getEvent("N8"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=86) -> (nb=" + EVENT + "(intPrimitive<na.intPrimitive)" +
                " or nc=" + EVENT + "(intPrimitive > na.intPrimitive))");
        testCase.add("N5", "na", events.getEvent("N4"), "nb", events.getEvent("N5"), "nc", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=86) -> (nb=" + EVENT + "(intPrimitive>na.intPrimitive)" +
                " or nc=" + EVENT + "(intBoxed < na.intBoxed))");
        testCase.add("N8", "na", events.getEvent("N4"), "nb", events.getEvent("N8"), "nc", null);
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "(intPrimitive=86) -> (nb=" + EVENT + "(intPrimitive>na.intPrimitive)" +
                " and nc=" + EVENT + "(intBoxed < na.intBoxed))");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive in [0:na.doublePrimitive])");
        testCase.add("N4", "na", events.getEvent("N1"), "nb", events.getEvent("N4"));
        testCase.add("N6", "na", events.getEvent("N1"), "nb", events.getEvent("N6"));
        testCase.add("N7", "na", events.getEvent("N1"), "nb", events.getEvent("N7"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive in (0:na.doublePrimitive))");
        testCase.add("N4", "na", events.getEvent("N1"), "nb", events.getEvent("N4"));
        testCase.add("N7", "na", events.getEvent("N1"), "nb", events.getEvent("N7"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "() -> every nb=" + EVENT + "(intPrimitive in (na.intPrimitive:na.doublePrimitive))");
        testCase.add("N7", "na", events.getEvent("N1"), "nb", events.getEvent("N7"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("na=" + EVENT + "() -> every nb=" + EVENT + "(intPrimitive in (na.intPrimitive:60))");
        testCase.add("N6", "na", events.getEvent("N1"), "nb", events.getEvent("N6"));
        testCase.add("N7", "na", events.getEvent("N1"), "nb", events.getEvent("N7"));
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }

    public void testObjectId() throws Exception
    {
        final String EVENT = SupportBean_S0.class.getName();

        EventCollection events = EventCollectionFactory.getSetFourExternalClock(0, 1000);
        CaseList testCaseList = new CaseList();
        EventExpressionCase testCase = null;

        testCase = new EventExpressionCase("X1=" + EVENT + "() -> X2=" + EVENT + "(p00=X1.p00)");
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("X1=" + EVENT + "(p00='B') -> X2=" + EVENT + "(p00=X1.p00)");
        testCase.add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("X1=" + EVENT + "(p00='B') -> every X2=" + EVENT + "(p00=X1.p00)");
        testCase.add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
        testCase.add("e11", "X1", events.getEvent("e2"), "X2", events.getEvent("e11"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every X1=" + EVENT + "(p00='B') -> every X2=" + EVENT + "(p00=X1.p00)");
        testCase.add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
        testCase.add("e11", "X1", events.getEvent("e2"), "X2", events.getEvent("e11"));
        testCase.add("e11", "X1", events.getEvent("e6"), "X2", events.getEvent("e11"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every X1=" + EVENT + "() -> X2=" + EVENT + "(p00=X1.p00)");
        testCase.add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
        testCase.add("e8", "X1", events.getEvent("e3"), "X2", events.getEvent("e8"));
        testCase.add("e10", "X1", events.getEvent("e9"), "X2", events.getEvent("e10"));
        testCase.add("e11", "X1", events.getEvent("e6"), "X2", events.getEvent("e11"));
        testCase.add("e12", "X1", events.getEvent("e7"), "X2", events.getEvent("e12"));
        testCaseList.addTest(testCase);

        testCase = new EventExpressionCase("every X1=" + EVENT + "() -> every X2=" + EVENT + "(p00=X1.p00)");
        testCase.add("e6", "X1", events.getEvent("e2"), "X2", events.getEvent("e6"));
        testCase.add("e8", "X1", events.getEvent("e3"), "X2", events.getEvent("e8"));
        testCase.add("e10", "X1", events.getEvent("e9"), "X2", events.getEvent("e10"));
        testCase.add("e11", "X1", events.getEvent("e2"), "X2", events.getEvent("e11"));
        testCase.add("e11", "X1", events.getEvent("e6"), "X2", events.getEvent("e11"));
        testCase.add("e12", "X1", events.getEvent("e7"), "X2", events.getEvent("e12"));
        testCaseList.addTest(testCase);

        PatternTestHarness util = new PatternTestHarness(events, testCaseList);
        util.runTest();
    }
}