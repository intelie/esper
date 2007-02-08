using System;
using System.Collections.Generic;

using net.esper.view;

namespace net.esper.eql.spec
{
	
	/// <summary> Specification object for historical data poll via database SQL statement.</summary>
	public class DBStatementStreamSpec:StreamSpec
	{
		/// <summary> Returns the database name.</summary>
		/// <returns> name of database.
		/// </returns>
		
        virtual public String DatabaseName
		{
			get { return databaseName; }
		}

		/// <summary> Returns the SQL with substitution parameters.</summary>
		/// <returns> SQL with parameters embedded as ${stream.param}
		/// </returns>
		
        virtual public String SqlWithSubsParams
		{
			get { return sqlWithSubsParams; }
		}

		private String databaseName;
		private String sqlWithSubsParams;
		
		/// <summary> Ctor.</summary>
		/// <param name="optionalStreamName">is a stream name optionally given to stream
		/// </param>
		/// <param name="viewSpecs">is a list of views onto the stream
		/// </param>
		/// <param name="databaseName">is the database name to poll
		/// </param>
		/// <param name="sqlWithSubsParams">is the SQL with placeholder parameters
		/// </param>

        public DBStatementStreamSpec(String optionalStreamName, IList<ViewSpec> viewSpecs, String databaseName, String sqlWithSubsParams)
            : base(optionalStreamName, viewSpecs)
        {
            this.databaseName = databaseName;
            this.sqlWithSubsParams = sqlWithSubsParams;
        }
	}
}