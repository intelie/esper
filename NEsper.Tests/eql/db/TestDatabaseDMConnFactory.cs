using System;
using System.Configuration;
using System.Collections.Specialized;
using System.Data;
using System.Data.Common;
using System.IO;

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
        	NameValueCollection properties = new NameValueCollection() ;

            // driver-manager config 1
            ConfigurationDBRef config = new ConfigurationDBRef();
            properties["Name"] = "Connection1";
            properties["Server"] = "localhost";
            properties["Uid"] = "nesper";
            properties["Pwd"] = "nesper-test";
            config.SetDatabaseProviderConnection(SupportDatabaseService.DBPROVIDER, properties);
            config.ConnectionAutoCommit = false; // not supported yet
            config.ConnectionTransactionIsolation = IsolationLevel.Unspecified;
            config.ConnectionReadOnly = false; // not supported yet
            databaseDMConnFactoryOne = new DatabaseProviderConnFactory((DbProviderFactoryConnection)config.ConnectionFactoryDesc, config.ConnectionSettings);

            // driver-manager config 2
            config = new ConfigurationDBRef();
            config.SetDatabaseProviderConnection(new ConnectionStringSettings(
                "Connection2",
                SupportDatabaseService.DBCONNECTION_STRING,
                SupportDatabaseService.DBPROVIDER));
            databaseDMConnFactoryTwo = new DatabaseProviderConnFactory((DbProviderFactoryConnection)config.ConnectionFactoryDesc, config.ConnectionSettings);

            // driver-manager config 3
            config = new ConfigurationDBRef();
            config.SetDatabaseProviderConnection(new ConnectionStringSettings(
                "Connection3",
                SupportDatabaseService.DBCONNECTION_STRING,
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
        	DbCommand stmt ;
        	
        	stmt = connection.CreateCommand() ;
        	stmt.CommandText = "CREATE TABLE DUAL (ID INTEGER PRIMARY KEY)" ;
        	stmt.ExecuteNonQuery() ;
        	
        	stmt = connection.CreateCommand() ;
        	stmt.CommandText = "INSERT INTO DUAL(ID) VALUES(1)" ;
        	stmt.ExecuteNonQuery() ;
        	
            stmt = connection.CreateCommand();
            stmt.CommandText = "SELECT ID FROM DUAL";

            DbDataReader result = stmt.ExecuteReader();
            Assert.IsTrue(result.Read());
            Assert.AreEqual(1, result.GetInt32(0));

            result.Close();

            stmt.Connection.Close();
        }

        private static Log log = LogFactory.GetLog(typeof(TestDatabaseDMConnFactory));
    }
}
