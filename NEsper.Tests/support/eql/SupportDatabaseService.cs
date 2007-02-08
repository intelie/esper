using System;
using System.Configuration;

using net.esper.client;
using net.esper.compat;
using net.esper.eql.db;
using net.esper.support.schedule;

namespace net.esper.support.eql
{
	public class SupportDatabaseService
	{
        public const String DBCONNECTION_STRING =
            "LocalSqlServer: data source=127.0.0.1" +
            ";Integrated Security=SSPI" +
            ";Initial Catalog=aspnetdb";

		public const String DBPROVIDER = "System.Data.SqlClient";

        public const String DBNAME_FULL = "mydb";

		public static DatabaseConfigServiceImpl makeService()
		{
			EDictionary<String, ConfigurationDBRef> configs = new EHashDictionary<String, ConfigurationDBRef>();

            ConnectionStringSettings settings;

            settings = new ConnectionStringSettings();
            settings.ProviderName = DBPROVIDER;
            settings.ConnectionString = DBCONNECTION_STRING;

			ConfigurationDBRef config = new ConfigurationDBRef();
			config.setDatabaseProviderConnection( settings ) ;
			configs.Put( DBNAME_FULL, config );

			return new DatabaseConfigServiceImpl( configs, new SupportSchedulingServiceImpl(), null );
		}
	}
}
