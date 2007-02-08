using System;
namespace net.esper.eql.db
{
	
	/// <summary> Service providing database connection factory and configuration information
	/// for use with historical data polling.
	/// </summary>
	public interface DatabaseConfigService
	{
		/// <summary> Returns a connection factory for a configured database.</summary>
		/// <param name="databaseName">is the name of the database
		/// </param>
		/// <returns> is a connection factory to use to get connections to the database
		/// </returns>
		/// <throws>  DatabaseConfigException is thrown to indicate database configuration errors </throws>
		DatabaseConnectionFactory getConnectionFactory(String databaseName);
		
		/// <summary> Returns true to indicate a setting to retain connections between lookups.</summary>
		/// <param name="databaseName">is the name of the database
		/// </param>
		/// <param name="preparedStatementText">is the sql text
		/// </param>
		/// <returns> a cache implementation to cache connection and prepared statements
		/// </returns>
		/// <throws>  DatabaseConfigException is thrown to indicate database configuration errors </throws>
		ConnectionCache getConnectionCache(String databaseName, String preparedStatementText);
		
		/// <summary> Returns a new cache implementation for this database.</summary>
		/// <param name="databaseName">is the name of the database to return a new cache implementation for for
		/// </param>
		/// <returns> cache implementation
		/// </returns>
		/// <throws>  DatabaseConfigException is thrown to indicate database configuration errors </throws>
		DataCache getDataCache(String databaseName);
	}
}