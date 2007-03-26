using System;
using System.Data.Common;
using System.Configuration;
using System.Collections.Specialized;

using net.esper.client;

namespace net.esper.eql.db
{
	/// <summary>
	/// Database connection factory using DbProviderFactory to obtain connections.
	/// </summary>
	
	public class DatabaseProviderConnFactory : DatabaseConnectionFactory
	{
        private readonly DbProviderFactoryConnection dbConfig;
        private readonly ConnectionSettings connectionSettings;

        /// <summary>
        /// Creates a new database connection.
        /// </summary>
        /// <value></value>
        /// <returns> new connection
        /// </returns>
        /// <throws>  DatabaseConfigException throws to indicate a problem getting a new connection </throws>
        virtual public DbConnection Connection
		{
			get
			{
                ConnectionStringSettings settings = dbConfig.Settings;

                DbProviderFactory dbProviderFactory = DbProviderFactories.GetFactory(settings.ProviderName);
                DbConnection dbConnection = null;

                try
                {
                    dbConnection = dbProviderFactory.CreateConnection() ;
                    dbConnection.ConnectionString = settings.ConnectionString;
                    dbConnection.Open();
				}
				catch (DbException ex)
				{
					String detail = "DbException: " + ex.Message + " VendorError: " + ex.ErrorCode;
					throw new DatabaseConfigException(
                        "Error obtaining database connection using connection-string '" + settings.ConnectionString + 
                        "' with detail " + detail, ex);
				}
				
				setConnectionOptions(dbConnection, connectionSettings);
				
				return dbConnection;
			}
		}

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="dbConfig">is the database provider configuration</param>
        /// <param name="connectionSettings">are connection-level settings</param>
        /// <throws>  DatabaseConfigException thrown if the driver class cannot be loaded </throws>

        public DatabaseProviderConnFactory(DbProviderFactoryConnection dbConfig, ConnectionSettings connectionSettings)
		{
			this.dbConfig = dbConfig;
			this.connectionSettings = connectionSettings;
		}
		
		/// <summary> Method to set connection-level configuration settings.</summary>
		/// <param name="connection">is the connection to set on
		/// </param>
		/// <param name="connectionSettings">are the settings to apply
		/// </param>
		/// <throws>  DatabaseConfigException is thrown if an DbException is thrown </throws>
		internal static void setConnectionOptions(DbConnection connection, ConnectionSettings connectionSettings)
		{
			try
			{
				if (connectionSettings.ReadOnly)
				{
                    throw new NotSupportedException("Read-only semantics not yet supported in this version");
				}
			}
			catch (DbException ex)
			{
				throw new DatabaseConfigException(
                    "Error setting read-only to " + connectionSettings.ReadOnly +
                    " on connection with detail " + getDetail(ex), ex);
			}
			
			try
			{
				if (connectionSettings.TransactionIsolation != null)
				{
                    // Begin a transaction to provide the proper isolation.  Need to ensure
                    // that the transaction is properly committed upon completion since we
                    // do not have auto-commit handled.
                    connection.BeginTransaction(connectionSettings.TransactionIsolation.Value);
				}
			}
			catch (DbException ex)
			{
				throw new DatabaseConfigException(
                    "Error setting transaction isolation level to " + connectionSettings.TransactionIsolation +
                    " on connection with detail " + getDetail(ex), ex);
			}
			
			try
			{
				if (connectionSettings.Catalog != null)
				{
                    connection.ChangeDatabase(connectionSettings.Catalog);
				}
			}
			catch (DbException ex)
			{
				throw new DatabaseConfigException(
                    "Error setting catalog to '" + connectionSettings.Catalog +
                    "' on connection with detail " + getDetail(ex), ex);
			}
			
			try
			{
				if (connectionSettings.AutoCommit)
				{
                    throw new NotSupportedException("AutoCommit semantics not yet supported in this version");
				}
			}
			catch (DbException ex)
			{
				throw new DatabaseConfigException(
                    "Error setting auto-commit to " + connectionSettings.AutoCommit + 
                    " on connection with detail " + getDetail(ex), ex);
			}
		}
		
		private static String getDetail(DbException ex)
		{
			return
                "DbException: " + ex.Message +
                " VendorError: " + ex.ErrorCode;
		}
	}
}
