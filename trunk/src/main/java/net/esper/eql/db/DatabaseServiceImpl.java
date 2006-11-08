package net.esper.eql.db;

import net.esper.client.ConfigurationDBRef;
import net.esper.eql.db.DatabaseConnectionFactory;

import java.util.Map;
import java.util.HashMap;

/**
 * Implementation provides database instance services such as connection factory and
 * connection settings.
 */
public class DatabaseServiceImpl implements DatabaseService
{
    private final Map<String, ConfigurationDBRef> mapDatabaseRef;
    private final Map<String, DatabaseConnectionFactory> connectionFactories;

    /**
     * Ctor.
     * @param mapDatabaseRef is a map of database name and database configuration entries
     */
    public DatabaseServiceImpl(Map<String, ConfigurationDBRef> mapDatabaseRef)
    {
        this.mapDatabaseRef = mapDatabaseRef;
        connectionFactories = new HashMap<String, DatabaseConnectionFactory>();
    }

    public boolean isRetainConnection(String databaseName) throws DatabaseException
    {
        ConfigurationDBRef config = mapDatabaseRef.get(databaseName);
        if (config == null)
        {
            throw new DatabaseException("Cannot locate configuration information for database '" + databaseName + "'");
        }

        return config.getConnectionLifecycleEnum().equals(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);
    }

    public DatabaseConnectionFactory getConnectionFactory(String databaseName) throws DatabaseException
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
            throw new DatabaseException("Cannot locate configuration information for database '" + databaseName + "'");
        }

        if (config.getConnectionFactoryDesc() instanceof ConfigurationDBRef.DriverManagerConnection)
        {
            ConfigurationDBRef.DriverManagerConnection dmConfig = (ConfigurationDBRef.DriverManagerConnection) config.getConnectionFactoryDesc();
            factory = new DatabaseDMConnFactory(dmConfig);
        }
        else
        {
            ConfigurationDBRef.DataSourceConnection dsConfig = (ConfigurationDBRef.DataSourceConnection) config.getConnectionFactoryDesc();
            factory = new DatabaseDSConnFactory(dsConfig);
        }

        connectionFactories.put(databaseName, factory);

        return factory;
    }
}
