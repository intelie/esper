using System;
using System.Data.Common;

using net.esper.collection;

namespace net.esper.eql.db
{
	
	/// <summary>
    /// Implementation of a connection cache that simply doesn't cache but gets
	/// a new connection and statement every request, and closes these every time
	/// a client indicates done.
	/// </summary>
    
    public class ConnectionNoCacheImpl : ConnectionCache
    {
        /// <summary> Ctor.</summary>
        /// <param name="databaseConnectionFactory">is the connection factory
        /// </param>
        /// <param name="sql">is the statement sql
        /// </param>
        public ConnectionNoCacheImpl(DatabaseConnectionFactory databaseConnectionFactory, String sql)
            : base(databaseConnectionFactory, sql)
        {
        }

        public override Pair<DbConnection, DbCommand> getConnection()
        {
            return makeNew();
        }

        public override void doneWith(Pair<DbConnection, DbCommand> pair)
        {
            close(pair);
        }

        public override void destroy()
        {
            // no resources held
        }
    }
}
