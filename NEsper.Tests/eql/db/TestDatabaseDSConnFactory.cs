using System;

using com.mysql.jdbc.jdbc2.optional;

using net.esper.client;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.db
{
	
	public class TestDatabaseDSConnFactory:TestCase
	{
		private DatabaseDSConnFactory databaseDSConnFactory;
		
		[SetUp]
		public virtual void  setUp()
		{
			MysqlDataSource mySQLDataSource = new MysqlDataSource();
			mySQLDataSource.setUser(SupportDatabaseService.DBUSER);
			mySQLDataSource.setPassword(SupportDatabaseService.DBPWD);
			mySQLDataSource.setURL("jdbc:mysql://localhost/test");
			
			String envName = "java:comp/env/jdbc/MySQLDB";
			SupportInitialContextFactory.AddContextEntry(envName, mySQLDataSource);
			
			ConfigurationDBRef config = new ConfigurationDBRef();
			System.Collections.Specialized.NameValueCollection properties = new System.Collections.Specialized.NameValueCollection();
			properties[(String) "java.naming.factory.initial"] = (String) typeof(SupportInitialContextFactory).FullName;
			config.setDataSourceConnection(envName, properties);
			
			databaseDSConnFactory = new DatabaseDSConnFactory((ConfigurationDBRef.DataSourceConnection) config.ConnectionFactoryDesc, config.ConnectionSettings);
		}
		
		[Test]
		public virtual void  testGetConnection()
		{
			System.Data.OleDb.OleDbConnection connection = databaseDSConnFactory.Connection;
			tryAndCloseConnection(connection);
		}
		
		private void  tryAndCloseConnection(System.Data.OleDb.OleDbConnection connection)
		{
			System.Data.OleDb.OleDbCommand stmt = SupportClass.TransactionManager.manager.CreateStatement(connection);
			System.Data.OleDb.OleDbCommand temp_OleDbCommand;
			temp_OleDbCommand = stmt;
			temp_OleDbCommand.CommandText = "select 1 from dual";
			temp_OleDbCommand.ExecuteNonQuery();

			System.Data.OleDb.OleDbDataReader result = stmt.getResultSet();
			result.Read();
			Assert.AreEqual(1, result.GetInt32(1 - 1));
			result.Close();

			stmt.close();

			SupportClass.TransactionManager.manager.Close(connection);
		}
		
		private static Log log = LogFactory.GetLog(typeof(TestDatabaseDSConnFactory));
	}
}
