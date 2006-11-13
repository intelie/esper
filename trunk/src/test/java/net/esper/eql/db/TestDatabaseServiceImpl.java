package net.esper.eql.db;

import net.esper.client.ConfigurationDBRef;
import net.esper.support.eql.SupportDatabaseService;
import net.esper.schedule.SchedulingServiceImpl;
import net.esper.schedule.SchedulingService;

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
        config.setLRUCache(10000);
        configs.put("name2", config);

        config = new ConfigurationDBRef();
        config.setDataSourceConnection("context", new Properties());
        config.setExpiryTimeCache(1, 3);
        configs.put("name3", config);

        SchedulingService schedulingService = new SchedulingServiceImpl();
        databaseServiceImpl = new DatabaseConfigServiceImpl(configs, schedulingService, schedulingService.allocateBucket());
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
        assertEquals(1000, exp.getMaxAgeMSec());
        assertEquals(3000, exp.getPurgeIntervalMSec());
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
