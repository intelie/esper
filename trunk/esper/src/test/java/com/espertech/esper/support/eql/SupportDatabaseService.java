package com.espertech.esper.support.eql;

import com.espertech.esper.client.ConfigurationDBRef;
import com.espertech.esper.eql.db.DatabaseConfigServiceImpl;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class SupportDatabaseService
{
    public final static String DBNAME_FULLURL = "mydb";
    public final static String DBNAME_PARTURL = "mydb_part";

    public final static String DBUSER = "root";
    public final static String DBPWD = "password";
    public final static String DRIVER = "com.mysql.jdbc.Driver";
    public final static String FULLURL = "jdbc:mysql://localhost/test?user=root&password=password";
    public final static String PARTURL = "jdbc:mysql://localhost/test";

    public static DatabaseConfigServiceImpl makeService()
    {
        Map<String, ConfigurationDBRef> configs = new HashMap<String, ConfigurationDBRef>();

        ConfigurationDBRef config = new ConfigurationDBRef();
        config.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configs.put(DBNAME_FULLURL, config);

        config = new ConfigurationDBRef();
        Properties properties = new Properties();
        properties.put("user", DBUSER);
        properties.put("password", DBPWD);
        config.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.PARTURL, properties);
        configs.put(DBNAME_PARTURL, config);

        return new DatabaseConfigServiceImpl(configs, new SupportSchedulingServiceImpl(), null);
    }
}
