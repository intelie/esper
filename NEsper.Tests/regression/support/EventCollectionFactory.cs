using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.support.bean;

namespace net.esper.regression.support
{
	public class EventCollectionFactory : SupportBeanConstants
	{
    /**
     * Make a A to G data set for testing with external clocking
     */
    public static EventCollection getEventSetOne(long baseTime, long numMSecBetweenEvents)
    {
        LinkedDictionary<String, Object> testData = makeMixedSet();
        LinkedDictionary<String, Int64> times = makeExternalClockTimes(testData, baseTime, numMSecBetweenEvents);
        return new EventCollection(testData, times);
    }

    /**
     * Make a A only data set for testing with external clocking
     */
    public static EventCollection getSetTwoExternalClock(long baseTime, long numMSecBetweenEvents)
    {
        LinkedDictionary<String, Object> testData = makeTestDataUniform();
        LinkedDictionary<String, Int64> times = makeExternalClockTimes(testData, baseTime, numMSecBetweenEvents);
        return new EventCollection(testData, times);
    }

    public static EventCollection getSetThreeExternalClock(long baseTime, long numMSecBetweenEvents)
    {
        LinkedDictionary<String, Object> testData = makeTestDataNumeric();
        LinkedDictionary<String, Int64> times = makeExternalClockTimes(testData, baseTime, numMSecBetweenEvents);
        return new EventCollection(testData, times);
    }

    public static EventCollection getSetFourExternalClock(long baseTime, long numMSecBetweenEvents)
    {
        LinkedDictionary<String, Object> testData = makeTestDataS0();
        LinkedDictionary<String, Int64> times = makeExternalClockTimes(testData, baseTime, numMSecBetweenEvents);
        return new EventCollection(testData, times);
    }

    public static EventCollection getSetFiveInterfaces()
    {
        LinkedDictionary<String, Object> testData = makeTestDataInterfaces();
        LinkedDictionary<String, Int64> times = makeExternalClockTimes(testData, 0, 100);
        return new EventCollection(testData, times);
    }

    public static EventCollection getSetSixComplexProperties()
    {
        LinkedDictionary<String, Object> testData = makeTestDataComplexProps();
        LinkedDictionary<String, Int64> times = makeExternalClockTimes(testData, 0, 100);
        return new EventCollection(testData, times);
    }

    private static LinkedDictionary<String, Object> makeMixedSet()
    {
        LinkedDictionary<String, Object> testData = new LinkedDictionary<String, Object>();

        testData.Put("A1", new SupportBean_A("A1"));
        testData.Put("B1", new SupportBean_B("B1"));
        testData.Put("C1", new SupportBean_C("C1"));
        testData.Put("B2", new SupportBean_B("B2"));
        testData.Put("A2", new SupportBean_A("A2"));
        testData.Put("D1", new SupportBean_D("D1"));
        testData.Put("E1", new SupportBean_E("E1"));
        testData.Put("F1", new SupportBean_F("F1"));
        testData.Put("D2", new SupportBean_D("D2"));
        testData.Put("B3", new SupportBean_B("B3"));
        testData.Put("G1", new SupportBean_G("G1"));
        testData.Put("D3", new SupportBean_D("D3"));

        return testData;
    }

    // Make time values sending events exactly every seconds, Starting at time zero, first event after 1 second
    private static LinkedDictionary<String, long> makeExternalClockTimes(LinkedDictionary<String, Object> testData,
                                                                      long baseTime,
                                                                      long numMSecBetweenEvents)
    {
        LinkedDictionary<String, Int64> testDataTimers = new LinkedDictionary<String, Int64>();

        testDataTimers.Put(EventCollection.ON_START_EVENT_ID, baseTime);

        foreach (String id in testData.Keys)
        {
            baseTime += numMSecBetweenEvents;
            testDataTimers.Put(id, baseTime);
        }

        return testDataTimers;
    }

    private static LinkedDictionary<String, Object> makeTestDataUniform()
    {
        LinkedDictionary<String, Object> testData = new LinkedDictionary<String, Object>();

        testData.Put("B1", new SupportBean_A("B1"));
        testData.Put("B2", new SupportBean_A("B2"));
        testData.Put("B3", new SupportBean_A("B3"));
        testData.Put("A4", new SupportBean_A("A4"));
        testData.Put("A5", new SupportBean_A("A5"));
        testData.Put("A6", new SupportBean_A("A6"));

        return testData;
    }

