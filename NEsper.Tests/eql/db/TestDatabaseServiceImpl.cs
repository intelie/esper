///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;
using net.esper.client;
using net.esper.schedule;
using net.esper.support.eql;

using org.apache.commons.logging;

namespace net.esper.eql.db
{
	[TestFixture]
	public class TestDatabaseServiceImpl
	{
	    private DatabaseConfigServiceImpl databaseServiceImpl;

	    [SetUp]
	    public void SetUp()
	    {
	        EDictionary<String, ConfigurationDBRef> configs = new HashDictionary<String, ConfigurationDBRef>();

	        ConfigurationDBRef config = new ConfigurationDBRef();
	        config.SetDriverManagerConnection(SupportDatabaseService.DRIVER, "url", new Properties());
	        configs.Put("name1", config);

	        config = new ConfigurationDBRef();
	        config.SetDataSourceConnection("context", new Properties());
	        config.SetLRUCache(10000);
	        configs.Put("name2", config);

	        config = new ConfigurationDBRef();
	        config.SetDataSourceConnection("context", new Properties());
	        config.SetExpiryTimeCache(1, 3);
	        configs.Put("name3", config);

	        SchedulingService schedulingService = new SchedulingServiceImpl();
	        databaseServiceImpl = new DatabaseConfigServiceImpl(configs, schedulingService, schedulingService.AllocateBucket());
	    }

	    [Test]
	    public void TestGetConnection()
	    {
	        DatabaseConnectionFactory factory = databaseServiceImpl.GetConnectionFactory("name1");
	        Assert.IsTrue(factory is DatabaseDMConnFactory);

	        factory = databaseServiceImpl.GetConnectionFactory("name2");
	        Assert.IsTrue(factory is DatabaseDSConnFactory);
	    }

	    [Test]
	    public void TestGetCache()
	    {
	        Assert.IsTrue(databaseServiceImpl.GetDataCache("name1", null) is DataCacheNullImpl);

	        DataCacheLRUImpl lru = (DataCacheLRUImpl) databaseServiceImpl.GetDataCache("name2", null);
	        Assert.AreEqual(10000, lru.CacheSize);

	        DataCacheExpiringImpl exp = (DataCacheExpiringImpl) databaseServiceImpl.GetDataCache("name3", null);
	        Assert.AreEqual(1000, exp.MaxAgeMSec);
	        Assert.AreEqual(3000, exp.PurgeIntervalMSec);
	    }

	    [Test]
	    public void TestInvalid()
	    {
	        try
	        {
	            databaseServiceImpl.GetConnectionFactory("xxx");
	            Assert.Fail();
	        }
	        catch (DatabaseConfigException ex)
	        {
	            log.Debug(ex);
	            // expected
	        }
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
