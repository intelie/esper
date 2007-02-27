using System;
using System.Collections.Specialized;
using System.Configuration;

using net.esper.client;
using net.esper.compat;
using net.esper.schedule;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.db
{
	[TestFixture]
	public class TestDatabaseServiceImpl
	{
		private DatabaseConfigServiceImpl databaseServiceImpl;

		[SetUp]
		public virtual void setUp()
		{
			EDictionary<String, ConfigurationDBRef> configs = new EHashDictionary<String, ConfigurationDBRef>();

            ConnectionStringSettings settings;
            
            settings = new ConnectionStringSettings();
            settings.ProviderName = SupportDatabaseService.DBPROVIDER;
            settings.Name = "name1" ;
            settings.ConnectionString = SupportDatabaseService.DBCONNECTION_STRING;

			ConfigurationDBRef config = new ConfigurationDBRef();
            config.SetDatabaseProviderConnection( settings ) ;
			configs.Put( "name1", config );

            settings = new ConnectionStringSettings();
            settings.ProviderName = SupportDatabaseService.DBPROVIDER;
            settings.Name = "name2";
            settings.ConnectionString = SupportDatabaseService.DBCONNECTION_STRING;

			config = new ConfigurationDBRef();
            config.SetDatabaseProviderConnection(settings);
			config.LRUCache = 10000;
			configs.Put( "name2", config );

            settings = new ConnectionStringSettings();
            settings.ProviderName = SupportDatabaseService.DBPROVIDER;
            settings.Name = "name3";
            settings.ConnectionString = SupportDatabaseService.DBCONNECTION_STRING;

			config = new ConfigurationDBRef();
            config.SetDatabaseProviderConnection(settings);
			config.setExpiryTimeCache( 1, 3 );
			configs.Put( "name3", config );

			SchedulingService schedulingService = new SchedulingServiceImpl();
			databaseServiceImpl = new DatabaseConfigServiceImpl(
                configs, schedulingService, schedulingService.allocateBucket() );
		}

		[Test]
		public virtual void testGetConnection()
		{
			DatabaseConnectionFactory factory ;
            
            factory = databaseServiceImpl.GetConnectionFactory( "name1" );
            Object foo = factory.GetType();
			Assert.IsTrue( factory is DatabaseProviderConnFactory ) ;

            factory = databaseServiceImpl.GetConnectionFactory("name2");
            Assert.IsTrue(factory is DatabaseProviderConnFactory);
        }

		[Test]
		public virtual void testGetCache()
		{
			Assert.IsTrue( databaseServiceImpl.GetDataCache( "name1" ) is DataCacheNullImpl );

			DataCacheLRUImpl lru = (DataCacheLRUImpl) databaseServiceImpl.GetDataCache( "name2" );
			Assert.AreEqual( 10000, lru.CacheSize );

			DataCacheExpiringImpl exp = (DataCacheExpiringImpl) databaseServiceImpl.GetDataCache( "name3" );
			Assert.AreEqual( 1000, exp.MaxAgeMSec );
			Assert.AreEqual( 3000, exp.PurgeIntervalMSec );
		}

		[Test]
		public virtual void testInvalid()
		{
			try
			{
				databaseServiceImpl.GetConnectionFactory( "xxx" );
				Assert.Fail();
			}
			catch ( DatabaseConfigException ex )
			{
				log.Debug( ex );
				// expected
			}
		}

		private static Log log = LogFactory.GetLog( typeof( TestDatabaseServiceImpl ) );
	}
}
