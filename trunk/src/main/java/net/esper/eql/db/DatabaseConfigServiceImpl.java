package net.esper.eql.db;

import net.esper.client.ConfigurationDBRef;
import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleBucket;

import java.util.Map;
import java.util.HashMap;

/**
 * Implementation provides database instance services such as connection factory and
 * connection settings.
 */
public class DatabaseConfigServiceImpl implements DatabaseConfigService
{
    private final Map<String, ConfigurationDBRef> mapDatabaseRef;
    private final Map<String, DatabaseConnectionFactory> connectionFactories;
    private final SchedulingService schedulingService;
    private final ScheduleBucket scheduleBucket;

    /**
     * Ctor.
     * @param mapDatabaseRef is a map of database name and database configuration entries
     * @param schedulingService is for scheduling callbacks for a cache
     */
    public DatabaseConfigServiceImpl(Map<String, ConfigurationDBRef> mapDatabaseRef,
                                     SchedulingService schedulingService,
                                     ScheduleBucket scheduleBucket)
    {
        this.mapDatabaseRef = mapDatabaseRef;
        this.connectionFactories = new HashMap<String, DatabaseConnectionFactory>();
        this.schedulingService = schedulingService;
        this.scheduleBucket = scheduleBucket;
    }

    public ConnectionCache getConnectionCache(String databaseName, String preparedStatementText) throws DatabaseConfigException
    {
        ConfigurationDBRef config = mapDatabaseRef.get(databaseName);
        if (config == null)
        {
            throw new DatabaseConfigException("Cannot locate configuration information for database '" + databaseName + "'");
        }

        DatabaseConnectionFactory connectionFactory = getConnectionFactory(databaseName);

        boolean retain = config.getConnectionLifecycleEnum().equals(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);
        if (retain)
        {
            return new ConnectionCacheImpl(connectionFactory, preparedStatementText);
        }
        else
        {
            return new ConnectionNoCacheImpl(connectionFactory, preparedStatementText);
        }
    }

    public DatabaseConnectionFactory getConnectionFactory(String databaseName) throws DatabaseConfigException
    {
        // check if we already have a reference
        DatabaseConnectionFactory factory = connectionFactories.get(databaseName);
        if (factory != null)
        {
            return factory;
        }

        ConfigurationDBRef config = mapDatabaseRef.get(databaseName);
        if (config == null)
        {
            throw new DatabaseConfigException("Cannot locate configuration information for database '" + databaseName + "'");
        }

        ConfigurationDBRef.ConnectionSettings settings = (ConfigurationDBRef.ConnectionSettings) config.getConnectionSettings();
        if (config.getConnectionFactoryDesc() instanceof ConfigurationDBRef.DriverManagerConnection)
        {
            ConfigurationDBRef.DriverManagerConnection dmConfig = (ConfigurationDBRef.DriverManagerConnection) config.getConnectionFactoryDesc();
            factory = new DatabaseDMConnFactory(dmConfig, settings);
        }
        else
        {
            ConfigurationDBRef.DataSourceConnection dsConfig = (ConfigurationDBRef.DataSourceConnection) config.getConnectionFactoryDesc();
            factory = new DatabaseDSConnFactory(dsConfig, settings);
        }

        connectionFactories.put(databaseName, factory);

        return factory;
    }

    public DataCache getDataCache(String databaseName) throws DatabaseConfigException
    {
        ConfigurationDBRef config = mapDatabaseRef.get(databaseName);
        if (config == null)
        {
            throw new DatabaseConfigException("Cannot locate configuration information for database '" + databaseName + "'");
        }

        // default is no cache
        if (config.getDataCacheDesc() == null)
        {
            return new DataCacheNullImpl();
        }

        if (config.getDataCacheDesc() instanceof ConfigurationDBRef.LRUCacheDesc)
        {
            ConfigurationDBRef.LRUCacheDesc lruCache = (ConfigurationDBRef.LRUCacheDesc) config.getDataCacheDesc();
            return new DataCacheLRUImpl(lruCache.getSize());
        }

        if (config.getDataCacheDesc() instanceof ConfigurationDBRef.ExpiryTimeCacheDesc)
        {
            ConfigurationDBRef.ExpiryTimeCacheDesc expCache = (ConfigurationDBRef.ExpiryTimeCacheDesc) config.getDataCacheDesc();
            return new DataCacheExpiringImpl(expCache.getMaxAgeSeconds(), expCache.getPurgeIntervalSeconds(), schedulingService,
                    scheduleBucket.allocateSlot());
        }

        throw new IllegalStateException("Cache implementation class not configured");
    }
}
