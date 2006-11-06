package net.esper.eql.db;

import junit.framework.TestCase;
import net.esper.support.eql.SupportDatabaseService;
import net.esper.client.ConfigurationDBRef;
import net.esper.eql.db.DatabaseDMConnFactory;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestDatabaseDMConnFactory extends TestCase
{
    private DatabaseDMConnFactory databaseDMConnFactoryOne;
    private DatabaseDMConnFactory databaseDMConnFactoryTwo;
    private DatabaseDMConnFactory databaseDMConnFactoryThree;

    public void setUp() throws Exception
    {
        // driver-manager config 1
        ConfigurationDBRef config = new ConfigurationDBRef();
        config.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        databaseDMConnFactoryOne = new DatabaseDMConnFactory((ConfigurationDBRef.DriverManagerConnection) config.getConnectionFactoryDesc());

        // driver-manager config 2
        config = new ConfigurationDBRef();
        config.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.PARTURL, SupportDatabaseService.DBUSER, SupportDatabaseService.DBPWD);
        databaseDMConnFactoryTwo = new DatabaseDMConnFactory((ConfigurationDBRef.DriverManagerConnection) config.getConnectionFactoryDesc());

        // driver-manager config 3
        config = new ConfigurationDBRef();
        Properties properties = new Properties();
        properties.setProperty("user", SupportDatabaseService.DBUSER);
        properties.setProperty("password", SupportDatabaseService.DBPWD);
        config.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.PARTURL, properties);
        databaseDMConnFactoryThree = new DatabaseDMConnFactory((ConfigurationDBRef.DriverManagerConnection) config.getConnectionFactoryDesc());
    }

    public void testGetConnection() throws Exception
    {
        Connection connection = databaseDMConnFactoryOne.getConnection();
        tryAndCloseConnection(connection);

        connection = databaseDMConnFactoryTwo.getConnection();
        tryAndCloseConnection(connection);

        connection = databaseDMConnFactoryThree.getConnection();
        tryAndCloseConnection(connection);
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

    private static Log log = LogFactory.getLog(TestDatabaseDMConnFactory.class);
}
