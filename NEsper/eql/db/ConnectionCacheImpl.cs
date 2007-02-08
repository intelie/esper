using System;
using System.Data.Common;

using net.esper.collection;

using org.apache.commons.logging;

namespace net.esper.eql.db
{

    /// <summary> Caches the Connection and DbCommand instance for reuse.</summary>
    public class ConnectionCacheImpl : ConnectionCache
    {
        private Pair<DbConnection, DbCommand> cache;

        /// <summary> Ctor.</summary>
        /// <param name="databaseConnectionFactory">- connection factory
        /// </param>
        /// <param name="sql">- statement sql
        /// </param>

        public ConnectionCacheImpl(DatabaseConnectionFactory databaseConnectionFactory, String sql)
            : base(databaseConnectionFactory, sql)
        {
        }

        public override Pair<DbConnection, DbCommand> getConnection()
        {
            if (cache == null)
            {
                cache = makeNew();
            }
            return cache;
        }

        public override void doneWith(Pair<DbConnection, DbCommand> pair)
        {
            // no need to implement
        }

        public override void destroy()
        {
            if (cache != null)
            {
                close(cache);
            }
            cache = null;
        }
    }
}
