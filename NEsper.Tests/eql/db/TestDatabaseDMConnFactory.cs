using System;
using System.Configuration;
using System.Data;
using System.Data.Common;

using net.esper.client;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.db
{
    [TestFixture]
    public class TestDatabaseDMConnFactory
    {
        private DatabaseProviderConnFactory databaseDMConnFactoryOne;
        private DatabaseProviderConnFactory databaseDMConnFactoryTwo;
        private DatabaseProviderConnFactory databaseDMConnFactoryThree;

        [SetUp]
        public virtual void setUp()
        {
            // driver-manager config 1
            ConfigurationDBRef config = new ConfigurationDBRef();
            config.setDatabaseProviderConnection(new ConnectionStringSettings(
                "Connection1",
                "LocalSqlServer: data source=127.0.0.1;Integrated Security=SSPI;Initial Catalog=aspnetdb",
                SupportDatabaseService.DBPROVIDER));
            config.ConnectionAutoCommit = true;
            config.ConnectionCatalog = "test";
            config.ConnectionTransactionIsolation = IsolationLevel.Unspecified;
            config.ConnectionReadOnly = true;
            databaseDMConnFactoryOne = new DatabaseProviderConnFactory((DbProviderFactoryConnection)config.ConnectionFactoryDesc, config.ConnectionSettings);

            // driver-manager config 2
            config = new ConfigurationDBRef();
            config.setDatabaseProviderConnection(new ConnectionStringSettings(
                "Connection2",
                "LocalSqlServer: data source=127.0.0.1;Integrated Security=SSPI;Initial Catalog=aspnetdb",
                SupportDatabaseService.DBPROVIDER));
            databaseDMConnFactoryTwo = new DatabaseProviderConnFactory((DbProviderFactoryConnection)config.ConnectionFactoryDesc, config.ConnectionSettings);

            // driver-manager config 3
            config = new ConfigurationDBRef();
            config.setDatabaseProviderConnection(new ConnectionStringSettings(
                "Connection3",
                "LocalSqlServer: data source=127.0.0.1;Integrated Security=SSPI;Initial Catalog=aspnetdb",
                SupportDatabaseService.DBPROVIDER));
            databaseDMConnFactoryThree = new DatabaseProviderConnFactory((DbProviderFactoryConnection)config.ConnectionFactoryDesc, config.ConnectionSettings);
        }

        [Test]
        public virtual void testGetConnection()
        {
            DbConnection connection = databaseDMConnFactoryOne.Connection;
            tryAndCloseConnection(connection);

            connection = databaseDMConnFactoryTwo.Connection;
            tryAndCloseConnection(connection);

            connection = databaseDMConnFactoryThree.Connection;
            tryAndCloseConnection(connection);
        }

        private void tryAndCloseConnection(DbConnection connection)
        {
            DbCommand stmt = connection.CreateCommand();
            stmt.CommandText = "select 1 from dual";

            DbDataReader result = stmt.ExecuteReader();
            Assert.IsTrue(result.Read());
            Assert.AreEqual(1, result.GetInt32(0));

            result.Close();

            stmt.Connection.Close();
        }

        private static Log log = LogFactory.GetLog(typeof(TestDatabaseDMConnFactory));
    }
}
