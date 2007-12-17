package net.esper.regression.db;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.eql.SupportDatabaseService;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

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
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

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

    public void testOuterJoinOnFilter()
    {
        String stmtText = "select s0.intPrimitive as MyInt, " + TestDatabaseOuterJoin.ALL_FIELDS + " from " +
                " sql:MyDB ['select " + TestDatabaseOuterJoin.ALL_FIELDS + " from mytesttable where ${s0.intPrimitive} = mytesttable.mybigint'] as s1 right outer join " +
                SupportBean.class.getName() + " as s0 on string = myvarchar";

        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        // No result as the SQL query returns 1 row and therefore the on-clause filters it out
        sendEvent(1, "xxx");
        assertFalse(listener.isInvoked());

        // Result as the SQL query returns 0 rows
        sendEvent(-1, "xxx");
        EventBean received = listener.assertOneGetNewAndReset();
        assertEquals(-1, received.get("MyInt"));
        assertReceived(received, null, null, null, null, null, null, null, null, null);

        sendEvent(2, "B");
        received = listener.assertOneGetNewAndReset();
        assertEquals(2, received.get("MyInt"));
        assertReceived(received, 2l, 20, "B", "Y", false, new BigDecimal(100), new BigDecimal(200), 2.2d, 2.3d);
    }

    public void tryOuterJoinNoResult(String statementText)
    {
        EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
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
        EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
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
