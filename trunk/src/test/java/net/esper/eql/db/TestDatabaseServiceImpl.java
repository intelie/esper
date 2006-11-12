package net.esper.eql.db;

import net.esper.client.ConfigurationDBRef;
import net.esper.support.eql.SupportDatabaseService;
import net.esper.support.schedule.SupportSchedulingServiceImpl;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestDatabaseServiceImpl extends TestCase
{
    private DatabaseConfigServiceImpl databaseServiceImpl;

    public void setUp()
    {
        Map<String, ConfigurationDBRef> configs = new HashMap<String, ConfigurationDBRef>();

        ConfigurationDBRef config = new ConfigurationDBRef();
        config.setDriverManagerConnection(SupportDatabaseService.DRIVER, "url", new Properties());
        configs.put("name1", config);

        config = new ConfigurationDBRef();
        config.setDataSourceConnection("context", new Properties());
        configs.put("name2", config);
        config.setLRUCache(10000);

        config = new ConfigurationDBRef();
        config.setDataSourceConnection("context", new Properties());
        configs.put("name3", config);
        config.setExpiryTimeCache(1, 99999);

        databaseServiceImpl = new DatabaseConfigServiceImpl(configs, new SupportSchedulingServiceImpl(), null);
    }

    public void testGetConnection() throws Exception
    {
        DatabaseConnectionFactory factory = databaseServiceImpl.getConnectionFactory("name1");
        assertTrue(factory instanceof DatabaseDMConnFactory);

        factory = databaseServiceImpl.getConnectionFactory("name2");
        assertTrue(factory instanceof DatabaseDSConnFactory);
    }

    public void testGetCache() throws Exception
    {
        assertTrue(databaseServiceImpl.getDataCache("name1") instanceof DataCacheNullImpl);

        DataCacheLRUImpl lru = (DataCacheLRUImpl) databaseServiceImpl.getDataCache("name2");
        assertEquals(10000, lru.getCacheSize());

        DataCacheExpiringImpl exp = (DataCacheExpiringImpl) databaseServiceImpl.getDataCache("name3");
        assertEquals(1, exp.getMaxAgeMSec());
    }

    public void testInvalid()
    {
        try
        {
            databaseServiceImpl.getConnectionFactory("xxx");
            fail();
        }
        catch (DatabaseConfigException ex)
        {
            log.debug(ex);
            // expected
        }
    }

    private static Log log = LogFactory.getLog(TestDatabaseServiceImpl.class);
}
