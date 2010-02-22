package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.epl.SupportStaticMethodLib;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.core.EPStatementSPI;

public class TestFromClauseMethod extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType(SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void test2JoinHistoricalSubordinateOuterMultiField()
    {
        String className = SupportStaticMethodLib.class.getName();
        String stmtText;

        // fetchBetween must execute first, fetchIdDelimited is dependent on the result of fetchBetween
        stmtText = "select intPrimitive,intBoxed,col1,col2 from SupportBean.win:keepall() " +
                   "left outer join " +
                   "method:" + className + ".fetchResult100() " +
                   "on intPrimitive = col1 and intBoxed = col2";

        String[] fields = "intPrimitive,intBoxed,col1,col2".split(",");
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);
        stmt.addListener(listener);

        sendSupportBeanEvent(2, 4);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{2,4,2,4}});
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{2,4,2,4}});

        stmt.destroy();
    }

    public void test2JoinHistoricalSubordinateOuter()
    {
        String className = SupportStaticMethodLib.class.getName();
        String stmtText;

        // fetchBetween must execute first, fetchIdDelimited is dependent on the result of fetchBetween
        stmtText = "select s0.value as valueOne, s1.value as valueTwo from method:" + className + ".fetchResult12(0) as s0 " +
                   "left outer join " +
                   "method:" + className + ".fetchResult23(s0.value) as s1 on s0.value = s1.value";
        assertJoinHistoricalSubordinateOuter(stmtText);

        stmtText = "select s0.value as valueOne, s1.value as valueTwo from " +
                    "method:" + className + ".fetchResult23(s0.value) as s1 " +
                    "right outer join " +
                    "method:" + className + ".fetchResult12(0) as s0 on s0.value = s1.value";
        assertJoinHistoricalSubordinateOuter(stmtText);

        stmtText = "select s0.value as valueOne, s1.value as valueTwo from " +
                    "method:" + className + ".fetchResult23(s0.value) as s1 " +
                    "full outer join " +
                    "method:" + className + ".fetchResult12(0) as s0 on s0.value = s1.value";
        assertJoinHistoricalSubordinateOuter(stmtText);

        stmtText = "select s0.value as valueOne, s1.value as valueTwo from " +
                    "method:" + className + ".fetchResult12(0) as s0 " +
                    "full outer join " +
                    "method:" + className + ".fetchResult23(s0.value) as s1 on s0.value = s1.value";
        assertJoinHistoricalSubordinateOuter(stmtText);
    }

    public void test2JoinHistoricalIndependentOuter()
    {
        String[] fields = "valueOne,valueTwo".split(",");
        String className = SupportStaticMethodLib.class.getName();
        String stmtText;

        // fetchBetween must execute first, fetchIdDelimited is dependent on the result of fetchBetween
        stmtText = "select s0.value as valueOne, s1.value as valueTwo from method:" + className + ".fetchResult12(0) as s0 " +
                   "left outer join " +
                   "method:" + className + ".fetchResult23(0) as s1 on s0.value = s1.value";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, null}, {2, 2}});
        stmt.destroy();

        stmtText = "select s0.value as valueOne, s1.value as valueTwo from " +
                    "method:" + className + ".fetchResult23(0) as s1 " +
                    "right outer join " +
                    "method:" + className + ".fetchResult12(0) as s0 on s0.value = s1.value";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, null}, {2, 2}});
        stmt.destroy();

        stmtText = "select s0.value as valueOne, s1.value as valueTwo from " +
                    "method:" + className + ".fetchResult23(0) as s1 " +
                    "full outer join " +
                    "method:" + className + ".fetchResult12(0) as s0 on s0.value = s1.value";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, null}, {2, 2}, {null, 3}});
        stmt.destroy();

        stmtText = "select s0.value as valueOne, s1.value as valueTwo from " +
                    "method:" + className + ".fetchResult12(0) as s0 " +
                    "full outer join " +
                    "method:" + className + ".fetchResult23(0) as s1 on s0.value = s1.value";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, null}, {2, 2}, {null, 3}});
        stmt.destroy();
    }

    private void assertJoinHistoricalSubordinateOuter(String expression)
    {
        String[] fields = "valueOne,valueTwo".split(",");
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, null}, {2, 2}});
        stmt.destroy();
    }

    public void test2JoinHistoricalOnlyDependent()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().createEPL("create variable int lower");
        epService.getEPAdministrator().createEPL("create variable int upper");
        EPStatement setStmt = epService.getEPAdministrator().createEPL("on SupportBean set lower=intPrimitive,upper=intBoxed");
        assertEquals(StatementType.ON_SET, ((EPStatementSPI) setStmt).getStatementMetadata().getStatementType());

        String className = SupportStaticMethodLib.class.getName();
        String stmtText;

        // fetchBetween must execute first, fetchIdDelimited is dependent on the result of fetchBetween
        stmtText = "select value,result from method:" + className + ".fetchBetween(lower, upper), " +
                                        "method:" + className + ".fetchIdDelimited(value)";
        assertJoinHistoricalOnlyDependent(stmtText);

        stmtText = "select value,result from " +
                                        "method:" + className + ".fetchIdDelimited(value), " +
                                        "method:" + className + ".fetchBetween(lower, upper)";
        assertJoinHistoricalOnlyDependent(stmtText);
    }

    public void test2JoinHistoricalOnlyIndependent()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().createEPL("create variable int lower");
        epService.getEPAdministrator().createEPL("create variable int upper");
        epService.getEPAdministrator().createEPL("on SupportBean set lower=intPrimitive,upper=intBoxed");

        String className = SupportStaticMethodLib.class.getName();
        String stmtText;

        // fetchBetween must execute first, fetchIdDelimited is dependent on the result of fetchBetween
        stmtText = "select s0.value as valueOne, s1.value as valueTwo from method:" + className + ".fetchBetween(lower, upper) as s0, " +
                                        "method:" + className + ".fetchBetweenString(lower, upper) as s1";
        assertJoinHistoricalOnlyIndependent(stmtText);

        stmtText = "select s0.value as valueOne, s1.value as valueTwo from " +
                                        "method:" + className + ".fetchBetweenString(lower, upper) as s1, " +
                                        "method:" + className + ".fetchBetween(lower, upper) as s0 ";
        assertJoinHistoricalOnlyIndependent(stmtText);
    }

    private void assertJoinHistoricalOnlyIndependent(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "valueOne,valueTwo".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendSupportBeanEvent(5, 5);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{5, "5"}});

        sendSupportBeanEvent(1, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, "1"}, {1, "2"}, {2, "1"}, {2, "2"}});

        sendSupportBeanEvent(0, -1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        stmt.destroy();
        sendSupportBeanEvent(0, -1);
        assertFalse(listener.isInvoked());
    }

    private void assertJoinHistoricalOnlyDependent(String expression)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        String[] fields = "value,result".split(",");
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendSupportBeanEvent(5, 5);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{5, "|5|"}});

        sendSupportBeanEvent(1, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1, "|1|"}, {2, "|2|"}});

        sendSupportBeanEvent(0, -1);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendSupportBeanEvent(4, 6);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{4, "|4|"}, {5, "|5|"}, {6, "|6|"}});

        stmt.destroy();
        sendSupportBeanEvent(0, -1);
        assertFalse(listener.isInvoked());
    }

    public void testNoJoinIterateVariables()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().createEPL("create variable int lower");
        epService.getEPAdministrator().createEPL("create variable int upper");
        epService.getEPAdministrator().createEPL("on SupportBean set lower=intPrimitive,upper=intBoxed");

        // Test int and singlerow
        String className = SupportStaticMethodLib.class.getName();
        String stmtText = "select value from method:" + className + ".fetchBetween(lower, upper)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), new String[] {"value"}, null);

        sendSupportBeanEvent(5, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), new String[] {"value"}, new Object[][] {{5}, {6}, {7}, {8}, {9}, {10}});

        sendSupportBeanEvent(10, 5);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), new String[] {"value"}, null);

        sendSupportBeanEvent(4, 4);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), new String[] {"value"}, new Object[][] {{4}});

        stmt.destroy();
        assertFalse(listener.isInvoked());
    }
    
    public void testMapReturnTypeMultipleRow()
    {
        String[] fields = "string,intPrimitive,mapstring,mapint".split(",");
        String joinStatement = "select string, intPrimitive, mapstring, mapint from " +
                SupportBean.class.getName() + ".win:keepall() as s1, " +
                "method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchMapArray(string, intPrimitive)";

        EPStatement stmt = epService.getEPAdministrator().createEPL(joinStatement);
        stmt.addListener(listener);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendBeanEvent("E1", 0);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendBeanEvent("E2", -1);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendBeanEvent("E3", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 1, "|E3_0|", 100});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3", 1, "|E3_0|", 100}});

        sendBeanEvent("E4", 2);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields,
                new Object[][] {{"E4", 2, "|E4_0|", 100}, {"E4", 2, "|E4_1|", 101}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3", 1, "|E3_0|", 100}, {"E4", 2, "|E4_0|", 100}, {"E4", 2, "|E4_1|", 101}});

        sendBeanEvent("E5", 3);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields,
                new Object[][] {{"E5", 3, "|E5_0|", 100}, {"E5", 3, "|E5_1|", 101}, {"E5", 3, "|E5_2|", 102}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3", 1, "|E3_0|", 100}, 
                {"E4", 2, "|E4_0|", 100}, {"E4", 2, "|E4_1|", 101},
                {"E5", 3, "|E5_0|", 100}, {"E5", 3, "|E5_1|", 101}, {"E5", 3, "|E5_2|", 102}});

        stmt.destroy();
    }

    public void testMapReturnTypeSingleRow()
    {
        String joinStatement = "select string, intPrimitive, mapstring, mapint from " +
                SupportBean.class.getName() + ".win:keepall() as s1, " +
                "method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchMap(string, intPrimitive)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(joinStatement);
        stmt.addListener(listener);
        String[] fields = new String[] {"string", "intPrimitive", "mapstring", "mapint"};

        sendBeanEvent("E1", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1, "|E1|", 2});

        sendBeanEvent("E2", 3);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 3, "|E2|", 4});

        sendBeanEvent("E3", 0);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 0, null, null});

        sendBeanEvent("E4", -1);
        assertFalse(listener.isInvoked());

        stmt.destroy();
    }

    public void testArrayNoArg()
    {
        String joinStatement = "select id, string from " +
                SupportBean.class.getName() + ".win:length(3) as s1, " +
                "method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchArrayNoArg";
        EPStatement stmt = epService.getEPAdministrator().createEPL(joinStatement);
        tryArrayNoArg(stmt);

        joinStatement = "select id, string from " +
                SupportBean.class.getName() + ".win:length(3) as s1, " +
                "method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchArrayNoArg()";
        stmt = epService.getEPAdministrator().createEPL(joinStatement);
        tryArrayNoArg(stmt);

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(joinStatement);
        assertEquals(joinStatement, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        tryArrayNoArg(stmt);

        model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create("id", "string"));
        model.setFromClause(FromClause.create()
            .add(FilterStream.create(SupportBean.class.getName(), "s1").addView("win", "length", Expressions.constant(3)))
            .add(MethodInvocationStream.create(SupportStaticMethodLib.class.getName(), "fetchArrayNoArg")));
        stmt = epService.getEPAdministrator().create(model);
        assertEquals(joinStatement, model.toEPL());

        tryArrayNoArg(stmt);
    }

    private void tryArrayNoArg(EPStatement stmt)
    {
        stmt.addListener(listener);
        String[] fields = new String[] {"id", "string"};

        sendBeanEvent("E1");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"1", "E1"});

        sendBeanEvent("E2");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"1", "E2"});

        stmt.destroy();
    }

    public void testArrayWithArg()
    {
        String joinStatement = "select irstream id, string from " +
                SupportBean.class.getName() + "().win:length(3) as s1, " +
                " method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchArrayGen(intPrimitive)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(joinStatement);
        tryArrayWithArg(stmt);

        joinStatement = "select irstream id, string from " +
                "method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchArrayGen(intPrimitive) as s0, " +
                SupportBean.class.getName() + ".win:length(3)";
        stmt = epService.getEPAdministrator().createEPL(joinStatement);
        tryArrayWithArg(stmt);

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(joinStatement);
        assertEquals(joinStatement, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        tryArrayWithArg(stmt);

        model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create("id", "string").setStreamSelector(StreamSelector.RSTREAM_ISTREAM_BOTH));
        model.setFromClause(FromClause.create()
            .add(MethodInvocationStream.create(SupportStaticMethodLib.class.getName(), "fetchArrayGen", "s0")
                .addParameter(Expressions.property("intPrimitive")))
                .add(FilterStream.create(SupportBean.class.getName()).addView("win", "length", Expressions.constant(3)))
            );
        stmt = epService.getEPAdministrator().create(model);
        assertEquals(joinStatement, model.toEPL());

        tryArrayWithArg(stmt);
    }

    private void tryArrayWithArg(EPStatement stmt)
    {
        stmt.addListener(listener);
        String[] fields = new String[] {"id", "string"};

        sendBeanEvent("E1", -1);
        assertFalse(listener.isInvoked());

        sendBeanEvent("E2", 0);
        assertFalse(listener.isInvoked());

        sendBeanEvent("E3", 1);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A", "E3"});

        sendBeanEvent("E4", 2);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"A", "E4"}, {"B", "E4"}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendBeanEvent("E5", 3);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"A", "E5"}, {"B", "E5"}, {"C", "E5"}});
        assertNull(listener.getLastOldData());
        listener.reset();

        sendBeanEvent("E6", 1);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"A", "E6"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), fields, new Object[][] {{"A", "E3"}});
        listener.reset();

        sendBeanEvent("E7", 1);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"A", "E7"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), fields, new Object[][] {{"A", "E4"}, {"B", "E4"}});
        listener.reset();

        stmt.destroy();
    }

    public void testObjectNoArg()
    {
        String joinStatement = "select id, string from " +
                SupportBean.class.getName() + "().win:length(3) as s1, " +
                " method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchObjectNoArg()";

        EPStatement stmt = epService.getEPAdministrator().createEPL(joinStatement);
        stmt.addListener(listener);
        String[] fields = new String[] {"id", "string"};

        sendBeanEvent("E1");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"2", "E1"});

        sendBeanEvent("E2");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"2", "E2"});
    }

    public void testObjectWithArg()
    {
        String joinStatement = "select id, string from " +
                SupportBean.class.getName() + "().win:length(3) as s1, " +
                " method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchObject(string)";

        EPStatement stmt = epService.getEPAdministrator().createEPL(joinStatement);
        stmt.addListener(listener);
        String[] fields = new String[] {"id", "string"};

        sendBeanEvent("E1");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"|E1|", "E1"});

        sendBeanEvent(null);
        assertFalse(listener.isInvoked());

        sendBeanEvent("E2");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"|E2|", "E2"});
    }

    public void testInvocationTargetEx()
    {
        String joinStatement = "select s1.string from " +
                SupportBean.class.getName() + "().win:length(3) as s1, " +
                " method:com.espertech.esper.support.epl.SupportStaticMethodLib.throwExceptionBeanReturn()";

        epService.getEPAdministrator().createEPL(joinStatement);

        try
        {
            sendBeanEvent("E1");
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("com.espertech.esper.client.EPException: Method 'throwExceptionBeanReturn' of class 'com.espertech.esper.support.epl.SupportStaticMethodLib' reported an exception: java.lang.Exception: throwException text here", ex.getMessage());
        }
    }

    public void testInvalid()
    {
        tryInvalid("select * from SupportBean, method:.abc where 1=2",
                   "Incorrect syntax near '.' at line 1 column 34, please check the method invocation join within the from clause [select * from SupportBean, method:.abc where 1=2]");

        tryInvalid("select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchArrayGen()",
                   "Error starting statement: Method footprint does not match the number or type of expression parameters, expecting no parameters in method: Could not find static method named 'fetchArrayGen' in class 'com.espertech.esper.support.epl.SupportStaticMethodLib' taking no parameters (nearest match found was 'fetchArrayGen' taking type(s) 'int') [select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchArrayGen()]");

        tryInvalid("select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchObjectAndSleep(1)",
                   "Error starting statement: Method footprint does not match the number or type of expression parameters, expecting a method where parameters are typed 'Integer': Could not find static method named 'fetchObjectAndSleep' in class 'com.espertech.esper.support.epl.SupportStaticMethodLib' with matching parameter number and expected parameter type(s) 'Integer' (nearest match found was 'fetchObjectAndSleep' taking type(s) 'String, int, long') [select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchObjectAndSleep(1)]");

        tryInvalid("select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.sleep(100) where 1=2",
                   "Error starting statement: Invalid return type for static method 'sleep' of class 'com.espertech.esper.support.epl.SupportStaticMethodLib', expecting a Java class [select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.sleep(100) where 1=2]");

        tryInvalid("select * from SupportBean, method:AClass. where 1=2",
                   "Incorrect syntax near 'where' (a reserved keyword) expecting an identifier but found 'where' at line 1 column 42, please check the view specifications within the from clause [select * from SupportBean, method:AClass. where 1=2]");

        tryInvalid("select * from SupportBean, method:Dummy.abc where 1=2",
                   "Error starting statement: Could not load class by name 'Dummy', please check imports [select * from SupportBean, method:Dummy.abc where 1=2]");

        tryInvalid("select * from SupportBean, method:Math where 1=2",
                   "No method name specified for method-based join [select * from SupportBean, method:Math where 1=2]");

        tryInvalid("select * from SupportBean, method:Dummy.dummy().win:length(100) where 1=2",
                   "Error starting statement: Method data joins do not allow views onto the data, view 'win:length' is not valid in this context [select * from SupportBean, method:Dummy.dummy().win:length(100) where 1=2]");

        tryInvalid("select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.dummy where 1=2",
                   "Error starting statement: Could not find static method named 'dummy' in class 'com.espertech.esper.support.epl.SupportStaticMethodLib' [select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.dummy where 1=2]");

        tryInvalid("select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.minusOne() where 1=2",
                   "Error starting statement: Invalid return type for static method 'minusOne' of class 'com.espertech.esper.support.epl.SupportStaticMethodLib', expecting a Java class [select * from SupportBean, method:com.espertech.esper.support.epl.SupportStaticMethodLib.minusOne() where 1=2]");

        tryInvalid("select * from SupportBean, xyz:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchArrayNoArg() where 1=2",
                   "Expecting keyword 'method', found 'xyz' [select * from SupportBean, xyz:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchArrayNoArg() where 1=2]");

        tryInvalid("select * from method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s1.value, s1.value) as s0, method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s0.value, s0.value) as s1",
                   "Error starting statement: Circular dependency detected between historical streams [select * from method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s1.value, s1.value) as s0, method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s0.value, s0.value) as s1]");

        tryInvalid("select * from method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s0.value, s0.value) as s0, method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s0.value, s0.value) as s1",
                   "Error starting statement: Parameters for historical stream 0 indicate that the stream is subordinate to itself as stream parameters originate in the same stream [select * from method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s0.value, s0.value) as s0, method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s0.value, s0.value) as s1]");

        tryInvalid("select * from method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s0.value, s0.value) as s0",
                   "Error starting statement: Parameters for historical stream 0 indicate that the stream is subordinate to itself as stream parameters originate in the same stream [select * from method:com.espertech.esper.support.epl.SupportStaticMethodLib.fetchBetween(s0.value, s0.value) as s0]");
    }

    private void tryInvalid(String stmt, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(stmt);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    private void sendBeanEvent(String string)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendBeanEvent(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendSupportBeanEvent(int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }
}
