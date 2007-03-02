using System;
using System.Collections.Specialized;
using System.Configuration;
using System.Data;

namespace net.esper.client
{
    /// <summary> Container for database configuration information, such as
    /// options around getting a database connection and options to control the lifecycle
    /// of connections and set connection parameters.
    /// </summary>

    public class ConfigurationDBRef
    {
        private ConnectionFactoryDesc connectionFactoryDesc;
        private ConnectionSettings connectionSettings;
        private ConnectionLifecycleEnum connectionLifecycleEnum;
        private DataCacheDesc dataCacheDesc;

        /// <summary> Sets the auto-commit connection settings for new connections to this database.</summary>
        /// <param name="value">is true to set auto-commit to true, or false to set auto-commit to false, or null to accepts the default
        /// </param>
        
		virtual public bool ConnectionAutoCommit
        {
			get { return this.connectionSettings.AutoCommit ; }
            set { this.connectionSettings.AutoCommit = value; }
        }
        
		/// <summary> Sets the transaction isolation level on new connections created for this database.</summary>
        /// <param name="value">is the transaction isolation level
        /// </param>
        
		virtual public IsolationLevel ConnectionTransactionIsolation
        {
			get { return this.connectionSettings.TransactionIsolation.GetValueOrDefault() ; }
            set { this.connectionSettings.TransactionIsolation = value ; }
        }
        
		/// <summary> Sets the read-only flag on new connections created for this database.</summary>
        /// <param name="isReadOnly">is the read-only flag
        /// </param>
        
		virtual public bool ConnectionReadOnly
        {
			get { return this.connectionSettings.ReadOnly ; }
			set { this.connectionSettings.ReadOnly = value; }
        }
        
		/// <summary> Sets the catalog name for new connections created for this database.</summary>
        /// <param name="catalog">is the catalog name
        /// </param>
        
		virtual public String ConnectionCatalog
        {
            set { this.connectionSettings.Catalog = value; }
        }
        
		/// <summary> Configures a LRU cache of the given size for the database.</summary>
        /// <param name="size">is the maximum number of entries before query results are evicted using.
        /// </param>
        
		virtual public int LRUCache
        {
            set { dataCacheDesc = new LRUCacheDesc(value); }
        }
        
		/// <summary>
		/// Ctor.
		/// </summary>
        
		public ConfigurationDBRef()
        {
            connectionLifecycleEnum = ConnectionLifecycleEnum.RETAIN;
            connectionSettings = new ConnectionSettings();
        }

		/// <summary>
		/// Sets the connection factory
		/// </summary>
		/// <param name="className"></param>
		/// <param name="url"></param>
		/// <param name="connectionArgs"></param>
		
		public void SetDatabaseProviderConnection(String providerName, NameValueCollection connectionArgs)
    	{
        	connectionFactoryDesc = new DbProviderFactoryConnection(providerName, connectionArgs);
	    }

        /// <summary>
        /// Sets the connection factory to use DbProviderFactory to obtain a connection.
        /// </summary>
        /// <param name="settings">The settings.</param>
        
		public virtual void SetDatabaseProviderConnection( ConnectionStringSettings settings )
        {
            connectionFactoryDesc = new DbProviderFactoryConnection( settings ) ;
        }

        /// <summary> Returns the connection settings for this database.</summary>
        /// <returns> connection settings
        /// </returns>
        
		public virtual ConnectionSettings ConnectionSettings
        {
            get { return connectionSettings; }
        }

        /// <summary> Returns the setting to control whether a new connection is obtained for each lookup,
        /// or connections are retained between lookups.
        /// </summary>
        /// <returns> enum controlling connection allocation
        /// </returns>
        /// <summary> Controls whether a new connection is obtained for each lookup,
        /// or connections are retained between lookups.
        /// </summary>
        /// <param name="connectionLifecycleEnum">is an enum controlling connection allocation
        /// </param>
        
        public virtual ConnectionLifecycleEnum ConnectionLifecycle
        {
	        get { return connectionLifecycleEnum; }
	        set { this.connectionLifecycleEnum = value; }
		}

        /// <summary> Returns the descriptor controlling connection creation settings.</summary>
        /// <returns> connection factory settings
        /// </returns>
        
        public virtual ConnectionFactoryDesc ConnectionFactoryDesc
        {
        	get { return connectionFactoryDesc; }
        }

        /// <summary> Configures an expiry-time cache of the given maximum age in seconds and pruge interval in seconds.</summary>
        /// <param name="maxAgeSeconds">is the maximum number of seconds before a query result is considered stale (also known as time-to-live)
        /// </param>
        /// <param name="purgeIntervalSeconds">is the interval at which the engine purges stale data from the cache
        /// </param>

        public virtual void setExpiryTimeCache(double maxAgeSeconds, double purgeIntervalSeconds)
        {
            dataCacheDesc = new ExpiryTimeCacheDesc(maxAgeSeconds, purgeIntervalSeconds);
        }

        /// <summary> Return a query result data cache descriptor.</summary>
        /// <returns> cache descriptor
        /// </returns>
        
        public virtual DataCacheDesc DataCacheDesc
        {
        	get { return dataCacheDesc; }
        }
    }
}
