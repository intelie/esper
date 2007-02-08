using System;
using System.Data.Common;

namespace net.esper.eql.db
{
	/// <summary>
	///  Factory for new database connections.
	/// </summary>
	
	public interface DatabaseConnectionFactory
	{
		/// <summary> Creates a new database connection.</summary>
		/// <returns> new connection
		/// </returns>
		/// <throws>  DatabaseConfigException throws to indicate a problem getting a new connection </throws>

		DbConnection Connection { get ; }
	}
}
