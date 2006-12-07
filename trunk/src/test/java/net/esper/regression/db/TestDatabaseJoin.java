package net.esper.regression.db;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.eql.SupportDatabaseService;
import net.esper.event.EventBean;
import net.esper.event.EventType;

import java.util.Properties;
import java.sql.*;
import java.math.BigDecimal;

public class TestDatabaseJoin extends TestCase
{
    private final static String ALL_FIELDS = "mybigint, myint, myvarchar, mychar, mybool, mynumeric, mydecimal, mydouble, myreal";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);
        configDB.setConnectionCatalog("test");
        configDB.setConnectionReadOnly(true);
        configDB.setConnectionTransactionIsolation(1);
        configDB.setConnectionAutoCommit(true);

        Configuration configuration = new Configuration();
        configuration.addDatabaseReference("MyDB", configDB);

        epService = EPServiceProviderManager.getProvider("TestDatabaseJoinRetained", configuration);
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testTimeBatch()
    {
        String stmtText = "select " + ALL_FIELDS + " from " +
                " sql:MyDB ['select " + ALL_FIELDS + " from mytesttable where ${intPrimitive} = mytesttable.mybigint'] as s0," +
                SupportBean.class.getName() + ".win:time_batch(10 sec) as s1";

        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        sendSupportBeanEvent(10);
        sendSupportBeanEvent(5);
        sendSupportBeanEvent(2);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(10000));
        EventBean[] received = listener.getLastNewData();
        assertEquals(3, received.length);
        assertEquals(100, received[0].get("myint"));
        assertEquals(50, received[1].get("myint"));
        assertEquals(20, received[2].get("myint"));
    }

    public void testInvalidSQL()
    {
        String stmtText = "select myvarchar from " +
                " sql:MyDB ['select mychar,, from mytesttable where '] as s0," +
                SupportBeanComplexProps.class.getName() + " as s1";

        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Error in statement 'select mychar,, from mytesttable where ', failed to obtain result metadata, please check the statement, reason: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ' from mytesttable where' at line 1 [select myvarchar from  sql:MyDB ['select mychar,, from mytesttable where '] as s0,net.esper.support.bean.SupportBeanComplexProps as s1]", ex.getMessage());
        }
    }

    public void testInvalidBothHistorical()
    {
        String sqlOne = "sql:MyDB ['select myvarchar from mytesttable where ${mychar} = mytesttable.mybigint']";
        String sqlTwo = "sql:MyDB ['select mychar from mytesttable where ${myvarchar} = mytesttable.mybigint']";
        String stmtText = "select s0.myvarchar as s0Name, s1.mychar as s1Name from " +
                sqlOne + " as s0, " + sqlTwo + "  as s1";

        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Joins between historical data streams are not supported [select s0.myvarchar as s0Name, s1.mychar as s1Name from sql:MyDB ['select myvarchar from mytesttable where ${mychar} = mytesttable.mybigint'] as s0, sql:MyDB ['select mychar from mytesttable where ${myvarchar} = mytesttable.mybigint']  as s1]", ex.getMessage());
        }
    }

    public void testInvalid3Streams()
    {
        String sql = "sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.mybigint']";
        String stmtText = "select s0.myvarchar as s0Name from " +
                sql + " as s0, " + SupportBean.class.getName() + " as s1," + SupportBean_S0.class.getName() + " as s2";

        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Joins between historical data require a only one event stream in the join [select s0.myvarchar as s0Name from sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.mybigint'] as s0, net.esper.support.bean.SupportBean as s1,net.esper.support.bean.SupportBean_S0 as s2]", ex.getMessage());
        }
    }

    public void testInvalidPropertyEvent()
    {
        String stmtText = "select myvarchar from " +
                " sql:MyDB ['select mychar from mytesttable where ${s1.xxx[0]} = mytesttable.mybigint'] as s0," +
                SupportBeanComplexProps.class.getName() + " as s1";

        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Property 's1.xxx[0]' failed to resolve, reason: Property named 'xxx[0]' is not valid in stream s1 [select myvarchar from  sql:MyDB ['select mychar from mytesttable where ${s1.xxx[0]} = mytesttable.mybigint'] as s0,net.esper.support.bean.SupportBeanComplexProps as s1]", ex.getMessage());
        }
    }

    public void testInvalidPropertyHistorical()
    {
        String stmtText = "select myvarchar from " +
                " sql:MyDB ['select myvarchar from mytesttable where ${myvarchar} = mytesttable.mybigint'] as s0," +
                SupportBeanComplexProps.class.getName() + " as s1";

        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Invalid property 'myvarchar' resolves to the historical data itself [select myvarchar from  sql:MyDB ['select myvarchar from mytesttable where ${myvarchar} = mytesttable.mybigint'] as s0,net.esper.support.bean.SupportBeanComplexProps as s1]", ex.getMessage());
        }
    }

    public void testInvalid1Stream()
    {
        String sql = "sql:MyDB ['select myvarchar, mybigint from mytesttable where ${mybigint} = myint']";
        String stmtText = "select myvarchar as s0Name from " + sql + " as s0";

        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Invalid property 'mybigint' resolves to the historical data itself [select myvarchar as s0Name from sql:MyDB ['select myvarchar, mybigint from mytesttable where ${mybigint} = myint'] as s0]", ex.getMessage());
        }
    }

    public void testInvalidSubviews()
    {
        String sql = "sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.myint'].win:time(30 sec)";
        String stmtText = "select myvarchar as s0Name from " +
                sql + " as s0, " + SupportBean.class.getName() + " as s1";

        try
        {
            epService.getEPAdministrator().createEQL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Historical data joins do not allow views onto the data, view 'win:time' is not valid in this context [select myvarchar as s0Name from sql:MyDB ['select myvarchar from mytesttable where ${intPrimitive} = mytesttable.myint'].win:time(30 sec) as s0, net.esper.support.bean.SupportBean as s1]", ex.getMessage());
        }
    }

    public void testStreamNamesAndRename()
    {
        String stmtText = "select s1.a as mybigint, " +
                " s1.b as myint," +
                " s1.c as myvarchar," +
                " s1.d as mychar," +
                " s1.e as mybool," +
                " s1.f as mynumeric," +
                " s1.g as mydecimal," +
                " s1.h as mydouble," +
                " s1.i as myreal " +
                " from " + SupportBean_S0.class.getName() + " as s0," +
                " sql:MyDB ['select mybigint as a, " +
                " myint as b," +
                " myvarchar as c," +
                " mychar as d," +
                " mybool as e," +
                " mynumeric as f," +
                " mydecimal as g," +
                " mydouble as h," +
                " myreal as i " +
                "from mytesttable where ${id} = mytesttable.mybigint'] as s1";

        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        sendEventS0(1);
        assertReceived(1, 10, "A", "Z", true, new BigDecimal(5000), new BigDecimal(100), 1.2, 1.3);
    }

    public void testWithPattern()
    {
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        String stmtText = "select mychar from " +
                " sql:MyDB ['select mychar from mytesttable where mytesttable.mybigint = 2'] as s0," +
                " pattern [every timer:interval(5 sec) ]";

        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(5000));
        assertEquals("Y", listener.assertOneGetNewAndReset().get("mychar"));

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(9999));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(10000));
        assertEquals("Y", listener.assertOneGetNewAndReset().get("mychar"));
    }

    public void testPropertyResolution()
    {
        String stmtText = "select " + ALL_FIELDS + " from " +
                " sql:MyDB ['select " + ALL_FIELDS + " from mytesttable where ${s1.arrayProperty[0]} = mytesttable.mybigint'] as s0," +
                SupportBeanComplexProps.class.getName() + " as s1";
        // s1.arrayProperty[0] returns 10 for that bean

        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
        assertReceived(10, 100, "J", "P", true, null, new BigDecimal(1000), 10.2, 10.3);
    }

    public void testSimpleJoinLeft()
    {
        String stmtText = "select " + ALL_FIELDS + " from " +
                SupportBean_S0.class.getName() + " as s0," +
                " sql:MyDB ['select " + ALL_FIELDS + " from mytesttable where ${id} = mytesttable.mybigint'] as s1";

        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        sendEventS0(1);
        assertReceived(1, 10, "A", "Z", true, new BigDecimal(5000), new BigDecimal(100), 1.2, 1.3);
    }

    public void testRestartStatement()
    {
        String stmtText = "select mychar from " +
                SupportBean_S0.class.getName() + " as s0," +
                " sql:MyDB ['select mychar from mytesttable where ${id} = mytesttable.mybigint'] as s1";

        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        // Too many connections unless the stop actually relieves them
        for (int i = 0; i < 100; i++)
        {
            statement.stop();

            sendEventS0(1);
            assertFalse(listener.isInvoked());

            statement.start();
            sendEventS0(1);
            assertEquals("Z", listener.assertOneGetNewAndReset().get("mychar"));
        }
    }

    public void testSimpleJoinRight()
    {
        String stmtText = "select " + ALL_FIELDS + " from " +
                " sql:MyDB ['select " + ALL_FIELDS + " from mytesttable where ${id} = mytesttable.mybigint'] as s0," +
                SupportBean_S0.class.getName() + " as s1";

        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        EventType eventType = statement.getEventType();
        assertEquals(Long.class, eventType.getPropertyType("mybigint"));
        assertEquals(Integer.class, eventType.getPropertyType("myint"));
        assertEquals(String.class, eventType.getPropertyType("myvarchar"));
        assertEquals(String.class, eventType.getPropertyType("mychar"));
        assertEquals(Boolean.class, eventType.getPropertyType("mybool"));
        assertEquals(BigDecimal.class, eventType.getPropertyType("mynumeric"));
        assertEquals(BigDecimal.class, eventType.getPropertyType("mydecimal"));
        assertEquals(Double.class, eventType.getPropertyType("mydouble"));
        assertEquals(Double.class, eventType.getPropertyType("myreal"));

        sendEventS0(1);
        assertReceived(1, 10, "A", "Z", true, new BigDecimal(5000), new BigDecimal(100), 1.2, 1.3);
    }

    private void assertReceived(long mybigint, int myint, String myvarchar, String mychar, boolean mybool, BigDecimal mynumeric, BigDecimal mydecimal, Double mydouble, Double myreal)
    {
        EventBean event = listener.assertOneGetNewAndReset();
        assertReceived(event, mybigint, myint, myvarchar, mychar, mybool, mynumeric, mydecimal, mydouble, myreal);
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

    public void testMySQLDatabaseConnection() throws Exception
    {
        Class.forName(SupportDatabaseService.DRIVER).newInstance();
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(SupportDatabaseService.FULLURL);
        }
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw ex;
        }
        Statement stmt = conn.createStatement( );
        ResultSet rs = stmt.executeQuery( "SELECT * FROM tomtest");
        rs.close();
        stmt.close();
        conn.close();

        /**
         * Using JNDI to get a connectiong (for J2EE containers or outside)
         */
        /**
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MySQLDB");
            Connection connection = ds.getConnection();
        */
    }

    private void sendEventS0(int id)
    {
        SupportBean_S0 bean = new SupportBean_S0(id);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendSupportBeanEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }
}
