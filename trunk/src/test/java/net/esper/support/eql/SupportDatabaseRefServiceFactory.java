package net.esper.support.eql;

import net.esper.client.ConfigurationDBRef;
import net.esper.eql.core.DatabaseRefServiceImpl;
import net.esper.eql.core.DatabaseRefService;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class SupportDatabaseRefServiceFactory
{
    private final static String DBUSER = "root";
    private final static String DBPWD = "password";

    public static DatabaseRefServiceImpl makeService()
    {
        Map<String, ConfigurationDBRef> configs = new HashMap<String, ConfigurationDBRef>();

        // driver-manager config 1
        ConfigurationDBRef config = new ConfigurationDBRef();
        config.setDriverManagerConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/test?user=root&password=password", new Properties());
        configs.put("mydbOne", config);

        // driver-manager config 2
        config = new ConfigurationDBRef();
        config.setDriverManagerConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/test", DBUSER, DBPWD);
        configs.put("mydbTwo", config);

        // driver-manager config 3
        config = new ConfigurationDBRef();
        Properties properties = new Properties();
        properties.setProperty("user", DBUSER);
        properties.setProperty("password", DBPWD);
        config.setDriverManagerConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/test", properties);
        configs.put("mydbThree", config);

        // data source config 4
        config = new ConfigurationDBRef();
        MysqlDataSource mySQLDataSource = new MysqlDataSource();
        mySQLDataSource.setUser(DBUSER);
        mySQLDataSource.setPassword(DBPWD);
        mySQLDataSource.setURL("jdbc:mysql://localhost/test");

        String envName = "java:comp/env/jdbc/MySQLDB";
        SupportInitialContextFactory.addContextEntry(envName, mySQLDataSource);
        properties.put("java.naming.factory.initial", SupportInitialContextFactory.class.getName());

        config.setDataSourceConnection(envName, properties);
        configs.put("mydbFour", config);

        return new DatabaseRefServiceImpl(configs);
    }
}
