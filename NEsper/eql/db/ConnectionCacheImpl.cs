using System;
using System.Data.Common;

using net.esper.collection;

using org.apache.commons.logging;

namespace net.esper.eql.db
{
    /// <summary>
    /// Caches the Connection and DbCommand instance for reuse.
    /// </summary>
    
    public class ConnectionCacheImpl : ConnectionCache
    {
        private Pair<DbConnection, DbCommand> cache;

        /// <summary> Ctor.</summary>
        /// <param name="databaseConnectionFactory">connection factory
        /// </param>
        /// <param name="sql">statement sql
        /// </param>

        public ConnectionCacheImpl(DatabaseConnectionFactory databaseConnectionFactory, String sql)
            : base(databaseConnectionFactory, sql)
        {
        }

        /// <summary>
        /// Returns a cached or new connection and statement pair.
        /// </summary>
        /// <returns>connection and statement pair</returns>
        public override Pair<DbConnection, DbCommand> GetConnection()
        {
            if (cache == null)
            {
                cache = MakeNew();
            }
            return cache;
        }

        /// <summary>
        /// Indicate to return the connection and statement pair after use.
        /// </summary>
        /// <param name="pair">is the resources to return</param>
        public override void DoneWith(Pair<DbConnection, DbCommand> pair)
        {
            // no need to implement
        }

        /// <summary>
        /// Destroys cache closing all resources cached, if any.
        /// </summary>
        public override void Destroy()
        {
            if (cache != null)
            {
                Close(cache);
            }
            cache = null;
        }
    }
}
