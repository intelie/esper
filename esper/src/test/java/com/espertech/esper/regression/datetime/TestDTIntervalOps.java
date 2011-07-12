package com.espertech.esper.regression.datetime;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
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
    // test configuration returns Date or Calendar object
    // test bean actually returns null for duration or timestamp
    // test "a.getTime(xxx)" and "a.toCalendar()"
    // test "a.get(b.timestamp)"

    // TODO
    // doc ConfigurationEventTypeLegacy "timestamp" and "duration" property names, all others
    // all types of datetime expression

    // TODO
    // remaining event types and their configs for timestamp and duration
    // create-schema support for timestamp and duration

    // TODO - performance
    // all operators
    // test outer join performance

    public void testCalendarOps() {
        String seedTime = "2002-05-30T9:00:00.000"; // seed is time for B

        Object[][] expected = {
                {"2999-01-01T9:00:00.001", 0, true},       // sending in A
        };
        assertExpression(seedTime, 0, "a.withDate(2001, 1, 1).before(b)", expected, null);

        expected = new Object[][] {
                {"2999-01-01T10:00:00.001", 0, false},
                {"2999-01-01T8:00:00.001", 0, true},
        };
        assertExpression(seedTime, 0, "a.withDate(2001, 1, 1).before(b.withDate(2001, 1, 1))", expected, null);

        // Test duration preserved when using calendar ops
        // Test target preserves duration.
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 2000, false},
        };
        assertExpression(seedTime, 0, "a.before(b)", expected, null);
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 2000, false},
        };
        assertExpression(seedTime, 0, "a.withTime(8, 59, 59, 0).before(b)", expected, null);

        // Test duration preserved when using calendar ops
        // Parameter preserves duration.
        expected = new Object[][] {
                {"2002-05-30T9:00:01.000", 0, false},
                {"2002-05-30T9:00:01.001", 0, true},
        };
        assertExpression(seedTime, 1000, "a.after(b)", expected, null);
        assertExpression(seedTime, 0, "a.after(b.withTime(9, 0, 0, 0))", expected, null);   // the "b.withTime(...) must retain the duration
    }

    public void testInvalid() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class.getName());

        // wrong 1st parameter - string
        tryInvalid("select a.before('x') from A as a",
                   "Error starting statement: Failed to resolve enumeration method, date-time method or mapped property 'a.before('x')': For date-time method 'before' the first parameter expression returns 'class java.lang.String', however requires a Date, Calendar, Long-type return value or event (with timestamp) [select a.before('x') from A as a]");

        // wrong 1st parameter - event not defined with timestamp expression
        tryInvalid("select a.before(b) from A.std:lastevent() as a, SupportBean.std:lastevent() as b",
                   "Error starting statement: For date-time method 'before' the first parameter is event type 'SupportBean', however no timestamp property has been defined for this event type [select a.before(b) from A.std:lastevent() as a, SupportBean.std:lastevent() as b]");

        // wrong 1st parameter - boolean
        tryInvalid("select a.before(true) from A.std:lastevent() as a, SupportBean.std:lastevent() as b",
                   "Error starting statement: For date-time method 'before' the first parameter expression returns 'class java.lang.Boolean', however requires a Date, Calendar, Long-type return value or event (with timestamp) [select a.before(true) from A.std:lastevent() as a, SupportBean.std:lastevent() as b]");

        // wrong zero parameters
        tryInvalid("select a.before() from A.std:lastevent() as a, SupportBean.std:lastevent() as b",
                   "Error starting statement: Parameters mismatch for date-time method 'before', the method has multiple footprints accepting an expression providing timestamp or timestamped-event, or an expression providing timestamp or timestamped-event and an expression providing interval start value, or an expression providing timestamp or timestamped-event and an expression providing interval start value and an expression providing interval finishes value, but receives no parameters [select a.before() from A.std:lastevent() as a, SupportBean.std:lastevent() as b]");

        // wrong target
        tryInvalid("select string.before(a) from A.std:lastevent() as a, SupportBean.std:lastevent() as b",
                   "Error starting statement: Date-time enumeration method 'before' requires either a Calendar, Date or long value as input or events of an event type that declares a timestamp property but received java.lang.String [select string.before(a) from A.std:lastevent() as a, SupportBean.std:lastevent() as b]");
        tryInvalid("select b.before(a) from A.std:lastevent() as a, SupportBean.std:lastevent() as b",
                   "Error starting statement: Date-time enumeration method 'before' requires either a Calendar, Date or long value as input or events of an event type that declares a timestamp property [select b.before(a) from A.std:lastevent() as a, SupportBean.std:lastevent() as b]");
        tryInvalid("select a.get('month').before(a) from A.std:lastevent() as a, SupportBean.std:lastevent() as b",
                   "Error starting statement: Invalid input for date-time method 'before' [select a.get('month').before(a) from A.std:lastevent() as a, SupportBean.std:lastevent() as b]");

        // test before/after
        tryInvalid("select a.before(b, 'abc') from A.std:lastevent() as a, B.std:lastevent() as b",
                   "Error starting statement: Error validating date-time method 'before', expected a time-period expression or a numeric-type result for expression parameter 1 but received java.lang.String [select a.before(b, 'abc') from A.std:lastevent() as a, B.std:lastevent() as b]");
        tryInvalid("select a.before(b, 1, 'def') from A.std:lastevent() as a, B.std:lastevent() as b",
                   "Error starting statement: Error validating date-time method 'before', expected a time-period expression or a numeric-type result for expression parameter 2 but received java.lang.String [select a.before(b, 1, 'def') from A.std:lastevent() as a, B.std:lastevent() as b]");
        tryInvalid("select a.before(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b",
                   "Error starting statement: Parameters mismatch for date-time method 'before', the method has multiple footprints accepting an expression providing timestamp or timestamped-event, or an expression providing timestamp or timestamped-event and an expression providing interval start value, or an expression providing timestamp or timestamped-event and an expression providing interval start value and an expression providing interval finishes value, but receives 4 expressions [select a.before(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b]");

        // test coincides
        tryInvalid("select a.coincides(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b",
                   "Error starting statement: Parameters mismatch for date-time method 'coincides', the method has multiple footprints accepting an expression providing timestamp or timestamped-event, or an expression providing timestamp or timestamped-event and an expression providing threshold for start and end value, or an expression providing timestamp or timestamped-event and an expression providing threshold for start value and an expression providing threshold for end value, but receives 4 expressions [select a.coincides(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b]");

        // test during+interval
        tryInvalid("select a.during(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b",
                   "Error starting statement: Parameters mismatch for date-time method 'during', the method has multiple footprints accepting an expression providing timestamp or timestamped-event, or an expression providing timestamp or timestamped-event and an expression providing maximum distance interval both start and end, or an expression providing timestamp or timestamped-event and an expression providing minimum distance interval both start and end and an expression providing maximum distance interval both start and end, or an expression providing timestamp or timestamped-event and an expression providing minimum distance start and an expression providing maximum distance start and an expression providing minimum distance end and an expression providing maximum distance end, but receives 4 expressions [select a.during(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b]");

        // test finishes+finished-by
        tryInvalid("select a.finishes(b, 1, 2) from A.std:lastevent() as a, B.std:lastevent() as b",
                   "Error starting statement: Parameters mismatch for date-time method 'finishes', the method has multiple footprints accepting an expression providing timestamp or timestamped-event, or an expression providing timestamp or timestamped-event and an expression providing maximum distance between end timestamps, but receives 3 expressions [select a.finishes(b, 1, 2) from A.std:lastevent() as a, B.std:lastevent() as b]");

        // test meets+met-by
        tryInvalid("select a.meets(b, 1, 2) from A.std:lastevent() as a, B.std:lastevent() as b",
                   "Error starting statement: Parameters mismatch for date-time method 'meets', the method has multiple footprints accepting an expression providing timestamp or timestamped-event, or an expression providing timestamp or timestamped-event and an expression providing maximum distance between start and end timestamps, but receives 3 expressions [select a.meets(b, 1, 2) from A.std:lastevent() as a, B.std:lastevent() as b]");

        // test overlaps+overlapped-by
        tryInvalid("select a.overlaps(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b",
                   "Error starting statement: Parameters mismatch for date-time method 'overlaps', the method has multiple footprints accepting an expression providing timestamp or timestamped-event, or an expression providing timestamp or timestamped-event and an expression providing maximum distance interval both start and end, or an expression providing timestamp or timestamped-event and an expression providing minimum distance interval both start and end and an expression providing maximum distance interval both start and end, but receives 4 expressions [select a.overlaps(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b]");

        // test start/startedby
        tryInvalid("select a.starts(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b",
                   "Error starting statement: Parameters mismatch for date-time method 'starts', the method has multiple footprints accepting an expression providing timestamp or timestamped-event, or an expression providing timestamp or timestamped-event and an expression providing maximum distance between start timestamps, but receives 4 expressions [select a.starts(b, 1, 2, 3) from A.std:lastevent() as a, B.std:lastevent() as b]");
    }

    public void testInvalidConfig() {
        ConfigurationEventTypeLegacy configBean = new ConfigurationEventTypeLegacy();

        configBean.setTimestampProperty("xyz");
        tryInvalidConfig(configBean, "Declared timestamp property name 'xyz' was not found");

        configBean.setTimestampProperty("longPrimitive");
        configBean.setDurationProperty("xyz");
        tryInvalidConfig(configBean, "Declared duration property name 'xyz' was not found");

        configBean.setDurationProperty(null);
        configBean.setTimestampProperty("string");
        tryInvalidConfig(configBean, "Declared timestamp property does not return a Date, Calendar or long-typed value for property 'string'");

        configBean.setTimestampProperty("longPrimitive");
        configBean.setDurationProperty("string");
        tryInvalidConfig(configBean, "Declared duration property does not return a long-typed value for property 'string'");
    }

    private void tryInvalidConfig(ConfigurationEventTypeLegacy config, String message) {
        try {
            epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class.getName(), config);
            fail();
        }
        catch (ConfigurationException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    private void tryInvalid(String epl, String message) {
        try {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

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

    public void testDuringWhereClause() {

        Validator expectedValidator = new DuringValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, true},
                {"2002-05-30T9:00:00.001", 98, true},
                {"2002-05-30T9:00:00.001", 99, false},
                {"2002-05-30T9:00:00.099", 0, true},
                {"2002-05-30T9:00:00.099", 1, false},
                {"2002-05-30T9:00:00.100", 0, false},
        };
        assertExpression(seedTime, 100, "a.during(b)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, false},
                {"2002-05-30T9:00:00.001", 1, false},
        };
        assertExpression(seedTime, 0, "a.during(b)", expected, expectedValidator);

        // test msec-NO-duration
        expected = new Object[][] {
                {"2002-05-30T9:00:00.001", 0, true},
                {"2002-05-30T9:00:00.001", 2000000, true},
        };
        assertExpression(seedTime, 100, "a.msecdate.during(b)", expected, null);    // want to use null-validator here

        // test 1-parameter footprint
        expected = new Object[][] {
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 100, false},
                {"2002-05-30T9:00:00.001", 0, false},
                {"2002-05-30T9:00:00.001", 83, false},
                {"2002-05-30T9:00:00.001", 84, true},
                {"2002-05-30T9:00:00.001", 98, true},
                {"2002-05-30T9:00:00.001", 99, false},
                {"2002-05-30T9:00:00.015", 69, false},
                {"2002-05-30T9:00:00.015", 70, true},
                {"2002-05-30T9:00:00.015", 84, true},
                {"2002-05-30T9:00:00.015", 85, false},
                {"2002-05-30T9:00:00.016", 80, false},
                {"2002-05-30T9:00:00.099", 0, false},
        };
        expectedValidator = new DuringValidator(15L);
        assertExpression(seedTime, 100, "a.during(b, 15 milliseconds)", expected, expectedValidator);

        // test 2-parameter footprint
        expected = new Object[][] {
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 100, false},
                {"2002-05-30T9:00:00.001", 0, false},
                {"2002-05-30T9:00:00.001", 78, false},
                {"2002-05-30T9:00:00.001", 79, false},
                {"2002-05-30T9:00:00.004", 85, false},
                {"2002-05-30T9:00:00.005", 74, false},
                {"2002-05-30T9:00:00.005", 75, true},
                {"2002-05-30T9:00:00.005", 90, true},
                {"2002-05-30T9:00:00.005", 91, false},
                {"2002-05-30T9:00:00.006", 83, true},
                {"2002-05-30T9:00:00.020", 76, false},
                {"2002-05-30T9:00:00.020", 75, true},
                {"2002-05-30T9:00:00.020", 60, true},
                {"2002-05-30T9:00:00.020", 59, false},
                {"2002-05-30T9:00:00.021", 68, false},
                {"2002-05-30T9:00:00.099", 0, false},
        };
        expectedValidator = new DuringValidator(5L, 20L);
        assertExpression(seedTime, 100, "a.during(b, 5 milliseconds, 20 milliseconds)", expected, expectedValidator);

        // test 4-parameter footprint
        expected = new Object[][] {
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 100, false},
                {"2002-05-30T9:00:00.004", 85, false},
                {"2002-05-30T9:00:00.005", 64, false},
                {"2002-05-30T9:00:00.005", 65, true},
                {"2002-05-30T9:00:00.005", 85, true},
                {"2002-05-30T9:00:00.005", 86, false},
                {"2002-05-30T9:00:00.020", 49, false},
                {"2002-05-30T9:00:00.020", 50, true},
                {"2002-05-30T9:00:00.020", 70, true},
                {"2002-05-30T9:00:00.020", 71, false},
                {"2002-05-30T9:00:00.021", 55, false},
        };
        expectedValidator = new DuringValidator(5L, 20L, 10L, 30L);
        assertExpression(seedTime, 100, "a.during(b, 5 milliseconds, 20 milliseconds, 10 milliseconds, 30 milliseconds)", expected, expectedValidator);
    }

    public void testFinishesWhereClause() {

        Validator expectedValidator = new FinishesValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.001", 0, false},
                {"2002-05-30T9:00:00.001", 98, false},
                {"2002-05-30T9:00:00.001", 99, true},
                {"2002-05-30T9:00:00.001", 100, false},
                {"2002-05-30T9:00:00.050", 50, true},
                {"2002-05-30T9:00:00.099", 0, false},
                {"2002-05-30T9:00:00.099", 1, true},
                {"2002-05-30T9:00:00.100", 0, true},
                {"2002-05-30T9:00:00.101", 0, false},
        };
        assertExpression(seedTime, 100, "a.finishes(b)", expected, expectedValidator);
        assertExpression(seedTime, 100, "a.finishes(b, 0)", expected, expectedValidator);
        assertExpression(seedTime, 100, "a.finishes(b, 0 milliseconds)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 99, false},
                {"2002-05-30T9:00:00.001", 93, false},
                {"2002-05-30T9:00:00.001", 94, true},
                {"2002-05-30T9:00:00.001", 100, true},
                {"2002-05-30T9:00:00.001", 104, true},
                {"2002-05-30T9:00:00.001", 105, false},
                {"2002-05-30T9:00:00.050", 50, true},
                {"2002-05-30T9:00:00.104", 0, true},
                {"2002-05-30T9:00:00.104", 1, true},
                {"2002-05-30T9:00:00.105", 0, true},
                {"2002-05-30T9:00:00.105", 1, false},
        };
        expectedValidator = new FinishesValidator(5L);
        assertExpression(seedTime, 100, "a.finishes(b, 5 milliseconds)", expected, expectedValidator);
    }

    public void testFinishedByWhereClause() {

        Validator expectedValidator = new FinishedByValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T8:59:59.000", 1099, false},
                {"2002-05-30T8:59:59.000", 1100, true},
                {"2002-05-30T8:59:59.000", 1101, false},
                {"2002-05-30T8:59:59.999", 100, false},
                {"2002-05-30T8:59:59.999", 101, true},
                {"2002-05-30T8:59:59.999", 102, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 50, false},
                {"2002-05-30T9:00:00.000", 100, false},
        };
        assertExpression(seedTime, 100, "a.finishedBy(b)", expected, expectedValidator);
        assertExpression(seedTime, 100, "a.finishedBy(b, 0)", expected, expectedValidator);
        assertExpression(seedTime, 100, "a.finishedBy(b, 0 milliseconds)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T8:59:59.000", 1094, false},
                {"2002-05-30T8:59:59.000", 1095, true},
                {"2002-05-30T8:59:59.000", 1105, true},
                {"2002-05-30T8:59:59.000", 1106, false},
                {"2002-05-30T8:59:59.999", 95, false},
                {"2002-05-30T8:59:59.999", 96, true},
                {"2002-05-30T8:59:59.999", 106, true},
                {"2002-05-30T8:59:59.999", 107, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 95, false},
                {"2002-05-30T9:00:00.000", 100, false},
                {"2002-05-30T9:00:00.000", 105, false},
        };
        expectedValidator = new FinishedByValidator(5L);
        assertExpression(seedTime, 100, "a.finishedBy(b, 5 milliseconds)", expected, expectedValidator);
    }

    public void testIncludesByWhereClause() {

        Validator expectedValidator = new IncludesValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 1100, false},
                {"2002-05-30T8:59:59.000", 1101, true},
                {"2002-05-30T8:59:59.000", 3000, true},
                {"2002-05-30T8:59:59.999", 101, false},
                {"2002-05-30T8:59:59.999", 102, true},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 50, false},
                {"2002-05-30T9:00:00.000", 102, false},
        };
        assertExpression(seedTime, 100, "a.includes(b)", expected, expectedValidator);

        // test 1-parameter form
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T8:59:59.000", 1100, false},
                {"2002-05-30T8:59:59.000", 1105, false},
                {"2002-05-30T8:59:59.994", 106, false},
                {"2002-05-30T8:59:59.994", 110, false},
                {"2002-05-30T8:59:59.995", 105, false},
                {"2002-05-30T8:59:59.995", 106, true},
                {"2002-05-30T8:59:59.995", 110, true},
                {"2002-05-30T8:59:59.995", 111, false},
                {"2002-05-30T8:59:59.999", 101, false},
                {"2002-05-30T8:59:59.999", 102, true},
                {"2002-05-30T8:59:59.999", 106, true},
                {"2002-05-30T8:59:59.999", 107, false},
                {"2002-05-30T9:00:00.000", 105, false},
                {"2002-05-30T9:00:00.000", 106, false},
        };
        expectedValidator = new IncludesValidator(5L);
        assertExpression(seedTime, 100, "a.includes(b, 5 milliseconds)", expected, expectedValidator);

        // test 2-parameter form
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T8:59:59.000", 1100, false},
                {"2002-05-30T8:59:59.000", 1105, false},
                {"2002-05-30T8:59:59.979", 130, false},
                {"2002-05-30T8:59:59.980", 124, false},
                {"2002-05-30T8:59:59.980", 125, true},
                {"2002-05-30T8:59:59.980", 140, true},
                {"2002-05-30T8:59:59.980", 141, false},
                {"2002-05-30T8:59:59.995", 109, false},
                {"2002-05-30T8:59:59.995", 110, true},
                {"2002-05-30T8:59:59.995", 125, true},
                {"2002-05-30T8:59:59.995", 126, false},
                {"2002-05-30T8:59:59.996", 112, false},
        };
        expectedValidator = new IncludesValidator(5L, 20L);
        assertExpression(seedTime, 100, "a.includes(b, 5 milliseconds, 20 milliseconds)", expected, expectedValidator);

        // test 4-parameter form
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T8:59:59.000", 1100, false},
                {"2002-05-30T8:59:59.000", 1105, false},
                {"2002-05-30T8:59:59.979", 150, false},
                {"2002-05-30T8:59:59.980", 129, false},
                {"2002-05-30T8:59:59.980", 130, true},
                {"2002-05-30T8:59:59.980", 150, true},
                {"2002-05-30T8:59:59.980", 151, false},
                {"2002-05-30T8:59:59.995", 114, false},
                {"2002-05-30T8:59:59.995", 115, true},
                {"2002-05-30T8:59:59.995", 135, true},
                {"2002-05-30T8:59:59.995", 136, false},
                {"2002-05-30T8:59:59.996", 124, false},
        };
        expectedValidator = new IncludesValidator(5L, 20L, 10L, 30L);
        assertExpression(seedTime, 100, "a.includes(b, 5 milliseconds, 20 milliseconds, 10 milliseconds, 30 milliseconds)", expected, expectedValidator);
    }

    public void testMeetsWhereClause() {

        Validator expectedValidator = new MeetsValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 1000, true},
                {"2002-05-30T8:59:59.000", 1001, false},
                {"2002-05-30T8:59:59.998", 1, false},
                {"2002-05-30T8:59:59.999", 1, true},
                {"2002-05-30T9:00:00.000", 0, true},
                {"2002-05-30T9:00:00.000", 1, false},
                {"2002-05-30T9:00:00.001", 0, false},
        };
        assertExpression(seedTime, 0, "a.meets(b)", expected, expectedValidator);

        // test 1-parameter form
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 0, false},
                {"2002-05-30T8:59:59.000", 994, false},
                {"2002-05-30T8:59:59.000", 995, true},
                {"2002-05-30T8:59:59.000", 1005, true},
                {"2002-05-30T8:59:59.000", 1006, false},
                {"2002-05-30T8:59:59.994", 0, false},
                {"2002-05-30T8:59:59.994", 1, true},
                {"2002-05-30T8:59:59.995", 0, true},
                {"2002-05-30T8:59:59.999", 0, true},
                {"2002-05-30T8:59:59.999", 1, true},
                {"2002-05-30T8:59:59.999", 6, true},
                {"2002-05-30T8:59:59.999", 7, false},
                {"2002-05-30T9:00:00.000", 0, true},
                {"2002-05-30T9:00:00.000", 1, true},
                {"2002-05-30T9:00:00.000", 5, true},
                {"2002-05-30T9:00:00.005", 0, true},
                {"2002-05-30T9:00:00.005", 1, false},
        };
        expectedValidator = new MeetsValidator(5L);
        assertExpression(seedTime, 0, "a.meets(b, 5 milliseconds)", expected, expectedValidator);
    }

    public void testMetByWhereClause() {

        Validator expectedValidator = new MetByValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T9:00:00.99", 0, false},
                {"2002-05-30T9:00:00.100", 0, true},
                {"2002-05-30T9:00:00.100", 500, true},
                {"2002-05-30T9:00:00.101", 0, false},
        };
        assertExpression(seedTime, 100, "a.metBy(b)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T8:59:59.999", 1, false},
                {"2002-05-30T9:00:00.000", 0, true},
                {"2002-05-30T9:00:00.000", 1, true},
        };
        assertExpression(seedTime, 0, "a.metBy(b)", expected, expectedValidator);

        // test 1-parameter form
        expected = new Object[][] {
                {"2002-05-30T8:59:59.994", 0, false},
                {"2002-05-30T8:59:59.994", 5, false},
                {"2002-05-30T8:59:59.995", 0, true},
                {"2002-05-30T9:00:00.000", 0, true},
                {"2002-05-30T9:00:00.000", 20, true},
                {"2002-05-30T9:00:00.005", 0, true},
                {"2002-05-30T9:00:00.005", 1000, true},
                {"2002-05-30T9:00:00.006", 0, false},
        };
        expectedValidator = new MetByValidator(5L);
        assertExpression(seedTime, 0, "a.metBy(b, 5 milliseconds)", expected, expectedValidator);

        expected = new Object[][] {
                {"2002-05-30T8:59:59.994", 0, false},
                {"2002-05-30T8:59:59.994", 5, false},
                {"2002-05-30T8:59:59.995", 0, false},
                {"2002-05-30T9:00:00.094", 0, false},
                {"2002-05-30T9:00:00.095", 0, true},
                {"2002-05-30T9:00:00.105", 0, true},
                {"2002-05-30T9:00:00.105", 5000, true},
                {"2002-05-30T9:00:00.106", 0, false},
        };
        expectedValidator = new MetByValidator(5L);
        assertExpression(seedTime, 100, "a.metBy(b, 5 milliseconds)", expected, expectedValidator);
    }

    public void testOverlapsWhereClause() {

        Validator expectedValidator = new OverlapsValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 1000, false},
                {"2002-05-30T8:59:59.000", 1001, true},
                {"2002-05-30T8:59:59.000", 1050, true},
                {"2002-05-30T8:59:59.000", 1099, true},
                {"2002-05-30T8:59:59.000", 1100, false},
                {"2002-05-30T8:59:59.999", 1, false},
                {"2002-05-30T8:59:59.999", 2, true},
                {"2002-05-30T8:59:59.999", 100, true},
                {"2002-05-30T8:59:59.999", 101, false},
                {"2002-05-30T9:00:00.000", 0, false},
        };
        assertExpression(seedTime, 100, "a.overlaps(b)", expected, expectedValidator);

        // test 1-parameter form (overlap by not more then X msec)
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 1000, false},
                {"2002-05-30T8:59:59.000", 1001, true},
                {"2002-05-30T8:59:59.000", 1005, true},
                {"2002-05-30T8:59:59.000", 1006, false},
                {"2002-05-30T8:59:59.000", 1100, false},
                {"2002-05-30T8:59:59.999", 1, false},
                {"2002-05-30T8:59:59.999", 2, true},
                {"2002-05-30T8:59:59.999", 6, true},
                {"2002-05-30T8:59:59.999", 7, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 5, false},
        };
        expectedValidator = new OverlapsValidator(5L);
        assertExpression(seedTime, 100, "a.overlaps(b, 5 milliseconds)", expected, expectedValidator);

        // test 2-parameter form (overlap by min X and not more then Y msec)
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 1004, false},
                {"2002-05-30T8:59:59.000", 1005, true},
                {"2002-05-30T8:59:59.000", 1010, true},
                {"2002-05-30T8:59:59.000", 1011, false},
                {"2002-05-30T8:59:59.999", 5, false},
                {"2002-05-30T8:59:59.999", 6, true},
                {"2002-05-30T8:59:59.999", 11, true},
                {"2002-05-30T8:59:59.999", 12, false},
                {"2002-05-30T8:59:59.999", 12, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 5, false},
        };
        expectedValidator = new OverlapsValidator(5L, 10L);
        assertExpression(seedTime, 100, "a.overlaps(b, 5 milliseconds, 10 milliseconds)", expected, expectedValidator);
    }

    public void testOverlappedByWhereClause() {

        Validator expectedValidator = new OverlappedByValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.000", 1000, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 1, false},
                {"2002-05-30T9:00:00.001", 99, false},
                {"2002-05-30T9:00:00.001", 100, true},
                {"2002-05-30T9:00:00.099", 1, false},
                {"2002-05-30T9:00:00.099", 2, true},
                {"2002-05-30T9:00:00.100", 0, false},
                {"2002-05-30T9:00:00.100", 1, false},
        };
        assertExpression(seedTime, 100, "a.overlappedBy(b)", expected, expectedValidator);

        // test 1-parameter form (overlap by not more then X msec)
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 1000, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 1, false},
                {"2002-05-30T9:00:00.001", 99, false},
                {"2002-05-30T9:00:00.094", 7, false},
                {"2002-05-30T9:00:00.094", 100, false},
                {"2002-05-30T9:00:00.095", 5, false},
                {"2002-05-30T9:00:00.095", 6, true},
                {"2002-05-30T9:00:00.095", 100, true},
                {"2002-05-30T9:00:00.099", 1, false},
                {"2002-05-30T9:00:00.099", 2, true},
                {"2002-05-30T9:00:00.099", 100, true},
                {"2002-05-30T9:00:00.100", 100, false},
        };
        expectedValidator = new OverlappedByValidator(5L);
        assertExpression(seedTime, 100, "a.overlappedBy(b, 5 milliseconds)", expected, expectedValidator);

        // test 2-parameter form (overlap by min X and not more then Y msec)
        expected = new Object[][] {
                {"2002-05-30T8:59:59.000", 1000, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 1, false},
                {"2002-05-30T9:00:00.001", 99, false},
                {"2002-05-30T9:00:00.089", 14, false},
                {"2002-05-30T9:00:00.090", 10, false},
                {"2002-05-30T9:00:00.090", 11, true},
                {"2002-05-30T9:00:00.090", 1000, true},
                {"2002-05-30T9:00:00.095", 5, false},
                {"2002-05-30T9:00:00.095", 6, true},
                {"2002-05-30T9:00:00.096", 5, false},
                {"2002-05-30T9:00:00.096", 100, false},
                {"2002-05-30T9:00:00.100", 100, false},
        };
        expectedValidator = new OverlappedByValidator(5L, 10L);
        assertExpression(seedTime, 100, "a.overlappedBy(b, 5 milliseconds, 10 milliseconds)", expected, expectedValidator);
    }

    public void testStartsWhereClause() {

        Validator expectedValidator = new StartsValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.999", 100, false},
                {"2002-05-30T9:00:00.000", 0, true},
                {"2002-05-30T9:00:00.000", 1, true},
                {"2002-05-30T9:00:00.000", 99, true},
                {"2002-05-30T9:00:00.000", 100, false},
                {"2002-05-30T9:00:00.001", 0, false},
        };
        assertExpression(seedTime, 100, "a.starts(b)", expected, expectedValidator);

        // test 1-parameter form (max distance between start times)
        expected = new Object[][] {
                {"2002-05-30T8:59:59.994", 6, false},
                {"2002-05-30T8:59:59.995", 0, true},
                {"2002-05-30T8:59:59.995", 104, true},
                {"2002-05-30T8:59:59.995", 105, false},
                {"2002-05-30T9:00:00.000", 0, true},
                {"2002-05-30T9:00:00.000", 1, true},
                {"2002-05-30T9:00:00.000", 99, true},
                {"2002-05-30T9:00:00.000", 100, false},
                {"2002-05-30T9:00:00.001", 0, true},
                {"2002-05-30T9:00:00.005", 94, true},
                {"2002-05-30T9:00:00.005", 95, false},
                {"2002-05-30T9:00:00.005", 100, false},
        };
        expectedValidator = new StartsValidator(5L);
        assertExpression(seedTime, 100, "a.starts(b, 5 milliseconds)", expected, expectedValidator);
    }

    public void testStartedByWhereClause() {

        Validator expectedValidator = new StartedByValidator();
        String seedTime = "2002-05-30T9:00:00.000";
        Object[][] expected = {
                {"2002-05-30T8:59:59.999", 100, false},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 100, false},
                {"2002-05-30T9:00:00.000", 101, true},
                {"2002-05-30T9:00:00.001", 0, false},
                {"2002-05-30T9:00:00.001", 101, false},
        };
        assertExpression(seedTime, 100, "a.startedBy(b)", expected, expectedValidator);

        // test 1-parameter form (max distance between start times)
        expected = new Object[][] {
                {"2002-05-30T8:59:59.994", 6, false},
                {"2002-05-30T8:59:59.995", 0, false},
                {"2002-05-30T8:59:59.995", 105, false},
                {"2002-05-30T8:59:59.995", 106, true},
                {"2002-05-30T9:00:00.000", 0, false},
                {"2002-05-30T9:00:00.000", 100, false},
                {"2002-05-30T9:00:00.000", 101, true},
                {"2002-05-30T9:00:00.001", 99, false},
                {"2002-05-30T9:00:00.001", 100, true},
                {"2002-05-30T9:00:00.005", 94, false},
                {"2002-05-30T9:00:00.005", 95, false},
                {"2002-05-30T9:00:00.005", 96, true},
        };
        expectedValidator = new StartedByValidator(5L);
        assertExpression(seedTime, 100, "a.startedBy(b, 5 milliseconds)", expected, expectedValidator);
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

            if (validator != null) {
                assertEquals("Validation of expected result failed for " + message, expected, validator.validate(left, testduration, right, seedDuration));
            }

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

    private class DuringValidator implements Validator {

        private int form;
        private Long threshold;
        private Long minThreshold;
        private Long maxThreshold;
        private Long minStartThreshold;
        private Long maxStartThreshold;
        private Long minEndThreshold;
        private Long maxEndThreshold;

        private DuringValidator() {
            form = 1;
        }

        private DuringValidator(Long threshold) {
            form = 2;
            this.threshold = threshold;
        }

        private DuringValidator(Long minThreshold, Long maxThreshold) {
            form = 3;
            this.minThreshold = minThreshold;
            this.maxThreshold = maxThreshold;
        }

        private DuringValidator(Long minStartThreshold, Long maxStartThreshold, Long minEndThreshold, Long maxEndThreshold) {
            form = 4;
            this.minStartThreshold = minStartThreshold;
            this.maxStartThreshold = maxStartThreshold;
            this.minEndThreshold = minEndThreshold;
            this.maxEndThreshold = maxEndThreshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {
            if (form == 1) {
                return right < left &&
                       (left + durationLeft) < (right + durationRight);
            }
            else if (form == 2) {
                long distanceStart = left - right;
                if (distanceStart <= 0 || distanceStart > threshold) {
                    return false;
                }
                long distanceEnd = (right + durationRight) - (left + durationLeft);
                return !(distanceEnd <= 0 || distanceEnd > threshold);
            }
            else if (form == 3) {
                long distanceStart = left - right;
                if (distanceStart < minThreshold || distanceStart > maxThreshold) {
                    return false;
                }
                long distanceEnd = (right + durationRight) - (left + durationLeft);
                return !(distanceEnd < minThreshold || distanceEnd > maxThreshold);
            }
            else if (form == 4) {
                long distanceStart = left - right;
                if (distanceStart < minStartThreshold || distanceStart > maxStartThreshold) {
                    return false;
                }
                long distanceEnd = (right + durationRight) - (left + durationLeft);
                return !(distanceEnd < minEndThreshold || distanceEnd > maxEndThreshold);
            }
            throw new IllegalStateException("Invalid form: " + form);
        }
    }

    private class FinishesValidator implements Validator {
        private Long threshold;

        private FinishesValidator() {
        }

        private FinishesValidator(Long threshold) {
            this.threshold = threshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {
            if (threshold == null) {
                return ((right < left) && ((left + durationLeft) == (right + durationRight)));
            }
            else {
                if (right >= left) {
                    return false;
                }
                long delta = Math.abs((left + durationLeft) - (right + durationRight));
                return delta <= threshold;
            }
        }
    }

    private class FinishedByValidator implements Validator {
        private Long threshold;

        private FinishedByValidator() {
        }

        private FinishedByValidator(Long threshold) {
            this.threshold = threshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {

            if (threshold == null) {
                return ((left < right) && ((left + durationLeft) == (right + durationRight)));
            }
            else {
                if (left >= right) {
                    return false;
                }
                long delta = Math.abs((left + durationLeft) - (right + durationRight));
                return delta <= threshold;
            }
        }
    }

    private class IncludesValidator implements Validator {

        private int form;
        private Long threshold;
        private Long minThreshold;
        private Long maxThreshold;
        private Long minStartThreshold;
        private Long maxStartThreshold;
        private Long minEndThreshold;
        private Long maxEndThreshold;

        private IncludesValidator() {
            form = 1;
        }

        private IncludesValidator(Long threshold) {
            form = 2;
            this.threshold = threshold;
        }

        private IncludesValidator(Long minThreshold, Long maxThreshold) {
            form = 3;
            this.minThreshold = minThreshold;
            this.maxThreshold = maxThreshold;
        }

        private IncludesValidator(Long minStartThreshold, Long maxStartThreshold, Long minEndThreshold, Long maxEndThreshold) {
            form = 4;
            this.minStartThreshold = minStartThreshold;
            this.maxStartThreshold = maxStartThreshold;
            this.minEndThreshold = minEndThreshold;
            this.maxEndThreshold = maxEndThreshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {
                                
            if (form == 1) {
                return left < right &&
                       (right + durationRight) < (left + durationLeft);
            }
            else if (form == 2) {
                long distanceStart = right - left;
                if (distanceStart <= 0 || distanceStart > threshold) {
                    return false;
                }
                long distanceEnd = (left + durationLeft) - (right + durationRight);
                return !(distanceEnd <= 0 || distanceEnd > threshold);
            }
            else if (form == 3) {
                long distanceStart = right - left;
                if (distanceStart < minThreshold || distanceStart > maxThreshold) {
                    return false;
                }
                long distanceEnd = (left + durationLeft) - (right + durationRight);
                return !(distanceEnd < minThreshold || distanceEnd > maxThreshold);
            }
            else if (form == 4) {
                long distanceStart = right - left;
                if (distanceStart < minStartThreshold || distanceStart > maxStartThreshold) {
                    return false;
                }
                long distanceEnd = (left + durationLeft) - (right + durationRight);
                return !(distanceEnd < minEndThreshold || distanceEnd > maxEndThreshold);
            }
            throw new IllegalStateException("Invalid form: " + form);
        }
    }

    private class MeetsValidator implements Validator {
        private Long threshold;

        private MeetsValidator() {
        }

        private MeetsValidator(Long threshold) {
            this.threshold = threshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {

            if (threshold == null) {
                return right == (left + durationLeft);
            }
            else {
                long delta = Math.abs(right - (left + durationLeft));
                return delta <= threshold;
            }
        }
    }

    private class MetByValidator implements Validator {
        private Long threshold;

        private MetByValidator() {
        }

        private MetByValidator(Long threshold) {
            this.threshold = threshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {

            if (threshold == null) {
                return left == (right + durationRight);
            }
            else {
                long delta = Math.abs(left - (right + durationRight));
                return delta <= threshold;
            }
        }
    }

    private class OverlapsValidator implements Validator {
        private int form;
        private Long threshold;
        private Long minThreshold;
        private Long maxThreshold;

        private OverlapsValidator() {
            form = 1;
        }

        private OverlapsValidator(Long threshold) {
            form = 2;
            this.threshold = threshold;
        }

        private OverlapsValidator(Long minThreshold, Long maxThreshold) {
            form = 3;
            this.minThreshold = minThreshold;
            this.maxThreshold = maxThreshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {

            boolean match = (left < right) &&
                       (right < (left + durationLeft)) &&
                       ((left + durationLeft) < (right + durationRight));

            if (form == 1) {
                return match;
            }
            else if (form == 2) {
                if (!match) {
                    return false;
                }
                long delta = (left + durationLeft) - right;
                return 0 <= delta && delta <= threshold;
            }
            else if (form == 3) {
                if (!match) {
                    return false;
                }
                long delta = (left + durationLeft) - right;
                return minThreshold <= delta && delta <= maxThreshold;
            }
            throw new IllegalArgumentException("Invalid form " + form);
        }
    }

    private class OverlappedByValidator implements Validator {
        private int form;
        private Long threshold;
        private Long minThreshold;
        private Long maxThreshold;

        private OverlappedByValidator() {
            form = 1;
        }

        private OverlappedByValidator(Long threshold) {
            form = 2;
            this.threshold = threshold;
        }

        private OverlappedByValidator(Long minThreshold, Long maxThreshold) {
            form = 3;
            this.minThreshold = minThreshold;
            this.maxThreshold = maxThreshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {

            boolean match = (right < left) &&
                            (left < (right + durationRight)) &&
                            ((right + durationRight) < (left + durationLeft));

            if (form == 1) {
                return match;
            }
            else if (form == 2) {
                if (!match) {
                    return false;
                }
                long delta = (right + durationRight) - left;
                return 0 <= delta && delta <= threshold;
            }
            else if (form == 3) {
                if (!match) {
                    return false;
                }
                long delta = (right + durationRight) - left;
                return minThreshold <= delta && delta <= maxThreshold;
            }
            throw new IllegalArgumentException("Invalid form " + form);
        }
    }

    private class StartsValidator implements Validator {
        private Long threshold;

        private StartsValidator() {
        }

        private StartsValidator(Long threshold) {
            this.threshold = threshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {
            if (threshold == null) {
                return (left == right) && ((left + durationLeft) < (right + durationRight));
            }
            else {
                long delta = Math.abs(left - right);
                return (delta <= threshold) && ((left + durationLeft) < (right + durationRight));
            }
        }
    }

    private class StartedByValidator implements Validator {
        private Long threshold;

        private StartedByValidator() {
        }

        private StartedByValidator(Long threshold) {
            this.threshold = threshold;
        }

        public boolean validate(long left, long durationLeft, long right, long durationRight) {
            if (threshold == null) {
                return (left == right) && ((left + durationLeft) > (right + durationRight));
            }
            else {
                long delta = Math.abs(left - right);
                return (delta <= threshold) && ((left + durationLeft) > (right + durationRight));
            }
        }
    }
}
