package net.esper.regression.db;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.bean.SupportBean_S1;
import net.esper.support.bean.SupportBean_S2;
import net.esper.support.bean.SupportBean_S3;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;

import javax.naming.*;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Hashtable;
import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestDatabaseJoin extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testSimplJoin()
    {
        String stmtText = "select id, name, address " +
                " from " +
                SupportBean_S0.class.getName() + " as s0," +
                " database MyDB schema test [[select name, address from customers where ?s0.id = customers.custid]]";

        System.out.println(stmtText);
        EPStatement statement = epService.getEPAdministrator().createEQL(stmtText);

        SupportUpdateListener updateListener = new SupportUpdateListener();
        statement.addListener(updateListener);
    }

    public void testMySQLDatabaseConnection() throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=welcome");
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
}