    private static LinkedDictionary<String, Object> makeTestDataNumeric()
    {
        LinkedDictionary<String, Object> testData = new LinkedDictionary<String, Object>();

        testData.Put("N1", new SupportBean_N( 01, -56, 44.0,  -60.5,  true,  true));
        testData.Put("N2", new SupportBean_N( 66,  59, 48.0, 70.999,  true, false));
        testData.Put("N3", new SupportBean_N( 87,  -5, 44.5,  -23.5, false,  true));
        testData.Put("N4", new SupportBean_N( 86, -98, 42.1,  -79.5,  true,  true));
        testData.Put("N5", new SupportBean_N( 00, -33, 48.0,  44.45,  true, false));
        testData.Put("N6", new SupportBean_N( 55, -55, 44.0,  -60.5, false,  true));
        testData.Put("N7", new SupportBean_N( 34,  92, 39.0,  -66.5, false,  true));
        testData.Put("N8", new SupportBean_N(100,  66, 47.5,   45.0,  true, false));

        return testData;
    }

    private static LinkedDictionary<String, Object> makeTestDataS0()
    {
        LinkedDictionary<String, Object> testData = new LinkedDictionary<String, Object>();

        // B arrives 3 times
        // G arrives twice, in a row
        // F and C arrive twice
        testData.Put("e1", new SupportBean_S0(1,   "A"));
        testData.Put("e2", new SupportBean_S0(2,   "B"));   // B
        testData.Put("e3", new SupportBean_S0(3,   "C"));                   // C
        testData.Put("e4", new SupportBean_S0(4,   "D"));
        testData.Put("e5", new SupportBean_S0(5,   "E"));
        testData.Put("e6", new SupportBean_S0(6,   "B"));   // B
        testData.Put("e7", new SupportBean_S0(7,   "F"));               // F
        testData.Put("e8", new SupportBean_S0(8,   "C"));                   // C
        testData.Put("e9", new SupportBean_S0(9,   "G"));           // G
        testData.Put("e10", new SupportBean_S0(10, "G"));           // G
        testData.Put("e11", new SupportBean_S0(11, "B"));   // B
        testData.Put("e12", new SupportBean_S0(12, "F"));               // F

        return testData;
    }

    /**
     * ISupportBaseAB
     *      ISupportA
     *      ISupportB
     *          ISupportABCImpl
     *
     * @return
     */

    private static LinkedDictionary<String, Object> makeTestDataInterfaces()
    {
        LinkedDictionary<String, Object> testData = new LinkedDictionary<String, Object>();

        testData.Put("e1", new ISupportCImpl("C1"));
        testData.Put("e2", new ISupportABCImpl("A1", "B1", "BaseB", "C1"));
        testData.Put("e3", new ISupportAImpl("A1", "BaseAB"));
        testData.Put("e4", new ISupportBImpl("B1", "BaseAB"));
        testData.Put("e5", new ISupportDImpl("D1", "BaseD", "BaseDBase"));
        testData.Put("e6", new ISupportBCImpl("B2", "BaseAB2", "C2"));
        testData.Put("e7", new ISupportBaseABImpl("BaseAB3"));
        testData.Put("e8", new SupportOverrideOneA("OA1", "O1", "OBase"));
        testData.Put("e9", new SupportOverrideOneB("OB1", "O2", "OBase"));
        testData.Put("e10", new SupportOverrideOne("O3", "OBase"));
        testData.Put("e11", new SupportOverrideBase("OBase"));
        testData.Put("e12", new ISupportAImplSuperGImplPlus("G1", "A3", "BaseAB4", "B4", "C2"));
        testData.Put("e13", new ISupportAImplSuperGImpl("G2", "A14", "BaseAB5"));

        return testData;
    }

    private static LinkedDictionary<String, Object> makeTestDataComplexProps()
    {
        LinkedDictionary<String, Object> testData = new LinkedDictionary<String, Object>();

        testData.Put("e1", SupportBeanComplexProps.makeDefaultBean());
        testData.Put("e2", SupportBeanCombinedProps.makeDefaultBean());

        return testData;
    }
	}
}
