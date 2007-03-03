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

        /// <summary> Gets or sets the auto-commit connection settings for new connections to this database.</summary>
        
		virtual public bool ConnectionAutoCommit
        {
			get { return this.connectionSettings.AutoCommit ; }
            set { this.connectionSettings.AutoCommit = value; }
        }
      
		/// <summary>
		/// Gets or sets the transaction isolation level on new connections created for this database.
		/// </summary>
        
		virtual public IsolationLevel ConnectionTransactionIsolation
        {
			get { return this.connectionSettings.TransactionIsolation.GetValueOrDefault() ; }
            set { this.connectionSettings.TransactionIsolation = value ; }
        }
        
		/// <summary> Gets or sets the read-only flag on new connections created for this database.</summary>
        
		virtual public bool ConnectionReadOnly
        {
			get { return this.connectionSettings.ReadOnly ; }
			set { this.connectionSettings.ReadOnly = value; }
        }
        
		/// <summary> Gets or sets the catalog name for new connections created for this database.</summary>
        
		virtual public String ConnectionCatalog
        {
            get { return this.connectionSettings.Catalog; }
            set { this.connectionSettings.Catalog = value; }
        }
        
		/// <summary> Gets or sets the LRU cache to a given size for the database.</summary>
        
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

        /// <summary>
	/// Gets or sets the setting to control whether a new connection is obtained
	/// for each lookup, or connections are retained between lookups. Controls
	/// whether a new connection is obtained for each lookup, or connections
	/// are retained between lookups.
        /// </summary>
        
        public virtual ConnectionLifecycleEnum ConnectionLifecycle
        {
	        get { return connectionLifecycleEnum; }
	        set { this.connectionLifecycleEnum = value; }
		}

        /// <summary> Gets the descriptor controlling connection creation settings.</summary>

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

        /// <summary> Gets a query result data cache descriptor.</summary>
        
        public virtual DataCacheDesc DataCacheDesc
        {
        	get { return dataCacheDesc; }
        }
    }
}
