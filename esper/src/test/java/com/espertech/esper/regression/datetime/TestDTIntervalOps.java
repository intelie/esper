package com.espertech.esper.regression.datetime;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportDateTime;
import com.espertech.esper.support.bean.SupportTimeDurationA;
import com.espertech.esper.support.bean.SupportTimeDurationB;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestDTIntervalOps extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        ConfigurationEventTypeLegacy configBean = new ConfigurationEventTypeLegacy();
        configBean.setTimestampProperty("msecdate");
        configBean.setDurationProperty("duration");
        epService.getEPAdministrator().getConfiguration().addEventType("A", SupportTimeDurationA.class.getName(), configBean);
        epService.getEPAdministrator().getConfiguration().addEventType("B", SupportTimeDurationB.class.getName(), configBean);
    }

    // TODO
    // test all other interval operators
    // test first parameter to "before" could also be an absolute time or some other origin (i.e. bean property)
    // test configuration returns Date or Calendar object
    // test bean actually returns null for duration or timestamp
    // test in conjuntion with reformat ops and calendar ops+reformace
    // test outer join performance
    // test "a.getTime(xxx)" and "a.toCalendar()"
    // test "a.get(b.timestamp)"

    // TODO
    // doc ConfigurationEventTypeLegacy "timestamp" and "duration" property names

    // TODO
    // invalid config: bean timestamp property config not found
    // invalid mix of reformat ops and interval ops
    // invalid not-configured used of timestamp: "a.before(b)" without timestamp expressions configured
    // invalid "before" method zero params, 2 params without timestamps, string params, too many parameters
    // invalid target param to "before": object.before, string.before

    public void testBeforeInSelectClause() {

        String[] fields = "c0,c1".split(",");
        String epl =
                "select " +
                "a.msecdate.before(b.msecdate) as c0," +
                "a.before(b) as c1 " +
                " from A.std:lastevent() as a, " +
                "      B.std:lastevent() as b";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        LambdaAssertionUtil.assertTypesAllSame(stmt.getEventType(), fields, Boolean.class);

        epService.getEPRuntime().sendEvent(SupportTimeDurationB.make("B1", "2002-05-30T9:00:00.000", 0));

        epService.getEPRuntime().sendEvent(SupportTimeDurationA.make("A1", "2002-05-30T8:59:59.000", 0));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fields, true);

        epService.getEPRuntime().sendEvent(SupportTimeDurationA.make("A2", "2002-05-30T8:59:59.950", 0));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fields, true);
    }

    public void testBeforeWhereClause() {

        Validator expectedValidator = new BeforeValidator(1L, Long.MAX_VALUE);
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 0, true},
                {"2002-05-30T8:59:59.999", 0, true},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, false},
        };
        assertExpression(seedTime, 0, "a.before(b)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.before(b, 1 millisecond)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.before(b, 1 millisecond, 1000000000L)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.msecdate.before(b)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.utildate.before(b)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.caldate.before(b)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.before(b.msecdate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.before(b.utildate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.before(b.caldate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.msecdate.before(b.msecdate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.msecdate.before(b.msecdate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.utildate.before(b.utildate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.caldate.before(b.caldate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.utildate.before(b.caldate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.utildate.before(b.msecdate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.caldate.before(b.utildate)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.caldate.before(b.msecdate)", expected, expectedValidator);

        expectedValidator = new BeforeValidator(1L, Long.MAX_VALUE);
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, true},
                {"2002-05-30T8:59:59.000", 999, true},
                {"2002-05-30T8:59:59.000", 1000, false},
                {"2002-05-30T8:59:59.000", 1001, false},
                {"2002-05-30T8:59:59.999", 0, true},
                {"2002-05-30T8:59:59.999", 1, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, false},
        };
        assertExpression(seedTime, 0, "a.before(b)", expected, expectedValidator);
        assertExpression(seedTime, 100000, "a.before(b)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, true},
                {"2002-05-30T8:59:59.899", 0, true},
                {"2002-05-30T8:59:59.900", 0, true},
                {"2002-05-30T8:59:59.901", 0, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, false},
        };
        expectedValidator = new BeforeValidator(100L, Long.MAX_VALUE);
        assertExpression(seedTime, 0, "a.before(b, 100 milliseconds)", expected, expectedValidator);
        assertExpression(seedTime, 100000, "a.before(b, 100 milliseconds)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T8:59:59.499", 0, false},
                {"2002-05-30T8:59:59.499", 1, true},
                {"2002-05-30T8:59:59.500", 0, true},
                {"2002-05-30T8:59:59.500", 1, true},
                {"2002-05-30T8:59:59.500", 400, true},
                {"2002-05-30T8:59:59.500", 401, false},
                {"2002-05-30T8:59:59.899", 0, true},
                {"2002-05-30T8:59:59.899", 2, false},
                {"2002-05-30T8:59:59.900", 0, true},
                {"2002-05-30T8:59:59.900", 1, false},
                {"2002-05-30T8:59:59.901", 0, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, false},
        };
        expectedValidator = new BeforeValidator(100L, 500L);
        assertExpression(seedTime, 0, "a.before(b, 100 milliseconds, 500 milliseconds)", expected, expectedValidator);
        assertExpression(seedTime, 100000, "a.before(b, 100 milliseconds, 500 milliseconds)", expected, expectedValidator);

        // test expression params
        epService.getEPAdministrator().createEPL("create variable long V_START = 100");
        epService.getEPAdministrator().createEPL("create variable long V_END = 500");
        assertExpression(seedTime, 0, "a.before(b, V_START milliseconds, V_END milliseconds)", expected, expectedValidator);

        epService.getEPRuntime().setVariableValue("V_START", 200);
        epService.getEPRuntime().setVariableValue("V_END", 800);
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T8:59:59.199", 0, false},
                {"2002-05-30T8:59:59.199", 1, true},
                {"2002-05-30T8:59:59.200", 0, true},
                {"2002-05-30T8:59:59.800", 0, true},
                {"2002-05-30T8:59:59.801", 0, false},
        };
        expectedValidator = new BeforeValidator(200L, 800L);
        assertExpression(seedTime, 0, "a.before(b, V_START milliseconds, V_END milliseconds)", expected, expectedValidator);
    }

    public void testAfterWhereClause() {

        Validator expectedValidator = new AfterValidator(1L, Long.MAX_VALUE);
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, true},
        };
        assertExpression(seedTime, 0, "a.after(b)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.after(b, 1 millisecond)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.after(b, 1 millisecond, 1000000000L)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.msecdate.after(b)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.after(b.utildate)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, false},
                {"2002-05-30T9:00:00.002", 0, true},
        };
        assertExpression(seedTime, 1, "a.after(b)", expected, expectedValidator);
        assertExpression(seedTime, 1, "a.after(b, 1 millisecond, 1000000000L)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.099", 0, false},
                {"2002-05-30T9:00:00.100", 0, true},
                {"2002-05-30T9:00:00.101", 0, true},
        };
        expectedValidator = new AfterValidator(100L, Long.MAX_VALUE);
        assertExpression(seedTime, 0, "a.after(b, 100 milliseconds)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.after(b, 100 milliseconds, 1000000000L)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.099", 0, false},
                {"2002-05-30T9:00:00.100", 0, true},
                {"2002-05-30T9:00:00.500", 0, true},
                {"2002-05-30T9:00:00.501", 0, false},
        };
        expectedValidator = new AfterValidator(100L, 500L);
        assertExpression(seedTime, 0, "a.after(b, 100 milliseconds, 500 milliseconds)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.after(b, 100 milliseconds, 500 milliseconds)", expected, expectedValidator);

        // test expression params
        epService.getEPAdministrator().createEPL("create variable long V_START = 100");
        epService.getEPAdministrator().createEPL("create variable long V_END = 500");
        assertExpression(seedTime, 0, "a.after(b, V_START milliseconds, V_END milliseconds)", expected, expectedValidator);

        epService.getEPRuntime().setVariableValue("V_START", 200);
        epService.getEPRuntime().setVariableValue("V_END", 800);
        expected = new Object[][] {
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.199", 0, false},
                {"2002-05-30T9:00:00.200", 0, true},
                {"2002-05-30T9:00:00.800", 0, true},
                {"2002-05-30T9:00:00.801", 0, false},
        };
        expectedValidator = new AfterValidator(200L, 800L);
        assertExpression(seedTime, 0, "a.after(b, V_START milliseconds, V_END milliseconds)", expected, expectedValidator);
    }

    public void testCoincidesWhereClause() {

        Validator expectedValidator = new CoincidesValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T9:00:00.000", 0, true},
                {"2002-05-30T9:00:00.001", 0, false},
        };
        assertExpression(seedTime, 0, "a.coincides(b)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.coincides(b, 0 millisecond)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.coincides(b, 0, 0)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.msecdate.coincides(b)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.coincides(b.utildate)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T9:00:00.000", 1, true},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, false},
                {"2002-05-30T9:00:00.001", 1, false},
        };
        assertExpression(seedTime, 1, "a.coincides(b)", expected, expectedValidator);
        assertExpression(seedTime, 1, "a.coincides(b, 0, 0)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T8:59:59.899", 0, false},
                {"2002-05-30T8:59:59.900", 0, true},
                {"2002-05-30T9:00:00.000", 0, true},
                {"2002-05-30T9:00:00.000", 50, true},
                {"2002-05-30T9:00:00.000", 100, true},
                {"2002-05-30T9:00:00.000", 101, false},
                {"2002-05-30T9:00:00.099", 0, true},
                {"2002-05-30T9:00:00.100", 0, true},
                {"2002-05-30T9:00:00.101", 0, false},
        };
        expectedValidator = new CoincidesValidator(100L);
        assertExpression(seedTime, 0, "a.coincides(b, 100 milliseconds)", expected, expectedValidator);
        assertExpression(seedTime, 0, "a.coincides(b, 100 milliseconds, 0.1 sec)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T8:59:59.799", 0, false},
                {"2002-05-30T8:59:59.800", 0, true},
                {"2002-05-30T9:00:00.000", 0, true},
                {"2002-05-30T9:00:00.099", 0, true},
                {"2002-05-30T9:00:00.100", 0, true},
                {"2002-05-30T9:00:00.200", 0, true},
                {"2002-05-30T9:00:00.201", 0, false},
        };
        expectedValidator = new CoincidesValidator(200L, 500L);
        assertExpression(seedTime, 0, "a.coincides(b, 200 milliseconds, 500 milliseconds)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T8:59:59.799", 0, false},
                {"2002-05-30T8:59:59.799", 200, false},
                {"2002-05-30T8:59:59.799", 201, false},
                {"2002-05-30T8:59:59.800", 0, false},
                {"2002-05-30T8:59:59.800", 199, false},
                {"2002-05-30T8:59:59.800", 200, true},
                {"2002-05-30T8:59:59.800", 300, true},
                {"2002-05-30T8:59:59.800", 301, false},
                {"2002-05-30T9:00:00.050", 0, true},
                {"2002-05-30T9:00:00.099", 0, true},
                {"2002-05-30T9:00:00.100", 0, true},
                {"2002-05-30T9:00:00.101", 0, false},
        };
        expectedValidator = new CoincidesValidator(200L, 50L);
        assertExpression(seedTime, 50, "a.coincides(b, 200 milliseconds, 50 milliseconds)", expected, expectedValidator);

        // test expression params
        epService.getEPAdministrator().createEPL("create variable long V_START = 200");
        epService.getEPAdministrator().createEPL("create variable long V_END = 50");
        assertExpression(seedTime, 50, "a.coincides(b, V_START milliseconds, V_END milliseconds)", expected, expectedValidator);

        epService.getEPRuntime().setVariableValue("V_START", 200);
        epService.getEPRuntime().setVariableValue("V_END", 70);
        expected = new Object[][] {
                {"2002-05-30T8:59:59.800", 0, false},
                {"2002-05-30T8:59:59.800", 179, false},
                {"2002-05-30T8:59:59.800", 180, true},
                {"2002-05-30T8:59:59.800", 200, true},
                {"2002-05-30T8:59:59.800", 320, true},
                {"2002-05-30T8:59:59.800", 321, false},
        };
        expectedValidator = new CoincidesValidator(200L, 70L);
        assertExpression(seedTime, 50, "a.coincides(b, V_START milliseconds, V_END milliseconds)", expected, expectedValidator);
    }

    private void assertExpression(String seedTime, long seedDuration, String whereClause, Object[][] timestampsAndResult, Validator validator) {

        String epl = "select * from A.std:lastevent() as a, B.std:lastevent() as b " +
                "where " + whereClause;
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(SupportTimeDurationB.make("B", seedTime, seedDuration));

        for (Object[] test : timestampsAndResult) {
            String testtime = (String) test[0];
            Long testduration = ((Number) test[1]).longValue();
            boolean expected = (Boolean) test[2];

            long right = SupportDateTime.parse(seedTime).getTime();
            long left = SupportDateTime.parse(testtime).getTime();
            String message = "time " + testtime + " duration " + testduration + " for '" + whereClause + "'";

            assertEquals("Validation of expected result failed for " + message, expected, validator.validate(left, testduration, right, seedDuration));

            epService.getEPRuntime().sendEvent(SupportTimeDurationA.make("A", testtime, testduration));

            if (!listener.isInvoked() && expected) {
                fail("Expected but not received for " + message);
            }
            if (listener.isInvoked() && !expected) {
                fail("Not expected but received for " + message);
            }
            listener.reset();
        }

        stmt.destroy();
    }

    private interface Validator {
        public boolean validate(long left, long durationLeft, long right, long durationRight);
    }

    private class BeforeValidator implements Validator {
        private Long start;
        private Long end;

        private BeforeValidator(Long start, Long end) {
            this.start = start;
            this.end = end;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {
            long delta = right - (left + durationLeft);
            return start <= delta && delta <= end;
        }
    }

    private class AfterValidator implements Validator {
        private Long start;
        private Long end;

        private AfterValidator(Long start, Long end) {
            this.start = start;
            this.end = end;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {
            long delta = left - (right + durationRight);
            return start <= delta && delta <= end;
        }
    }

    private class CoincidesValidator implements Validator {
        private final Long startThreshold;
        private final Long endThreshold;

        private CoincidesValidator() {
            startThreshold = 0L;
            endThreshold = 0L;
        }

        private CoincidesValidator(Long startThreshold) {
            this.startThreshold = startThreshold;
            this.endThreshold = startThreshold;
        }

        private CoincidesValidator(Long startThreshold, Long endThreshold) {
            this.startThreshold = startThreshold;
            this.endThreshold = endThreshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {
            long startDelta = Math.abs(left - right);
            long endDelta = Math.abs((left + durationLeft) - (right + durationRight));
            return ((startDelta <= startThreshold) &&
                   (endDelta <= endThreshold));
        }
    }
}
