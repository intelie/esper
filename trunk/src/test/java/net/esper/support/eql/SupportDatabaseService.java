package net.esper.support.eql;

import net.esper.client.ConfigurationDBRef;
import net.esper.eql.db.DatabaseServiceImpl;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class SupportDatabaseService
{
    public final static String DBNAME = "mydb";
    public final static String DBUSER = "root";
    public final static String DBPWD = "password";
    public final static String DRIVER = "com.mysql.jdbc.Driver";
    public final static String FULLURL = "jdbc:mysql://localhost/test?user=root&password=password";
    public final static String PARTURL = "jdbc:mysql://localhost/test";

    public static DatabaseServiceImpl makeService()
    {
        Map<String, ConfigurationDBRef> configs = new HashMap<String, ConfigurationDBRef>();

        ConfigurationDBRef config = new ConfigurationDBRef();
        config.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configs.put(DBNAME, config);

        return new DatabaseServiceImpl(configs);
    }
}
