using System;
using System.Configuration;
using System.Data.Common;
using System.IO;

using net.esper.client;
using net.esper.compat;
using net.esper.eql.db;
using net.esper.support.schedule;

namespace net.esper.support.eql
{
	public class SupportDatabaseService
	{
        public const String DBCONNECTION_STRING = "Server=localhost;Database=test;Uid=nesper;Pwd=nesper-test;";
        public const String DBCONNECTION2_STRING = "Server=localhost;Database=test;Uid=nesper;Pwd=nesper-test;";

        public const String DBPROVIDER = "MySql.Data.MySqlClient";

        public const String DBNAME_FULL = "mydb";
        public const String DBNAME_PART = "mydb_part";

        public const String DBUSER = "nesper";
        public const String DBPWD = "nesper-test";

        private static void ExecuteNonQuery( DbConnection dbConnection, String command ) {
        	DbCommand dbCommand = dbConnection.CreateCommand() ;
        	dbCommand.CommandText = command ;
        	dbCommand.ExecuteNonQuery() ;
        }
        
		public static DatabaseConfigServiceImpl MakeService()
		{
			EDictionary<String, ConfigurationDBRef> configs = new HashDictionary<String, ConfigurationDBRef>();
			
            ConnectionStringSettings settings;

            settings = new ConnectionStringSettings();
            settings.ProviderName = DBPROVIDER;
            settings.ConnectionString = DBCONNECTION_STRING;

			ConfigurationDBRef config = new ConfigurationDBRef();
            config.SetDatabaseProviderConnection(settings);
			configs.Put( DBNAME_FULL, config );

			return new DatabaseConfigServiceImpl( configs, new SupportSchedulingServiceImpl(), null );
		}
	}
}
