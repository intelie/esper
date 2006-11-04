package net.esper.eql.core;

import net.esper.client.ConfigurationDBRef;
import net.esper.support.eql.SupportInitialContextFactory;
import net.esper.support.eql.SupportDatabaseRefServiceFactory;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TestDatabaseRefServiceImpl extends TestCase
{
    private DatabaseRefServiceImpl databaseRefServiceImpl;

    public void setUp()
    {
        databaseRefServiceImpl = SupportDatabaseRefServiceFactory.makeService();
    }

    public void testGetConnection() throws Exception
    {
        for (String databaseName : new String[] {"mydbFour"})
        //for (String databaseName : new String[] {"mydbOne", "mydbTwo", "mydbThree", "mydbFour"})
        {
            Connection connection = databaseRefServiceImpl.getConnection(databaseName);
            tryAndCloseConnection(connection);
        }
    }

    public void testInvalid()
    {
        try
        {
            databaseRefServiceImpl.getConnection("xxx");
            fail();
        }
        catch (DatabaseRefException ex)
        {
            log.debug(ex);
            // expected
        }
    }

    private void tryAndCloseConnection(Connection connection) throws Exception
    {
        Statement stmt = connection.createStatement();
        stmt.execute("select 1 from dual");
        ResultSet result = stmt.getResultSet();
        result.next();
        assertEquals(1, result.getInt(1));
        result.close();
        stmt.close();
        connection.close();
    }

    private static Log log = LogFactory.getLog(TestDatabaseRefServiceImpl.class);
}
