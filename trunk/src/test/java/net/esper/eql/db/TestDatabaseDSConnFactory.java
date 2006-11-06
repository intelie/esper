package net.esper.eql.db;

import junit.framework.TestCase;
import net.esper.support.eql.SupportInitialContextFactory;
import net.esper.support.eql.SupportDatabaseService;
import net.esper.client.ConfigurationDBRef;
import net.esper.eql.db.DatabaseDSConnFactory;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TestDatabaseDSConnFactory extends TestCase
{
    private DatabaseDSConnFactory databaseDSConnFactory;

    public void setUp()
    {
        MysqlDataSource mySQLDataSource = new MysqlDataSource();
        mySQLDataSource.setUser(SupportDatabaseService.DBUSER);
        mySQLDataSource.setPassword(SupportDatabaseService.DBPWD);
        mySQLDataSource.setURL("jdbc:mysql://localhost/test");

        String envName = "java:comp/env/jdbc/MySQLDB";
        SupportInitialContextFactory.addContextEntry(envName, mySQLDataSource);

        ConfigurationDBRef config = new ConfigurationDBRef();
        Properties properties = new Properties();
        properties.put("java.naming.factory.initial", SupportInitialContextFactory.class.getName());
        config.setDataSourceConnection(envName, properties);

        databaseDSConnFactory = new DatabaseDSConnFactory((ConfigurationDBRef.DataSourceConnection)config.getConnectionFactoryDesc());
    }

    public void testGetConnection() throws Exception
    {
        Connection connection = databaseDSConnFactory.getConnection();
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

    private static Log log = LogFactory.getLog(TestDatabaseDSConnFactory.class);
}
