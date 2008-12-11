package com.espertech.esper.regression.db;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.epl.SupportDatabaseService;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.EventBean;

import java.util.Properties;
import java.math.BigDecimal;

public class TestDatabaseOuterJoin extends TestCase
{
    private final static String ALL_FIELDS = "mybigint, myint, myvarchar, mychar, mybool, mynumeric, mydecimal, mydouble, myreal";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addDatabaseReference("MyDB", configDB);

        epService = EPServiceProviderManager.getProvider("TestDatabaseJoinRetained", configuration);
        epService.initialize();
    }

    public void testOuterJoinLeftS0()
    {
        String stmtText = "select s0.intPrimitive as MyInt, " + TestDatabaseOuterJoin.ALL_FIELDS + " from " +
                SupportBean.class.getName() + " as s0 left outer join " +
                " sql:MyDB ['select " + TestDatabaseOuterJoin.ALL_FIELDS + " from mytesttable where ${s0.intPrimitive} = mytesttable.mybigint'] as s1 on intPrimitive = mybigint";
        tryOuterJoinResult(stmtText);
    }

    public void testOuterJoinRightS1()
    {
        String stmtText = "select s0.intPrimitive as MyInt, " + TestDatabaseOuterJoin.ALL_FIELDS + " from " +
                " sql:MyDB ['select " + TestDatabaseOuterJoin.ALL_FIELDS + " from mytesttable where ${s0.intPrimitive} = mytesttable.mybigint'] as s1 right outer join " +
                SupportBean.class.getName() + " as s0 on intPrimitive = mybigint";
        tryOuterJoinResult(stmtText);
    }

    public void testOuterJoinFullS0()
    {
        String stmtText = "select s0.intPrimitive as MyInt, " + TestDatabaseOuterJoin.ALL_FIELDS + " from " +
                " sql:MyDB ['select " + TestDatabaseOuterJoin.ALL_FIELDS + " from mytesttable where ${s0.intPrimitive} = mytesttable.mybigint'] as s1 full outer join " +
                SupportBean.class.getName() + " as s0 on intPrimitive = mybigint";
        tryOuterJoinResult(stmtText);
    }

    public void testOuterJoinFullS1()
    {
        String stmtText = "select s0.intPrimitive as MyInt, " + TestDatabaseOuterJoin.ALL_FIELDS + " from " +
                SupportBean.class.getName() + " as s0 full outer join " +
                " sql:MyDB ['select " + TestDatabaseOuterJoin.ALL_FIELDS + " from mytesttable where ${s0.intPrimitive} = mytesttable.mybigint'] as s1 on intPrimitive = mybigint";
        tryOuterJoinResult(stmtText);
    }

    public void testOuterJoinRightS0()
    {
        String stmtText = "select s0.intPrimitive as MyInt, " + TestDatabaseOuterJoin.ALL_FIELDS + " from " +
                SupportBean.class.getName() + " as s0 right outer join " +
                " sql:MyDB ['select " + TestDatabaseOuterJoin.ALL_FIELDS + " from mytesttable where ${s0.intPrimitive} = mytesttable.mybigint'] as s1 on intPrimitive = mybigint";
        tryOuterJoinNoResult(stmtText);
    }

    public void testOuterJoinLeftS1()
    {
        String stmtText = "select s0.intPrimitive as MyInt, " + TestDatabaseOuterJoin.ALL_FIELDS + " from " +
                " sql:MyDB ['select " + TestDatabaseOuterJoin.ALL_FIELDS + " from mytesttable where ${s0.intPrimitive} = mytesttable.mybigint'] as s1 left outer join " +
                SupportBean.class.getName() + " as s0 on intPrimitive = mybigint";
        tryOuterJoinNoResult(stmtText);
    }

    public void testLeftOuterJoinOnFilter()
    {
        String[] fields = "MyInt,myint".split(",");
        String stmtText = "select s0.intPrimitive as MyInt, " + TestDatabaseOuterJoin.ALL_FIELDS + " from " +
                SupportBean.class.getName() + " as s0 " +
                " left outer join " +
                " sql:MyDB ['select " + TestDatabaseOuterJoin.ALL_FIELDS + " from mytesttable where ${s0.intPrimitive} = mytesttable.mybigint'] as s1 " +
                "on string = myvarchar";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields, null);

        // Result as the SQL query returns 1 row and therefore the on-clause filters it out, but because of left out still getting a row
        sendEvent(1, "xxx");
        EventBean received = listener.assertOneGetNewAndReset();
        assertEquals(1, received.get("MyInt"));
        assertReceived(received, null, null, null, null, null, null, null, null, null);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{1, null}});

        // Result as the SQL query returns 0 rows
        sendEvent(-1, "xxx");
        received = listener.assertOneGetNewAndReset();
        assertEquals(-1, received.get("MyInt"));
        assertReceived(received, null, null, null, null, null, null, null, null, null);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{-1, null}});

        sendEvent(2, "B");
        received = listener.assertOneGetNewAndReset();
        assertEquals(2, received.get("MyInt"));
        assertReceived(received, 2l, 20, "B", "Y", false, new BigDecimal(100), new BigDecimal(200), 2.2d, 2.3d);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{2, 20}});
    }

    public void testRightOuterJoinOnFilter()
    {
        String[] fields = "MyInt,myint".split(",");
        String stmtText = "select s0.intPrimitive as MyInt, " + TestDatabaseOuterJoin.ALL_FIELDS + " from " +
                " sql:MyDB ['select " + TestDatabaseOuterJoin.ALL_FIELDS + " from mytesttable where ${s0.intPrimitive} = mytesttable.mybigint'] as s1 right outer join " +
                SupportBean.class.getName() + " as s0 on string = myvarchar";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields, null);

        // No result as the SQL query returns 1 row and therefore the on-clause filters it out
        sendEvent(1, "xxx");
        EventBean received = listener.assertOneGetNewAndReset();
        assertEquals(1, received.get("MyInt"));
        assertReceived(received, null, null, null, null, null, null, null, null, null);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{1, null}});

        // Result as the SQL query returns 0 rows
        sendEvent(-1, "xxx");
        received = listener.assertOneGetNewAndReset();
        assertEquals(-1, received.get("MyInt"));
        assertReceived(received, null, null, null, null, null, null, null, null, null);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{-1, null}});

        sendEvent(2, "B");
        received = listener.assertOneGetNewAndReset();
        assertEquals(2, received.get("MyInt"));
        assertReceived(received, 2l, 20, "B", "Y", false, new BigDecimal(100), new BigDecimal(200), 2.2d, 2.3d);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{2, 20}});
    }

    public void testOuterJoinReversedOnFilter()
    {
        String[] fields = "MyInt,MyVarChar".split(",");
        String stmtText = "select s0.intPrimitive as MyInt, MyVarChar from " +
                SupportBean.class.getName() + ".win:keepall() as s0 " +
                " right outer join " +
                " sql:MyDB ['select myvarchar MyVarChar from mytesttable'] as s1 " +
                "on string = MyVarChar";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields, null);

        // No result as the SQL query returns 1 row and therefore the on-clause filters it out
        sendEvent(1, "xxx");
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields, null);

        sendEvent(-1, "A");
        EventBean received = listener.assertOneGetNewAndReset();
        assertEquals(-1, received.get("MyInt"));
        assertEquals("A", received.get("MyVarChar"));
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{-1, "A"}});
    }

    public void tryOuterJoinNoResult(String statementText)
    {
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        sendEvent(2);
        EventBean received = listener.assertOneGetNewAndReset();
        assertEquals(2, received.get("MyInt"));
        assertReceived(received, 2l, 20, "B", "Y", false, new BigDecimal(100), new BigDecimal(200), 2.2d, 2.3d);

        sendEvent(11);
        assertFalse(listener.isInvoked());
    }

    public void tryOuterJoinResult(String statementText)
    {
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        sendEvent(1);
        EventBean received = listener.assertOneGetNewAndReset();
        assertEquals(1, received.get("MyInt"));
        assertReceived(received, 1l, 10, "A", "Z", true, new BigDecimal(5000), new BigDecimal(100), 1.2d, 1.3d);

        sendEvent(11);
        received = listener.assertOneGetNewAndReset();
        assertEquals(11, received.get("MyInt"));
        assertReceived(received, null, null, null, null, null, null, null, null, null);
    }

    private void assertReceived(EventBean event, Long mybigint, Integer myint, String myvarchar, String mychar, Boolean mybool, BigDecimal mynumeric, BigDecimal mydecimal, Double mydouble, Double myreal)
    {
        assertEquals(mybigint, event.get("mybigint"));
        assertEquals(myint, event.get("myint"));
        assertEquals(myvarchar, event.get("myvarchar"));
        assertEquals(mychar, event.get("mychar"));
        assertEquals(mybool, event.get("mybool"));
        assertEquals(mynumeric, event.get("mynumeric"));
        assertEquals(mydecimal, event.get("mydecimal"));
        assertEquals(mydouble, event.get("mydouble"));
        Object r = event.get("myreal");
        assertEquals(myreal, event.get("myreal"));
    }

    private void sendEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEvent(int intPrimitive, String string)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setString(string);
        epService.getEPRuntime().sendEvent(bean);
    }
}
