using System;
using System.Data;
using System.Data.Common;

using net.esper.collection;
using net.esper.client;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.eql.db
{
    /// <summary> Base class for a Connection and DbCommand cache.
    /// <p>
    /// Implementations control the lifecycle via lifecycle methods, or
    /// may simple obtain new resources and close new resources every time.
    /// <p>
    /// This is not a pool - a cache is associated with one client class and that
    /// class is expected to use cache methods in well-defined order of get, done-with and destroy.
    /// </summary>
    public abstract class ConnectionCache : IDisposable
    {
        private DatabaseConnectionFactory databaseConnectionFactory;
        private String sql;

        /// <summary> Returns a cached or new connection and statement pair.</summary>
        /// <returns> connection and statement pair
        /// </returns>
        public abstract Pair<DbConnection, DbCommand> GetConnection();

        /// <summary> Indicate to return the connection and statement pair after use.</summary>
        /// <param name="pair">is the resources to return
        /// </param>
        public abstract void DoneWith(Pair<DbConnection, DbCommand> pair);

        /// <summary> Destroys cache closing all resources cached, if any.</summary>
        public abstract void Destroy();

        /// <summary> Ctor.</summary>
        /// <param name="databaseConnectionFactory">- connection factory</param>
        /// <param name="sql">- statement sql</param>

        internal ConnectionCache(DatabaseConnectionFactory databaseConnectionFactory, String sql)
        {
            this.databaseConnectionFactory = databaseConnectionFactory;
            this.sql = sql;
        }

        /// <summary> Close resources.</summary>
        /// <param name="pair">is the resources to close.
        /// </param>
        
        protected void Close(Pair<DbConnection, DbCommand> pair)
        {
            log.Info(".close Closing statement and connection");
            try
            {
                pair.Second.Dispose();
            }
            catch (DbException ex)
            {
                try
                {
					pair.First.Close();
                }
                catch (DbException)
                {
                }

                throw new EPException("Error closing statement", ex);
            }

            try
            {
                pair.First.Close();
            }
            catch (DbException ex)
            {
                throw new EPException("Error closing statement", ex);
            }
        }

        /// <summary> Make a new pair of resources.</summary>
        /// <returns> pair of resources
        /// </returns>
        
        protected Pair<DbConnection, DbCommand> MakeNew()
        {
            log.Info(".makeNew Obtaining new connection and statement");
            DbConnection connection = null;
            try
            {
                connection = databaseConnectionFactory.Connection;
            }
            catch (DatabaseConfigException ex)
            {
                throw new EPException("Error obtaining connection", ex);
            }

            DbCommand preparedStatement = null;
            try
            {
                preparedStatement = connection.CreateCommand() ;
                preparedStatement.CommandText = sql ;
                preparedStatement.Prepare() ;
            }
            catch (DbException ex)
            {
                throw new EPException("Error preparing statement '" + sql + "'", ex);
            }

            return new Pair<DbConnection, DbCommand>(connection, preparedStatement);
        }

        private static Log log = LogFactory.GetLog(typeof(ConnectionCache));

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        
        public void Dispose()
        {
            Destroy() ;
        }
    }
}
