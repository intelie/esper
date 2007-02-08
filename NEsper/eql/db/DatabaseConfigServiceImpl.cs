using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.schedule;

namespace net.esper.eql.db
{
	/// <summary> Implementation provides database instance services such as connection factory and
	/// connection settings.
	/// </summary>
	
	public class DatabaseConfigServiceImpl : DatabaseConfigService
	{
        private readonly IDictionary<String, ConfigurationDBRef> mapDatabaseRef;
		private readonly IDictionary<String, DatabaseConnectionFactory> connectionFactories;
		private readonly SchedulingService schedulingService;
		private readonly ScheduleBucket scheduleBucket;
		
		/// <summary> Ctor.</summary>
		/// <param name="mapDatabaseRef">is a map of database name and database configuration entries
		/// </param>
		/// <param name="schedulingService">is for scheduling callbacks for a cache
		/// </param>
		/// <param name="scheduleBucket">is a system bucket for all scheduling callbacks for caches
		/// </param>
        public DatabaseConfigServiceImpl(IDictionary<String, ConfigurationDBRef> mapDatabaseRef, SchedulingService schedulingService, ScheduleBucket scheduleBucket)
        {
            this.mapDatabaseRef = mapDatabaseRef;
            this.connectionFactories = new Dictionary<String, DatabaseConnectionFactory>();
            this.schedulingService = schedulingService;
            this.scheduleBucket = scheduleBucket;
        }
		
		public virtual ConnectionCache getConnectionCache(String databaseName, String preparedStatementText)
		{
            ConfigurationDBRef config = null;
            if ( ! mapDatabaseRef.TryGetValue(databaseName, out config ) )
			{
				throw new DatabaseConfigException("Cannot locate configuration information for database '" + databaseName + "'");
			}
			
			DatabaseConnectionFactory connectionFactory = getConnectionFactory(databaseName);
			
			if ( config.ConnectionLifecycle == ConnectionLifecycleEnum.RETAIN)
			{
				return new ConnectionCacheImpl(connectionFactory, preparedStatementText);
			}
			else
			{
				return new ConnectionNoCacheImpl(connectionFactory, preparedStatementText);
			}
		}
		
		public virtual DatabaseConnectionFactory getConnectionFactory(String databaseName)
		{
			// check if we already have a reference
            DatabaseConnectionFactory factory = null;
            if ( ! connectionFactories.TryGetValue(databaseName, out factory ) )
			{
				return factory;
			}

            ConfigurationDBRef config = null;
            if ( ! mapDatabaseRef.TryGetValue( databaseName, out config ) )
			{
				throw new DatabaseConfigException("Cannot locate configuration information for database '" + databaseName + "'");
			}
			
			ConnectionSettings settings = config.ConnectionSettings;
			if (config.ConnectionFactoryDesc is DbProviderFactoryConnection)
			{
                DbProviderFactoryConnection dbConfig = (DbProviderFactoryConnection)config.ConnectionFactoryDesc;
				factory = new DatabaseProviderConnFactory(dbConfig, settings);
			}
			else
			{
                throw new NotSupportedException();
			}
			
			connectionFactories[databaseName] = factory;
			
			return factory;
		}
		
		public virtual DataCache getDataCache(String databaseName)
		{
            ConfigurationDBRef config = null;
            if ( ! mapDatabaseRef.TryGetValue(databaseName, out config ) )
			{
				throw new DatabaseConfigException("Cannot locate configuration information for database '" + databaseName + "'");
			}
			
			// default is no cache
			if (config.DataCacheDesc == null)
			{
				return new DataCacheNullImpl();
			}
			
			if (config.DataCacheDesc is LRUCacheDesc)
			{
				LRUCacheDesc lruCache = (LRUCacheDesc) config.DataCacheDesc;
				return new DataCacheLRUImpl(lruCache.Size);
			}
			
			if (config.DataCacheDesc is ExpiryTimeCacheDesc)
			{
				ExpiryTimeCacheDesc expCache = (ExpiryTimeCacheDesc) config.DataCacheDesc;
				return new DataCacheExpiringImpl(expCache.MaxAgeSeconds, expCache.PurgeIntervalSeconds, schedulingService, scheduleBucket.allocateSlot());
			}
			
			throw new SystemException("Cache implementation class not configured");
		}
	}
}
