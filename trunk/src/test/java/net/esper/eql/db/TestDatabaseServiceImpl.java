package net.esper.eql.db;

import net.esper.client.ConfigurationDBRef;
import net.esper.support.eql.SupportDatabaseService;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestDatabaseServiceImpl extends TestCase
{
    private DatabaseServiceImpl databaseServiceImpl;

    public void setUp()
    {
        Map<String, ConfigurationDBRef> configs = new HashMap<String, ConfigurationDBRef>();

        ConfigurationDBRef config = new ConfigurationDBRef();
        config.setDriverManagerConnection(SupportDatabaseService.DRIVER, "url", new Properties());
        configs.put("name1", config);

        config = new ConfigurationDBRef();
        config.setDataSourceConnection("context", new Properties());
        configs.put("name2", config);

        databaseServiceImpl = new DatabaseServiceImpl(configs);
    }

    public void testGetConnection() throws Exception
    {
        DatabaseConnectionFactory factory = databaseServiceImpl.getConnectionFactory("name1");
        assertTrue(factory instanceof DatabaseDMConnFactory);

        factory = databaseServiceImpl.getConnectionFactory("name2");
        assertTrue(factory instanceof DatabaseDSConnFactory);
    }

    public void testInvalid()
    {
        try
        {
            databaseServiceImpl.getConnectionFactory("xxx");
            fail();
        }
        catch (DatabaseException ex)
        {
            log.debug(ex);
            // expected
        }
    }

    private static Log log = LogFactory.getLog(TestDatabaseServiceImpl.class);
}
